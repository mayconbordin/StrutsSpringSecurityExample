package com.strutstool.search;

import com.strutstool.conversor.ObjectConverter;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author maycon
 */
public class EntityFieldType {
    public static final String STRING = "string";
    public static final String INT = "int";
    public static final String LONG = "long";
    public static final String SHORT = "short";
    public static final String FLOAT = "float";
    public static final String DOUBLE = "double";
    public static final String BIGDECIMAL = "bigdecimal";
    public static final String BOOLEAN = "boolean";
    public static final String DATE = "date";
    
    public static Object parse(String type, Object value) {
        if (type.equals(STRING)) {
            return value;
        } else if (type.equals(INT)) {
            return ObjectConverter.convert(value, Integer.class);
        } else if (type.equals(LONG)) {
            return ObjectConverter.convert(value, Long.class);
        } else if (type.equals(SHORT)) {
            return ObjectConverter.convert(value, Short.class);
        } else if (type.equals(FLOAT)) {
            return ObjectConverter.convert(value, Float.class);
        } else if (type.equals(DOUBLE)) {
            return ObjectConverter.convert(value, Double.class);
        } else if (type.equals(BIGDECIMAL)) {
            return ObjectConverter.convert(value, BigDecimal.class);
        } else if (type.equals(BOOLEAN)) {
            return ObjectConverter.convert(value, Boolean.class);
        } else if (type.equals(DATE)) {
            return ObjectConverter.convert(value, Date.class);
        }

        return null;
    }
}
