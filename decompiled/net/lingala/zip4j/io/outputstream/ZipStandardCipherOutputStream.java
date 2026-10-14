/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.outputstream;

import java.io.IOException;
import java.io.OutputStream;
import net.lingala.zip4j.crypto.StandardEncrypter;
import net.lingala.zip4j.io.outputstream.CipherOutputStream;
import net.lingala.zip4j.io.outputstream.ZipEntryOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jUtil;

class ZipStandardCipherOutputStream
extends CipherOutputStream<StandardEncrypter> {
    public ZipStandardCipherOutputStream(ZipEntryOutputStream outputStream2, ZipParameters zipParameters, char[] password, boolean useUtf8ForPassword) throws IOException {
        super(outputStream2, zipParameters, password, useUtf8ForPassword);
    }

    @Override
    protected StandardEncrypter initializeEncrypter(OutputStream outputStream2, ZipParameters zipParameters, char[] password, boolean useUtf8ForPassword) throws IOException {
        long key = this.getEncryptionKey(zipParameters);
        StandardEncrypter encrypter = new StandardEncrypter(password, key, useUtf8ForPassword);
        this.writeHeaders(encrypter.getHeaderBytes());
        return encrypter;
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
        super.write(b, off, len);
    }

    private long getEncryptionKey(ZipParameters zipParameters) {
        if (zipParameters.isWriteExtendedLocalFileHeader()) {
            long dosTime = Zip4jUtil.epochToExtendedDosTime(zipParameters.getLastModifiedFileTime());
            return (dosTime & 0xFFFFL) << 16;
        }
        return zipParameters.getEntryCRC();
    }
}

