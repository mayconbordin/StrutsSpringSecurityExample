package com.app.web.model.repository;

import com.app.web.model.entity.User;
import com.strutstool.hibernate.repository.LookupRepositoryHibernate;
import com.strutstool.repository.RepositoryException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

public class UserRepositoryHibernate extends LookupRepositoryHibernate<User, Integer> implements UserRepository {
    public UserRepositoryHibernate() {
        super(User.class);
    }

    public User findByName(String name) throws RepositoryException {
        try {
            Criteria select = getSession().createCriteria( User.class );
            select.add( Restrictions.eq("username", name) );
            return (User) select.uniqueResult();
        } catch (HibernateException e) {
            throw new RepositoryException(e);
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }
}
