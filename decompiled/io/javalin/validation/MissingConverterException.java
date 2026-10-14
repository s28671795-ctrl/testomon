/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.validation;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00060\u0001j\u0002`\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2={"Lio/javalin/validation/MissingConverterException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "className", "", "(Ljava/lang/String;)V", "getClassName", "()Ljava/lang/String;", "javalin"})
public final class MissingConverterException
extends RuntimeException {
    @NotNull
    private final String className;

    public MissingConverterException(@NotNull String className) {
        Intrinsics.checkNotNullParameter(className, "className");
        this.className = className;
    }

    @NotNull
    public final String getClassName() {
        return this.className;
    }
}

