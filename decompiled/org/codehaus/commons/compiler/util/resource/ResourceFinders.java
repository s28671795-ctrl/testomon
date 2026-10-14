/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.codehaus.commons.compiler.lang.ClassLoaders;
import org.codehaus.commons.compiler.util.resource.ListableResourceFinder;
import org.codehaus.commons.compiler.util.resource.LocatableResource;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;
import org.codehaus.commons.nullanalysis.Nullable;

public final class ResourceFinders {
    private ResourceFinders() {
    }

    public static ListableResourceFinder fromClassLoader(final ClassLoader classLoader) {
        return new ListableResourceFinder(){

            @Override
            @Nullable
            public Resource findResource(String resourceName) {
                URL url2;
                if (resourceName.startsWith("/")) {
                    resourceName = resourceName.substring(1);
                }
                if ((url2 = classLoader.getResource(resourceName)) == null) {
                    return null;
                }
                String finalResourceName = resourceName;
                return ResourceFinders.resourceFromUrl(url2, finalResourceName);
            }

            @Override
            @Nullable
            public Iterable<Resource> list(String resourceNamePrefix, boolean recurse) throws IOException {
                Map<String, URL> allSubresources = ClassLoaders.getSubresources(classLoader, resourceNamePrefix, false, recurse);
                ArrayList<Resource> result = new ArrayList<Resource>(allSubresources.size());
                for (Map.Entry<String, URL> e : allSubresources.entrySet()) {
                    String name = e.getKey();
                    URL url2 = e.getValue();
                    if (!name.endsWith(".class")) continue;
                    result.add(ResourceFinders.resourceFromUrl(url2, name));
                }
                return result;
            }
        };
    }

    public static ResourceFinder debugResourceFinder(final ResourceFinder delegate) {
        if (delegate instanceof ListableResourceFinder) {
            return ResourceFinders.debugListableResourceFinder((ListableResourceFinder)delegate);
        }
        return new ResourceFinder(){

            @Override
            @Nullable
            public Resource findResource(String resourceName) {
                Resource result = delegate.findResource(resourceName);
                System.err.println("findResource(\"" + resourceName + "\") => " + result);
                return result;
            }
        };
    }

    private static ResourceFinder debugListableResourceFinder(final ListableResourceFinder delegate) {
        return new ListableResourceFinder(){

            @Override
            @Nullable
            public Resource findResource(String resourceName) {
                Resource result = delegate.findResource(resourceName);
                System.err.println("findResource(\"" + resourceName + "\") => " + result);
                return result;
            }

            @Override
            @Nullable
            public Iterable<Resource> list(String resourceNamePrefix, boolean recurse) throws IOException {
                Iterable<Resource> result = delegate.list(resourceNamePrefix, recurse);
                System.err.println("list(\"" + resourceNamePrefix + "\", " + recurse + ") => " + ResourceFinders.toString(result));
                return result;
            }
        };
    }

    private static <T> String toString(Iterable<T> i) {
        Iterator<T> it = i.iterator();
        if (!it.hasNext()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[ ").append(it.next().toString());
        while (it.hasNext()) {
            sb.append(", ").append(it.next());
        }
        return sb.append(" ]").toString();
    }

    private static LocatableResource resourceFromUrl(final URL url2, final String resourceName) {
        return new LocatableResource(){

            @Override
            public URL getLocation() throws IOException {
                return url2;
            }

            @Override
            public InputStream open() throws IOException {
                return url2.openStream();
            }

            @Override
            public String getFileName() {
                return resourceName;
            }

            @Override
            public long lastModified() {
                return 0L;
            }
        };
    }
}

