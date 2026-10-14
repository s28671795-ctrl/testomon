/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Map;
import org.jetbrains.annotations.Nullable;
import org.jooq.CacheContext;

@FunctionalInterface
public interface CacheProvider {
    @Nullable
    public Map<Object, Object> provide(CacheContext var1);
}

