/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Loader {
    private static final Logger LOG = LoggerFactory.getLogger(Loader.class);

    private Loader() {
    }

    public static InputStream loadInputStream(ClassLoader loader, String resource) throws WSSecurityException, IOException {
        InputStream is = null;
        if (resource != null) {
            URL url2 = null;
            try {
                url2 = new URL(resource);
            }
            catch (MalformedURLException malformedURLException) {
                // empty catch block
            }
            if (url2 == null) {
                url2 = Loader.getResource(loader, resource);
            }
            if (url2 != null) {
                is = url2.openStream();
            }
            if (is == null) {
                try {
                    is = Files.newInputStream(Paths.get(resource, new String[0]), new OpenOption[0]);
                }
                catch (Exception e) {
                    LOG.debug(e.getMessage(), e);
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "resourceNotFound", new Object[]{resource});
                }
            }
        }
        return is;
    }

    public static URL getResource(String resource) {
        URL url2 = null;
        try {
            ClassLoader classLoader = Loader.getTCL();
            if (classLoader != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Trying to find [" + resource + "] using " + classLoader + " class loader.");
                }
                if ((url2 = classLoader.getResource(resource)) == null && resource.startsWith("/")) {
                    url2 = classLoader.getResource(resource.substring(1));
                }
                if (url2 != null) {
                    return url2;
                }
            }
        }
        catch (Exception e) {
            LOG.warn("Caught Exception while in Loader.getResource. This may be innocuous.", e);
        }
        ClassLoader cluClassloader = Loader.class.getClassLoader();
        if (cluClassloader == null) {
            cluClassloader = ClassLoader.getSystemClassLoader();
        }
        if ((url2 = cluClassloader.getResource(resource)) == null && resource.startsWith("/")) {
            url2 = cluClassloader.getResource(resource.substring(1));
        }
        if (url2 != null) {
            return url2;
        }
        LOG.debug("Trying to find [{}] using ClassLoader.getSystemResource().", (Object)resource);
        return ClassLoader.getSystemResource(resource);
    }

    public static URL getResource(ClassLoader loader, String resource) {
        URL url2 = null;
        try {
            if (loader != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Trying to find [" + resource + "] using " + loader + " class loader.");
                }
                if ((url2 = loader.getResource(resource)) == null && resource.startsWith("/")) {
                    url2 = loader.getResource(resource.substring(1));
                }
                if (url2 != null) {
                    return url2;
                }
            }
        }
        catch (Exception e) {
            LOG.warn("Caught Exception while in Loader.getResource. This may be innocuous.", e);
        }
        return Loader.getResource(resource);
    }

    public static InputStream getResourceAsStream(String resourceName) {
        URL url2 = Loader.getResource(resourceName);
        try {
            return url2 != null ? url2.openStream() : null;
        }
        catch (IOException e) {
            LOG.debug(e.getMessage(), e);
            return null;
        }
    }

    public static ClassLoader getTCL() throws IllegalAccessException, InvocationTargetException {
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

            @Override
            public ClassLoader run() {
                return Thread.currentThread().getContextClassLoader();
            }
        });
    }

    public static ClassLoader getClassLoader(final Class<?> clazz) {
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

            @Override
            public ClassLoader run() {
                return clazz.getClassLoader();
            }
        });
    }

    public static Class<?> loadClass(ClassLoader loader, String clazz) throws ClassNotFoundException {
        try {
            Class<?> c;
            if (loader != null && (c = loader.loadClass(clazz)) != null) {
                return c;
            }
        }
        catch (Exception e) {
            LOG.warn(e.getMessage(), e);
        }
        return Loader.loadClass(clazz, true);
    }

    public static <T> Class<? extends T> loadClass(ClassLoader loader, String clazz, Class<T> type) throws ClassNotFoundException {
        try {
            Class<?> c;
            if (loader != null && (c = loader.loadClass(clazz)) != null) {
                return c.asSubclass(type);
            }
        }
        catch (Exception e) {
            LOG.warn(e.getMessage(), e);
        }
        return Loader.loadClass(clazz, true, type);
    }

    public static Class<?> loadClass(String clazz) throws ClassNotFoundException {
        return Loader.loadClass(clazz, true);
    }

    public static <T> Class<? extends T> loadClass(String clazz, Class<T> type) throws ClassNotFoundException {
        return Loader.loadClass(clazz, true, type);
    }

    public static <T> Class<? extends T> loadClass(String clazz, boolean warn, Class<T> type) throws ClassNotFoundException {
        return Loader.loadClass(clazz, warn).asSubclass(type);
    }

    public static Class<?> loadClass(String clazz, boolean warn) throws ClassNotFoundException {
        try {
            Class<?> c;
            ClassLoader tcl = Loader.getTCL();
            if (tcl != null && (c = tcl.loadClass(clazz)) != null) {
                return c;
            }
        }
        catch (Exception e) {
            if (warn) {
                LOG.warn(e.getMessage(), e);
            }
            LOG.debug(e.getMessage(), e);
        }
        return Loader.loadClass2(clazz, null);
    }

    private static Class<?> loadClass2(String className, Class<?> callingClass) throws ClassNotFoundException {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            block5: {
                try {
                    if (Loader.class.getClassLoader() != null) {
                        return Loader.class.getClassLoader().loadClass(className);
                    }
                }
                catch (ClassNotFoundException exc) {
                    if (callingClass == null || callingClass.getClassLoader() == null) break block5;
                    return callingClass.getClassLoader().loadClass(className);
                }
            }
            throw ex;
        }
    }
}

