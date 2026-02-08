package com.jomarm.hofkiinsewundun.security;

import com.jomarm.hofkiinsewundun.RootPages;
import com.jomarm.hofkiinsewundun.actor.model.Actor;
import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.vertx.http.runtime.security.FormAuthenticationMechanism;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

public class Security extends Controller {
    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance login(List<Actor> loginCandidates);
    }

    @Inject
    SecurityIdentity identity;

    public TemplateInstance login() {
        if (!identity.isAnonymous()) {
            redirect(RootPages.class).index();
        }

        List<Actor> loginCandidates = Actor.findLocal();
        return Templates.login(loginCandidates);
    }

    @POST
    @RolesAllowed("user")
    public void logout() {
        FormAuthenticationMechanism.logout(identity);
        login();
    }
}
