/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring;

import javax.annotation.Priority;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.spi.ForcedAutoDiscoverable;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.internal.monitoring.MonitoringFeature;

@ConstrainedTo(value=RuntimeType.SERVER)
@Priority(value=2000)
public final class MonitoringAutodiscoverable
implements ForcedAutoDiscoverable {
    @Override
    public void configure(FeatureContext context) {
        if (!context.getConfiguration().isRegistered(MonitoringFeature.class)) {
            Boolean monitoringEnabled = ServerProperties.getValue(context.getConfiguration().getProperties(), "jersey.config.server.monitoring.enabled", Boolean.FALSE);
            Boolean statisticsEnabled = ServerProperties.getValue(context.getConfiguration().getProperties(), "jersey.config.server.monitoring.statistics.enabled", Boolean.FALSE);
            Boolean mbeansEnabled = ServerProperties.getValue(context.getConfiguration().getProperties(), "jersey.config.server.monitoring.statistics.mbeans.enabled", Boolean.FALSE);
            if (monitoringEnabled.booleanValue() || statisticsEnabled.booleanValue() || mbeansEnabled.booleanValue()) {
                context.register(MonitoringFeature.class);
            }
        }
    }
}

