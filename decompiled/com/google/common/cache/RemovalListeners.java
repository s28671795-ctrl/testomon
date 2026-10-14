/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.cache.ElementTypesAreNonnullByDefault;
import com.google.common.cache.RemovalListener;
import java.util.concurrent.Executor;

@ElementTypesAreNonnullByDefault
@GwtIncompatible
public final class RemovalListeners {
    private RemovalListeners() {
    }

    public static <K, V> RemovalListener<K, V> asynchronous(RemovalListener<K, V> listener, Executor executor2) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(executor2);
        return notification -> executor2.execute(() -> listener.onRemoval(notification));
    }
}

