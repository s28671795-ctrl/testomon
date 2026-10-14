/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import liquibase.resource.AbstractPathHandler;
import liquibase.resource.PathResource;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;
import liquibase.resource.ZipResourceAccessor;

public class ZipPathHandler
extends AbstractPathHandler {
    @Override
    public int getPriority(String root) {
        if (root == null) {
            return -1;
        }
        if (root.toLowerCase().endsWith(".zip") || root.toLowerCase().endsWith(".jar")) {
            return 10;
        }
        if (root.startsWith("jar:") && root.endsWith("!/")) {
            return 10;
        }
        return -1;
    }

    @Override
    public ResourceAccessor getResourceAccessor(String root) throws FileNotFoundException {
        int endIndex;
        int startIndex = root.startsWith("jar:") ? 4 : 0;
        if ((root = root.substring(startIndex, (endIndex = root.lastIndexOf(33)) > 4 ? endIndex : root.length())).matches("^\\w\\w+:.*")) {
            String[] paths = root.split("!");
            Path rootPath = Paths.get(URI.create(paths[0]));
            if (paths.length > 1) {
                return new ZipResourceAccessor(rootPath, Arrays.copyOfRange(paths, 1, paths.length));
            }
            return new ZipResourceAccessor(rootPath);
        }
        return new ZipResourceAccessor(new File(root));
    }

    @Override
    public Resource getResource(String path) throws IOException {
        return new PathResource(path, Paths.get(path, new String[0]));
    }

    @Override
    public OutputStream createResource(String path) throws IOException {
        return Files.newOutputStream(Paths.get(path, new String[0]), StandardOpenOption.CREATE_NEW);
    }
}

