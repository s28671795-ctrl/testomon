/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.handler.gzip;

import java.nio.ByteBuffer;
import java.nio.channels.WritePendingException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import org.eclipse.jetty.http.CompressedContentFormat;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.PreEncodedHttpField;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.gzip.GzipFactory;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.IteratingCallback;
import org.eclipse.jetty.util.IteratingNestedCallback;
import org.eclipse.jetty.util.compression.CompressionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GzipHttpOutputInterceptor
implements HttpOutput.Interceptor {
    public static Logger LOG = LoggerFactory.getLogger(GzipHttpOutputInterceptor.class);
    private static final byte[] GZIP_HEADER = new byte[]{31, -117, 8, 0, 0, 0, 0, 0, 0, 0};
    public static final HttpField VARY_ACCEPT_ENCODING = new PreEncodedHttpField(HttpHeader.VARY, HttpHeader.ACCEPT_ENCODING.asString());
    private final AtomicReference<GZState> _state = new AtomicReference<GZState>(GZState.MIGHT_COMPRESS);
    private final CRC32 _crc = new CRC32();
    private final GzipFactory _factory;
    private final HttpOutput.Interceptor _interceptor;
    private final HttpChannel _channel;
    private final HttpField _vary;
    private final int _bufferSize;
    private final boolean _syncFlush;
    private CompressionPool.Entry _deflaterEntry;
    private ByteBuffer _buffer;

    public GzipHttpOutputInterceptor(GzipFactory factory2, HttpChannel channel, HttpOutput.Interceptor next, boolean syncFlush) {
        this(factory2, VARY_ACCEPT_ENCODING, channel.getHttpConfiguration().getOutputBufferSize(), channel, next, syncFlush);
    }

    public GzipHttpOutputInterceptor(GzipFactory factory2, HttpField vary, HttpChannel channel, HttpOutput.Interceptor next, boolean syncFlush) {
        this(factory2, vary, channel.getHttpConfiguration().getOutputBufferSize(), channel, next, syncFlush);
    }

    public GzipHttpOutputInterceptor(GzipFactory factory2, HttpField vary, int bufferSize, HttpChannel channel, HttpOutput.Interceptor next, boolean syncFlush) {
        this._factory = factory2;
        this._channel = channel;
        this._interceptor = next;
        this._vary = vary;
        this._bufferSize = bufferSize;
        this._syncFlush = syncFlush;
    }

    @Override
    public HttpOutput.Interceptor getNextInterceptor() {
        return this._interceptor;
    }

    @Override
    public void write(ByteBuffer content, boolean complete, Callback callback) {
        switch (this._state.get()) {
            case MIGHT_COMPRESS: {
                this.commit(content, complete, callback);
                break;
            }
            case NOT_COMPRESSING: {
                this._interceptor.write(content, complete, callback);
                return;
            }
            case COMMITTING: {
                callback.failed(new WritePendingException());
                break;
            }
            case COMPRESSING: {
                this.gzip(content, complete, callback);
                break;
            }
            default: {
                callback.failed(new IllegalStateException("state=" + (Object)((Object)this._state.get())));
            }
        }
    }

    private void addTrailer() {
        BufferUtil.putIntLittleEndian(this._buffer, (int)this._crc.getValue());
        BufferUtil.putIntLittleEndian(this._buffer, ((Deflater)this._deflaterEntry.get()).getTotalIn());
    }

    private void gzip(ByteBuffer content, boolean complete, Callback callback) {
        if (content.hasRemaining() || complete) {
            new GzipBufferCB(content, complete, callback).iterate();
        } else {
            callback.succeeded();
        }
    }

    protected void commit(ByteBuffer content, boolean complete, Callback callback) {
        String baseType;
        Response response = this._channel.getResponse();
        int sc = response.getStatus();
        if (sc > 0 && (sc < 200 || sc == 204 || sc == 205 || sc >= 300)) {
            LOG.debug("{} exclude by status {}", (Object)this, (Object)sc);
            this.noCompression();
            if (sc == 304) {
                String requestEtags = (String)this._channel.getRequest().getAttribute("o.e.j.s.h.gzip.GzipHandler.etag");
                String responseEtag = response.getHttpFields().get(HttpHeader.ETAG);
                if (requestEtags != null && responseEtag != null) {
                    String responseEtagGzip = this.etagGzip(responseEtag);
                    if (requestEtags.contains(responseEtagGzip)) {
                        response.getHttpFields().put(HttpHeader.ETAG, responseEtagGzip);
                    }
                    if (this._vary != null) {
                        response.getHttpFields().ensureField(this._vary);
                    }
                }
            }
            this._interceptor.write(content, complete, callback);
            return;
        }
        String ct = response.getContentType();
        if (ct != null && !this._factory.isMimeTypeGzipable(baseType = HttpField.valueParameters(ct, null))) {
            LOG.debug("{} exclude by mimeType {}", (Object)this, (Object)ct);
            this.noCompression();
            this._interceptor.write(content, complete, callback);
            return;
        }
        HttpFields.Mutable fields = response.getHttpFields();
        String ce = fields.get(HttpHeader.CONTENT_ENCODING);
        if (ce != null) {
            LOG.debug("{} exclude by content-encoding {}", (Object)this, (Object)ce);
            this.noCompression();
            this._interceptor.write(content, complete, callback);
            return;
        }
        if (this._state.compareAndSet(GZState.MIGHT_COMPRESS, GZState.COMMITTING)) {
            long contentLength;
            if (this._vary != null) {
                fields.ensureField(this._vary);
            }
            if ((contentLength = response.getContentLength()) < 0L && complete) {
                contentLength = content.remaining();
            }
            this._deflaterEntry = this._factory.getDeflaterEntry(this._channel.getRequest(), contentLength);
            if (this._deflaterEntry == null) {
                LOG.debug("{} exclude no deflater", (Object)this);
                this._state.set(GZState.NOT_COMPRESSING);
                this._interceptor.write(content, complete, callback);
                return;
            }
            fields.put(CompressedContentFormat.GZIP.getContentEncoding());
            this._crc.reset();
            response.setContentLength(-1);
            String etag = fields.get(HttpHeader.ETAG);
            if (etag != null) {
                fields.put(HttpHeader.ETAG, this.etagGzip(etag));
            }
            LOG.debug("{} compressing {}", (Object)this, (Object)this._deflaterEntry);
            this._state.set(GZState.COMPRESSING);
            if (BufferUtil.isEmpty(content)) {
                this._interceptor.write(BufferUtil.EMPTY_BUFFER, complete, callback);
            } else {
                this.gzip(content, complete, callback);
            }
        } else {
            callback.failed(new WritePendingException());
        }
    }

    private String etagGzip(String etag) {
        return CompressedContentFormat.GZIP.etag(etag);
    }

    public void noCompression() {
        block4: while (true) {
            switch (this._state.get()) {
                case NOT_COMPRESSING: {
                    return;
                }
                case MIGHT_COMPRESS: {
                    if (!this._state.compareAndSet(GZState.MIGHT_COMPRESS, GZState.NOT_COMPRESSING)) continue block4;
                    return;
                }
            }
            break;
        }
        throw new IllegalStateException(this._state.get().toString());
    }

    public boolean mightCompress() {
        return this._state.get() == GZState.MIGHT_COMPRESS;
    }

    private static enum GZState {
        MIGHT_COMPRESS,
        NOT_COMPRESSING,
        COMMITTING,
        COMPRESSING,
        FINISHED;

    }

    private class GzipBufferCB
    extends IteratingNestedCallback {
        private final ByteBuffer _content;
        private final boolean _last;

        public GzipBufferCB(ByteBuffer content, boolean complete, Callback callback) {
            super(callback);
            this._content = content;
            this._last = complete;
            GzipHttpOutputInterceptor.this._crc.update(this._content.slice());
            Deflater deflater = (Deflater)GzipHttpOutputInterceptor.this._deflaterEntry.get();
            deflater.setInput(this._content);
            if (this._last) {
                deflater.finish();
            }
        }

        @Override
        protected void onCompleteFailure(Throwable x) {
            if (GzipHttpOutputInterceptor.this._deflaterEntry != null) {
                GzipHttpOutputInterceptor.this._deflaterEntry.release();
                GzipHttpOutputInterceptor.this._deflaterEntry = null;
            }
            super.onCompleteFailure(x);
        }

        @Override
        protected IteratingCallback.Action process() throws Exception {
            if (GzipHttpOutputInterceptor.this._deflaterEntry == null) {
                if (GzipHttpOutputInterceptor.this._buffer != null) {
                    GzipHttpOutputInterceptor.this._channel.getByteBufferPool().release(GzipHttpOutputInterceptor.this._buffer);
                    GzipHttpOutputInterceptor.this._buffer = null;
                }
                return IteratingCallback.Action.SUCCEEDED;
            }
            if (GzipHttpOutputInterceptor.this._buffer == null) {
                GzipHttpOutputInterceptor.this._buffer = GzipHttpOutputInterceptor.this._channel.getByteBufferPool().acquire(GzipHttpOutputInterceptor.this._bufferSize, GzipHttpOutputInterceptor.this._channel.isUseOutputDirectByteBuffers());
                BufferUtil.fill(GzipHttpOutputInterceptor.this._buffer, GZIP_HEADER, 0, GZIP_HEADER.length);
            } else {
                BufferUtil.clear(GzipHttpOutputInterceptor.this._buffer);
            }
            Deflater deflater = (Deflater)GzipHttpOutputInterceptor.this._deflaterEntry.get();
            if (!deflater.finished()) {
                if (deflater.needsInput() && !this._last) {
                    return IteratingCallback.Action.SUCCEEDED;
                }
                int pos = BufferUtil.flipToFill(GzipHttpOutputInterceptor.this._buffer);
                deflater.deflate(GzipHttpOutputInterceptor.this._buffer, GzipHttpOutputInterceptor.this._syncFlush ? 2 : 0);
                BufferUtil.flipToFlush(GzipHttpOutputInterceptor.this._buffer, pos);
            }
            if (deflater.finished() && BufferUtil.space(GzipHttpOutputInterceptor.this._buffer) >= 8) {
                GzipHttpOutputInterceptor.this.addTrailer();
                GzipHttpOutputInterceptor.this._deflaterEntry.release();
                GzipHttpOutputInterceptor.this._deflaterEntry = null;
            }
            GzipHttpOutputInterceptor.this._interceptor.write(GzipHttpOutputInterceptor.this._buffer, GzipHttpOutputInterceptor.this._deflaterEntry == null, this);
            return IteratingCallback.Action.SCHEDULED;
        }

        @Override
        public String toString() {
            return String.format("%s[content=%s last=%b buffer=%s deflate=%s %s]", super.toString(), BufferUtil.toDetailString(this._content), this._last, BufferUtil.toDetailString(GzipHttpOutputInterceptor.this._buffer), GzipHttpOutputInterceptor.this._deflaterEntry, GzipHttpOutputInterceptor.this._deflaterEntry != null && ((Deflater)GzipHttpOutputInterceptor.this._deflaterEntry.get()).finished() ? "(finished)" : "");
        }
    }
}

