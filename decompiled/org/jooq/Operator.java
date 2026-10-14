/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Condition;
import org.jooq.Keyword;
import org.jooq.impl.DSL;

public enum Operator {
    AND("and"),
    OR("or"),
    XOR("xor");

    private final String sql;
    private final Keyword keyword;

    private Operator(String sql) {
        this.sql = sql;
        this.keyword = DSL.keyword(sql);
    }

    public final String toSQL() {
        return this.sql;
    }

    public final Keyword toKeyword() {
        return this.keyword;
    }

    public final Condition identity() {
        return this == AND ? DSL.trueCondition() : DSL.falseCondition();
    }
}

