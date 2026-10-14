/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.impl.ObjIntFunction;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
class QueryPartListView<T extends QueryPart>
extends QueryPartCollectionView<T>
implements QOM.UnmodifiableList<T> {
    @SafeVarargs
    static final <T extends QueryPart> QueryPartListView<T> wrap(T ... wrappedList) {
        return new QueryPartListView(wrappedList);
    }

    static final <T extends QueryPart> QueryPartListView<T> wrap(List<T> wrappedList) {
        return new QueryPartListView<T>(wrappedList);
    }

    @SafeVarargs
    QueryPartListView(T ... wrappedList) {
        this(Arrays.asList(wrappedList));
    }

    QueryPartListView(List<T> wrappedList) {
        super(wrappedList);
    }

    @Override
    QueryPartListView<T> qualify(boolean newQualify) {
        return (QueryPartListView)super.qualify(newQualify);
    }

    @Override
    QueryPartListView<T> map(Function<? super T, ? extends T> newMapper) {
        return (QueryPartListView)super.map(newMapper);
    }

    @Override
    QueryPartListView<T> map(ObjIntFunction<? super T, ? extends T> newMapper) {
        return (QueryPartListView)super.map(newMapper);
    }

    @Override
    QueryPartListView<T> separator(String newSeparator) {
        return (QueryPartListView)super.separator(newSeparator);
    }

    @Override
    List<T> wrapped() {
        return (List)super.wrapped();
    }

    @Override
    protected void toSQLEmptyList(Context<?> context) {
    }

    @Override
    public final boolean addAll(int index, Collection<? extends T> c) {
        boolean modified = false;
        for (QueryPart e : c) {
            if (!this.canAdd(e) || !(modified |= true)) continue;
            this.wrapped().add(index++, e);
        }
        return modified;
    }

    @Override
    public final T get(int index) {
        return (T)((QueryPart)this.wrapped().get(index));
    }

    @Override
    public final T set(int index, T element) {
        if (this.canAdd(element)) {
            return (T)((QueryPart)this.wrapped().set(index, element));
        }
        return null;
    }

    @Override
    public final void add(int index, T element) {
        if (this.canAdd(element)) {
            this.wrapped().add(index, element);
        }
    }

    @Override
    public final T remove(int index) {
        return (T)((QueryPart)this.wrapped().remove(index));
    }

    @Override
    public final int indexOf(Object o) {
        return this.wrapped().indexOf(o);
    }

    @Override
    public final int lastIndexOf(Object o) {
        return this.wrapped().lastIndexOf(o);
    }

    @Override
    public final ListIterator<T> listIterator() {
        return this.wrapped().listIterator();
    }

    @Override
    public final ListIterator<T> listIterator(int index) {
        return this.wrapped().listIterator(index);
    }

    @Override
    public final List<T> subList(int fromIndex, int toIndex) {
        return this.wrapped().subList(fromIndex, toIndex);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof List) {
            return this.wrapped.equals(that);
        }
        return false;
    }
}

