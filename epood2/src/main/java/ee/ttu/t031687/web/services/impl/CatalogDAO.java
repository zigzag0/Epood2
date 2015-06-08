package ee.ttu.t031687.web.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ee.ttu.t031687.web.beans.Catalog;
import ee.ttu.t031687.web.services.ICatalogDAO;

@Service
public class CatalogDAO extends AbstractDAO<Catalog> implements ICatalogDAO {

    public CatalogDAO() {
        super(Catalog.class);
    }
    
    @Transactional
    @Override
    public List<Catalog> list() {
        
        @SuppressWarnings("unchecked")
        List<Catalog> all = (List<Catalog>) getHibernateTemplate().find("from Catalog order by name");
        
        List<Catalog> roots = new ArrayList<Catalog>();
        Map<Long, Catalog> map = new HashMap<Long, Catalog>();
        
        // add to map
        for(Catalog c: all) {
            map.put(c.getId(), c);
            if(c.getUpperCatalog() == null || c.getUpperCatalog().longValue() == 0) {
                roots.add(c);
            }
        }
        
        // build hierarchy
        for(Catalog c: all) {
            Catalog u = map.get(c.getUpperCatalog());
            if(u != null) {
                u.addSubCatalog(c);
            }
        }
        
        return roots;
    }
    
}
