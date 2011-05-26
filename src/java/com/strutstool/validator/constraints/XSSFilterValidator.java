package com.strutstool.validator.constraints;

import com.strutstool.filter.HTMLInputFilter;
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
public class XSSFilterValidator implements ConstraintValidator<XSSFilter, Object> {

    XSSFilter xssFilter;

    /**
     * Constructor of the class
     * @param xssFilter the constraint class
     */
    public void initialize(XSSFilter xssFilter) {
            this.xssFilter = xssFilter;
    }

    /**
     *
     * @param object
     * @param cvc
     * @return boolean true if is valid or false otherwise
     */
    public boolean isValid(Object object, ConstraintValidatorContext cvc) {
        if (object == null) {
            return true;
        }
        
        String originalStr = object.toString();
        String filteredStr = new HTMLInputFilter().filter( originalStr );

        if (originalStr.equals(filteredStr)) {
            return true;
        } else {
            return false;
        }
    }
}