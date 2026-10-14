/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Name;
import org.jooq.impl.DSL;

enum ExpressionOperator {
    CONCAT("||", true, false),
    ADD("+", true, true),
    SUBTRACT("-"),
    MULTIPLY("*", true, true),
    DIVIDE("/");

    private final String sql;
    private final Name name;
    private final boolean associative;
    private final boolean commutative;

    private ExpressionOperator(String sql) {
        this(sql, false, false);
    }

    private ExpressionOperator(String sql, boolean associative, boolean commutative) {
        this.sql = sql;
        this.name = DSL.name(this.name().toLowerCase());
        this.associative = associative;
        this.commutative = commutative;
    }

    final String toSQL() {
        return this.sql;
    }

    final Name toName() {
        return this.name;
    }

    final boolean associative() {
        return this.associative;
    }

    final boolean commutative() {
        return this.commutative;
    }
}

