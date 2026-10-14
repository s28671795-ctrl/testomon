/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.simpleframework.xml.core.MethodName;
import org.simpleframework.xml.core.MethodPart;
import org.simpleframework.xml.core.MethodType;
import org.simpleframework.xml.core.Reflector;
import org.simpleframework.xml.util.Cache;
import org.simpleframework.xml.util.ConcurrentCache;

class SetPart
implements MethodPart {
    private final Cache<Annotation> cache = new ConcurrentCache<Annotation>();
    private final Annotation[] list;
    private final Annotation label;
    private final MethodType type;
    private final Method method;
    private final String name;

    public SetPart(MethodName method2, Annotation label, Annotation[] list) {
        this.method = method2.getMethod();
        this.name = method2.getName();
        this.type = method2.getType();
        this.label = label;
        this.list = list;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Class getType() {
        return this.method.getParameterTypes()[0];
    }

    @Override
    public Class getDependent() {
        return Reflector.getParameterDependent(this.method, 0);
    }

    @Override
    public Class[] getDependents() {
        return Reflector.getParameterDependents(this.method, 0);
    }

    @Override
    public Class getDeclaringClass() {
        return this.method.getDeclaringClass();
    }

    @Override
    public Annotation getAnnotation() {
        return this.label;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> type) {
        if (this.cache.isEmpty()) {
            for (Annotation entry : this.list) {
                Class<? extends Annotation> key = entry.annotationType();
                this.cache.cache(key, entry);
            }
        }
        return (T)this.cache.fetch(type);
    }

    @Override
    public MethodType getMethodType() {
        return this.type;
    }

    @Override
    public Method getMethod() {
        if (!this.method.isAccessible()) {
            this.method.setAccessible(true);
        }
        return this.method;
    }

    @Override
    public String toString() {
        return this.method.toGenericString();
    }
}

