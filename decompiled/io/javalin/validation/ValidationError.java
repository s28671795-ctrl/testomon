/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.validation;

import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B1\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0016\b\u0002\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0011\u001a\u00020\u0004H\u00c6\u0003J\u0017\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006H\u00c6\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0002H\u00c6\u0003J=\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\u0016\b\u0002\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0002H\u00c6\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u0004H\u00d6\u0001R\u001f\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0002X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001b"}, d2={"Lio/javalin/validation/ValidationError;", "T", "", "message", "", "args", "", "value", "(Ljava/lang/String;Ljava/util/Map;Ljava/lang/Object;)V", "getArgs", "()Ljava/util/Map;", "getMessage", "()Ljava/lang/String;", "getValue", "()Ljava/lang/Object;", "setValue", "(Ljava/lang/Object;)V", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "javalin"})
public final class ValidationError<T> {
    @NotNull
    private final String message;
    @NotNull
    private final Map<String, Object> args;
    @Nullable
    private Object value;

    public ValidationError(@NotNull String message, @NotNull Map<String, ? extends Object> args2, @Nullable Object value) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(args2, "args");
        this.message = message;
        this.args = args2;
        this.value = value;
    }

    public /* synthetic */ ValidationError(String string, Map map, Object object, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            map = MapsKt.emptyMap();
        }
        if ((n & 4) != 0) {
            object = null;
        }
        this(string, map, object);
    }

    @NotNull
    public final String getMessage() {
        return this.message;
    }

    @NotNull
    public final Map<String, Object> getArgs() {
        return this.args;
    }

    @Nullable
    public final Object getValue() {
        return this.value;
    }

    public final void setValue(@Nullable Object object) {
        this.value = object;
    }

    @NotNull
    public final String component1() {
        return this.message;
    }

    @NotNull
    public final Map<String, Object> component2() {
        return this.args;
    }

    @Nullable
    public final Object component3() {
        return this.value;
    }

    @NotNull
    public final ValidationError<T> copy(@NotNull String message, @NotNull Map<String, ? extends Object> args2, @Nullable Object value) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(args2, "args");
        return new ValidationError<T>(message, args2, value);
    }

    public static /* synthetic */ ValidationError copy$default(ValidationError validationError, String string, Map map, Object object, int n, Object object2) {
        if ((n & 1) != 0) {
            string = validationError.message;
        }
        if ((n & 2) != 0) {
            map = validationError.args;
        }
        if ((n & 4) != 0) {
            object = validationError.value;
        }
        return validationError.copy(string, map, object);
    }

    @NotNull
    public String toString() {
        return "ValidationError(message=" + this.message + ", args=" + this.args + ", value=" + this.value + ")";
    }

    public int hashCode() {
        int result = this.message.hashCode();
        result = result * 31 + ((Object)this.args).hashCode();
        result = result * 31 + (this.value == null ? 0 : this.value.hashCode());
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ValidationError)) {
            return false;
        }
        ValidationError validationError = (ValidationError)other;
        if (!Intrinsics.areEqual(this.message, validationError.message)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.args, validationError.args)) {
            return false;
        }
        return Intrinsics.areEqual(this.value, validationError.value);
    }
}

