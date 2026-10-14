/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.protocol.commons.EnumWithValue;

public enum SMB3EncryptionCipher implements EnumWithValue<SMB3EncryptionCipher>
{
    AES_128_CCM(1L, "AES/CCM/NoPadding", 11),
    AES_128_GCM(2L, "AES/GCM/NoPadding", 12);

    private long value;
    private String algorithmName;
    private int nonceLength;

    private SMB3EncryptionCipher(long value, String algorithmName, int nonceLength) {
        this.value = value;
        this.algorithmName = algorithmName;
        this.nonceLength = nonceLength;
    }

    @Override
    public long getValue() {
        return this.value;
    }

    public String getAlgorithmName() {
        return this.algorithmName;
    }

    public int getNonceLength() {
        return this.nonceLength;
    }
}

