package ee.ttu.t031687.web.services.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ee.ttu.t031687.web.beans.User;
import ee.ttu.t031687.web.services.ILoginService;
import ee.ttu.t031687.web.services.IUserDAO;

@Service
public class LoginService implements ILoginService {
    
    @Autowired
    private IUserDAO userDAO;
    
    public User check() {
        HttpSession s = session(false);
        if(s != null) {
            Long uid = (Long) s.getAttribute("currentUser");
            if(uid != null) {
                return userDAO.get(uid.longValue());
            }
        }
        return null;
    }
    
    public User login(String username, String password) {
        HttpSession s = session(true);
        User user = userDAO.getByLogin(username);
        
        if(user != null && password != null && user.getPassw().equals(password)) {
            s.setAttribute("currentUser", user.getId());
            return user;
        }
        
        return null;
    }
    
    public void logout() {
        HttpSession s = session(false);
        if(s != null) s.invalidate();
    }
    
    private static HttpSession session(boolean create) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(create);
    }
}
