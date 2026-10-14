/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import org.ldaptive.transcode.AbstractPrimitiveValueTranscoder;

public class ShortValueTranscoder
extends AbstractPrimitiveValueTranscoder<Short> {
    public ShortValueTranscoder() {
    }

    public ShortValueTranscoder(boolean b) {
        this.setPrimitive(b);
    }

    @Override
    public Short decodeStringValue(String value) {
        return Short.valueOf(value);
    }

    @Override
    public Class<Short> getType() {
        return this.isPrimitive() ? Short.TYPE : Short.class;
    }
}

