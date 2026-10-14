/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import java.io.IOException;
import java.io.OutputStream;

public class StringBufferOutputStream
extends OutputStream {
    protected StringBuffer buffer;

    public StringBufferOutputStream(StringBuffer out) {
        this.buffer = out;
    }

    public void write(int ch) throws IOException {
        this.buffer.append((char)ch);
    }
}

