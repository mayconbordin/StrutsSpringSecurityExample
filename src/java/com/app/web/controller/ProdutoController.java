package com.app.web.controller;

// generator:imports
import com.app.web.model.entity.Marca;
import com.app.web.model.service.MarcaServiceImpl;
import java.util.List;
import com.app.web.model.entity.Produto;
import com.app.web.model.search.ProdutoSearchMap;
import com.app.web.model.service.ProdutoService;
import com.strutstool.pagination.manager.PagingLookupManagerException;
import com.strutstool.search.EntitySearchMap;
import com.strutstool.struts.StrutsController;
import com.strutstool.search.SearchAware;
import com.strutstool.validator.ValidatorException;
import com.opensymphony.xwork2.ModelDriven;
import com.strutstool.repository.RepositoryException;

public class ProdutoController extends StrutsController 
        implements ModelDriven<Produto>, SearchAware {
    
    // generator:attributes
    private List<Marca> marcaList;

    private ProdutoService produtoService;
    private Produto produto = new Produto();
    private Integer produtoId;

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
            marcaList = new MarcaServiceImpl().findAll();
        	
            if (isSave()) {
                getProdutoService().save(produto);
                return SUCCESS_SAVE;
            } else {
                produto = getProdutoService().findById(produtoId);
                if (produto == null) {
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
            marcaList = new MarcaServiceImpl().findAll();
        	
            if (isSave()) {
                getProdutoService().save(produto);
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
            getProdutoService().delete(produtoId);
        } catch (RepositoryException ex) {
            errorHandler(ex);
        }
    }

    // Implemented interface methods ===========================================

    public Produto getModel() {
        return produto;
    }

    public EntitySearchMap getEntitySearchMap() {
        return new ProdutoSearchMap();
    }

    // Getters and Setters =====================================================
    // generator:accessors
    public List<Marca> getMarcaList() {
        return marcaList;
    }

    public void setMarcaList(List<Marca> marcaList) {
        this.marcaList = marcaList;
    }

    
    public ProdutoService getProdutoService() {
        return produtoService;
    }

    public void setProdutoService(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }
}
