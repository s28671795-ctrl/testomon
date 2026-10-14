/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
abstract class AbstractQueryPartMap<K extends QueryPart, V extends QueryPart>
extends AbstractQueryPart
implements QOM.UnmodifiableMap<K, V> {
    private final Map<K, V> map;

    AbstractQueryPartMap() {
        this(new LinkedHashMap());
    }

    AbstractQueryPartMap(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public abstract void accept(Context<?> var1);

    @Override
    public final int size() {
        return this.map.size();
    }

    @Override
    public final boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public final boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public final boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public final V get(Object key) {
        return (V)((QueryPart)this.map.get(key));
    }

    @Override
    public V put(K key, V value) {
        return (V)((QueryPart)this.map.put(key, value));
    }

    @Override
    public final V remove(Object key) {
        return (V)((QueryPart)this.map.remove(key));
    }

    @Override
    public final void putAll(Map<? extends K, ? extends V> m4) {
        m4.forEach(this::put);
    }

    @Override
    public final void clear() {
        this.map.clear();
    }

    @Override
    public final Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public final Collection<V> values() {
        return this.map.values();
    }

    @Override
    public final Set<Map.Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public final QOM.UnmodifiableList<QOM.Tuple2<K, V>> $tuples() {
        return QOM.unmodifiable(Tools.map(this.entrySet(), e -> QOM.tuple((QueryPart)e.getKey(), (QueryPart)e.getValue())));
    }

    abstract Function<? super Map<K, V>, ? extends AbstractQueryPartMap<K, V>> $construct();

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$tuples());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$tuples(), t2 -> {
            LinkedHashMap m4 = new LinkedHashMap();
            t2.forEach((? super T x) -> m4.put(x.$1(), x.$2()));
            return this.$construct().apply(m4);
        }, replacer);
    }
}

