/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.context.navigate.ContextDataLookupFunction
 */
package org.opensaml.profile.context.navigate;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.profile.context.ProfileRequestContext;

public class OutboundMessageContextLookup
implements ContextDataLookupFunction<ProfileRequestContext, MessageContext> {
    @Nullable
    public MessageContext apply(@Nullable ProfileRequestContext input) {
        if (input != null) {
            return input.getOutboundMessageContext();
        }
        return null;
    }
}

