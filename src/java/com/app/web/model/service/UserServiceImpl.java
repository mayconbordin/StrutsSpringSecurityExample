package com.app.web.model.service;

import com.app.web.model.repository.UserRepository;
import com.app.web.model.repository.UserRepositoryHibernate;
import com.app.web.model.entity.User;
import com.strutstool.repository.RepositoryException;
import com.strutstool.validator.Validator;
import com.strutstool.validator.ValidatorException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private Validator userValidator;

    public UserServiceImpl() {}

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // Implemented interface methods ===========================================

    public void save(User user) throws RepositoryException, ValidatorException {
        getUserValidator().validate(user);
        getUserRepository().save(user);
    }

    public void delete(Integer id) throws RepositoryException {
        User user = getUserRepository().get(id);
        getUserRepository().delete(user);
    }

    public List<User> findAll() throws RepositoryException {
        return getUserRepository().getAll();
    }

    public List<User> findAll(String orderBy, String orderType) throws RepositoryException {
        return getUserRepository().findAll(orderBy, orderType);
    }

    public User findById(Integer id) throws RepositoryException {
        return getUserRepository().get(id);
    }
    
    public List<User> findAllByExample(User user) throws RepositoryException {
        return getUserRepository().findAllByExample(user);
    }

    public List<User> findAllWithPagination(int min, int max, String orderBy, String orderType) throws RepositoryException {
        return getUserRepository().findAllWithPagination(min, max, orderBy, orderType);
    }

    public Integer count() throws RepositoryException {
        return getUserRepository().countAll();
    }

    // Getters and Setters =====================================================

    public UserRepository getUserRepository() {
        if (userRepository == null) {
            setUserRepository(new UserRepositoryHibernate());
        }

        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Validator getUserValidator() {
        if (userValidator == null) {
            setUserValidator(new Validator<User>());
        }
        return userValidator;
    }

    public void setUserValidator(Validator userValidator) {
        this.userValidator = userValidator;
    }
}
