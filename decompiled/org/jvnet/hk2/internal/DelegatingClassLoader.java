/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.net.URL;
import org.glassfish.hk2.utilities.reflection.Pretty;

class DelegatingClassLoader
extends ClassLoader {
    private final ClassLoader[] delegates;

    DelegatingClassLoader(ClassLoader parent, ClassLoader ... classLoaderDelegates) {
        super(parent);
        this.delegates = classLoaderDelegates;
    }

    @Override
    public Class<?> loadClass(String clazz) throws ClassNotFoundException {
        if (this.getParent() != null) {
            try {
                return this.getParent().loadClass(clazz);
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
        ClassNotFoundException firstFail = null;
        for (ClassLoader delegate : this.delegates) {
            try {
                return delegate.loadClass(clazz);
            }
            catch (ClassNotFoundException ncfe) {
                if (firstFail != null) continue;
                firstFail = ncfe;
            }
        }
        if (firstFail != null) {
            throw firstFail;
        }
        throw new ClassNotFoundException("Could not find " + clazz);
    }

    @Override
    public URL getResource(String resource) {
        URL u;
        if (this.getParent() != null && (u = this.getParent().getResource(resource)) != null) {
            return u;
        }
        for (ClassLoader delegate : this.delegates) {
            URL u2 = delegate.getResource(resource);
            if (u2 == null) continue;
            return u2;
        }
        return null;
    }

    public String toString() {
        return "DelegatingClassLoader(" + this.getParent() + "," + Pretty.array(this.delegates) + "," + System.identityHashCode(this) + ")";
    }
}

