/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.GoAwayFrame;
import org.eclipse.jetty.http2.parser.BodyParser;
import org.eclipse.jetty.http2.parser.HeaderParser;
import org.eclipse.jetty.http2.parser.Parser;

public class GoAwayBodyParser
extends BodyParser {
    private State state = State.PREPARE;
    private int cursor;
    private int length;
    private int lastStreamId;
    private int error;
    private byte[] payload;

    public GoAwayBodyParser(HeaderParser headerParser, Parser.Listener listener) {
        super(headerParser, listener);
    }

    private void reset() {
        this.state = State.PREPARE;
        this.cursor = 0;
        this.length = 0;
        this.lastStreamId = 0;
        this.error = 0;
        this.payload = null;
    }

    @Override
    public boolean parse(ByteBuffer buffer) {
        block9: while (buffer.hasRemaining()) {
            switch (this.state) {
                case PREPARE: {
                    this.state = State.LAST_STREAM_ID;
                    this.length = this.getBodyLength();
                    continue block9;
                }
                case LAST_STREAM_ID: {
                    if (buffer.remaining() >= 4) {
                        this.lastStreamId = buffer.getInt();
                        this.lastStreamId &= Integer.MAX_VALUE;
                        this.state = State.ERROR;
                        this.length -= 4;
                        if (this.length > 0) continue block9;
                        return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_go_away_frame");
                    }
                    this.state = State.LAST_STREAM_ID_BYTES;
                    this.cursor = 4;
                    continue block9;
                }
                case LAST_STREAM_ID_BYTES: {
                    int currByte = buffer.get() & 0xFF;
                    --this.cursor;
                    this.lastStreamId += currByte << 8 * this.cursor;
                    --this.length;
                    if (this.cursor > 0 && this.length <= 0) {
                        return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_go_away_frame");
                    }
                    if (this.cursor != 0) continue block9;
                    this.lastStreamId &= Integer.MAX_VALUE;
                    this.state = State.ERROR;
                    if (this.length != 0) continue block9;
                    return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_go_away_frame");
                }
                case ERROR: {
                    if (buffer.remaining() >= 4) {
                        this.error = buffer.getInt();
                        this.state = State.PAYLOAD;
                        this.length -= 4;
                        if (this.length < 0) {
                            return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_go_away_frame");
                        }
                        if (this.length != 0) continue block9;
                        return this.onGoAway(this.lastStreamId, this.error, null);
                    }
                    this.state = State.ERROR_BYTES;
                    this.cursor = 4;
                    continue block9;
                }
                case ERROR_BYTES: {
                    int currByte = buffer.get() & 0xFF;
                    --this.cursor;
                    this.error += currByte << 8 * this.cursor;
                    --this.length;
                    if (this.cursor > 0 && this.length <= 0) {
                        return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_go_away_frame");
                    }
                    if (this.cursor != 0) continue block9;
                    this.state = State.PAYLOAD;
                    if (this.length != 0) continue block9;
                    return this.onGoAway(this.lastStreamId, this.error, null);
                }
                case PAYLOAD: {
                    this.payload = new byte[this.length];
                    if (buffer.remaining() >= this.length) {
                        buffer.get(this.payload);
                        return this.onGoAway(this.lastStreamId, this.error, this.payload);
                    }
                    this.state = State.PAYLOAD_BYTES;
                    this.cursor = this.length;
                    continue block9;
                }
                case PAYLOAD_BYTES: {
                    this.payload[this.payload.length - this.cursor] = buffer.get();
                    --this.cursor;
                    if (this.cursor != 0) continue block9;
                    return this.onGoAway(this.lastStreamId, this.error, this.payload);
                }
            }
            throw new IllegalStateException();
        }
        return false;
    }

    private boolean onGoAway(int lastStreamId, int error, byte[] payload) {
        GoAwayFrame frame = new GoAwayFrame(lastStreamId, error, payload);
        this.reset();
        this.notifyGoAway(frame);
        return true;
    }

    private static enum State {
        PREPARE,
        LAST_STREAM_ID,
        LAST_STREAM_ID_BYTES,
        ERROR,
        ERROR_BYTES,
        PAYLOAD,
        PAYLOAD_BYTES;

    }
}

