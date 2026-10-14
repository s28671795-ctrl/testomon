/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.WritableResource
 */
package liquibase.integration.spring;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.resource.AbstractResource;
import liquibase.resource.OpenOptions;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

class SpringResource
extends AbstractResource {
    private final Resource resource;

    public SpringResource(String path, URI uri, Resource resource) {
        super(path, uri);
        this.resource = resource;
    }

    @Override
    public boolean exists() {
        return this.resource.exists();
    }

    @Override
    public liquibase.resource.Resource resolve(String other) {
        try {
            Resource otherResource = this.resource.createRelative(other);
            return new SpringResource(this.resolvePath(other), otherResource.getURI(), otherResource);
        }
        catch (IOException e) {
            throw new UnexpectedLiquibaseException(e.getMessage(), e);
        }
    }

    @Override
    public liquibase.resource.Resource resolveSibling(String other) {
        try {
            Resource otherResource = this.resource.createRelative(other);
            return new SpringResource(this.resolveSiblingPath(other), otherResource.getURI(), otherResource);
        }
        catch (IOException e) {
            throw new UnexpectedLiquibaseException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isWritable() {
        return this.resource instanceof WritableResource && ((WritableResource)this.resource).isWritable();
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return this.resource.getInputStream();
    }

    @Override
    public OutputStream openOutputStream(OpenOptions openOptions) throws IOException {
        if (!this.resource.exists() && !openOptions.isCreateIfNeeded()) {
            throw new IOException("Resource " + this.getUri() + " does not exist");
        }
        if (openOptions != null && openOptions.isAppend() && this.exists()) {
            throw new IOException("Spring only supports truncating the existing resources.");
        }
        if (this.resource instanceof WritableResource) {
            return ((WritableResource)this.resource).getOutputStream();
        }
        throw new IOException("Read only");
    }
}

