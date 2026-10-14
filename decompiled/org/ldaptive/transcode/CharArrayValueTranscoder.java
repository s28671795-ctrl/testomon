/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import org.ldaptive.transcode.AbstractStringValueTranscoder;

public class CharArrayValueTranscoder
extends AbstractStringValueTranscoder<char[]> {
    @Override
    public char[] decodeStringValue(String value) {
        return value.toCharArray();
    }

    @Override
    public String encodeStringValue(char[] value) {
        return String.valueOf(value);
    }

    @Override
    public Class<char[]> getType() {
        return char[].class;
    }
}

