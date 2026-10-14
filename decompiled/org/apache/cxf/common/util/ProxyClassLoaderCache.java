/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ProxyClassLoader;

public class ProxyClassLoaderCache {
    private static final Logger LOG = LogUtils.getL7dLogger(ProxyClassLoaderCache.class);
    private static final ThreadLocal<ClassLoader> PARENT_CLASSLOADER = new ThreadLocal();
    private static final ThreadLocal<Class<?>[]> PROXY_INTERFACES = new ThreadLocal();
    private final ClassValue<ClassLoader> backend = new ClassValue<ClassLoader>(){

        @Override
        protected ClassLoader computeValue(Class<?> proxyInterface) {
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("can't find ProxyClassLoader from ClassValue Cache, will create a new one");
                LOG.fine("interface for new created ProxyClassLoader is " + proxyInterface.getName());
                LOG.fine("interface's classloader for new created ProxyClassLoader is " + ClassLoaderUtils.getClassLoaderName(proxyInterface));
            }
            return ProxyClassLoaderCache.this.createProxyClassLoader(proxyInterface);
        }
    };

    private ClassLoader createProxyClassLoader(Class<?> proxyInterface) {
        SecurityManager sm = System.getSecurityManager();
        ProxyClassLoader ret = sm == null ? new ProxyClassLoader(PARENT_CLASSLOADER.get(), PROXY_INTERFACES.get()) : AccessController.doPrivileged(new PrivilegedAction<ProxyClassLoader>(){

            @Override
            public ProxyClassLoader run() {
                return new ProxyClassLoader((ClassLoader)PARENT_CLASSLOADER.get(), (Class[])PROXY_INTERFACES.get());
            }
        });
        for (Class<?> currentInterface : PROXY_INTERFACES.get()) {
            ret.addLoader(ProxyClassLoaderCache.getClassLoader(currentInterface));
            if (!LOG.isLoggable(Level.FINE)) continue;
            LOG.fine("interface for new created ProxyClassLoader is " + currentInterface.getName());
            LOG.fine("interface's classloader for new created ProxyClassLoader is " + ProxyClassLoaderCache.getClassLoader(currentInterface));
        }
        return ret;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ClassLoader getProxyClassLoader(ClassLoader parent, Class<?>[] proxyInterfaces) {
        try {
            PARENT_CLASSLOADER.set(parent);
            PROXY_INTERFACES.set(proxyInterfaces);
            for (Class<?> currentInterface : proxyInterfaces) {
                String ifName = currentInterface.getName();
                if (LOG.isLoggable(Level.FINE)) {
                    LOG.fine("the interface we are checking is " + ifName);
                    LOG.fine("the interface's classloader we are checking is " + ProxyClassLoaderCache.getClassLoader(currentInterface));
                }
                if (ifName.startsWith("org.apache.cxf") || ifName.startsWith("java")) continue;
                if (LOG.isLoggable(Level.FINE)) {
                    LOG.fine("the customer interface is " + ifName + ". Will try to fetch it from Cache");
                }
                ClassLoader classLoader = this.backend.get(currentInterface);
                return classLoader;
            }
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("Non of interfaces are customer interface, retrive the last interface as key:" + proxyInterfaces[proxyInterfaces.length - 1].getName());
            }
            ClassLoader classLoader = this.backend.get(proxyInterfaces[proxyInterfaces.length - 1]);
            return classLoader;
        }
        finally {
            PARENT_CLASSLOADER.remove();
            PROXY_INTERFACES.remove();
        }
    }

    public void removeStaleProxyClassLoader(Class<?> proxyInterface) {
        this.backend.remove(proxyInterface);
    }

    private static ClassLoader getClassLoader(final Class<?> clazz) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    return clazz.getClassLoader();
                }
            });
        }
        return clazz.getClassLoader();
    }
}

