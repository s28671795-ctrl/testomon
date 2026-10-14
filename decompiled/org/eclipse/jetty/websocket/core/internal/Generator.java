/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.nio.ByteBuffer;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.websocket.core.Frame;

public class Generator {
    public static final int MAX_HEADER_LENGTH = 28;

    public void generateWholeFrame(Frame frame, ByteBuffer buffer) {
        this.generateHeader(frame, buffer);
        this.generatePayload(frame, buffer);
    }

    public void generateHeader(Frame frame, ByteBuffer buffer) {
        int pos = BufferUtil.flipToFill(buffer);
        byte b = 0;
        if (frame.isFin()) {
            b = (byte)(b | 0x80);
        }
        if (frame.isRsv1()) {
            b = (byte)(b | 0x40);
        }
        if (frame.isRsv2()) {
            b = (byte)(b | 0x20);
        }
        if (frame.isRsv3()) {
            b = (byte)(b | 0x10);
        }
        byte opcode = frame.getOpCode();
        b = (byte)(b | opcode & 0xF);
        buffer.put(b);
        b = frame.isMasked() ? (byte)-128 : 0;
        int payloadLength = frame.getPayloadLength();
        if (payloadLength > 65535) {
            b = (byte)(b | 0x7F);
            buffer.put(b);
            buffer.put((byte)0);
            buffer.put((byte)0);
            buffer.put((byte)0);
            buffer.put((byte)0);
            buffer.put((byte)(payloadLength >> 24 & 0xFF));
            buffer.put((byte)(payloadLength >> 16 & 0xFF));
            buffer.put((byte)(payloadLength >> 8 & 0xFF));
            buffer.put((byte)(payloadLength & 0xFF));
        } else if (payloadLength >= 126) {
            b = (byte)(b | 0x7E);
            buffer.put(b);
            buffer.put((byte)(payloadLength >> 8));
            buffer.put((byte)(payloadLength & 0xFF));
        } else {
            b = (byte)(b | payloadLength & 0x7F);
            buffer.put(b);
        }
        if (frame.isMasked()) {
            buffer.put(frame.getMask());
        }
        BufferUtil.flipToFlush(buffer, pos);
    }

    public void generatePayload(Frame frame, ByteBuffer buffer) {
        ByteBuffer payload = frame.getPayload();
        if (!BufferUtil.hasContent(payload)) {
            return;
        }
        int pos = BufferUtil.flipToFill(buffer);
        if (frame.isMasked()) {
            this.maskPayload(buffer, frame);
        } else {
            buffer.put(payload.slice());
        }
        BufferUtil.flipToFlush(buffer, pos);
    }

    private void maskPayload(ByteBuffer buffer, Frame frame) {
        byte[] mask = frame.getMask();
        int maskInt = 0;
        for (byte maskByte : mask) {
            maskInt = (maskInt << 8) + (maskByte & 0xFF);
        }
        ByteBuffer payload = frame.getPayload();
        if (payload != null && payload.remaining() > 0) {
            int remaining;
            int maskOffset = 0;
            int start2 = payload.position();
            int end = payload.limit();
            while ((remaining = end - start2) > 0) {
                if (remaining >= 4) {
                    buffer.putInt(payload.getInt(start2) ^ maskInt);
                    start2 += 4;
                    continue;
                }
                buffer.put((byte)(payload.get(start2) ^ mask[maskOffset & 3]));
                ++start2;
                ++maskOffset;
            }
        }
    }
}

