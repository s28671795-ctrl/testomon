/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.ResetFrame;
import org.eclipse.jetty.http2.parser.BodyParser;
import org.eclipse.jetty.http2.parser.HeaderParser;
import org.eclipse.jetty.http2.parser.Parser;

public class ResetBodyParser
extends BodyParser {
    private State state = State.PREPARE;
    private int cursor;
    private int error;

    public ResetBodyParser(HeaderParser headerParser, Parser.Listener listener) {
        super(headerParser, listener);
    }

    private void reset() {
        this.state = State.PREPARE;
        this.cursor = 0;
        this.error = 0;
    }

    @Override
    public boolean parse(ByteBuffer buffer) {
        block5: while (buffer.hasRemaining()) {
            switch (this.state) {
                case PREPARE: {
                    if (this.getStreamId() == 0) {
                        return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_rst_stream_frame");
                    }
                    int length = this.getBodyLength();
                    if (length != 4) {
                        return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_rst_stream_frame");
                    }
                    this.state = State.ERROR;
                    continue block5;
                }
                case ERROR: {
                    if (buffer.remaining() >= 4) {
                        return this.onReset(buffer.getInt());
                    }
                    this.state = State.ERROR_BYTES;
                    this.cursor = 4;
                    continue block5;
                }
                case ERROR_BYTES: {
                    int currByte = buffer.get() & 0xFF;
                    --this.cursor;
                    this.error += currByte << 8 * this.cursor;
                    if (this.cursor != 0) continue block5;
                    return this.onReset(this.error);
                }
            }
            throw new IllegalStateException();
        }
        return false;
    }

    private boolean onReset(int error) {
        ResetFrame frame = new ResetFrame(this.getStreamId(), error);
        this.reset();
        this.notifyReset(frame);
        return true;
    }

    private static enum State {
        PREPARE,
        ERROR,
        ERROR_BYTES;

    }
}

