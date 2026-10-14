/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.resource;

import java.io.InputStream;
import java.util.Map;
import org.apache.cxf.resource.ResourceResolver;

public class PropertiesResolver
implements ResourceResolver {
    private final Map<String, Object> properties;

    public PropertiesResolver(Map<String, Object> p) {
        this.properties = p;
    }

    @Override
    public InputStream getAsStream(String name) {
        return null;
    }

    @Override
    public <T> T resolve(String resourceName, Class<T> resourceType) {
        if (resourceName == null) {
            return null;
        }
        Object obj = this.properties.get(resourceName);
        if (null != obj) {
            return resourceType.cast(this.properties.get(resourceName));
        }
        return null;
    }
}

