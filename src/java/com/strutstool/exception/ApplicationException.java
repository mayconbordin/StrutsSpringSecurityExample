package com.strutstool.exception;

/**
 *
 * @author maycon
 */
public class ApplicationException extends Exception {
    private String detailedMessage;

    public ApplicationException() {
        super();
    }

    public ApplicationException( String message ) {
        super(message);
    }
    
    public ApplicationException( Exception ex , String message ) {
        super(message, ex.getCause());
        setDetailedMessage( String.valueOf(ex) );
    }

    public ApplicationException( Exception ex ) {
        super(ex.getMessage(), ex.getCause());
        setDetailedMessage( String.valueOf(ex) );
    }

    /**
     * @return the detailedMessage
     */
    public String getDetailedMessage() {
        return detailedMessage;
    }

    /**
     * @param detailedMessage the detailedMessage to set
     */
    public void setDetailedMessage(String detailedMessage) {
        this.detailedMessage = detailedMessage;
    }
}
