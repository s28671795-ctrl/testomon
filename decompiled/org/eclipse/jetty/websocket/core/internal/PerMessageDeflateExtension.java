/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongConsumer;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.compression.CompressionPool;
import org.eclipse.jetty.websocket.core.AbstractExtension;
import org.eclipse.jetty.websocket.core.ExtensionConfig;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.exception.BadPayloadException;
import org.eclipse.jetty.websocket.core.exception.MessageTooLargeException;
import org.eclipse.jetty.websocket.core.exception.ProtocolException;
import org.eclipse.jetty.websocket.core.internal.DemandChain;
import org.eclipse.jetty.websocket.core.internal.DemandingFlusher;
import org.eclipse.jetty.websocket.core.internal.TransformingFlusher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerMessageDeflateExtension
extends AbstractExtension
implements DemandChain {
    private static final byte[] TAIL_BYTES = new byte[]{0, 0, -1, -1};
    private static final ByteBuffer TAIL_BYTES_BUF = ByteBuffer.wrap(TAIL_BYTES);
    private static final Logger LOG = LoggerFactory.getLogger(PerMessageDeflateExtension.class);
    private static final int DEFAULT_BUF_SIZE = 8192;
    private final OutgoingFlusher outgoingFlusher = new OutgoingFlusher();
    private final IncomingFlusher incomingFlusher = new IncomingFlusher();
    private CompressionPool.Entry deflaterHolder;
    private CompressionPool.Entry inflaterHolder;
    private boolean incomingCompressed;
    private ExtensionConfig configRequested;
    private ExtensionConfig configNegotiated;
    private int deflateBufferSize = 8192;
    private int inflateBufferSize = 8192;
    private boolean incomingContextTakeover = true;
    private boolean outgoingContextTakeover = true;

    @Override
    public String getName() {
        return "permessage-deflate";
    }

    @Override
    public boolean isRsv1User() {
        return true;
    }

    @Override
    public void sendFrame(Frame frame, Callback callback, boolean batch) {
        this.outgoingFlusher.sendFrame(frame, callback, batch);
    }

    @Override
    public void onFrame(Frame frame, Callback callback) {
        this.incomingFlusher.onFrame(frame, callback);
    }

    @Override
    public void init(ExtensionConfig config, WebSocketComponents components) {
        this.configRequested = new ExtensionConfig(config);
        HashMap<String, String> paramsNegotiated = new HashMap<String, String>();
        block15: for (String key : config.getParameterKeys()) {
            switch (key = key.trim()) {
                case "client_max_window_bits": 
                case "server_max_window_bits": {
                    continue block15;
                }
                case "client_no_context_takeover": {
                    paramsNegotiated.put("client_no_context_takeover", null);
                    this.incomingContextTakeover = false;
                    continue block15;
                }
                case "server_no_context_takeover": {
                    paramsNegotiated.put("server_no_context_takeover", null);
                    this.outgoingContextTakeover = false;
                    continue block15;
                }
                case "@deflate_buffer_size": {
                    this.deflateBufferSize = config.getParameter(key, 8192);
                    continue block15;
                }
                case "@inflate_buffer_size": {
                    this.inflateBufferSize = config.getParameter(key, 8192);
                    continue block15;
                }
            }
            throw new IllegalArgumentException();
        }
        this.configNegotiated = new ExtensionConfig(config.getName(), paramsNegotiated);
        LOG.debug("config: outgoingContextTakover={}, incomingContextTakeover={} : {}", this.outgoingContextTakeover, this.incomingContextTakeover, this);
        super.init(this.configNegotiated, components);
    }

    @Override
    public void close() {
        this.incomingFlusher.closeFlusher();
        this.outgoingFlusher.closeFlusher();
        this.releaseInflater();
        this.releaseDeflater();
    }

    private static String toDetail(Inflater inflater) {
        return String.format("Inflater[finished=%b,read=%d,written=%d,remaining=%d,in=%d,out=%d]", inflater.finished(), inflater.getBytesRead(), inflater.getBytesWritten(), inflater.getRemaining(), inflater.getTotalIn(), inflater.getTotalOut());
    }

    private static String toDetail(Deflater deflater) {
        return String.format("Deflater[finished=%b,read=%d,written=%d,in=%d,out=%d]", deflater.finished(), deflater.getBytesRead(), deflater.getBytesWritten(), deflater.getTotalIn(), deflater.getTotalOut());
    }

    public static boolean endsWithTail(ByteBuffer buf) {
        if (buf == null || buf.remaining() < TAIL_BYTES.length) {
            return false;
        }
        int limit = buf.limit();
        for (int i = TAIL_BYTES.length; i > 0; --i) {
            if (buf.get(limit - i) == TAIL_BYTES[TAIL_BYTES.length - i]) continue;
            return false;
        }
        return true;
    }

    public Deflater getDeflater() {
        if (this.deflaterHolder == null) {
            this.deflaterHolder = this.getDeflaterPool().acquire();
        }
        return (Deflater)this.deflaterHolder.get();
    }

    public Inflater getInflater() {
        if (this.inflaterHolder == null) {
            this.inflaterHolder = this.getInflaterPool().acquire();
        }
        return (Inflater)this.inflaterHolder.get();
    }

    public void releaseInflater() {
        if (this.inflaterHolder != null) {
            this.inflaterHolder.release();
            this.inflaterHolder = null;
        }
    }

    public void releaseDeflater() {
        if (this.deflaterHolder != null) {
            this.deflaterHolder.release();
            this.deflaterHolder = null;
        }
    }

    @Override
    public String toString() {
        return String.format("%s[requested=\"%s\", negotiated=\"%s\"]", this.getClass().getSimpleName(), this.configRequested.getParameterizedName(), this.configNegotiated.getParameterizedName());
    }

    @Override
    protected void nextIncomingFrame(Frame frame, Callback callback) {
        if (frame.isFin() && !this.incomingContextTakeover) {
            LOG.debug("Incoming Context Reset");
            this.releaseInflater();
        }
        super.nextIncomingFrame(frame, callback);
    }

    @Override
    protected void nextOutgoingFrame(Frame frame, Callback callback, boolean batch) {
        if (frame.isFin() && !this.outgoingContextTakeover) {
            LOG.debug("Outgoing Context Reset");
            this.releaseDeflater();
        }
        super.nextOutgoingFrame(frame, callback, batch);
    }

    @Override
    public void setNextDemand(LongConsumer nextDemand) {
        this.incomingFlusher.setNextDemand(nextDemand);
    }

    @Override
    public void demand(long n) {
        this.incomingFlusher.demand(n);
    }

    private class OutgoingFlusher
    extends TransformingFlusher {
        private boolean _first;
        private Frame _frame;
        private boolean _batch;

        private OutgoingFlusher() {
        }

        @Override
        protected boolean onFrame(Frame frame, Callback callback, boolean batch) {
            if (frame.isControlFrame()) {
                PerMessageDeflateExtension.this.nextOutgoingFrame(frame, callback, batch);
                return true;
            }
            this._first = true;
            this._frame = frame;
            this._batch = batch;
            PerMessageDeflateExtension.this.getDeflater().setInput(frame.getPayload().slice());
            callback.succeeded();
            return false;
        }

        @Override
        protected boolean transform(Callback callback) {
            boolean finished = this.deflate(callback);
            this._first = false;
            return finished;
        }

        private boolean deflate(Callback callback) {
            ByteBuffer payload;
            boolean finished;
            ByteBuffer buffer;
            block9: {
                int compressed;
                long maxFrameSize = PerMessageDeflateExtension.this.getConfiguration().getMaxFrameSize();
                int bufferSize = maxFrameSize <= 0L ? PerMessageDeflateExtension.this.deflateBufferSize : (int)Math.min(maxFrameSize, (long)PerMessageDeflateExtension.this.deflateBufferSize);
                buffer = PerMessageDeflateExtension.this.getBufferPool().acquire(bufferSize, false);
                callback = Callback.from(callback, () -> PerMessageDeflateExtension.this.getBufferPool().release(buffer));
                BufferUtil.clear(buffer);
                finished = false;
                Deflater deflater = PerMessageDeflateExtension.this.getDeflater();
                do {
                    compressed = deflater.deflate(buffer.array(), buffer.arrayOffset() + buffer.position(), bufferSize - buffer.position(), 2);
                    buffer.limit(buffer.limit() + compressed);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Compressed {} bytes {}", (Object)compressed, (Object)PerMessageDeflateExtension.toDetail(deflater));
                    }
                    if (buffer.limit() != bufferSize) continue;
                    if (!PerMessageDeflateExtension.this.getConfiguration().isAutoFragment()) {
                        throw new MessageTooLargeException("Deflated payload exceeded the compress buffer size");
                    }
                    break block9;
                } while (compressed != 0);
                finished = true;
            }
            if ((payload = buffer).hasRemaining()) {
                if (finished && this._frame.isFin() && PerMessageDeflateExtension.endsWithTail(payload)) {
                    payload.limit(payload.limit() - TAIL_BYTES.length);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("payload (TAIL_DROP_FIN_ONLY) = {}", (Object)BufferUtil.toDetailString(payload));
                    }
                }
            } else if (this._frame.isFin()) {
                payload = ByteBuffer.wrap(new byte[]{0});
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Compressed {}: payload:{}", (Object)this._frame, (Object)payload.remaining());
            }
            Frame chunk = new Frame(this._first ? this._frame.getOpCode() : (byte)0);
            chunk.setRsv1(this._first && this._frame.getOpCode() != 0);
            chunk.setPayload(payload);
            chunk.setFin(this._frame.isFin() && finished);
            PerMessageDeflateExtension.this.nextOutgoingFrame(chunk, callback, this._batch);
            return finished;
        }
    }

    private class IncomingFlusher
    extends DemandingFlusher {
        private boolean _tailBytes;
        private AtomicReference<ByteBuffer> _payloadRef;

        public IncomingFlusher() {
            super(PerMessageDeflateExtension.this::nextIncomingFrame);
            this._payloadRef = new AtomicReference();
        }

        @Override
        protected boolean handle(Frame frame, Callback callback, boolean first) {
            if (first) {
                if (frame.isControlFrame()) {
                    this.emitFrame(frame, callback);
                    return true;
                }
                switch (frame.getOpCode()) {
                    case 1: 
                    case 2: {
                        PerMessageDeflateExtension.this.incomingCompressed = frame.isRsv1();
                        break;
                    }
                    case 0: {
                        if (!frame.isRsv1()) break;
                        throw new ProtocolException("Invalid RSV1 set on permessage-deflate CONTINUATION frame");
                    }
                }
                if (!PerMessageDeflateExtension.this.incomingCompressed) {
                    this.emitFrame(frame, callback);
                    return true;
                }
                this._tailBytes = false;
                PerMessageDeflateExtension.this.getInflater().setInput(frame.getPayload().slice());
            }
            try {
                return this.inflate(frame, callback, first);
            }
            catch (DataFormatException e) {
                throw new BadPayloadException(e);
            }
        }

        private boolean inflate(Frame frame, Callback callback, boolean first) throws DataFormatException {
            boolean complete;
            ByteBuffer payload;
            block5: {
                long maxFrameSize = PerMessageDeflateExtension.this.getConfiguration().getMaxFrameSize();
                int bufferSize = maxFrameSize <= 0L ? PerMessageDeflateExtension.this.inflateBufferSize : (int)Math.min(maxFrameSize, (long)PerMessageDeflateExtension.this.inflateBufferSize);
                payload = PerMessageDeflateExtension.this.getBufferPool().acquire(bufferSize, false);
                this._payloadRef = new AtomicReference<ByteBuffer>(payload);
                BufferUtil.clear(payload);
                Inflater inflater = PerMessageDeflateExtension.this.getInflater();
                complete = false;
                while (true) {
                    int decompressed = inflater.inflate(payload.array(), payload.arrayOffset() + payload.position(), bufferSize - payload.position());
                    payload.limit(payload.limit() + decompressed);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Decompress: read {} {}", (Object)decompressed, (Object)PerMessageDeflateExtension.toDetail(inflater));
                    }
                    if (payload.limit() == bufferSize) {
                        if (!PerMessageDeflateExtension.this.getConfiguration().isAutoFragment()) {
                            throw new MessageTooLargeException("Inflated payload exceeded the decompress buffer size");
                        }
                        break block5;
                    }
                    if (decompressed != 0) continue;
                    if (this._tailBytes || !frame.isFin()) break;
                    inflater.setInput(TAIL_BYTES_BUF.slice());
                    this._tailBytes = true;
                }
                complete = true;
            }
            Frame chunk = new Frame(first ? frame.getOpCode() : (byte)0);
            chunk.setRsv1(false);
            chunk.setPayload(payload);
            chunk.setFin(frame.isFin() && complete);
            boolean completeCallback = complete;
            AtomicReference<ByteBuffer> payloadRef = this._payloadRef;
            Callback payloadCallback = Callback.from(() -> {
                PerMessageDeflateExtension.this.getBufferPool().release(payloadRef.getAndSet(null));
                if (completeCallback) {
                    callback.succeeded();
                }
            }, (Throwable t2) -> {
                PerMessageDeflateExtension.this.getBufferPool().release(payloadRef.getAndSet(null));
                if (completeCallback) {
                    callback.failed((Throwable)t2);
                }
                this.failFlusher((Throwable)t2);
            });
            this.emitFrame(chunk, payloadCallback);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Decompress finished: {} {}", (Object)complete, (Object)chunk);
            }
            return complete;
        }

        @Override
        protected void onCompleteFailure(Throwable cause) {
            PerMessageDeflateExtension.this.getBufferPool().release(this._payloadRef.getAndSet(null));
            super.onCompleteFailure(cause);
        }
    }
}

