package ee.ttu.t031687.web.services.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import ee.ttu.t031687.web.services.IDAO;

public abstract class AbstractDAO<T> extends HibernateDaoSupport implements IDAO<T> {
    
    private Class<T> clazz;

    public AbstractDAO(Class<T> clazz) {
        this.clazz = clazz;
    }
    
    @Autowired
    public final void init(SessionFactory factory) {
        setSessionFactory(factory);
    }
    
    @Transactional
    @Override
    public T get(long id) {
        return getHibernateTemplate().get(clazz, id);
    }

    @Transactional
    @Override
    public void save(T c) {
        getHibernateTemplate().saveOrUpdate(c);
    }

    @Transactional
    @Override
    public void delete(final long id) {
        getHibernateTemplate().execute(new HibernateCallback<Void>() {
            @Override
            public Void doInHibernate(Session session) {
                session.delete(session.load(clazz, id));
                return null;
            }
        });
    }
    
    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public List<T> list() {
        return (List<T>) getHibernateTemplate().find("from " + clazz.getSimpleName() + " order by name");
    }
}
