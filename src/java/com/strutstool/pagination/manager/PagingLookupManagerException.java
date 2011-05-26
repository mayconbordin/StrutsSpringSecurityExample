package com.strutstool.pagination.manager;

import com.strutstool.exception.ApplicationException;

/**
 *
 * @author maycon
 */
public class PagingLookupManagerException extends ApplicationException {
    public PagingLookupManagerException() {
        super();
    }

    public PagingLookupManagerException( String message ) {
        super(message);
    }

    public PagingLookupManagerException( Exception ex , String message ) {
        super(ex, message);
    }

    public PagingLookupManagerException( Exception ex ) {
        super(ex);
    }
}
