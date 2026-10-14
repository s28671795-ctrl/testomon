/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.MethodException;
import org.simpleframework.xml.core.MethodPart;

class MethodContact
implements Contact {
    private Annotation label;
    private MethodPart set;
    private MethodPart get;
    private Class[] items;
    private Class owner;
    private Class item;
    private Class type;
    private String name;

    public MethodContact(MethodPart get2) {
        this(get2, null);
    }

    public MethodContact(MethodPart get2, MethodPart set) {
        this.owner = get2.getDeclaringClass();
        this.label = get2.getAnnotation();
        this.items = get2.getDependents();
        this.item = get2.getDependent();
        this.type = get2.getType();
        this.name = get2.getName();
        this.set = set;
        this.get = get2;
    }

    @Override
    public boolean isReadOnly() {
        return this.set == null;
    }

    public MethodPart getRead() {
        return this.get;
    }

    public MethodPart getWrite() {
        return this.set;
    }

    @Override
    public Annotation getAnnotation() {
        return this.label;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> type) {
        T result = this.get.getAnnotation(type);
        if (type == this.label.annotationType()) {
            return (T)this.label;
        }
        if (result == null && this.set != null) {
            return this.set.getAnnotation(type);
        }
        return result;
    }

    @Override
    public Class getType() {
        return this.type;
    }

    @Override
    public Class getDependent() {
        return this.item;
    }

    @Override
    public Class[] getDependents() {
        return this.items;
    }

    @Override
    public Class getDeclaringClass() {
        return this.owner;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void set(Object source2, Object value) throws Exception {
        Method method2 = this.get.getMethod();
        Class<?> type = method2.getDeclaringClass();
        if (this.set == null) {
            throw new MethodException("Property '%s' is read only in %s", this.name, type);
        }
        this.set.getMethod().invoke(source2, value);
    }

    @Override
    public Object get(Object source2) throws Exception {
        return this.get.getMethod().invoke(source2, new Object[0]);
    }

    @Override
    public String toString() {
        return String.format("method '%s'", this.name);
    }
}

