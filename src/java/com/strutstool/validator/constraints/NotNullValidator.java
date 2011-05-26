package com.strutstool.validator.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Verifica se um objeto é nulo ou não
 * 
 * @author Maycon Bordin
 * @version 1.0
 * @created 04-out-2010 13:24:50
 *
 */
public class NotNullValidator implements ConstraintValidator<NotNull, Object> {

	NotNull notNull;

        /**
         * Constructor of the class
         * @param notNull the constraint class
         */
	public void initialize(NotNull notNull) {
		this.notNull = notNull;
	}

        /**
         *
         * @param object
         * @param cvc
         * @return boolean true if is valid or false otherwise
         */
	public boolean isValid(Object object, ConstraintValidatorContext cvc) {
            try {
                if( object.toString().isEmpty() || object == null
                        || object.toString().trim().length() == 0 ) {
                    return false;
                } else {
                    return true;
                }
            } catch (NullPointerException ex) {
                return false;
            }
	}
}