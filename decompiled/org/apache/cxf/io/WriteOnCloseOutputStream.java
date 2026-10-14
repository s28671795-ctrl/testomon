/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.cxf.io.CachedOutputStream;

public class WriteOnCloseOutputStream
extends CachedOutputStream {
    OutputStream flowThroughStream;

    public WriteOnCloseOutputStream(OutputStream stream) {
        this.flowThroughStream = stream;
    }

    @Override
    protected void doClose() throws IOException {
        this.resetOut(this.flowThroughStream, true);
        this.flowThroughStream.flush();
    }
}

