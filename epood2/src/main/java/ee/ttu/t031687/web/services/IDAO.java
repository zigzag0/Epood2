package ee.ttu.t031687.web.services;

import java.util.List;

public interface IDAO<T> {
    
    T get(long id);
    
    void save(T c);

    void delete(long id);
    
    List<T> list();
}
