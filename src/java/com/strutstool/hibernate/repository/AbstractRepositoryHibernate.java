package com.strutstool.hibernate.repository;

import com.strutstool.hibernate.HibernateUtil;
import com.strutstool.repository.RepositoryException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

/**
 *
 * @author maycon
 */
public abstract class AbstractRepositoryHibernate<T> {
    private Session session;
    private Class<T> persistentClass;

    public AbstractRepositoryHibernate(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    protected List<T> findByCriteria(Criterion... criterion) throws RepositoryException {
        try {
            Criteria criteria = getSession().createCriteria(getPersistentClass());
            for (Criterion c : criterion) {
                criteria.add(c);
            }
            return criteria.list();
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

    protected Integer count() throws RepositoryException {
        try {
            Criteria c = getSession().createCriteria(getPersistentClass());
            Number count = (Number) c.setProjection(Projections.rowCount()).uniqueResult();

            return count.intValue();
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

    protected Integer countByCriterion(Criterion criterion) throws RepositoryException {
        try {
            Criteria c = getSession().createCriteria(getPersistentClass());
            c.add(criterion);
            c.setProjection(Projections.rowCount());
            return (Integer) c.uniqueResult();
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

    protected Criteria pagination(int min, int max) throws RepositoryException {
        try {
            Integer count = 0;

            Criteria criteria = getSession().createCriteria(getPersistentClass());

            count = count();

            if (count < max) max = count;
            if (min >= 0) criteria.setFirstResult(min);
            if (max >= 0) criteria.setMaxResults(max);

            return criteria;
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

    protected void recover(Transaction t) {
        if (t != null) {
            t.rollback();
        }
        getSession().close();
        setSession(null);
    }

    public void createInitialLuceneIndex() throws RepositoryException {
        try {
            FullTextSession fullTextSession = Search.getFullTextSession(getSession());
            fullTextSession.createIndexer().startAndWait();
        } catch (InterruptedException ex) {
            throw new RepositoryException(ex);
        }
    }

    // Getters and Setters =====================================================

    public Session getSession() {
        if (session == null) {
            setSession(HibernateUtil.getSession());
        }

        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }
}
