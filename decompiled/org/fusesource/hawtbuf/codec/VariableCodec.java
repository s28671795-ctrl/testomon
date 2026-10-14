/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf.codec;

import org.fusesource.hawtbuf.codec.Codec;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class VariableCodec<T>
implements Codec<T> {
    @Override
    public int getFixedSize() {
        return -1;
    }

    @Override
    public boolean isDeepCopySupported() {
        return false;
    }

    @Override
    public T deepCopy(T source2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEstimatedSizeSupported() {
        return false;
    }

    @Override
    public int estimatedSize(T object) {
        throw new UnsupportedOperationException();
    }
}

