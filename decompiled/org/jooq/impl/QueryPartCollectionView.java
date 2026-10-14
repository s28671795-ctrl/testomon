/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.DSL;
import org.jooq.impl.ObjIntFunction;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.SeparatedQueryPart;
import org.jooq.impl.SimpleCheckQueryPart;
import org.jooq.impl.Tools;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
class QueryPartCollectionView<T extends QueryPart>
extends AbstractQueryPart
implements QOM.UnmodifiableCollection<T>,
SimpleCheckQueryPart,
SeparatedQueryPart {
    final Collection<T> wrapped;
    Boolean qualify;
    String separator;
    ObjIntFunction<? super T, ? extends T> mapper;

    static final <T extends QueryPart> QueryPartCollectionView<T> wrap(Collection<T> wrapped) {
        return new QueryPartCollectionView<T>(wrapped);
    }

    QueryPartCollectionView(Collection<T> wrapped) {
        Collection<T> collection = this.wrapped = wrapped != null ? wrapped : Collections.emptyList();
        if (wrapped instanceof QueryPartCollectionView) {
            QueryPartCollectionView v = (QueryPartCollectionView)wrapped;
            this.qualify = v.qualify;
            this.separator = v.separator;
            this.mapper = v.mapper;
        } else {
            this.separator = ",";
        }
    }

    QueryPartCollectionView<T> qualify(boolean newQualify) {
        this.qualify = newQualify;
        return this;
    }

    QueryPartCollectionView<T> map(Function<? super T, ? extends T> newMapper) {
        return this.map((? super T t2, int i) -> (QueryPart)newMapper.apply(t2));
    }

    QueryPartCollectionView<T> map(ObjIntFunction<? super T, ? extends T> newMapper) {
        this.mapper = this.mapper == null ? newMapper : this.mapper.andThen(newMapper);
        return this;
    }

    QueryPartCollectionView<T> separator(String newSeparator) {
        this.separator = newSeparator;
        return this;
    }

    Collection<T> wrapped() {
        return this.wrapped;
    }

    @Override
    public boolean isSimple(Context<?> ctx) {
        return Tools.allMatch(this, e -> Tools.isSimple(ctx, e));
    }

    @Override
    public boolean rendersSeparator() {
        if (this.isEmpty()) {
            return false;
        }
        return Tools.isRendersSeparator((QueryPart)Tools.last(this.wrapped));
    }

    @Override
    public boolean rendersContent(Context<?> ctx) {
        return !this.isEmpty();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void accept(Context<?> ctx) {
        boolean indent;
        BitSet rendersContent = new BitSet(this.size());
        int i = 0;
        for (QueryPart e : this) {
            rendersContent.set(i++, ((QueryPartInternal)e).rendersContent(ctx));
        }
        int size = rendersContent.cardinality();
        boolean format = ctx.format() && (size >= 2 && !this.isSimple(ctx) || size > 4);
        boolean previousQualify = ctx.qualify();
        boolean previousAlreadyIndented = Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_LIST_ALREADY_INDENTED));
        boolean bl = indent = format && !previousAlreadyIndented;
        if (previousAlreadyIndented) {
            ctx.data(Tools.BooleanDataKey.DATA_LIST_ALREADY_INDENTED, false);
        }
        if (this.qualify != null) {
            ctx.qualify(this.qualify);
        }
        if (indent) {
            ctx.formatIndentStart();
        }
        if (ctx.separatorRequired()) {
            if (format) {
                ctx.formatSeparator();
            } else {
                ctx.sql(' ');
            }
        }
        if (size == 0) {
            this.toSQLEmptyList(ctx);
        } else {
            int j = 0;
            int k = 0;
            QueryPart prev = null;
            for (QueryPart part : this) {
                try {
                    int j0 = j;
                    if (!rendersContent.get(j++)) continue;
                    if (this.mapper != null) {
                        part = (QueryPart)this.mapper.apply(part, j0);
                    }
                    if (k++ > 0) {
                        if (!(prev instanceof SeparatedQueryPart) || !((SeparatedQueryPart)((Object)prev)).rendersSeparator()) {
                            ctx.sql(this.separator);
                        }
                        if (format) {
                            ctx.formatSeparator();
                        } else {
                            ctx.sql(' ');
                        }
                    } else if (indent) {
                        ctx.formatNewLine();
                    }
                    if (indent) {
                        QueryPart t2 = part;
                        ctx.data(Tools.BooleanDataKey.DATA_LIST_ALREADY_INDENTED, t2 instanceof QueryPartCollectionView && ((QueryPartCollectionView)t2).size() > 1, c -> this.acceptElement((Context<?>)c, (T)t2));
                        continue;
                    }
                    this.acceptElement(ctx, part);
                }
                finally {
                    prev = part;
                }
            }
        }
        if (indent) {
            ctx.formatIndentEnd().formatNewLine();
        }
        if (this.qualify != null) {
            ctx.qualify(previousQualify);
        }
        if (previousAlreadyIndented) {
            ctx.data(Tools.BooleanDataKey.DATA_LIST_ALREADY_INDENTED, previousAlreadyIndented);
        }
    }

    protected void acceptElement(Context<?> ctx, T part) {
        if (part instanceof Condition) {
            Condition c = (Condition)part;
            ctx.visit((QueryPart)DSL.field(c));
        } else {
            ctx.visit((QueryPart)part);
        }
    }

    protected void toSQLEmptyList(Context<?> context) {
    }

    @Override
    public final int size() {
        return this.wrapped.size();
    }

    @Override
    public final boolean isEmpty() {
        return this.wrapped.isEmpty();
    }

    @Override
    public final boolean contains(Object o) {
        return this.wrapped.contains(o);
    }

    @Override
    public final Iterator<T> iterator() {
        return this.wrapped.iterator();
    }

    @Override
    public final Object[] toArray() {
        return this.wrapped.toArray();
    }

    @Override
    public final <E> E[] toArray(E[] a) {
        return this.wrapped.toArray(a);
    }

    boolean canAdd(T e) {
        return e != null;
    }

    @Override
    public final boolean add(T e) {
        if (this.canAdd(e)) {
            return this.wrapped.add(e);
        }
        return false;
    }

    @Override
    public final boolean remove(Object o) {
        return this.wrapped.remove(o);
    }

    @Override
    public final boolean containsAll(Collection<?> c) {
        return this.wrapped.containsAll(c);
    }

    final boolean addAll0(Iterable<? extends T> c) {
        boolean modified = false;
        if (c != null) {
            for (QueryPart e : c) {
                if (!this.add((T)e)) continue;
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public final boolean addAll(Collection<? extends T> c) {
        return this.addAll0(c);
    }

    @Override
    public final boolean removeAll(Collection<?> c) {
        return this.wrapped.removeAll(c);
    }

    @Override
    public final boolean retainAll(Collection<?> c) {
        return this.wrapped.retainAll(c);
    }

    @Override
    public final void clear() {
        this.wrapped.clear();
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.wrapped.toArray(Tools.EMPTY_QUERYPART));
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.wrapped, l -> {
            QueryPartListView r = new QueryPartList(l);
            if (this.qualify != null) {
                r = r.qualify(this.qualify);
            }
            return ((QueryPartList)r.map((ObjIntFunction)this.mapper)).separator(this.separator);
        }, replacer);
    }

    @Override
    public int hashCode() {
        return this.wrapped.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof List && !(this instanceof List)) {
            return false;
        }
        if (that instanceof QueryPartCollectionView) {
            QueryPartCollectionView q = (QueryPartCollectionView)that;
            return this.wrapped.equals(q.wrapped);
        }
        return super.equals(that);
    }
}

