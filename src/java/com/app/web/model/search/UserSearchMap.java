package com.app.web.model.search;

import com.strutstool.collection.DataCollection;
import com.strutstool.search.EntityField;
import com.strutstool.search.EntityFieldType;
import com.strutstool.search.EntitySearchMap;

public class UserSearchMap implements EntitySearchMap {

    private DataCollection<String, EntityField> fields;

    public DataCollection<String, EntityField> getFields() {
        fields = new DataCollection<String, EntityField>();
        // generator:fields
		fields.put("id", new EntityField("Id", "id", EntityFieldType.INT));
		fields.put("username", new EntityField("Username", "username", EntityFieldType.STRING));
		fields.put("password", new EntityField("Password", "password", EntityFieldType.STRING));
		fields.put("enabled", new EntityField("Enabled", "enabled", EntityFieldType.BOOLEAN));
		fields.put("authority", new EntityField("Authority", "authority", EntityFieldType.INT));


        return fields;
    }
    
}

