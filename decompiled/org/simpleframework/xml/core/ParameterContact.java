/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Reflector;

abstract class ParameterContact<T extends Annotation>
implements Contact {
    protected final Annotation[] labels;
    protected final Constructor factory;
    protected final Class owner;
    protected final int index;
    protected final T label;

    public ParameterContact(T label, Constructor factory2, int index) {
        this.labels = factory2.getParameterAnnotations()[index];
        this.owner = factory2.getDeclaringClass();
        this.factory = factory2;
        this.index = index;
        this.label = label;
    }

    @Override
    public Annotation getAnnotation() {
        return this.label;
    }

    @Override
    public Class getType() {
        return this.factory.getParameterTypes()[this.index];
    }

    @Override
    public Class getDependent() {
        return Reflector.getParameterDependent(this.factory, this.index);
    }

    @Override
    public Class[] getDependents() {
        return Reflector.getParameterDependents(this.factory, this.index);
    }

    @Override
    public Class getDeclaringClass() {
        return this.owner;
    }

    @Override
    public Object get(Object source2) {
        return null;
    }

    @Override
    public void set(Object source2, Object value) {
    }

    public <A extends Annotation> A getAnnotation(Class<A> type) {
        for (Annotation label : this.labels) {
            Class<? extends Annotation> expect = label.annotationType();
            if (!expect.equals(type)) continue;
            return (A)label;
        }
        return null;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("parameter %s of constructor %s", this.index, this.factory);
    }

    @Override
    public abstract String getName();
}

