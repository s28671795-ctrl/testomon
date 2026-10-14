/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

class Comparer {
    private static final String NAME = "name";
    private final String[] ignore;

    public Comparer() {
        this(NAME);
    }

    public Comparer(String ... ignore) {
        this.ignore = ignore;
    }

    public boolean equals(Annotation left, Annotation right) throws Exception {
        Class<? extends Annotation> type = left.annotationType();
        Class<? extends Annotation> expect = right.annotationType();
        Method[] list = type.getDeclaredMethods();
        if (type.equals(expect)) {
            for (Method method2 : list) {
                Object other;
                Object value;
                if (this.isIgnore(method2) || (value = method2.invoke((Object)left, new Object[0])).equals(other = method2.invoke((Object)right, new Object[0]))) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean isIgnore(Method method2) {
        String name = method2.getName();
        if (this.ignore != null) {
            for (String value : this.ignore) {
                if (!name.equals(value)) continue;
                return true;
            }
        }
        return false;
    }
}

