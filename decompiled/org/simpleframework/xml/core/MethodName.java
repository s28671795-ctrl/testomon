/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.reflect.Method;
import org.simpleframework.xml.core.MethodType;

class MethodName {
    private MethodType type;
    private Method method;
    private String name;

    public MethodName(Method method2, MethodType type, String name) {
        this.method = method2;
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public MethodType getType() {
        return this.type;
    }

    public Method getMethod() {
        return this.method;
    }
}

