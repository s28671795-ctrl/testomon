/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CacheException
 */
package net.sf.ehcache.hibernate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.hibernate.AbstractEhcacheProvider;
import net.sf.ehcache.hibernate.HibernateUtil;
import net.sf.ehcache.hibernate.management.impl.ProviderMBeanRegistrationHelper;
import org.hibernate.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public final class SingletonEhCacheProvider
extends AbstractEhcacheProvider {
    public static final String NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME = "net.sf.ehcache.configurationResourceName";
    private static final Logger LOG = LoggerFactory.getLogger(SingletonEhCacheProvider.class.getName());
    private static int referenceCount;
    private final ProviderMBeanRegistrationHelper mbeanRegistrationHelper = new ProviderMBeanRegistrationHelper();

    public final void start(Properties properties) throws CacheException {
        String configurationResourceName = null;
        if (properties != null) {
            configurationResourceName = (String)properties.get(NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME);
        }
        if (configurationResourceName == null || configurationResourceName.length() == 0) {
            this.manager = CacheManager.create();
            ++referenceCount;
        } else {
            URL url2;
            try {
                url2 = new URL(configurationResourceName);
            }
            catch (MalformedURLException e) {
                if (!configurationResourceName.startsWith("/")) {
                    configurationResourceName = "/" + configurationResourceName;
                    LOG.debug("prepending / to {}. It should be placed in the rootof the classpath rather than in a package.", (Object)configurationResourceName);
                }
                url2 = this.loadResource(configurationResourceName);
            }
            Configuration configuration = ConfigurationFactory.parseConfiguration(url2);
            this.manager = CacheManager.create(HibernateUtil.overwriteCacheManagerIfConfigured(configuration, properties));
            ++referenceCount;
        }
        this.mbeanRegistrationHelper.registerMBean(this.manager, properties);
    }

    public void stop() {
        if (this.manager != null) {
            if (--referenceCount == 0) {
                this.manager.shutdown();
            }
            this.manager = null;
        }
    }
}

