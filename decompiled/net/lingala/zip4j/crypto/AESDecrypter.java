/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.crypto;

import java.util.Arrays;
import net.lingala.zip4j.crypto.AesCipherUtil;
import net.lingala.zip4j.crypto.Decrypter;
import net.lingala.zip4j.crypto.PBKDF2.MacBasedPRF;
import net.lingala.zip4j.crypto.engine.AESEngine;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.enums.AesKeyStrength;

public class AESDecrypter
implements Decrypter {
    private AESEngine aesEngine;
    private MacBasedPRF mac;
    private int nonce = 1;
    private byte[] iv = new byte[16];
    private byte[] counterBlock = new byte[16];

    public AESDecrypter(AESExtraDataRecord aesExtraDataRecord, char[] password, byte[] salt, byte[] passwordVerifier, boolean useUtf8ForPassword) throws ZipException {
        this.init(salt, passwordVerifier, password, aesExtraDataRecord, useUtf8ForPassword);
    }

    private void init(byte[] salt, byte[] passwordVerifier, char[] password, AESExtraDataRecord aesExtraDataRecord, boolean useUtf8ForPassword) throws ZipException {
        if (password == null || password.length <= 0) {
            throw new ZipException("empty or null password provided for AES decryption", ZipException.Type.WRONG_PASSWORD);
        }
        AesKeyStrength aesKeyStrength = aesExtraDataRecord.getAesKeyStrength();
        byte[] derivedKey = AesCipherUtil.derivePasswordBasedKey(salt, password, aesKeyStrength, useUtf8ForPassword);
        byte[] derivedPasswordVerifier = AesCipherUtil.derivePasswordVerifier(derivedKey, aesKeyStrength);
        if (!Arrays.equals(passwordVerifier, derivedPasswordVerifier)) {
            throw new ZipException("Wrong Password", ZipException.Type.WRONG_PASSWORD);
        }
        this.aesEngine = AesCipherUtil.getAESEngine(derivedKey, aesKeyStrength);
        this.mac = AesCipherUtil.getMacBasedPRF(derivedKey, aesKeyStrength);
    }

    @Override
    public int decryptData(byte[] buff, int start2, int len) throws ZipException {
        for (int j = start2; j < start2 + len; j += 16) {
            int loopCount = j + 16 <= start2 + len ? 16 : start2 + len - j;
            this.mac.update(buff, j, loopCount);
            AesCipherUtil.prepareBuffAESIVBytes(this.iv, this.nonce);
            this.aesEngine.processBlock(this.iv, this.counterBlock);
            for (int k = 0; k < loopCount; ++k) {
                buff[j + k] = (byte)(buff[j + k] ^ this.counterBlock[k]);
            }
            ++this.nonce;
        }
        return len;
    }

    public byte[] getCalculatedAuthenticationBytes(int numberOfBytesPushedBack) {
        return this.mac.doFinal(numberOfBytesPushedBack);
    }
}

