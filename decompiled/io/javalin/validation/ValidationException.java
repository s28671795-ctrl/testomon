/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.validation;

import io.javalin.validation.ValidationError;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u00002\u00060\u0001j\u0002`\u0002B%\u0012\u001e\u0010\u0003\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00060\u0004\u00a2\u0006\u0002\u0010\tR)\u0010\u0003\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00060\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2={"Lio/javalin/validation/ValidationException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "errors", "", "", "", "Lio/javalin/validation/ValidationError;", "", "(Ljava/util/Map;)V", "getErrors", "()Ljava/util/Map;", "javalin"})
public final class ValidationException
extends RuntimeException {
    @NotNull
    private final Map<String, List<ValidationError<Object>>> errors;

    public ValidationException(@NotNull Map<String, ? extends List<ValidationError<Object>>> errors2) {
        Intrinsics.checkNotNullParameter(errors2, "errors");
        this.errors = errors2;
    }

    @NotNull
    public final Map<String, List<ValidationError<Object>>> getErrors() {
        return this.errors;
    }
}

