package com.app.web.controller;

// generator:imports

import com.app.web.model.entity.Marca;
import com.app.web.model.search.MarcaSearchMap;
import com.app.web.model.service.MarcaService;
import com.strutstool.pagination.manager.PagingLookupManagerException;
import com.strutstool.search.EntitySearchMap;
import com.strutstool.struts.StrutsController;
import com.strutstool.search.SearchAware;
import com.strutstool.validator.ValidatorException;
import com.opensymphony.xwork2.ModelDriven;
import com.strutstool.repository.RepositoryException;

public class MarcaController extends StrutsController 
        implements ModelDriven<Marca>, SearchAware {
    
    // generator:attributes

    private MarcaService marcaService;
    private Marca marca = new Marca();
    private Integer marcaId;

    // Actions =================================================================
    // generator:actions
    
    public String index() {
        try {
            paginatedList = paginateListFactory.getPaginatedListFromRequest(request);
            paginatedList.setPageSize(Integer.parseInt(getText("table.pagesize")));

            if (isSearch()) {
                paginatedList = pagingManager.getSearchRecordsPage(searchParams, paginatedList);
            } else {
                paginatedList = pagingManager.getAllRecordsPage(paginatedList);
            }
        } catch (PagingLookupManagerException ex) {
            errorHandler(ex);
        }
        
        statusHandler();
        return SUCCESS;
    }

    public String edit() {
        try {
            // generator:loaders

            if (isSave()) {
                getMarcaService().save(marca);
                return SUCCESS_SAVE;
            } else {
                marca = getMarcaService().findById(marcaId);
                if (marca == null) {
                    return NOT_FOUND;
                }
            }
        } catch (RepositoryException ex) {
            errorHandler(ex);
        } catch (ValidatorException ex) {
            errorHandler(ex);
        }

        return SUCCESS;
    }

    public String add() {
        try {
            // generator:loaders
        	
            if (isSave()) {
                getMarcaService().save(marca);
                return SUCCESS_SAVE;
            }
        } catch (RepositoryException ex) {
            errorHandler(ex);
        } catch (ValidatorException ex) {
            errorHandler(ex);
        }

        return SUCCESS;
    }

    public void delete() {
        try {
            getMarcaService().delete(marcaId);
        } catch (RepositoryException ex) {
            errorHandler(ex);
        }
    }

    // Implemented interface methods ===========================================

    public Marca getModel() {
        return marca;
    }

    public EntitySearchMap getEntitySearchMap() {
        return new MarcaSearchMap();
    }

    // Getters and Setters =====================================================
    // generator:accessors

    
    public MarcaService getMarcaService() {
        return marcaService;
    }

    public void setMarcaService(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Integer getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }
}
