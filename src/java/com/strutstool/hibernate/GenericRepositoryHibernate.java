package com.strutstool.hibernate;

import com.strutstool.repository.RepositoryException;
import com.strutstool.repository.GenericRepository;
import com.strutstool.search.Operator;
import com.strutstool.search.SearchParams;
import java.io.Serializable;
import java.util.List;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

/**
 * Classe abstrata para Repositorios.
 *
 * @author maycon
 */
public class GenericRepositoryHibernate<T, ID extends Serializable> implements GenericRepository<T, ID> {

    private Session session;
    private Class<T> persistentClass;

    /**
     * Construtor da Classe
     */
    public GenericRepositoryHibernate(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    // Implemented interface methods ===========================================

    public void save(T object) throws RepositoryException {
        Transaction t = null;
        try {
            t = getSession().beginTransaction();
            clear();
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

    public void save(List<T> objects) throws RepositoryException {
        Transaction t = null;
        try {
            t = getSession().beginTransaction();

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

    public void delete(List<T> objects) throws RepositoryException {
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

    public List<T> findAll() throws RepositoryException {
        try {
            return findByCriteria();
        } catch (RepositoryException ex) {
            throw ex;
        }
    }

    public T findById(ID id) throws RepositoryException {
        try {
            T entity = (T) getSession().get(getPersistentClass(), id);

            if (entity == null) {
                return null;
            }
            
            return entity;
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

    public List<T> findAllByExample(T object) throws RepositoryException {
        try {
            Example example = Example.create(object).ignoreCase()
                              .enableLike(MatchMode.ANYWHERE);

            return findByCriteria(example);
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

    public List<T> findAllByExample(T object, String orderBy, String orderType) throws RepositoryException {
        try {
            Example example = Example.create(object)
                                     .ignoreCase()
                                     .enableLike(MatchMode.ANYWHERE);
            
            Criteria c = getSession().createCriteria(getPersistentClass());
            c.add(example);

            if (orderType.toLowerCase().equals("desc")) {
                c.addOrder(Order.desc(orderBy));
            } else {
                c.addOrder(Order.asc(orderBy));
            }
            
            return c.list();
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

    public List<T> findAllByExampleWithPagination(T object, int min, int max) throws RepositoryException {
        try {
            Example example = Example.create(object)
                                     .ignoreCase()
                                     .enableLike(MatchMode.ANYWHERE);

            Criteria c = pagination(min, max);
            c.add(example);
            
            return c.list();
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

    public T findByExample(T object) throws RepositoryException {
        try {
            Example example = Example.create(object).ignoreCase();

            Criteria c = getSession().createCriteria(getPersistentClass());
            c.add(example);
            
            return (T) c.uniqueResult();
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

    public List<T> findAll(String orderBy, String orderType) throws RepositoryException {
        try {
            Criteria c = getSession().createCriteria(getPersistentClass());

            if (orderType.toLowerCase().equals("desc")) {
                c.addOrder(Order.desc(orderBy));
            } else {
                c.addOrder(Order.asc(orderBy));
            }

            return c.list();
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

    public List<T> findAllWithPagination(int min, int max) throws RepositoryException {
        try {
            Criteria c = pagination(min, max);

            return c.list();
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

    public Integer count() throws RepositoryException {
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

    public List<T> findAllWithPagination(int min, int max, String orderBy, String orderType) throws RepositoryException {
        try {
            Criteria c = pagination(min, max);

            if (orderBy != null) {
                if (orderType.toLowerCase().equals("desc")) {
                    c.addOrder(Order.desc(orderBy));
                } else {
                    c.addOrder(Order.asc(orderBy));
                }
            }

            return c.list();
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

    public List<T> findAllByFieldWithPagination(SearchParams params, int min,
            int max, String orderBy, String orderType) throws RepositoryException {
        try {
            String field = params.getField();
            String operator = params.getOperator();
            Object value = params.getValue();

            Criteria c = pagination(min, max);

            if (operator.equals(Operator.EQUAL)) {
                c.add( Restrictions.eq(field, value) );
            } else if (operator.equals(Operator.NOT_EQUAL)) {
                c.add( Restrictions.ne(field, value) );
            } else if (operator.equals(Operator.GREATER_THAN_OR_EQUAL)) {
                c.add( Restrictions.ge(field, value) );
            } else if (operator.equals(Operator.GREATER_THAN)) {
                c.add( Restrictions.gt(field, value) );
            } else if (operator.equals(Operator.I_LIKE)) {
                c.add( Restrictions.ilike(field, value) );
            } else if (operator.equals(Operator.LIKE)) {
                c.add( Restrictions.like(field, value) );
            } else if (operator.equals(Operator.LESS_THAN_OR_EQUAL)) {
                c.add( Restrictions.le(field, value) );
            } else if (operator.equals(Operator.LESS_THAN)) {
                c.add( Restrictions.lt(field, value) );
            }

            if (orderBy != null) {
                if (orderType.toLowerCase().equals("desc")) {
                    c.addOrder(Order.desc(orderBy));
                } else {
                    c.addOrder(Order.asc(orderBy));
                }
            }
            
            return c.list();
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

    public List<T> search(String searchField, String searchString, 
            String sortField, String sortOrder, Integer offset, Integer limit)
            throws RepositoryException {
        Transaction t = null;
        try {
            FullTextSession fullTextSession = Search.getFullTextSession(getSession());
            t = fullTextSession.beginTransaction();

            // create native Lucene query unsing the query DSL
            // alternatively you can write the Lucene query using the Lucene query parser
            // or the Lucene programmatic API. The Hibernate Search DSL is recommended though
            QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
                .buildQueryBuilder()
                .forEntity( getPersistentClass() )
                .get();

            org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .wildcard() // i also tried removing this line
                .onField(searchField) // anyway, let's say sortField is "updatedAt"
                .ignoreFieldBridge()
                .matching("*"+searchString+"*")
                .createQuery();
            
            // wrap Lucene query in a org.hibernate.Query
            FullTextQuery fullTextQuery =
                fullTextSession.createFullTextQuery(query, getPersistentClass());
            
            Boolean isReversed = false;
            if(sortOrder.equals("desc")) {
                isReversed = true;
            }

            fullTextQuery.setSort(new Sort(new SortField(sortField, SortField.STRING, isReversed)));
            fullTextQuery.setFirstResult(offset);
            fullTextQuery.setMaxResults(limit);

            // execute search
            List result = fullTextQuery.list();
            t.commit();
            return result;
        } catch (HibernateException e) {
            System.out.println("ERRO: " + e.getMessage());
            //recover(t);
            throw new RepositoryException(e);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            //recover(t);
            throw new RepositoryException(e);
        }
    }

    public int countSearch(String searchField, String searchString) throws RepositoryException {
        Transaction t = null;
        try {
            FullTextSession fullTextSession = Search.getFullTextSession(getSession());
            t = fullTextSession.beginTransaction();

            // create native Lucene query unsing the query DSL
            // alternatively you can write the Lucene query using the Lucene query parser
            // or the Lucene programmatic API. The Hibernate Search DSL is recommended though
            QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
                .buildQueryBuilder()
                .forEntity( getPersistentClass() )
                .get();

            org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .wildcard() // i also tried removing this line
                .onField(searchField) // anyway, let's say sortField is "updatedAt"
                .ignoreFieldBridge()
                .matching("*"+searchString+"*")
                .createQuery();

            // wrap Lucene query in a org.hibernate.Query
            FullTextQuery fullTextQuery =
                fullTextSession.createFullTextQuery(query, getPersistentClass());

            int count = fullTextQuery.getResultSize();

            t.commit();
            return count;
        } catch (HibernateException e) {
            System.out.println("ERRO: " + e.getMessage());
            //recover(t);
            throw new RepositoryException(e);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            //recover(t);
            throw new RepositoryException(e);
        }
    }

    // Functionality methods ===================================================
    
    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }

    private void recover(Transaction t) {
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

    /**
     *
     * @param criterion Undefined number of arguments (varargs)
     * @return
     */
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
