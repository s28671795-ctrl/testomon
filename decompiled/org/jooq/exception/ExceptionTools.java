/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

import org.jetbrains.annotations.Nullable;

public final class ExceptionTools {
    private static int maxCauseLookups = 256;

    @Nullable
    public static <T extends Throwable> T getCause(Throwable t2, Class<? extends T> type) {
        Throwable next = t2.getCause();
        for (int i = 0; i < maxCauseLookups; ++i) {
            if (next == null) {
                return null;
            }
            if (type.isInstance(next)) {
                return (T)next;
            }
            Throwable prev = next;
            if (prev != (next = next.getCause())) continue;
            return null;
        }
        return null;
    }

    public static void sneakyThrow(Throwable throwable) {
        ExceptionTools.sneakyThrow0(throwable);
    }

    private static <E extends Throwable> void sneakyThrow0(Throwable throwable) throws E {
        throw throwable;
    }

    private ExceptionTools() {
    }
}

