/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.util;

import java.io.IOException;
import java.io.Reader;

public class MIMEInputReader
extends Reader {
    protected Reader source;
    protected boolean atLineBreak = true;
    protected boolean endOfData = false;

    public MIMEInputReader(Reader source2) {
        this.source = source2;
    }

    public int read(char[] buffer, int off, int len) throws IOException {
        int lastRead;
        int bytesRead;
        if (len == 0) {
            return 0;
        }
        if (this.endOfData) {
            return -1;
        }
        for (bytesRead = 0; bytesRead < len && (lastRead = this.source.read()) >= 0; ++bytesRead) {
            if (this.atLineBreak && lastRead == 46) {
                lastRead = this.source.read();
                if (lastRead == 13) {
                    this.source.read();
                    this.endOfData = true;
                    break;
                }
                this.atLineBreak = false;
            } else {
                this.atLineBreak = lastRead == 10;
            }
            buffer[off++] = (char)lastRead;
        }
        if (bytesRead == 0) {
            return -1;
        }
        return bytesRead;
    }

    public void close() throws IOException {
    }
}

