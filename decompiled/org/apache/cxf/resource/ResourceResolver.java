/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.resource;

import java.io.InputStream;

public interface ResourceResolver {
    public <T> T resolve(String var1, Class<T> var2);

    public InputStream getAsStream(String var1);
}

