/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.geronimo.mail.util.QuotedPrintableEncoderStream;

public class TraceOutputStream
extends FilterOutputStream {
    protected boolean debug = false;
    protected OutputStream traceStream;

    public TraceOutputStream(OutputStream out, OutputStream traceStream, boolean debug, boolean encode) {
        super(out);
        this.debug = debug;
        this.traceStream = encode ? new QuotedPrintableEncoderStream(traceStream) : traceStream;
    }

    public void setDebug(boolean d) {
        this.debug = d;
    }

    public void write(int b) throws IOException {
        if (this.debug) {
            this.traceStream.write(b);
        }
        super.write(b);
    }
}

