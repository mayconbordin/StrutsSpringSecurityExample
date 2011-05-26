package com.strutstool.hibernate.repository;

import com.strutstool.repository.LookupRepository;
import com.strutstool.repository.RepositoryException;
import com.strutstool.search.Operator;
import com.strutstool.search.SearchParams;
import java.io.Serializable;
import java.util.List;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

/**
 *
 * @author maycon
 */
public class LookupRepositoryHibernate<T, PK extends Serializable>
        extends BaseRepositoryHibernate<T, PK>
        implements LookupRepository<T, PK> {

    public LookupRepositoryHibernate(Class<T> persistentClass) {
        super(persistentClass);
    }

    // Implemented interface methods ===========================================

    public Integer countAll() throws RepositoryException {
        try {
            return count();
        } catch (RepositoryException ex) {
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

    public List<T> findAllWithPagination(int min, int max, String orderBy, String orderType)
            throws RepositoryException {
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

    public List<T> findAllByExample(T object, String orderBy, String orderType)
            throws RepositoryException {
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

    public List<T> findAllByExampleWithPagination(T object, int min, int max)
            throws RepositoryException {
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

    public List<T> search(String searchField, String searchString, String sortField,
            String sortOrder, Integer offset, Integer limit) throws RepositoryException {
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
            throw new RepositoryException(e);
        } catch (Exception e) {
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
        } catch (HibernateException ex) {
            throw new RepositoryException(ex);
        } catch (Exception ex) {
            throw new RepositoryException(ex);
        }
    }

}