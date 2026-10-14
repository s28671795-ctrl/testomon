/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CacheDataDescription
 *  org.hibernate.cache.CacheException
 *  org.hibernate.cache.CollectionRegion
 *  org.hibernate.cache.EntityRegion
 *  org.hibernate.cache.QueryResultsRegion
 *  org.hibernate.cache.RegionFactory
 *  org.hibernate.cache.TimestampsRegion
 *  org.hibernate.cache.access.AccessType
 *  org.hibernate.cfg.Settings
 */
package net.sf.ehcache.hibernate;

import java.net.URL;
import java.util.Properties;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.hibernate.management.impl.ProviderMBeanRegistrationHelper;
import net.sf.ehcache.hibernate.nonstop.NonstopAccessStrategyFactory;
import net.sf.ehcache.hibernate.regions.EhcacheCollectionRegion;
import net.sf.ehcache.hibernate.regions.EhcacheEntityRegion;
import net.sf.ehcache.hibernate.regions.EhcacheQueryResultsRegion;
import net.sf.ehcache.hibernate.regions.EhcacheTimestampsRegion;
import net.sf.ehcache.hibernate.strategy.EhcacheAccessStrategyFactory;
import net.sf.ehcache.hibernate.strategy.EhcacheAccessStrategyFactoryImpl;
import net.sf.ehcache.util.Timestamper;
import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.QueryResultsRegion;
import org.hibernate.cache.RegionFactory;
import org.hibernate.cache.TimestampsRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractEhcacheRegionFactory
implements RegionFactory {
    public static final String NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME = "net.sf.ehcache.configurationResourceName";
    public static final String NET_SF_EHCACHE_CACHE_MANAGER_NAME = "net.sf.ehcache.cacheManagerName";
    private static final Logger LOG = LoggerFactory.getLogger(AbstractEhcacheRegionFactory.class);
    protected final ProviderMBeanRegistrationHelper mbeanRegistrationHelper = new ProviderMBeanRegistrationHelper();
    protected volatile CacheManager manager;
    protected Settings settings;
    protected final EhcacheAccessStrategyFactory accessStrategyFactory = new NonstopAccessStrategyFactory(new EhcacheAccessStrategyFactoryImpl());

    AbstractEhcacheRegionFactory() {
    }

    public boolean isMinimalPutsEnabledByDefault() {
        return true;
    }

    public long nextTimestamp() {
        return Timestamper.next();
    }

    public EntityRegion buildEntityRegion(String regionName, Properties properties, CacheDataDescription metadata) throws org.hibernate.cache.CacheException {
        return new EhcacheEntityRegion(this.accessStrategyFactory, this.getCache(regionName), this.settings, metadata, properties);
    }

    public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription metadata) throws org.hibernate.cache.CacheException {
        return new EhcacheCollectionRegion(this.accessStrategyFactory, this.getCache(regionName), this.settings, metadata, properties);
    }

    public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws org.hibernate.cache.CacheException {
        return new EhcacheQueryResultsRegion(this.accessStrategyFactory, this.getCache(regionName), properties);
    }

    public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties) throws org.hibernate.cache.CacheException {
        return new EhcacheTimestampsRegion(this.accessStrategyFactory, this.getCache(regionName), properties);
    }

    private Ehcache getCache(String name) throws org.hibernate.cache.CacheException {
        try {
            Ehcache cache = this.manager.getEhcache(name);
            if (cache == null) {
                LOG.warn("Couldn't find a specific ehcache configuration for cache named [" + name + "]; using defaults.");
                this.manager.addCache(name);
                cache = this.manager.getEhcache(name);
                LOG.debug("started EHCache region: " + name);
            }
            return cache;
        }
        catch (CacheException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    protected static URL loadResource(String configurationResourceName) {
        ClassLoader standardClassloader = Thread.currentThread().getContextClassLoader();
        URL url2 = null;
        if (standardClassloader != null) {
            url2 = standardClassloader.getResource(configurationResourceName);
        }
        if (url2 == null) {
            url2 = AbstractEhcacheRegionFactory.class.getResource(configurationResourceName);
        }
        LOG.debug("Creating EhCacheRegionFactory from a specified resource: {}.  Resolved to URL: {}", (Object)configurationResourceName, (Object)url2);
        if (url2 == null) {
            LOG.warn("A configurationResourceName was set to {} but the resource could not be loaded from the classpath.Ehcache will configure itself using defaults.", (Object)configurationResourceName);
        }
        return url2;
    }

    public AccessType getDefaultAccessType() {
        return AccessType.READ_WRITE;
    }
}

