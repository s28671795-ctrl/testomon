/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.security;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0012"}, d2={"Lio/javalin/security/BasicAuthCredentials;", "", "username", "", "password", "(Ljava/lang/String;Ljava/lang/String;)V", "getPassword", "()Ljava/lang/String;", "getUsername", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "javalin"})
public final class BasicAuthCredentials {
    @NotNull
    private final String username;
    @NotNull
    private final String password;

    public BasicAuthCredentials(@NotNull String username, @NotNull String password) {
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(password, "password");
        this.username = username;
        this.password = password;
    }

    @NotNull
    public final String getUsername() {
        return this.username;
    }

    @NotNull
    public final String getPassword() {
        return this.password;
    }

    @NotNull
    public final String component1() {
        return this.username;
    }

    @NotNull
    public final String component2() {
        return this.password;
    }

    @NotNull
    public final BasicAuthCredentials copy(@NotNull String username, @NotNull String password) {
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(password, "password");
        return new BasicAuthCredentials(username, password);
    }

    public static /* synthetic */ BasicAuthCredentials copy$default(BasicAuthCredentials basicAuthCredentials, String string, String string2, int n, Object object) {
        if ((n & 1) != 0) {
            string = basicAuthCredentials.username;
        }
        if ((n & 2) != 0) {
            string2 = basicAuthCredentials.password;
        }
        return basicAuthCredentials.copy(string, string2);
    }

    @NotNull
    public String toString() {
        return "BasicAuthCredentials(username=" + this.username + ", password=" + this.password + ")";
    }

    public int hashCode() {
        int result = this.username.hashCode();
        result = result * 31 + this.password.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BasicAuthCredentials)) {
            return false;
        }
        BasicAuthCredentials basicAuthCredentials = (BasicAuthCredentials)other;
        if (!Intrinsics.areEqual(this.username, basicAuthCredentials.username)) {
            return false;
        }
        return Intrinsics.areEqual(this.password, basicAuthCredentials.password);
    }
}

