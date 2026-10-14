/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.profile.context.navigate;

import com.google.common.base.Function;
import javax.annotation.Nullable;
import org.opensaml.profile.context.EventContext;
import org.opensaml.profile.context.PreviousEventContext;
import org.opensaml.profile.context.ProfileRequestContext;

public class CurrentOrPreviousEventLookup
implements Function<ProfileRequestContext, EventContext> {
    @Override
    @Nullable
    public EventContext apply(@Nullable ProfileRequestContext input) {
        if (input != null) {
            EventContext eventCtx = (EventContext)input.getSubcontext(EventContext.class);
            if (eventCtx != null && eventCtx.getEvent() != null) {
                return eventCtx;
            }
            return (EventContext)input.getSubcontext(PreviousEventContext.class);
        }
        return null;
    }
}

