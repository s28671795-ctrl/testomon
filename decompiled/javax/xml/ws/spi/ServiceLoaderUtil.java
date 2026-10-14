/*
 * Decompiled with CFR 0.152.
 */
package javax.xml.ws.spi;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

class ServiceLoaderUtil {
    ServiceLoaderUtil() {
    }

    static <P, T extends Exception> P firstByServiceLoader(Class<P> spiClass, Logger logger2, ExceptionHandler<T> handler) throws T {
        logger2.log(Level.FINE, "Using java.util.ServiceLoader to find {0}", spiClass.getName());
        try {
            ServiceLoader<P> serviceLoader = ServiceLoader.load(spiClass);
            Iterator<P> iterator2 = serviceLoader.iterator();
            if (iterator2.hasNext()) {
                P impl = iterator2.next();
                logger2.fine("ServiceProvider loading Facility used; returning object [" + impl.getClass().getName() + "]");
                return impl;
            }
        }
        catch (Throwable t2) {
            throw handler.createException(t2, "Error while searching for service [" + spiClass.getName() + "]");
        }
        return null;
    }

    static void checkPackageAccess(String className) {
        int i;
        SecurityManager s2 = System.getSecurityManager();
        if (s2 != null && (i = className.lastIndexOf(46)) != -1) {
            s2.checkPackageAccess(className.substring(0, i));
        }
    }

    static Class nullSafeLoadClass(String className, ClassLoader classLoader) throws ClassNotFoundException {
        if (classLoader == null) {
            return Class.forName(className);
        }
        return classLoader.loadClass(className);
    }

    static <T extends Exception> Object newInstance(String className, String defaultImplClassName, ClassLoader classLoader, ExceptionHandler<T> handler) throws T {
        try {
            return ServiceLoaderUtil.safeLoadClass(className, defaultImplClassName, classLoader).newInstance();
        }
        catch (ClassNotFoundException x) {
            throw handler.createException(x, "Provider " + className + " not found");
        }
        catch (Exception x) {
            throw handler.createException(x, "Provider " + className + " could not be instantiated: " + x);
        }
    }

    static Class safeLoadClass(String className, String defaultImplClassName, ClassLoader classLoader) throws ClassNotFoundException {
        try {
            ServiceLoaderUtil.checkPackageAccess(className);
        }
        catch (SecurityException se) {
            if (defaultImplClassName != null && defaultImplClassName.equals(className)) {
                return Class.forName(className);
            }
            throw se;
        }
        return ServiceLoaderUtil.nullSafeLoadClass(className, classLoader);
    }

    static <T extends Exception> ClassLoader contextClassLoader(ExceptionHandler<T> exceptionHandler) throws T {
        try {
            return Thread.currentThread().getContextClassLoader();
        }
        catch (Exception x) {
            throw exceptionHandler.createException(x, x.toString());
        }
    }

    static abstract class ExceptionHandler<T extends Exception> {
        ExceptionHandler() {
        }

        public abstract T createException(Throwable var1, String var2);
    }
}

