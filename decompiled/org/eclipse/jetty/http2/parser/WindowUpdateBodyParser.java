/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.WindowUpdateFrame;
import org.eclipse.jetty.http2.parser.BodyParser;
import org.eclipse.jetty.http2.parser.HeaderParser;
import org.eclipse.jetty.http2.parser.Parser;

public class WindowUpdateBodyParser
extends BodyParser {
    private State state = State.PREPARE;
    private int cursor;
    private int windowDelta;

    public WindowUpdateBodyParser(HeaderParser headerParser, Parser.Listener listener) {
        super(headerParser, listener);
    }

    private void reset() {
        this.state = State.PREPARE;
        this.cursor = 0;
        this.windowDelta = 0;
    }

    @Override
    public boolean parse(ByteBuffer buffer) {
        block5: while (buffer.hasRemaining()) {
            switch (this.state) {
                case PREPARE: {
                    int length = this.getBodyLength();
                    if (length != 4) {
                        return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_window_update_frame");
                    }
                    this.state = State.WINDOW_DELTA;
                    continue block5;
                }
                case WINDOW_DELTA: {
                    if (buffer.remaining() >= 4) {
                        this.windowDelta = buffer.getInt() & Integer.MAX_VALUE;
                        return this.onWindowUpdate(buffer, this.windowDelta);
                    }
                    this.state = State.WINDOW_DELTA_BYTES;
                    this.cursor = 4;
                    continue block5;
                }
                case WINDOW_DELTA_BYTES: {
                    byte currByte = buffer.get();
                    --this.cursor;
                    this.windowDelta += (currByte & 0xFF) << 8 * this.cursor;
                    if (this.cursor != 0) continue block5;
                    this.windowDelta &= Integer.MAX_VALUE;
                    return this.onWindowUpdate(buffer, this.windowDelta);
                }
            }
            throw new IllegalStateException();
        }
        return false;
    }

    private boolean onWindowUpdate(ByteBuffer buffer, int windowDelta) {
        int streamId = this.getStreamId();
        if (windowDelta == 0) {
            if (streamId == 0) {
                return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_window_update_frame");
            }
            return this.streamFailure(streamId, ErrorCode.PROTOCOL_ERROR.code, "invalid_window_update_frame");
        }
        WindowUpdateFrame frame = new WindowUpdateFrame(streamId, windowDelta);
        this.reset();
        this.notifyWindowUpdate(frame);
        return true;
    }

    private static enum State {
        PREPARE,
        WINDOW_DELTA,
        WINDOW_DELTA_BYTES;

    }
}

