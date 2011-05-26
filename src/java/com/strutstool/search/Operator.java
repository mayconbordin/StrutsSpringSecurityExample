package com.strutstool.search;

/**
 *
 * @author maycon
 */
public class Operator {
    private String name;
    private String key;
    private boolean onlyString;

    public static final String EQUAL = "eq";
    public static final String NOT_EQUAL = "ne";
    
    public static final String GREATER_THAN_OR_EQUAL = "ge";
    public static final String GREATER_THAN = "gt";

    public static final String I_LIKE = "ilike";
    public static final String LIKE = "like";

    public static final String LESS_THAN_OR_EQUAL = "le";
    public static final String LESS_THAN = "lt";

    public Operator(String name, String key, boolean onlyString) {
        this.name = name;
        this.key = key;
        this.onlyString = onlyString;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }
}
