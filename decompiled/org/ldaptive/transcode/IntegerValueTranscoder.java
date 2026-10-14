/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import org.ldaptive.transcode.AbstractPrimitiveValueTranscoder;

public class IntegerValueTranscoder
extends AbstractPrimitiveValueTranscoder<Integer> {
    public IntegerValueTranscoder() {
    }

    public IntegerValueTranscoder(boolean b) {
        this.setPrimitive(b);
    }

    @Override
    public Integer decodeStringValue(String value) {
        return Integer.valueOf(value);
    }

    @Override
    public Class<Integer> getType() {
        return this.isPrimitive() ? Integer.TYPE : Integer.class;
    }
}

