/*
 * Decompiled with CFR 0.152.
 */
package com.github.zafarkhaja.semver.expr;

import com.github.zafarkhaja.semver.Version;
import com.github.zafarkhaja.semver.expr.Expression;

class Not
implements Expression {
    private final Expression expr;

    Not(Expression expr) {
        this.expr = expr;
    }

    @Override
    public boolean interpret(Version version) {
        return !this.expr.interpret(version);
    }
}

