/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import org.h2.util.json.JSONTarget;

public abstract class JSONTextSource {
    final JSONTarget<?> target;
    private final StringBuilder builder;

    JSONTextSource(JSONTarget<?> jSONTarget) {
        this.target = jSONTarget;
        this.builder = new StringBuilder();
    }

    final void parse() {
        int n;
        boolean bl = false;
        block10: while ((n = this.nextCharAfterWhitespace()) >= 0) {
            if (n == 125 || n == 93) {
                if (bl) {
                    throw new IllegalArgumentException();
                }
                if (n == 125) {
                    this.target.endObject();
                    continue;
                }
                this.target.endArray();
                continue;
            }
            if (n == 44) {
                if (bl || !this.target.isValueSeparatorExpected()) {
                    throw new IllegalArgumentException();
                }
                bl = true;
                continue;
            }
            if (bl != this.target.isValueSeparatorExpected()) {
                throw new IllegalArgumentException();
            }
            bl = false;
            switch (n) {
                case 102: {
                    this.readKeyword1("false");
                    this.target.valueFalse();
                    continue block10;
                }
                case 110: {
                    this.readKeyword1("null");
                    this.target.valueNull();
                    continue block10;
                }
                case 116: {
                    this.readKeyword1("true");
                    this.target.valueTrue();
                    continue block10;
                }
                case 123: {
                    this.target.startObject();
                    continue block10;
                }
                case 91: {
                    this.target.startArray();
                    continue block10;
                }
                case 34: {
                    String string = this.readString();
                    if (this.target.isPropertyExpected()) {
                        if (this.nextCharAfterWhitespace() != 58) {
                            throw new IllegalArgumentException();
                        }
                        this.target.member(string);
                        continue block10;
                    }
                    this.target.valueString(string);
                    continue block10;
                }
                case 45: {
                    this.parseNumber(false);
                    continue block10;
                }
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: {
                    this.parseNumber(true);
                    continue block10;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    abstract int nextCharAfterWhitespace();

    abstract void readKeyword1(String var1);

    abstract void parseNumber(boolean var1);

    abstract int nextChar();

    abstract char readHex();

    private String readString() {
        this.builder.setLength(0);
        boolean bl = false;
        block13: while (true) {
            int n = this.nextChar();
            switch (n) {
                case 34: {
                    if (bl) {
                        throw new IllegalArgumentException();
                    }
                    return this.builder.toString();
                }
                case 92: {
                    n = this.nextChar();
                    switch (n) {
                        case 34: 
                        case 47: 
                        case 92: {
                            this.appendNonSurrogate((char)n, bl);
                            continue block13;
                        }
                        case 98: {
                            this.appendNonSurrogate('\b', bl);
                            continue block13;
                        }
                        case 102: {
                            this.appendNonSurrogate('\f', bl);
                            continue block13;
                        }
                        case 110: {
                            this.appendNonSurrogate('\n', bl);
                            continue block13;
                        }
                        case 114: {
                            this.appendNonSurrogate('\r', bl);
                            continue block13;
                        }
                        case 116: {
                            this.appendNonSurrogate('\t', bl);
                            continue block13;
                        }
                        case 117: {
                            bl = this.appendChar(this.readHex(), bl);
                            continue block13;
                        }
                    }
                    throw new IllegalArgumentException();
                }
            }
            if (Character.isBmpCodePoint(n)) {
                bl = this.appendChar((char)n, bl);
                continue;
            }
            if (bl) {
                throw new IllegalArgumentException();
            }
            this.builder.appendCodePoint(n);
            bl = false;
        }
    }

    private void appendNonSurrogate(char c, boolean bl) {
        if (bl) {
            throw new IllegalArgumentException();
        }
        this.builder.append(c);
    }

    private boolean appendChar(char c, boolean bl) {
        if (bl != Character.isLowSurrogate(c)) {
            throw new IllegalArgumentException();
        }
        if (bl) {
            bl = false;
        } else if (Character.isHighSurrogate(c)) {
            bl = true;
        }
        this.builder.append(c);
        return bl;
    }
}

