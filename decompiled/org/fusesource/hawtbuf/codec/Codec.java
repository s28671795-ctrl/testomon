/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf.codec;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface Codec<T> {
    public void encode(T var1, DataOutput var2) throws IOException;

    public T decode(DataInput var1) throws IOException;

    public int getFixedSize();

    public boolean isEstimatedSizeSupported();

    public int estimatedSize(T var1);

    public boolean isDeepCopySupported();

    public T deepCopy(T var1);
}

