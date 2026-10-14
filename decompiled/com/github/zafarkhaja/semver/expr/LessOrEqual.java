/*
 * Decompiled with CFR 0.152.
 */
package com.github.zafarkhaja.semver.expr;

import com.github.zafarkhaja.semver.Version;
import com.github.zafarkhaja.semver.expr.Expression;

class LessOrEqual
implements Expression {
    private final Version parsedVersion;

    LessOrEqual(Version parsedVersion) {
        this.parsedVersion = parsedVersion;
    }

    @Override
    public boolean interpret(Version version) {
        return version.lessThanOrEqualTo(this.parsedVersion);
    }
}

