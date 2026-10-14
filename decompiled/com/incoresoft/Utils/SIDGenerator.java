/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class SIDGenerator {
    protected static final String DEFAULT_ALGORITHM = "MD5";
    private String algorithm = "MD5";
    private Random random = new SecureRandom();
    private MessageDigest digest = null;

    public SIDGenerator() {
        long seed = System.currentTimeMillis();
        char[] entropy = this.toString().toCharArray();
        for (int i = 0; i < entropy.length; ++i) {
            long update = (byte)entropy[i] << i % 8 * 8;
            seed ^= update;
        }
        this.random.setSeed(seed);
    }

    public synchronized String generateId(int length) {
        byte[] buffer = new byte[length];
        StringBuffer reply = new StringBuffer();
        int resultLenBytes = 0;
        while (resultLenBytes < length) {
            this.random.nextBytes(buffer);
            buffer = this.getDigest().digest(buffer);
            for (int j = 0; j < buffer.length && resultLenBytes < length; ++resultLenBytes, ++j) {
                byte b1 = (byte)((buffer[j] & 0xF0) >> 4);
                if (b1 < 10) {
                    reply.append((char)(48 + b1));
                } else {
                    reply.append((char)(65 + (b1 - 10)));
                }
                byte b2 = (byte)(buffer[j] & 0xF);
                if (b2 < 10) {
                    reply.append((char)(48 + b2));
                    continue;
                }
                reply.append((char)(65 + (b2 - 10)));
            }
        }
        return reply.toString();
    }

    public synchronized String getAlgorithm() {
        return this.algorithm;
    }

    public synchronized void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        this.digest = null;
    }

    private MessageDigest getDigest() {
        if (this.digest == null) {
            try {
                this.digest = MessageDigest.getInstance(this.algorithm);
            }
            catch (NoSuchAlgorithmException ex) {
                try {
                    this.digest = MessageDigest.getInstance(DEFAULT_ALGORITHM);
                }
                catch (NoSuchAlgorithmException ex2) {
                    this.digest = null;
                    throw new IllegalStateException("No algorithms for IdGenerator");
                }
            }
        }
        return this.digest;
    }

    public final String toString() {
        return super.toString();
    }
}

