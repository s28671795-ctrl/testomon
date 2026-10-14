/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import liquibase.Scope;
import liquibase.resource.AbstractPathResourceAccessor;
import liquibase.resource.PathResource;
import liquibase.resource.Resource;

public class DirectoryResourceAccessor
extends AbstractPathResourceAccessor {
    private final Path rootDirectory;

    public DirectoryResourceAccessor(File directory) throws FileNotFoundException {
        this(directory.toPath());
    }

    public DirectoryResourceAccessor(Path directory) throws FileNotFoundException {
        if (directory == null) {
            throw new IllegalArgumentException("Directory must not be null");
        }
        directory = directory.normalize().toAbsolutePath();
        Scope.getCurrentScope().getLog(this.getClass()).fine("Creating resourceAccessor for directory " + directory);
        this.rootDirectory = directory;
        if (!Files.exists(directory, new LinkOption[0])) {
            throw new FileNotFoundException("Non-existent directory: " + directory.toAbsolutePath());
        }
        if (!Files.isDirectory(directory, new LinkOption[0])) {
            throw new IllegalArgumentException("Not a directory: " + directory.toAbsolutePath());
        }
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    protected Path getRootPath() {
        return this.rootDirectory;
    }

    @Override
    protected Resource createResource(Path file, String pathToAdd) {
        return new PathResource(pathToAdd, file);
    }
}

