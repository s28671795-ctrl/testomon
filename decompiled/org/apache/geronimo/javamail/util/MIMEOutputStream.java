/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.util;

import java.io.IOException;
import java.io.OutputStream;

public class MIMEOutputStream
extends OutputStream {
    protected OutputStream out;
    protected int lastWrite = -1;
    protected boolean atLineBreak = true;

    public MIMEOutputStream(OutputStream out) {
        this.out = out;
    }

    public void write(int ch) throws IOException {
        if (ch == 13) {
            this.out.write(13);
            this.out.write(10);
            this.atLineBreak = true;
        } else if (ch == 10) {
            if (this.lastWrite != 13) {
                this.out.write(13);
                this.out.write(10);
            }
            this.atLineBreak = true;
        } else if (ch == 46) {
            if (this.atLineBreak) {
                this.out.write(46);
            }
            this.out.write(46);
            this.atLineBreak = false;
        } else {
            this.out.write(ch);
            this.atLineBreak = false;
        }
        this.lastWrite = ch;
    }

    public void forceTerminatingLineBreak() throws IOException {
        if (!this.atLineBreak) {
            this.out.write(13);
            this.out.write(10);
            this.atLineBreak = true;
        }
    }

    public void writeSMTPTerminator() throws IOException {
        this.forceTerminatingLineBreak();
        this.out.write(46);
        this.out.write(13);
        this.out.write(10);
    }
}

