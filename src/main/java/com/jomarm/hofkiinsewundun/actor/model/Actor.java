package com.jomarm.hofkiinsewundun.actor.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;

import java.net.URI;
import java.util.List;

@Entity
@UserDefinition
public class Actor extends PanacheEntity {
    @Username
    public String preferredUsername;

    @Password
    public String password;

    @Roles
    public String role;

    public String name;
    public URI logo;
    public URI link;

    @Email
    public String email;

    @Column(columnDefinition = "text")
    public String note;

    public static List<Actor> findLocal() {
        return list("link = ?1", URI.create("/"));
    }

    public static Actor findLocalByUsername(String username) {
        return find("link = ?1 and preferredUsername = ?2", URI.create("/"), username).singleResult();
    }
}
