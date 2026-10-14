/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.Internal;
import io.grpc.ServerCredentials;
import io.grpc.internal.ObjectPool;
import io.grpc.netty.shaded.io.grpc.netty.InternalProtocolNegotiator;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerCredentials;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiators;
import java.util.concurrent.Executor;

@Internal
public final class InternalNettyServerCredentials {
    private InternalNettyServerCredentials() {
    }

    public static ServerCredentials create(InternalProtocolNegotiator.ProtocolNegotiator negotiator) {
        return NettyServerCredentials.create(ProtocolNegotiators.fixedServerFactory(negotiator));
    }

    public static ServerCredentials create(InternalProtocolNegotiator.ServerFactory negotiator) {
        return NettyServerCredentials.create(negotiator);
    }

    public static InternalProtocolNegotiator.ServerFactory toNegotiator(ServerCredentials serverCredentials) {
        ProtocolNegotiators.FromServerCredentialsResult result = ProtocolNegotiators.from(serverCredentials);
        if (result.error != null) {
            throw new IllegalArgumentException(result.error);
        }
        final class ServerFactory
        implements InternalProtocolNegotiator.ServerFactory {
            final /* synthetic */ ProtocolNegotiators.FromServerCredentialsResult val$result;

            ServerFactory(ProtocolNegotiators.FromServerCredentialsResult fromServerCredentialsResult) {
                this.val$result = fromServerCredentialsResult;
            }

            @Override
            public InternalProtocolNegotiator.ProtocolNegotiator newNegotiator(ObjectPool<? extends Executor> offloadExecutorPool) {
                return new InternalProtocolNegotiator.ProtocolNegotiatorAdapter(this.val$result.negotiator.newNegotiator(offloadExecutorPool));
            }
        }
        return new ServerFactory(result);
    }
}

