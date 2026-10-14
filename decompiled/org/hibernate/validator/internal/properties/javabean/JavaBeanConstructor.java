/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.properties.javabean;

import java.lang.reflect.Constructor;
import java.lang.reflect.TypeVariable;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.properties.javabean.JavaBeanExecutable;

public class JavaBeanConstructor
extends JavaBeanExecutable<Constructor<?>> {
    public JavaBeanConstructor(Constructor<?> executable) {
        super(executable, true);
    }

    @Override
    public String getName() {
        return this.getDeclaringClass().getSimpleName();
    }

    @Override
    public ConstrainedElement.ConstrainedElementKind getConstrainedElementKind() {
        return ConstrainedElement.ConstrainedElementKind.CONSTRUCTOR;
    }

    @Override
    public TypeVariable<?>[] getTypeParameters() {
        return ((Constructor)this.executable).getDeclaringClass().getTypeParameters();
    }
}

