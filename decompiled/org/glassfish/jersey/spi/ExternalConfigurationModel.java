/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.spi;

import java.util.Map;
import java.util.Optional;
import org.glassfish.jersey.ExtendedConfig;

public interface ExternalConfigurationModel<CONFIG>
extends ExtendedConfig {
    public <T> T as(String var1, Class<T> var2);

    public <T> Optional<T> getOptionalProperty(String var1, Class<T> var2);

    public ExternalConfigurationModel mergeProperties(Map<String, Object> var1);

    public CONFIG getConfig();
}

