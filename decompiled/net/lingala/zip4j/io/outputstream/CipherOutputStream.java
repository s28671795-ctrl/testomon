/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.outputstream;

import java.io.IOException;
import java.io.OutputStream;
import net.lingala.zip4j.crypto.Encrypter;
import net.lingala.zip4j.io.outputstream.ZipEntryOutputStream;
import net.lingala.zip4j.model.ZipParameters;

abstract class CipherOutputStream<T extends Encrypter>
extends OutputStream {
    private ZipEntryOutputStream zipEntryOutputStream;
    private T encrypter;

    public CipherOutputStream(ZipEntryOutputStream zipEntryOutputStream, ZipParameters zipParameters, char[] password, boolean useUtf8ForPassword) throws IOException {
        this.zipEntryOutputStream = zipEntryOutputStream;
        this.encrypter = this.initializeEncrypter(zipEntryOutputStream, zipParameters, password, useUtf8ForPassword);
    }

    @Override
    public void write(int b) throws IOException {
        this.zipEntryOutputStream.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.zipEntryOutputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        this.encrypter.encryptData(b, off, len);
        this.zipEntryOutputStream.write(b, off, len);
    }

    public void writeHeaders(byte[] b) throws IOException {
        this.zipEntryOutputStream.write(b);
    }

    public void closeEntry() throws IOException {
        this.zipEntryOutputStream.closeEntry();
    }

    @Override
    public void close() throws IOException {
        this.zipEntryOutputStream.close();
    }

    public long getNumberOfBytesWrittenForThisEntry() {
        return this.zipEntryOutputStream.getNumberOfBytesWrittenForThisEntry();
    }

    protected T getEncrypter() {
        return this.encrypter;
    }

    protected abstract T initializeEncrypter(OutputStream var1, ZipParameters var2, char[] var3, boolean var4) throws IOException;
}

