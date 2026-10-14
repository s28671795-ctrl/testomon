/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.DynamicAccessPermission;

public abstract class DelegateInvocationHandlerImpl {
    private DelegateInvocationHandlerImpl() {
    }

    public static InvocationHandler create(final Object delegate) {
        SecurityManager s2 = System.getSecurityManager();
        if (s2 != null) {
            s2.checkPermission(new DynamicAccessPermission("access"));
        }
        return new InvocationHandler(){

            @Override
            public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
                try {
                    return method2.invoke(delegate, args2);
                }
                catch (InvocationTargetException ite) {
                    throw ite.getCause();
                }
            }
        };
    }
}

