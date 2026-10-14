/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.util;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import org.jboss.sun.corba.Bridge;

class JDKClassLoader {
    private static final JDKClassLoaderCache classCache = new JDKClassLoaderCache();
    private static final Bridge bridge = AccessController.doPrivileged(new PrivilegedAction<Bridge>(){

        @Override
        public Bridge run() {
            return Bridge.get();
        }
    });

    JDKClassLoader() {
    }

    static Class<?> loadClass(Class<?> aClass, String className) throws ClassNotFoundException {
        if (className == null) {
            throw new NullPointerException();
        }
        if (className.length() == 0) {
            throw new ClassNotFoundException();
        }
        ClassLoader loader = aClass != null ? aClass.getClassLoader() : bridge.getLatestUserDefinedLoader();
        Object key = classCache.createKey(className, loader);
        if (classCache.knownToFail(key)) {
            throw new ClassNotFoundException(className);
        }
        try {
            return Class.forName(className, false, loader);
        }
        catch (ClassNotFoundException cnfe) {
            classCache.recordFailure(key);
            throw cnfe;
        }
    }

    private static class JDKClassLoaderCache {
        private final Map<Object, Object> cache = Collections.synchronizedMap(new WeakHashMap());
        private static final Object KNOWN_TO_FAIL = new Object();

        private JDKClassLoaderCache() {
        }

        public final void recordFailure(Object key) {
            this.cache.put(key, KNOWN_TO_FAIL);
        }

        public final Object createKey(String className, ClassLoader latestLoader) {
            return new CacheKey(className, latestLoader);
        }

        public final boolean knownToFail(Object key) {
            return this.cache.get(key) == KNOWN_TO_FAIL;
        }

        private static class CacheKey {
            String className;
            ClassLoader loader;

            public CacheKey(String className, ClassLoader loader) {
                this.className = className;
                this.loader = loader;
            }

            public int hashCode() {
                if (this.loader == null) {
                    return this.className.hashCode();
                }
                return this.className.hashCode() ^ this.loader.hashCode();
            }

            public boolean equals(Object obj) {
                try {
                    if (obj == null) {
                        return false;
                    }
                    CacheKey other = (CacheKey)obj;
                    return this.className.equals(other.className) && this.loader == other.loader;
                }
                catch (ClassCastException cce) {
                    return false;
                }
            }
        }
    }
}

