/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import liquibase.plugin.Plugin;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;

public interface PathHandler
extends Plugin {
    public int getPriority(String var1);

    public ResourceAccessor getResourceAccessor(String var1) throws IOException, FileNotFoundException;

    public Resource getResource(String var1) throws IOException;

    public OutputStream createResource(String var1) throws IOException;
}

