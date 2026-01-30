package com.jomarm.rest;

import com.jomarm.model.Entry;
import com.jomarm.model.EntryType;
import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import java.util.List;

public class Entries extends Controller {
    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance notes(List<Entry> entries);
    }

    public TemplateInstance notes() {
        List<Entry> entries = Entry.findByType(EntryType.Note);
        return Templates.notes(entries);
    }
}
