/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.config;

import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceLoader;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.config.Configuration;
import org.opensaml.core.config.ConfigurationPropertiesSource;
import org.opensaml.core.config.provider.MapBasedConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationService {
    @Nonnull
    public static final String DEFAULT_PARTITION_NAME = "default";
    @Nonnull
    public static final String PROPERTY_PARTITION_NAME = "opensaml.config.partitionName";
    private static ServiceLoader<ConfigurationPropertiesSource> configPropertiesLoader = ServiceLoader.load(ConfigurationPropertiesSource.class);
    private static Configuration configuration;

    protected ConfigurationService() {
    }

    public static <T> T get(@Nonnull Class<T> configClass) {
        String partitionName = ConfigurationService.getPartitionName();
        return ConfigurationService.getConfiguration().get(configClass, partitionName);
    }

    public static <T, I extends T> void register(@Nonnull Class<T> configClass, @Nonnull I configInstance) {
        String partitionName = ConfigurationService.getPartitionName();
        ConfigurationService.getConfiguration().register(configClass, configInstance, partitionName);
    }

    public static <T> T deregister(@Nonnull Class<T> configClass) {
        String partitionName = ConfigurationService.getPartitionName();
        return ConfigurationService.getConfiguration().deregister(configClass, partitionName);
    }

    @Nullable
    public static Properties getConfigurationProperties() {
        Logger log = ConfigurationService.getLogger();
        log.trace("Resolving configuration propreties source");
        for (ConfigurationPropertiesSource source2 : configPropertiesLoader) {
            log.trace("Evaluating configuration properties implementation: {}", (Object)source2.getClass().getName());
            Properties props = source2.getProperties();
            if (props == null) continue;
            log.trace("Resolved non-null configuration properties using implementation: {}", (Object)source2.getClass().getName());
            return props;
        }
        log.trace("Unable to resolve non-null configuration properties from any ConfigurationPropertiesSource");
        return null;
    }

    public static void setConfiguration(@Nonnull Configuration newConfiguration) {
        configuration = Constraint.isNotNull(newConfiguration, "Configuration cannot be null");
    }

    @Nonnull
    @NotEmpty
    protected static String getPartitionName() {
        Logger log = ConfigurationService.getLogger();
        Properties configProperties = ConfigurationService.getConfigurationProperties();
        String partitionName = null;
        partitionName = configProperties != null ? configProperties.getProperty(PROPERTY_PARTITION_NAME, DEFAULT_PARTITION_NAME) : DEFAULT_PARTITION_NAME;
        log.trace("Resolved effective configuration partition name '{}'", (Object)partitionName);
        return partitionName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nonnull
    protected static Configuration getConfiguration() {
        if (configuration != null) return configuration;
        Class<ConfigurationService> clazz = ConfigurationService.class;
        synchronized (ConfigurationService.class) {
            ServiceLoader<Configuration> loader = ServiceLoader.load(Configuration.class);
            Iterator<Configuration> iter = loader.iterator();
            configuration = iter.hasNext() ? iter.next() : new MapBasedConfiguration();
            // ** MonitorExit[var0] (shouldn't be in output)
            return configuration;
        }
    }

    @Nonnull
    private static Logger getLogger() {
        return LoggerFactory.getLogger(ConfigurationService.class);
    }
}

