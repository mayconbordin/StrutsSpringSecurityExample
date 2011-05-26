package com.app.web.model.service;

import com.app.web.model.entity.User;
import com.strutstool.repository.RepositoryException;
import com.strutstool.validator.ValidatorException;
import java.util.List;

public interface UserService {
    public void save(User user) throws RepositoryException, ValidatorException;
    public void delete(Integer id) throws RepositoryException;
    public List<User> findAll() throws RepositoryException;
    public List<User> findAll(String orderBy, String orderType) throws RepositoryException;
    public User findById(Integer id) throws RepositoryException;

    public List<User> findAllByExample(User user) throws RepositoryException;
    public List<User> findAllWithPagination(int min, int max, String orderBy, String orderType) throws RepositoryException;

    public Integer count() throws RepositoryException;
}
