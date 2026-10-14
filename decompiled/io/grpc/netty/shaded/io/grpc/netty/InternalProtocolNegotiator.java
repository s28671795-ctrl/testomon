/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import io.grpc.Internal;
import io.grpc.internal.ObjectPool;
import io.grpc.netty.shaded.io.grpc.netty.GrpcHttp2ConnectionHandler;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiator;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.util.AsciiString;
import java.util.concurrent.Executor;

@Internal
public final class InternalProtocolNegotiator {
    private InternalProtocolNegotiator() {
    }

    public static interface ServerFactory
    extends ProtocolNegotiator.ServerFactory {
        @Override
        public ProtocolNegotiator newNegotiator(ObjectPool<? extends Executor> var1);
    }

    public static interface ClientFactory
    extends ProtocolNegotiator.ClientFactory {
        @Override
        public ProtocolNegotiator newNegotiator();
    }

    static final class ProtocolNegotiatorAdapter
    implements ProtocolNegotiator {
        private final io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiator negotiator;

        public ProtocolNegotiatorAdapter(io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiator negotiator) {
            this.negotiator = Preconditions.checkNotNull(negotiator, "negotiator");
        }

        @Override
        public AsciiString scheme() {
            return this.negotiator.scheme();
        }

        @Override
        public ChannelHandler newHandler(GrpcHttp2ConnectionHandler grpcHandler) {
            return this.negotiator.newHandler(grpcHandler);
        }

        @Override
        public void close() {
            this.negotiator.close();
        }
    }

    public static interface ProtocolNegotiator
    extends io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiator {
    }
}

