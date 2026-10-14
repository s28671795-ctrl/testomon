/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.io;

import java.io.IOException;
import java.io.InputStream;
import org.ldaptive.LdapUtils;
import org.ldaptive.io.ClasspathResourceLoader;
import org.ldaptive.io.FileResourceLoader;
import org.ldaptive.io.ResourceLoader;

public final class ResourceUtils {
    private static final ResourceLoader[] DEFAULT_RESOURCE_LOADERS = new ResourceLoader[]{new ClasspathResourceLoader(), new FileResourceLoader()};
    private static ResourceLoader[] customResourceLoaders;

    private ResourceUtils() {
    }

    public static void setCustomResourceLoaders(ResourceLoader ... loaders) {
        customResourceLoaders = loaders;
    }

    public static boolean isResource(String path, ResourceLoader ... loaders) {
        for (ResourceLoader loader : loaders) {
            if (!loader.supports(path)) continue;
            return true;
        }
        return false;
    }

    public static boolean isResource(String path) {
        if (customResourceLoaders != null && customResourceLoaders.length > 0) {
            return ResourceUtils.isResource(path, LdapUtils.concatArrays(DEFAULT_RESOURCE_LOADERS, new ResourceLoader[][]{customResourceLoaders}));
        }
        return ResourceUtils.isResource(path, DEFAULT_RESOURCE_LOADERS);
    }

    public static InputStream getResource(String path, ResourceLoader ... loaders) throws IOException {
        for (ResourceLoader loader : loaders) {
            if (!loader.supports(path)) continue;
            return loader.load(path);
        }
        throw new IllegalArgumentException("Could not find a resource loader for '" + path + "'");
    }

    public static InputStream getResource(String path) throws IOException {
        if (customResourceLoaders != null && customResourceLoaders.length > 0) {
            return ResourceUtils.getResource(path, LdapUtils.concatArrays(DEFAULT_RESOURCE_LOADERS, new ResourceLoader[][]{customResourceLoaders}));
        }
        return ResourceUtils.getResource(path, DEFAULT_RESOURCE_LOADERS);
    }

    public static byte[] readResource(String path, ResourceLoader ... loaders) throws IOException {
        return LdapUtils.readInputStream(ResourceUtils.getResource(path, loaders));
    }

    public static byte[] readResource(String path) throws IOException {
        return LdapUtils.readInputStream(ResourceUtils.getResource(path));
    }
}

