/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Keyword;
import org.jooq.impl.DSL;

public enum Comparator {
    IN("in", false, true, false),
    NOT_IN("not in", false, true, false),
    EQUALS("=", true, true, false),
    NOT_EQUALS("<>", true, true, false),
    LESS("<", true, true, false),
    LESS_OR_EQUAL("<=", true, true, false),
    GREATER(">", true, true, false),
    GREATER_OR_EQUAL(">=", true, true, false),
    IS_DISTINCT_FROM("is distinct from", false, false, true),
    IS_NOT_DISTINCT_FROM("is not distinct from", false, false, true),
    LIKE("like", false, false, false),
    NOT_LIKE("not like", false, false, false),
    SIMILAR_TO("similar to", false, false, false),
    NOT_SIMILAR_TO("not similar to", false, false, false),
    LIKE_IGNORE_CASE("ilike", false, false, false),
    NOT_LIKE_IGNORE_CASE("not ilike", false, false, false);

    private final String sql;
    private final Keyword keyword;
    private final boolean supportsQuantifier;
    private final boolean supportsSubselect;
    private final boolean supportsNulls;

    private Comparator(String sql, boolean supportsQuantifier, boolean supportsSubselect, boolean supportsNulls) {
        this.sql = sql;
        this.keyword = DSL.keyword(sql);
        this.supportsQuantifier = supportsQuantifier;
        this.supportsSubselect = supportsSubselect;
        this.supportsNulls = supportsNulls;
    }

    public String toSQL() {
        return this.sql;
    }

    public Keyword toKeyword() {
        return this.keyword;
    }

    public Comparator inverse() {
        switch (this) {
            case EQUALS: {
                return NOT_EQUALS;
            }
            case GREATER: {
                return LESS_OR_EQUAL;
            }
            case GREATER_OR_EQUAL: {
                return LESS;
            }
            case IN: {
                return NOT_IN;
            }
            case IS_DISTINCT_FROM: {
                return IS_NOT_DISTINCT_FROM;
            }
            case IS_NOT_DISTINCT_FROM: {
                return IS_DISTINCT_FROM;
            }
            case LESS: {
                return GREATER_OR_EQUAL;
            }
            case LESS_OR_EQUAL: {
                return GREATER;
            }
            case LIKE: {
                return NOT_LIKE;
            }
            case LIKE_IGNORE_CASE: {
                return NOT_LIKE_IGNORE_CASE;
            }
            case NOT_EQUALS: {
                return EQUALS;
            }
            case NOT_IN: {
                return IN;
            }
            case NOT_LIKE: {
                return LIKE;
            }
            case NOT_LIKE_IGNORE_CASE: {
                return LIKE_IGNORE_CASE;
            }
            case NOT_SIMILAR_TO: {
                return SIMILAR_TO;
            }
            case SIMILAR_TO: {
                return NOT_SIMILAR_TO;
            }
        }
        throw new IllegalStateException();
    }

    public Comparator mirror() {
        switch (this) {
            case EQUALS: {
                return EQUALS;
            }
            case GREATER: {
                return LESS;
            }
            case GREATER_OR_EQUAL: {
                return LESS_OR_EQUAL;
            }
            case IS_DISTINCT_FROM: {
                return IS_DISTINCT_FROM;
            }
            case IS_NOT_DISTINCT_FROM: {
                return IS_NOT_DISTINCT_FROM;
            }
            case LESS: {
                return GREATER;
            }
            case LESS_OR_EQUAL: {
                return GREATER_OR_EQUAL;
            }
            case NOT_EQUALS: {
                return NOT_EQUALS;
            }
        }
        return null;
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final boolean supportsNulls() {
        return this.supportsNulls;
    }

    @Deprecated(forRemoval=true, since="3.14")
    public boolean supportsQuantifier() {
        return this.supportsQuantifier;
    }

    @Deprecated(forRemoval=true, since="3.14")
    public boolean supportsSubselect() {
        return this.supportsSubselect;
    }
}

