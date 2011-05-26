package com.app.web.model.service;

import com.app.web.model.repository.MarcaRepository;
import com.app.web.model.repository.MarcaRepositoryHibernate;
import com.app.web.model.entity.Marca;
import com.strutstool.repository.RepositoryException;
import com.strutstool.validator.Validator;
import com.strutstool.validator.ValidatorException;
import java.util.List;

public class MarcaServiceImpl implements MarcaService {
    private MarcaRepository marcaRepository;
    private Validator marcaValidator;

    public MarcaServiceImpl() {}

    public MarcaServiceImpl(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }
    
    // Implemented interface methods ===========================================

    public void save(Marca marca) throws RepositoryException, ValidatorException {
        getMarcaValidator().validate(marca);
        getMarcaRepository().save(marca);
    }

    public void delete(Integer id) throws RepositoryException {
        Marca marca = getMarcaRepository().get(id);
        getMarcaRepository().delete(marca);
    }

    public List<Marca> findAll() throws RepositoryException {
        return getMarcaRepository().getAll();
    }

    public List<Marca> findAll(String orderBy, String orderType) throws RepositoryException {
        return getMarcaRepository().findAll(orderBy, orderType);
    }

    public Marca findById(Integer id) throws RepositoryException {
        return getMarcaRepository().get(id);
    }
    
    public List<Marca> findAllByExample(Marca marca) throws RepositoryException {
        return getMarcaRepository().findAllByExample(marca);
    }

    public List<Marca> findAllWithPagination(int min, int max, String orderBy, String orderType) throws RepositoryException {
        return getMarcaRepository().findAllWithPagination(min, max, orderBy, orderType);
    }

    public Integer count() throws RepositoryException {
        return getMarcaRepository().countAll();
    }

    // Getters and Setters =====================================================

    public MarcaRepository getMarcaRepository() {
        if (marcaRepository == null) marcaRepository = new MarcaRepositoryHibernate();
        return marcaRepository;
    }

    public void setMarcaRepository(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    public Validator getMarcaValidator() {
        if (marcaValidator == null) marcaValidator = new Validator<Marca>();
        return marcaValidator;
    }

    public void setMarcaValidator(Validator marcaValidator) {
        this.marcaValidator = marcaValidator;
    }
}
