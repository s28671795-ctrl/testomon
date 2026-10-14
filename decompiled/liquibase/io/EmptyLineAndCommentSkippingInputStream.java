/*
 * Decompiled with CFR 0.152.
 */
package liquibase.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import liquibase.util.StringUtil;

public class EmptyLineAndCommentSkippingInputStream
extends BufferedInputStream {
    public static final int MAX_CHAR_SIZE_IN_BYTES = 4;
    private final String commentLineStartsWith;
    private final boolean commentSkipEnabled;
    private int lastRead = -1;

    public EmptyLineAndCommentSkippingInputStream(InputStream in, String commentLineStartsWith) {
        super(in);
        this.commentLineStartsWith = commentLineStartsWith;
        this.commentSkipEnabled = StringUtil.isNotEmpty(commentLineStartsWith);
    }

    @Override
    public synchronized int read() throws IOException {
        return this.read(this.lastRead, false);
    }

    private int read(int lastRead, boolean lookAhead) throws IOException {
        int read = super.read();
        if (this.commentSkipEnabled && read == this.commentLineStartsWith.toCharArray()[0] && (lastRead == 10 || lastRead < 0)) {
            while ((read = super.read()) != 10 && read != 13 && read > 0) {
            }
        }
        if (read < 0) {
            return read;
        }
        if (read == 13) {
            return this.read();
        }
        if (read == 10 && lastRead == 10) {
            return this.read();
        }
        if (read == 10) {
            if (lastRead < 0) {
                return this.read();
            }
            this.mark(4);
            if (this.read(10, true) < 0) {
                return -1;
            }
            this.reset();
        }
        if (!lookAhead) {
            this.lastRead = read;
        }
        return read;
    }
}

