/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.lang.reflect.Proxy;

public interface ClassUnwrapper {
    public Class<?> getRealClass(Object var1);

    public Class<?> getRealClassFromClass(Class<?> var1);

    default public Object getRealObject(Object o) {
        return o instanceof Proxy ? Proxy.getInvocationHandler(o) : o;
    }
}

