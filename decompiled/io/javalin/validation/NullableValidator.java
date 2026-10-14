/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.validation;

import io.javalin.validation.BaseValidator;
import io.javalin.validation.StringSource;
import io.javalin.validation.ValidationError;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\bB+\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00018\u0000\u0012\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\fJ<\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002 \u0010\r\u001a\u001c\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\u0004\u0012\u00020\u000f0\u000ej\n\u0012\u0006\u0012\u0004\u0018\u00018\u0000`\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u0012J6\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002 \u0010\r\u001a\u001c\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\u0004\u0012\u00020\u000f0\u000ej\n\u0012\u0006\u0012\u0004\u0018\u00018\u0000`\u00102\u0006\u0010\u0011\u001a\u00020\u0004\u00a8\u0006\u0013"}, d2={"Lio/javalin/validation/NullableValidator;", "T", "Lio/javalin/validation/BaseValidator;", "stringValue", "", "clazz", "Ljava/lang/Class;", "fieldName", "(Ljava/lang/String;Ljava/lang/Class;Ljava/lang/String;)V", "typedValue", "stringSource", "Lio/javalin/validation/StringSource;", "(Ljava/lang/String;Ljava/lang/Object;Lio/javalin/validation/StringSource;)V", "check", "Lkotlin/Function1;", "", "Lio/javalin/validation/Check;", "error", "Lio/javalin/validation/ValidationError;", "javalin"})
public class NullableValidator<T>
extends BaseValidator<T> {
    public NullableValidator(@NotNull String fieldName, @Nullable T typedValue, @Nullable StringSource<T> stringSource) {
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        super(fieldName, typedValue, stringSource);
    }

    public /* synthetic */ NullableValidator(String string, Object object, StringSource stringSource, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            object = null;
        }
        if ((n & 4) != 0) {
            stringSource = null;
        }
        this(string, object, stringSource);
    }

    public NullableValidator(@Nullable String stringValue, @NotNull Class<T> clazz, @NotNull String fieldName) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        this(fieldName, null, new StringSource(stringValue, clazz, null, 4, null));
    }

    @NotNull
    public final NullableValidator<T> check(@NotNull Function1<? super T, Boolean> check2, @NotNull String error) {
        Intrinsics.checkNotNullParameter(check2, "check");
        Intrinsics.checkNotNullParameter(error, "error");
        BaseValidator<? super T> baseValidator = this.addRule(this.getFieldName(), check2, error);
        Intrinsics.checkNotNull(baseValidator, "null cannot be cast to non-null type io.javalin.validation.NullableValidator<T of io.javalin.validation.NullableValidator>");
        return (NullableValidator)baseValidator;
    }

    @NotNull
    public final NullableValidator<T> check(@NotNull Function1<? super T, Boolean> check2, @NotNull ValidationError<T> error) {
        Intrinsics.checkNotNullParameter(check2, "check");
        Intrinsics.checkNotNullParameter(error, "error");
        BaseValidator<? super T> baseValidator = this.addRule(this.getFieldName(), check2, error);
        Intrinsics.checkNotNull(baseValidator, "null cannot be cast to non-null type io.javalin.validation.NullableValidator<T of io.javalin.validation.NullableValidator>");
        return (NullableValidator)baseValidator;
    }
}

