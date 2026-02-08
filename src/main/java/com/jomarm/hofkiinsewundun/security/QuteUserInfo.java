package com.jomarm.hofkiinsewundun.security;

import com.jomarm.hofkiinsewundun.actor.model.Actor;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
@Named("user")
public class QuteUserInfo {
    @Inject
    SecurityIdentity identity;

    public boolean getIsAnonymous() {
        return identity.isAnonymous();
    }

    public Actor getActor() {
        return Actor.findLocalByUsername(identity.getPrincipal().getName());
    }
}
