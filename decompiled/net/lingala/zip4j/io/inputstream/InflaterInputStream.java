/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.inputstream;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import net.lingala.zip4j.io.inputstream.CipherInputStream;
import net.lingala.zip4j.io.inputstream.DecompressedInputStream;

public class InflaterInputStream
extends DecompressedInputStream {
    private Inflater inflater;
    private byte[] buff;
    private byte[] singleByteBuffer = new byte[1];
    private int len;

    public InflaterInputStream(CipherInputStream<?> cipherInputStream, int bufferSize) {
        super(cipherInputStream);
        this.inflater = new Inflater(true);
        this.buff = new byte[bufferSize];
    }

    @Override
    public int read() throws IOException {
        int readLen = this.read(this.singleByteBuffer);
        if (readLen == -1) {
            return -1;
        }
        return this.singleByteBuffer[0];
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        try {
            int n;
            while ((n = this.inflater.inflate(b, off, len)) == 0) {
                if (this.inflater.finished() || this.inflater.needsDictionary()) {
                    return -1;
                }
                if (!this.inflater.needsInput()) continue;
                this.fill();
            }
            return n;
        }
        catch (DataFormatException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void endOfEntryReached(InputStream inputStream2, int numberOfBytesPushedBack) throws IOException {
        if (this.inflater != null) {
            this.inflater.end();
            this.inflater = null;
        }
        super.endOfEntryReached(inputStream2, numberOfBytesPushedBack);
    }

    @Override
    public int pushBackInputStreamIfNecessary(PushbackInputStream pushbackInputStream) throws IOException {
        int n = this.inflater.getRemaining();
        if (n > 0) {
            byte[] rawDataCache = this.getLastReadRawDataCache();
            pushbackInputStream.unread(rawDataCache, this.len - n, n);
        }
        return n;
    }

    @Override
    public void close() throws IOException {
        if (this.inflater != null) {
            this.inflater.end();
        }
        super.close();
    }

    private void fill() throws IOException {
        this.len = super.read(this.buff, 0, this.buff.length);
        if (this.len == -1) {
            throw new EOFException("Unexpected end of input stream");
        }
        this.inflater.setInput(this.buff, 0, this.len);
    }
}

