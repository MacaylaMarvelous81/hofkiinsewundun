package com.jomarm.actor.rest;

import com.jomarm.RootPages;
import com.jomarm.actor.model.Actor;
import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import org.jboss.resteasy.reactive.RestPath;

public class Actors extends Controller {
    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance actor(Actor actor);
    }

    public TemplateInstance actor(@RestPath Long id) {
        Actor actor = Actor.findById(id);

        // Redirect to index page for the local user
        if (actor.link.getHost() == null) {
            redirect(RootPages.class).index();
        }

        return Templates.actor(actor);
    }
}
