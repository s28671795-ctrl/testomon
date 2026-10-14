/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.validation;

import io.javalin.json.JsonMapper;
import io.javalin.validation.BaseValidator;
import io.javalin.validation.ValidationError;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B%\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ8\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u001c\u0010\n\u001a\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00028\u0000`\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fJ2\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u001c\u0010\n\u001a\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00028\u0000`\r2\u0006\u0010\u000e\u001a\u00020\u0004J@\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0010\u001a\u00020\u00042\u001c\u0010\n\u001a\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00028\u0000`\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fJ:\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0010\u001a\u00020\u00042\u001c\u0010\n\u001a\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00028\u0000`\r2\u0006\u0010\u000e\u001a\u00020\u0004J\r\u0010\u0011\u001a\u00028\u0000H\u0017\u00a2\u0006\u0002\u0010\u0012J=\u0010\u0013\u001a\u00028\u00002.\u0010\u0014\u001a*\u0012\u001c\u0012\u001a\u0012\u0004\u0012\u00020\u0004\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u000f0\u00160\u0015\u0012\b\u0012\u00060\u0018j\u0002`\u00190\u000bH\u0017\u00a2\u0006\u0002\u0010\u001a\u00a8\u0006\u001b"}, d2={"Lio/javalin/validation/BodyValidator;", "T", "Lio/javalin/validation/BaseValidator;", "stringValue", "", "clazz", "Ljava/lang/Class;", "jsonMapper", "Lio/javalin/json/JsonMapper;", "(Ljava/lang/String;Ljava/lang/Class;Lio/javalin/json/JsonMapper;)V", "check", "Lkotlin/Function1;", "", "Lio/javalin/validation/Check;", "error", "Lio/javalin/validation/ValidationError;", "fieldName", "get", "()Ljava/lang/Object;", "getOrThrow", "exceptionFunction", "", "", "", "Ljava/lang/Exception;", "Lkotlin/Exception;", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "javalin"})
public class BodyValidator<T>
extends BaseValidator<T> {
    public BodyValidator(@Nullable String stringValue, @NotNull Class<T> clazz, @NotNull JsonMapper jsonMapper) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(jsonMapper, "jsonMapper");
        super(stringValue, clazz, "REQUEST_BODY", jsonMapper);
    }

    @NotNull
    public final BodyValidator<T> check(@NotNull Function1<? super T, Boolean> check2, @NotNull String error) {
        Intrinsics.checkNotNullParameter(check2, "check");
        Intrinsics.checkNotNullParameter(error, "error");
        return this.check("REQUEST_BODY", check2, error);
    }

    @NotNull
    public final BodyValidator<T> check(@NotNull Function1<? super T, Boolean> check2, @NotNull ValidationError<T> error) {
        Intrinsics.checkNotNullParameter(check2, "check");
        Intrinsics.checkNotNullParameter(error, "error");
        return this.check("REQUEST_BODY", check2, error);
    }

    @NotNull
    public final BodyValidator<T> check(@NotNull String fieldName, @NotNull Function1<? super T, Boolean> check2, @NotNull String error) {
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        Intrinsics.checkNotNullParameter(check2, "check");
        Intrinsics.checkNotNullParameter(error, "error");
        BaseValidator baseValidator = this.addRule(fieldName, (Function1)new Function1<T, Boolean>(check2){
            final /* synthetic */ Function1<T, Boolean> $check;
            {
                this.$check = $check;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@Nullable T it) {
                T t2 = it;
                Intrinsics.checkNotNull(t2);
                return this.$check.invoke(t2);
            }
        }, error);
        Intrinsics.checkNotNull(baseValidator, "null cannot be cast to non-null type io.javalin.validation.BodyValidator<T of io.javalin.validation.BodyValidator>");
        return (BodyValidator)baseValidator;
    }

    @NotNull
    public final BodyValidator<T> check(@NotNull String fieldName, @NotNull Function1<? super T, Boolean> check2, @NotNull ValidationError<T> error) {
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        Intrinsics.checkNotNullParameter(check2, "check");
        Intrinsics.checkNotNullParameter(error, "error");
        BaseValidator<T> baseValidator = this.addRule(fieldName, (Function1)new Function1<T, Boolean>(check2){
            final /* synthetic */ Function1<T, Boolean> $check;
            {
                this.$check = $check;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@Nullable T it) {
                T t2 = it;
                Intrinsics.checkNotNull(t2);
                return this.$check.invoke(t2);
            }
        }, error);
        Intrinsics.checkNotNull(baseValidator, "null cannot be cast to non-null type io.javalin.validation.BodyValidator<T of io.javalin.validation.BodyValidator>");
        return (BodyValidator)baseValidator;
    }

    @Override
    @NotNull
    public T get() {
        Object t2 = super.get();
        Intrinsics.checkNotNull(t2);
        return t2;
    }

    @Override
    @NotNull
    public T getOrThrow(@NotNull Function1<? super Map<String, ? extends List<ValidationError<Object>>>, ? extends Exception> exceptionFunction) {
        Intrinsics.checkNotNullParameter(exceptionFunction, "exceptionFunction");
        Object t2 = super.getOrThrow(exceptionFunction);
        Intrinsics.checkNotNull(t2);
        return t2;
    }
}

