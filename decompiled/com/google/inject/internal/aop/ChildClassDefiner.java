/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.aop;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.internal.aop.ClassDefiner;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Logger;

final class ChildClassDefiner
implements ClassDefiner {
    private static final Logger logger = Logger.getLogger(ChildClassDefiner.class.getName());

    ChildClassDefiner() {
    }

    @Override
    public Class<?> define(Class<?> hostClass, byte[] bytecode) throws Exception {
        ClassLoader hostLoader = hostClass.getClassLoader();
        ChildLoader childLoader = hostLoader != null ? ChildLoaderCacheHolder.CHILD_LOADER_CACHE.get(hostLoader) : SystemChildLoaderHolder.SYSTEM_CHILD_LOADER;
        return childLoader.defineInChild(bytecode);
    }

    static <T> T doPrivileged(PrivilegedAction<T> action) {
        return AccessController.doPrivileged(action);
    }

    static ChildLoader childLoader(ClassLoader hostLoader) {
        logger.fine("Creating a child loader for " + hostLoader);
        return ChildClassDefiner.doPrivileged(() -> new ChildLoader(hostLoader));
    }

    private static final class ChildLoader
    extends ClassLoader {
        ChildLoader(ClassLoader parent) {
            super(parent);
        }

        ChildLoader() {
        }

        Class<?> defineInChild(byte[] bytecode) {
            Class<?> type = this.defineClass(null, bytecode, 0, bytecode.length, null);
            this.resolveClass(type);
            return type;
        }
    }

    private static class ChildLoaderCacheHolder {
        static final LoadingCache<ClassLoader, ChildLoader> CHILD_LOADER_CACHE = CacheBuilder.newBuilder().weakKeys().weakValues().build(CacheLoader.from(ChildClassDefiner::childLoader));

        private ChildLoaderCacheHolder() {
        }
    }

    private static class SystemChildLoaderHolder {
        static final ChildLoader SYSTEM_CHILD_LOADER = ChildClassDefiner.doPrivileged(ChildLoader::new);

        private SystemChildLoaderHolder() {
        }
    }
}

