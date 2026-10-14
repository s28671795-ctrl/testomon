/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.protocol.commons.EnumWithValue;

public enum SMB3CompressionAlgorithm implements EnumWithValue<SMB3CompressionAlgorithm>
{
    NONE(0L),
    LZNT1(1L),
    LZ77(2L),
    LZ77_HUFFMAN(3L);

    private long value;

    private SMB3CompressionAlgorithm(long value) {
        this.value = value;
    }

    @Override
    public long getValue() {
        return this.value;
    }
}

