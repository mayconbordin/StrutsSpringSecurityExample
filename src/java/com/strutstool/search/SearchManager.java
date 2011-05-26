package com.strutstool.search;

import java.util.Map;

/**
 *
 * @author maycon
 */
public class SearchManager {
    private EntitySearchMap entitySearch;
    
    public SearchManager(EntitySearchMap entitySearch) {
        this.entitySearch = entitySearch;
    }
    public boolean isValid(SearchParams params) {
        if (entitySearch.getFields().containsKey(params.getField()) &&
                OperatorCollection.operators.isValid(params.getOperator())) {
            return true;
        }

        return false;
    }
}
