package com.jomarm.hofkiinsewundun;

import com.jomarm.hofkiinsewundun.actor.model.Actor;
import com.jomarm.hofkiinsewundun.entry.model.Entry;
import com.jomarm.hofkiinsewundun.entry.model.EntryType;
import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.Path;

import java.util.List;

public class RootPages extends Controller {
    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance index(Actor actor, List<Entry> notes);
    }

    @Path("/")
    public TemplateInstance index() {
        Actor actor = Actor.findLocal().getFirst();
        List<Entry> notes = Entry.findByType(EntryType.NOTE);
        return Templates.index(actor, notes);
    }
}
