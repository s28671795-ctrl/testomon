/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import liquibase.resource.AbstractResource;
import liquibase.resource.Resource;

public class URIResource
extends AbstractResource {
    public URIResource(String path, URI uri) {
        super(path, uri);
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public Resource resolve(String other) {
        return new URIResource(this.resolvePath(other), URI.create(this.getUri().toString() + "/" + other));
    }

    @Override
    public Resource resolveSibling(String other) {
        return new URIResource(this.resolveSiblingPath(other), URI.create(this.getUri().toString().replaceFirst("/[^/]*$", "") + "/" + other));
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return this.getUri().toURL().openStream();
    }
}

