/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.bind.v2.model.annotation;

import com.sun.xml.bind.v2.model.annotation.AbstractInlineAnnotationReaderImpl;
import com.sun.xml.bind.v2.model.annotation.Locatable;
import com.sun.xml.bind.v2.model.annotation.LocatableAnnotation;
import com.sun.xml.bind.v2.model.annotation.Messages;
import com.sun.xml.bind.v2.model.annotation.RuntimeAnnotationReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class RuntimeInlineAnnotationReader
extends AbstractInlineAnnotationReaderImpl<Type, Class, Field, Method>
implements RuntimeAnnotationReader {
    private final Map<Class<? extends Annotation>, Map<Package, Annotation>> packageCache = new HashMap<Class<? extends Annotation>, Map<Package, Annotation>>();

    @Override
    public <A extends Annotation> A getFieldAnnotation(Class<A> annotation, Field field2, Locatable srcPos) {
        return LocatableAnnotation.create(field2.getAnnotation(annotation), srcPos);
    }

    @Override
    public boolean hasFieldAnnotation(Class<? extends Annotation> annotationType, Field field2) {
        return field2.isAnnotationPresent(annotationType);
    }

    @Override
    public boolean hasClassAnnotation(Class clazz, Class<? extends Annotation> annotationType) {
        return clazz.isAnnotationPresent(annotationType);
    }

    @Override
    public Annotation[] getAllFieldAnnotations(Field field2, Locatable srcPos) {
        Annotation[] r = field2.getAnnotations();
        for (int i = 0; i < r.length; ++i) {
            r[i] = LocatableAnnotation.create(r[i], srcPos);
        }
        return r;
    }

    @Override
    public <A extends Annotation> A getMethodAnnotation(Class<A> annotation, Method method2, Locatable srcPos) {
        return LocatableAnnotation.create(method2.getAnnotation(annotation), srcPos);
    }

    @Override
    public boolean hasMethodAnnotation(Class<? extends Annotation> annotation, Method method2) {
        return method2.isAnnotationPresent(annotation);
    }

    @Override
    public Annotation[] getAllMethodAnnotations(Method method2, Locatable srcPos) {
        Annotation[] r = method2.getAnnotations();
        for (int i = 0; i < r.length; ++i) {
            r[i] = LocatableAnnotation.create(r[i], srcPos);
        }
        return r;
    }

    @Override
    public <A extends Annotation> A getMethodParameterAnnotation(Class<A> annotation, Method method2, int paramIndex, Locatable srcPos) {
        Annotation[] pa;
        for (Annotation a : pa = method2.getParameterAnnotations()[paramIndex]) {
            if (a.annotationType() != annotation) continue;
            return (A)LocatableAnnotation.create(a, srcPos);
        }
        return null;
    }

    @Override
    public <A extends Annotation> A getClassAnnotation(Class<A> a, Class clazz, Locatable srcPos) {
        return LocatableAnnotation.create(clazz.getAnnotation(a), srcPos);
    }

    @Override
    public <A extends Annotation> A getPackageAnnotation(Class<A> a, Class clazz, Locatable srcPos) {
        Package p = clazz.getPackage();
        if (p == null) {
            return null;
        }
        Map<Package, Annotation> cache = this.packageCache.get(a);
        if (cache == null) {
            cache = new HashMap<Package, Annotation>();
            this.packageCache.put(a, cache);
        }
        if (cache.containsKey(p)) {
            return (A)cache.get(p);
        }
        A ann = LocatableAnnotation.create(p.getAnnotation(a), srcPos);
        cache.put(p, (Annotation)ann);
        return ann;
    }

    @Override
    public Class getClassValue(Annotation a, String name) {
        try {
            return (Class)a.annotationType().getMethod(name, new Class[0]).invoke((Object)a, new Object[0]);
        }
        catch (IllegalAccessException e) {
            throw new IllegalAccessError(e.getMessage());
        }
        catch (InvocationTargetException e) {
            throw new InternalError(Messages.CLASS_NOT_FOUND.format(a.annotationType(), e.getMessage()));
        }
        catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }
    }

    public Class[] getClassArrayValue(Annotation a, String name) {
        try {
            return (Class[])a.annotationType().getMethod(name, new Class[0]).invoke((Object)a, new Object[0]);
        }
        catch (IllegalAccessException e) {
            throw new IllegalAccessError(e.getMessage());
        }
        catch (InvocationTargetException e) {
            throw new InternalError(e.getMessage());
        }
        catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }
    }

    @Override
    protected String fullName(Method m4) {
        return m4.getDeclaringClass().getName() + '#' + m4.getName();
    }
}

