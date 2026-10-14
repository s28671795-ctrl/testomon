/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;

@InternalApi
public final class JsonObjectParser$ {
    public static final JsonObjectParser$ MODULE$ = new JsonObjectParser$();
    private static final byte SquareBraceStart = (byte)91;
    private static final byte SquareBraceEnd = (byte)93;
    private static final byte CurlyBraceStart = (byte)123;
    private static final byte CurlyBraceEnd = (byte)125;
    private static final byte DoubleQuote = (byte)34;
    private static final byte Backslash = (byte)92;
    private static final byte Comma = (byte)44;

    public int $lessinit$greater$default$1() {
        return Integer.MAX_VALUE;
    }

    public final byte SquareBraceStart() {
        return SquareBraceStart;
    }

    public final byte SquareBraceEnd() {
        return SquareBraceEnd;
    }

    public final byte CurlyBraceStart() {
        return CurlyBraceStart;
    }

    public final byte CurlyBraceEnd() {
        return CurlyBraceEnd;
    }

    public final byte DoubleQuote() {
        return DoubleQuote;
    }

    public final byte Backslash() {
        return Backslash;
    }

    public final byte Comma() {
        return Comma;
    }

    public final int LineBreak() {
        return 10;
    }

    public final int LineBreak2() {
        return 13;
    }

    public final int Tab() {
        return 9;
    }

    public final int Space() {
        return 32;
    }

    public boolean isWhitespace(byte b) {
        byte by = b;
        switch (by) {
            case 32: {
                return true;
            }
            case 10: {
                return true;
            }
            case 13: {
                return true;
            }
            case 9: {
                return true;
            }
        }
        return false;
    }

    private JsonObjectParser$() {
    }
}

