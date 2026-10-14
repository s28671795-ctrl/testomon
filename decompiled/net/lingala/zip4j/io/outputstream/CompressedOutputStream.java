/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.outputstream;

import java.io.IOException;
import java.io.OutputStream;
import net.lingala.zip4j.io.outputstream.CipherOutputStream;

abstract class CompressedOutputStream
extends OutputStream {
    private CipherOutputStream<?> cipherOutputStream;

    public CompressedOutputStream(CipherOutputStream<?> cipherOutputStream) {
        this.cipherOutputStream = cipherOutputStream;
    }

    @Override
    public void write(int b) throws IOException {
        this.write(new byte[]{(byte)b});
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        this.cipherOutputStream.write(b, off, len);
    }

    protected void closeEntry() throws IOException {
        this.cipherOutputStream.closeEntry();
    }

    @Override
    public void close() throws IOException {
        this.cipherOutputStream.close();
    }

    public long getCompressedSize() {
        return this.cipherOutputStream.getNumberOfBytesWrittenForThisEntry();
    }
}

