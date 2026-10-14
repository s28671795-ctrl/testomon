/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import org.jooq.QueryPart;
import org.jooq.impl.Lazy;
import org.jooq.impl.ObjIntFunction;
import org.jooq.impl.QueryPartListView;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
class QueryPartList<T extends QueryPart>
extends QueryPartListView<T> {
    private static final Lazy<QueryPartList> EMPTY_LIST = Lazy.of(() -> new QueryPartList());

    QueryPartList() {
        this((Collection)null);
    }

    @SafeVarargs
    QueryPartList(T ... wrappedList) {
        this((Iterable<T>)Arrays.asList(wrappedList));
    }

    QueryPartList(Iterable<? extends T> wrappedList) {
        super(new ArrayList());
        this.addAll0(wrappedList);
    }

    @Override
    QueryPartList<T> qualify(boolean newQualify) {
        return (QueryPartList)super.qualify(newQualify);
    }

    @Override
    QueryPartList<T> map(Function<? super T, ? extends T> newMapper) {
        return (QueryPartList)super.map((Function)newMapper);
    }

    @Override
    QueryPartList<T> map(ObjIntFunction<? super T, ? extends T> newMapper) {
        return (QueryPartList)super.map((ObjIntFunction)newMapper);
    }

    @Override
    QueryPartList<T> separator(String newSeparator) {
        return (QueryPartList)super.separator(newSeparator);
    }

    static final <Q extends QueryPart> QueryPartList<Q> emptyList() {
        return EMPTY_LIST.get();
    }
}

