/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.strategy.Type;

class OverrideType
implements Type {
    private final Class override;
    private final Type type;

    public OverrideType(Type type, Class override) {
        this.override = override;
        this.type = type;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> label) {
        return this.type.getAnnotation(label);
    }

    @Override
    public Class getType() {
        return this.override;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}

