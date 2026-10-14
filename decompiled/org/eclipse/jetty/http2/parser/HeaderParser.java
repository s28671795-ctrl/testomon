/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.parser.RateControl;

public class HeaderParser {
    private final RateControl rateControl;
    private State state = State.LENGTH;
    private int cursor;
    private int length;
    private int type;
    private int flags;
    private int streamId;

    public HeaderParser(RateControl rateControl) {
        this.rateControl = rateControl;
    }

    public RateControl getRateControl() {
        return this.rateControl;
    }

    protected void reset() {
        this.state = State.LENGTH;
        this.cursor = 0;
        this.length = 0;
        this.type = 0;
        this.flags = 0;
        this.streamId = 0;
    }

    public boolean parse(ByteBuffer buffer) {
        block7: while (buffer.hasRemaining()) {
            switch (this.state) {
                case LENGTH: {
                    int octet = buffer.get() & 0xFF;
                    this.length = (this.length << 8) + octet;
                    if (++this.cursor != 3) continue block7;
                    this.length &= 0xFFFFFF;
                    this.state = State.TYPE;
                    continue block7;
                }
                case TYPE: {
                    this.type = buffer.get() & 0xFF;
                    this.state = State.FLAGS;
                    continue block7;
                }
                case FLAGS: {
                    this.flags = buffer.get() & 0xFF;
                    this.state = State.STREAM_ID;
                    continue block7;
                }
                case STREAM_ID: {
                    if (buffer.remaining() >= 4) {
                        this.streamId = buffer.getInt();
                        this.streamId &= Integer.MAX_VALUE;
                        return true;
                    }
                    this.state = State.STREAM_ID_BYTES;
                    this.cursor = 4;
                    continue block7;
                }
                case STREAM_ID_BYTES: {
                    int currByte = buffer.get() & 0xFF;
                    --this.cursor;
                    this.streamId += currByte << 8 * this.cursor;
                    if (this.cursor != 0) continue block7;
                    this.streamId &= Integer.MAX_VALUE;
                    return true;
                }
            }
            throw new IllegalStateException();
        }
        return false;
    }

    public int getLength() {
        return this.length;
    }

    public int getFrameType() {
        return this.type;
    }

    public boolean hasFlag(int bit) {
        return (this.flags & bit) == bit;
    }

    public int getStreamId() {
        return this.streamId;
    }

    public String toString() {
        return String.format("[%s|%d|%d|%d]", new Object[]{FrameType.from(this.getFrameType()), this.getLength(), this.flags, this.getStreamId()});
    }

    private static enum State {
        LENGTH,
        TYPE,
        FLAGS,
        STREAM_ID,
        STREAM_ID_BYTES;

    }
}

