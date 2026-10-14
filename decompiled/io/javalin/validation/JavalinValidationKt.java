/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.validation;

import io.javalin.validation.BaseValidator;
import io.javalin.validation.JavalinValidation;
import io.javalin.validation.ValidationError;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000 \n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\u001a0\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0014\u0012\u0012\u0012\u000e\u0012\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00050\u00040\u00030\u0001*\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0006\u00a8\u0006\b"}, d2={"collectErrors", "", "", "", "Lio/javalin/validation/ValidationError;", "", "", "Lio/javalin/validation/BaseValidator;", "javalin"})
public final class JavalinValidationKt {
    @NotNull
    public static final Map<String, List<ValidationError<? extends Object>>> collectErrors(@NotNull Iterable<? extends BaseValidator<?>> $this$collectErrors) {
        Intrinsics.checkNotNullParameter($this$collectErrors, "<this>");
        return JavalinValidation.collectErrors($this$collectErrors);
    }
}

