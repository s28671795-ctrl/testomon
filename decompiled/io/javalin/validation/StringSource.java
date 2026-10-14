/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.validation;

import io.javalin.json.JsonMapper;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B)\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\tJ\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006H\u00c6\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\bH\u00c6\u0003J7\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bH\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u0004H\u00d6\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001a"}, d2={"Lio/javalin/validation/StringSource;", "T", "", "stringValue", "", "clazz", "Ljava/lang/Class;", "jsonMapper", "Lio/javalin/json/JsonMapper;", "(Ljava/lang/String;Ljava/lang/Class;Lio/javalin/json/JsonMapper;)V", "getClazz", "()Ljava/lang/Class;", "getJsonMapper", "()Lio/javalin/json/JsonMapper;", "getStringValue", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "javalin"})
public final class StringSource<T> {
    @Nullable
    private final String stringValue;
    @NotNull
    private final Class<T> clazz;
    @Nullable
    private final JsonMapper jsonMapper;

    public StringSource(@Nullable String stringValue, @NotNull Class<T> clazz, @Nullable JsonMapper jsonMapper) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        this.stringValue = stringValue;
        this.clazz = clazz;
        this.jsonMapper = jsonMapper;
    }

    public /* synthetic */ StringSource(String string, Class clazz, JsonMapper jsonMapper, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            jsonMapper = null;
        }
        this(string, clazz, jsonMapper);
    }

    @Nullable
    public final String getStringValue() {
        return this.stringValue;
    }

    @NotNull
    public final Class<T> getClazz() {
        return this.clazz;
    }

    @Nullable
    public final JsonMapper getJsonMapper() {
        return this.jsonMapper;
    }

    @Nullable
    public final String component1() {
        return this.stringValue;
    }

    @NotNull
    public final Class<T> component2() {
        return this.clazz;
    }

    @Nullable
    public final JsonMapper component3() {
        return this.jsonMapper;
    }

    @NotNull
    public final StringSource<T> copy(@Nullable String stringValue, @NotNull Class<T> clazz, @Nullable JsonMapper jsonMapper) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return new StringSource<T>(stringValue, clazz, jsonMapper);
    }

    public static /* synthetic */ StringSource copy$default(StringSource stringSource, String string, Class clazz, JsonMapper jsonMapper, int n, Object object) {
        if ((n & 1) != 0) {
            string = stringSource.stringValue;
        }
        if ((n & 2) != 0) {
            clazz = stringSource.clazz;
        }
        if ((n & 4) != 0) {
            jsonMapper = stringSource.jsonMapper;
        }
        return stringSource.copy(string, clazz, jsonMapper);
    }

    @NotNull
    public String toString() {
        return "StringSource(stringValue=" + this.stringValue + ", clazz=" + this.clazz + ", jsonMapper=" + this.jsonMapper + ")";
    }

    public int hashCode() {
        int result = this.stringValue == null ? 0 : this.stringValue.hashCode();
        result = result * 31 + this.clazz.hashCode();
        result = result * 31 + (this.jsonMapper == null ? 0 : this.jsonMapper.hashCode());
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StringSource)) {
            return false;
        }
        StringSource stringSource = (StringSource)other;
        if (!Intrinsics.areEqual(this.stringValue, stringSource.stringValue)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.clazz, stringSource.clazz)) {
            return false;
        }
        return Intrinsics.areEqual(this.jsonMapper, stringSource.jsonMapper);
    }
}

