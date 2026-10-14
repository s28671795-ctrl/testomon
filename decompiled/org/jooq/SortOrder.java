/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Keyword;
import org.jooq.impl.DSL;

public enum SortOrder {
    ASC("asc"),
    DESC("desc"),
    DEFAULT("");

    private final String sql;
    private final Keyword keyword;

    private SortOrder(String sql) {
        this.sql = sql;
        this.keyword = DSL.keyword(sql);
    }

    @NotNull
    public final String toSQL() {
        return this.sql;
    }

    @NotNull
    public final Keyword toKeyword() {
        return this.keyword;
    }
}

