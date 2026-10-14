/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.outputstream;

import java.io.IOException;
import java.io.OutputStream;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.outputstream.OutputStreamWithSplitZipSupport;
import net.lingala.zip4j.io.outputstream.SplitOutputStream;

public class CountingOutputStream
extends OutputStream
implements OutputStreamWithSplitZipSupport {
    private OutputStream outputStream;
    private long numberOfBytesWritten = 0L;

    public CountingOutputStream(OutputStream outputStream2) {
        this.outputStream = outputStream2;
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
        this.outputStream.write(b, off, len);
        this.numberOfBytesWritten += (long)len;
    }

    @Override
    public int getCurrentSplitFileCounter() {
        if (this.isSplitZipFile()) {
            return ((SplitOutputStream)this.outputStream).getCurrentSplitFileCounter();
        }
        return 0;
    }

    public long getOffsetForNextEntry() throws IOException {
        if (this.outputStream instanceof SplitOutputStream) {
            return ((SplitOutputStream)this.outputStream).getFilePointer();
        }
        return this.numberOfBytesWritten;
    }

    public long getSplitLength() {
        if (this.isSplitZipFile()) {
            return ((SplitOutputStream)this.outputStream).getSplitLength();
        }
        return 0L;
    }

    public boolean isSplitZipFile() {
        return this.outputStream instanceof SplitOutputStream && ((SplitOutputStream)this.outputStream).isSplitZipFile();
    }

    public long getNumberOfBytesWritten() throws IOException {
        if (this.outputStream instanceof SplitOutputStream) {
            return ((SplitOutputStream)this.outputStream).getFilePointer();
        }
        return this.numberOfBytesWritten;
    }

    public boolean checkBuffSizeAndStartNextSplitFile(int bufferSize) throws ZipException {
        if (!this.isSplitZipFile()) {
            return false;
        }
        return ((SplitOutputStream)this.outputStream).checkBufferSizeAndStartNextSplitFile(bufferSize);
    }

    @Override
    public long getFilePointer() throws IOException {
        if (this.outputStream instanceof SplitOutputStream) {
            return ((SplitOutputStream)this.outputStream).getFilePointer();
        }
        return this.numberOfBytesWritten;
    }

    @Override
    public void close() throws IOException {
        this.outputStream.close();
    }
}

