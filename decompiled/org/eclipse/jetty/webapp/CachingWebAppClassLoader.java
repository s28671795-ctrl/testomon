/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.annotation.ManagedOperation;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject
public class CachingWebAppClassLoader
extends WebAppClassLoader {
    private static final Logger LOG = LoggerFactory.getLogger(CachingWebAppClassLoader.class);
    private final Set<String> _notFound = ConcurrentHashMap.newKeySet();
    private final ConcurrentHashMap<String, URL> _cache = new ConcurrentHashMap();

    public CachingWebAppClassLoader(ClassLoader parent, WebAppClassLoader.Context context) throws IOException {
        super(parent, context);
    }

    public CachingWebAppClassLoader(WebAppClassLoader.Context context) throws IOException {
        super(context);
    }

    @Override
    public URL getResource(String name) {
        if (this._notFound.contains(name)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Not found cache hit resource {}", (Object)name);
            }
            return null;
        }
        URL url2 = this._cache.get(name);
        if (url2 == null) {
            url2 = super.getResource(name);
            if (url2 == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Caching not found resource {}", (Object)name);
                }
                this._notFound.add(name);
            } else {
                this._cache.putIfAbsent(name, url2);
            }
        }
        return url2;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (this._notFound.contains(name)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Not found cache hit resource {}", (Object)name);
            }
            throw new ClassNotFoundException(name + ": in notfound cache");
        }
        try {
            return super.loadClass(name);
        }
        catch (ClassNotFoundException nfe) {
            if (this._notFound.add(name) && LOG.isDebugEnabled()) {
                LOG.debug("Caching not found {}", (Object)name, (Object)nfe);
            }
            throw nfe;
        }
    }

    @ManagedOperation
    public void clearCache() {
        this._cache.clear();
        this._notFound.clear();
    }

    @Override
    public String toString() {
        return "Caching[" + super.toString() + "]";
    }
}

