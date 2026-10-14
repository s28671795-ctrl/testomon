/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.geronimo.mail.util.QuotedPrintableEncoderStream;

public class TraceInputStream
extends FilterInputStream {
    protected boolean debug = false;
    protected OutputStream traceStream;

    public TraceInputStream(InputStream in, OutputStream traceStream, boolean debug, boolean encode) {
        super(in);
        this.debug = debug;
        this.traceStream = encode ? new QuotedPrintableEncoderStream(traceStream) : traceStream;
    }

    public void setDebug(boolean d) {
        this.debug = d;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int count = this.in.read(b, off, len);
        if (this.debug && count > 0) {
            this.traceStream.write(b, off, count);
        }
        return count;
    }

    public int read() throws IOException {
        int b = this.in.read();
        if (this.debug) {
            this.traceStream.write(b);
        }
        return b;
    }
}

