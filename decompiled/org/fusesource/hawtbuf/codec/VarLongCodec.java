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
public class VarLongCodec
implements Codec<Long> {
    public static final VarLongCodec INSTANCE = new VarLongCodec();

    @Override
    public void encode(Long object, DataOutput dataOut) throws IOException {
        long value = object;
        while (true) {
            if ((value & 0xFFFFFFFFFFFFFF80L) == 0L) {
                dataOut.writeByte((int)value);
                return;
            }
            dataOut.writeByte((int)value & 0x7F | 0x80);
            value >>>= 7;
        }
    }

    @Override
    public Long decode(DataInput dataIn) throws IOException {
        long result = 0L;
        for (int shift = 0; shift < 64; shift += 7) {
            byte b = dataIn.readByte();
            result |= (long)(b & 0x7F) << shift;
            if ((b & 0x80) != 0) continue;
            return result;
        }
        throw new ProtocolException("Encountered a malformed variable int");
    }

    @Override
    public boolean isEstimatedSizeSupported() {
        return true;
    }

    @Override
    public int estimatedSize(Long object) {
        long value = object;
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

    @Override
    public int getFixedSize() {
        return -1;
    }

    @Override
    public Long deepCopy(Long source2) {
        return source2;
    }

    @Override
    public boolean isDeepCopySupported() {
        return true;
    }
}

