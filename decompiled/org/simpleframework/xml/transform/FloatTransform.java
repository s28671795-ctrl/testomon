/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.Transform;

class FloatTransform
implements Transform<Float> {
    FloatTransform() {
    }

    @Override
    public Float read(String value) {
        return Float.valueOf(value);
    }

    @Override
    public String write(Float value) {
        return value.toString();
    }
}

