/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.metrics.impl;

import com.codahale.metrics.MetricRegistry;
import javax.annotation.Nonnull;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.Initializer;
import org.opensaml.core.metrics.FilteredMetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetricRegistryInitializer
implements Initializer {
    @Nonnull
    private Logger log = LoggerFactory.getLogger(MetricRegistryInitializer.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void init() throws InitializationException {
        Class<ConfigurationService> clazz = ConfigurationService.class;
        synchronized (ConfigurationService.class) {
            MetricRegistry registry = ConfigurationService.get(MetricRegistry.class);
            if (registry == null) {
                this.log.debug("MetricRegistry did not exist in ConfigurationService, a disabled one will be created");
                registry = new FilteredMetricRegistry();
                ConfigurationService.register(MetricRegistry.class, registry);
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }
}

