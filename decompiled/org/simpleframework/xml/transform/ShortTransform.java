/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.Transform;

class ShortTransform
implements Transform<Short> {
    ShortTransform() {
    }

    @Override
    public Short read(String value) {
        return Short.valueOf(value);
    }

    @Override
    public String write(Short value) {
        return value.toString();
    }
}

