/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.constructs.classloader;

import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.classloader.ClassLoaderAwareCache;
import net.sf.ehcache.terracotta.InternalEhcache;

public class InternalClassLoaderAwareCache
extends ClassLoaderAwareCache
implements InternalEhcache {
    public InternalClassLoaderAwareCache(InternalEhcache cache, ClassLoader classLoader) {
        super(cache, classLoader);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Element removeAndReturnElement(Object arg0) throws IllegalStateException {
        Thread t2 = Thread.currentThread();
        ClassLoader prev = t2.getContextClassLoader();
        t2.setContextClassLoader(this.classLoader);
        try {
            Element element = ((InternalEhcache)this.cache).removeAndReturnElement(arg0);
            return element;
        }
        finally {
            t2.setContextClassLoader(prev);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void recalculateSize(Object arg0) {
        Thread t2 = Thread.currentThread();
        ClassLoader prev = t2.getContextClassLoader();
        t2.setContextClassLoader(this.classLoader);
        try {
            ((InternalEhcache)this.cache).recalculateSize(arg0);
        }
        finally {
            t2.setContextClassLoader(prev);
        }
    }
}

