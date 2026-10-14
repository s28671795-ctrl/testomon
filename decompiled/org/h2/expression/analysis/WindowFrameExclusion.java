/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.analysis;

public enum WindowFrameExclusion {
    EXCLUDE_CURRENT_ROW("EXCLUDE CURRENT ROW"),
    EXCLUDE_GROUP("EXCLUDE GROUP"),
    EXCLUDE_TIES("EXCLUDE TIES"),
    EXCLUDE_NO_OTHERS("EXCLUDE NO OTHERS");

    private final String sql;

    private WindowFrameExclusion(String string2) {
        this.sql = string2;
    }

    public boolean isGroupOrNoOthers() {
        return this == EXCLUDE_GROUP || this == EXCLUDE_NO_OTHERS;
    }

    public String getSQL() {
        return this.sql;
    }
}

