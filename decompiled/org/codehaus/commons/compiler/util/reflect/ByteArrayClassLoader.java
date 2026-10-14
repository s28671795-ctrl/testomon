/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.reflect;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.AccessControlException;
import java.security.ProtectionDomain;
import java.util.Collections;
import java.util.Map;
import org.codehaus.commons.nullanalysis.Nullable;

public class ByteArrayClassLoader
extends ClassLoader {
    private final Map<String, byte[]> classes;
    private Map<String, byte[]> resources = Collections.emptyMap();

    public ByteArrayClassLoader(Map<String, byte[]> classes) {
        this.classes = classes;
    }

    public ByteArrayClassLoader(Map<String, byte[]> classes, ClassLoader parent) {
        super(parent);
        this.classes = classes;
    }

    public void setResources(Map<String, byte[]> resources) {
        this.resources = resources;
    }

    @Override
    protected Class<?> findClass(@Nullable String name) throws ClassNotFoundException {
        ProtectionDomain protectionDomain;
        assert (name != null);
        byte[] data = this.classes.get(name);
        if (data == null && (data = this.classes.get(name.replace('.', '/') + ".class")) == null) {
            throw new ClassNotFoundException(name);
        }
        try {
            protectionDomain = this.getClass().getProtectionDomain();
        }
        catch (AccessControlException ace) {
            protectionDomain = null;
        }
        return super.defineClass(name, data, 0, data.length, protectionDomain);
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        InputStream result = super.getResourceAsStream(name);
        if (result != null) {
            return result;
        }
        byte[] ba = this.resources.get(name);
        return ba == null ? null : new ByteArrayInputStream(ba);
    }
}

