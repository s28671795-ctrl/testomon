/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import org.ldaptive.transcode.AbstractPrimitiveValueTranscoder;

public class DoubleValueTranscoder
extends AbstractPrimitiveValueTranscoder<Double> {
    public DoubleValueTranscoder() {
    }

    public DoubleValueTranscoder(boolean b) {
        this.setPrimitive(b);
    }

    @Override
    public Double decodeStringValue(String value) {
        return Double.valueOf(value);
    }

    @Override
    public Class<Double> getType() {
        return this.isPrimitive() ? Double.TYPE : Double.class;
    }
}

