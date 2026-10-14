/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import org.ldaptive.transcode.AbstractBinaryValueTranscoder;

public class ByteArrayValueTranscoder
extends AbstractBinaryValueTranscoder<byte[]> {
    @Override
    public byte[] decodeBinaryValue(byte[] value) {
        return value;
    }

    @Override
    public byte[] encodeBinaryValue(byte[] value) {
        return value;
    }

    @Override
    public Class<byte[]> getType() {
        return byte[].class;
    }
}

