/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import io.javalin.util.JavalinException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lio/javalin/util/JavalinBindException;", "Lio/javalin/util/JavalinException;", "message", "", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "javalin"})
public final class JavalinBindException
extends JavalinException {
    public JavalinBindException(@NotNull String message, @NotNull Throwable cause) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(cause, "cause");
        super(message, cause);
    }
}

