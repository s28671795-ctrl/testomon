/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import io.grpc.ProxiedSocketAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import javax.annotation.Nullable;

public final class HttpConnectProxiedSocketAddress
extends ProxiedSocketAddress {
    private static final long serialVersionUID = 0L;
    private final SocketAddress proxyAddress;
    private final InetSocketAddress targetAddress;
    @Nullable
    private final String username;
    @Nullable
    private final String password;

    private HttpConnectProxiedSocketAddress(SocketAddress proxyAddress, InetSocketAddress targetAddress, @Nullable String username, @Nullable String password) {
        Preconditions.checkNotNull(proxyAddress, "proxyAddress");
        Preconditions.checkNotNull(targetAddress, "targetAddress");
        if (proxyAddress instanceof InetSocketAddress) {
            Preconditions.checkState(!((InetSocketAddress)proxyAddress).isUnresolved(), "The proxy address %s is not resolved", (Object)proxyAddress);
        }
        this.proxyAddress = proxyAddress;
        this.targetAddress = targetAddress;
        this.username = username;
        this.password = password;
    }

    @Nullable
    public String getPassword() {
        return this.password;
    }

    @Nullable
    public String getUsername() {
        return this.username;
    }

    public SocketAddress getProxyAddress() {
        return this.proxyAddress;
    }

    public InetSocketAddress getTargetAddress() {
        return this.targetAddress;
    }

    public boolean equals(Object o) {
        if (!(o instanceof HttpConnectProxiedSocketAddress)) {
            return false;
        }
        HttpConnectProxiedSocketAddress that = (HttpConnectProxiedSocketAddress)o;
        return Objects.equal(this.proxyAddress, that.proxyAddress) && Objects.equal(this.targetAddress, that.targetAddress) && Objects.equal(this.username, that.username) && Objects.equal(this.password, that.password);
    }

    public int hashCode() {
        return Objects.hashCode(this.proxyAddress, this.targetAddress, this.username, this.password);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("proxyAddr", this.proxyAddress).add("targetAddr", this.targetAddress).add("username", this.username).add("hasPassword", this.password != null).toString();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private SocketAddress proxyAddress;
        private InetSocketAddress targetAddress;
        @Nullable
        private String username;
        @Nullable
        private String password;

        private Builder() {
        }

        public Builder setProxyAddress(SocketAddress proxyAddress) {
            this.proxyAddress = Preconditions.checkNotNull(proxyAddress, "proxyAddress");
            return this;
        }

        public Builder setTargetAddress(InetSocketAddress targetAddress) {
            this.targetAddress = Preconditions.checkNotNull(targetAddress, "targetAddress");
            return this;
        }

        public Builder setUsername(@Nullable String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(@Nullable String password) {
            this.password = password;
            return this;
        }

        public HttpConnectProxiedSocketAddress build() {
            return new HttpConnectProxiedSocketAddress(this.proxyAddress, this.targetAddress, this.username, this.password);
        }
    }
}

