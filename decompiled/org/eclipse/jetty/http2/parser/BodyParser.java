/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.GoAwayFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PingFrame;
import org.eclipse.jetty.http2.frames.PriorityFrame;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.http2.frames.ResetFrame;
import org.eclipse.jetty.http2.frames.SettingsFrame;
import org.eclipse.jetty.http2.frames.WindowUpdateFrame;
import org.eclipse.jetty.http2.parser.HeaderParser;
import org.eclipse.jetty.http2.parser.Parser;
import org.eclipse.jetty.util.BufferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BodyParser {
    protected static final Logger LOG = LoggerFactory.getLogger(BodyParser.class);
    private final HeaderParser headerParser;
    private final Parser.Listener listener;

    protected BodyParser(HeaderParser headerParser, Parser.Listener listener) {
        this.headerParser = headerParser;
        this.listener = listener;
    }

    public abstract boolean parse(ByteBuffer var1);

    protected void emptyBody(ByteBuffer buffer) {
        this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_frame");
    }

    protected boolean hasFlag(int bit) {
        return this.headerParser.hasFlag(bit);
    }

    protected boolean isPadding() {
        return this.headerParser.hasFlag(8);
    }

    protected boolean isEndStream() {
        return this.headerParser.hasFlag(1);
    }

    protected int getStreamId() {
        return this.headerParser.getStreamId();
    }

    protected int getBodyLength() {
        return this.headerParser.getLength();
    }

    protected int getFrameType() {
        return this.headerParser.getFrameType();
    }

    protected void notifyData(DataFrame frame) {
        try {
            this.listener.onData(frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected void notifyHeaders(HeadersFrame frame) {
        try {
            this.listener.onHeaders(frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected void notifyPriority(PriorityFrame frame) {
        try {
            this.listener.onPriority(frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected void notifyReset(ResetFrame frame) {
        try {
            this.listener.onReset(frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected void notifySettings(SettingsFrame frame) {
        try {
            this.listener.onSettings(frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected void notifyPushPromise(PushPromiseFrame frame) {
        try {
            this.listener.onPushPromise(frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected void notifyPing(PingFrame frame) {
        try {
            this.listener.onPing(frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected void notifyGoAway(GoAwayFrame frame) {
        try {
            this.listener.onGoAway(frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected void notifyWindowUpdate(WindowUpdateFrame frame) {
        try {
            this.listener.onWindowUpdate(frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected boolean connectionFailure(ByteBuffer buffer, int error, String reason) {
        BufferUtil.clear(buffer);
        this.notifyConnectionFailure(error, reason);
        return false;
    }

    private void notifyConnectionFailure(int error, String reason) {
        try {
            this.listener.onConnectionFailure(error, reason);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected boolean streamFailure(int streamId, int error, String reason) {
        this.notifyStreamFailure(streamId, error, reason);
        return false;
    }

    private void notifyStreamFailure(int streamId, int error, String reason) {
        try {
            this.listener.onStreamFailure(streamId, error, reason);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected boolean rateControlOnEvent(Object o) {
        return this.headerParser.getRateControl().onEvent(o);
    }
}

