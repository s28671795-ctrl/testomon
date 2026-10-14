/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf;

import java.io.IOException;
import java.net.ProtocolException;

public abstract class AbstractVarIntSupport {
    protected abstract byte readByte() throws IOException;

    protected abstract void writeByte(int var1) throws IOException;

    public int readVarInt() throws IOException {
        byte tmp = this.readByte();
        if (tmp >= 0) {
            return tmp;
        }
        int result = tmp & 0x7F;
        tmp = this.readByte();
        if (tmp >= 0) {
            result |= tmp << 7;
        } else {
            result |= (tmp & 0x7F) << 7;
            tmp = this.readByte();
            if (tmp >= 0) {
                result |= tmp << 14;
            } else {
                result |= (tmp & 0x7F) << 14;
                tmp = this.readByte();
                if (tmp >= 0) {
                    result |= tmp << 21;
                } else {
                    result |= (tmp & 0x7F) << 21;
                    tmp = this.readByte();
                    result |= tmp << 28;
                    if (tmp < 0) {
                        for (int i = 0; i < 5; ++i) {
                            if (this.readByte() < 0) continue;
                            return result;
                        }
                        throw new ProtocolException("Encountered a malformed variable int");
                    }
                }
            }
        }
        return result;
    }

    public long readVarLong() throws IOException {
        long result = 0L;
        for (int shift = 0; shift < 64; shift += 7) {
            byte b = this.readByte();
            result |= (long)(b & 0x7F) << shift;
            if ((b & 0x80) != 0) continue;
            return result;
        }
        throw new ProtocolException("Encountered a malformed variable int");
    }

    public int readVarSignedInt() throws IOException {
        return AbstractVarIntSupport.decodeZigZag32(this.readVarInt());
    }

    public long readVarSignedLong() throws IOException {
        return AbstractVarIntSupport.decodeZigZag64(this.readVarLong());
    }

    public void writeVarInt(int value) throws IOException {
        while (true) {
            if ((value & 0xFFFFFF80) == 0) {
                this.writeByte(value);
                return;
            }
            this.writeByte(value & 0x7F | 0x80);
            value >>>= 7;
        }
    }

    public void writeVarLong(long value) throws IOException {
        while (true) {
            if ((value & 0xFFFFFFFFFFFFFF80L) == 0L) {
                this.writeByte((int)value);
                return;
            }
            this.writeByte((int)value & 0x7F | 0x80);
            value >>>= 7;
        }
    }

    public void writeVarSignedInt(int value) throws IOException {
        this.writeVarInt(AbstractVarIntSupport.encodeZigZag32(value));
    }

    public void writeVarSignedLong(long value) throws IOException {
        this.writeVarLong(AbstractVarIntSupport.encodeZigZag64(value));
    }

    private static int decodeZigZag32(int n) {
        return n >>> 1 ^ -(n & 1);
    }

    private static long decodeZigZag64(long n) {
        return n >>> 1 ^ -(n & 1L);
    }

    private static int encodeZigZag32(int n) {
        return n << 1 ^ n >> 31;
    }

    private static long encodeZigZag64(long n) {
        return n << 1 ^ n >> 63;
    }

    public static int computeVarIntSize(int value) {
        if ((value & 0xFFFFFF80) == 0) {
            return 1;
        }
        if ((value & 0xFFFFC000) == 0) {
            return 2;
        }
        if ((value & 0xFFE00000) == 0) {
            return 3;
        }
        if ((value & 0xF0000000) == 0) {
            return 4;
        }
        return 5;
    }

    public static int computeVarLongSize(long value) {
        if ((value & 0xFFFFFFFFFFFFFF80L) == 0L) {
            return 1;
        }
        if ((value & 0xFFFFFFFFFFFFC000L) == 0L) {
            return 2;
        }
        if ((value & 0xFFFFFFFFFFE00000L) == 0L) {
            return 3;
        }
        if ((value & 0xFFFFFFFFF0000000L) == 0L) {
            return 4;
        }
        if ((value & 0xFFFFFFF800000000L) == 0L) {
            return 5;
        }
        if ((value & 0xFFFFFC0000000000L) == 0L) {
            return 6;
        }
        if ((value & 0xFFFE000000000000L) == 0L) {
            return 7;
        }
        if ((value & 0xFF00000000000000L) == 0L) {
            return 8;
        }
        if ((value & Long.MIN_VALUE) == 0L) {
            return 9;
        }
        return 10;
    }

    public static int computeVarSignedIntSize(int value) {
        return AbstractVarIntSupport.computeVarIntSize(AbstractVarIntSupport.encodeZigZag32(value));
    }

    public static int computeVarSignedLongSize(long value) {
        return AbstractVarIntSupport.computeVarLongSize(AbstractVarIntSupport.encodeZigZag64(value));
    }
}

