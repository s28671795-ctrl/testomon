/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.jaxb;

import java.io.Writer;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public final class EscapeHandlerInvocationHandler
implements InvocationHandler {
    private Object target;

    public EscapeHandlerInvocationHandler(Object obj) {
        this.target = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
        Object result = null;
        if ("escape".equals(method2.getName()) && args2.length == 5) {
            if ((Integer)args2[1] == 0 && (Integer)args2[2] == 0) {
                Writer writer = (Writer)args2[4];
                writer.write("");
                return null;
            }
            result = method2.invoke(this.target, args2);
        }
        return result;
    }
}

