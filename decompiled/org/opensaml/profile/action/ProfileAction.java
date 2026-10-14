/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.profile.action;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import org.opensaml.profile.context.ProfileRequestContext;

public interface ProfileAction<InboundMessageType, OutboundMessageType>
extends InitializableComponent {
    public void execute(@Nonnull ProfileRequestContext<InboundMessageType, OutboundMessageType> var1);
}

