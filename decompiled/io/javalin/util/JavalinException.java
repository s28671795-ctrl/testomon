/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\b\u0016\u0018\u00002\u00060\u0001j\u0002`\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bB\u000f\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2={"Lio/javalin/util/JavalinException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "message", "", "(Ljava/lang/String;)V", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "(Ljava/lang/Throwable;)V", "javalin"})
public class JavalinException
extends RuntimeException {
    public JavalinException(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        super(message);
    }

    public JavalinException(@NotNull String message, @NotNull Throwable cause) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(cause, "cause");
        super(message, cause);
    }

    public JavalinException(@NotNull Throwable cause) {
        Intrinsics.checkNotNullParameter(cause, "cause");
        super(cause);
    }
}

