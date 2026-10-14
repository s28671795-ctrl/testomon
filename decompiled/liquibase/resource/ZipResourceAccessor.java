/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import liquibase.Scope;
import liquibase.resource.AbstractPathResourceAccessor;
import liquibase.resource.PathResource;
import liquibase.resource.Resource;

public class ZipResourceAccessor
extends AbstractPathResourceAccessor {
    private FileSystem fileSystem;

    public ZipResourceAccessor(File file) throws FileNotFoundException {
        this(file.toPath());
    }

    public ZipResourceAccessor(Path file) throws FileNotFoundException {
        this(file, new String[0]);
    }

    protected ZipResourceAccessor(Path file, String[] embeddedPaths) throws FileNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException("File must not be null");
        }
        Scope.getCurrentScope().getLog(this.getClass()).fine("Creating resourceAccessor for file " + file);
        if (!Files.exists(file, new LinkOption[0])) {
            throw new FileNotFoundException("Non-existent file: " + file.toAbsolutePath());
        }
        if (!Files.isRegularFile(file, new LinkOption[0])) {
            throw new IllegalArgumentException("Not a regular file: " + file.toAbsolutePath());
        }
        String lowerCaseName = file.toString().toLowerCase();
        if (!lowerCaseName.endsWith(".jar") && !lowerCaseName.endsWith(".zip")) {
            throw new IllegalArgumentException("Not a jar or zip file: " + file.toAbsolutePath());
        }
        URI finalUri = URI.create("jar:" + file.toUri());
        try {
            try {
                this.fileSystem = FileSystems.getFileSystem(finalUri);
            }
            catch (FileSystemNotFoundException e) {
                try {
                    this.fileSystem = FileSystems.newFileSystem(finalUri, Collections.emptyMap(), Scope.getCurrentScope().getClassLoader());
                }
                catch (FileSystemAlreadyExistsException ex) {
                    this.fileSystem = FileSystems.getFileSystem(finalUri);
                }
            }
            for (String embeddedPath : embeddedPaths) {
                Path innerPath = this.fileSystem.getPath(embeddedPath, new String[0]);
                try {
                    this.fileSystem = FileSystems.newFileSystem(innerPath, null);
                }
                catch (FileSystemNotFoundException e) {
                    this.fileSystem = FileSystems.newFileSystem(innerPath, Scope.getCurrentScope().getClassLoader());
                }
            }
        }
        catch (Throwable e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    protected Path getRootPath() {
        return this.fileSystem.getPath("/", new String[0]);
    }

    @Override
    protected Resource createResource(Path file, String pathToAdd) {
        return new PathResource(pathToAdd, file);
    }

    @Override
    public List<String> describeLocations() {
        return Collections.singletonList(this.fileSystem.toString());
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " (" + this.getRootPath() + ") (" + this.fileSystem.toString() + ")";
    }
}

