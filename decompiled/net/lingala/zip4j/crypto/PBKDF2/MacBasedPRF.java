/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.crypto.PBKDF2;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import net.lingala.zip4j.crypto.PBKDF2.PRF;

public class MacBasedPRF
implements PRF {
    private Mac mac;
    private int hLen;
    private String macAlgorithm;
    private ByteArrayOutputStream macCache;

    public MacBasedPRF(String macAlgorithm) {
        this.macAlgorithm = macAlgorithm;
        this.macCache = new ByteArrayOutputStream(4096);
        try {
            this.mac = Mac.getInstance(macAlgorithm);
            this.hLen = this.mac.getMacLength();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] doFinal(byte[] M) {
        if (this.macCache.size() > 0) {
            this.doMacUpdate(0);
        }
        return this.mac.doFinal(M);
    }

    public byte[] doFinal() {
        return this.doFinal(0);
    }

    public byte[] doFinal(int numberOfBytesToPushbackForMac) {
        if (this.macCache.size() > 0) {
            this.doMacUpdate(numberOfBytesToPushbackForMac);
        }
        return this.mac.doFinal();
    }

    @Override
    public int getHLen() {
        return this.hLen;
    }

    @Override
    public void init(byte[] P2) {
        try {
            this.mac.init(new SecretKeySpec(P2, this.macAlgorithm));
        }
        catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(byte[] u) {
        this.update(u, 0, u.length);
    }

    public void update(byte[] u, int start2, int len) {
        try {
            if (this.macCache.size() + len > 4096) {
                this.doMacUpdate(0);
            }
            this.macCache.write(u, start2, len);
        }
        catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    private void doMacUpdate(int numberOfBytesToPushBack) {
        byte[] macBytes = this.macCache.toByteArray();
        int numberOfBytesToRead = macBytes.length - numberOfBytesToPushBack;
        for (int i = 0; i < numberOfBytesToRead; i += 16) {
            int updateLength = i + 16 <= numberOfBytesToRead ? 16 : numberOfBytesToRead - i;
            this.mac.update(macBytes, i, updateLength);
        }
        this.macCache.reset();
    }
}

