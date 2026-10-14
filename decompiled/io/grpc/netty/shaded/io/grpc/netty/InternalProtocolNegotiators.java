/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.ChannelLogger;
import io.grpc.netty.shaded.io.grpc.netty.GrpcHttp2ConnectionHandler;
import io.grpc.netty.shaded.io.grpc.netty.InternalProtocolNegotiator;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiator;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiators;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.grpc.netty.shaded.io.netty.util.AsciiString;

public final class InternalProtocolNegotiators {
    private InternalProtocolNegotiators() {
    }

    public static InternalProtocolNegotiator.ProtocolNegotiator tls(SslContext sslContext) {
        ProtocolNegotiator negotiator = ProtocolNegotiators.tls(sslContext);
        final class TlsNegotiator
        implements InternalProtocolNegotiator.ProtocolNegotiator {
            final /* synthetic */ ProtocolNegotiator val$negotiator;

            TlsNegotiator(ProtocolNegotiator protocolNegotiator) {
                this.val$negotiator = protocolNegotiator;
            }

            @Override
            public AsciiString scheme() {
                return this.val$negotiator.scheme();
            }

            @Override
            public ChannelHandler newHandler(GrpcHttp2ConnectionHandler grpcHandler) {
                return this.val$negotiator.newHandler(grpcHandler);
            }

            @Override
            public void close() {
                this.val$negotiator.close();
            }
        }
        return new TlsNegotiator(negotiator);
    }

    public static InternalProtocolNegotiator.ProtocolNegotiator serverTls(SslContext sslContext) {
        ProtocolNegotiator negotiator = ProtocolNegotiators.serverTls(sslContext);
        final class ServerTlsNegotiator
        implements InternalProtocolNegotiator.ProtocolNegotiator {
            final /* synthetic */ ProtocolNegotiator val$negotiator;

            ServerTlsNegotiator(ProtocolNegotiator protocolNegotiator) {
                this.val$negotiator = protocolNegotiator;
            }

            @Override
            public AsciiString scheme() {
                return this.val$negotiator.scheme();
            }

            @Override
            public ChannelHandler newHandler(GrpcHttp2ConnectionHandler grpcHandler) {
                return this.val$negotiator.newHandler(grpcHandler);
            }

            @Override
            public void close() {
                this.val$negotiator.close();
            }
        }
        return new ServerTlsNegotiator(negotiator);
    }

    public static InternalProtocolNegotiator.ProtocolNegotiator plaintext() {
        ProtocolNegotiator negotiator = ProtocolNegotiators.plaintext();
        final class PlaintextNegotiator
        implements InternalProtocolNegotiator.ProtocolNegotiator {
            final /* synthetic */ ProtocolNegotiator val$negotiator;

            PlaintextNegotiator(ProtocolNegotiator protocolNegotiator) {
                this.val$negotiator = protocolNegotiator;
            }

            @Override
            public AsciiString scheme() {
                return this.val$negotiator.scheme();
            }

            @Override
            public ChannelHandler newHandler(GrpcHttp2ConnectionHandler grpcHandler) {
                return this.val$negotiator.newHandler(grpcHandler);
            }

            @Override
            public void close() {
                this.val$negotiator.close();
            }
        }
        return new PlaintextNegotiator(negotiator);
    }

    public static InternalProtocolNegotiator.ProtocolNegotiator serverPlaintext() {
        ProtocolNegotiator negotiator = ProtocolNegotiators.serverPlaintext();
        final class ServerPlaintextNegotiator
        implements InternalProtocolNegotiator.ProtocolNegotiator {
            final /* synthetic */ ProtocolNegotiator val$negotiator;

            ServerPlaintextNegotiator(ProtocolNegotiator protocolNegotiator) {
                this.val$negotiator = protocolNegotiator;
            }

            @Override
            public AsciiString scheme() {
                return this.val$negotiator.scheme();
            }

            @Override
            public ChannelHandler newHandler(GrpcHttp2ConnectionHandler grpcHandler) {
                return this.val$negotiator.newHandler(grpcHandler);
            }

            @Override
            public void close() {
                this.val$negotiator.close();
            }
        }
        return new ServerPlaintextNegotiator(negotiator);
    }

    public static ChannelHandler waitUntilActiveHandler(ChannelHandler next, ChannelLogger negotiationLogger) {
        return new ProtocolNegotiators.WaitUntilActiveHandler(next, negotiationLogger);
    }

    public static ChannelHandler grpcNegotiationHandler(GrpcHttp2ConnectionHandler next) {
        return new ProtocolNegotiators.GrpcNegotiationHandler(next);
    }

    public static ChannelHandler clientTlsHandler(ChannelHandler next, SslContext sslContext, String authority, ChannelLogger negotiationLogger) {
        return new ProtocolNegotiators.ClientTlsHandler(next, sslContext, authority, null, negotiationLogger);
    }

    public static class ProtocolNegotiationHandler
    extends ProtocolNegotiators.ProtocolNegotiationHandler {
        protected ProtocolNegotiationHandler(ChannelHandler next, String negotiatorName, ChannelLogger negotiationLogger) {
            super(next, negotiatorName, negotiationLogger);
        }

        protected ProtocolNegotiationHandler(ChannelHandler next, ChannelLogger negotiationLogger) {
            super(next, negotiationLogger);
        }
    }
}

