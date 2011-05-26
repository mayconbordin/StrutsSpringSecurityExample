package com.app.web.model.entity;

import com.strutstool.validator.constraints.NotNull;
import com.strutstool.validator.constraints.XSSFilter;
import java.io.Serializable;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
// generator:imports

@Indexed
public class Produto implements Serializable {
    @DocumentId
    private int id;

    @Field(index=Index.TOKENIZED,store=Store.YES)
    private String nome;

    private Marca marca;

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

    @NotNull
    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    // generator:methods
}