/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf.codec;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.fusesource.hawtbuf.codec.VarLongCodec;

public class VarSignedLongCodec
extends VarLongCodec {
    public static final VarSignedLongCodec INSTANCE = new VarSignedLongCodec();

    public void encode(Long value, DataOutput dataOut) throws IOException {
        super.encode(VarSignedLongCodec.encodeZigZag(value), dataOut);
    }

    public Long decode(DataInput dataIn) throws IOException {
        return VarSignedLongCodec.decodeZigZag(super.decode(dataIn));
    }

    private static long decodeZigZag(long n) {
        return n >>> 1 ^ -(n & 1L);
    }

    private static long encodeZigZag(long n) {
        return n << 1 ^ n >> 63;
    }

    public int estimatedSize(Long value) {
        return super.estimatedSize(VarSignedLongCodec.encodeZigZag(value));
    }
}

