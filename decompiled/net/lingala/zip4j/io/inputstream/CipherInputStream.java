/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.inputstream;

import java.io.IOException;
import java.io.InputStream;
import net.lingala.zip4j.crypto.Decrypter;
import net.lingala.zip4j.io.inputstream.ZipEntryInputStream;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.util.Zip4jUtil;

abstract class CipherInputStream<T extends Decrypter>
extends InputStream {
    private ZipEntryInputStream zipEntryInputStream;
    private T decrypter;
    private byte[] lastReadRawDataCache;
    private byte[] singleByteBuffer = new byte[1];
    private LocalFileHeader localFileHeader;

    public CipherInputStream(ZipEntryInputStream zipEntryInputStream, LocalFileHeader localFileHeader, char[] password, int bufferSize, boolean useUtf8ForPassword) throws IOException {
        this.zipEntryInputStream = zipEntryInputStream;
        this.decrypter = this.initializeDecrypter(localFileHeader, password, useUtf8ForPassword);
        this.localFileHeader = localFileHeader;
        if (Zip4jUtil.getCompressionMethod(localFileHeader).equals((Object)CompressionMethod.DEFLATE)) {
            this.lastReadRawDataCache = new byte[bufferSize];
        }
    }

    @Override
    public int read() throws IOException {
        int readLen = this.read(this.singleByteBuffer);
        if (readLen == -1) {
            return -1;
        }
        return this.singleByteBuffer[0] & 0xFF;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int readLen = Zip4jUtil.readFully(this.zipEntryInputStream, b, off, len);
        if (readLen > 0) {
            this.cacheRawData(b, readLen);
            this.decrypter.decryptData(b, off, readLen);
        }
        return readLen;
    }

    @Override
    public void close() throws IOException {
        this.zipEntryInputStream.close();
    }

    public byte[] getLastReadRawDataCache() {
        return this.lastReadRawDataCache;
    }

    protected int readRaw(byte[] b) throws IOException {
        return this.zipEntryInputStream.readRawFully(b);
    }

    private void cacheRawData(byte[] b, int len) {
        if (this.lastReadRawDataCache != null) {
            System.arraycopy(b, 0, this.lastReadRawDataCache, 0, len);
        }
    }

    public T getDecrypter() {
        return this.decrypter;
    }

    protected void endOfEntryReached(InputStream inputStream2, int numberOfBytesPushedBack) throws IOException {
    }

    protected long getNumberOfBytesReadForThisEntry() {
        return this.zipEntryInputStream.getNumberOfBytesRead();
    }

    public LocalFileHeader getLocalFileHeader() {
        return this.localFileHeader;
    }

    protected abstract T initializeDecrypter(LocalFileHeader var1, char[] var2, boolean var3) throws IOException;
}

