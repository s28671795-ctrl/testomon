/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import liquibase.Scope;
import liquibase.resource.AbstractResource;
import liquibase.resource.OpenOptions;
import liquibase.resource.Resource;

public class PathResource
extends AbstractResource {
    private final Path path;

    public PathResource(String logicalPath, Path path) {
        super(logicalPath, path.normalize().toUri());
        this.path = path.normalize();
    }

    @Override
    public InputStream openInputStream() throws IOException {
        if (!Files.exists(this.path, new LinkOption[0])) {
            throw new FileNotFoundException(this.path + " does not exist");
        }
        if (Files.isDirectory(this.path, new LinkOption[0])) {
            throw new FileNotFoundException(this.path + " is a directory");
        }
        InputStream stream = Files.newInputStream(this.path, new OpenOption[0]);
        if (this.path.getFileName().toString().toLowerCase().endsWith(".gz")) {
            stream = new GZIPInputStream(stream);
        }
        return stream;
    }

    @Override
    public boolean exists() {
        return Files.exists(this.path, new LinkOption[0]);
    }

    @Override
    public Resource resolve(String other) {
        return new PathResource(this.resolvePath(other), this.path.resolve(other));
    }

    @Override
    public Resource resolveSibling(String other) {
        return new PathResource(this.resolveSiblingPath(other), this.path.resolveSibling(other));
    }

    @Override
    public boolean isWritable() {
        return !Files.isDirectory(this.path, new LinkOption[0]) && Files.isWritable(this.path);
    }

    @Override
    public OutputStream openOutputStream(OpenOptions openOptions) throws IOException {
        if (!this.exists()) {
            if (openOptions.isCreateIfNeeded()) {
                boolean mkdirs;
                Path parent = this.path.getParent();
                if (parent != null && !parent.toFile().exists() && !(mkdirs = parent.toFile().mkdirs())) {
                    Scope.getCurrentScope().getLog(this.getClass()).warning("Failed to create parent directories for file " + this.path);
                }
            } else {
                throw new IOException("File " + this.getUri() + " does not exist");
            }
        }
        if (Files.isDirectory(this.path, new LinkOption[0])) {
            throw new FileNotFoundException(this.getPath() + " is a directory");
        }
        ArrayList<StandardOpenOption> options = new ArrayList<StandardOpenOption>();
        if (openOptions.isCreateIfNeeded()) {
            options.add(StandardOpenOption.CREATE);
        }
        if (openOptions.isTruncate()) {
            options.addAll(Arrays.asList(StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE));
        } else {
            options.add(StandardOpenOption.APPEND);
        }
        return Files.newOutputStream(this.path, options.toArray(new OpenOption[0]));
    }
}

