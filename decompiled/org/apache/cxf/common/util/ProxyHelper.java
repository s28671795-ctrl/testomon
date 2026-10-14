/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.CglibProxyHelper;
import org.apache.cxf.common.util.ProxyClassLoaderCache;
import org.apache.cxf.common.util.SortedArraySet;

public class ProxyHelper {
    static final ProxyHelper HELPER;
    private static final Logger LOG;
    protected ProxyClassLoaderCache proxyClassLoaderCache = new ProxyClassLoaderCache();

    protected ProxyHelper() {
    }

    protected Object getProxyInternal(ClassLoader loader, Class<?>[] interfaces, InvocationHandler handler) {
        ClassLoader combinedLoader = this.getClassLoaderForInterfaces(loader, interfaces);
        return Proxy.newProxyInstance(combinedLoader, interfaces, handler);
    }

    private ClassLoader getClassLoaderForInterfaces(ClassLoader loader, Class<?>[] interfaces) {
        if (this.canSeeAllInterfaces(loader, interfaces)) {
            LOG.log(Level.FINE, "current classloader " + loader + " can see all interface");
            return loader;
        }
        String sortedNameFromInterfaceArray = this.getSortedNameFromInterfaceArray(interfaces);
        ClassLoader cachedLoader = this.proxyClassLoaderCache.getProxyClassLoader(loader, interfaces);
        if (this.canSeeAllInterfaces(cachedLoader, interfaces)) {
            LOG.log(Level.FINE, "find required loader from ProxyClassLoader cache with key" + sortedNameFromInterfaceArray);
            return cachedLoader;
        }
        LOG.log(Level.FINE, "find a loader from ProxyClassLoader cache with interfaces " + sortedNameFromInterfaceArray + " but can't see all interfaces");
        for (Class<?> currentInterface : interfaces) {
            String ifName = currentInterface.getName();
            if (ifName.startsWith("org.apache.cxf") || ifName.startsWith("java")) continue;
            this.proxyClassLoaderCache.removeStaleProxyClassLoader(currentInterface);
            cachedLoader = this.proxyClassLoaderCache.getProxyClassLoader(loader, interfaces);
        }
        return cachedLoader;
    }

    private String getSortedNameFromInterfaceArray(Class<?>[] interfaces) {
        SortedArraySet<String> arraySet = new SortedArraySet<String>();
        for (Class<?> currentInterface : interfaces) {
            arraySet.add(currentInterface.getName() + ClassLoaderUtils.getClassLoaderName(currentInterface));
        }
        return arraySet.toString();
    }

    private boolean canSeeAllInterfaces(ClassLoader loader, Class<?>[] interfaces) {
        for (Class<?> currentInterface : interfaces) {
            String ifName = currentInterface.getName();
            try {
                Class<?> ifClass = Class.forName(ifName, true, loader);
                if (ifClass != currentInterface) {
                    return false;
                }
                for (Method m4 : ifClass.getMethods()) {
                    Class<?> returnType = m4.getReturnType();
                    if (!returnType.isPrimitive()) {
                        Class.forName(returnType.getName(), true, loader);
                    }
                    for (Class<?> p : m4.getParameterTypes()) {
                        if (p.isPrimitive()) continue;
                        Class.forName(p.getName(), true, loader);
                    }
                }
            }
            catch (ClassNotFoundException | NoClassDefFoundError e) {
                return false;
            }
        }
        return true;
    }

    public static Object getProxy(ClassLoader loader, Class<?>[] interfaces, InvocationHandler handler) {
        return HELPER.getProxyInternal(loader, interfaces, handler);
    }

    static {
        ProxyHelper theHelper = null;
        try {
            theHelper = new CglibProxyHelper();
        }
        catch (Throwable ex) {
            theHelper = new ProxyHelper();
        }
        HELPER = theHelper;
        LOG = LogUtils.getL7dLogger(ProxyHelper.class);
    }
}

