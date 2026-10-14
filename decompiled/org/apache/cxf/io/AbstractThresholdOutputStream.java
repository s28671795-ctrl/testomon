/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.io;

import java.io.IOException;
import org.apache.cxf.helpers.LoadingByteArrayOutputStream;
import org.apache.cxf.io.AbstractWrappedOutputStream;

public abstract class AbstractThresholdOutputStream
extends AbstractWrappedOutputStream {
    protected int threshold;
    protected LoadingByteArrayOutputStream buffer;

    public AbstractThresholdOutputStream(int threshold) {
        this.threshold = threshold;
        if (threshold >= 0) {
            this.buffer = new LoadingByteArrayOutputStream(threshold + 1);
        }
    }

    public abstract void thresholdReached() throws IOException;

    public abstract void thresholdNotReached() throws IOException;

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (this.buffer != null) {
            int space = this.threshold - this.buffer.size();
            if (space > len) {
                space = len;
            }
            this.buffer.write(b, off, space);
            len -= space;
            off += space;
            if (this.buffer.size() >= this.threshold) {
                this.thresholdReached();
                this.unBuffer();
            }
            if (len == 0) {
                return;
            }
        }
        super.write(b, off, len);
    }

    @Override
    public void write(int b) throws IOException {
        if (this.buffer != null) {
            this.buffer.write(b);
            if (this.buffer.size() >= this.threshold) {
                this.thresholdReached();
                this.unBuffer();
            }
            return;
        }
        super.write(b);
    }

    public void unBuffer() throws IOException {
        if (this.buffer != null) {
            if (this.buffer.size() > 0) {
                super.write(this.buffer.getRawBytes(), 0, this.buffer.size());
            }
            this.buffer = null;
        }
    }

    @Override
    public void close() throws IOException {
        if (this.buffer != null) {
            this.thresholdNotReached();
            this.unBuffer();
        }
        super.close();
    }
}

