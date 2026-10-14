/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.properties;

import java.lang.reflect.Type;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;

public interface Constrainable {
    public String getName();

    public Class<?> getDeclaringClass();

    public Type getTypeForValidatorResolution();

    public Type getType();

    public ConstrainedElement.ConstrainedElementKind getConstrainedElementKind();

    default public <T> T as(Class<T> clazz) {
        return (T)this;
    }
}

