/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.building;

import java.io.IOException;
import java.io.InputStream;

public interface Source {
    public InputStream getInputStream() throws IOException;

    public String getLocation();
}

