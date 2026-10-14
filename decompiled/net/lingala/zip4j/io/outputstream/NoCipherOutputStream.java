/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.outputstream;

import java.io.IOException;
import java.io.OutputStream;
import net.lingala.zip4j.crypto.Encrypter;
import net.lingala.zip4j.io.outputstream.CipherOutputStream;
import net.lingala.zip4j.io.outputstream.ZipEntryOutputStream;
import net.lingala.zip4j.model.ZipParameters;

class NoCipherOutputStream
extends CipherOutputStream<NoEncrypter> {
    public NoCipherOutputStream(ZipEntryOutputStream zipEntryOutputStream, ZipParameters zipParameters, char[] password) throws IOException {
        super(zipEntryOutputStream, zipParameters, password, true);
    }

    @Override
    protected NoEncrypter initializeEncrypter(OutputStream outputStream2, ZipParameters zipParameters, char[] password, boolean useUtf8ForPassword) {
        return new NoEncrypter();
    }

    static class NoEncrypter
    implements Encrypter {
        NoEncrypter() {
        }

        @Override
        public int encryptData(byte[] buff) {
            return this.encryptData(buff, 0, buff.length);
        }

        @Override
        public int encryptData(byte[] buff, int start2, int len) {
            return len;
        }
    }
}

