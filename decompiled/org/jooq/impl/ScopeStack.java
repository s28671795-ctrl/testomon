/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jooq.impl.ObjIntFunction;

final class ScopeStack<K, V>
implements Iterable<V> {
    private int scopeLevel = -1;
    private Map<K, List<V>> stack;
    private final ObjIntFunction<K, V> constructor;

    ScopeStack() {
        this((ObjIntFunction)null);
    }

    ScopeStack(V defaultValue) {
        this((part, scopeLevel) -> defaultValue);
    }

    ScopeStack(ObjIntFunction<K, V> constructor) {
        this.constructor = constructor;
    }

    private final Map<K, List<V>> stack() {
        if (this.stack == null) {
            this.stack = new LinkedHashMap<K, List<V>>();
        }
        return this.stack;
    }

    private final void trim() {
        int l = this.scopeLevel + 1;
        if (l >= 0) {
            Iterator<Map.Entry<K, List<V>>> it = this.stack().entrySet().iterator();
            while (it.hasNext()) {
                int size;
                Map.Entry<K, List<V>> entry = it.next();
                List<V> list = entry.getValue();
                while ((size = list.size()) > l || size > 0 && list.get(size - 1) == null) {
                    list.remove(size - 1);
                }
                if (!list.isEmpty()) continue;
                it.remove();
            }
        }
    }

    final boolean isEmpty() {
        return !this.iterator().hasNext();
    }

    final Iterable<Value<V>> valueIterable() {
        return () -> new ScopeStackIterator<Value>(Value::lastOf, e -> true);
    }

    @Override
    public final Iterator<V> iterator() {
        return this.iterable(e -> true).iterator();
    }

    final Iterable<V> iterableAtScopeLevel() {
        return () -> new ScopeStackIterator<Object>(list -> list.size() == this.scopeLevel + 1 ? list.get(this.scopeLevel) : null, e -> true);
    }

    final Iterable<V> iterable(Predicate<? super V> filter2) {
        return () -> new ScopeStackIterator<Object>(list -> list.get(list.size() - 1), filter2);
    }

    final void setAll(V value) {
        for (K key : this.stack().keySet()) {
            this.set(key, value);
        }
    }

    final void set(K key, V value) {
        this.set0(this.list(key), value);
    }

    private final V get0(List<V> list) {
        if (list == null) {
            return null;
        }
        int i = list.size();
        if (i == 0) {
            return null;
        }
        return list.get(i - 1);
    }

    final V get(K key) {
        return this.get0(this.listOrNull(key));
    }

    final <T extends Throwable> V getOrThrow(K key, Supplier<T> exception) throws T {
        V result = this.get(key);
        if (result == null) {
            throw (Throwable)exception.get();
        }
        return result;
    }

    final V getOrCreate(K key) {
        List<V> list = this.list(key);
        V result = this.get0(list);
        return result != null ? result : this.create0(key, list);
    }

    final V create(K key) {
        return this.create0(key, this.list(key));
    }

    private final V create0(K key, List<V> list) {
        V result = this.constructor.apply(key, this.scopeLevel);
        this.set0(list, result);
        return result;
    }

    private void set0(List<V> list, V value) {
        int l = this.scopeLevel + 1;
        int size = list.size();
        if (size < l) {
            list.addAll(Collections.nCopies(l - size, null));
        }
        list.set(this.scopeLevel, value);
    }

    private List<V> listOrNull(K key) {
        return this.stack().get(key);
    }

    private List<V> list(K key) {
        return this.stack().computeIfAbsent(key, k -> new ArrayList());
    }

    final boolean inScope() {
        return this.scopeLevel > -1;
    }

    final int scopeLevel() {
        return this.scopeLevel;
    }

    final void scopeStart() {
        ++this.scopeLevel;
    }

    final void scopeEnd() {
        --this.scopeLevel;
        this.trim();
    }

    public String toString() {
        return this.stack().toString();
    }

    private final class ScopeStackIterator<U>
    implements Iterator<U> {
        final Iterator<List<V>> it;
        final Function<List<V>, U> valueExtractor;
        final Predicate<? super U> filter;
        U next;

        ScopeStackIterator(Function<List<V>, U> valueExtractor, Predicate<? super U> filter2) {
            this.it = ScopeStack.this.stack().values().iterator();
            this.valueExtractor = valueExtractor;
            this.filter = filter2;
        }

        @Override
        public boolean hasNext() {
            return this.move() != null;
        }

        @Override
        public U next() {
            if (this.next == null) {
                return this.move();
            }
            U result = this.next;
            this.next = null;
            return result;
        }

        private U move() {
            List list;
            while (this.it.hasNext() && ((list = this.it.next()).isEmpty() || (this.next = this.valueExtractor.apply(list)) == null || !this.filter.test(this.next))) {
                this.next = null;
            }
            return this.next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    record Value<V>(int scopeLevel, V value) {
        static <V> Value<V> of(int scopeLevel, V value) {
            return value == null ? null : new Value<V>(scopeLevel, value);
        }

        static <V> Value<V> lastOf(List<V> list) {
            int size = list.size();
            V value = list.get(size - 1);
            return Value.of(size - 1, value);
        }
    }
}

