/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.outputstream;

import java.io.IOException;
import java.io.OutputStream;

class ZipEntryOutputStream
extends OutputStream {
    private long numberOfBytesWrittenForThisEntry = 0L;
    private OutputStream outputStream;
    private boolean entryClosed;

    public ZipEntryOutputStream(OutputStream outputStream2) {
        this.outputStream = outputStream2;
        this.entryClosed = false;
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
        if (this.entryClosed) {
            throw new IllegalStateException("ZipEntryOutputStream is closed");
        }
        this.outputStream.write(b, off, len);
        this.numberOfBytesWrittenForThisEntry += (long)len;
    }

    public void closeEntry() throws IOException {
        this.entryClosed = true;
    }

    public long getNumberOfBytesWrittenForThisEntry() {
        return this.numberOfBytesWrittenForThisEntry;
    }

    @Override
    public void close() throws IOException {
    }
}

