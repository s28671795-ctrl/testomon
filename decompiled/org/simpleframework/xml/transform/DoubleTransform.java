/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.Transform;

class DoubleTransform
implements Transform<Double> {
    DoubleTransform() {
    }

    @Override
    public Double read(String value) {
        return Double.valueOf(value);
    }

    @Override
    public String write(Double value) {
        return value.toString();
    }
}

