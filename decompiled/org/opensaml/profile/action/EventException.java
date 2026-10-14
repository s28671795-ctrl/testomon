/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.profile.action;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class EventException
extends RuntimeException {
    private static final long serialVersionUID = -6394047591957378161L;
    private final String eventID;

    public EventException(@Nonnull String event) {
        this.eventID = Constraint.isNotNull(StringSupport.trimOrNull(event), "Event ID may not be null");
    }

    public EventException(@Nonnull String event, @Nullable String message) {
        super(message);
        this.eventID = Constraint.isNotNull(StringSupport.trimOrNull(event), "Event ID may not be null");
    }

    public EventException(@Nonnull String event, @Nullable Throwable cause) {
        super(cause);
        this.eventID = Constraint.isNotNull(StringSupport.trimOrNull(event), "Event ID may not be null");
    }

    public EventException(@Nonnull String event, @Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
        this.eventID = Constraint.isNotNull(StringSupport.trimOrNull(event), "Event ID may not be null");
    }

    @Nonnull
    public String getEventID() {
        return this.eventID;
    }
}

