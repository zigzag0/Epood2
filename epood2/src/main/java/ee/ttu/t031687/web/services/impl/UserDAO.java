package ee.ttu.t031687.web.services.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ee.ttu.t031687.web.beans.User;
import ee.ttu.t031687.web.services.IUserDAO;

@Service
public class UserDAO extends AbstractDAO<User> implements IUserDAO {

    
    public UserDAO() {
        super(User.class);
    }

    @Transactional
    @Override
    public User getByLogin(String login) {
        DetachedCriteria c = DetachedCriteria.forClass(User.class).add(Restrictions.eq("username", login));
        @SuppressWarnings("unchecked")
        List<User> result = (List<User>) getHibernateTemplate().findByCriteria(c, 0, 1);
        return result.isEmpty() ? null : result.get(0);
    }

}
