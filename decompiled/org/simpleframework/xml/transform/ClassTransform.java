/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.Transform;

class ClassTransform
implements Transform<Class> {
    private static final String INTEGER = "int";
    private static final String DOUBLE = "double";
    private static final String FLOAT = "float";
    private static final String BOOLEAN = "boolean";
    private static final String SHORT = "short";
    private static final String CHARACTER = "char";
    private static final String LONG = "long";
    private static final String BYTE = "byte";
    private static final String VOID = "void";

    ClassTransform() {
    }

    @Override
    public Class read(String target) throws Exception {
        Class type = this.readPrimitive(target);
        if (type == null) {
            ClassLoader loader = ClassTransform.getClassLoader();
            if (loader == null) {
                loader = this.getCallerClassLoader();
            }
            return loader.loadClass(target);
        }
        return type;
    }

    private Class readPrimitive(String target) throws Exception {
        if (target.equals(BYTE)) {
            return Byte.TYPE;
        }
        if (target.equals(SHORT)) {
            return Short.TYPE;
        }
        if (target.equals(INTEGER)) {
            return Integer.TYPE;
        }
        if (target.equals(LONG)) {
            return Long.TYPE;
        }
        if (target.equals(CHARACTER)) {
            return Character.TYPE;
        }
        if (target.equals(FLOAT)) {
            return Float.TYPE;
        }
        if (target.equals(DOUBLE)) {
            return Double.TYPE;
        }
        if (target.equals(BOOLEAN)) {
            return Boolean.TYPE;
        }
        if (target.equals(VOID)) {
            return Void.TYPE;
        }
        return null;
    }

    @Override
    public String write(Class target) throws Exception {
        return target.getName();
    }

    private ClassLoader getCallerClassLoader() {
        return this.getClass().getClassLoader();
    }

    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}

