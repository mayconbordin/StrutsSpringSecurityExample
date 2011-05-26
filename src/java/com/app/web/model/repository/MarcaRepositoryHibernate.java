package com.app.web.model.repository;

import com.app.web.model.entity.Marca;
import com.strutstool.hibernate.repository.LookupRepositoryHibernate;

public class MarcaRepositoryHibernate extends LookupRepositoryHibernate<Marca, Integer>
        implements MarcaRepository {
    
    public MarcaRepositoryHibernate() {
        super(Marca.class);
    }
}
