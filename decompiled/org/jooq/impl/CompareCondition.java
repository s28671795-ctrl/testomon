/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Comparator;
import org.jooq.Context;
import org.jooq.Pro;

final class CompareCondition {
    CompareCondition() {
    }

    @Pro
    static void acceptJava(Context<?> ctx, Comparator comparator) {
        switch (comparator) {
            case EQUALS: {
                ctx.sql("eq");
                break;
            }
            case GREATER: {
                ctx.sql("gt");
                break;
            }
            case GREATER_OR_EQUAL: {
                ctx.sql("ge");
                break;
            }
            case IN: {
                ctx.sql("in");
                break;
            }
            case IS_DISTINCT_FROM: {
                ctx.sql("isDistinctFrom");
                break;
            }
            case IS_NOT_DISTINCT_FROM: {
                ctx.sql("isNotDistinctFrom");
                break;
            }
            case LESS: {
                ctx.sql("lt");
                break;
            }
            case LESS_OR_EQUAL: {
                ctx.sql("le");
                break;
            }
            case LIKE: {
                ctx.sql("like");
                break;
            }
            case LIKE_IGNORE_CASE: {
                ctx.sql("likeIgnoreCase");
                break;
            }
            case NOT_EQUALS: {
                ctx.sql("ne");
                break;
            }
            case NOT_IN: {
                ctx.sql("notIn");
                break;
            }
            case NOT_LIKE: {
                ctx.sql("notLike");
                break;
            }
            case NOT_LIKE_IGNORE_CASE: {
                ctx.sql("notLikeIgnoreCase");
                break;
            }
            case NOT_SIMILAR_TO: {
                ctx.sql("notSimilarTo");
                break;
            }
            case SIMILAR_TO: {
                ctx.sql("similarTo");
            }
        }
    }
}

