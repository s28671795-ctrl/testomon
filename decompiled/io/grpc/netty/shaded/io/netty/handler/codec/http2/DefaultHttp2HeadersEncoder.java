/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http2;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.Unpooled;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.HpackEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Error;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Exception;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Headers;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2HeadersEncoder;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;

public class DefaultHttp2HeadersEncoder
implements Http2HeadersEncoder,
Http2HeadersEncoder.Configuration {
    private final HpackEncoder hpackEncoder;
    private final Http2HeadersEncoder.SensitivityDetector sensitivityDetector;
    private ByteBuf tableSizeChangeOutput;

    public DefaultHttp2HeadersEncoder() {
        this(NEVER_SENSITIVE);
    }

    public DefaultHttp2HeadersEncoder(Http2HeadersEncoder.SensitivityDetector sensitivityDetector) {
        this(sensitivityDetector, new HpackEncoder());
    }

    public DefaultHttp2HeadersEncoder(Http2HeadersEncoder.SensitivityDetector sensitivityDetector, boolean ignoreMaxHeaderListSize) {
        this(sensitivityDetector, new HpackEncoder(ignoreMaxHeaderListSize));
    }

    public DefaultHttp2HeadersEncoder(Http2HeadersEncoder.SensitivityDetector sensitivityDetector, boolean ignoreMaxHeaderListSize, int dynamicTableArraySizeHint) {
        this(sensitivityDetector, ignoreMaxHeaderListSize, dynamicTableArraySizeHint, 512);
    }

    public DefaultHttp2HeadersEncoder(Http2HeadersEncoder.SensitivityDetector sensitivityDetector, boolean ignoreMaxHeaderListSize, int dynamicTableArraySizeHint, int huffCodeThreshold) {
        this(sensitivityDetector, new HpackEncoder(ignoreMaxHeaderListSize, dynamicTableArraySizeHint, huffCodeThreshold));
    }

    DefaultHttp2HeadersEncoder(Http2HeadersEncoder.SensitivityDetector sensitivityDetector, HpackEncoder hpackEncoder) {
        this.sensitivityDetector = ObjectUtil.checkNotNull(sensitivityDetector, "sensitiveDetector");
        this.hpackEncoder = ObjectUtil.checkNotNull(hpackEncoder, "hpackEncoder");
    }

    @Override
    public void encodeHeaders(int streamId, Http2Headers headers, ByteBuf buffer) throws Http2Exception {
        try {
            if (this.tableSizeChangeOutput != null && this.tableSizeChangeOutput.isReadable()) {
                buffer.writeBytes(this.tableSizeChangeOutput);
                this.tableSizeChangeOutput.clear();
            }
            this.hpackEncoder.encodeHeaders(streamId, buffer, headers, this.sensitivityDetector);
        }
        catch (Http2Exception e) {
            throw e;
        }
        catch (Throwable t2) {
            throw Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, t2, "Failed encoding headers block: %s", t2.getMessage());
        }
    }

    @Override
    public void maxHeaderTableSize(long max) throws Http2Exception {
        if (this.tableSizeChangeOutput == null) {
            this.tableSizeChangeOutput = Unpooled.buffer();
        }
        this.hpackEncoder.setMaxHeaderTableSize(this.tableSizeChangeOutput, max);
    }

    @Override
    public long maxHeaderTableSize() {
        return this.hpackEncoder.getMaxHeaderTableSize();
    }

    @Override
    public void maxHeaderListSize(long max) throws Http2Exception {
        this.hpackEncoder.setMaxHeaderListSize(max);
    }

    @Override
    public long maxHeaderListSize() {
        return this.hpackEncoder.getMaxHeaderListSize();
    }

    @Override
    public Http2HeadersEncoder.Configuration configuration() {
        return this;
    }
}

