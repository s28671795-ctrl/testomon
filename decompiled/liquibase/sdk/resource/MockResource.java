/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sdk.resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import liquibase.resource.AbstractResource;
import liquibase.resource.Resource;

public class MockResource
extends AbstractResource {
    private final String content;

    public MockResource(String path, String content) {
        super(path, URI.create("mock:" + path));
        this.content = content;
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public Resource resolve(String other) {
        return new MockResource(this.resolvePath(other), "Resource relative to " + this.getPath());
    }

    @Override
    public Resource resolveSibling(String other) {
        return new MockResource(this.resolveSiblingPath(other), "Sibling resource to " + this.getPath());
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return new ByteArrayInputStream(this.content.getBytes());
    }
}

