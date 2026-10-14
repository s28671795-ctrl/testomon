/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.crypto;

import java.security.SecureRandom;
import net.lingala.zip4j.crypto.Encrypter;
import net.lingala.zip4j.crypto.engine.ZipCryptoEngine;
import net.lingala.zip4j.exception.ZipException;

public class StandardEncrypter
implements Encrypter {
    private final ZipCryptoEngine zipCryptoEngine = new ZipCryptoEngine();
    private byte[] headerBytes;

    public StandardEncrypter(char[] password, long key, boolean useUtf8ForPassword) throws ZipException {
        this.init(password, key, useUtf8ForPassword);
    }

    private void init(char[] password, long key, boolean useUtf8ForPassword) throws ZipException {
        if (password == null || password.length <= 0) {
            throw new ZipException("input password is null or empty, cannot initialize standard encrypter");
        }
        this.zipCryptoEngine.initKeys(password, useUtf8ForPassword);
        this.headerBytes = this.generateRandomBytes();
        this.zipCryptoEngine.initKeys(password, useUtf8ForPassword);
        this.headerBytes[11] = (byte)(key >>> 24);
        this.headerBytes[10] = (byte)(key >>> 16);
        this.encryptData(this.headerBytes);
    }

    @Override
    public int encryptData(byte[] buff) throws ZipException {
        if (buff == null) {
            throw new NullPointerException();
        }
        return this.encryptData(buff, 0, buff.length);
    }

    @Override
    public int encryptData(byte[] buff, int start2, int len) throws ZipException {
        if (len < 0) {
            throw new ZipException("invalid length specified to decrpyt data");
        }
        for (int i = start2; i < start2 + len; ++i) {
            buff[i] = this.encryptByte(buff[i]);
        }
        return len;
    }

    protected byte encryptByte(byte val) {
        byte temp_val = (byte)(val ^ this.zipCryptoEngine.decryptByte() & 0xFF);
        this.zipCryptoEngine.updateKeys(val);
        return temp_val;
    }

    protected byte[] generateRandomBytes() {
        byte[] buff = new byte[12];
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < buff.length; ++i) {
            buff[i] = this.encryptByte((byte)random.nextInt(256));
        }
        return buff;
    }

    public byte[] getHeaderBytes() {
        return this.headerBytes;
    }
}

