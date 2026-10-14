/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.compression;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public final class Zstd {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Zstd.class);
    private static final Throwable cause;

    public static boolean isAvailable() {
        return cause == null;
    }

    public static void ensureAvailability() throws Throwable {
        if (cause != null) {
            throw cause;
        }
    }

    public static Throwable cause() {
        return cause;
    }

    private Zstd() {
    }

    static {
        Throwable t2 = null;
        try {
            Class.forName("com.github.luben.zstd.Zstd", false, PlatformDependent.getClassLoader(Zstd.class));
        }
        catch (ClassNotFoundException e) {
            t2 = e;
            logger.debug("zstd-jni not in the classpath; Zstd support will be unavailable.");
        }
        catch (Throwable e) {
            t2 = e;
            logger.debug("Failed to load zstd-jni; Zstd support will be unavailable.", t2);
        }
        cause = t2;
    }
}

