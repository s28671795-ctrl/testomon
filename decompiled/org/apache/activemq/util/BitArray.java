/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BitArray
implements Serializable {
    private static final long serialVersionUID = 1L;
    static final int LONG_SIZE = 64;
    static final int INT_SIZE = 32;
    static final int SHORT_SIZE = 16;
    static final int BYTE_SIZE = 8;
    private static final long[] BIT_VALUES = new long[]{1L, 2L, 4L, 8L, 16L, 32L, 64L, 128L, 256L, 512L, 1024L, 2048L, 4096L, 8192L, 16384L, 32768L, 65536L, 131072L, 262144L, 524288L, 0x100000L, 0x200000L, 0x400000L, 0x800000L, 0x1000000L, 0x2000000L, 0x4000000L, 0x8000000L, 0x10000000L, 0x20000000L, 0x40000000L, 0x80000000L, 0x100000000L, 0x200000000L, 0x400000000L, 0x800000000L, 0x1000000000L, 0x2000000000L, 0x4000000000L, 0x8000000000L, 0x10000000000L, 0x20000000000L, 0x40000000000L, 0x80000000000L, 0x100000000000L, 0x200000000000L, 0x400000000000L, 0x800000000000L, 0x1000000000000L, 0x2000000000000L, 0x4000000000000L, 0x8000000000000L, 0x10000000000000L, 0x20000000000000L, 0x40000000000000L, 0x80000000000000L, 0x100000000000000L, 0x200000000000000L, 0x400000000000000L, 0x800000000000000L, 0x1000000000000000L, 0x2000000000000000L, 0x4000000000000000L, Long.MIN_VALUE};
    private long bits;
    private int length;

    public int length() {
        return this.length;
    }

    public long getBits() {
        return this.bits;
    }

    public boolean set(int index, boolean flag) {
        boolean oldValue;
        this.length = Math.max(this.length, index + 1);
        boolean bl = oldValue = (this.bits & BIT_VALUES[index]) != 0L;
        if (flag) {
            this.bits |= BIT_VALUES[index];
        } else if (oldValue) {
            this.bits &= BIT_VALUES[index] ^ 0xFFFFFFFFFFFFFFFFL;
        }
        return oldValue;
    }

    public boolean get(int index) {
        return (this.bits & BIT_VALUES[index]) != 0L;
    }

    public void reset() {
        this.bits = 0L;
    }

    public void reset(long bits) {
        this.bits = bits;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        this.writeToStream(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.readFromStream(in);
    }

    public void writeToStream(DataOutput dataOut) throws IOException {
        dataOut.writeByte(this.length);
        if (this.length <= 8) {
            dataOut.writeByte((byte)this.bits);
        } else if (this.length <= 16) {
            dataOut.writeShort((short)this.bits);
        } else if (this.length <= 32) {
            dataOut.writeInt((int)this.bits);
        } else {
            dataOut.writeLong(this.bits);
        }
    }

    public void readFromStream(DataInput dataIn) throws IOException {
        this.length = dataIn.readByte();
        this.bits = this.length <= 8 ? (long)dataIn.readByte() : (this.length <= 16 ? (long)dataIn.readShort() : (this.length <= 32 ? (long)dataIn.readInt() : dataIn.readLong()));
    }
}

