/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import org.ldaptive.transcode.AbstractPrimitiveValueTranscoder;

public class FloatValueTranscoder
extends AbstractPrimitiveValueTranscoder<Float> {
    public FloatValueTranscoder() {
    }

    public FloatValueTranscoder(boolean b) {
        this.setPrimitive(b);
    }

    @Override
    public Float decodeStringValue(String value) {
        return Float.valueOf(value);
    }

    @Override
    public Class<Float> getType() {
        return this.isPrimitive() ? Float.TYPE : Float.class;
    }
}

