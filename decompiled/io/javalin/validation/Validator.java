/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.validation;

import io.javalin.util.JavalinLogger;
import io.javalin.validation.BaseValidator;
import io.javalin.validation.JavalinValidation;
import io.javalin.validation.MissingConverterException;
import io.javalin.validation.NullableValidator;
import io.javalin.validation.StringSource;
import io.javalin.validation.ValidationError;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 #*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001#B'\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\bB+\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00018\u0000\u0012\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\fJ\f\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eJ8\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u001c\u0010\u000f\u001a\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00110\u0010j\b\u0012\u0004\u0012\u00028\u0000`\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014J2\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u001c\u0010\u000f\u001a\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00110\u0010j\b\u0012\u0004\u0012\u00028\u0000`\u00122\u0006\u0010\u0013\u001a\u00020\u0004J\r\u0010\u0015\u001a\u00028\u0000H\u0017\u00a2\u0006\u0002\u0010\u0016J\u0013\u0010\u0017\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0019JB\u0010\u001a\u001a\u0004\b\u00028\u00002.\u0010\u001b\u001a*\u0012\u001c\u0012\u001a\u0012\u0004\u0012\u00020\u0004\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u00140\u001d0\u001c\u0012\b\u0012\u00060\u001fj\u0002` 0\u0010H\u0017\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J\u0006\u0010\"\u001a\u00020\u0011\u0082\u0002\u0004\n\u0002\b9\u00a8\u0006$"}, d2={"Lio/javalin/validation/Validator;", "T", "Lio/javalin/validation/BaseValidator;", "stringValue", "", "clazz", "Ljava/lang/Class;", "fieldName", "(Ljava/lang/String;Ljava/lang/Class;Ljava/lang/String;)V", "typedValue", "stringSource", "Lio/javalin/validation/StringSource;", "(Ljava/lang/String;Ljava/lang/Object;Lio/javalin/validation/StringSource;)V", "allowNullable", "Lio/javalin/validation/NullableValidator;", "check", "Lkotlin/Function1;", "", "Lio/javalin/validation/Check;", "error", "Lio/javalin/validation/ValidationError;", "get", "()Ljava/lang/Object;", "getOrDefault", "default", "(Ljava/lang/Object;)Ljava/lang/Object;", "getOrThrow", "exceptionFunction", "", "", "", "Ljava/lang/Exception;", "Lkotlin/Exception;", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "hasValue", "Companion", "javalin"})
public class Validator<T>
extends BaseValidator<T> {
    @NotNull
    public static final Companion Companion = new Companion(null);

    public Validator(@NotNull String fieldName, @Nullable T typedValue, @Nullable StringSource<T> stringSource) {
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        super(fieldName, typedValue, stringSource);
    }

    public /* synthetic */ Validator(String string, Object object, StringSource stringSource, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            object = null;
        }
        if ((n & 4) != 0) {
            stringSource = null;
        }
        this(string, object, stringSource);
    }

    public Validator(@Nullable String stringValue, @NotNull Class<T> clazz, @NotNull String fieldName) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        this(fieldName, null, new StringSource(stringValue, clazz, null, 4, null));
    }

    @NotNull
    public final NullableValidator<T> allowNullable() {
        if (this.getRules$javalin().isEmpty()) {
            return new NullableValidator(this.getFieldName(), this.getTypedValue(), this.getStringSource());
        }
        throw new IllegalStateException("Validator#allowNullable must be called before adding rules");
    }

    @NotNull
    public final Validator<T> check(@NotNull Function1<? super T, Boolean> check2, @NotNull String error) {
        Intrinsics.checkNotNullParameter(check2, "check");
        Intrinsics.checkNotNullParameter(error, "error");
        BaseValidator baseValidator = this.addRule(this.getFieldName(), (Function1)new Function1<T, Boolean>(check2){
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
        Intrinsics.checkNotNull(baseValidator, "null cannot be cast to non-null type io.javalin.validation.Validator<T of io.javalin.validation.Validator>");
        return (Validator)baseValidator;
    }

    @NotNull
    public final Validator<T> check(@NotNull Function1<? super T, Boolean> check2, @NotNull ValidationError<T> error) {
        Intrinsics.checkNotNullParameter(check2, "check");
        Intrinsics.checkNotNullParameter(error, "error");
        BaseValidator<T> baseValidator = this.addRule(this.getFieldName(), (Function1)new Function1<T, Boolean>(check2){
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
        Intrinsics.checkNotNull(baseValidator, "null cannot be cast to non-null type io.javalin.validation.Validator<T of io.javalin.validation.Validator>");
        return (Validator)baseValidator;
    }

    public final boolean hasValue() {
        StringSource stringSource = this.getStringSource();
        return (stringSource != null ? stringSource.getStringValue() : null) != null || this.getTypedValue() != null;
    }

    @Override
    @NotNull
    public T get() {
        Object t2 = super.get();
        Intrinsics.checkNotNull(t2);
        return t2;
    }

    public final T getOrDefault(T t2) {
        T t3;
        if (this.hasValue()) {
            Object t4 = super.get();
            t3 = t4;
            Intrinsics.checkNotNull(t4);
        } else {
            t3 = t2;
        }
        return t3;
    }

    @Override
    @NotNull
    public T getOrThrow(@NotNull Function1<? super Map<String, ? extends List<ValidationError<Object>>>, ? extends Exception> exceptionFunction) {
        Intrinsics.checkNotNullParameter(exceptionFunction, "exceptionFunction");
        Object t2 = super.getOrThrow(exceptionFunction);
        Intrinsics.checkNotNull(t2);
        return t2;
    }

    @JvmStatic
    @NotNull
    public static final <T> Validator<T> create(@NotNull Class<T> clazz, @Nullable String value, @NotNull String fieldName) {
        return Companion.create(clazz, value, fieldName);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0001\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00072\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\tH\u0007\u00a8\u0006\u000b"}, d2={"Lio/javalin/validation/Validator$Companion;", "", "()V", "create", "Lio/javalin/validation/Validator;", "T", "clazz", "Ljava/lang/Class;", "value", "", "fieldName", "javalin"})
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @NotNull
        public final <T> Validator<T> create(@NotNull Class<T> clazz, @Nullable String value, @NotNull String fieldName) {
            Intrinsics.checkNotNullParameter(clazz, "clazz");
            Intrinsics.checkNotNullParameter(fieldName, "fieldName");
            if (!JavalinValidation.INSTANCE.hasConverter(clazz)) {
                JavalinLogger.info$default("Can't convert to " + clazz.getName() + ". Register a converter using JavalinValidation#register.", null, 2, null);
                String string = clazz.getName();
                Intrinsics.checkNotNullExpressionValue(string, "clazz.name");
                throw new MissingConverterException(string);
            }
            return new Validator<T>(value, clazz, fieldName);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

