/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf.codec;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.fusesource.hawtbuf.codec.Codec;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class IntegerCodec
implements Codec<Integer> {
    public static final IntegerCodec INSTANCE = new IntegerCodec();

    @Override
    public void encode(Integer object, DataOutput dataOut) throws IOException {
        dataOut.writeInt(object);
    }

    @Override
    public Integer decode(DataInput dataIn) throws IOException {
        return dataIn.readInt();
    }

    @Override
    public int getFixedSize() {
        return 4;
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
    public int estimatedSize(Integer object) {
        return 4;
    }
}

