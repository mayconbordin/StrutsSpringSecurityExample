package com.strutstool.pagination.manager;

import com.strutstool.pagination.ExtendedPaginatedList;
import com.strutstool.search.SearchParams;
import java.io.Serializable;

public interface PagingLookupManager<T, ID extends Serializable> {
    public ExtendedPaginatedList getAllRecordsPage(ExtendedPaginatedList paginatedList) throws PagingLookupManagerException;
    public ExtendedPaginatedList getSearchRecordsPage(SearchParams params, ExtendedPaginatedList paginatedList) throws PagingLookupManagerException;
}