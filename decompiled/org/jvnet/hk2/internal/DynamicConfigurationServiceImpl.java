/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.Populator;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.internal.DynamicConfigurationImpl;
import org.jvnet.hk2.internal.PopulatorImpl;
import org.jvnet.hk2.internal.ServiceLocatorImpl;

@Singleton
public class DynamicConfigurationServiceImpl
implements DynamicConfigurationService {
    private final ServiceLocatorImpl locator;
    private final Populator populator;

    @Inject
    private DynamicConfigurationServiceImpl(ServiceLocator locator) {
        this.locator = (ServiceLocatorImpl)locator;
        this.populator = new PopulatorImpl(locator, this);
    }

    @Override
    public DynamicConfiguration createDynamicConfiguration() {
        return new DynamicConfigurationImpl(this.locator);
    }

    @Override
    public Populator getPopulator() {
        return this.populator;
    }
}

