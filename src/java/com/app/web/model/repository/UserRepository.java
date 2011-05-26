package com.app.web.model.repository;

import com.app.web.model.entity.User;
import com.strutstool.repository.LookupRepository;
import com.strutstool.repository.RepositoryException;

public interface UserRepository extends LookupRepository<User, Integer>  {
    public User findByName(String name) throws RepositoryException;
}
