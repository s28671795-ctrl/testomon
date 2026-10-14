/*
 * Decompiled with CFR 0.152.
 */
package org.xerial.snappy;

import java.io.OutputStream;
import org.xerial.snappy.SnappyOutputStream;
import org.xerial.snappy.buffer.CachedBufferAllocator;

public class SnappyHadoopCompatibleOutputStream
extends SnappyOutputStream {
    public SnappyHadoopCompatibleOutputStream(OutputStream outputStream2) {
        this(outputStream2, 32768);
    }

    public SnappyHadoopCompatibleOutputStream(OutputStream outputStream2, int n) {
        super(outputStream2, n, CachedBufferAllocator.getBufferAllocatorFactory());
    }

    @Override
    protected int writeHeader() {
        return 0;
    }

    @Override
    protected void writeBlockPreemble() {
        this.writeCurrentDataSize();
    }
}

