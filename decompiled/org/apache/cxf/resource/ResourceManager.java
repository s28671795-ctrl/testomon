/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.resource;

import java.io.InputStream;
import java.util.List;
import org.apache.cxf.resource.ResourceResolver;

public interface ResourceManager {
    public <T> T resolveResource(String var1, Class<T> var2);

    public <T> T resolveResource(String var1, Class<T> var2, List<ResourceResolver> var3);

    public InputStream getResourceAsStream(String var1);

    public void addResourceResolver(ResourceResolver var1);

    public void removeResourceResolver(ResourceResolver var1);

    public List<ResourceResolver> getResourceResolvers();
}

