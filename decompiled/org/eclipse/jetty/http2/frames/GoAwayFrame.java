/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import java.nio.charset.StandardCharsets;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;

public class GoAwayFrame
extends Frame {
    public static final GoAwayFrame GRACEFUL = new GoAwayFrame(Integer.MAX_VALUE, ErrorCode.NO_ERROR.code, new byte[]{103, 114, 97, 99, 101, 102, 117, 108});
    private final int lastStreamId;
    private final int error;
    private final byte[] payload;

    public GoAwayFrame(int lastStreamId, int error, byte[] payload) {
        super(FrameType.GO_AWAY);
        this.lastStreamId = lastStreamId;
        this.error = error;
        this.payload = payload;
    }

    public boolean isGraceful() {
        return this.lastStreamId == Integer.MAX_VALUE;
    }

    public int getLastStreamId() {
        return this.lastStreamId;
    }

    public int getError() {
        return this.error;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public String tryConvertPayload() {
        if (this.payload == null || this.payload.length == 0) {
            return "";
        }
        try {
            return new String(this.payload, StandardCharsets.UTF_8);
        }
        catch (Throwable x) {
            return "";
        }
    }

    @Override
    public String toString() {
        return String.format("%s{%d/%s/%s}", super.toString(), this.lastStreamId, ErrorCode.toString(this.error, null), this.tryConvertPayload());
    }
}

