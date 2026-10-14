/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.ChannelCredentials;
import io.grpc.ExperimentalApi;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelCredentials;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiators;

@ExperimentalApi(value="There is no plan to make this API stable, given transport API instability")
public final class InsecureFromHttp1ChannelCredentials {
    private InsecureFromHttp1ChannelCredentials() {
    }

    public static ChannelCredentials create() {
        return NettyChannelCredentials.create(ProtocolNegotiators.plaintextUpgradeClientFactory());
    }
}

