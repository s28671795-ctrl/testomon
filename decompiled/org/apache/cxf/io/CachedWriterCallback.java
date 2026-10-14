/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.io;

import org.apache.cxf.io.CachedWriter;

public interface CachedWriterCallback {
    public void onClose(CachedWriter var1);

    public void onFlush(CachedWriter var1);
}

