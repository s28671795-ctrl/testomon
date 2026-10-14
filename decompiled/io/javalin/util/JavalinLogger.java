/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import io.javalin.Javalin;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u0007J\u001c\u0010\u000e\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u0007J\u001c\u0010\u000f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u0007J\u0010\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u001c\u0010\u0011\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u0007R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lio/javalin/util/JavalinLogger;", "", "()V", "enabled", "", "log", "Lorg/slf4j/Logger;", "startupInfo", "debug", "", "message", "", "throwable", "", "error", "info", "startup", "warn", "javalin"})
public final class JavalinLogger {
    @NotNull
    public static final JavalinLogger INSTANCE = new JavalinLogger();
    @NotNull
    private static final Logger log;
    @JvmField
    public static boolean enabled;
    @JvmField
    public static boolean startupInfo;

    private JavalinLogger() {
    }

    @JvmStatic
    public static final void startup(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        if (!enabled) {
            return;
        }
        if (startupInfo) {
            log.info(message);
        }
    }

    @JvmStatic
    @JvmOverloads
    public static final void info(@NotNull String message, @Nullable Throwable throwable) {
        Intrinsics.checkNotNullParameter(message, "message");
        if (!enabled) {
            return;
        }
        if (throwable != null) {
            log.info(message, throwable);
        } else {
            log.info(message);
        }
    }

    public static /* synthetic */ void info$default(String string, Throwable throwable, int n, Object object) {
        if ((n & 2) != 0) {
            throwable = null;
        }
        JavalinLogger.info(string, throwable);
    }

    @JvmStatic
    @JvmOverloads
    public static final void warn(@NotNull String message, @Nullable Throwable throwable) {
        Intrinsics.checkNotNullParameter(message, "message");
        if (!enabled) {
            return;
        }
        if (throwable != null) {
            log.warn(message, throwable);
        } else {
            log.warn(message);
        }
    }

    public static /* synthetic */ void warn$default(String string, Throwable throwable, int n, Object object) {
        if ((n & 2) != 0) {
            throwable = null;
        }
        JavalinLogger.warn(string, throwable);
    }

    @JvmStatic
    @JvmOverloads
    public static final void error(@NotNull String message, @Nullable Throwable throwable) {
        Intrinsics.checkNotNullParameter(message, "message");
        if (!enabled) {
            return;
        }
        if (throwable != null) {
            log.error(message, throwable);
        } else {
            log.error(message);
        }
    }

    public static /* synthetic */ void error$default(String string, Throwable throwable, int n, Object object) {
        if ((n & 2) != 0) {
            throwable = null;
        }
        JavalinLogger.error(string, throwable);
    }

    @JvmStatic
    @JvmOverloads
    public static final void debug(@NotNull String message, @Nullable Throwable throwable) {
        Intrinsics.checkNotNullParameter(message, "message");
        if (!enabled) {
            return;
        }
        if (throwable != null) {
            log.debug(message, throwable);
        } else {
            log.debug(message);
        }
    }

    public static /* synthetic */ void debug$default(String string, Throwable throwable, int n, Object object) {
        if ((n & 2) != 0) {
            throwable = null;
        }
        JavalinLogger.debug(string, throwable);
    }

    @JvmStatic
    @JvmOverloads
    public static final void info(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        JavalinLogger.info$default(message, null, 2, null);
    }

    @JvmStatic
    @JvmOverloads
    public static final void warn(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        JavalinLogger.warn$default(message, null, 2, null);
    }

    @JvmStatic
    @JvmOverloads
    public static final void error(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        JavalinLogger.error$default(message, null, 2, null);
    }

    @JvmStatic
    @JvmOverloads
    public static final void debug(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        JavalinLogger.debug$default(message, null, 2, null);
    }

    static {
        Logger logger2 = LoggerFactory.getLogger(Javalin.class);
        Intrinsics.checkNotNull(logger2);
        log = logger2;
        enabled = true;
        startupInfo = true;
    }
}

