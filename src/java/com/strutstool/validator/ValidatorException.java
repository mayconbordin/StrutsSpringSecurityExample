package com.strutstool.validator;

import com.strutstool.exception.ApplicationException;
import java.util.Set;
import javax.validation.ConstraintViolation;

/**
 * Exceção para Validador
 *
 * @author maycon
 */
public class ValidatorException extends ApplicationException {
    private static final String message = "Foram encontrados erros nos dados validados.";
    private Set<ConstraintViolation> constraintViolations;
    private String detailedMessage;

    /**
     * Constructor of the exception
     * @param constraintViolations
     */
    public ValidatorException( Object constraintViolations ) {
        super(message);
        this.constraintViolations = (Set<ConstraintViolation>) constraintViolations;

        makeDetailedMessage();
    }

    /**
     *
     * @param message
     */
    public ValidatorException( String message ) {
        super(message);
    }

    /**
     *
     */
    public ValidatorException() {
        super(message);
    }

    /**
     * Cria mensagem detalhada com base nas violações recebidas
     */
    private void makeDetailedMessage() {
        detailedMessage = "";

        for ( ConstraintViolation constraint : (Set<ConstraintViolation>) constraintViolations ) {
            detailedMessage += constraint.getMessage() + "\n";
        }
    }

    /**
     * @return the constraintViolations
     */
    public Set<ConstraintViolation> getConstraintViolations() {
        return constraintViolations;
    }

    /**
     * @return the detailedMessage
     */
    @Override
    public String getDetailedMessage() {
        return detailedMessage;
    }

    @Override
    public String toString() {
        String errors = "";

        for ( ConstraintViolation constraint : constraintViolations ) {
            errors += "Erro em: " + constraint.getPropertyPath().toString() +
                    "\nMensagem:" + constraint.getMessage();
        }

        return errors;
    }

}