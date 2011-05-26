package com.app.web.model.service;

import com.app.web.model.entity.Produto;
import com.strutstool.repository.RepositoryException;
import com.strutstool.validator.ValidatorException;
import java.util.List;

public interface ProdutoService {
    public void save(Produto produto) throws RepositoryException, ValidatorException;
    public void delete(Integer id) throws RepositoryException;
    public List<Produto> findAll() throws RepositoryException;
    public List<Produto> findAll(String orderBy, String orderType) throws RepositoryException;
    public Produto findById(Integer id) throws RepositoryException;

    public List<Produto> findAllByExample(Produto produto) throws RepositoryException;
    public List<Produto> findAllWithPagination(int min, int max, String orderBy, String orderType) throws RepositoryException;

    public Integer count() throws RepositoryException;
}
