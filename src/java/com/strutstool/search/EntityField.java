package com.strutstool.search;

/**
 *
 * @author maycon
 */
public class EntityField {
    private String name;
    private String key;
    private String type;

    public EntityField(String name, String key, String type) {
        this.name = name;
        this.key = key;
        this.type = type;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
