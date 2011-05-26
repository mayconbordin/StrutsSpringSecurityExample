package com.app.web.model.search;

import com.strutstool.collection.DataCollection;
import com.strutstool.search.EntityField;
import com.strutstool.search.EntityFieldType;
import com.strutstool.search.EntitySearchMap;

public class MarcaSearchMap implements EntitySearchMap {

    private DataCollection<String, EntityField> fields;

    public DataCollection<String, EntityField> getFields() {
        fields = new DataCollection<String, EntityField>();
        // generator:fields
		fields.put("id", new EntityField("Id", "id", EntityFieldType.INT));
		fields.put("nome", new EntityField("Nome", "nome", EntityFieldType.STRING));


        return fields;
    }
    
}

