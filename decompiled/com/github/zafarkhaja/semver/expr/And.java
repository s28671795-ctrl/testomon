/*
 * Decompiled with CFR 0.152.
 */
package com.github.zafarkhaja.semver.expr;

import com.github.zafarkhaja.semver.Version;
import com.github.zafarkhaja.semver.expr.Expression;

class And
implements Expression {
    private final Expression left;
    private final Expression right;

    And(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(Version version) {
        return this.left.interpret(version) && this.right.interpret(version);
    }
}

