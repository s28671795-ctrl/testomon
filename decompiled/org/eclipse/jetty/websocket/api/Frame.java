/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

import java.nio.ByteBuffer;

public interface Frame {
    public byte[] getMask();

    public byte getOpCode();

    public ByteBuffer getPayload();

    public int getPayloadLength();

    public Type getType();

    public boolean hasPayload();

    public boolean isFin();

    public boolean isMasked();

    public boolean isRsv1();

    public boolean isRsv2();

    public boolean isRsv3();

    public static enum Type {
        CONTINUATION(0),
        TEXT(1),
        BINARY(2),
        CLOSE(8),
        PING(9),
        PONG(10);

        private final byte opcode;

        public static Type from(byte op) {
            for (Type type : Type.values()) {
                if (type.opcode != op) continue;
                return type;
            }
            throw new IllegalArgumentException("OpCode " + op + " is not a valid Frame.Type");
        }

        private Type(byte code) {
            this.opcode = code;
        }

        public byte getOpCode() {
            return this.opcode;
        }

        public boolean isControl() {
            return this.opcode >= CLOSE.getOpCode();
        }

        public boolean isData() {
            return this.opcode == TEXT.getOpCode() || this.opcode == BINARY.getOpCode();
        }

        public boolean isContinuation() {
            return this.opcode == CONTINUATION.getOpCode();
        }

        public String toString() {
            return this.name();
        }
    }
}

