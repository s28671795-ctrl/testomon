/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.io;

import java.io.InputStream;
import org.ldaptive.LdapUtils;
import org.ldaptive.io.ResourceLoader;

public class ClasspathResourceLoader
implements ResourceLoader {
    private static final String PREFIX = "classpath:";

    @Override
    public boolean supports(String path) {
        return path != null && path.startsWith(PREFIX);
    }

    @Override
    public InputStream load(String path) {
        if (!this.supports(path)) {
            throw new IllegalArgumentException("Path '" + path + "' must start with classpath:");
        }
        InputStream is = LdapUtils.class.getResourceAsStream(path.substring(PREFIX.length()));
        if (is == null) {
            throw new NullPointerException("Could not get stream from '" + path + "' classpath");
        }
        return is;
    }
}

