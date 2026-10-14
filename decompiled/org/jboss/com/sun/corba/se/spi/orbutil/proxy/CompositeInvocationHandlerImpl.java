/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.rmi.PortableRemoteObject;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.spi.orbutil.proxy.CompositeInvocationHandler;

public class CompositeInvocationHandlerImpl
implements CompositeInvocationHandler {
    private Map<Class<?>, InvocationHandler> classToInvocationHandler = new LinkedHashMap();
    private InvocationHandler defaultHandler = null;

    @Override
    public void addInvocationHandler(Class<?> interf, InvocationHandler handler) {
        this.classToInvocationHandler.put(interf, handler);
    }

    @Override
    public void setDefaultHandler(InvocationHandler handler) {
        this.defaultHandler = handler;
    }

    @Override
    public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
        Class<?> cls = method2.getDeclaringClass();
        InvocationHandler handler = this.classToInvocationHandler.get(cls);
        if (handler == null) {
            if (this.defaultHandler != null) {
                handler = this.defaultHandler;
            } else {
                ORBUtilSystemException wrapper = ORBUtilSystemException.get("util");
                throw wrapper.noInvocationHandler("\"" + method2.toString() + "\"");
            }
        }
        Object result = handler.invoke(proxy, method2, args2);
        Class<?> returnType = method2.getReturnType();
        if (result == null) {
            return result;
        }
        if (returnType.isPrimitive() || returnType.isAssignableFrom(result.getClass())) {
            return result;
        }
        return PortableRemoteObject.narrow(result, returnType);
    }
}

