/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 */
package org.apache.cxf.bus.blueprint;

import java.io.IOException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import org.osgi.framework.Bundle;

public class BundleDelegatingClassLoader
extends ClassLoader {
    private final Bundle bundle;
    private final ClassLoader classLoader;

    public BundleDelegatingClassLoader(Bundle bundle2) {
        this(bundle2, null);
    }

    public BundleDelegatingClassLoader(Bundle bundle2, ClassLoader classLoader) {
        this.bundle = bundle2;
        this.classLoader = classLoader;
    }

    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        try {
            return (Class)AccessController.doPrivileged(new PrivilegedExceptionAction<Class<?>>(){

                @Override
                public Class<?> run() throws ClassNotFoundException {
                    return BundleDelegatingClassLoader.this.bundle.loadClass(name);
                }
            });
        }
        catch (PrivilegedActionException e) {
            Exception cause = e.getException();
            if (cause instanceof ClassNotFoundException) {
                throw (ClassNotFoundException)cause;
            }
            throw (RuntimeException)cause;
        }
    }

    @Override
    protected URL findResource(final String name) {
        URL resource = AccessController.doPrivileged(new PrivilegedAction<URL>(){

            @Override
            public URL run() {
                return BundleDelegatingClassLoader.this.bundle.getResource(name);
            }
        });
        if (this.classLoader != null && resource == null) {
            resource = this.classLoader.getResource(name);
        }
        return resource;
    }

    @Override
    protected Enumeration<URL> findResources(final String name) throws IOException {
        Enumeration<URL> urls2;
        try {
            urls2 = AccessController.doPrivileged(new PrivilegedExceptionAction<Enumeration<URL>>(){

                @Override
                public Enumeration<URL> run() throws IOException {
                    return BundleDelegatingClassLoader.this.bundle.getResources(name);
                }
            });
        }
        catch (PrivilegedActionException e) {
            Exception cause = e.getException();
            if (cause instanceof IOException) {
                throw (IOException)cause;
            }
            throw (RuntimeException)cause;
        }
        if (urls2 == null) {
            urls2 = Collections.enumeration(new ArrayList());
        }
        return urls2;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> clazz;
        try {
            clazz = this.findClass(name);
        }
        catch (ClassNotFoundException cnfe) {
            if (this.classLoader != null) {
                try {
                    clazz = this.classLoader.loadClass(name);
                }
                catch (ClassNotFoundException e) {
                    throw new ClassNotFoundException(name + " from bundle " + this.bundle.getBundleId() + " (" + this.bundle.getSymbolicName() + ")", cnfe);
                }
            }
            throw new ClassNotFoundException(name + " from bundle " + this.bundle.getBundleId() + " (" + this.bundle.getSymbolicName() + ")", cnfe);
        }
        if (resolve) {
            this.resolveClass(clazz);
        }
        return clazz;
    }

    public Bundle getBundle() {
        return this.bundle;
    }
}

