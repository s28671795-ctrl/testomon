/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ProxyClassLoader
extends ClassLoader {
    private final Class<?>[] classes;
    private final Set<ClassLoader> loaders = new HashSet<ClassLoader>();
    private boolean checkSystem;

    public ProxyClassLoader(ClassLoader parent) {
        super(parent);
        this.classes = null;
    }

    public ProxyClassLoader(ClassLoader parent, Class<?>[] cls) {
        super(parent);
        this.classes = cls;
    }

    public void addLoader(ClassLoader loader) {
        if (loader == null) {
            this.checkSystem = true;
        } else {
            this.loaders.add(loader);
        }
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        if (this.classes != null) {
            for (Class<?> c : this.classes) {
                if (!name.equals(c.getName())) continue;
                return c;
            }
        }
        for (ClassLoader loader : this.loaders) {
            try {
                return loader.loadClass(name);
            }
            catch (ClassNotFoundException | NoClassDefFoundError throwable) {
            }
        }
        if (this.checkSystem) {
            try {
                return ProxyClassLoader.getSystemClassLoader().loadClass(name);
            }
            catch (ClassNotFoundException | NoClassDefFoundError throwable) {
                // empty catch block
            }
        }
        throw new ClassNotFoundException(name);
    }

    @Override
    public URL findResource(String name) {
        for (ClassLoader loader : this.loaders) {
            URL url2 = loader.getResource(name);
            if (url2 == null) continue;
            return url2;
        }
        return null;
    }
}

