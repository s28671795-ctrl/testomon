/*
 * Decompiled with CFR 0.152.
 */
package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CRLFOutputStream
extends FilterOutputStream {
    protected int lastb = -1;
    protected boolean atBOL = true;
    private static final byte[] newline = new byte[]{13, 10};

    public CRLFOutputStream(OutputStream os) {
        super(os);
    }

    public void write(int b) throws IOException {
        if (b == 13) {
            this.writeln();
        } else if (b == 10) {
            if (this.lastb != 13) {
                this.writeln();
            }
        } else {
            this.out.write(b);
            this.atBOL = false;
        }
        this.lastb = b;
    }

    public void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        int start2 = off;
        len += off;
        for (int i = start2; i < len; ++i) {
            if (b[i] == 13) {
                this.out.write(b, start2, i - start2);
                this.writeln();
                start2 = i + 1;
            } else if (b[i] == 10) {
                if (this.lastb != 13) {
                    this.out.write(b, start2, i - start2);
                    this.writeln();
                }
                start2 = i + 1;
            }
            this.lastb = b[i];
        }
        if (len - start2 > 0) {
            this.out.write(b, start2, len - start2);
            this.atBOL = false;
        }
    }

    public void writeln() throws IOException {
        this.out.write(newline);
        this.atBOL = true;
    }
}

