/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import javax.annotation.Nonnull;

public interface Resource {
    public boolean exists();

    public boolean isReadable();

    public boolean isOpen();

    public URL getURL() throws IOException;

    public URI getURI() throws IOException;

    public File getFile() throws IOException;

    @Nonnull
    public InputStream getInputStream() throws IOException;

    public long contentLength() throws IOException;

    public long lastModified() throws IOException;

    public Resource createRelativeResource(String var1) throws IOException;

    public String getFilename();

    public String getDescription();
}

