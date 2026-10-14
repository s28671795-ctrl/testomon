/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.websocket.core.OpCode;

public class Frame {
    public static final int MAX_CONTROL_PAYLOAD = 125;
    protected byte finRsvOp;
    protected byte[] mask;
    protected ByteBuffer payload;

    public static Frame copyWithoutPayload(Frame original) {
        Frame copy = new Frame(original.getOpCode());
        copy.copyHeaders(original);
        return copy;
    }

    public static Frame copy(Frame original) {
        Frame copy = Frame.copyWithoutPayload(original);
        copy.setPayload(BufferUtil.copy(original.getPayload()));
        return copy;
    }

    public Frame(byte opcode) {
        this((byte)(0x80 | opcode & 0xF), null, null);
    }

    public Frame(byte opCode, ByteBuffer payload) {
        this(opCode);
        this.setPayload(payload);
    }

    public Frame(byte opCode, String payload) {
        this(opCode);
        this.setPayload(payload);
    }

    public Frame(byte opCode, boolean fin, ByteBuffer payload) {
        this(opCode, payload);
        this.setFin(fin);
    }

    public Frame(byte opCode, boolean fin, String payload) {
        this(opCode, payload);
        this.setFin(fin);
    }

    protected Frame() {
        this(-1, null, null);
    }

    public Frame(byte finRsvOp, byte[] mask, ByteBuffer payload) {
        this.finRsvOp = finRsvOp;
        this.mask = mask;
        this.payload = payload;
    }

    public boolean isControlFrame() {
        return OpCode.isControlFrame(this.getOpCode());
    }

    public boolean isDataFrame() {
        return OpCode.isDataFrame(this.getOpCode());
    }

    protected void copyHeaders(Frame frame) {
        byte opCode = (byte)(this.finRsvOp & 0xF);
        this.finRsvOp = 0;
        this.finRsvOp = (byte)(this.finRsvOp | (frame.isFin() ? 128 : 0));
        this.finRsvOp = (byte)(this.finRsvOp | (frame.isRsv1() ? 64 : 0));
        this.finRsvOp = (byte)(this.finRsvOp | (frame.isRsv2() ? 32 : 0));
        this.finRsvOp = (byte)(this.finRsvOp | (frame.isRsv3() ? 16 : 0));
        this.finRsvOp = (byte)(this.finRsvOp | opCode);
        this.mask = (byte[])(frame.isMasked() ? Arrays.copyOf(frame.getMask(), frame.getMask().length) : null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Frame other = (Frame)obj;
        if (this.payload == null ? other.payload != null : !this.payload.equals(other.payload)) {
            return false;
        }
        if (this.finRsvOp != other.finRsvOp) {
            return false;
        }
        return Arrays.equals(this.mask, other.mask);
    }

    public byte[] getMask() {
        return this.mask;
    }

    public byte getOpCode() {
        return OpCode.getOpCode(this.finRsvOp);
    }

    public ByteBuffer getPayload() {
        if (this.payload == null) {
            return BufferUtil.EMPTY_BUFFER;
        }
        return this.payload;
    }

    public String getPayloadAsUTF8() {
        if (this.payload == null) {
            return "";
        }
        return BufferUtil.toUTF8String(this.payload);
    }

    public int getPayloadLength() {
        if (this.payload == null) {
            return 0;
        }
        return this.payload.remaining();
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.payload == null ? 0 : this.payload.hashCode());
        result = 31 * result + this.finRsvOp;
        result = 31 * result + Arrays.hashCode(this.mask);
        return result;
    }

    public boolean hasPayload() {
        return this.getPayload().remaining() > 0;
    }

    public boolean isFin() {
        return (byte)(this.finRsvOp & 0x80) != 0;
    }

    public boolean isMasked() {
        return this.mask != null;
    }

    public boolean isRsv1() {
        return (byte)(this.finRsvOp & 0x40) != 0;
    }

    public boolean isRsv2() {
        return (byte)(this.finRsvOp & 0x20) != 0;
    }

    public boolean isRsv3() {
        return (byte)(this.finRsvOp & 0x10) != 0;
    }

    public void reset() {
        this.finRsvOp = (byte)-128;
        this.payload = null;
        this.mask = null;
    }

