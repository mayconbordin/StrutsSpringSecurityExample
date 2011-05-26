package com.strutstool.repository;

import com.strutstool.exception.ApplicationException;

/**
 *
 * @author maycon
 */
public class RepositoryException extends ApplicationException {
    public RepositoryException() {
        super();
    }

    public RepositoryException( String message ) {
        super(message);
    }

    public RepositoryException( Exception ex , String message ) {
        super(ex, message);
    }

    public RepositoryException( Exception ex ) {
        super(ex);
    }
}
