/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jooq.Context;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.impl.CommonTableExpressionList;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.ScopeMarker;
import org.jooq.impl.Tools;

final class TopLevelCte
extends QueryPartList<QueryPart>
implements ScopeMarker.ScopeContent {
    boolean recursive;

    TopLevelCte() {
    }

    @Override
    public void accept(Context<?> ctx) {
        CommonTableExpressionList.markTopLevelCteAndAccept(ctx, c -> super.accept((Context<?>)c));
    }

    @Override
    public final boolean declaresCTE() {
        return true;
    }

    @Pro
    static void addIfAbsent(Context<?> ctx, Predicate<? super QueryPart> test, Supplier<? extends QueryPart> construct) {
        block2: {
            TopLevelCte cte = (TopLevelCte)ctx.data(Tools.SimpleDataKey.DATA_TOP_LEVEL_CTE);
            if (cte != null) {
                for (QueryPart p : cte) {
                    if (!test.test(p)) continue;
                    break block2;
                }
                cte.add(construct.get());
            }
        }
    }
}

