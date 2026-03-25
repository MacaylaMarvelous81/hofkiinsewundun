package com.jomarm.hofkiinsewundun.entry.rest;

import com.jomarm.hofkiinsewundun.actor.model.Actor;
import com.jomarm.hofkiinsewundun.entry.model.Entry;
import com.jomarm.hofkiinsewundun.entry.model.EntryType;
import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestPath;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class Entries extends Controller {
    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance editNote(Entry note);
        public static native TemplateInstance entry(Entry entry);
        public static native TemplateInstance notes(List<Entry> entries);
    }

    @Inject
    SecurityIdentity securityIdentity;

    public TemplateInstance entry(@RestPath Long id) {
        Entry entry = Entry.findById(id);
        return Templates.entry(entry);
    }

    @POST
    @RolesAllowed("user")
    public void createNote(@RestForm String name, @RestForm String summary, @RestForm String content) {
        Actor author = Actor.findLocalByUsername(securityIdentity.getPrincipal().getName());
        Date now = Date.from(Instant.now());

        Entry entry = new Entry();
        entry.type = EntryType.NOTE;
        entry.name = name;
        entry.summary = summary;
        entry.published = now;
        entry.updated = now;
        entry.htmlContent = content;
        entry.author = author;
        entry.persist();

        entry(entry.id);
    }

    @POST
    @RolesAllowed("user")
    public void edit(@RestPath Long id, @RestForm String name, @RestForm String summary, @RestForm String content) {
        Entry entry = Entry.findById(id);
        entry.updated = Date.from(Instant.now());
        entry.name = name;
        entry.summary = summary;
        entry.htmlContent = content;

        entry(entry.id);
    }

    public TemplateInstance notes() {
        List<Entry> entries = Entry.findByType(EntryType.NOTE);
        return Templates.notes(entries);
    }

    public TemplateInstance createNote() {
        return Templates.editNote(null);
    }

    public TemplateInstance editNote(@RestPath Long id) {
        Entry note = Entry.findById(id);
        return Templates.editNote(note);
    }

    @Path("/o")
    public void legacyRedirect(@RestPath String legacyId) {
        Entry entry = Entry.findByLegacyId(legacyId);
        entry(entry.id);
    }
}
