/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import org.ldaptive.transcode.AbstractStringValueTranscoder;

public abstract class AbstractPrimitiveValueTranscoder<T>
extends AbstractStringValueTranscoder<T> {
    private boolean primitive;

    public boolean isPrimitive() {
        return this.primitive;
    }

    public void setPrimitive(boolean b) {
        this.primitive = b;
    }

    @Override
    public String encodeStringValue(T value) {
        return value.toString();
    }
}

