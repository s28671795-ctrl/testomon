/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ElementTypesAreNonnullByDefault;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated=true)
final class Platform {
    static boolean isInstanceOfThrowableClass(@CheckForNull Throwable t2, Class<? extends Throwable> expectedClass) {
        return expectedClass.isInstance(t2);
    }

    static void restoreInterruptIfIsInterruptedException(Throwable t2) {
        Preconditions.checkNotNull(t2);
        if (t2 instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    private Platform() {
    }
}

