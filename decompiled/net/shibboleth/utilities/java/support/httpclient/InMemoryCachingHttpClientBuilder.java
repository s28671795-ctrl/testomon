/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.http.client.cache.HttpCacheStorage
 *  org.apache.http.client.cache.ResourceFactory
 *  org.apache.http.impl.client.cache.BasicHttpCacheStorage
 *  org.apache.http.impl.client.cache.CacheConfig
 *  org.apache.http.impl.client.cache.CacheConfig$Builder
 *  org.apache.http.impl.client.cache.CachingHttpClientBuilder
 *  org.apache.http.impl.client.cache.HeapResourceFactory
 */
package net.shibboleth.utilities.java.support.httpclient;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.httpclient.HttpClientBuilder;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.apache.http.client.cache.HttpCacheStorage;
import org.apache.http.client.cache.ResourceFactory;
import org.apache.http.impl.client.cache.BasicHttpCacheStorage;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClientBuilder;
import org.apache.http.impl.client.cache.HeapResourceFactory;

public class InMemoryCachingHttpClientBuilder
extends HttpClientBuilder {
    private int maxCacheEntries = 50;
    private long maxCacheEntrySize = 0x100000L;

    public InMemoryCachingHttpClientBuilder() {
        this(CachingHttpClientBuilder.create());
    }

    public InMemoryCachingHttpClientBuilder(@Nonnull CachingHttpClientBuilder builder) {
        super((org.apache.http.impl.client.HttpClientBuilder)builder);
    }

    public int getMaxCacheEntries() {
        return this.maxCacheEntries;
    }

    public void setMaxCacheEntries(int maxEntries) {
        this.maxCacheEntries = (int)Constraint.isGreaterThan(0L, maxEntries, "Maximum number of cache entries must be greater than 0");
    }

    public long getMaxCacheEntrySize() {
        return this.maxCacheEntrySize;
    }

    public void setMaxCacheEntrySize(long size) {
        this.maxCacheEntrySize = Constraint.isGreaterThan(0L, size, "Maximum cache entry size must be greater than 0");
    }

    @Override
    protected void decorateApacheBuilder() throws Exception {
        super.decorateApacheBuilder();
        CachingHttpClientBuilder cachingBuilder = (CachingHttpClientBuilder)this.getApacheBuilder();
        CacheConfig.Builder cacheConfigBuilder = CacheConfig.custom();
        cacheConfigBuilder.setMaxCacheEntries(this.maxCacheEntries);
        cacheConfigBuilder.setMaxObjectSize(this.maxCacheEntrySize);
        cacheConfigBuilder.setHeuristicCachingEnabled(false);
        cacheConfigBuilder.setSharedCache(false);
        CacheConfig cacheConfig = cacheConfigBuilder.build();
        cachingBuilder.setCacheConfig(cacheConfig);
        cachingBuilder.setResourceFactory((ResourceFactory)new HeapResourceFactory());
        cachingBuilder.setHttpCacheStorage((HttpCacheStorage)new BasicHttpCacheStorage(cacheConfig));
    }
}

