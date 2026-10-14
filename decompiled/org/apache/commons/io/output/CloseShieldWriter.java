/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.output;

import java.io.Writer;
import org.apache.commons.io.output.ClosedWriter;
import org.apache.commons.io.output.ProxyWriter;

public class CloseShieldWriter
extends ProxyWriter {
    public CloseShieldWriter(Writer out) {
        super(out);
    }

    @Override
    public void close() {
        this.out = ClosedWriter.CLOSED_WRITER;
    }
}

