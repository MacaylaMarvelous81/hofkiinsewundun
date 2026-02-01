package com.jomarm.entry.rest;

import com.jomarm.entry.model.Entry;
import com.jomarm.entry.model.EntryType;
import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;

public class Entries extends Controller {
    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance entry(Entry entry);
        public static native TemplateInstance notes(List<Entry> entries);
    }

    public TemplateInstance entry(@RestPath Long id) {
        Entry entry = Entry.findById(id);
        return Templates.entry(entry);
    }

    public TemplateInstance notes() {
        List<Entry> entries = Entry.findByType(EntryType.NOTE);
        return Templates.notes(entries);
    }

    @Path("/o")
    public void legacyRedirect(@RestPath String legacyId) {
        Entry entry = Entry.findByLegacyId(legacyId);
        entry(entry.id);
    }
}
