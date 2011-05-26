package com.strutstool.search;

import com.strutstool.collection.DataCollection;

/**
 *
 * @author maycon
 */
public interface EntitySearchMap {
    /**
     * @return DataCollection<fieldName, isString>
     */
    public DataCollection<String, EntityField> getFields();
}
