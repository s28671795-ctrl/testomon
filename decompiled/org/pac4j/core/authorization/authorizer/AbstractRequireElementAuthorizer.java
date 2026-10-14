/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.authorizer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.pac4j.core.authorization.authorizer.ProfileAuthorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;

public abstract class AbstractRequireElementAuthorizer<E>
extends ProfileAuthorizer {
    protected Set<E> elements;

    @Override
    public boolean isAuthorized(WebContext context, SessionStore sessionStore, List<UserProfile> profiles) {
        return this.isAnyAuthorized(context, sessionStore, profiles);
    }

    protected abstract boolean check(WebContext var1, SessionStore var2, UserProfile var3, E var4);

    public Set<E> getElements() {
        return this.elements;
    }

    public void setElements(Set<E> elements) {
        this.elements = elements;
    }

    public void setElements(List<E> elements) {
        if (elements != null) {
            this.elements = new HashSet<E>(elements);
        }
    }

    public void setElements(E ... elements) {
        if (elements != null) {
            this.setElements(Arrays.asList(elements));
        }
    }
}

