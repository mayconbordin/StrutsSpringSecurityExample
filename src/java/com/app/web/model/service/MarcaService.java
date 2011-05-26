package com.app.web.model.service;

import com.app.web.model.entity.Marca;
import com.strutstool.repository.RepositoryException;
import com.strutstool.validator.ValidatorException;
import java.util.List;

public interface MarcaService {
    public void save(Marca marca) throws RepositoryException, ValidatorException;
    public void delete(Integer id) throws RepositoryException;
    public List<Marca> findAll() throws RepositoryException;
    public List<Marca> findAll(String orderBy, String orderType) throws RepositoryException;
    public Marca findById(Integer id) throws RepositoryException;

    public List<Marca> findAllByExample(Marca marca) throws RepositoryException;
    public List<Marca> findAllWithPagination(int min, int max, String orderBy, 
            String orderType) throws RepositoryException;

    public Integer count() throws RepositoryException;
}
