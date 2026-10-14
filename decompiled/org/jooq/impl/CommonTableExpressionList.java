/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.Consumer;
import org.jooq.CommonTableExpression;
import org.jooq.Context;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.ScopeMarker;

final class CommonTableExpressionList
extends QueryPartList<CommonTableExpression<?>> {
    CommonTableExpressionList() {
    }

    @Override
    public void accept(Context<?> ctx) {
        CommonTableExpressionList.markTopLevelCteAndAccept(ctx, c -> super.accept((Context<?>)c));
    }

    static void markTopLevelCteAndAccept(Context<?> ctx, Consumer<? super Context<?>> consumer) {
        if (ctx.subqueryLevel() == 0) {
            ctx.scopeMarkStart(ScopeMarker.TOP_LEVEL_CTE.beforeFirst).scopeMarkEnd(ScopeMarker.TOP_LEVEL_CTE.beforeFirst);
        }
        consumer.accept(ctx);
        if (ctx.subqueryLevel() == 0) {
            ctx.scopeMarkStart(ScopeMarker.TOP_LEVEL_CTE.afterLast).scopeMarkEnd(ScopeMarker.TOP_LEVEL_CTE.afterLast);
        }
    }

    @Override
    public final boolean declaresCTE() {
        return true;
    }
}

