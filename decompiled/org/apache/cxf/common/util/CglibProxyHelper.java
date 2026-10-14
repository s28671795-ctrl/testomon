/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.cglib.proxy.Callback
 *  net.sf.cglib.proxy.Enhancer
 *  net.sf.cglib.proxy.MethodInterceptor
 *  net.sf.cglib.proxy.MethodProxy
 */
package org.apache.cxf.common.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.cxf.common.util.ProxyHelper;

class CglibProxyHelper
extends ProxyHelper {
    CglibProxyHelper() throws Exception {
        Class.forName("net.sf.cglib.proxy.Enhancer");
        Class.forName("net.sf.cglib.proxy.MethodInterceptor");
        Class.forName("net.sf.cglib.proxy.MethodProxy");
    }

    @Override
    protected Object getProxyInternal(ClassLoader loader, Class<?>[] interfaces, final InvocationHandler h2) {
        Class<?> superClass = null;
        ArrayList theInterfaces = new ArrayList();
        for (Class<?> c : interfaces) {
            if (!c.isInterface()) {
                if (superClass != null) {
                    throw new IllegalArgumentException("Only a single superclass is supported");
                }
                superClass = c;
                continue;
            }
            theInterfaces.add(c);
        }
        if (superClass != null) {
            Enhancer enhancer = new Enhancer();
            enhancer.setClassLoader(loader);
            enhancer.setSuperclass(superClass);
            enhancer.setInterfaces(theInterfaces.toArray(new Class[0]));
            enhancer.setCallback((Callback)new MethodInterceptor(){

                public Object intercept(Object obj, Method method2, Object[] args2, MethodProxy proxy) throws Throwable {
                    return h2.invoke(obj, method2, args2);
                }
            });
            return enhancer.create();
        }
        return super.getProxyInternal(loader, interfaces, h2);
    }
}

