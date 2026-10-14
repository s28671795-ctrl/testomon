/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class BooleanStream {
    byte[] data = new byte[48];
    short arrayLimit;
    short arrayPos;
    byte bytePos;

    public boolean readBoolean() throws IOException {
        assert (this.arrayPos <= this.arrayLimit);
        byte b = this.data[this.arrayPos];
        boolean rc = (b >> this.bytePos & 1) != 0;
        this.bytePos = (byte)(this.bytePos + 1);
        if (this.bytePos >= 8) {
            this.bytePos = 0;
            this.arrayPos = (short)(this.arrayPos + 1);
        }
        return rc;
    }

    public void writeBoolean(boolean value) throws IOException {
        if (this.bytePos == 0) {
            this.arrayLimit = (short)(this.arrayLimit + 1);
            if (this.arrayLimit >= this.data.length) {
                byte[] d = new byte[this.data.length * 2];
                System.arraycopy(this.data, 0, d, 0, this.data.length);
                this.data = d;
            }
        }
        if (value) {
            short s2 = this.arrayPos;
            this.data[s2] = (byte)(this.data[s2] | 1 << this.bytePos);
        }
        this.bytePos = (byte)(this.bytePos + 1);
        if (this.bytePos >= 8) {
            this.bytePos = 0;
            this.arrayPos = (short)(this.arrayPos + 1);
        }
    }

    public void marshal(DataOutput dataOut) throws IOException {
        if (this.arrayLimit < 64) {
            dataOut.writeByte(this.arrayLimit);
        } else if (this.arrayLimit < 256) {
            dataOut.writeByte(192);
            dataOut.writeByte(this.arrayLimit);
        } else {
            dataOut.writeByte(128);
            dataOut.writeShort(this.arrayLimit);
        }
        dataOut.write(this.data, 0, this.arrayLimit);
        this.clear();
    }

    public void marshal(ByteBuffer dataOut) {
        if (this.arrayLimit < 64) {
            dataOut.put((byte)this.arrayLimit);
        } else if (this.arrayLimit < 256) {
            dataOut.put((byte)-64);
            dataOut.put((byte)this.arrayLimit);
        } else {
            dataOut.put((byte)-128);
            dataOut.putShort(this.arrayLimit);
        }
        dataOut.put(this.data, 0, this.arrayLimit);
    }

    public void unmarshal(DataInput dataIn) throws IOException {
        this.arrayLimit = (short)(dataIn.readByte() & 0xFF);
        if (this.arrayLimit == 192) {
            this.arrayLimit = (short)(dataIn.readByte() & 0xFF);
        } else if (this.arrayLimit == 128) {
            this.arrayLimit = dataIn.readShort();
        }
        if (this.data.length < this.arrayLimit) {
            this.data = new byte[this.arrayLimit];
        }
        dataIn.readFully(this.data, 0, this.arrayLimit);
        this.clear();
    }

    public void clear() {
        this.arrayPos = 0;
        this.bytePos = 0;
    }

    public int marshalledSize() {
        if (this.arrayLimit < 64) {
            return 1 + this.arrayLimit;
        }
        if (this.arrayLimit < 256) {
            return 2 + this.arrayLimit;
        }
        return 3 + this.arrayLimit;
    }
}

