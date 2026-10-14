/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.salt;

import org.jasypt.commons.CommonUtils;
import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.salt.SaltGenerator;

public class FixedByteArraySaltGenerator
implements SaltGenerator {
    private byte[] salt = null;

    public synchronized void setSalt(byte[] salt) {
        CommonUtils.validateNotNull(salt, "Salt cannot be set null");
        this.salt = (byte[])salt.clone();
    }

    @Override
    public byte[] generateSalt(int lengthBytes) {
        if (this.salt == null) {
            throw new EncryptionInitializationException("Salt has not been set");
        }
        if (this.salt.length < lengthBytes) {
            throw new EncryptionInitializationException("Requested salt larger than set");
        }
        byte[] generatedSalt = new byte[lengthBytes];
        System.arraycopy(this.salt, 0, generatedSalt, 0, lengthBytes);
        return generatedSalt;
    }

    @Override
    public boolean includePlainSaltInEncryptionResults() {
        return false;
    }
}

