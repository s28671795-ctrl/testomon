/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.core5.http.message;

import java.io.Serializable;
import org.apache.hc.core5.http.FormattedHeader;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.CharArrayBuffer;
import org.apache.hc.core5.util.Tokenizer;

public class BufferedHeader
implements FormattedHeader,
Serializable {
    private static final long serialVersionUID = -2768352615787625448L;
    private final String name;
    private final CharArrayBuffer buffer;
    private final int valuePos;

    public static BufferedHeader create(CharArrayBuffer buffer) {
        try {
            return new BufferedHeader(buffer);
        }
        catch (ParseException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public BufferedHeader(CharArrayBuffer buffer) throws ParseException {
        this(buffer, true);
    }

    BufferedHeader(CharArrayBuffer buffer, boolean strict) throws ParseException {
        Args.notNull(buffer, "Char array buffer");
        int colon2 = buffer.indexOf(58);
        if (colon2 <= 0) {
            throw new ParseException("Invalid header", buffer, 0, buffer.length());
        }
        if (strict && Tokenizer.isWhitespace(buffer.charAt(colon2 - 1))) {
            throw new ParseException("Invalid header", buffer, 0, buffer.length(), colon2 - 1);
        }
        String s2 = buffer.substringTrimmed(0, colon2);
        if (s2.isEmpty()) {
            throw new ParseException("Invalid header", buffer, 0, buffer.length(), colon2);
        }
        this.buffer = buffer;
        this.name = s2;
        this.valuePos = colon2 + 1;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.buffer.substringTrimmed(this.valuePos, this.buffer.length());
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public int getValuePos() {
        return this.valuePos;
    }

    @Override
    public CharArrayBuffer getBuffer() {
        return this.buffer;
    }

    public String toString() {
        return this.buffer.toString();
    }
}

