/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.IOException;
import java.io.InputStream;

public interface Resource {
    public InputStream open() throws IOException;

    public String getFileName();

    public long lastModified();
}

