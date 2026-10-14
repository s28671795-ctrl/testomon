/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.spi;

import java.util.Map;
import org.glassfish.jersey.spi.ExternalConfigurationModel;

public interface ExternalConfigurationProvider {
    public Map<String, Object> getProperties();

    public ExternalConfigurationModel getConfiguration();

    public ExternalConfigurationModel merge(ExternalConfigurationModel var1);
}

