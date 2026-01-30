package com.jomarm.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.util.Date;
import java.util.List;

@Entity
public class Entry extends PanacheEntity {
    public EntryType type;
    public String name;
    public String summary;
    public Date published;
    public Date updated;
    @ManyToOne
    public Entry replyTo;

    public static List<Entry> findByType(EntryType type) {
        return find("type = ?1 ORDER BY id", type).list();
    }
}
