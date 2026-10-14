/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.Transform;

class ByteTransform
implements Transform<Byte> {
    ByteTransform() {
    }

    @Override
    public Byte read(String value) {
        return Byte.valueOf(value);
    }

    @Override
    public String write(Byte value) {
        return value.toString();
    }
}

