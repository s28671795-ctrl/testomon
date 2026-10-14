/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.PingFrame;
import org.eclipse.jetty.http2.parser.BodyParser;
import org.eclipse.jetty.http2.parser.HeaderParser;
import org.eclipse.jetty.http2.parser.Parser;

public class PingBodyParser
extends BodyParser {
    private State state = State.PREPARE;
    private int cursor;
    private byte[] payload;

    public PingBodyParser(HeaderParser headerParser, Parser.Listener listener) {
        super(headerParser, listener);
    }

    private void reset() {
        this.state = State.PREPARE;
        this.cursor = 0;
        this.payload = null;
    }

    @Override
    public boolean parse(ByteBuffer buffer) {
        block5: while (buffer.hasRemaining()) {
            switch (this.state) {
                case PREPARE: {
                    if (this.getStreamId() != 0) {
                        return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_ping_frame");
                    }
                    if (this.getBodyLength() != 8) {
                        return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_ping_frame");
                    }
                    this.state = State.PAYLOAD;
                    continue block5;
                }
                case PAYLOAD: {
                    this.payload = new byte[8];
                    if (buffer.remaining() >= 8) {
                        buffer.get(this.payload);
                        return this.onPing(buffer, this.payload);
                    }
                    this.state = State.PAYLOAD_BYTES;
                    this.cursor = 8;
                    continue block5;
                }
                case PAYLOAD_BYTES: {
                    this.payload[8 - this.cursor] = buffer.get();
                    --this.cursor;
                    if (this.cursor != 0) continue block5;
                    return this.onPing(buffer, this.payload);
                }
            }
            throw new IllegalStateException();
        }
        return false;
    }

    private boolean onPing(ByteBuffer buffer, byte[] payload) {
        PingFrame frame = new PingFrame(payload, this.hasFlag(1));
        if (!this.rateControlOnEvent(frame)) {
            return this.connectionFailure(buffer, ErrorCode.ENHANCE_YOUR_CALM_ERROR.code, "invalid_ping_frame_rate");
        }
        this.reset();
        this.notifyPing(frame);
        return true;
    }

    private static enum State {
        PREPARE,
        PAYLOAD,
        PAYLOAD_BYTES;

    }
}

