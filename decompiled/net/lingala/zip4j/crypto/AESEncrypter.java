/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.crypto;

import java.security.SecureRandom;
import net.lingala.zip4j.crypto.AesCipherUtil;
import net.lingala.zip4j.crypto.Encrypter;
import net.lingala.zip4j.crypto.PBKDF2.MacBasedPRF;
import net.lingala.zip4j.crypto.engine.AESEngine;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.enums.AesKeyStrength;

public class AESEncrypter
implements Encrypter {
    private AESEngine aesEngine;
    private MacBasedPRF mac;
    private final SecureRandom random = new SecureRandom();
    private boolean finished;
    private int nonce = 1;
    private int loopCount = 0;
    private final byte[] iv;
    private final byte[] counterBlock;
    private byte[] derivedPasswordVerifier;
    private byte[] saltBytes;

    public AESEncrypter(char[] password, AesKeyStrength aesKeyStrength, boolean useUtf8ForPassword) throws ZipException {
        if (password == null || password.length == 0) {
            throw new ZipException("input password is empty or null");
        }
        if (aesKeyStrength != AesKeyStrength.KEY_STRENGTH_128 && aesKeyStrength != AesKeyStrength.KEY_STRENGTH_256) {
            throw new ZipException("Invalid AES key strength");
        }
        this.finished = false;
        this.counterBlock = new byte[16];
        this.iv = new byte[16];
        this.init(password, aesKeyStrength, useUtf8ForPassword);
    }

    private void init(char[] password, AesKeyStrength aesKeyStrength, boolean useUtf8ForPassword) throws ZipException {
        this.saltBytes = this.generateSalt(aesKeyStrength.getSaltLength());
        byte[] derivedKey = AesCipherUtil.derivePasswordBasedKey(this.saltBytes, password, aesKeyStrength, useUtf8ForPassword);
        this.derivedPasswordVerifier = AesCipherUtil.derivePasswordVerifier(derivedKey, aesKeyStrength);
        this.aesEngine = AesCipherUtil.getAESEngine(derivedKey, aesKeyStrength);
        this.mac = AesCipherUtil.getMacBasedPRF(derivedKey, aesKeyStrength);
    }

    @Override
    public int encryptData(byte[] buff) throws ZipException {
        if (buff == null) {
            throw new ZipException("input bytes are null, cannot perform AES encryption");
        }
        return this.encryptData(buff, 0, buff.length);
    }

    @Override
    public int encryptData(byte[] buff, int start2, int len) throws ZipException {
        if (this.finished) {
            throw new ZipException("AES Encrypter is in finished state (A non 16 byte block has already been passed to encrypter)");
        }
        if (len % 16 != 0) {
            this.finished = true;
        }
        for (int j = start2; j < start2 + len; j += 16) {
            this.loopCount = j + 16 <= start2 + len ? 16 : start2 + len - j;
            AesCipherUtil.prepareBuffAESIVBytes(this.iv, this.nonce);
            this.aesEngine.processBlock(this.iv, this.counterBlock);
            for (int k = 0; k < this.loopCount; ++k) {
                buff[j + k] = (byte)(buff[j + k] ^ this.counterBlock[k]);
            }
            this.mac.update(buff, j, this.loopCount);
            ++this.nonce;
        }
        return len;
    }

    private byte[] generateSalt(int size) throws ZipException {
        if (size != 8 && size != 16) {
            throw new ZipException("invalid salt size, cannot generate salt");
        }
        int rounds = size == 8 ? 2 : 4;
        byte[] salt = new byte[size];
        for (int j = 0; j < rounds; ++j) {
            int i = this.random.nextInt();
            salt[j * 4] = (byte)(i >> 24);
            salt[1 + j * 4] = (byte)(i >> 16);
            salt[2 + j * 4] = (byte)(i >> 8);
            salt[3 + j * 4] = (byte)i;
        }
        return salt;
    }

    public byte[] getFinalMac() {
        byte[] rawMacBytes = this.mac.doFinal();
        byte[] macBytes = new byte[10];
        System.arraycopy(rawMacBytes, 0, macBytes, 0, 10);
        return macBytes;
    }

    public byte[] getDerivedPasswordVerifier() {
        return this.derivedPasswordVerifier;
    }

    public byte[] getSaltBytes() {
        return this.saltBytes;
    }
}

