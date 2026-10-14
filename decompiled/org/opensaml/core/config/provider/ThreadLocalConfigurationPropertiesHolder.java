/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.config.provider;

import java.util.Properties;
import javax.annotation.Nonnull;

public final class ThreadLocalConfigurationPropertiesHolder {
    @Nonnull
    private static ThreadLocal<Properties> properties = new ThreadLocal();

    private ThreadLocalConfigurationPropertiesHolder() {
    }

    public static Properties getProperties() {
        return properties.get();
    }

    public static void setProperties(Properties newProperties) {
        properties.set(newProperties);
    }

    public static void clear() {
        properties.remove();
    }
}

