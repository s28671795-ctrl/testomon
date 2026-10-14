/*
 * Decompiled with CFR 0.152.
 */
package com.github.zafarkhaja.semver.expr;

import com.github.zafarkhaja.semver.ParseException;
import com.github.zafarkhaja.semver.expr.Lexer;
import com.github.zafarkhaja.semver.util.UnexpectedElementException;
import java.util.Arrays;

public class UnexpectedTokenException
extends ParseException {
    private final Lexer.Token unexpected;
    private final Lexer.Token.Type[] expected;

    UnexpectedTokenException(UnexpectedElementException cause) {
        this.unexpected = (Lexer.Token)cause.getUnexpectedElement();
        this.expected = (Lexer.Token.Type[])cause.getExpectedElementTypes();
    }

    UnexpectedTokenException(Lexer.Token token, Lexer.Token.Type ... expected) {
        this.unexpected = token;
        this.expected = expected;
    }

    Lexer.Token getUnexpectedToken() {
        return this.unexpected;
    }

    Lexer.Token.Type[] getExpectedTokenTypes() {
        return this.expected;
    }

    @Override
    public String toString() {
        String message = String.format("Unexpected token '%s'", this.unexpected);
        if (this.expected.length > 0) {
            message = message + String.format(", expecting '%s'", Arrays.toString(this.expected));
        }
        return message;
    }
}

