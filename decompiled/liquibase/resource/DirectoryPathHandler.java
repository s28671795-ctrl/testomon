/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import liquibase.Scope;
import liquibase.resource.AbstractPathHandler;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.PathResource;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;

public class DirectoryPathHandler
extends AbstractPathHandler {
    @Override
    public int getPriority(String root) {
        if (root == null) {
            return -1;
        }
        if (root.startsWith("/") || !root.contains(":")) {
            return 1;
        }
        if (root.startsWith("file:") || root.matches("^[A-Za-z]:.*")) {
            return 1;
        }
        return -1;
    }

    @Override
    public ResourceAccessor getResourceAccessor(String root) throws FileNotFoundException {
        root = root.replace("file:", "").replace("\\", "/");
        try {
            root = URLDecoder.decode(root, StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException e) {
            Scope.getCurrentScope().getLog(this.getClass()).fine("Failed to decode path " + root + "; continuing without decoding.", e);
        }
        return new DirectoryResourceAccessor(new File(root));
    }

    @Override
    public Resource getResource(String path) throws IOException {
        return new PathResource(path, Paths.get(path, new String[0]));
    }

    @Override
    public OutputStream createResource(String path) throws IOException {
        Path path1 = Paths.get(path, new String[0]);
        Path parent = path1.getParent();
        if (parent != null && !Files.exists(parent, new LinkOption[0])) {
            Files.createDirectories(parent, new FileAttribute[0]);
        }
        return Files.newOutputStream(path1, StandardOpenOption.CREATE_NEW);
    }
}

