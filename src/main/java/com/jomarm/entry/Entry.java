package com.jomarm.entry;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.util.Date;
import java.util.List;

@Entity
public class Entry extends PanacheEntity {
    public EntryType type;
    public String name;
    public String summary;
    public String htmlContent;
    public Date published;
    public Date updated;
    @ManyToOne
    public Entry replyTo;
    @Column(unique = true)
    public String legacyId;

    public static List<Entry> findByType(EntryType type) {
        return find("type = ?1 ORDER BY id", type).list();
    }

    public static Entry findByLegacyId(String legacyId) {
        return find("legacyId = ?1", legacyId).firstResult();
    }
}
