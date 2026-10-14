/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf.codec;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.ProtocolException;
import org.fusesource.hawtbuf.codec.Codec;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class VarIntegerCodec
implements Codec<Integer> {
    public static final VarIntegerCodec INSTANCE = new VarIntegerCodec();

    @Override
    public void encode(Integer x, DataOutput dataOut) throws IOException {
        int value = x;
        while (true) {
            if ((value & 0xFFFFFF80) == 0) {
                dataOut.writeByte(value);
                return;
            }
            dataOut.writeByte(value & 0x7F | 0x80);
            value >>>= 7;
        }
    }

    @Override
    public Integer decode(DataInput dataIn) throws IOException {
        byte tmp = dataIn.readByte();
        if (tmp >= 0) {
            return tmp;
        }
        int result = tmp & 0x7F;
        tmp = dataIn.readByte();
        if (tmp >= 0) {
            result |= tmp << 7;
        } else {
            result |= (tmp & 0x7F) << 7;
            tmp = dataIn.readByte();
            if (tmp >= 0) {
                result |= tmp << 14;
            } else {
                result |= (tmp & 0x7F) << 14;
                tmp = dataIn.readByte();
                if (tmp >= 0) {
                    result |= tmp << 21;
                } else {
                    result |= (tmp & 0x7F) << 21;
                    tmp = dataIn.readByte();
                    result |= tmp << 28;
                    if (tmp < 0) {
                        for (int i = 0; i < 5; ++i) {
                            if (dataIn.readByte() < 0) continue;
                            return result;
                        }
                        throw new ProtocolException("Encountered a malformed variable int");
                    }
                }
            }
        }
        return result;
    }

    @Override
    public int getFixedSize() {
        return -1;
    }

    @Override
    public Integer deepCopy(Integer source2) {
        return source2;
    }

    @Override
    public boolean isDeepCopySupported() {
        return true;
    }

    @Override
    public boolean isEstimatedSizeSupported() {
        return true;
    }

    @Override
    public int estimatedSize(Integer x) {
        int value = x;
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
}

