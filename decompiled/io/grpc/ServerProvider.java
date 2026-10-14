/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.Internal;
import io.grpc.ManagedChannelProvider;
import io.grpc.ServerBuilder;
import io.grpc.ServerCredentials;
import io.grpc.ServerRegistry;

@Internal
public abstract class ServerProvider {
    public static ServerProvider provider() {
        ServerProvider provider = ServerRegistry.getDefaultRegistry().provider();
        if (provider == null) {
            throw new ManagedChannelProvider.ProviderNotFoundException("No functional server found. Try adding a dependency on the grpc-netty or grpc-netty-shaded artifact");
        }
        return provider;
    }

    protected abstract boolean isAvailable();

    protected abstract int priority();

    protected abstract ServerBuilder<?> builderForPort(int var1);

    protected NewServerBuilderResult newServerBuilderForPort(int port, ServerCredentials creds) {
        return NewServerBuilderResult.error("ServerCredentials are unsupported");
    }

    public static final class NewServerBuilderResult {
        private final ServerBuilder<?> serverBuilder;
        private final String error;

        private NewServerBuilderResult(ServerBuilder<?> serverBuilder, String error) {
            this.serverBuilder = serverBuilder;
            this.error = error;
        }

        public static NewServerBuilderResult serverBuilder(ServerBuilder<?> builder) {
            return new NewServerBuilderResult(Preconditions.checkNotNull(builder), null);
        }

        public static NewServerBuilderResult error(String error) {
            return new NewServerBuilderResult(null, Preconditions.checkNotNull(error));
        }

        public ServerBuilder<?> getServerBuilder() {
            return this.serverBuilder;
        }

        public String getError() {
            return this.error;
        }
    }
}

