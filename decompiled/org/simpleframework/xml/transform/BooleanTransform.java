/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.Transform;

class BooleanTransform
implements Transform<Boolean> {
    BooleanTransform() {
    }

    @Override
    public Boolean read(String value) {
        return Boolean.valueOf(value);
    }

    @Override
    public String write(Boolean value) {
        return value.toString();
    }
}

