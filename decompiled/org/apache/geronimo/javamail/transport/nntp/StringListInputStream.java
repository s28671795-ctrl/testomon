/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.transport.nntp;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class StringListInputStream
extends InputStream {
    protected List lines;
    protected int nextLine = 0;
    byte[] buffer;
    int offset;
    boolean atLineBreak = false;

    public StringListInputStream(List lines) throws IOException {
        this.lines = lines;
        this.nextLine = 0;
        this.buffer = null;
        this.offset = 0;
        this.atLineBreak = false;
        if (lines.size() > 0) {
            this.nextBuffer();
        }
    }

    public int read() throws IOException {
        if (this.atLineBreak) {
            this.atLineBreak = false;
            this.nextBuffer();
            return 10;
        }
        if (this.buffer == null) {
            return -1;
        }
        if (this.offset >= this.buffer.length) {
            this.atLineBreak = true;
            return 13;
        }
        return this.buffer[this.offset++];
    }

    protected void nextBuffer() throws IOException {
        if (this.nextLine >= this.lines.size()) {
            this.buffer = null;
        } else {
            try {
                String next = (String)this.lines.get(this.nextLine++);
                this.buffer = next.getBytes("US-ASCII");
            }
            catch (UnsupportedEncodingException e) {
                throw new IOException("Invalid string encoding");
            }
        }
        this.offset = 0;
    }
}

