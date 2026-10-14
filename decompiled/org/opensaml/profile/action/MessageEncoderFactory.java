/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.encoder.MessageEncoder
 */
package org.opensaml.profile.action;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.messaging.encoder.MessageEncoder;
import org.opensaml.profile.context.ProfileRequestContext;

public interface MessageEncoderFactory {
    @Nullable
    public MessageEncoder getMessageEncoder(@Nonnull ProfileRequestContext var1);
}

