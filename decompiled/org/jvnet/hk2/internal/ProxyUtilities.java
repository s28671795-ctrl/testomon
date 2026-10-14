/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ProxyCtl;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.internal.DelegatingClassLoader;
import org.jvnet.hk2.internal.MethodInterceptorImpl;
import org.jvnet.hk2.internal.MethodInterceptorInvocationHandler;
import org.jvnet.hk2.internal.ServiceHandleImpl;
import org.jvnet.hk2.internal.ServiceLocatorImpl;
import org.jvnet.hk2.internal.Utilities;

public class ProxyUtilities {
    private static final Object proxyCreationLock = new Object();
    private final HashMap<ClassLoader, DelegatingClassLoader> superClassToDelegator = new HashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private <T> T secureCreate(final Class<?> superclass, final Class<?>[] interfaces, final MethodHandler callback, boolean useJDKProxy, ServiceLocator anchor) {
        DelegatingClassLoader initDelegatingLoader;
        final ClassLoader loader = AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

            @Override
            public ClassLoader run() {
                ClassLoader retVal = superclass.getClassLoader();
                if (retVal == null) {
                    try {
                        retVal = ClassLoader.getSystemClassLoader();
                    }
                    catch (SecurityException se) {
                        throw new IllegalStateException("Insufficient privilege to get system classloader while looking for classloader of " + superclass.getName(), se);
                    }
                }
                if (retVal == null) {
                    throw new IllegalStateException("Could not find system classloader or classloader of " + superclass.getName());
                }
                return retVal;
            }
        });
        HashMap<ClassLoader, DelegatingClassLoader> hashMap = this.superClassToDelegator;
        synchronized (hashMap) {
            initDelegatingLoader = this.superClassToDelegator.get(loader);
            if (initDelegatingLoader == null) {
                initDelegatingLoader = AccessController.doPrivileged(new PrivilegedAction<DelegatingClassLoader>(){

                    @Override
                    public DelegatingClassLoader run() {
                        return new DelegatingClassLoader(loader, ProxyFactory.class.getClassLoader(), ProxyCtl.class.getClassLoader());
                    }
                });
                this.superClassToDelegator.put(loader, initDelegatingLoader);
            }
        }
        final DelegatingClassLoader delegatingLoader = initDelegatingLoader;
        if (useJDKProxy) {
            return AccessController.doPrivileged(new PrivilegedAction<T>(){

                @Override
                public T run() {
                    return Proxy.newProxyInstance(delegatingLoader, interfaces, new MethodInterceptorInvocationHandler(callback));
                }
            });
        }
        return AccessController.doPrivileged(new PrivilegedAction<T>(){

            /*
             * Loose catch block
             * Enabled aggressive exception aggregation
             */
            @Override
            public T run() {
                Object object = proxyCreationLock;
                synchronized (object) {
                    ProxyFactory.ClassLoaderProvider originalProvider = ProxyFactory.classLoaderProvider;
                    ProxyFactory.classLoaderProvider = new ProxyFactory.ClassLoaderProvider(){

                        @Override
                        public ClassLoader get(ProxyFactory arg0) {
                            return delegatingLoader;
                        }
                    };
                    ProxyFactory proxyFactory = new ProxyFactory();
                    proxyFactory.setInterfaces(interfaces);
                    proxyFactory.setSuperclass(superclass);
                    Class<?> proxyClass = proxyFactory.createClass();
                    Object proxy = proxyClass.newInstance();
                    ((ProxyObject)proxy).setHandler(callback);
                    Object obj = proxy;
                    return obj;
                    {
                        catch (Exception e1) {
                            throw new RuntimeException(e1);
                        }
                    }
                    finally {
                        ProxyFactory.classLoaderProvider = originalProvider;
                    }
                }
            }
        });
    }

    public <T> T generateProxy(Class<?> requestedClass, ServiceLocatorImpl locator, ActiveDescriptor<T> root, ServiceHandleImpl<T> handle2, Injectee injectee) {
        T proxy;
        Class[] iFaces;
        Class<?> proxyClass;
        boolean isInterface;
        boolean bl = isInterface = requestedClass == null ? false : requestedClass.isInterface();
        if (isInterface) {
            proxyClass = requestedClass;
            iFaces = new Class[]{proxyClass, ProxyCtl.class};
        } else {
            proxyClass = Utilities.getFactoryAwareImplementationClass(root);
            iFaces = Utilities.getInterfacesForProxy(root.getContractTypes());
        }
        try {
            proxy = this.secureCreate(proxyClass, iFaces, new MethodInterceptorImpl(locator, root, handle2, injectee), isInterface, locator);
        }
        catch (Throwable th) {
            IllegalArgumentException addMe = new IllegalArgumentException("While attempting to create a Proxy for " + proxyClass.getName() + " in scope " + root.getScope() + " an error occured while creating the proxy");
            if (th instanceof MultiException) {
                MultiException me = (MultiException)th;
                me.addError(addMe);
                throw me;
            }
            MultiException me = new MultiException(th);
            me.addError(addMe);
            throw me;
        }
        return proxy;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void releaseCache() {
        HashMap<ClassLoader, DelegatingClassLoader> hashMap = this.superClassToDelegator;
        synchronized (hashMap) {
            this.superClassToDelegator.clear();
        }
    }
}

