/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.strategy.Type;

class ClassType
implements Type {
    private final Class type;

    public ClassType(Class type) {
        this.type = type;
    }

    @Override
    public Class getType() {
        return this.type;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> type) {
        return null;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}

