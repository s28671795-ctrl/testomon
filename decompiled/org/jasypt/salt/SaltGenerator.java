/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.salt;

public interface SaltGenerator {
    public byte[] generateSalt(int var1);

    public boolean includePlainSaltInEncryptionResults();
}

