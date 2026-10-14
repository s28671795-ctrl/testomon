/*
 * Decompiled with CFR 0.152.
 */
package com.sun.mail.smtp;

import com.sun.mail.util.CRLFOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SMTPOutputStream
extends CRLFOutputStream {
    public SMTPOutputStream(OutputStream os) {
        super(os);
    }

    public void write(int b) throws IOException {
        if ((this.lastb == 10 || this.lastb == 13 || this.lastb == -1) && b == 46) {
            this.out.write(46);
        }
        super.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        int lastc = this.lastb == -1 ? 10 : this.lastb;
        int start2 = off;
        len += off;
        for (int i = off; i < len; ++i) {
            if ((lastc == 10 || lastc == 13) && b[i] == 46) {
                super.write(b, start2, i - start2);
                this.out.write(46);
                start2 = i;
            }
            lastc = b[i];
        }
        if (len - start2 > 0) {
            super.write(b, start2, len - start2);
        }
    }

    public void flush() {
    }

    public void ensureAtBOL() throws IOException {
        if (!this.atBOL) {
            super.writeln();
        }
    }
}

