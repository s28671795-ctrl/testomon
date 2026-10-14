/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.simpleframework.xml.core.AnnotationFactory;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.GetPart;
import org.simpleframework.xml.core.MethodException;
import org.simpleframework.xml.core.MethodName;
import org.simpleframework.xml.core.MethodPart;
import org.simpleframework.xml.core.MethodType;
import org.simpleframework.xml.core.Reflector;
import org.simpleframework.xml.core.SetPart;
import org.simpleframework.xml.core.Support;

class MethodPartFactory {
    private final AnnotationFactory factory;

    public MethodPartFactory(Detail detail, Support support) {
        this.factory = new AnnotationFactory(detail, support);
    }

    public MethodPart getInstance(Method method2, Annotation[] list) throws Exception {
        Annotation label = this.getAnnotation(method2);
        if (label != null) {
            return this.getInstance(method2, label, list);
        }
        return null;
    }

    public MethodPart getInstance(Method method2, Annotation label, Annotation[] list) throws Exception {
        MethodName name = this.getName(method2, label);
        MethodType type = name.getType();
        if (type == MethodType.SET) {
            return new SetPart(name, label, list);
        }
        return new GetPart(name, label, list);
    }

    private MethodName getName(Method method2, Annotation label) throws Exception {
        MethodType type = this.getMethodType(method2);
        if (type == MethodType.GET) {
            return this.getRead(method2, type);
        }
        if (type == MethodType.IS) {
            return this.getRead(method2, type);
        }
        if (type == MethodType.SET) {
            return this.getWrite(method2, type);
        }
        throw new MethodException("Annotation %s must mark a set or get method", label);
    }

    private MethodType getMethodType(Method method2) {
        String name = method2.getName();
        if (name.startsWith("get")) {
            return MethodType.GET;
        }
        if (name.startsWith("is")) {
            return MethodType.IS;
        }
        if (name.startsWith("set")) {
            return MethodType.SET;
        }
        return MethodType.NONE;
    }

    private Annotation getAnnotation(Method method2) throws Exception {
        Class[] dependents = this.getDependents(method2);
        Class type = this.getType(method2);
        if (type != null) {
            return this.factory.getInstance(type, dependents);
        }
        return null;
    }

    private Class[] getDependents(Method method2) throws Exception {
        MethodType type = this.getMethodType(method2);
        if (type == MethodType.SET) {
            return Reflector.getParameterDependents(method2, 0);
        }
        if (type == MethodType.GET) {
            return Reflector.getReturnDependents(method2);
        }
        if (type == MethodType.IS) {
            return Reflector.getReturnDependents(method2);
        }
        return null;
    }

    public Class getType(Method method2) throws Exception {
        MethodType type = this.getMethodType(method2);
        if (type == MethodType.SET) {
            return this.getParameterType(method2);
        }
        if (type == MethodType.GET) {
            return this.getReturnType(method2);
        }
        if (type == MethodType.IS) {
            return this.getReturnType(method2);
        }
        return null;
    }

    private Class getParameterType(Method method2) throws Exception {
        Class<?>[] list = method2.getParameterTypes();
        if (list.length == 1) {
            return method2.getParameterTypes()[0];
        }
        return null;
    }

    private Class getReturnType(Method method2) throws Exception {
        Class<?>[] list = method2.getParameterTypes();
        if (list.length == 0) {
            return method2.getReturnType();
        }
        return null;
    }

    private MethodName getRead(Method method2, MethodType type) throws Exception {
        Class<?>[] list = method2.getParameterTypes();
        String real = method2.getName();
        if (list.length != 0) {
            throw new MethodException("Get method %s is not a valid property", method2);
        }
        String name = this.getTypeName(real, type);
        if (name == null) {
            throw new MethodException("Could not get name for %s", method2);
        }
        return new MethodName(method2, type, name);
    }

    private MethodName getWrite(Method method2, MethodType type) throws Exception {
        Class<?>[] list = method2.getParameterTypes();
        String real = method2.getName();
        if (list.length != 1) {
            throw new MethodException("Set method %s is not a valid property", method2);
        }
        String name = this.getTypeName(real, type);
        if (name == null) {
            throw new MethodException("Could not get name for %s", method2);
        }
        return new MethodName(method2, type, name);
    }

    private String getTypeName(String name, MethodType type) {
        int prefix = type.getPrefix();
        int size = name.length();
        if (size > prefix) {
            name = name.substring(prefix, size);
        }
        return Reflector.getName(name);
    }
}

