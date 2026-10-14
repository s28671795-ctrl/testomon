/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.commons.compiler.util.Predicate;
import org.codehaus.commons.compiler.util.iterator.Iterables;
import org.codehaus.commons.compiler.util.resource.ListableResourceFinder;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.nullanalysis.Nullable;

public class MapResourceFinder
extends ListableResourceFinder {
    private final Map<String, Resource> map = new HashMap<String, Resource>();
    private long lastModified;

    public MapResourceFinder() {
    }

    public MapResourceFinder(Map<String, byte[]> map) {
        for (Map.Entry<String, byte[]> me : map.entrySet()) {
            Resource prev = this.addResource(me.getKey(), me.getValue());
            assert (prev == null);
        }
    }

    @Nullable
    public Resource addResource(final String fileName, final byte[] data) {
        return this.map.put(fileName, new Resource(){

            @Override
            public InputStream open() {
                return new ByteArrayInputStream(data);
            }

            @Override
            public String getFileName() {
                return fileName;
            }

            @Override
            public long lastModified() {
                return MapResourceFinder.this.lastModified;
            }
        });
    }

    @Nullable
    public Resource addResource(String fileName, String data) {
        return this.addResource(fileName, data.getBytes());
    }

    public Resource addResource(Resource resource) {
        return this.map.put(resource.getFileName(), resource);
    }

    public Collection<Resource> resources() {
        return Collections.unmodifiableCollection(this.map.values());
    }

    public final void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    @Nullable
    public final Resource findResource(String resourceName) {
        return this.map.get(resourceName);
    }

    @Override
    @Nullable
    public Iterable<Resource> list(final String resourceNamePrefix, final boolean recurse) {
        return Iterables.filter(this.map.values(), new Predicate<Object>(){

            @Override
            public boolean evaluate(@Nullable Object o) {
                Resource r = (Resource)o;
                assert (r != null);
                String resourceName = r.getFileName();
                int rnpl = resourceNamePrefix.length();
                return resourceName.startsWith(resourceNamePrefix) && (recurse || resourceName.indexOf(47, rnpl) == -1);
            }
        });
    }
}

