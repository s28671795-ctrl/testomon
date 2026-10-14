/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.hibernate;

import java.net.URL;
import java.util.Properties;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.NonstopConfiguration;
import net.sf.ehcache.config.TimeoutBehaviorConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HibernateUtil {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateUtil.class);

    private HibernateUtil() {
    }

    static Configuration loadAndCorrectConfiguration(URL url2) {
        Configuration config = ConfigurationFactory.parseConfiguration(url2);
        if (config.getDefaultCacheConfiguration() != null && config.getDefaultCacheConfiguration().isTerracottaClustered()) {
            HibernateUtil.setupHibernateTimeoutBehavior(config.getDefaultCacheConfiguration().getTerracottaConfiguration().getNonstopConfiguration());
        }
        for (CacheConfiguration cacheConfig : config.getCacheConfigurations().values()) {
            if (!cacheConfig.isTerracottaClustered()) continue;
            HibernateUtil.setupHibernateTimeoutBehavior(cacheConfig.getTerracottaConfiguration().getNonstopConfiguration());
        }
        return config;
    }

    private static void setupHibernateTimeoutBehavior(NonstopConfiguration nonstopConfig) {
        nonstopConfig.getTimeoutBehavior().setType(TimeoutBehaviorConfiguration.TimeoutBehaviorType.EXCEPTION.getTypeName());
    }

    static Configuration overwriteCacheManagerIfConfigured(Configuration configuration, Properties properties) {
        String cacheManagerName = properties.getProperty("net.sf.ehcache.cacheManagerName");
        if (cacheManagerName != null) {
            configuration.setName(cacheManagerName);
        }
        return configuration;
    }
}

