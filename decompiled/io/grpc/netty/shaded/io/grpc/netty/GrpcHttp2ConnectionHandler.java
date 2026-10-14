/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.ChannelLogger;
import io.grpc.Internal;
import io.grpc.InternalChannelz;
import io.grpc.netty.shaded.io.grpc.netty.NettyAdaptiveCumulator;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.handler.codec.ByteToMessageDecoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2ConnectionHandler;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Settings;
import io.grpc.netty.shaded.io.netty.util.Version;
import javax.annotation.Nullable;

@Internal
public abstract class GrpcHttp2ConnectionHandler
extends Http2ConnectionHandler {
    static final int ADAPTIVE_CUMULATOR_COMPOSE_MIN_SIZE_DEFAULT = 1024;
    static final ByteToMessageDecoder.Cumulator ADAPTIVE_CUMULATOR = new NettyAdaptiveCumulator(1024);
    @Nullable
    protected final ChannelPromise channelUnused;
    private final ChannelLogger negotiationLogger;
    private static final boolean usingPre4_1_111_Netty;

    protected GrpcHttp2ConnectionHandler(ChannelPromise channelUnused, Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder, Http2Settings initialSettings, ChannelLogger negotiationLogger) {
        super(decoder, encoder, initialSettings);
        this.channelUnused = channelUnused;
        this.negotiationLogger = negotiationLogger;
        if (GrpcHttp2ConnectionHandler.usingPre4_1_111_Netty()) {
            this.setCumulator(ADAPTIVE_CUMULATOR);
        }
    }

    @VisibleForTesting
    static boolean usingPre4_1_111_Netty() {
        return usingPre4_1_111_Netty;
    }

    @Deprecated
    public void handleProtocolNegotiationCompleted(Attributes attrs) {
        this.handleProtocolNegotiationCompleted(attrs, null);
    }

    public void handleProtocolNegotiationCompleted(Attributes attrs, InternalChannelz.Security securityInfo) {
    }

    public ChannelLogger getNegotiationLogger() {
        Preconditions.checkState(this.negotiationLogger != null, "NegotiationLogger must not be null");
        return this.negotiationLogger;
    }

    public void notifyUnused() {
        this.channelUnused.setSuccess(null);
    }

    public Attributes getEagAttributes() {
        return Attributes.EMPTY;
    }

    public String getAuthority() {
        throw new UnsupportedOperationException();
    }

    static {
        boolean identifiedOldVersion = false;
        try {
            String[] split;
            Version version = Version.identify().get("netty-buffer");
            if (version != null && (split = version.artifactVersion().split("\\.")).length >= 3 && Integer.parseInt(split[0]) == 4 && Integer.parseInt(split[1]) <= 1 && Integer.parseInt(split[2]) < 111) {
                identifiedOldVersion = true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        usingPre4_1_111_Netty = identifiedOldVersion;
    }
}

