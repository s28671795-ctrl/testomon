/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.profile.action;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.profile.context.EventContext;
import org.opensaml.profile.context.ProfileRequestContext;

public final class ActionSupport {
    private ActionSupport() {
    }

    public static void buildProceedEvent(@Nonnull ProfileRequestContext profileRequestContext) {
        profileRequestContext.removeSubcontext(EventContext.class);
    }

    public static void buildEvent(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull @NotEmpty String eventId) {
        Constraint.isNotNull(profileRequestContext, "Profile request context cannot be null");
        String trimmedEventId = Constraint.isNotNull(StringSupport.trimOrNull(eventId), "ID of event cannot be null or empty");
        ((EventContext)profileRequestContext.getSubcontext(EventContext.class, true)).setEvent(trimmedEventId);
    }
}

