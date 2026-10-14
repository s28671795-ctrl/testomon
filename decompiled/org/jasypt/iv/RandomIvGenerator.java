/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.iv;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.iv.IvGenerator;

public class RandomIvGenerator
implements IvGenerator {
    public static final String DEFAULT_SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
    private final SecureRandom random;

    public RandomIvGenerator() {
        this(DEFAULT_SECURE_RANDOM_ALGORITHM);
    }

    public RandomIvGenerator(String secureRandomAlgorithm) {
        try {
            this.random = SecureRandom.getInstance(secureRandomAlgorithm);
        }
        catch (NoSuchAlgorithmException e) {
            throw new EncryptionInitializationException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public byte[] generateIv(int lengthBytes) {
        byte[] iv = new byte[lengthBytes];
        SecureRandom secureRandom = this.random;
        synchronized (secureRandom) {
            this.random.nextBytes(iv);
        }
        return iv;
    }

    @Override
    public boolean includePlainIvInEncryptionResults() {
        return true;
    }
}

