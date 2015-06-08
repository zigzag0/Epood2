package ee.ttu.t031687.web.services;

import ee.ttu.t031687.web.beans.User;

public interface ILoginService {

    User check();

    User login(String username, String password);

    void logout();

}
