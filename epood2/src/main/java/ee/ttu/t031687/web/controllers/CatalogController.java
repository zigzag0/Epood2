package ee.ttu.t031687.web.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ee.ttu.t031687.web.beans.Catalog;
import ee.ttu.t031687.web.beans.User;
import ee.ttu.t031687.web.services.ICatalogDAO;
import ee.ttu.t031687.web.services.ILoginService;


@Controller
@RequestMapping(value = "/catalogs")
public class CatalogController extends AbstractController {
    
    @Autowired
    ILoginService login;
    
    @Autowired
    ICatalogDAO catalogs;
    
    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Result all() {
        User user = login.check();
        if(user == null) return Result.authfail();
        
        return Result.success(catalogs.list());
    }
    
    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Result add(@RequestBody Catalog req) {
        User user = login.check();
        if(user == null) return Result.authfail();
        
        Catalog c = new Catalog();
        c.setName(req.getName());
        c.setDescription(req.getName());
        c.setUpperCatalog(req.getUpperCatalog());
        c.setCreated(new Date());
        c.setUpdated(new Date());
        c.setCreatedBy(user.getId());
        c.setUpdatedBy(user.getId());
        c.setStructUnit(11);
        catalogs.save(c);
        
        return Result.success(c);
    }
    
    @Transactional
    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody Result update(@RequestBody List<Catalog> reqs) {
        User user = login.check();
        if(user == null) return Result.authfail();
        
        List<Catalog> cs = new ArrayList<Catalog>();
        
        for(Catalog req: reqs) {
            Catalog c = catalogs.get(req.getId());
            if(c == null) return Result.notFound();
            
            if(req.getName() != null) c.setName(req.getName());
            if(req.getName() != null) c.setDescription(req.getName());
            if(req.getUpperCatalog() != null) c.setUpperCatalog(req.getUpperCatalog());
            c.setUpdated(new Date());
            c.setUpdatedBy(user.getId());
            catalogs.save(c);
            cs.add(c);
        }
        
        return Result.success(cs);
    }
    
    @Transactional
    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody Result delete(@RequestBody List<Long> ids) {
        User user = login.check();
        if(user == null) return Result.authfail();
        
        List<Catalog> allCatalogs = catalogs.list();
        Map<Long, Catalog> map = new HashMap<Long, Catalog>();
        mapCatalogs(allCatalogs, map);
        
        // collect catalogs to delete
        List<Catalog> delete = new ArrayList<Catalog>();
        for(Long id: ids) {
            Catalog c = map.get(id);
            if(c != null) {
                Catalog up = map.get(c.getUpperCatalog());
                if(up != null) {
                    if(up.getSubCatalogs() != null) up.getSubCatalogs().remove(c);
                }
                delete.add(c);
            }
        }
        
        // do not delete non-empty catalogs
        Iterator<Catalog> it = delete.iterator();
        while(it.hasNext()) {
            Catalog c = it.next();
            if(c.getSubCatalogs() != null && !c.getSubCatalogs().isEmpty()) {
                it.remove();
            }
        }
        
        for(Catalog c: delete) {
            catalogs.delete(c.getId());
        }
        
        return Result.success();
    }
    
    @RequestMapping(value = "selection", method = RequestMethod.GET)
    public @ResponseBody Result getSelection(HttpSession session) {
        User user = login.check();
        if(user == null) return Result.authfail();
        
        @SuppressWarnings("unchecked")
        Set<Long> selection = (Set<Long>) session.getAttribute("catalogSelection");
        if(selection == null) {
            selection = Collections.emptySet();
        }
        
        return Result.success(selection);
    }
    
    @RequestMapping(value = "selection", method = RequestMethod.POST)
    public @ResponseBody Result setSelection(@RequestBody Set<Long> selection, HttpSession session) {
        User user = login.check();
        if(user == null) return Result.authfail();
        
        session.setAttribute("catalogSelection", selection);
        
        return Result.success();
    }
    
    private void mapCatalogs(List<Catalog> cs, Map<Long, Catalog> map) {
        for(Catalog c: cs) {
            map.put(c.getId(), c);
            if(c.getSubCatalogs() != null) {
                mapCatalogs(c.getSubCatalogs(), map);
            }
        }
    }

}