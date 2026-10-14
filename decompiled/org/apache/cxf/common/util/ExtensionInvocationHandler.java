/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ExtensionInvocationHandler
implements InvocationHandler {
    private Object obj;

    public ExtensionInvocationHandler(Object o) {
        this.obj = o;
    }

    @Override
    public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
        if (method2.getDeclaringClass().isAssignableFrom(this.obj.getClass())) {
            return method2.invoke(this.obj, args2);
        }
        Method m4 = this.obj.getClass().getMethod(method2.getName(), method2.getParameterTypes());
        return m4.invoke(this.obj, args2);
    }
}

