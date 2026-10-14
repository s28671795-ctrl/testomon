/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Keyword;
import org.jooq.impl.DSL;

public enum JoinType {
    JOIN("join", "inner join", "join", true),
    CROSS_JOIN("cross join", false),
    LEFT_OUTER_JOIN("left outer join", "left outer join", "left join", true),
    RIGHT_OUTER_JOIN("right outer join", "right outer join", "right join", true),
    FULL_OUTER_JOIN("full outer join", "full outer join", "full join", true),
    NATURAL_JOIN("natural join", "natural inner join", "natural join", false),
    NATURAL_LEFT_OUTER_JOIN("natural left outer join", "natural left outer join", "natural left join", false),
    NATURAL_RIGHT_OUTER_JOIN("natural right outer join", "natural right outer join", "natural right join", false),
    NATURAL_FULL_OUTER_JOIN("natural full outer join", "natural full outer join", "natural full join", false),
    CROSS_APPLY("cross apply", false),
    OUTER_APPLY("outer apply", false),
    STRAIGHT_JOIN("straight_join", true),
    LEFT_SEMI_JOIN("left semi join", true),
    LEFT_ANTI_JOIN("left anti join", true);

    private final String defaultSql;
    private final Keyword defaultKeyword;
    private final Keyword includingOptionalKeywords;
    private final Keyword excludingOptionalKeywords;
    private final boolean qualified;

    private JoinType(String sql, boolean qualified) {
        this(sql, sql, sql, qualified);
    }

    private JoinType(String defaultSql, String includingOptionalKeywords, String excludingOptionalKeywords, boolean qualified) {
        this.defaultSql = defaultSql;
        this.includingOptionalKeywords = DSL.keyword(includingOptionalKeywords);
        this.excludingOptionalKeywords = DSL.keyword(excludingOptionalKeywords);
        this.defaultKeyword = DSL.keyword(defaultSql);
        this.qualified = qualified;
    }

    public final String toSQL() {
        return this.defaultSql;
    }

    public final Keyword toKeyword() {
        return this.defaultKeyword;
    }

    public final Keyword toKeyword(boolean includeOptionalKeywords) {
        return includeOptionalKeywords ? this.includingOptionalKeywords : this.excludingOptionalKeywords;
    }

    public final boolean qualified() {
        return this.qualified;
    }
}

