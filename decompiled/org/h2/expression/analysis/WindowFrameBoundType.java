/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.analysis;

public enum WindowFrameBoundType {
    UNBOUNDED_PRECEDING("UNBOUNDED PRECEDING"),
    PRECEDING("PRECEDING"),
    CURRENT_ROW("CURRENT ROW"),
    FOLLOWING("FOLLOWING"),
    UNBOUNDED_FOLLOWING("UNBOUNDED FOLLOWING");

    private final String sql;

    private WindowFrameBoundType(String string2) {
        this.sql = string2;
    }

    public String getSQL() {
        return this.sql;
    }
}

