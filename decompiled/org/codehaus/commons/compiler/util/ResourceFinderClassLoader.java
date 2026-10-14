/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;
import org.codehaus.commons.nullanalysis.Nullable;

public class ResourceFinderClassLoader
extends ClassLoader {
    private final ResourceFinder resourceFinder;

    public ResourceFinderClassLoader(ResourceFinder resourceFinder, ClassLoader parent) {
        super(parent);
        this.resourceFinder = resourceFinder;
    }

    public ResourceFinder getResourceFinder() {
        return this.resourceFinder;
    }

    @Override
    protected Class<?> findClass(@Nullable String className) throws ClassNotFoundException {
        InputStream is;
        assert (className != null);
        Resource classFileResource = this.resourceFinder.findResource(className.replace('.', '/') + ".class");
        if (classFileResource == null) {
            throw new ClassNotFoundException(className);
        }
        try {
            is = classFileResource.open();
        }
        catch (IOException ex) {
            throw new ClassNotFoundException("Opening class file resource \"" + classFileResource.getFileName() + "\": " + ex.getMessage(), ex);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            int bytesRead;
            byte[] buffer = new byte[4096];
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
        }
        catch (IOException ex) {
            throw new ClassNotFoundException("Reading class file from \"" + classFileResource + "\"", ex);
        }
        finally {
            try {
                is.close();
            }
            catch (IOException iOException) {}
        }
        byte[] ba = baos.toByteArray();
        Class<?> clazz = super.defineClass(null, ba, 0, ba.length);
        if (!clazz.getName().equals(className)) {
            throw new ClassNotFoundException(className);
        }
        return clazz;
    }
}

