/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Map;
import java.util.function.Function;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.impl.AbstractQueryPartMap;
import org.jooq.impl.QueryPartCollectionView;

final class QueryPartMapView<K extends QueryPart, V extends QueryPart>
extends AbstractQueryPartMap<K, V> {
    QueryPartMapView(Map<K, V> map) {
        super(map);
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(QueryPartCollectionView.wrap(this.$tuples()));
    }

    @Override
    final Function<? super Map<K, V>, ? extends AbstractQueryPartMap<K, V>> $construct() {
        return QueryPartMapView::new;
    }
}

