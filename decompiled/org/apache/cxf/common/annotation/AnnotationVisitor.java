/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public interface AnnotationVisitor {
    public void setTarget(Object var1);

    public List<Class<? extends Annotation>> getTargetAnnotations();

    public void visitClass(Class<?> var1, Annotation var2);

    public void visitField(Field var1, Annotation var2);

    public void visitMethod(Method var1, Annotation var2);
}

