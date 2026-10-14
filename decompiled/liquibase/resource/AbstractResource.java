/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import liquibase.resource.OpenOptions;
import liquibase.resource.Resource;

public abstract class AbstractResource
implements Resource {
    private final String path;
    private final URI uri;

    public AbstractResource(String path, URI uri) {
        this.path = path.replace("\\", "/").replaceFirst("^classpath\\*?:", "").replaceFirst("^/", "");
        this.uri = uri != null ? uri.normalize() : null;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public URI getUri() {
        return this.uri;
    }

    @Override
    public boolean isWritable() {
        return false;
    }

    @Override
    public OutputStream openOutputStream(OpenOptions openOptions) throws IOException {
        if (!this.isWritable()) {
            throw new IOException("Read only");
        }
        throw new IOException("Write not implemented");
    }

    public String toString() {
        return this.getPath();
    }

    public int hashCode() {
        return this.getUri().hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Resource)) {
            return false;
        }
        return this.getUri().equals(((Resource)obj).getUri());
    }

    protected String resolvePath(String other) {
        if (this.getPath().endsWith("/")) {
            return this.getPath() + other;
        }
        return this.getPath() + "/" + other;
    }

    protected String resolveSiblingPath(String other) {
        if (this.getPath().contains("/")) {
            return this.getPath().replaceFirst("/[^/]*$", "") + "/" + other;
        }
        return "/" + other;
    }
}

