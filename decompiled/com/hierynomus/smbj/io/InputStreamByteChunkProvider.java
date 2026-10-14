/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.io;

import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.protocol.commons.buffer.Endian;
import com.hierynomus.smbj.common.SMBRuntimeException;
import com.hierynomus.smbj.io.BufferByteChunkProvider;
import com.hierynomus.smbj.io.ByteChunkProvider;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamByteChunkProvider
extends ByteChunkProvider {
    private BufferedInputStream is;
    private BufferByteChunkProvider cachingProvider;
    private Buffer<Buffer.PlainBuffer> buffer = new Buffer.PlainBuffer(Endian.BE);

    public InputStreamByteChunkProvider(InputStream is) {
        this.cachingProvider = new BufferByteChunkProvider(this.buffer);
        this.is = is instanceof BufferedInputStream ? (BufferedInputStream)is : new BufferedInputStream(is);
    }

    @Override
    public void prepareWrite(int maxBytesToPrepare) {
        if (this.is == null) {
            return;
        }
        byte[] chunk = new byte[1024];
        this.buffer.compact();
        try {
            int read;
            for (int bytesNeeded = maxBytesToPrepare - this.buffer.available(); bytesNeeded > 0 && (read = this.is.read(chunk, 0, chunk.length)) != -1; bytesNeeded -= read) {
                this.buffer.putRawBytes(chunk, 0, read);
            }
        }
        catch (IOException e) {
            throw new SMBRuntimeException(e);
        }
    }

    @Override
    protected int getChunk(byte[] chunk) throws IOException {
        return this.cachingProvider.getChunk(chunk);
    }

    @Override
    public int bytesLeft() {
        return this.cachingProvider.bytesLeft();
    }

    @Override
    public boolean isAvailable() {
        try {
            return this.cachingProvider.isAvailable() || this.is.available() > 0;
        }
        catch (IOException e) {
            throw new SMBRuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        this.cachingProvider.close();
        if (this.is != null) {
            try {
                this.is.close();
            }
            finally {
                this.is = null;
            }
        }
    }
}

