/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.Transform;

class EnumTransform
implements Transform<Enum> {
    private final Class type;

    public EnumTransform(Class type) {
        this.type = type;
    }

    @Override
    public Enum read(String value) throws Exception {
        return Enum.valueOf(this.type, value);
    }

    @Override
    public String write(Enum value) throws Exception {
        return value.name();
    }
}

