/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.io;

import java.io.InputStream;
import java.net.URL;
import org.ldaptive.io.ResourceLoader;

public class URLResourceLoader
implements ResourceLoader {
    @Override
    public boolean supports(String path) {
        try {
            new URL(path);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public InputStream load(String path) {
        try {
            return new URL(path).openStream();
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}

