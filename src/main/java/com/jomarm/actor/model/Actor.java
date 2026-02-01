package com.jomarm.actor.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;

import java.net.URI;

@Entity
public class Actor extends PanacheEntity {
    public String name;
    public URI logo;
    public URI link;
    @Email
    public String email;
    @Column(columnDefinition = "text")
    public String note;

    public static Actor findLocal() {
        return find("link = ?1", URI.create("/")).firstResult();
    }
}
