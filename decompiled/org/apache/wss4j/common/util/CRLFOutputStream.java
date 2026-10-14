/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CRLFOutputStream
extends FilterOutputStream {
    private static final byte CR = 13;
    private static final byte LF = 10;
    private static final byte[] CRLF = new byte[]{13, 10};
    private boolean lastByteCR = false;

    public CRLFOutputStream(OutputStream out) {
        super(out);
    }

    @Override
    public void write(int b) throws IOException {
        if (b == 13) {
            this.out.write(CRLF);
            this.lastByteCR = true;
        } else if (b == 10) {
            if (this.lastByteCR) {
                this.lastByteCR = false;
            } else {
                this.out.write(CRLF);
            }
        } else {
            this.out.write(b);
            this.lastByteCR = false;
        }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        int start2 = off;
        for (int i = off; i < len; ++i) {
            if (b[i] == 13) {
                this.out.write(b, start2, i + 1 - start2);
                this.out.write(10);
                this.lastByteCR = true;
                start2 = i + 1;
                continue;
            }
            if (b[i] == 10) {
                if (this.lastByteCR) {
                    this.lastByteCR = false;
                    ++start2;
                    continue;
                }
                int l = i - start2;
                if (l > 0) {
                    this.out.write(b, start2, l);
                }
                this.out.write(CRLF);
                start2 = i + 1;
                continue;
            }
            this.lastByteCR = false;
        }
        this.out.write(b, start2, len - start2);
    }
}

