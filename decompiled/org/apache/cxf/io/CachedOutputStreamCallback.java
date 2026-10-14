/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.io;

import org.apache.cxf.io.CachedOutputStream;

public interface CachedOutputStreamCallback {
    public void onClose(CachedOutputStream var1);

    public void onFlush(CachedOutputStream var1);
}

