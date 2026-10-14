/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0080\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0006H\u00c6\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0006H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t\u00a8\u0006\u0016"}, d2={"Lio/javalin/plugin/bundled/OriginParts;", "", "scheme", "", "host", "port", "", "(Ljava/lang/String;Ljava/lang/String;I)V", "getHost", "()Ljava/lang/String;", "getPort", "()I", "getScheme", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "javalin"})
public final class OriginParts {
    @NotNull
    private final String scheme;
    @NotNull
    private final String host;
    private final int port;

    public OriginParts(@NotNull String scheme2, @NotNull String host2, int port) {
        Intrinsics.checkNotNullParameter(scheme2, "scheme");
        Intrinsics.checkNotNullParameter(host2, "host");
        this.scheme = scheme2;
        this.host = host2;
        this.port = port;
    }

    @NotNull
    public final String getScheme() {
        return this.scheme;
    }

    @NotNull
    public final String getHost() {
        return this.host;
    }

    public final int getPort() {
        return this.port;
    }

    @NotNull
    public final String component1() {
        return this.scheme;
    }

    @NotNull
    public final String component2() {
        return this.host;
    }

    public final int component3() {
        return this.port;
    }

    @NotNull
    public final OriginParts copy(@NotNull String scheme2, @NotNull String host2, int port) {
        Intrinsics.checkNotNullParameter(scheme2, "scheme");
        Intrinsics.checkNotNullParameter(host2, "host");
        return new OriginParts(scheme2, host2, port);
    }

    public static /* synthetic */ OriginParts copy$default(OriginParts originParts, String string, String string2, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            string = originParts.scheme;
        }
        if ((n2 & 2) != 0) {
            string2 = originParts.host;
        }
        if ((n2 & 4) != 0) {
            n = originParts.port;
        }
        return originParts.copy(string, string2, n);
    }

    @NotNull
    public String toString() {
        return "OriginParts(scheme=" + this.scheme + ", host=" + this.host + ", port=" + this.port + ")";
    }

    public int hashCode() {
        int result = this.scheme.hashCode();
        result = result * 31 + this.host.hashCode();
        result = result * 31 + Integer.hashCode(this.port);
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OriginParts)) {
            return false;
        }
        OriginParts originParts = (OriginParts)other;
        if (!Intrinsics.areEqual(this.scheme, originParts.scheme)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.host, originParts.host)) {
            return false;
        }
        return this.port == originParts.port;
    }
}

