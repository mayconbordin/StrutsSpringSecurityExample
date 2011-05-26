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
public class User implements Serializable {
    public static final int ROLE_USER = 0;
    public static final int ROLE_ADMIN = 1;

    @DocumentId
    private int id;

    @Field(index=Index.TOKENIZED,store=Store.YES)
    private String username;

    @Field(index=Index.TOKENIZED,store=Store.YES)
    private String password;

    @Field(index=Index.TOKENIZED,store=Store.YES)
    private boolean enabled;

    @Field(index=Index.TOKENIZED,store=Store.YES)
    private int authority;

    // generator:attributes
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    @XSSFilter
    @Length(max = 15)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @XSSFilter
    @Length(max = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @XSSFilter
    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @NotNull
    @XSSFilter
    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    // generator:methods
}