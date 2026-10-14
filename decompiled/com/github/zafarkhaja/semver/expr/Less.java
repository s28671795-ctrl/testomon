/*
 * Decompiled with CFR 0.152.
 */
package com.github.zafarkhaja.semver.expr;

import com.github.zafarkhaja.semver.Version;
import com.github.zafarkhaja.semver.expr.Expression;

class Less
implements Expression {
    private final Version parsedVersion;

    Less(Version parsedVersion) {
        this.parsedVersion = parsedVersion;
    }

    @Override
    public boolean interpret(Version version) {
        return version.lessThan(this.parsedVersion);
    }
}

