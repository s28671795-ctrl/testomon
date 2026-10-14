/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.codehaus.commons.compiler.util.resource.Resource;

public class StringResource
implements Resource {
    private final String fileName;
    private final byte[] data;

    public StringResource(String fileName, String text) {
        this.fileName = fileName;
        this.data = text.getBytes();
    }

    @Override
    public final String getFileName() {
        return this.fileName;
    }

    @Override
    public final InputStream open() {
        return new ByteArrayInputStream(this.data);
    }

    @Override
    public final long lastModified() {
        return 0L;
    }

    public final String toString() {
        return this.getFileName();
    }
}

