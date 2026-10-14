/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.strategy;

import java.lang.annotation.Annotation;

public interface Type {
    public Class getType();

    public <T extends Annotation> T getAnnotation(Class<T> var1);

    public String toString();
}

