/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.stream.reader;

import java.io.BufferedReader;
import java.io.IOException;

public class LineReader {
    private final BufferedReader reader;
    private final boolean keepCarriageReturns;

    public LineReader(BufferedReader reader, boolean keepCarriageReturns) {
        this.reader = reader;
        this.keepCarriageReturns = keepCarriageReturns;
    }

    public String readLine() throws IOException {
        return this.keepCarriageReturns ? this.readUntilNewline() : this.reader.readLine();
    }

    private String readUntilNewline() throws IOException {
        StringBuilder sb = new StringBuilder(1024);
        int c = this.reader.read();
        while (c > -1 && c != 10) {
            sb.append((char)c);
            c = this.reader.read();
        }
        return sb.length() > 0 ? sb.toString() : null;
    }
}

