/*
 * Decompiled with CFR 0.152.
 */
package com.github.zafarkhaja.semver.expr;

import com.github.zafarkhaja.semver.ParseException;

public class LexerException
extends ParseException {
    private final String expr;

    LexerException(String expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "Illegal character near '" + this.expr + "'";
    }
}

