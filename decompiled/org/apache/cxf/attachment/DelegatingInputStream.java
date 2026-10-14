/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.cxf.attachment.AttachmentDeserializer;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.Transferable;

public class DelegatingInputStream
extends InputStream
implements Transferable {
    private InputStream is;
    private AttachmentDeserializer deserializer;
    private boolean isClosed;

    DelegatingInputStream(InputStream is, AttachmentDeserializer ads) {
        this.is = is;
        this.deserializer = ads;
    }

    DelegatingInputStream(InputStream is) {
        this.is = is;
        this.deserializer = null;
    }

    @Override
    public void close() throws IOException {
        IOUtils.consume(this.is);
        this.is.close();
        if (!this.isClosed && this.deserializer != null) {
            this.deserializer.markClosed(this);
        }
        this.isClosed = true;
    }

    @Override
    public void transferTo(File destinationFile) throws IOException {
        if (this.isClosed) {
            throw new IOException("Stream is closed");
        }
        IOUtils.transferTo(this.is, destinationFile);
    }

    public boolean isClosed() {
        return this.isClosed;
    }

    public void setClosed(boolean closed) {
        this.isClosed = closed;
    }

    @Override
    public int read() throws IOException {
        return this.is.read();
    }

    @Override
    public int available() throws IOException {
        return this.is.available();
    }

    @Override
    public synchronized void mark(int arg0) {
        this.is.mark(arg0);
    }

    @Override
    public boolean markSupported() {
        return this.is.markSupported();
    }

    @Override
    public int read(byte[] bytes, int arg1, int arg2) throws IOException {
        return this.is.read(bytes, arg1, arg2);
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        return this.is.read(bytes);
    }

    @Override
    public synchronized void reset() throws IOException {
        this.is.reset();
    }

    @Override
    public long skip(long n) throws IOException {
        return this.is.skip(n);
    }

    public void setInputStream(InputStream inputStream2) {
        this.is = inputStream2;
    }

    public InputStream getInputStream() {
        return this.is;
    }
}

