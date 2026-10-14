/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

import java.nio.ByteBuffer;
import org.jvnet.mimepull.Data;
import org.jvnet.mimepull.DataFile;
import org.jvnet.mimepull.DataHead;

final class FileData
implements Data {
    private final DataFile file;
    private final long pointer;
    private final int length;

    FileData(DataFile file, ByteBuffer buf) {
        this(file, file.writeTo(buf.array(), 0, buf.limit()), buf.limit());
    }

    FileData(DataFile file, long pointer, int length) {
        this.file = file;
        this.pointer = pointer;
        this.length = length;
    }

    @Override
    public byte[] read() {
        byte[] buf = new byte[this.length];
        this.file.read(this.pointer, buf, 0, this.length);
        return buf;
    }

    @Override
    public long writeTo(DataFile file) {
        throw new IllegalStateException();
    }

    @Override
    public int size() {
        return this.length;
    }

    @Override
    public Data createNext(DataHead dataHead, ByteBuffer buf) {
        return new FileData(this.file, buf);
    }
}

