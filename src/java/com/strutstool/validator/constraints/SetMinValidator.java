package com.strutstool.validator.constraints;

import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Verifica se o conjunto tem a quantidade mínima exigida
 *
 * @author Maycon Bordin
 * @version 1.0
 * @created 04-out-2010 13:24:50
 *
 */
public class SetMinValidator implements ConstraintValidator<SetMin, Object> {

	SetMin setMin;

        /**
         * Constructor of the class
         * @param setMin the constraint class
         */
	public void initialize(SetMin setMin) {
		this.setMin = setMin;
	}

        /**
         *
         * @param object
         * @param cvc
         * @return boolean true if is valid or false otherwise
         */
	public boolean isValid(Object object, ConstraintValidatorContext cvc) {
            Set set = (Set) object;

            if ( set.size() >= setMin.value() ) {
                return true;
            } else {
                return false;
            }
	}
}