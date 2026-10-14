/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.handler.gzip;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.compression.CompressionPool;

public interface GzipFactory {
    public CompressionPool.Entry getDeflaterEntry(Request var1, long var2);

    public boolean isMimeTypeGzipable(String var1);
}

