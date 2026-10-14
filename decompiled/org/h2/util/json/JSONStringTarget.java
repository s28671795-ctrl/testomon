/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import java.math.BigDecimal;
import org.h2.util.ByteStack;
import org.h2.util.json.JSONTarget;

public final class JSONStringTarget
extends JSONTarget<String> {
    static final char[] HEX = "0123456789abcdef".toCharArray();
    static final byte OBJECT = 1;
    static final byte ARRAY = 2;
    private final StringBuilder builder = new StringBuilder();
    private final ByteStack stack = new ByteStack();
    private final boolean asciiPrintableOnly;
    private boolean needSeparator;
    private boolean afterName;

    public static StringBuilder encodeString(StringBuilder stringBuilder, String string, boolean bl) {
        stringBuilder.append('\"');
        int n = string.length();
        block10: for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            switch (c) {
                case '\b': {
                    stringBuilder.append("\\b");
                    continue block10;
                }
                case '\t': {
                    stringBuilder.append("\\t");
                    continue block10;
                }
                case '\f': {
                    stringBuilder.append("\\f");
                    continue block10;
                }
                case '\n': {
                    stringBuilder.append("\\n");
                    continue block10;
                }
                case '\r': {
                    stringBuilder.append("\\r");
                    continue block10;
                }
                case '\"': {
                    stringBuilder.append("\\\"");
                    continue block10;
                }
                case '\'': {
                    if (bl) {
                        stringBuilder.append("\\u0027");
                        continue block10;
                    }
                    stringBuilder.append('\'');
                    continue block10;
                }
                case '\\': {
                    stringBuilder.append("\\\\");
                    continue block10;
                }
                default: {
                    if (c < ' ') {
                        stringBuilder.append("\\u00").append(HEX[c >>> 4 & 0xF]).append(HEX[c & 0xF]);
                        continue block10;
                    }
                    if (!bl || c <= '\u007f') {
                        stringBuilder.append(c);
                        continue block10;
                    }
                    stringBuilder.append("\\u").append(HEX[c >>> 12 & 0xF]).append(HEX[c >>> 8 & 0xF]).append(HEX[c >>> 4 & 0xF]).append(HEX[c & 0xF]);
                }
            }
        }
        return stringBuilder.append('\"');
    }

    public JSONStringTarget() {
        this(false);
    }

    public JSONStringTarget(boolean bl) {
        this.asciiPrintableOnly = bl;
    }

    @Override
    public void startObject() {
        this.beforeValue();
        this.afterName = false;
        this.stack.push((byte)1);
        this.builder.append('{');
    }

    @Override
    public void endObject() {
        if (this.afterName || this.stack.poll(-1) != 1) {
            throw new IllegalStateException();
        }
        this.builder.append('}');
        this.afterValue();
    }

    @Override
    public void startArray() {
        this.beforeValue();
        this.afterName = false;
        this.stack.push((byte)2);
        this.builder.append('[');
    }

    @Override
    public void endArray() {
        if (this.stack.poll(-1) != 2) {
            throw new IllegalStateException();
        }
        this.builder.append(']');
        this.afterValue();
    }

    @Override
    public void member(String string) {
        if (this.afterName || this.stack.peek(-1) != 1) {
            throw new IllegalStateException();
        }
        this.afterName = true;
        this.beforeValue();
        JSONStringTarget.encodeString(this.builder, string, this.asciiPrintableOnly).append(':');
    }

    @Override
    public void valueNull() {
        this.beforeValue();
        this.builder.append("null");
        this.afterValue();
    }

    @Override
    public void valueFalse() {
        this.beforeValue();
        this.builder.append("false");
        this.afterValue();
    }

    @Override
    public void valueTrue() {
        this.beforeValue();
        this.builder.append("true");
        this.afterValue();
    }

    @Override
    public void valueNumber(BigDecimal bigDecimal) {
        this.beforeValue();
        String string = bigDecimal.toString();
        int n = string.indexOf(69);
        if (n >= 0 && string.charAt(++n) == '+') {
            this.builder.append(string, 0, n).append(string, n + 1, string.length());
        } else {
            this.builder.append(string);
        }
        this.afterValue();
    }

    @Override
    public void valueString(String string) {
        this.beforeValue();
        JSONStringTarget.encodeString(this.builder, string, this.asciiPrintableOnly);
        this.afterValue();
    }

    private void beforeValue() {
        if (!this.afterName && this.stack.peek(-1) == 1) {
            throw new IllegalStateException();
        }
        if (this.needSeparator) {
            if (this.stack.isEmpty()) {
                throw new IllegalStateException();
            }
            this.needSeparator = false;
            this.builder.append(',');
        }
    }

    private void afterValue() {
        this.needSeparator = true;
        this.afterName = false;
    }

    @Override
    public boolean isPropertyExpected() {
        return !this.afterName && this.stack.peek(-1) == 1;
    }

    @Override
    public boolean isValueSeparatorExpected() {
        return this.needSeparator;
    }

    @Override
    public String getResult() {
        if (!this.stack.isEmpty() || this.builder.length() == 0) {
            throw new IllegalStateException();
        }
        return this.builder.toString();
    }
}

