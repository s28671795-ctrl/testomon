/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf.codec;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.codec.VariableCodec;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class AbstractBufferCodec<T extends Buffer>
extends VariableCodec<T> {
    @Override
    public void encode(T value, DataOutput dataOut) throws IOException {
        dataOut.writeInt(((Buffer)value).length);
        dataOut.write(((Buffer)value).data, ((Buffer)value).offset, ((Buffer)value).length);
    }

    @Override
    public T decode(DataInput dataIn) throws IOException {
        int size = dataIn.readInt();
        byte[] data = new byte[size];
        dataIn.readFully(data);
        return this.createBuffer(data);
    }

    protected abstract T createBuffer(byte[] var1);

    @Override
    public T deepCopy(T source2) {
        return this.createBuffer(((Buffer)source2).deepCopy().data);
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
    public int estimatedSize(T object) {
        return ((Buffer)object).length + 4;
    }
}

