/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.validation;

import io.javalin.validation.ValidationError;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B=\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012 \u0010\u0005\u001a\u001c\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\u0004\u0012\u00020\u00070\u0006j\n\u0012\u0006\u0012\u0004\u0018\u00018\u0000`\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0012\u001a\u00020\u0004H\u00c6\u0003J#\u0010\u0013\u001a\u001c\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\u0004\u0012\u00020\u00070\u0006j\n\u0012\u0006\u0012\u0004\u0018\u00018\u0000`\bH\u00c6\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\nH\u00c6\u0003JM\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\"\b\u0002\u0010\u0005\u001a\u001c\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\u0004\u0012\u00020\u00070\u0006j\n\u0012\u0006\u0012\u0004\u0018\u00018\u0000`\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00072\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u0004H\u00d6\u0001R+\u0010\u0005\u001a\u001c\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\u0004\u0012\u00020\u00070\u0006j\n\u0012\u0006\u0012\u0004\u0018\u00018\u0000`\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001b"}, d2={"Lio/javalin/validation/Rule;", "T", "", "fieldName", "", "check", "Lkotlin/Function1;", "", "Lio/javalin/validation/Check;", "error", "Lio/javalin/validation/ValidationError;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lio/javalin/validation/ValidationError;)V", "getCheck", "()Lkotlin/jvm/functions/Function1;", "getError", "()Lio/javalin/validation/ValidationError;", "getFieldName", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "javalin"})
public final class Rule<T> {
    @NotNull
    private final String fieldName;
    @NotNull
    private final Function1<T, Boolean> check;
    @NotNull
    private final ValidationError<T> error;

    public Rule(@NotNull String fieldName, @NotNull Function1<? super T, Boolean> check2, @NotNull ValidationError<T> error) {
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        Intrinsics.checkNotNullParameter(check2, "check");
        Intrinsics.checkNotNullParameter(error, "error");
        this.fieldName = fieldName;
        this.check = check2;
        this.error = error;
    }

    @NotNull
    public final String getFieldName() {
        return this.fieldName;
    }

    @NotNull
    public final Function1<T, Boolean> getCheck() {
        return this.check;
    }

    @NotNull
    public final ValidationError<T> getError() {
        return this.error;
    }

    @NotNull
    public final String component1() {
        return this.fieldName;
    }

    @NotNull
    public final Function1<T, Boolean> component2() {
        return this.check;
    }

    @NotNull
    public final ValidationError<T> component3() {
        return this.error;
    }

    @NotNull
    public final Rule<T> copy(@NotNull String fieldName, @NotNull Function1<? super T, Boolean> check2, @NotNull ValidationError<T> error) {
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        Intrinsics.checkNotNullParameter(check2, "check");
        Intrinsics.checkNotNullParameter(error, "error");
        return new Rule<T>(fieldName, check2, error);
    }

    public static /* synthetic */ Rule copy$default(Rule rule, String string, Function1 function1, ValidationError validationError, int n, Object object) {
        if ((n & 1) != 0) {
            string = rule.fieldName;
        }
        if ((n & 2) != 0) {
            function1 = rule.check;
        }
        if ((n & 4) != 0) {
            validationError = rule.error;
        }
        return rule.copy(string, function1, validationError);
    }

    @NotNull
    public String toString() {
        return "Rule(fieldName=" + this.fieldName + ", check=" + this.check + ", error=" + this.error + ")";
    }

    public int hashCode() {
        int result = this.fieldName.hashCode();
        result = result * 31 + this.check.hashCode();
        result = result * 31 + this.error.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Rule)) {
            return false;
        }
        Rule rule = (Rule)other;
        if (!Intrinsics.areEqual(this.fieldName, rule.fieldName)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.check, rule.check)) {
            return false;
        }
        return Intrinsics.areEqual(this.error, rule.error);
    }
}

