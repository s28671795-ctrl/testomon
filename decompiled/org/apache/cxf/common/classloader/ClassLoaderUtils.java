/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class ClassLoaderUtils {
    private static final boolean SKIP_SM = System.getSecurityManager() == null;

    private ClassLoaderUtils() {
    }

    public static ClassLoaderHolder setThreadContextClassloader(final ClassLoader newLoader) {
        if (SKIP_SM) {
            Thread thread2 = Thread.currentThread();
            ClassLoader l = thread2.getContextClassLoader();
            thread2.setContextClassLoader(newLoader);
            return new ClassLoaderHolder(l, thread2);
        }
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoaderHolder>(){

            @Override
            public ClassLoaderHolder run() {
                Thread thread2 = Thread.currentThread();
                ClassLoader l = thread2.getContextClassLoader();
                thread2.setContextClassLoader(newLoader);
                return new ClassLoaderHolder(l, thread2);
            }
        });
    }

    public static ClassLoader getURLClassLoader(final URL[] urls2, final ClassLoader parent) {
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

            @Override
            public ClassLoader run() {
                return new URLClassLoader(urls2, parent);
            }
        });
    }

    public static ClassLoader getURLClassLoader(List<URL> urlList, ClassLoader parent) {
        return ClassLoaderUtils.getURLClassLoader(urlList.toArray(new URL[0]), parent);
    }

    public static URL getResource(String resourceName, Class<?> callingClass) {
        ClassLoader cl;
        ClassLoader cluClassloader;
        ClassLoader contextClassLoader = ClassLoaderUtils.getContextClassLoader();
        URL url2 = contextClassLoader.getResource(resourceName);
        if (url2 == null && resourceName.startsWith("/")) {
            url2 = contextClassLoader.getResource(resourceName.substring(1));
        }
        if ((cluClassloader = ClassLoaderUtils.class.getClassLoader()) == null) {
            cluClassloader = ClassLoader.getSystemClassLoader();
        }
        if (url2 == null) {
            url2 = cluClassloader.getResource(resourceName);
        }
        if (url2 == null && resourceName.startsWith("/")) {
            url2 = cluClassloader.getResource(resourceName.substring(1));
        }
        if (url2 == null && callingClass != null && (cl = callingClass.getClassLoader()) != null) {
            url2 = cl.getResource(resourceName);
        }
        if (url2 == null && callingClass != null) {
            url2 = callingClass.getResource(resourceName);
        }
        if (url2 == null && resourceName != null && resourceName.charAt(0) != '/') {
            return ClassLoaderUtils.getResource('/' + resourceName, callingClass);
        }
        return url2;
    }

    public static List<URL> getResources(String resourceName, Class<?> callingClass) {
        URL url2;
        ClassLoader cl;
        ClassLoader cluClassloader;
        ArrayList<URL> ret = new ArrayList<URL>();
        Enumeration<URL> urls2 = new Enumeration<URL>(){

            @Override
            public boolean hasMoreElements() {
                return false;
            }

            @Override
            public URL nextElement() {
                return null;
            }
        };
        try {
            urls2 = ClassLoaderUtils.getContextClassLoader().getResources(resourceName);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        if (!urls2.hasMoreElements() && resourceName.startsWith("/")) {
            try {
                urls2 = ClassLoaderUtils.getContextClassLoader().getResources(resourceName.substring(1));
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if ((cluClassloader = ClassLoaderUtils.class.getClassLoader()) == null) {
            cluClassloader = ClassLoader.getSystemClassLoader();
        }
        if (!urls2.hasMoreElements()) {
            try {
                urls2 = cluClassloader.getResources(resourceName);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if (!urls2.hasMoreElements() && resourceName.startsWith("/")) {
            try {
                urls2 = cluClassloader.getResources(resourceName.substring(1));
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if (!urls2.hasMoreElements() && (cl = callingClass.getClassLoader()) != null) {
            try {
                urls2 = cl.getResources(resourceName);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if (!urls2.hasMoreElements() && (url2 = callingClass.getResource(resourceName)) != null) {
            ret.add(url2);
        }
        while (urls2.hasMoreElements()) {
            ret.add(urls2.nextElement());
        }
        if (ret.isEmpty() && resourceName != null && resourceName.charAt(0) != '/') {
            return ClassLoaderUtils.getResources('/' + resourceName, callingClass);
        }
        return ret;
    }

    public static InputStream getResourceAsStream(String resourceName, Class<?> callingClass) {
        URL url2 = ClassLoaderUtils.getResource(resourceName, callingClass);
        try {
            return url2 != null ? url2.openStream() : null;
        }
        catch (IOException e) {
            return null;
        }
    }

    public static Class<?> loadClass(String className, Class<?> callingClass) throws ClassNotFoundException {
        try {
            ClassLoader cl = ClassLoaderUtils.getContextClassLoader();
            if (cl != null) {
                return cl.loadClass(className);
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        return ClassLoaderUtils.loadClass2(className, callingClass);
    }

    public static <T> Class<? extends T> loadClass(String className, Class<?> callingClass, Class<T> type) throws ClassNotFoundException {
        try {
            ClassLoader cl = ClassLoaderUtils.getContextClassLoader();
            if (cl != null) {
                return cl.loadClass(className).asSubclass(type);
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        return ClassLoaderUtils.loadClass2(className, callingClass).asSubclass(type);
    }

    public static String getClassLoaderName(Class<?> type) {
        ClassLoader loader = ClassLoaderUtils.getClassLoader(type);
        return loader == null ? "null" : loader.toString();
    }

    public static Class<?> loadClassFromContextLoader(String className) throws ClassNotFoundException {
        return ClassLoaderUtils.getContextClassLoader().loadClass(className);
    }

    private static Class<?> loadClass2(String className, Class<?> callingClass) throws ClassNotFoundException {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            block5: {
                try {
                    ClassLoader loader = ClassLoaderUtils.getClassLoader(ClassLoaderUtils.class);
                    if (loader != null) {
                        return loader.loadClass(className);
                    }
                }
                catch (ClassNotFoundException exc) {
                    ClassLoader callingClassLoader;
                    if (callingClass == null || (callingClassLoader = ClassLoaderUtils.getClassLoader(callingClass)) == null) break block5;
                    return callingClassLoader.loadClass(className);
                }
            }
            throw ex;
        }
    }

    static ClassLoader getContextClassLoader() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    ClassLoader loader = Thread.currentThread().getContextClassLoader();
                    return loader != null ? loader : ClassLoader.getSystemClassLoader();
                }
            });
        }
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader != null ? loader : ClassLoader.getSystemClassLoader();
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

    public static class ClassLoaderHolder {
        ClassLoader loader;
        Thread thread;

        ClassLoaderHolder(ClassLoader c, Thread thread2) {
            this.loader = c;
            this.thread = thread2;
        }

        public void reset() {
            if (SKIP_SM) {
                this.thread.setContextClassLoader(this.loader);
            } else {
                AccessController.doPrivileged(new PrivilegedAction<Void>(){

                    @Override
                    public Void run() {
                        thread.setContextClassLoader(loader);
                        return null;
                    }
                });
            }
        }
    }
}

