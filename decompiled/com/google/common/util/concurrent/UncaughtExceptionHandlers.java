/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.util.concurrent.ElementTypesAreNonnullByDefault;
import com.google.common.util.concurrent.LazyLogger;
import java.util.Locale;
import java.util.logging.Level;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
public final class UncaughtExceptionHandlers {
    private UncaughtExceptionHandlers() {
    }

    public static Thread.UncaughtExceptionHandler systemExit() {
        return new Exiter(Runtime.getRuntime());
    }

    @VisibleForTesting
    static final class Exiter
    implements Thread.UncaughtExceptionHandler {
        private static final LazyLogger logger = new LazyLogger(Exiter.class);
        private final Runtime runtime;

        Exiter(Runtime runtime) {
            this.runtime = runtime;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void uncaughtException(Thread t2, Throwable e) {
            try {
                logger.get().log(Level.SEVERE, String.format(Locale.ROOT, "Caught an exception in %s.  Shutting down.", t2), e);
            }
            catch (Throwable errorInLogging) {
                System.err.println(e.getMessage());
                System.err.println(errorInLogging.getMessage());
            }
            finally {
                this.runtime.exit(1);
            }
        }
    }
}

