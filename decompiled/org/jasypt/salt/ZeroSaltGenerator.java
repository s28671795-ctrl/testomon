/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.salt;

import java.util.Arrays;
import org.jasypt.salt.SaltGenerator;

public class ZeroSaltGenerator
implements SaltGenerator {
    @Override
    public byte[] generateSalt(int lengthBytes) {
        byte[] result = new byte[lengthBytes];
        Arrays.fill(result, (byte)0);
        return result;
    }

    @Override
    public boolean includePlainSaltInEncryptionResults() {
        return false;
    }
}

