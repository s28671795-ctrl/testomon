/*
 * Decompiled with CFR 0.152.
 */
package com.sun.mail.util;

import com.sun.mail.util.MailLogger;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;

public class LogOutputStream
extends OutputStream {
    protected MailLogger logger;
    protected Level level;
    private int lastb = -1;
    private byte[] buf = new byte[80];
    private int pos = 0;

    public LogOutputStream(MailLogger logger2) {
        this.logger = logger2;
        this.level = Level.FINEST;
    }

    public void write(int b) throws IOException {
        if (!this.logger.isLoggable(this.level)) {
            return;
        }
        if (b == 13) {
            this.logBuf();
        } else if (b == 10) {
            if (this.lastb != 13) {
                this.logBuf();
            }
        } else {
            this.expandCapacity(1);
            this.buf[this.pos++] = (byte)b;
        }
        this.lastb = b;
    }

    public void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        int start2 = off;
        if (!this.logger.isLoggable(this.level)) {
            return;
        }
        len += off;
        for (int i = start2; i < len; ++i) {
            if (b[i] == 13) {
                this.expandCapacity(i - start2);
                System.arraycopy(b, start2, this.buf, this.pos, i - start2);
                this.pos += i - start2;
                this.logBuf();
                start2 = i + 1;
            } else if (b[i] == 10) {
                if (this.lastb != 13) {
                    this.expandCapacity(i - start2);
                    System.arraycopy(b, start2, this.buf, this.pos, i - start2);
                    this.pos += i - start2;
                    this.logBuf();
                }
                start2 = i + 1;
            }
            this.lastb = b[i];
        }
        if (len - start2 > 0) {
            this.expandCapacity(len - start2);
            System.arraycopy(b, start2, this.buf, this.pos, len - start2);
            this.pos += len - start2;
        }
    }

    protected void log(String msg) {
        this.logger.log(this.level, msg);
    }

    private void logBuf() {
        String msg = new String(this.buf, 0, this.pos);
        this.pos = 0;
        this.log(msg);
    }

    private void expandCapacity(int len) {
        while (this.pos + len > this.buf.length) {
            byte[] nb = new byte[this.buf.length * 2];
            System.arraycopy(this.buf, 0, nb, 0, this.pos);
            this.buf = nb;
        }
    }
}

