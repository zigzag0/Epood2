package ee.ttu.t031687.web.services;

import ee.ttu.t031687.web.beans.User;

public interface IUserDAO extends IDAO<User> {
    
    User getByLogin(String login);
    
}
