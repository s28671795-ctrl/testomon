/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import java.util.function.Function;

public interface ValueTranscoder<T> {
    public T decodeStringValue(String var1);

    public T decodeBinaryValue(byte[] var1);

    public String encodeStringValue(T var1);

    public byte[] encodeBinaryValue(T var1);

    public Class<T> getType();

    default public Function<byte[], T> decoder() {
        return this::decodeBinaryValue;
    }

    default public Function<T, byte[]> encoder() {
        return this::encodeBinaryValue;
    }
}

