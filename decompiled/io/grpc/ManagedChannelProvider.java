/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.ChannelCredentials;
import io.grpc.Internal;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ManagedChannelRegistry;
import java.net.SocketAddress;
import java.util.Collection;

@Internal
public abstract class ManagedChannelProvider {
    public static ManagedChannelProvider provider() {
        ManagedChannelProvider provider = ManagedChannelRegistry.getDefaultRegistry().provider();
        if (provider == null) {
            throw new ProviderNotFoundException("No functional channel service provider found. Try adding a dependency on the grpc-okhttp, grpc-netty, or grpc-netty-shaded artifact");
        }
        return provider;
    }

    protected abstract boolean isAvailable();

    protected abstract int priority();

    protected abstract ManagedChannelBuilder<?> builderForAddress(String var1, int var2);

    protected abstract ManagedChannelBuilder<?> builderForTarget(String var1);

    protected NewChannelBuilderResult newChannelBuilder(String target, ChannelCredentials creds) {
        return NewChannelBuilderResult.error("ChannelCredentials are unsupported");
    }

    protected abstract Collection<Class<? extends SocketAddress>> getSupportedSocketAddressTypes();

    public static final class ProviderNotFoundException
    extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public ProviderNotFoundException(String msg) {
            super(msg);
        }
    }

    public static final class NewChannelBuilderResult {
        private final ManagedChannelBuilder<?> channelBuilder;
        private final String error;

        private NewChannelBuilderResult(ManagedChannelBuilder<?> channelBuilder, String error) {
            this.channelBuilder = channelBuilder;
            this.error = error;
        }

        public static NewChannelBuilderResult channelBuilder(ManagedChannelBuilder<?> builder) {
            return new NewChannelBuilderResult(Preconditions.checkNotNull(builder), null);
        }

        public static NewChannelBuilderResult error(String error) {
            return new NewChannelBuilderResult(null, Preconditions.checkNotNull(error));
        }

        public ManagedChannelBuilder<?> getChannelBuilder() {
            return this.channelBuilder;
        }

        public String getError() {
            return this.error;
        }
    }
}

