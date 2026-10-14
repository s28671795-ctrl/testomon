/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Keyword;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;

enum CombineOperator {
    UNION("union", true),
    UNION_ALL("union all", false),
    EXCEPT("except", true),
    EXCEPT_ALL("except all", false),
    INTERSECT("intersect", true),
    INTERSECT_ALL("intersect all", false);

    private final String sql;
    private final Keyword keywordOptionalDistinct;
    private final Keyword keyword;

    private CombineOperator(String sql, boolean distinct2) {
        this.sql = sql;
        this.keyword = DSL.keyword(sql);
        this.keywordOptionalDistinct = distinct2 ? DSL.keyword(sql + " distinct") : this.keyword;
    }

    public final String toSQL(SQLDialect dialect) {
        if (this == EXCEPT && dialect.family() == SQLDialect.ORACLE) {
            return "minus";
        }
        return this.sql;
    }

    public final Keyword toKeyword(SQLDialect dialect) {
        if (this == EXCEPT && dialect.family() == SQLDialect.ORACLE) {
            return Keywords.K_MINUS;
        }
        if (dialect.family() == SQLDialect.BIGQUERY) {
            return this.keywordOptionalDistinct;
        }
        return this.keyword;
    }
}

