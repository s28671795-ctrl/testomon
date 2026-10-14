/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import org.codehaus.commons.compiler.util.resource.ListableResourceFinder;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.nullanalysis.Nullable;

public abstract class ResourceFinder {
    public static final ListableResourceFinder EMPTY_RESOURCE_FINDER = new ListableResourceFinder(){

        @Override
        @Nullable
        public Resource findResource(String resourceName) {
            return null;
        }

        @Override
        @Nullable
        public Iterable<Resource> list(String resourceNamePrefix, boolean recurse) {
            return Collections.emptyList();
        }

        public String toString() {
            return "EMPTY_RESOURCE_FINDER";
        }
    };

    @Nullable
    public final InputStream findResourceAsStream(String resourceName) throws IOException {
        Resource resource = this.findResource(resourceName);
        if (resource == null) {
            return null;
        }
        return resource.open();
    }

    @Nullable
    public abstract Resource findResource(String var1);
}

