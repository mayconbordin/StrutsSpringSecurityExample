package com.app.web.model.entity;

import com.strutstool.validator.constraints.NotNull;
import com.strutstool.validator.constraints.XSSFilter;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
// generator:imports

@Indexed
public class Marca implements Serializable {
    @DocumentId
    private int id;

    @Field(index=Index.TOKENIZED,store=Store.YES)
    private String nome;

    private Set<Produto> produtos = new HashSet();

    // generator:attributes
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    @XSSFilter
    @Length(max = 100)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return nome;
    }

    // generator:methods
}