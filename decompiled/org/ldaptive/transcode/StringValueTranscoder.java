/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import org.ldaptive.transcode.AbstractStringValueTranscoder;

public class StringValueTranscoder
extends AbstractStringValueTranscoder<String> {
    @Override
    public String decodeStringValue(String value) {
        return value;
    }

    @Override
    public String encodeStringValue(String value) {
        return value;
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}

