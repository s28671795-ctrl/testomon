/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.util;

import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream
extends OutputStream {
    int count = 0;

    public void write(int ch) throws IOException {
        ++this.count;
    }

    public int getCount() {
        return this.count;
    }

    public void reset() {
        this.count = 0;
    }
}

