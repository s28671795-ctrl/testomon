/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.inputstream;

import java.io.IOException;
import java.io.InputStream;

class ZipEntryInputStream
extends InputStream {
    private static final int MAX_RAW_READ_FULLY_RETRY_ATTEMPTS = 15;
    private InputStream inputStream;
    private long numberOfBytesRead = 0L;
    private byte[] singleByteArray = new byte[1];
    private long compressedSize;

    public ZipEntryInputStream(InputStream inputStream2, long compressedSize) {
        this.inputStream = inputStream2;
        this.compressedSize = compressedSize;
    }

    @Override
    public int read() throws IOException {
        int readLen = this.read(this.singleByteArray);
        if (readLen == -1) {
            return -1;
        }
        return this.singleByteArray[0];
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int readLen;
        if (this.compressedSize != -1L) {
            if (this.numberOfBytesRead >= this.compressedSize) {
                return -1;
            }
            if ((long)len > this.compressedSize - this.numberOfBytesRead) {
                len = (int)(this.compressedSize - this.numberOfBytesRead);
            }
        }
        if ((readLen = this.inputStream.read(b, off, len)) > 0) {
            this.numberOfBytesRead += (long)readLen;
        }
        return readLen;
    }

    public int readRawFully(byte[] b) throws IOException {
        int readLen = this.inputStream.read(b);
        if (readLen == -1) {
            throw new IOException("Unexpected EOF reached when trying to read stream");
        }
        if (readLen != b.length && (readLen = this.readUntilBufferIsFull(b, readLen)) != b.length) {
            throw new IOException("Cannot read fully into byte buffer");
        }
        return readLen;
    }

    private int readUntilBufferIsFull(byte[] b, int readLength) throws IOException {
        int remainingLength = b.length - readLength;
        int loopReadLength = 0;
        for (int retryAttempt = 0; readLength < b.length && loopReadLength != -1 && retryAttempt < 15; ++retryAttempt) {
            if ((loopReadLength += this.inputStream.read(b, readLength, remainingLength)) <= 0) continue;
            readLength += loopReadLength;
            remainingLength -= loopReadLength;
        }
        return readLength;
    }

    @Override
    public void close() throws IOException {
        this.inputStream.close();
    }

    public long getNumberOfBytesRead() {
        return this.numberOfBytesRead;
    }
}

