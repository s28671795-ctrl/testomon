/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import liquibase.resource.OpenOptions;

public interface Resource {
    public String getPath();

    public InputStream openInputStream() throws IOException;

    public boolean isWritable();

    public boolean exists();

    public Resource resolve(String var1);

    public Resource resolveSibling(String var1);

    public OutputStream openOutputStream(OpenOptions var1) throws IOException;

    @Deprecated
    default public OutputStream openOutputStream(boolean createIfNeeded) throws IOException {
        return this.openOutputStream(new OpenOptions().setCreateIfNeeded(createIfNeeded));
    }

    public URI getUri();
}

