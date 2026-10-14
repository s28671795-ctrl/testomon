/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.resource;

import java.io.InputStream;
import java.net.URL;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.resource.ResourceResolver;

@NoJSR250Annotations
public class ClassLoaderResolver
implements ResourceResolver {
    private final ClassLoader loader;

    public ClassLoaderResolver() {
        this(ClassLoaderResolver.class.getClassLoader());
    }

    public ClassLoaderResolver(ClassLoader l) {
        this.loader = l;
    }

    @Override
    public <T> T resolve(String resourceName, Class<T> resourceType) {
        if (resourceName == null || resourceType == null) {
            return null;
        }
        URL url2 = this.loader.getResource(resourceName);
        if (resourceType.isInstance(url2)) {
            return resourceType.cast(url2);
        }
        return null;
    }

    @Override
    public InputStream getAsStream(String name) {
        return this.loader.getResourceAsStream(name);
    }
}

