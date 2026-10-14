/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.CacheContext;
import org.jooq.Configuration;
import org.jooq.impl.AbstractScope;
import org.jooq.impl.CacheType;

final class DefaultCacheContext
extends AbstractScope
implements CacheContext {
    private final CacheType cacheType;

    DefaultCacheContext(Configuration configuration, CacheType cacheType) {
        super(configuration);
        this.cacheType = cacheType;
    }

    @Override
    public final CacheType cacheType() {
        return this.cacheType;
    }
}

