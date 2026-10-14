/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.commons.compiler.util.resource.DirectoryResourceCreator;
import org.codehaus.commons.compiler.util.resource.DirectoryResourceFinder;
import org.codehaus.commons.compiler.util.resource.PathResourceFinder;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.ResourceCreator;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.JavaSourceClassLoader;
import org.codehaus.janino.util.ClassFile;

public class CachingJavaSourceClassLoader
extends JavaSourceClassLoader {
    private final ResourceFinder classFileCacheResourceFinder;
    private final ResourceCreator classFileCacheResourceCreator;
    private final ResourceFinder sourceFinder;

    public CachingJavaSourceClassLoader(ClassLoader parentClassLoader, @Nullable File[] sourcePath, @Nullable String characterEncoding2, File cacheDirectory) {
        this(parentClassLoader, sourcePath == null ? new DirectoryResourceFinder(new File(".")) : new PathResourceFinder(sourcePath), characterEncoding2, new DirectoryResourceFinder(cacheDirectory), new DirectoryResourceCreator(cacheDirectory));
    }

    public CachingJavaSourceClassLoader(ClassLoader parentClassLoader, ResourceFinder sourceFinder, @Nullable String characterEncoding2, ResourceFinder classFileCacheResourceFinder, ResourceCreator classFileCacheResourceCreator) {
        super(parentClassLoader, sourceFinder, characterEncoding2);
        this.classFileCacheResourceFinder = classFileCacheResourceFinder;
        this.classFileCacheResourceCreator = classFileCacheResourceCreator;
        this.sourceFinder = sourceFinder;
    }

    @Override
    @Nullable
    protected Map<String, byte[]> generateBytecodes(String className) throws ClassNotFoundException {
        Map<String, byte[]> bytecodes;
        Resource classFileResource = this.classFileCacheResourceFinder.findResource(ClassFile.getClassFileResourceName(className));
        if (classFileResource != null) {
            Resource sourceResource = this.sourceFinder.findResource(ClassFile.getSourceResourceName(className));
            if (sourceResource == null) {
                return null;
            }
            if (sourceResource.lastModified() < classFileResource.lastModified()) {
                byte[] bytecode;
                try {
                    bytecode = CachingJavaSourceClassLoader.readResource(classFileResource);
                }
                catch (IOException ex) {
                    throw new ClassNotFoundException("Reading class file from \"" + classFileResource + "\"", ex);
                }
                HashMap<String, byte[]> m4 = new HashMap<String, byte[]>();
                m4.put(className, bytecode);
                return m4;
            }
        }
        if ((bytecodes = super.generateBytecodes(className)) == null) {
            return null;
        }
        for (Map.Entry<String, byte[]> me : bytecodes.entrySet()) {
            String className2 = me.getKey();
            byte[] bytecode = me.getValue();
            try {
                CachingJavaSourceClassLoader.writeResource(this.classFileCacheResourceCreator, ClassFile.getClassFileResourceName(className2), bytecode);
            }
            catch (IOException ex) {
                throw new ClassNotFoundException("Writing class file to \"" + ClassFile.getClassFileResourceName(className2) + "\"", ex);
            }
        }
        return bytecodes;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static byte[] readResource(Resource r) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        InputStream is = r.open();
        try {
            int cnt;
            while ((cnt = is.read(buffer)) != -1) {
                baos.write(buffer, 0, cnt);
            }
        }
        finally {
            try {
                is.close();
            }
            catch (IOException iOException) {}
        }
        return baos.toByteArray();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void writeResource(ResourceCreator resourceCreator, String resourceName, byte[] data) throws IOException {
        OutputStream os = resourceCreator.createResource(resourceName);
        try {
            os.write(data);
        }
        finally {
            try {
                os.close();
            }
            catch (IOException iOException) {}
        }
    }
}

