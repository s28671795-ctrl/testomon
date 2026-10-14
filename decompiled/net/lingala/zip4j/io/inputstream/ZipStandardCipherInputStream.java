/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.inputstream;

import java.io.IOException;
import net.lingala.zip4j.crypto.StandardDecrypter;
import net.lingala.zip4j.io.inputstream.CipherInputStream;
import net.lingala.zip4j.io.inputstream.ZipEntryInputStream;
import net.lingala.zip4j.model.LocalFileHeader;

class ZipStandardCipherInputStream
extends CipherInputStream<StandardDecrypter> {
    public ZipStandardCipherInputStream(ZipEntryInputStream zipEntryInputStream, LocalFileHeader localFileHeader, char[] password, int bufferSize, boolean useUtf8ForPassword) throws IOException {
        super(zipEntryInputStream, localFileHeader, password, bufferSize, useUtf8ForPassword);
    }

    @Override
    protected StandardDecrypter initializeDecrypter(LocalFileHeader localFileHeader, char[] password, boolean useUtf8ForPassword) throws IOException {
        return new StandardDecrypter(password, localFileHeader.getCrc(), localFileHeader.getLastModifiedTime(), this.getStandardDecrypterHeaderBytes(), useUtf8ForPassword);
    }

    private byte[] getStandardDecrypterHeaderBytes() throws IOException {
        byte[] headerBytes = new byte[12];
        this.readRaw(headerBytes);
        return headerBytes;
    }
}

