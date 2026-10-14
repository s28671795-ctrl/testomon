/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Scope;
import org.jooq.impl.CacheType;

public interface CacheContext
extends Scope {
    public CacheType cacheType();
}

