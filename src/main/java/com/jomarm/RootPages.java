package com.jomarm;

import com.jomarm.entry.Entry;
import com.jomarm.entry.EntryType;
import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.Path;

import java.util.List;

public class RootPages extends Controller {
    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance index(List<Entry> notes);
    }

    @Path("/")
    public TemplateInstance index() {
        List<Entry> notes = Entry.findByType(EntryType.NOTE);
        return Templates.index(notes);
    }
}
