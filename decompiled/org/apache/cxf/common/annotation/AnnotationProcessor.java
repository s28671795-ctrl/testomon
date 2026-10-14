/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;
import org.apache.cxf.common.annotation.AnnotationVisitor;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ReflectionUtil;

public class AnnotationProcessor {
    private static final Logger LOG = LogUtils.getL7dLogger(AnnotationProcessor.class);
    private final Object target;
    private List<Class<? extends Annotation>> annotationTypes;

    public AnnotationProcessor(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(new Message("INVALID_CTOR_ARGS", LOG, new Object[0]).toString());
        }
        this.target = o;
    }

    public void accept(AnnotationVisitor visitor, Class<?> claz) {
        if (visitor == null) {
            throw new IllegalArgumentException();
        }
        this.annotationTypes = visitor.getTargetAnnotations();
        visitor.setTarget(this.target);
        this.processClass(visitor, claz);
        this.processFields(visitor, claz);
        this.processMethods(visitor, claz);
    }

    public void accept(AnnotationVisitor visitor) {
        this.accept(visitor, this.target.getClass());
    }

    private void processMethods(AnnotationVisitor visitor, Class<? extends Object> targetClass) {
        if (targetClass.getSuperclass() != null) {
            this.processMethods(visitor, targetClass.getSuperclass());
        }
        for (Method element : ReflectionUtil.getDeclaredMethods(targetClass)) {
            for (Class<? extends Annotation> clz : this.annotationTypes) {
                Annotation ann = element.getAnnotation(clz);
                if (ann == null) continue;
                visitor.visitMethod(element, ann);
            }
        }
    }

    private void processFields(AnnotationVisitor visitor, Class<? extends Object> targetClass) {
        if (targetClass.getSuperclass() != null) {
            this.processFields(visitor, targetClass.getSuperclass());
        }
        for (Field element : ReflectionUtil.getDeclaredFields(targetClass)) {
            for (Class<? extends Annotation> clz : this.annotationTypes) {
                Annotation ann = element.getAnnotation(clz);
                if (ann == null) continue;
                visitor.visitField(element, ann);
            }
        }
    }

    private void processClass(AnnotationVisitor visitor, Class<? extends Object> targetClass) {
        if (targetClass.getSuperclass() != null) {
            this.processClass(visitor, targetClass.getSuperclass());
        }
        for (Class<? extends Annotation> clz : this.annotationTypes) {
            Annotation ann = targetClass.getAnnotation(clz);
            if (ann == null) continue;
            visitor.visitClass(targetClass, ann);
        }
    }
}

