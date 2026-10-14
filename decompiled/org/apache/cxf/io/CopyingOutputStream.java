/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.io;

import java.io.IOException;
import java.io.InputStream;

public interface CopyingOutputStream {
    public int copyFrom(InputStream var1) throws IOException;
}

