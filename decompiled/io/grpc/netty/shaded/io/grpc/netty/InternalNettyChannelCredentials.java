/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.ChannelCredentials;
import io.grpc.Internal;
import io.grpc.netty.shaded.io.grpc.netty.InternalProtocolNegotiator;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelCredentials;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiators;

@Internal
public final class InternalNettyChannelCredentials {
    private InternalNettyChannelCredentials() {
    }

    public static ChannelCredentials create(InternalProtocolNegotiator.ClientFactory negotiator) {
        return NettyChannelCredentials.create(negotiator);
    }

    public static InternalProtocolNegotiator.ClientFactory toNegotiator(ChannelCredentials channelCredentials) {
        ProtocolNegotiators.FromChannelCredentialsResult result = ProtocolNegotiators.from(channelCredentials);
        if (result.error != null) {
            throw new IllegalArgumentException(result.error);
        }
        final class ClientFactory
        implements InternalProtocolNegotiator.ClientFactory {
            final /* synthetic */ ProtocolNegotiators.FromChannelCredentialsResult val$result;

            ClientFactory(ProtocolNegotiators.FromChannelCredentialsResult fromChannelCredentialsResult) {
                this.val$result = fromChannelCredentialsResult;
            }

            @Override
            public InternalProtocolNegotiator.ProtocolNegotiator newNegotiator() {
                return new InternalProtocolNegotiator.ProtocolNegotiatorAdapter(this.val$result.negotiator.newNegotiator());
            }

            @Override
            public int getDefaultPort() {
                return this.val$result.negotiator.getDefaultPort();
            }
        }
        return new ClientFactory(result);
    }
}

