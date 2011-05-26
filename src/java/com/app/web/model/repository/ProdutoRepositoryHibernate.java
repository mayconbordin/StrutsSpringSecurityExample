package com.app.web.model.repository;

import com.app.web.model.entity.Produto;
import com.strutstool.hibernate.repository.LookupRepositoryHibernate;

public class ProdutoRepositoryHibernate extends LookupRepositoryHibernate<Produto, Integer>
        implements ProdutoRepository {
    
    public ProdutoRepositoryHibernate() {
        super(Produto.class);
    }
}
