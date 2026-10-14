/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.inputstream;

import java.io.IOException;
import net.lingala.zip4j.crypto.Decrypter;
import net.lingala.zip4j.io.inputstream.CipherInputStream;
import net.lingala.zip4j.io.inputstream.ZipEntryInputStream;
import net.lingala.zip4j.model.LocalFileHeader;

class NoCipherInputStream
extends CipherInputStream<NoDecrypter> {
    public NoCipherInputStream(ZipEntryInputStream zipEntryInputStream, LocalFileHeader localFileHeader, char[] password, int bufferSize) throws IOException {
        super(zipEntryInputStream, localFileHeader, password, bufferSize, true);
    }

    @Override
    protected NoDecrypter initializeDecrypter(LocalFileHeader localFileHeader, char[] password, boolean useUtf8ForPassword) {
        return new NoDecrypter();
    }

    static class NoDecrypter
    implements Decrypter {
        NoDecrypter() {
        }

        @Override
        public int decryptData(byte[] buff, int start2, int len) {
            return len;
        }
    }
}

