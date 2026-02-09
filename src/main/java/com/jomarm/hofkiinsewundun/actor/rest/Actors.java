package com.jomarm.hofkiinsewundun.actor.rest;

import com.jomarm.hofkiinsewundun.RootPages;
import com.jomarm.hofkiinsewundun.actor.model.Actor;
import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.POST;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestPath;

public class Actors extends Controller {
    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance actor(Actor actor);
        public static native TemplateInstance edit(Actor actor);
    }

    @Inject
    SecurityIdentity securityIdentity;

    public TemplateInstance actor(@RestPath Long id) {
        Actor actor = Actor.findById(id);

        // Redirect to index page for the local user
        if (actor.link.getHost() == null) {
            redirect(RootPages.class).index();
        }

        return Templates.actor(actor);
    }

    @POST
    @RolesAllowed("user")
    public void edit(@RestPath Long id, @RestForm String name, @RestForm String email, @RestForm String note) {
        if (Actor.findLocalByUsername(securityIdentity.getPrincipal().getName()).id != id) {
            throw new NotAuthorizedException("Users may only edit their own information.");
        }

        Actor actor = Actor.findById(id);
        actor.name = name;
        actor.email = email;
        actor.note = note;

        actor(id);
    }

    @RolesAllowed("user")
    public TemplateInstance editSelf() {
        Actor self = Actor.findLocalByUsername(securityIdentity.getPrincipal().getName());
        return Templates.edit(self);
    }
}
