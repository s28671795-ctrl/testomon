/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.util.concurrent.ElementTypesAreNonnullByDefault;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

@ElementTypesAreNonnullByDefault
@GwtCompatible
final class LazyLogger {
    private final String loggerName;
    private volatile @Nullable Logger logger;

    LazyLogger(Class<?> ownerOfLogger) {
        this.loggerName = ownerOfLogger.getName();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Logger get() {
        Logger local = this.logger;
        if (local != null) {
            return local;
        }
        LazyLogger lazyLogger = this;
        synchronized (lazyLogger) {
            local = this.logger;
            if (local != null) {
                return local;
            }
            this.logger = Logger.getLogger(this.loggerName);
            return this.logger;
        }
    }
}

