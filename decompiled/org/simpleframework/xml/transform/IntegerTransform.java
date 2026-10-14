/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.Transform;

class IntegerTransform
implements Transform<Integer> {
    IntegerTransform() {
    }

    @Override
    public Integer read(String value) {
        return Integer.valueOf(value);
    }

    @Override
    public String write(Integer value) {
        return value.toString();
    }
}

