/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.resource;

import java.io.InputStream;
import org.apache.cxf.resource.ResourceResolver;

public class SinglePropertyResolver
implements ResourceResolver {
    private final String key;
    private final Object value;

    public SinglePropertyResolver(String k, Object v) {
        this.key = k;
        this.value = v;
    }

    @Override
    public InputStream getAsStream(String name) {
        return null;
    }

    @Override
    public <T> T resolve(String resourceName, Class<T> resourceType) {
        if (null != this.value && this.key.equals(resourceName)) {
            return resourceType.cast(this.value);
        }
        return null;
    }
}

