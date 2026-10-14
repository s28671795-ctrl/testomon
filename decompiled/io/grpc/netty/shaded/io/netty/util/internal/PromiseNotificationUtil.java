/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util.internal;

import io.grpc.netty.shaded.io.netty.util.concurrent.Promise;
import io.grpc.netty.shaded.io.netty.util.internal.ThrowableUtil;
import io.grpc.netty.shaded.io.netty.util.internal.logging.InternalLogger;

public final class PromiseNotificationUtil {
    private PromiseNotificationUtil() {
    }

    public static void tryCancel(Promise<?> p, InternalLogger logger2) {
        if (!p.cancel(false) && logger2 != null) {
            Throwable err = p.cause();
            if (err == null) {
                logger2.warn("Failed to cancel promise because it has succeeded already: {}", (Object)p);
            } else {
                logger2.warn("Failed to cancel promise because it has failed already: {}, unnotified cause:", (Object)p, (Object)err);
            }
        }
    }

    public static <V> void trySuccess(Promise<? super V> p, V result, InternalLogger logger2) {
        if (!p.trySuccess(result) && logger2 != null) {
            Throwable err = p.cause();
            if (err == null) {
                logger2.warn("Failed to mark a promise as success because it has succeeded already: {}", (Object)p);
            } else {
                logger2.warn("Failed to mark a promise as success because it has failed already: {}, unnotified cause:", (Object)p, (Object)err);
            }
        }
    }

    public static void tryFailure(Promise<?> p, Throwable cause, InternalLogger logger2) {
        if (!p.tryFailure(cause) && logger2 != null) {
            Throwable err = p.cause();
            if (err == null) {
                logger2.warn("Failed to mark a promise as failure because it has succeeded already: {}", (Object)p, (Object)cause);
            } else if (logger2.isWarnEnabled()) {
                logger2.warn("Failed to mark a promise as failure because it has failed already: {}, unnotified cause: {}", p, ThrowableUtil.stackTraceToString(err), cause);
            }
        }
    }
}

