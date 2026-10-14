/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.iv;

import org.jasypt.commons.CommonUtils;
import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.iv.FixedIvGenerator;

public class ByteArrayFixedIvGenerator
implements FixedIvGenerator {
    private final byte[] iv;

    public ByteArrayFixedIvGenerator(byte[] iv) {
        CommonUtils.validateNotNull(iv, "Initialization vector cannot be set null");
        this.iv = (byte[])iv.clone();
    }

    @Override
    public byte[] generateIv(int lengthBytes) {
        if (this.iv.length < lengthBytes) {
            throw new EncryptionInitializationException("Requested initialization vector larger than set");
        }
        byte[] generatedIv = new byte[lengthBytes];
        System.arraycopy(this.iv, 0, generatedIv, 0, lengthBytes);
        return generatedIv;
    }

    @Override
    public boolean includePlainIvInEncryptionResults() {
        return false;
    }
}

