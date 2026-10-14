/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import java.math.BigDecimal;
import org.h2.util.StringUtils;
import org.h2.util.json.JSONByteArrayTarget;
import org.h2.util.json.JSONTarget;
import org.h2.util.json.JSONTextSource;

public final class JSONStringSource
extends JSONTextSource {
    private final String string;
    private final int length;
    private int index;

    public static <R> R parse(String string, JSONTarget<R> jSONTarget) {
        new JSONStringSource(string, jSONTarget).parse();
        return jSONTarget.getResult();
    }

    public static byte[] normalize(String string) {
        return JSONStringSource.parse(string, new JSONByteArrayTarget());
    }

    JSONStringSource(String string, JSONTarget<?> jSONTarget) {
        super(jSONTarget);
        this.string = string;
        this.length = string.length();
        if (this.length == 0) {
            throw new IllegalArgumentException();
        }
        if (string.charAt(this.index) == '\ufeff') {
            ++this.index;
        }
    }

    @Override
    int nextCharAfterWhitespace() {
        int n = this.index;
        block3: while (n < this.length) {
            char c = this.string.charAt(n++);
            switch (c) {
                case '\t': 
                case '\n': 
                case '\r': 
                case ' ': {
                    continue block3;
                }
            }
            this.index = n;
            return c;
        }
        return -1;
    }

    @Override
    void readKeyword1(String string) {
        int n = string.length() - 1;
        if (!this.string.regionMatches(this.index, string, 1, n)) {
            throw new IllegalArgumentException();
        }
        this.index += n;
    }

    @Override
    void parseNumber(boolean bl) {
        int n;
        int n2;
        block6: {
            char c;
            block7: {
                n2 = this.index;
                n = n2 - 1;
                if ((n2 = this.skipInt(n2, bl)) >= this.length) break block6;
                c = this.string.charAt(n2);
                if (c != '.') break block7;
                if ((n2 = this.skipInt(n2 + 1, false)) >= this.length) break block6;
                c = this.string.charAt(n2);
            }
            if (c == 'E' || c == 'e') {
                if (++n2 >= this.length) {
                    throw new IllegalArgumentException();
                }
                c = this.string.charAt(n2);
                if (c == '+' || c == '-') {
                    ++n2;
                }
                n2 = this.skipInt(n2, false);
            }
        }
        this.target.valueNumber(new BigDecimal(this.string.substring(n, n2)));
        this.index = n2;
    }

    private int skipInt(int n, boolean bl) {
        char c;
        while (n < this.length && (c = this.string.charAt(n)) >= '0' && c <= '9') {
            bl = true;
            ++n;
        }
        if (!bl) {
            throw new IllegalArgumentException();
        }
        return n;
    }

    @Override
    int nextChar() {
        if (this.index >= this.length) {
            throw new IllegalArgumentException();
        }
        return this.string.charAt(this.index++);
    }

    @Override
    char readHex() {
        if (this.index + 3 >= this.length) {
            throw new IllegalArgumentException();
        }
        try {
            return (char)Integer.parseInt(this.string.substring(this.index, this.index += 4), 16);
        }
        catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException();
        }
    }

    public String toString() {
        return StringUtils.addAsterisk(this.string, this.index);
    }
}

