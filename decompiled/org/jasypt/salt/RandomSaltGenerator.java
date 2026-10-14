/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.salt;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.salt.SaltGenerator;

public class RandomSaltGenerator
implements SaltGenerator {
    public static final String DEFAULT_SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
    private final SecureRandom random;

    public RandomSaltGenerator() {
        this(DEFAULT_SECURE_RANDOM_ALGORITHM);
    }

    public RandomSaltGenerator(String secureRandomAlgorithm) {
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
    public byte[] generateSalt(int lengthBytes) {
        byte[] salt = new byte[lengthBytes];
        SecureRandom secureRandom = this.random;
        synchronized (secureRandom) {
            this.random.nextBytes(salt);
        }
        return salt;
    }

    @Override
    public boolean includePlainSaltInEncryptionResults() {
        return true;
    }
}

