/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.io;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceLoader {
    public boolean supports(String var1);

    public InputStream load(String var1) throws IOException;
}

