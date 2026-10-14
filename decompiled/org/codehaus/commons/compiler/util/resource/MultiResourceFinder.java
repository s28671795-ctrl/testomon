/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.codehaus.commons.compiler.util.resource.ListableResourceFinder;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;
import org.codehaus.commons.nullanalysis.Nullable;

public class MultiResourceFinder
extends ListableResourceFinder {
    private final Iterable<? extends ResourceFinder> resourceFinders;

    public MultiResourceFinder(Iterable<? extends ResourceFinder> resourceFinders) {
        this.resourceFinders = resourceFinders;
    }

    public MultiResourceFinder(ResourceFinder ... resourceFinders) {
        this(Arrays.asList(resourceFinders));
    }

    @Override
    @Nullable
    public final Resource findResource(String resourceName) {
        for (ResourceFinder resourceFinder : this.resourceFinders) {
            Resource resource = resourceFinder.findResource(resourceName);
            if (resource == null) continue;
            return resource;
        }
        return null;
    }

    @Override
    @Nullable
    public Iterable<Resource> list(String resourceNamePrefix, boolean recurse) throws IOException {
        ArrayList<Resource> result = new ArrayList<Resource>();
        for (ResourceFinder resourceFinder : this.resourceFinders) {
            Iterable<Resource> resources = ((ListableResourceFinder)resourceFinder).list(resourceNamePrefix, recurse);
            if (resources == null) continue;
            for (Resource r : resources) {
                result.add(r);
            }
        }
        return result;
    }
}

