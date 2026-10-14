/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Reflector;
import org.simpleframework.xml.util.Cache;
import org.simpleframework.xml.util.ConcurrentCache;

class FieldContact
implements Contact {
    private final Cache<Annotation> cache = new ConcurrentCache<Annotation>();
    private final Annotation[] list;
    private final Annotation label;
    private final Field field;
    private final String name;
    private final int modifier;

    public FieldContact(Field field2, Annotation label, Annotation[] list) {
        this.modifier = field2.getModifiers();
        this.name = field2.getName();
        this.label = label;
        this.field = field2;
        this.list = list;
    }

    @Override
    public boolean isReadOnly() {
        return !this.isStatic() && this.isFinal();
    }

    public boolean isStatic() {
        return Modifier.isStatic(this.modifier);
    }

    public boolean isFinal() {
        return Modifier.isFinal(this.modifier);
    }

    @Override
    public Class getType() {
        return this.field.getType();
    }

    @Override
    public Class getDependent() {
        return Reflector.getDependent(this.field);
    }

    @Override
    public Class[] getDependents() {
        return Reflector.getDependents(this.field);
    }

    @Override
    public Class getDeclaringClass() {
        return this.field.getDeclaringClass();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Annotation getAnnotation() {
        return this.label;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> type) {
        if (type == this.label.annotationType()) {
            return (T)this.label;
        }
        return this.getCache(type);
    }

    private <T extends Annotation> T getCache(Class<T> type) {
        if (this.cache.isEmpty()) {
            for (Annotation entry : this.list) {
                Class<? extends Annotation> key = entry.annotationType();
                this.cache.cache(key, entry);
            }
        }
        return (T)this.cache.fetch(type);
    }

    @Override
    public void set(Object source2, Object value) throws Exception {
        if (!this.isFinal()) {
            this.field.set(source2, value);
        }
    }

    @Override
    public Object get(Object source2) throws Exception {
        return this.field.get(source2);
    }

    @Override
    public String toString() {
        return String.format("field '%s' %s", this.getName(), this.field.toString());
    }
}

