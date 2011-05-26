package com.strutstool.struts;

import com.strutstool.pagination.ExtendedPaginatedList;
import com.strutstool.pagination.factory.PaginateListFactory;
import com.strutstool.pagination.manager.PagingLookupManager;
import com.strutstool.search.EntityField;
import com.strutstool.search.SearchAware;
import com.strutstool.search.SearchParams;
import com.strutstool.validator.ValidatorException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author maycon
 */
public class StrutsController extends ActionSupport
        implements SessionAware, ServletRequestAware, ServletResponseAware, Preparable {
    // Result options
    public static final String SUCCESS_SAVE = "successSave";
    public static final String SUCCESS_DELETE = "successDelete";
    public static final String NOT_FOUND = "notFound";

    protected boolean save = false;
    protected String status;

    // Servlet vars
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Map<String, Object> session;

    // Search vars
    protected List<EntityField> fields;
    protected SearchParams searchParams;
    protected boolean search = false;

    // Paginator vars
    protected PagingLookupManager pagingManager;
    protected PaginateListFactory paginateListFactory;
    protected ExtendedPaginatedList paginatedList;

    // Logger
    protected static final Logger logger = Logger.getLogger(StrutsController.class);

    // Implemented interface methods ===========================================
    
    public void prepare() throws Exception {
        if (this instanceof SearchAware) {
            loadFields();
        }
    }

    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
    }

    // Utility methods =========================================================

    public void loadFields() {
        fields = ((SearchAware)this).getEntitySearchMap().getFields().listValues();

        for (EntityField field : fields) {
            field.setName( getText("label." + field.getKey()) );
        }
    }

    public void statusHandler() {
        if (status != null) {
            String message = getText("status." + status);
            List messages = new ArrayList();
            messages.add(message);

            setActionMessages(messages);
        }
    }

    public void errorHandler(Exception ex) {
        if (ex instanceof ValidatorException) {
            Map errors = new HashMap();

            for (ConstraintViolation constraint : ((ValidatorException)ex).getConstraintViolations()) {
                List message = new ArrayList();
                message.add(constraint.getMessage());
                errors.put(constraint.getPropertyPath().toString(), message);
            }

            setFieldErrors(errors);
        } else {
            List error = new ArrayList();
            error.add(ex.getMessage());
            setActionErrors(error);
        }
    }

    // Getters and Setters =====================================================

    public HttpServletRequest getServletRequest() {
        return request;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public ExtendedPaginatedList getPaginatedList() {
        return paginatedList;
    }

    public void setPaginatedList(ExtendedPaginatedList paginatedList) {
        this.paginatedList = paginatedList;
    }

    public void setPaginateListFactory(PaginateListFactory paginateListFactory) {
        this.paginateListFactory = paginateListFactory;
    }

    public List<EntityField> getFields() {
        return fields;
    }

    public void setFields(List<EntityField> fields) {
        this.fields = fields;
    }

    public void setPagingManager(PagingLookupManager pagingManager) {
        this.pagingManager = pagingManager;
    }

    public SearchParams getSearchParams() {
        return searchParams;
    }

    public void setSearchParams(SearchParams searchParams) {
        this.searchParams = searchParams;
    }
}
