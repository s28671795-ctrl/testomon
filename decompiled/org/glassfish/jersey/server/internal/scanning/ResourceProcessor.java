/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.scanning;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceProcessor {
    public boolean accept(String var1);

    public void process(String var1, InputStream var2) throws IOException;
}

