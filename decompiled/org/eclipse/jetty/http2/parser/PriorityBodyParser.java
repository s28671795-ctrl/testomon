/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.PriorityFrame;
import org.eclipse.jetty.http2.parser.BodyParser;
import org.eclipse.jetty.http2.parser.HeaderParser;
import org.eclipse.jetty.http2.parser.Parser;

public class PriorityBodyParser
extends BodyParser {
    private State state = State.PREPARE;
    private int cursor;
    private boolean exclusive;
    private int parentStreamId;

    public PriorityBodyParser(HeaderParser headerParser, Parser.Listener listener) {
        super(headerParser, listener);
    }

    private void reset() {
        this.state = State.PREPARE;
        this.cursor = 0;
        this.exclusive = false;
        this.parentStreamId = 0;
    }

    @Override
    public boolean parse(ByteBuffer buffer) {
        block7: while (buffer.hasRemaining()) {
            switch (this.state) {
                case PREPARE: {
                    if (this.getStreamId() == 0) {
                        return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_priority_frame");
                    }
                    int length = this.getBodyLength();
                    if (length != 5) {
                        return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_priority_frame");
                    }
                    this.state = State.EXCLUSIVE;
                    continue block7;
                }
                case EXCLUSIVE: {
                    int currByte = buffer.get(buffer.position());
                    this.exclusive = (currByte & 0x80) == 128;
                    this.state = State.PARENT_STREAM_ID;
                    continue block7;
                }
                case PARENT_STREAM_ID: {
                    if (buffer.remaining() >= 4) {
                        this.parentStreamId = buffer.getInt();
                        this.parentStreamId &= Integer.MAX_VALUE;
                        this.state = State.WEIGHT;
                        continue block7;
                    }
                    this.state = State.PARENT_STREAM_ID_BYTES;
                    this.cursor = 4;
                    continue block7;
                }
                case PARENT_STREAM_ID_BYTES: {
                    int currByte = buffer.get() & 0xFF;
                    --this.cursor;
                    this.parentStreamId += currByte << 8 * this.cursor;
                    if (this.cursor != 0) continue block7;
                    this.parentStreamId &= Integer.MAX_VALUE;
                    this.state = State.WEIGHT;
                    continue block7;
                }
                case WEIGHT: {
                    if (this.getStreamId() == this.parentStreamId) {
                        return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_priority_frame");
                    }
                    int weight = (buffer.get() & 0xFF) + 1;
                    return this.onPriority(buffer, this.parentStreamId, weight, this.exclusive);
                }
            }
            throw new IllegalStateException();
        }
        return false;
    }

    private boolean onPriority(ByteBuffer buffer, int parentStreamId, int weight, boolean exclusive) {
        PriorityFrame frame = new PriorityFrame(this.getStreamId(), parentStreamId, weight, exclusive);
        if (!this.rateControlOnEvent(frame)) {
            return this.connectionFailure(buffer, ErrorCode.ENHANCE_YOUR_CALM_ERROR.code, "invalid_priority_frame_rate");
        }
        this.reset();
        this.notifyPriority(frame);
        return true;
    }

    private static enum State {
        PREPARE,
        EXCLUSIVE,
        PARENT_STREAM_ID,
        PARENT_STREAM_ID_BYTES,
        WEIGHT;

    }
}

