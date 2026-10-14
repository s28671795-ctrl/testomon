/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.codehaus.commons.compiler.util.resource.FileResource;
import org.codehaus.commons.compiler.util.resource.FileResourceFinder;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.nullanalysis.Nullable;

public class DirectoryResourceFinder
extends FileResourceFinder {
    private final File directory;
    private final Map<String, Set<File>> subdirectoryNameToFiles = new HashMap<String, Set<File>>();

    public DirectoryResourceFinder(File directory) {
        this.directory = directory;
    }

    public final String toString() {
        return "dir:" + this.directory;
    }

    @Override
    @Nullable
    protected final File findResourceAsFile(String resourceName) {
        int idx = resourceName.lastIndexOf(47);
        String subdirectoryName = idx == -1 ? null : resourceName.substring(0, idx).replace('/', File.separatorChar);
        Set<File> files = this.listFiles(subdirectoryName);
        if (files == null) {
            return null;
        }
        File file = new File(this.directory, resourceName.replace('/', File.separatorChar));
        if (!files.contains(file)) {
            return null;
        }
        return file;
    }

    @Nullable
    private Set<File> listFiles(@Nullable String subdirectoryName) {
        Set<File> files;
        if (subdirectoryName != null) {
            subdirectoryName = subdirectoryName.replace('/', File.separatorChar);
        }
        if ((files = this.subdirectoryNameToFiles.get(subdirectoryName)) != null || this.subdirectoryNameToFiles.containsKey(subdirectoryName)) {
            return files;
        }
        File subDirectory = subdirectoryName == null ? this.directory : new File(this.directory, subdirectoryName);
        File[] members = subDirectory.listFiles();
        if (members == null) {
            this.subdirectoryNameToFiles.put(subdirectoryName, null);
            return null;
        }
        HashSet<File> normalFiles = new HashSet<File>();
        for (File file : members) {
            if (!file.isFile()) continue;
            normalFiles.add(file);
        }
        this.subdirectoryNameToFiles.put(subdirectoryName, normalFiles);
        return normalFiles;
    }

    @Override
    @Nullable
    public Iterable<Resource> list(String resourceNamePrefix, boolean recurse) {
        assert (!recurse) : "This implementation does not support recursive directory listings";
        int idx = resourceNamePrefix.lastIndexOf(47);
        String directoryName = idx == -1 ? null : resourceNamePrefix.substring(0, idx);
        String relativeFileName = resourceNamePrefix.substring(idx + 1);
        Set<File> files = this.listFiles(directoryName);
        if (files == null) {
            return null;
        }
        ArrayList<Resource> result = new ArrayList<Resource>();
        for (File file : files) {
            if (!file.getName().startsWith(relativeFileName)) continue;
            result.add(new FileResource(file));
        }
        return result;
    }
}

