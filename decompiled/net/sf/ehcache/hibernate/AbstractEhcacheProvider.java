/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.Cache
 *  org.hibernate.cache.CacheException
 *  org.hibernate.cache.CacheProvider
 */
package net.sf.ehcache.hibernate;

import java.net.URL;
import java.util.Properties;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.hibernate.EhCache;
import net.sf.ehcache.util.Timestamper;
import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractEhcacheProvider
implements CacheProvider {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractEhcacheProvider.class.getName());
    protected volatile CacheManager manager;

    AbstractEhcacheProvider() {
    }

    public final Cache buildCache(String name, Properties properties) throws org.hibernate.cache.CacheException {
        try {
            Ehcache cache = this.manager.getEhcache(name);
            if (cache == null) {
                LOG.warn("Could not find a specific ehcache configuration for cache named [" + name + "]; using defaults.");
                this.manager.addCache(name);
                cache = this.manager.getEhcache(name);
                LOG.debug("started EHCache region: " + name);
            }
            return new EhCache(cache);
        }
        catch (CacheException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public final long nextTimestamp() {
        return Timestamper.next();
    }

    public final boolean isMinimalPutsEnabledByDefault() {
        return true;
    }

    protected URL loadResource(String configurationResourceName) {
        ClassLoader standardClassloader = Thread.currentThread().getContextClassLoader();
        URL url2 = null;
        if (standardClassloader != null) {
            url2 = standardClassloader.getResource(configurationResourceName);
        }
        if (url2 == null) {
            url2 = this.getClass().getResource(configurationResourceName);
        }
        LOG.debug("Creating EhCacheProvider from a specified resource: {}. Resolved to URL: ", (Object)configurationResourceName, (Object)url2);
        if (url2 == null) {
            LOG.warn("A configurationResourceName was set to {} but the resource could not be loaded from the classpath.Ehcache will configure itself using defaults.", (Object)configurationResourceName);
        }
        return url2;
    }
}

