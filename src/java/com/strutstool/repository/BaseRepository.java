package com.strutstool.repository;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author maycon
 */
public interface BaseRepository<T, PK extends Serializable> {
    public void save(T object) throws RepositoryException;
    public void saveAll(List<T> objects) throws RepositoryException;

    public void delete(T object) throws RepositoryException;
    public void deleteAll(List<T> objects) throws RepositoryException;

    public T get(PK id) throws RepositoryException;
    public List<T> getAll() throws RepositoryException;

    public boolean exists(PK id) throws RepositoryException;
}
