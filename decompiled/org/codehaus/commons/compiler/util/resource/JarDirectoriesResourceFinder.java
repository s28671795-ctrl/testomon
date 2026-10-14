/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.zip.ZipFile;
import org.codehaus.commons.compiler.util.iterator.MultiDimensionalIterator;
import org.codehaus.commons.compiler.util.iterator.TransformingIterator;
import org.codehaus.commons.compiler.util.resource.LazyMultiResourceFinder;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;
import org.codehaus.commons.compiler.util.resource.ZipFileResourceFinder;
import org.codehaus.commons.nullanalysis.Nullable;

public class JarDirectoriesResourceFinder
extends LazyMultiResourceFinder {
    public JarDirectoriesResourceFinder(File[] directories) {
        super(new MultiDimensionalIterator<ResourceFinder>(new TransformingIterator<Object, Iterator<?>>(Arrays.asList(directories).iterator()){

            @Override
            protected Iterator<ResourceFinder> transform(Object o) {
                File directory = (File)o;
                if (!directory.exists()) {
                    return Collections.emptyList().iterator();
                }
                File[] jarFiles = directory.listFiles(new FilenameFilter(){

                    @Override
                    public boolean accept(@Nullable File dir, @Nullable String name) {
                        assert (dir != null);
                        assert (name != null);
                        return name.endsWith(".jar");
                    }
                });
                return new TransformingIterator<Object, ResourceFinder>(Arrays.asList(jarFiles).iterator()){

                    @Override
                    protected ResourceFinder transform(Object o) {
                        File jarFile = (File)o;
                        try {
                            return new ZipFileResourceFinder(new ZipFile(jarFile));
                        }
                        catch (IOException e) {
                            return ResourceFinder.EMPTY_RESOURCE_FINDER;
                        }
                    }
                };
            }
        }, 2));
    }
}

