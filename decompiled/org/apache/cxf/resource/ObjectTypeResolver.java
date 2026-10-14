/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.resource;

import java.io.InputStream;
import org.apache.cxf.resource.ResourceResolver;

public class ObjectTypeResolver
implements ResourceResolver {
    private final Object value;

    public ObjectTypeResolver(Object v) {
        this.value = v;
    }

    @Override
    public InputStream getAsStream(String name) {
        return null;
    }

    @Override
    public <T> T resolve(String resourceName, Class<T> resourceType) {
        if (resourceName == null && this.value != null && resourceType.isInstance(this.value)) {
            return resourceType.cast(this.value);
        }
        return null;
    }
}

