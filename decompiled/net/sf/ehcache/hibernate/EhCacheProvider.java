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
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.hibernate.AbstractEhcacheProvider;
import net.sf.ehcache.hibernate.HibernateUtil;
import net.sf.ehcache.hibernate.management.impl.ProviderMBeanRegistrationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public final class EhCacheProvider
extends AbstractEhcacheProvider {
    public static final String NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME = "net.sf.ehcache.configurationResourceName";
    private static final Logger LOG = LoggerFactory.getLogger(EhCacheProvider.class.getName());
    private final ProviderMBeanRegistrationHelper mbeanRegistrationHelper = new ProviderMBeanRegistrationHelper();

    public final void start(Properties properties) throws org.hibernate.cache.CacheException {
        if (this.manager != null) {
            LOG.warn("Attempt to restart an already started EhCacheProvider. Use sessionFactory.close()  between repeated calls to buildSessionFactory. Using previously created EhCacheProvider. If this behaviour is required, consider using SingletonEhCacheProvider.");
            return;
        }
        try {
            String configurationResourceName = null;
            if (properties != null) {
                configurationResourceName = (String)properties.get(NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME);
            }
            if (configurationResourceName == null || configurationResourceName.length() == 0) {
                this.manager = new CacheManager();
            } else {
                URL url2;
                try {
                    url2 = new URL(configurationResourceName);
                }
                catch (MalformedURLException e) {
                    url2 = this.loadResource(configurationResourceName);
                }
                this.manager = new CacheManager(HibernateUtil.overwriteCacheManagerIfConfigured(HibernateUtil.loadAndCorrectConfiguration(url2), properties));
            }
            this.mbeanRegistrationHelper.registerMBean(this.manager, properties);
        }
        catch (CacheException e) {
            if (e.getMessage().startsWith("Cannot parseConfiguration CacheManager. Attempt to create a new instance of CacheManager using the diskStorePath")) {
                throw new org.hibernate.cache.CacheException("Attempt to restart an already started EhCacheProvider. Use sessionFactory.close()  between repeated calls to buildSessionFactory. Consider using SingletonEhCacheProvider. Error from  ehcache was: " + e.getMessage());
            }
            throw e;
        }
    }

    public final void stop() {
        if (this.manager != null) {
            this.manager.shutdown();
            this.manager = null;
        }
    }
}

