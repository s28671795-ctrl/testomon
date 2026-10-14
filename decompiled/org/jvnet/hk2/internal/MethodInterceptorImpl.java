/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import javassist.util.proxy.MethodHandler;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Context;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ProxyCtl;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.jvnet.hk2.internal.ServiceHandleImpl;
import org.jvnet.hk2.internal.ServiceLocatorImpl;

public class MethodInterceptorImpl
implements MethodHandler {
    private static final String PROXY_MORE_METHOD_NAME = "__make";
    private final ServiceLocatorImpl locator;
    private final ActiveDescriptor<?> descriptor;
    private final ServiceHandleImpl<?> root;
    private final WeakReference<Injectee> myInjectee;
    private static final String EQUALS_NAME = "equals";

    MethodInterceptorImpl(ServiceLocatorImpl sli, ActiveDescriptor<?> descriptor, ServiceHandleImpl<?> root, Injectee injectee) {
        this.locator = sli;
        this.descriptor = descriptor;
        this.root = root;
        this.myInjectee = injectee != null ? new WeakReference<Injectee>(injectee) : null;
    }

    private Object internalInvoke(Object target, Method method2, Method proceed, Object[] params) throws Throwable {
        Context<?> context = this.locator.resolveContext(this.descriptor.getScopeAnnotation());
        Object service2 = context.findOrCreate(this.descriptor, this.root);
        if (service2 == null) {
            throw new MultiException(new IllegalStateException("Proxiable context " + context + " findOrCreate returned a null for descriptor " + this.descriptor + " and handle " + this.root));
        }
        if (method2.getName().equals(PROXY_MORE_METHOD_NAME)) {
            return service2;
        }
        if (MethodInterceptorImpl.isEquals(method2) && params.length == 1 && params[0] != null && params[0] instanceof ProxyCtl) {
            ProxyCtl equalsProxy = (ProxyCtl)params[0];
            params = new Object[]{equalsProxy.__make()};
        }
        return ReflectionHelper.invoke(service2, method2, params, this.locator.getNeutralContextClassLoader());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object invoke(Object target, Method method2, Method proceed, Object[] params) throws Throwable {
        Injectee ref;
        boolean pushed = false;
        if (this.root != null && this.myInjectee != null && (ref = (Injectee)this.myInjectee.get()) != null) {
            this.root.pushInjectee(ref);
            pushed = true;
        }
        try {
            Object object = this.internalInvoke(target, method2, proceed, params);
            return object;
        }
        finally {
            if (pushed) {
                this.root.popInjectee();
            }
        }
    }

    private static boolean isEquals(Method m4) {
        if (!m4.getName().equals(EQUALS_NAME)) {
            return false;
        }
        Class<?>[] params = m4.getParameterTypes();
        if (params == null || params.length != 1) {
            return false;
        }
        return Object.class.equals(params[0]);
    }
}

