/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import java.util.function.UnaryOperator;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.GoAwayFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PingFrame;
import org.eclipse.jetty.http2.frames.PriorityFrame;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.http2.frames.ResetFrame;
import org.eclipse.jetty.http2.frames.SettingsFrame;
import org.eclipse.jetty.http2.frames.WindowUpdateFrame;
import org.eclipse.jetty.http2.hpack.HpackDecoder;
import org.eclipse.jetty.http2.parser.BodyParser;
import org.eclipse.jetty.http2.parser.ContinuationBodyParser;
import org.eclipse.jetty.http2.parser.DataBodyParser;
import org.eclipse.jetty.http2.parser.GoAwayBodyParser;
import org.eclipse.jetty.http2.parser.HeaderBlockFragments;
import org.eclipse.jetty.http2.parser.HeaderBlockParser;
import org.eclipse.jetty.http2.parser.HeaderParser;
import org.eclipse.jetty.http2.parser.HeadersBodyParser;
import org.eclipse.jetty.http2.parser.PingBodyParser;
import org.eclipse.jetty.http2.parser.PriorityBodyParser;
import org.eclipse.jetty.http2.parser.PushPromiseBodyParser;
import org.eclipse.jetty.http2.parser.RateControl;
import org.eclipse.jetty.http2.parser.ResetBodyParser;
import org.eclipse.jetty.http2.parser.SettingsBodyParser;
import org.eclipse.jetty.http2.parser.UnknownBodyParser;
import org.eclipse.jetty.http2.parser.WindowUpdateBodyParser;
import org.eclipse.jetty.io.ByteBufferPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parser {
    private static final Logger LOG = LoggerFactory.getLogger(Parser.class);
    private final ByteBufferPool byteBufferPool;
    private final Listener listener;
    private final HeaderParser headerParser;
    private final HpackDecoder hpackDecoder;
    private final BodyParser[] bodyParsers;
    private UnknownBodyParser unknownBodyParser;
    private int maxFrameLength = 16384;
    private int maxSettingsKeys = 64;
    private boolean continuation;
    private State state = State.HEADER;

    public Parser(ByteBufferPool byteBufferPool, Listener listener, int maxDynamicTableSize, int maxHeaderSize) {
        this(byteBufferPool, listener, maxDynamicTableSize, maxHeaderSize, RateControl.NO_RATE_CONTROL);
    }

    public Parser(ByteBufferPool byteBufferPool, Listener listener, int maxDynamicTableSize, int maxHeaderSize, RateControl rateControl) {
        this.byteBufferPool = byteBufferPool;
        this.listener = listener;
        this.headerParser = new HeaderParser(rateControl == null ? RateControl.NO_RATE_CONTROL : rateControl);
        this.hpackDecoder = new HpackDecoder(maxDynamicTableSize, maxHeaderSize);
        this.bodyParsers = new BodyParser[FrameType.values().length];
    }

    public void init(UnaryOperator<Listener> wrapper) {
        Listener listener = (Listener)wrapper.apply(this.listener);
        this.unknownBodyParser = new UnknownBodyParser(this.headerParser, listener);
        HeaderBlockParser headerBlockParser = new HeaderBlockParser(this.headerParser, this.byteBufferPool, this.hpackDecoder, this.unknownBodyParser);
        HeaderBlockFragments headerBlockFragments = new HeaderBlockFragments(this.byteBufferPool);
        this.bodyParsers[FrameType.DATA.getType()] = new DataBodyParser(this.headerParser, listener);
        this.bodyParsers[FrameType.HEADERS.getType()] = new HeadersBodyParser(this.headerParser, listener, headerBlockParser, headerBlockFragments);
        this.bodyParsers[FrameType.PRIORITY.getType()] = new PriorityBodyParser(this.headerParser, listener);
        this.bodyParsers[FrameType.RST_STREAM.getType()] = new ResetBodyParser(this.headerParser, listener);
        this.bodyParsers[FrameType.SETTINGS.getType()] = new SettingsBodyParser(this.headerParser, listener, this.getMaxSettingsKeys());
        this.bodyParsers[FrameType.PUSH_PROMISE.getType()] = new PushPromiseBodyParser(this.headerParser, listener, headerBlockParser);
        this.bodyParsers[FrameType.PING.getType()] = new PingBodyParser(this.headerParser, listener);
        this.bodyParsers[FrameType.GO_AWAY.getType()] = new GoAwayBodyParser(this.headerParser, listener);
        this.bodyParsers[FrameType.WINDOW_UPDATE.getType()] = new WindowUpdateBodyParser(this.headerParser, listener);
        this.bodyParsers[FrameType.CONTINUATION.getType()] = new ContinuationBodyParser(this.headerParser, listener, headerBlockParser, headerBlockFragments);
    }

    private void reset() {
        this.headerParser.reset();
        this.state = State.HEADER;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void parse(ByteBuffer buffer) {
        try {
            block6: while (true) {
                switch (this.state) {
                    case HEADER: {
                        if (this.parseHeader(buffer)) continue block6;
                        return;
                    }
                    case BODY: {
                        if (!this.parseBody(buffer)) return;
                        continue block6;
                    }
                }
                break;
            }
            throw new IllegalStateException();
        }
        catch (Throwable x) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Parse failed", x);
            }
            this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR, "parser_error");
            return;
        }
    }

    protected boolean parseHeader(ByteBuffer buffer) {
        if (!this.headerParser.parse(buffer)) {
            return false;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Parsed {} frame header from {}@{}", this.headerParser, buffer, Integer.toHexString(buffer.hashCode()));
        }
        if (this.headerParser.getLength() > this.getMaxFrameLength()) {
            return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR, "invalid_frame_length");
        }
        FrameType frameType = FrameType.from(this.getFrameType());
        if (this.continuation) {
            if (frameType != FrameType.CONTINUATION) {
                return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR, "expected_continuation_frame");
            }
            if (this.headerParser.hasFlag(4)) {
                this.continuation = false;
            }
        } else if (frameType == FrameType.HEADERS) {
            this.continuation = !this.headerParser.hasFlag(4);
        } else if (frameType == FrameType.CONTINUATION) {
            return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR, "unexpected_continuation_frame");
        }
        this.state = State.BODY;
        return true;
    }

    protected boolean parseBody(ByteBuffer buffer) {
        int type = this.getFrameType();
        if (type < 0 || type >= this.bodyParsers.length) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Ignoring unknown frame type {}", (Object)Integer.toHexString(type));
            }
            if (!this.unknownBodyParser.parse(buffer)) {
                return false;
            }
            this.reset();
            return true;
        }
        BodyParser bodyParser = this.bodyParsers[type];
        if (this.headerParser.getLength() == 0) {
            bodyParser.emptyBody(buffer);
        } else if (!bodyParser.parse(buffer)) {
            return false;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Parsed {} frame body from {}@{}", new Object[]{FrameType.from(type), buffer, Integer.toHexString(buffer.hashCode())});
        }
        this.reset();
        return true;
    }

    private boolean connectionFailure(ByteBuffer buffer, ErrorCode error, String reason) {
        return this.unknownBodyParser.connectionFailure(buffer, error.code, reason);
    }

    protected int getFrameType() {
        return this.headerParser.getFrameType();
    }

    protected boolean hasFlag(int bit) {
        return this.headerParser.hasFlag(bit);
    }

    public int getMaxFrameLength() {
        return this.maxFrameLength;
    }

    public void setMaxFrameLength(int maxFrameLength) {
        this.maxFrameLength = maxFrameLength;
    }

    public int getMaxSettingsKeys() {
        return this.maxSettingsKeys;
    }

    public void setMaxSettingsKeys(int maxSettingsKeys) {
        this.maxSettingsKeys = maxSettingsKeys;
    }

    protected void notifyConnectionFailure(int error, String reason) {
        try {
            this.listener.onConnectionFailure(error, reason);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    public static interface Listener {
        public void onData(DataFrame var1);

        public void onHeaders(HeadersFrame var1);

        public void onPriority(PriorityFrame var1);

        public void onReset(ResetFrame var1);

        public void onSettings(SettingsFrame var1);

        public void onPushPromise(PushPromiseFrame var1);

        public void onPing(PingFrame var1);

        public void onGoAway(GoAwayFrame var1);

        public void onWindowUpdate(WindowUpdateFrame var1);

        public void onStreamFailure(int var1, int var2, String var3);

        public void onConnectionFailure(int var1, String var2);

        public static class Wrapper
        implements Listener {
            private final Listener listener;

            public Wrapper(Listener listener) {
                this.listener = listener;
            }

            public Listener getParserListener() {
                return this.listener;
            }

            @Override
            public void onData(DataFrame frame) {
                this.listener.onData(frame);
            }

            @Override
            public void onHeaders(HeadersFrame frame) {
                this.listener.onHeaders(frame);
            }

            @Override
            public void onPriority(PriorityFrame frame) {
                this.listener.onPriority(frame);
            }

            @Override
            public void onReset(ResetFrame frame) {
                this.listener.onReset(frame);
            }

            @Override
            public void onSettings(SettingsFrame frame) {
                this.listener.onSettings(frame);
            }

            @Override
            public void onPushPromise(PushPromiseFrame frame) {
                this.listener.onPushPromise(frame);
            }

            @Override
            public void onPing(PingFrame frame) {
                this.listener.onPing(frame);
            }

            @Override
            public void onGoAway(GoAwayFrame frame) {
                this.listener.onGoAway(frame);
            }

            @Override
            public void onWindowUpdate(WindowUpdateFrame frame) {
                this.listener.onWindowUpdate(frame);
            }

            @Override
            public void onStreamFailure(int streamId, int error, String reason) {
                this.listener.onStreamFailure(streamId, error, reason);
            }

            @Override
            public void onConnectionFailure(int error, String reason) {
                this.listener.onConnectionFailure(error, reason);
            }
        }

        public static class Adapter
        implements Listener {
            @Override
            public void onData(DataFrame frame) {
            }

            @Override
            public void onHeaders(HeadersFrame frame) {
            }

            @Override
            public void onPriority(PriorityFrame frame) {
            }

            @Override
            public void onReset(ResetFrame frame) {
            }

            @Override
            public void onSettings(SettingsFrame frame) {
            }

            @Override
            public void onPushPromise(PushPromiseFrame frame) {
            }

            @Override
            public void onPing(PingFrame frame) {
            }

            @Override
            public void onGoAway(GoAwayFrame frame) {
            }

            @Override
            public void onWindowUpdate(WindowUpdateFrame frame) {
            }

            @Override
            public void onStreamFailure(int streamId, int error, String reason) {
            }

            @Override
            public void onConnectionFailure(int error, String reason) {
                LOG.warn("Connection failure: {}/{}", (Object)error, (Object)reason);
            }
        }
    }

    private static enum State {
        HEADER,
        BODY;

    }
}

