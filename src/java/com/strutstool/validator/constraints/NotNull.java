package com.strutstool.validator.constraints;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;



/**
 * Validação de variável nula
 * 
 * @author Maycon Bordin
 * @version 1.0
 * @created 04-out-2010 13:24:50
 *
 */
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NotNullValidator.class)
@Documented
public @interface NotNull{

    /**
     *
     * @return
     */
    String message() default "{com.strutstool.validator.constraints.NotNull.message}";
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


	//no input parameters
}
