/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.ldaptive.io.ResourceLoader;

public class FileResourceLoader
implements ResourceLoader {
    private static final String PREFIX = "file:";

    @Override
    public boolean supports(String path) {
        return path != null && path.startsWith(PREFIX);
    }

    @Override
    public InputStream load(String path) throws IOException {
        if (!this.supports(path)) {
            throw new IllegalArgumentException("Path '" + path + "' must start with file:");
        }
        return new FileInputStream(new File(path.substring(PREFIX.length())));
    }
}

