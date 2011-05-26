package com.app.web.model.service;

import com.app.web.model.repository.ProdutoRepository;
import com.app.web.model.repository.ProdutoRepositoryHibernate;
import com.app.web.model.entity.Produto;
import com.strutstool.repository.RepositoryException;
import com.strutstool.validator.Validator;
import com.strutstool.validator.ValidatorException;
import java.util.List;

public class ProdutoServiceImpl implements ProdutoService {
    private ProdutoRepository produtoRepository;
    private Validator produtoValidator;

    public ProdutoServiceImpl() {}

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    
    // Implemented interface methods ===========================================

    public void save(Produto produto) throws RepositoryException, ValidatorException {
        getProdutoValidator().validate(produto);
        getProdutoRepository().save(produto);
    }

    public void delete(Integer id) throws RepositoryException {
        Produto produto = getProdutoRepository().get(id);
        getProdutoRepository().delete(produto);
    }

    public List<Produto> findAll() throws RepositoryException {
        return getProdutoRepository().getAll();
    }

    public List<Produto> findAll(String orderBy, String orderType) throws RepositoryException {
        return getProdutoRepository().findAll(orderBy, orderType);
    }

    public Produto findById(Integer id) throws RepositoryException {
        return getProdutoRepository().get(id);
    }
    
    public List<Produto> findAllByExample(Produto produto) throws RepositoryException {
        return getProdutoRepository().findAllByExample(produto);
    }

    public List<Produto> findAllWithPagination(int min, int max, String orderBy, String orderType) throws RepositoryException {
        return getProdutoRepository().findAllWithPagination(min, max, orderBy, orderType);
    }

    public Integer count() throws RepositoryException {
        return getProdutoRepository().countAll();
    }

    // Getters and Setters =====================================================

    public ProdutoRepository getProdutoRepository() {
        if (produtoRepository == null) {
            setProdutoRepository(new ProdutoRepositoryHibernate());
        }

        return produtoRepository;
    }

    public void setProdutoRepository(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Validator getProdutoValidator() {
        if (produtoValidator == null) {
            setProdutoValidator(new Validator<Produto>());
        }
        return produtoValidator;
    }

    public void setProdutoValidator(Validator produtoValidator) {
        this.produtoValidator = produtoValidator;
    }
}
