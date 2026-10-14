/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

public class HexOutputStream
extends OutputStream {
    private static final char[] hex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private StringWriter writer;

    public HexOutputStream(StringWriter w) {
        this.writer = w;
    }

    @Override
    public synchronized void write(int b) throws IOException {
        this.writer.write(hex[b >> 4 & 0xF]);
        this.writer.write(hex[b >> 0 & 0xF]);
    }

    @Override
    public synchronized void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) throws IOException {
        for (int i = 0; i < len; ++i) {
            this.write(b[off + i]);
        }
    }
}

