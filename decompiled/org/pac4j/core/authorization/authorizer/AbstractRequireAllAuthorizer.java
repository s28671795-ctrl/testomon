/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.authorizer;

import org.pac4j.core.authorization.authorizer.AbstractRequireElementAuthorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;

public abstract class AbstractRequireAllAuthorizer<E>
extends AbstractRequireElementAuthorizer<E> {
    @Override
    protected boolean isProfileAuthorized(WebContext context, SessionStore sessionStore, UserProfile profile) {
        if (this.elements == null || this.elements.isEmpty()) {
            return true;
        }
        for (Object element : this.elements) {
            if (this.check(context, sessionStore, profile, element)) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "elements", this.getElements());
    }
}

