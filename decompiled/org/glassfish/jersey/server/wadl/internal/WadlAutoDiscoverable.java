/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal;

import javax.annotation.Priority;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.spi.ForcedAutoDiscoverable;
import org.glassfish.jersey.server.wadl.WadlFeature;

@ConstrainedTo(value=RuntimeType.SERVER)
@Priority(value=2000)
public final class WadlAutoDiscoverable
implements ForcedAutoDiscoverable {
    @Override
    public void configure(FeatureContext context) {
        if (!context.getConfiguration().isRegistered(WadlFeature.class)) {
            context.register(WadlFeature.class);
        }
    }
}

