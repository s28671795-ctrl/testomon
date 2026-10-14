/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.netty.shaded.io.grpc.netty.AbstractHttp2Headers;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Headers;
import io.grpc.netty.shaded.io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

final class GrpcHttp2OutboundHeaders
extends AbstractHttp2Headers {
    private final AsciiString[] normalHeaders;
    private final AsciiString[] preHeaders;
    private static final AsciiString[] EMPTY = new AsciiString[0];

    static GrpcHttp2OutboundHeaders clientRequestHeaders(byte[][] serializedMetadata, AsciiString authority, AsciiString path, AsciiString method2, AsciiString scheme2, AsciiString userAgent) {
        AsciiString[] preHeaders = new AsciiString[]{Http2Headers.PseudoHeaderName.AUTHORITY.value(), authority, Http2Headers.PseudoHeaderName.PATH.value(), path, Http2Headers.PseudoHeaderName.METHOD.value(), method2, Http2Headers.PseudoHeaderName.SCHEME.value(), scheme2, Utils.CONTENT_TYPE_HEADER, Utils.CONTENT_TYPE_GRPC, Utils.TE_HEADER, Utils.TE_TRAILERS, Utils.USER_AGENT, userAgent};
        return new GrpcHttp2OutboundHeaders(preHeaders, serializedMetadata);
    }

    static GrpcHttp2OutboundHeaders serverResponseHeaders(byte[][] serializedMetadata) {
        AsciiString[] preHeaders = new AsciiString[]{Http2Headers.PseudoHeaderName.STATUS.value(), Utils.STATUS_OK, Utils.CONTENT_TYPE_HEADER, Utils.CONTENT_TYPE_GRPC};
        return new GrpcHttp2OutboundHeaders(preHeaders, serializedMetadata);
    }

    static GrpcHttp2OutboundHeaders serverResponseTrailers(byte[][] serializedMetadata) {
        return new GrpcHttp2OutboundHeaders(EMPTY, serializedMetadata);
    }

    private GrpcHttp2OutboundHeaders(AsciiString[] preHeaders, byte[][] serializedMetadata) {
        this.normalHeaders = new AsciiString[serializedMetadata.length];
        for (int i = 0; i < this.normalHeaders.length; ++i) {
            this.normalHeaders[i] = new AsciiString(serializedMetadata[i], false);
        }
        this.preHeaders = preHeaders;
    }

    @Override
    public CharSequence status() {
        if (this.preHeaders.length >= 2 && this.preHeaders[0] == Http2Headers.PseudoHeaderName.STATUS.value()) {
            return this.preHeaders[1];
        }
        return null;
    }

    @Override
    public Iterator<Map.Entry<CharSequence, CharSequence>> iterator() {
        return new Itr();
    }

    @Override
    public int size() {
        return (this.normalHeaders.length + this.preHeaders.length) / 2;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(this.getClass().getSimpleName()).append('[');
        String separator = "";
        for (Map.Entry<CharSequence, CharSequence> e : this) {
            CharSequence name = e.getKey();
            CharSequence value = e.getValue();
            builder.append(separator);
            builder.append(name).append(": ").append(value);
            separator = ", ";
        }
        builder.append(']');
        return builder.toString();
    }

    private class Itr
    implements Map.Entry<CharSequence, CharSequence>,
    Iterator<Map.Entry<CharSequence, CharSequence>> {
        private int idx;
        private AsciiString[] current;
        private AsciiString key;
        private AsciiString value;

        private Itr() {
            this.current = GrpcHttp2OutboundHeaders.this.preHeaders.length != 0 ? GrpcHttp2OutboundHeaders.this.preHeaders : GrpcHttp2OutboundHeaders.this.normalHeaders;
        }

        @Override
        public boolean hasNext() {
            return this.idx < this.current.length;
        }

        @Override
        public Map.Entry<CharSequence, CharSequence> next() {
            if (this.hasNext()) {
                this.key = this.current[this.idx];
                this.value = this.current[this.idx + 1];
                this.idx += 2;
                if (this.idx >= this.current.length && this.current == GrpcHttp2OutboundHeaders.this.preHeaders) {
                    this.current = GrpcHttp2OutboundHeaders.this.normalHeaders;
                    this.idx = 0;
                }
                return this;
            }
            throw new NoSuchElementException();
        }

        @Override
        public CharSequence getKey() {
            return this.key;
        }

        @Override
        public CharSequence getValue() {
            return this.value;
        }

        @Override
        public CharSequence setValue(CharSequence value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

