/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.BaseContext
 */
package org.opensaml.saml.common.messaging.context;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.Live;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.saml.ext.saml2cb.ChannelBindings;

public class ChannelBindingsContext
extends BaseContext {
    @Nonnull
    @NonnullElements
    private Collection<ChannelBindings> channelBindings = new ArrayList<ChannelBindings>();

    @Nonnull
    @NonnullElements
    @Live
    public Collection<ChannelBindings> getChannelBindings() {
        return this.channelBindings;
    }
}

