package com.strutstool.validator.constraints;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;



/**
 * Validação de string contra injeção XSS
 *
 * @author Maycon Bordin
 * @version 1.0
 * @created 04-out-2010 13:24:50
 *
 */
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = XSSFilterValidator.class)
@Documented
public @interface XSSFilter {

    /**
     *
     * @return
     */
    String message() default "{com.strutstool.validator.constraints.XSSFilter.message}";
    
    /**
     *
     * @return
     */
    Class<?>[] groups() default {};
    /**
     *
     * @return
     */
    Class<? extends Payload>[] payload() default {};
}
