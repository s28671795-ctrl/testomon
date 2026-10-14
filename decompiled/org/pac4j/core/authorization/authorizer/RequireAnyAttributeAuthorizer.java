/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.authorizer;

import java.util.Collection;
import org.pac4j.core.authorization.authorizer.AbstractRequireAnyAuthorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;

public class RequireAnyAttributeAuthorizer
extends AbstractRequireAnyAuthorizer<String> {
    private final String valueToMatch;

    public RequireAnyAttributeAuthorizer() {
        this(".+");
    }

    public RequireAnyAttributeAuthorizer(String valueToMatch) {
        this.valueToMatch = valueToMatch;
    }

    @Override
    protected boolean check(WebContext context, SessionStore sessionStore, UserProfile profile, String element) {
        if (!profile.containsAttribute(element)) {
            return false;
        }
        if (CommonHelper.isBlank(this.valueToMatch)) {
            return true;
        }
        Object attributeValues = profile.getAttribute(element);
        if (attributeValues instanceof Collection) {
            return ((Collection)Collection.class.cast(attributeValues)).stream().anyMatch(v -> v.toString().matches(this.valueToMatch));
        }
        return attributeValues.toString().matches(this.valueToMatch);
    }

    public static RequireAnyAttributeAuthorizer requireAnyAttribute(String valueToMatch) {
        return new RequireAnyAttributeAuthorizer(valueToMatch);
    }
}

