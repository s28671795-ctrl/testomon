/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import org.ldaptive.transcode.AbstractPrimitiveValueTranscoder;

public class LongValueTranscoder
extends AbstractPrimitiveValueTranscoder<Long> {
    public LongValueTranscoder() {
    }

    public LongValueTranscoder(boolean b) {
        this.setPrimitive(b);
    }

    @Override
    public Long decodeStringValue(String value) {
        return Long.valueOf(value);
    }

    @Override
    public Class<Long> getType() {
        return this.isPrimitive() ? Long.TYPE : Long.class;
    }
}

