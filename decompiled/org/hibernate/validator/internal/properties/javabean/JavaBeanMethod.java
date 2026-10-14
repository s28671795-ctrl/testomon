/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.properties.javabean;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.properties.javabean.JavaBeanExecutable;

public class JavaBeanMethod
extends JavaBeanExecutable<Method> {
    JavaBeanMethod(Method method2) {
        super(method2, method2.getGenericReturnType() != Void.TYPE);
    }

    @Override
    public ConstrainedElement.ConstrainedElementKind getConstrainedElementKind() {
        return ConstrainedElement.ConstrainedElementKind.METHOD;
    }

    @Override
    public TypeVariable<?>[] getTypeParameters() {
        return ((Method)this.executable).getReturnType().getTypeParameters();
    }
}

