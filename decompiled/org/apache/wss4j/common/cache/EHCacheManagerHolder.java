/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.cache;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import org.apache.wss4j.common.util.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EHCacheManagerHolder {
    private static final Logger LOG = LoggerFactory.getLogger(EHCacheManagerHolder.class);
    private static final ConcurrentHashMap<String, AtomicInteger> COUNTS = new ConcurrentHashMap(8, 0.75f, 2);
    private static Method cacheManagerCreateMethodNoArg;
    private static Method createMethodURLArg;
    private static Method cacheManagerCreateMethodConfigurationArg;

    private EHCacheManagerHolder() {
    }

    public static CacheConfiguration getCacheConfiguration(String key, CacheManager cacheManager) {
        CacheConfiguration cc = cacheManager.getConfiguration().getCacheConfigurations().get(key);
        if (cc == null && key.contains("-")) {
            cc = cacheManager.getConfiguration().getCacheConfigurations().get(key.substring(0, key.lastIndexOf(45)));
        }
        if (cc == null) {
            cc = cacheManager.getConfiguration().getDefaultCacheConfiguration();
        }
        cc = cc == null ? new CacheConfiguration() : cc.clone();
        cc.setName(key);
        return cc;
    }

    public static synchronized CacheManager getCacheManager(String confName, URL configFileURL) {
        AtomicInteger a;
        CacheManager cacheManager = null;
        if (configFileURL == null) {
            cacheManager = EHCacheManagerHolder.findDefaultCacheManager(confName);
        }
        if (cacheManager == null) {
            cacheManager = configFileURL == null ? EHCacheManagerHolder.createCacheManager() : EHCacheManagerHolder.findDefaultCacheManager(confName, configFileURL);
        }
        if ((a = COUNTS.get(cacheManager.getName())) == null) {
            COUNTS.putIfAbsent(cacheManager.getName(), new AtomicInteger());
            a = COUNTS.get(cacheManager.getName());
        }
        a.incrementAndGet();
        return cacheManager;
    }

    private static CacheManager findDefaultCacheManager(String confName) {
        String defaultConfigFile = "/wss4j-ehcache.xml";
        URL configFileURL = null;
        try {
            configFileURL = Loader.getResource(defaultConfigFile);
            if (configFileURL == null) {
                configFileURL = new URL(defaultConfigFile);
            }
        }
        catch (IOException e) {
            LOG.debug(e.getMessage());
        }
        return EHCacheManagerHolder.findDefaultCacheManager(confName, configFileURL);
    }

    private static CacheManager findDefaultCacheManager(String confName, URL configFileURL) {
        try {
            Configuration conf = ConfigurationFactory.parseConfiguration(configFileURL);
            conf.setName(confName);
            if ("java.io.tmpdir".equals(conf.getDiskStoreConfiguration().getOriginalPath())) {
                String path = conf.getDiskStoreConfiguration().getPath() + File.separator + confName;
                conf.getDiskStoreConfiguration().setPath(path);
            }
            return EHCacheManagerHolder.createCacheManager(conf);
        }
        catch (Throwable t2) {
            return null;
        }
    }

    public static synchronized void releaseCacheManger(CacheManager cacheManager) {
        AtomicInteger a = COUNTS.get(cacheManager.getName());
        if (a == null) {
            return;
        }
        if (a.decrementAndGet() == 0) {
            cacheManager.shutdown();
        }
    }

    static CacheManager createCacheManager() throws CacheException {
        try {
            return (CacheManager)cacheManagerCreateMethodNoArg.invoke(null, (Object[])null);
        }
        catch (Exception e) {
            throw new CacheException(e);
        }
    }

    static CacheManager createCacheManager(URL url2) throws CacheException {
        try {
            return (CacheManager)createMethodURLArg.invoke(null, url2);
        }
        catch (Exception e) {
            throw new CacheException(e);
        }
    }

    static CacheManager createCacheManager(Configuration conf) throws CacheException {
        try {
            return (CacheManager)cacheManagerCreateMethodConfigurationArg.invoke(null, conf);
        }
        catch (Exception e) {
            throw new CacheException(e);
        }
    }

    static {
        try {
            cacheManagerCreateMethodNoArg = CacheManager.class.getMethod("newInstance", null);
            createMethodURLArg = CacheManager.class.getMethod("newInstance", URL.class);
            cacheManagerCreateMethodConfigurationArg = CacheManager.class.getMethod("newInstance", Configuration.class);
        }
        catch (NoSuchMethodException e) {
            try {
                cacheManagerCreateMethodNoArg = CacheManager.class.getMethod("create", null);
                createMethodURLArg = CacheManager.class.getMethod("create", URL.class);
                cacheManagerCreateMethodConfigurationArg = CacheManager.class.getMethod("create", Configuration.class);
            }
            catch (Throwable t2) {
                LOG.warn(t2.getMessage());
            }
        }
    }
}

