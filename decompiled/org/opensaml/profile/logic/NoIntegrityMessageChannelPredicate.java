/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageChannelSecurityContext
 */
package org.opensaml.profile.logic;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import org.opensaml.messaging.context.MessageChannelSecurityContext;
import org.opensaml.profile.context.ProfileRequestContext;

public class NoIntegrityMessageChannelPredicate
implements Predicate<ProfileRequestContext> {
    @Override
    public boolean apply(@Nullable ProfileRequestContext input) {
        return input == null || !((MessageChannelSecurityContext)input.getSubcontext(MessageChannelSecurityContext.class, true)).isIntegrityActive();
    }
}

