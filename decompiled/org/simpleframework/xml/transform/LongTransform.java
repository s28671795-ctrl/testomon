/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.Transform;

class LongTransform
implements Transform<Long> {
    LongTransform() {
    }

    @Override
    public Long read(String value) {
        return Long.valueOf(value);
    }

    @Override
    public String write(Long value) {
        return value.toString();
    }
}

