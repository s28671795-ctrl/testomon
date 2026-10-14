/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Map;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.core.AnnotationHandler;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.Support;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.Verbosity;

class AnnotationFactory {
    private final Format format;
    private final boolean required;

    public AnnotationFactory(Detail detail, Support support) {
        this.required = detail.isRequired();
        this.format = support.getFormat();
    }

    public Annotation getInstance(Class type, Class[] dependents) throws Exception {
        ClassLoader loader = this.getClassLoader();
        if (Map.class.isAssignableFrom(type)) {
            if (this.isPrimitiveKey(dependents) && this.isAttribute()) {
                return this.getInstance(loader, ElementMap.class, true);
            }
            return this.getInstance(loader, ElementMap.class);
        }
        if (Collection.class.isAssignableFrom(type)) {
            return this.getInstance(loader, ElementList.class);
        }
        return this.getInstance(type);
    }

    private Annotation getInstance(Class type) throws Exception {
        ClassLoader loader = this.getClassLoader();
        Class<?> entry = type.getComponentType();
        if (type.isArray()) {
            if (this.isPrimitive(entry)) {
                return this.getInstance(loader, Element.class);
            }
            return this.getInstance(loader, ElementArray.class);
        }
        if (this.isPrimitive(type) && this.isAttribute()) {
            return this.getInstance(loader, Attribute.class);
        }
        return this.getInstance(loader, Element.class);
    }

    private Annotation getInstance(ClassLoader loader, Class label) throws Exception {
        return this.getInstance(loader, label, false);
    }

    private Annotation getInstance(ClassLoader loader, Class label, boolean attribute) throws Exception {
        AnnotationHandler handler = new AnnotationHandler(label, this.required, attribute);
        Class[] list = new Class[]{label};
        return (Annotation)Proxy.newProxyInstance(loader, list, handler);
    }

    private ClassLoader getClassLoader() throws Exception {
        return AnnotationFactory.class.getClassLoader();
    }

    private boolean isPrimitiveKey(Class[] dependents) {
        if (dependents != null && dependents.length > 0) {
            Class parent = dependents[0].getSuperclass();
            Class type = dependents[0];
            if (parent != null) {
                if (parent.isEnum()) {
                    return true;
                }
                if (type.isEnum()) {
                    return true;
                }
            }
            return this.isPrimitive(type);
        }
        return false;
    }

    private boolean isPrimitive(Class type) {
        if (Number.class.isAssignableFrom(type)) {
            return true;
        }
        if (type == Boolean.class) {
            return true;
        }
        if (type == Character.class) {
            return true;
        }
        return type.isPrimitive();
    }

    private boolean isAttribute() {
        Verbosity verbosity = this.format.getVerbosity();
        if (verbosity != null) {
            return verbosity == Verbosity.LOW;
        }
        return false;
    }
}

