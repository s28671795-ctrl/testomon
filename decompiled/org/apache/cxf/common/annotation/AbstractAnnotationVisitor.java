/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.cxf.common.annotation.AnnotationVisitor;

public abstract class AbstractAnnotationVisitor
implements AnnotationVisitor {
    protected Object target;
    protected Class<?> targetClass;
    private final List<Class<? extends Annotation>> targetAnnotations = new ArrayList<Class<? extends Annotation>>();

    protected AbstractAnnotationVisitor(Class<? extends Annotation> ann) {
        this.addTargetAnnotation(ann);
    }

    protected AbstractAnnotationVisitor(List<Class<? extends Annotation>> ann) {
        this.targetAnnotations.addAll(ann);
    }

    protected final void addTargetAnnotation(Class<? extends Annotation> ann) {
        this.targetAnnotations.add(ann);
    }

    @Override
    public void visitClass(Class<?> clz, Annotation annotation) {
    }

    @Override
    public List<Class<? extends Annotation>> getTargetAnnotations() {
        return this.targetAnnotations;
    }

    @Override
    public void visitField(Field field2, Annotation annotation) {
    }

    @Override
    public void visitMethod(Method method2, Annotation annotation) {
    }

    @Override
    public void setTarget(Object object) {
        this.target = object;
        this.targetClass = object.getClass();
    }

    public void setTarget(Object object, Class<?> cls) {
        this.target = object;
        this.targetClass = cls;
    }

    public Object getTarget() {
        return this.target;
    }

    public Class<?> getTargetClass() {
        return this.targetClass;
    }
}

