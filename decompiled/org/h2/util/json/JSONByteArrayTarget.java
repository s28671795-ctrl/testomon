/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import org.h2.util.ByteStack;
import org.h2.util.json.JSONStringTarget;
import org.h2.util.json.JSONTarget;

public final class JSONByteArrayTarget
extends JSONTarget<byte[]> {
    private static final byte[] NULL_BYTES = "null".getBytes(StandardCharsets.ISO_8859_1);
    private static final byte[] FALSE_BYTES = "false".getBytes(StandardCharsets.ISO_8859_1);
    private static final byte[] TRUE_BYTES = "true".getBytes(StandardCharsets.ISO_8859_1);
    private static final byte[] U00_BYTES = "\\u00".getBytes(StandardCharsets.ISO_8859_1);
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private final ByteStack stack = new ByteStack();
    private boolean needSeparator;
    private boolean afterName;

    public static ByteArrayOutputStream encodeString(ByteArrayOutputStream byteArrayOutputStream, String string) {
        byteArrayOutputStream.write(34);
        int n = string.length();
        block9: for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            switch (c) {
                case '\b': {
                    byteArrayOutputStream.write(92);
                    byteArrayOutputStream.write(98);
                    continue block9;
                }
                case '\t': {
                    byteArrayOutputStream.write(92);
                    byteArrayOutputStream.write(116);
                    continue block9;
                }
                case '\f': {
                    byteArrayOutputStream.write(92);
                    byteArrayOutputStream.write(102);
                    continue block9;
                }
                case '\n': {
                    byteArrayOutputStream.write(92);
                    byteArrayOutputStream.write(110);
                    continue block9;
                }
                case '\r': {
                    byteArrayOutputStream.write(92);
                    byteArrayOutputStream.write(114);
                    continue block9;
                }
                case '\"': {
                    byteArrayOutputStream.write(92);
                    byteArrayOutputStream.write(34);
                    continue block9;
                }
                case '\\': {
                    byteArrayOutputStream.write(92);
                    byteArrayOutputStream.write(92);
                    continue block9;
                }
                default: {
                    if (c >= ' ') {
                        char c2;
                        if (c < '\u0080') {
                            byteArrayOutputStream.write(c);
                            continue block9;
                        }
                        if (c < '\u0800') {
                            byteArrayOutputStream.write(0xC0 | c >> 6);
                            byteArrayOutputStream.write(0x80 | c & 0x3F);
                            continue block9;
                        }
                        if (!Character.isSurrogate(c)) {
                            byteArrayOutputStream.write(0xE0 | c >> 12);
                            byteArrayOutputStream.write(0x80 | c >> 6 & 0x3F);
                            byteArrayOutputStream.write(0x80 | c & 0x3F);
                            continue block9;
                        }
                        if (!Character.isHighSurrogate(c) || ++i >= n || !Character.isLowSurrogate(c2 = string.charAt(i))) {
                            throw new IllegalArgumentException();
                        }
                        int n2 = Character.toCodePoint(c, c2);
                        byteArrayOutputStream.write(0xF0 | n2 >> 18);
                        byteArrayOutputStream.write(0x80 | n2 >> 12 & 0x3F);
                        byteArrayOutputStream.write(0x80 | n2 >> 6 & 0x3F);
                        byteArrayOutputStream.write(0x80 | n2 & 0x3F);
                        continue block9;
                    }
                    byteArrayOutputStream.write(U00_BYTES, 0, 4);
                    byteArrayOutputStream.write(JSONStringTarget.HEX[c >>> 4 & 0xF]);
                    byteArrayOutputStream.write(JSONStringTarget.HEX[c & 0xF]);
                }
            }
        }
        byteArrayOutputStream.write(34);
        return byteArrayOutputStream;
    }

    @Override
    public void startObject() {
        this.beforeValue();
        this.afterName = false;
        this.stack.push((byte)1);
        this.baos.write(123);
    }

    @Override
    public void endObject() {
        if (this.afterName || this.stack.poll(-1) != 1) {
            throw new IllegalStateException();
        }
        this.baos.write(125);
        this.afterValue();
    }

    @Override
    public void startArray() {
        this.beforeValue();
        this.afterName = false;
        this.stack.push((byte)2);
        this.baos.write(91);
    }

    @Override
    public void endArray() {
        if (this.stack.poll(-1) != 2) {
            throw new IllegalStateException();
        }
        this.baos.write(93);
        this.afterValue();
    }

    @Override
    public void member(String string) {
        if (this.afterName || this.stack.peek(-1) != 1) {
            throw new IllegalStateException();
        }
        this.afterName = true;
        this.beforeValue();
        JSONByteArrayTarget.encodeString(this.baos, string).write(58);
    }

    @Override
    public void valueNull() {
        this.beforeValue();
        this.baos.write(NULL_BYTES, 0, 4);
        this.afterValue();
    }

    @Override
    public void valueFalse() {
        this.beforeValue();
        this.baos.write(FALSE_BYTES, 0, 5);
        this.afterValue();
    }

    @Override
    public void valueTrue() {
        this.beforeValue();
        this.baos.write(TRUE_BYTES, 0, 4);
        this.afterValue();
    }

    @Override
    public void valueNumber(BigDecimal bigDecimal) {
        this.beforeValue();
        String string = bigDecimal.toString();
        int n = string.indexOf(69);
        byte[] byArray = string.getBytes(StandardCharsets.ISO_8859_1);
        if (n >= 0 && string.charAt(++n) == '+') {
            this.baos.write(byArray, 0, n);
            this.baos.write(byArray, n + 1, byArray.length - n - 1);
        } else {
            this.baos.write(byArray, 0, byArray.length);
        }
        this.afterValue();
    }

    @Override
    public void valueString(String string) {
        this.beforeValue();
        JSONByteArrayTarget.encodeString(this.baos, string);
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
            this.baos.write(44);
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
    public byte[] getResult() {
        if (!this.stack.isEmpty() || this.baos.size() == 0) {
            throw new IllegalStateException();
        }
        return this.baos.toByteArray();
    }
}

