/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.store;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.pac4j.core.store.AbstractStore;
import org.pac4j.core.util.CommonHelper;

public class GuavaStore<K, O>
extends AbstractStore<K, O> {
    private Cache<K, O> cache;
    private int size = 0;
    private int timeout = -1;
    private TimeUnit timeUnit;

    public GuavaStore() {
    }

    public GuavaStore(int size, int timeout2, TimeUnit timeUnit) {
        this.size = size;
        this.timeout = timeout2;
        this.timeUnit = timeUnit;
    }

    @Override
    protected void internalInit(boolean forceReinit) {
        CommonHelper.assertTrue(this.size > 0, "size mut be greater than zero");
        CommonHelper.assertTrue(this.timeout >= 0, "timeout must be greater than zero");
        CommonHelper.assertNotNull("timeUnit", (Object)this.timeUnit);
        this.cache = CacheBuilder.newBuilder().maximumSize(this.size).expireAfterWrite(this.timeout, this.timeUnit).build();
    }

    @Override
    protected Optional<O> internalGet(K key) {
        return Optional.ofNullable(this.cache.getIfPresent(key));
    }

    @Override
    protected void internalSet(K key, O value) {
        this.cache.put(key, value);
    }

    @Override
    protected void internalRemove(K key) {
        this.cache.invalidate(key);
    }

    public Cache<K, O> getCache() {
        return this.cache;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout2) {
        this.timeout = timeout2;
    }

    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), new Object[]{"size", this.size, "timeout", this.timeout, "timeUnit", this.timeUnit});
    }
}

