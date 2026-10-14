/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.ZipFile;
import org.codehaus.commons.compiler.util.StringUtil;
import org.codehaus.commons.compiler.util.iterator.TransformingIterator;
import org.codehaus.commons.compiler.util.resource.DirectoryResourceFinder;
import org.codehaus.commons.compiler.util.resource.LazyMultiResourceFinder;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;
import org.codehaus.commons.compiler.util.resource.ZipFileResourceFinder;

public class PathResourceFinder
extends LazyMultiResourceFinder {
    public PathResourceFinder(File[] entries) {
        super(PathResourceFinder.createIterator(Arrays.asList(entries).iterator()));
    }

    public PathResourceFinder(Iterator<ResourceFinder> entries) {
        super(entries);
    }

    public PathResourceFinder(String path) {
        this(StringUtil.parsePath(path));
    }

    private static Iterator<ResourceFinder> createIterator(Iterator<File> entries) {
        return new TransformingIterator<Object, ResourceFinder>(entries){

            @Override
            protected ResourceFinder transform(Object o) {
                return PathResourceFinder.createResourceFinder((File)o);
            }
        };
    }

    private static ResourceFinder createResourceFinder(File entry) {
        if ((entry.getName().endsWith(".jar") || entry.getName().endsWith(".zip")) && entry.isFile()) {
            try {
                return new ZipFileResourceFinder(new ZipFile(entry));
            }
            catch (IOException e) {
                return ResourceFinder.EMPTY_RESOURCE_FINDER;
            }
        }
        if (entry.isDirectory()) {
            return new DirectoryResourceFinder(entry);
        }
        return ResourceFinder.EMPTY_RESOURCE_FINDER;
    }
}

