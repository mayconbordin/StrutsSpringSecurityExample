package com.strutstool.hibernate.repository;

import com.strutstool.repository.BaseRepository;
import com.strutstool.repository.RepositoryException;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

/**
 *
 * @author maycon
 */
public class BaseRepositoryHibernate<T, PK extends Serializable> extends AbstractRepositoryHibernate
        implements BaseRepository<T, PK> {

    public BaseRepositoryHibernate(Class<T> persistentClass) {
        super(persistentClass);
    }

    // Implemented interface methods ===========================================

    public void save(T object) throws RepositoryException {
        Transaction t = null;
        try {
            t = getSession().beginTransaction();
            getSession().clear();
            getSession().saveOrUpdate(object);
            t.commit();
        } catch (HibernateException e) {
            recover(t);
            throw new RepositoryException(e);
        } catch (Exception e) {
            recover(t);
            throw new RepositoryException(e);
        }
    }

    public void saveAll(List<T> objects) throws RepositoryException {
        Transaction t = null;
        try {
            t = getSession().beginTransaction();
            getSession().clear();

            for (T object : objects) {
                getSession().saveOrUpdate(object);
            }

            t.commit();
        } catch (HibernateException e) {
            recover(t);
            throw new RepositoryException(e);
        } catch (Exception e) {
            recover(t);
            throw new RepositoryException(e);
        }
    }

    public void delete(T object) throws RepositoryException {
        Transaction t = null;
        try {
            t = getSession().beginTransaction();
            getSession().delete(object);
            t.commit();
        } catch (HibernateException ex) {
            recover(t);
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            recover(t);
            throw new RepositoryException(ex);
        }
    }

    public void deleteAll(List<T> objects) throws RepositoryException {
        Transaction t = null;
        try {
            t = getSession().beginTransaction();

            for (T object : objects) {
                getSession().delete(object);
            }

            t.commit();
        } catch (HibernateException ex) {
            recover(t);
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            recover(t);
            throw new RepositoryException(ex);
        }
    }

    public T get(PK id) throws RepositoryException {
        try {
            T entity = (T) getSession().get(getPersistentClass(), id);
            return entity;
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

    public List<T> getAll() throws RepositoryException {
        try {
            return findByCriteria();
        } catch (RepositoryException ex) {
            throw new RepositoryException(ex);
        }
    }

    public boolean exists(PK id) throws RepositoryException {
        try {
            T entity = (T) getSession().get(getPersistentClass(), id);
            return (entity == null);
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }
}
