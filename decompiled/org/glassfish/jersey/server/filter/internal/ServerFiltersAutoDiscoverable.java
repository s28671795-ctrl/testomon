/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.filter.internal;

import javax.annotation.Priority;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;
import org.glassfish.jersey.server.filter.UriConnegFilter;

@ConstrainedTo(value=RuntimeType.SERVER)
@Priority(value=2000)
public final class ServerFiltersAutoDiscoverable
implements AutoDiscoverable {
    @Override
    public void configure(FeatureContext context) {
        Configuration config = context.getConfiguration();
        Object languageMappings = config.getProperty("jersey.config.server.languageMappings");
        Object mediaTypesMappings = config.getProperty("jersey.config.server.mediaTypeMappings");
        if (!(config.isRegistered(UriConnegFilter.class) || languageMappings == null && mediaTypesMappings == null)) {
            context.register(UriConnegFilter.class);
        }
    }
}

