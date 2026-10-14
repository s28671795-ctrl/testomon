/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import org.ldaptive.transcode.AbstractPrimitiveValueTranscoder;

public class BooleanValueTranscoder
extends AbstractPrimitiveValueTranscoder<Boolean> {
    public BooleanValueTranscoder() {
    }

    public BooleanValueTranscoder(boolean b) {
        this.setPrimitive(b);
    }

    @Override
    public Boolean decodeStringValue(String value) {
        return Boolean.valueOf(value);
    }

    @Override
    public Class<Boolean> getType() {
        return this.isPrimitive() ? Boolean.TYPE : Boolean.class;
    }
}

