/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ClassLoaderUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ClassLoaderUtils.class);

    private ClassLoaderUtils() {
    }

    public static URL getResource(String resourceName, Class<?> callingClass) {
        ClassLoader cl;
        ClassLoader cluClassloader;
        URL url2 = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (url2 == null && resourceName.startsWith("/")) {
            url2 = Thread.currentThread().getContextClassLoader().getResource(resourceName.substring(1));
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
        if (url2 == null && (cl = callingClass.getClassLoader()) != null) {
            url2 = cl.getResource(resourceName);
        }
        if (url2 == null) {
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
            urls2 = Thread.currentThread().getContextClassLoader().getResources(resourceName);
        }
        catch (IOException e) {
            LOG.debug(e.getMessage(), e);
        }
        if (!urls2.hasMoreElements() && resourceName.startsWith("/")) {
            try {
                urls2 = Thread.currentThread().getContextClassLoader().getResources(resourceName.substring(1));
            }
            catch (IOException e) {
                LOG.debug(e.getMessage(), e);
            }
        }
        if ((cluClassloader = ClassLoaderUtils.class.getClassLoader()) == null) {
            cluClassloader = ClassLoader.getSystemClassLoader();
        }
        if (!urls2.hasMoreElements()) {
            try {
                urls2 = cluClassloader.getResources(resourceName);
            }
            catch (IOException e) {
                LOG.debug(e.getMessage(), e);
            }
        }
        if (!urls2.hasMoreElements() && resourceName.startsWith("/")) {
            try {
                urls2 = cluClassloader.getResources(resourceName.substring(1));
            }
            catch (IOException e) {
                LOG.debug(e.getMessage(), e);
            }
        }
        if (!urls2.hasMoreElements() && (cl = callingClass.getClassLoader()) != null) {
            try {
                urls2 = cl.getResources(resourceName);
            }
            catch (IOException e) {
                LOG.debug(e.getMessage(), e);
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
            LOG.debug(e.getMessage(), e);
            return null;
        }
    }

    public static Class<?> loadClass(String className, Class<?> callingClass) throws ClassNotFoundException {
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl != null) {
                return cl.loadClass(className);
            }
        }
        catch (ClassNotFoundException e) {
            LOG.debug(e.getMessage(), e);
        }
        return ClassLoaderUtils.loadClass2(className, callingClass);
    }

    private static Class<?> loadClass2(String className, Class<?> callingClass) throws ClassNotFoundException {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            block5: {
                try {
                    if (ClassLoaderUtils.class.getClassLoader() != null) {
                        return ClassLoaderUtils.class.getClassLoader().loadClass(className);
                    }
                }
                catch (ClassNotFoundException exc) {
                    if (callingClass == null || callingClass.getClassLoader() == null) break block5;
                    return callingClass.getClassLoader().loadClass(className);
                }
            }
            LOG.debug(ex.getMessage(), ex);
            throw ex;
        }
    }
}

