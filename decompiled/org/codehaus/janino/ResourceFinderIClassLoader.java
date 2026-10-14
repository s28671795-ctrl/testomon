/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.io.IOException;
import java.io.InputStream;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.ClassFileIClass;
import org.codehaus.janino.Descriptor;
import org.codehaus.janino.IClass;
import org.codehaus.janino.IClassLoader;
import org.codehaus.janino.util.ClassFile;

public class ResourceFinderIClassLoader
extends IClassLoader {
    private final ResourceFinder resourceFinder;

    public ResourceFinderIClassLoader(ResourceFinder resourceFinder, @Nullable IClassLoader parentIClassLoader) {
        super(parentIClassLoader);
        this.resourceFinder = resourceFinder;
        this.postConstruct();
    }

    @Override
    @Nullable
    protected IClass findIClass(String descriptor) throws ClassNotFoundException {
        ClassFile cf;
        InputStream is;
        String className = Descriptor.toClassName(descriptor);
        Resource classFileResource = this.resourceFinder.findResource(ClassFile.getClassFileResourceName(className));
        if (classFileResource == null) {
            return null;
        }
        try {
            is = classFileResource.open();
        }
        catch (IOException ex) {
            throw new ClassNotFoundException("Opening resource \"" + classFileResource.getFileName() + "\"", ex);
        }
        try {
            cf = new ClassFile(is);
        }
        catch (IOException e) {
            throw new ClassNotFoundException("Reading resource \"" + classFileResource.getFileName() + "\"", e);
        }
        finally {
            try {
                is.close();
            }
            catch (IOException iOException) {}
        }
        ClassFileIClass iClass = new ClassFileIClass(cf, this);
        this.defineIClass(iClass);
        return iClass;
    }
}