    public Frame setFin(boolean fin) {
        this.finRsvOp = (byte)(this.finRsvOp & 0x7F | (fin ? 128 : 0));
        return this;
    }

    public Frame setMask(byte[] maskingKey) {
        this.mask = maskingKey;
        return this;
    }

    protected Frame setOpCode(byte op) {
        this.finRsvOp = (byte)(this.finRsvOp & 0xF0 | op & 0xF);
        return this;
    }

    public Frame setPayload(ByteBuffer buf) {
        this.payload = buf;
        return this;
    }

    public Frame setPayload(String str) {
        this.setPayload(ByteBuffer.wrap(StringUtil.getUtf8Bytes(str)));
        return this;
    }

    public Frame setPayload(byte[] buf) {
        this.setPayload(ByteBuffer.wrap(buf));
        return this;
    }

    public Frame setRsv1(boolean rsv1) {
        this.finRsvOp = (byte)(this.finRsvOp & 0xBF | (rsv1 ? 64 : 0));
        return this;
    }

    public Frame setRsv2(boolean rsv2) {
        this.finRsvOp = (byte)(this.finRsvOp & 0xDF | (rsv2 ? 32 : 0));
        return this;
    }

    public Frame setRsv3(boolean rsv3) {
        this.finRsvOp = (byte)(this.finRsvOp & 0xEF | (rsv3 ? 16 : 0));
        return this;
    }

    public Frame asReadOnly() {
        return new ReadOnly(this);
    }

    public boolean hasRsv() {
        return (this.finRsvOp & 0x70) != 0;
    }

    public void demask() {
        if (this.isMasked() && this.hasPayload()) {
            int remaining;
            int maskInt = 0;
            for (byte maskByte : this.mask) {
                maskInt = (maskInt << 8) + (maskByte & 0xFF);
            }
            int maskOffset = 0;
            int start2 = this.payload.position();
            int end = this.payload.limit();
            int offset = maskOffset;
            while ((remaining = end - start2) > 0) {
                if (remaining >= 4 && (offset & 3) == 0) {
                    this.payload.putInt(start2, this.payload.getInt(start2) ^ maskInt);
                    start2 += 4;
                    offset += 4;
                    continue;
                }
                this.payload.put(start2, (byte)(this.payload.get(start2) ^ this.mask[offset & 3]));
                ++start2;
                ++offset;
            }
            Arrays.fill(this.mask, (byte)0);
        }
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(OpCode.name(OpCode.getOpCode(this.finRsvOp)));
        b.append('@');
        b.append(Integer.toHexString(super.hashCode()));
        b.append('[');
        b.append("len=").append(this.getPayloadLength());
        b.append(",fin=").append((this.finRsvOp & 0x80) != 0);
        b.append(",rsv=");
        b.append((this.finRsvOp & 0x40) != 0 ? (char)'1' : '0');
        b.append((this.finRsvOp & 0x20) != 0 ? (char)'1' : '0');
        b.append((this.finRsvOp & 0x10) != 0 ? (char)'1' : '0');
        b.append(",m=").append(this.mask == null ? "null" : TypeUtil.toHexString(this.mask));
        b.append(']');
        if (this.payload != null) {
            b.append(BufferUtil.toDetailString(this.payload));
        }
        return b.toString();
    }

    private static class ReadOnly
    extends Frame {
        private ReadOnly(Frame frame) {
            super(frame.finRsvOp, frame.isMasked() ? frame.getMask() : null, frame.getPayload());
        }

        @Override
        public ByteBuffer getPayload() {
            ByteBuffer buffer = super.getPayload();
            if (buffer == null) {
                return null;
            }
            return buffer.asReadOnlyBuffer();
        }

        @Override
        protected void copyHeaders(Frame frame) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void reset() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Frame setFin(boolean fin) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Frame setMask(byte[] maskingKey) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected Frame setOpCode(byte op) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Frame setPayload(ByteBuffer buf) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Frame setPayload(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Frame setPayload(byte[] buf) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Frame setRsv1(boolean rsv1) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Frame setRsv2(boolean rsv2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Frame setRsv3(boolean rsv3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Frame asReadOnly() {
            return this;
        }
    }
}

