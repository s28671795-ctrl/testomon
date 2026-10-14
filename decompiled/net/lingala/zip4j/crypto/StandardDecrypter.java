/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.crypto;

import net.lingala.zip4j.crypto.Decrypter;
import net.lingala.zip4j.crypto.engine.ZipCryptoEngine;
import net.lingala.zip4j.exception.ZipException;

public class StandardDecrypter
implements Decrypter {
    private ZipCryptoEngine zipCryptoEngine = new ZipCryptoEngine();

    public StandardDecrypter(char[] password, long crc, long lastModifiedFileTime, byte[] headerBytes, boolean useUtf8ForPassword) throws ZipException {
        this.init(headerBytes, password, lastModifiedFileTime, crc, useUtf8ForPassword);
    }

    @Override
    public int decryptData(byte[] buff, int start2, int len) throws ZipException {
        if (start2 < 0 || len < 0) {
            throw new ZipException("one of the input parameters were null in standard decrypt data");
        }
        for (int i = start2; i < start2 + len; ++i) {
            int val = buff[i] & 0xFF;
            val = (val ^ this.zipCryptoEngine.decryptByte()) & 0xFF;
            this.zipCryptoEngine.updateKeys((byte)val);
            buff[i] = (byte)val;
        }
        return len;
    }

    private void init(byte[] headerBytes, char[] password, long lastModifiedFileTime, long crc, boolean useUtf8ForPassword) throws ZipException {
        if (password == null || password.length <= 0) {
            throw new ZipException("Wrong password!", ZipException.Type.WRONG_PASSWORD);
        }
        this.zipCryptoEngine.initKeys(password, useUtf8ForPassword);
        byte result = headerBytes[0];
        for (int i = 0; i < 12; ++i) {
            byte verificationByte;
            if (i + 1 == 12 && (verificationByte = (byte)(result ^ this.zipCryptoEngine.decryptByte())) != (byte)(crc >> 24) && verificationByte != (byte)(lastModifiedFileTime >> 8)) {
                throw new ZipException("Wrong password!", ZipException.Type.WRONG_PASSWORD);
            }
            this.zipCryptoEngine.updateKeys((byte)(result ^ this.zipCryptoEngine.decryptByte()));
            if (i + 1 == 12) continue;
            result = headerBytes[i + 1];
        }
    }
}

