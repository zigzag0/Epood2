package ee.ttu.t031687.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ee.ttu.t031687.web.beans.User;
import ee.ttu.t031687.web.services.ILoginService;

@Controller
public class LoginController extends AbstractController {
    
    @Autowired
    ILoginService login;
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Result login(@RequestParam String usr, @RequestParam String pwd) {
        User user = login.login(usr, pwd);
        if(user == null) return Result.authfail();
        return Result.success(user);
    }
    
    @RequestMapping("/logout")
    @ResponseBody
    public Result logout() {
        login.logout();
        return Result.success();
    }
    
    @RequestMapping("/logincheck")
    @ResponseBody
    public Result check() {
        User user = login.check();
        return user == null ? Result.authfail() : Result.success(user);
    }

}
