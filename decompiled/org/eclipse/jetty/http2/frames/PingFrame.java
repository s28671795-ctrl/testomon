/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import java.util.Objects;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;

public class PingFrame
extends Frame {
    public static final int PING_LENGTH = 8;
    private static final byte[] EMPTY_PAYLOAD = new byte[8];
    private final byte[] payload;
    private final boolean reply;

    public PingFrame(boolean reply) {
        this(EMPTY_PAYLOAD, reply);
    }

    public PingFrame(long value, boolean reply) {
        this(PingFrame.toBytes(value), reply);
    }

    public PingFrame(byte[] payload, boolean reply) {
        super(FrameType.PING);
        this.payload = Objects.requireNonNull(payload);
        if (payload.length != 8) {
            throw new IllegalArgumentException("PING payload must be 8 bytes");
        }
        this.reply = reply;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public long getPayloadAsLong() {
        return PingFrame.toLong(this.payload);
    }

    public boolean isReply() {
        return this.reply;
    }

    private static byte[] toBytes(long value) {
        byte[] result = new byte[8];
        for (int i = result.length - 1; i >= 0; --i) {
            result[i] = (byte)(value & 0xFFL);
            value >>= 8;
        }
        return result;
    }

    private static long toLong(byte[] payload) {
        long result = 0L;
        for (int i = 0; i < 8; ++i) {
            result <<= 8;
            result |= (long)(payload[i] & 0xFF);
        }
        return result;
    }
}

