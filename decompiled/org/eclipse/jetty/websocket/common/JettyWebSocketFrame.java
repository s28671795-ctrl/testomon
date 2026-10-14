/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.common;

import java.nio.ByteBuffer;
import org.eclipse.jetty.websocket.api.Frame;

public class JettyWebSocketFrame
implements Frame {
    private final org.eclipse.jetty.websocket.core.Frame frame;

    public JettyWebSocketFrame(org.eclipse.jetty.websocket.core.Frame frame) {
        this.frame = frame;
    }

    @Override
    public byte[] getMask() {
        return this.frame.getMask();
    }

    @Override
    public byte getOpCode() {
        return this.frame.getOpCode();
    }

    @Override
    public ByteBuffer getPayload() {
        return this.frame.getPayload().asReadOnlyBuffer();
    }

    @Override
    public int getPayloadLength() {
        return this.frame.getPayloadLength();
    }

    @Override
    public Frame.Type getType() {
        return Frame.Type.from(this.getOpCode());
    }

    @Override
    public boolean hasPayload() {
        return this.frame.hasPayload();
    }

    @Override
    public boolean isFin() {
        return this.frame.isFin();
    }

    @Override
    public boolean isMasked() {
        return this.frame.isMasked();
    }

    @Override
    public boolean isRsv1() {
        return this.frame.isRsv1();
    }

    @Override
    public boolean isRsv2() {
        return this.frame.isRsv2();
    }

    @Override
    public boolean isRsv3() {
        return this.frame.isRsv3();
    }

    public String toString() {
        return this.frame.toString();
    }
}

