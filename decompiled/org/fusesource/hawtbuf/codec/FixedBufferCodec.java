/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf.codec;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.codec.Codec;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class FixedBufferCodec
implements Codec<Buffer> {
    private final int size;

    public FixedBufferCodec(int size) {
        this.size = size;
    }

    @Override
    public void encode(Buffer value, DataOutput dataOut) throws IOException {
        dataOut.write(value.data, value.offset, this.size);
    }

    @Override
    public Buffer decode(DataInput dataIn) throws IOException {
        byte[] data = new byte[this.size];
        dataIn.readFully(data);
        return new Buffer(data);
    }

    @Override
    public int getFixedSize() {
        return this.size;
    }

    @Override
    public Buffer deepCopy(Buffer source2) {
        return source2.deepCopy();
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
    public int estimatedSize(Buffer object) {
        return this.size;
    }
}

