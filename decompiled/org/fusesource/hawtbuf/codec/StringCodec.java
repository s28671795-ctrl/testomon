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
public class StringCodec
implements Codec<String> {
    public static final StringCodec INSTANCE = new StringCodec();

    @Override
    public void encode(String object, DataOutput dataOut) throws IOException {
        dataOut.writeUTF(object);
    }

    @Override
    public String decode(DataInput dataIn) throws IOException {
        return dataIn.readUTF();
    }

    @Override
    public int getFixedSize() {
        return -1;
    }

    @Override
    public String deepCopy(String source2) {
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
    public int estimatedSize(String object) {
        return object.length() + 2;
    }
}

