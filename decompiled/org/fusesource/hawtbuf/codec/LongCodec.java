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
public class LongCodec
implements Codec<Long> {
    public static final LongCodec INSTANCE = new LongCodec();

    @Override
    public void encode(Long object, DataOutput dataOut) throws IOException {
        dataOut.writeLong(object);
    }

    @Override
    public Long decode(DataInput dataIn) throws IOException {
        return dataIn.readLong();
    }

    @Override
    public int getFixedSize() {
        return 8;
    }

    @Override
    public Long deepCopy(Long source2) {
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
    public int estimatedSize(Long object) {
        return 8;
    }
}

