/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.config.provider;

import java.util.Properties;
import org.opensaml.core.config.ConfigurationPropertiesSource;
import org.opensaml.core.config.provider.ThreadLocalConfigurationPropertiesHolder;

public class ThreadLocalConfigurationPropertiesSource
implements ConfigurationPropertiesSource {
    @Override
    public Properties getProperties() {
        return ThreadLocalConfigurationPropertiesHolder.getProperties();
    }
}

