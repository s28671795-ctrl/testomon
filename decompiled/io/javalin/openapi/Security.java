/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u00c6\u0003J#\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001J\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0003R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0016"}, d2={"Lio/javalin/openapi/Security;", "", "name", "", "scopes", "", "(Ljava/lang/String;Ljava/util/List;)V", "getName", "()Ljava/lang/String;", "getScopes", "()Ljava/util/List;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "withScope", "scope", "openapi-specification"})
public final class Security {
    @NotNull
    private final String name;
    @NotNull
    private final List<String> scopes;

    @JvmOverloads
    public Security(@NotNull String name, @NotNull List<String> scopes) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        this.name = name;
        this.scopes = scopes;
    }

    public /* synthetic */ Security(String string, List list, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            list = new ArrayList();
        }
        this(string, list);
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final List<String> getScopes() {
        return this.scopes;
    }

    @NotNull
    public final Security withScope(@NotNull String scope) {
        Security security;
        Intrinsics.checkNotNullParameter(scope, "scope");
        Security it = security = this;
        boolean bl = false;
        this.scopes.add(scope);
        return security;
    }

    @NotNull
    public final String component1() {
        return this.name;
    }

    @NotNull
    public final List<String> component2() {
        return this.scopes;
    }

    @NotNull
    public final Security copy(@NotNull String name, @NotNull List<String> scopes) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        return new Security(name, scopes);
    }

    public static /* synthetic */ Security copy$default(Security security, String string, List list, int n, Object object) {
        if ((n & 1) != 0) {
            string = security.name;
        }
        if ((n & 2) != 0) {
            list = security.scopes;
        }
        return security.copy(string, list);
    }

    @NotNull
    public String toString() {
        return "Security(name=" + this.name + ", scopes=" + this.scopes + ")";
    }

    public int hashCode() {
        int result = this.name.hashCode();
        result = result * 31 + ((Object)this.scopes).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Security)) {
            return false;
        }
        Security security = (Security)other;
        if (!Intrinsics.areEqual(this.name, security.name)) {
            return false;
        }
        return Intrinsics.areEqual(this.scopes, security.scopes);
    }

    @JvmOverloads
    public Security(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        this(name, null, 2, null);
    }
}

