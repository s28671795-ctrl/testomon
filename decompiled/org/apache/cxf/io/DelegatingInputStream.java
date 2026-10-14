/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.io;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;

public class DelegatingInputStream
extends FilterInputStream {
    protected boolean cached;

    public DelegatingInputStream(InputStream is) {
        super(is);
    }

    public void setInputStream(InputStream inputStream2) {
        this.in = inputStream2;
    }

    public InputStream getInputStream() {
        return this.in;
    }

    public void cacheInput() {
        if (!this.cached) {
            CachedOutputStream cache = new CachedOutputStream();
            try {
                InputStream origIn = this.in;
                IOUtils.copy(this.in, cache);
                this.in = cache.size() > 0L ? cache.getInputStream() : new ByteArrayInputStream(new byte[0]);
                cache.close();
                origIn.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
            this.cached = true;
        }
    }
}

