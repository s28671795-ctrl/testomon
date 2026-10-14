/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.output;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import org.apache.commons.io.IOUtils;

public class ProxyWriter
extends FilterWriter {
    public ProxyWriter(Writer proxy) {
        super(proxy);
    }

    @Override
    public Writer append(char c) throws IOException {
        try {
            this.beforeWrite(1);
            this.out.append(c);
            this.afterWrite(1);
        }
        catch (IOException e) {
            this.handleIOException(e);
        }
        return this;
    }

    @Override
    public Writer append(CharSequence csq, int start2, int end) throws IOException {
        try {
            this.beforeWrite(end - start2);
            this.out.append(csq, start2, end);
            this.afterWrite(end - start2);
        }
        catch (IOException e) {
            this.handleIOException(e);
        }
        return this;
    }

    @Override
    public Writer append(CharSequence csq) throws IOException {
        try {
            int len = IOUtils.length(csq);
            this.beforeWrite(len);
            this.out.append(csq);
            this.afterWrite(len);
        }
        catch (IOException e) {
            this.handleIOException(e);
        }
        return this;
    }

    @Override
    public void write(int c) throws IOException {
        try {
            this.beforeWrite(1);
            this.out.write(c);
            this.afterWrite(1);
        }
        catch (IOException e) {
            this.handleIOException(e);
        }
    }

    @Override
    public void write(char[] cbuf) throws IOException {
        try {
            int len = IOUtils.length(cbuf);
            this.beforeWrite(len);
            this.out.write(cbuf);
            this.afterWrite(len);
        }
        catch (IOException e) {
            this.handleIOException(e);
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        try {
            this.beforeWrite(len);
            this.out.write(cbuf, off, len);
            this.afterWrite(len);
        }
        catch (IOException e) {
            this.handleIOException(e);
        }
    }

    @Override
    public void write(String str) throws IOException {
        try {
            int len = IOUtils.length(str);
            this.beforeWrite(len);
            this.out.write(str);
            this.afterWrite(len);
        }
        catch (IOException e) {
            this.handleIOException(e);
        }
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        try {
            this.beforeWrite(len);
            this.out.write(str, off, len);
            this.afterWrite(len);
        }
        catch (IOException e) {
            this.handleIOException(e);
        }
    }

    @Override
    public void flush() throws IOException {
        try {
            this.out.flush();
        }
        catch (IOException e) {
            this.handleIOException(e);
        }
    }

    @Override
    public void close() throws IOException {
        IOUtils.close(this.out, e -> this.handleIOException((IOException)e));
    }

    protected void beforeWrite(int n) throws IOException {
    }

    protected void afterWrite(int n) throws IOException {
    }

    protected void handleIOException(IOException e) throws IOException {
        throw e;
    }
}

