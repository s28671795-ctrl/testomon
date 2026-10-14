/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.commons.compiler.util.resource.ResourceCreator;

public class MapResourceCreator
implements ResourceCreator {
    private final Map<String, byte[]> map;

    public MapResourceCreator() {
        this.map = new HashMap<String, byte[]>();
    }

    public MapResourceCreator(Map<String, byte[]> map) {
        this.map = map;
    }

    public final Map<String, byte[]> getMap() {
        return this.map;
    }

    @Override
    public final OutputStream createResource(final String resourceName) {
        return new ByteArrayOutputStream(){

            @Override
            public void close() throws IOException {
                super.close();
                MapResourceCreator.this.map.put(resourceName, this.toByteArray());
            }
        };
    }

    @Override
    public final boolean deleteResource(String resourceName) {
        return this.map.remove(resourceName) != null;
    }
}

