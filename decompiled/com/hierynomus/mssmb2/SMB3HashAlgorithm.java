/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.protocol.commons.EnumWithValue;

public enum SMB3HashAlgorithm implements EnumWithValue<SMB3HashAlgorithm>
{
    SHA_512(1L, "SHA-512");

    private long value;
    private String algorithmName;

    private SMB3HashAlgorithm(long value, String algorithmName) {
        this.value = value;
        this.algorithmName = algorithmName;
    }

    @Override
    public long getValue() {
        return this.value;
    }

    public String getAlgorithmName() {
        return this.algorithmName;
    }
}

