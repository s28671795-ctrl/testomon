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
public class BytesCodec
implements Codec<byte[]> {
    public static final BytesCodec INSTANCE = new BytesCodec();

    @Override
    public void encode(byte[] data, DataOutput dataOut) throws IOException {
        dataOut.writeInt(data.length);
        dataOut.write(data);
    }

    @Override
    public byte[] decode(DataInput dataIn) throws IOException {
        int size = dataIn.readInt();
        byte[] data = new byte[size];
        dataIn.readFully(data);
        return data;
    }

    @Override
    public int getFixedSize() {
        return -1;
    }

    @Override
    public byte[] deepCopy(byte[] source2) {
        byte[] rc = new byte[source2.length];
        System.arraycopy(source2, 0, rc, 0, source2.length);
        return rc;
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
    public int estimatedSize(byte[] object) {
        return object.length + 4;
    }
}

