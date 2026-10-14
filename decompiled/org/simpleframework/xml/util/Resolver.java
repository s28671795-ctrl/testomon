/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.util;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.util.LimitedCache;
import org.simpleframework.xml.util.Match;

public class Resolver<M extends Match>
extends AbstractSet<M> {
    protected final Cache cache;
    protected final Stack stack = new Stack();

    public Resolver() {
        this.cache = new Cache();
    }

    public M resolve(String text) {
        List<M> list = (List<M>)this.cache.get(text);
        if (list == null) {
            list = this.resolveAll(text);
        }
        if (list.isEmpty()) {
            return null;
        }
        return (M)((Match)list.get(0));
    }

    public List<M> resolveAll(String text) {
        List list = (List)this.cache.get(text);
        if (list != null) {
            return list;
        }
        char[] array = text.toCharArray();
        if (array == null) {
            return null;
        }
        return this.resolveAll(text, array);
    }

    private List<M> resolveAll(String text, char[] array) {
        ArrayList<Match> list = new ArrayList<Match>();
        for (Match match : this.stack) {
            String wild = match.getPattern();
            if (!this.match(array, wild.toCharArray())) continue;
            this.cache.put(text, list);
            list.add(match);
        }
        return list;
    }

    @Override
    public boolean add(M match) {
        this.stack.push(match);
        return true;
    }

    @Override
    public Iterator<M> iterator() {
        return this.stack.sequence();
    }

    @Override
    public boolean remove(M match) {
        this.cache.clear();
        return this.stack.remove(match);
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public void clear() {
        this.cache.clear();
        this.stack.clear();
    }

    private boolean match(char[] text, char[] wild) {
        return this.match(text, 0, wild, 0);
    }

    private boolean match(char[] text, int off, char[] wild, int pos) {
        while (pos < wild.length && off < text.length) {
            if (wild[pos] == '*') {
                while (wild[pos] == '*') {
                    if (++pos < wild.length) continue;
                    return true;
                }
                if (wild[pos] == '?' && ++pos >= wild.length) {
                    return true;
                }
                while (off < text.length) {
                    if (text[off] == wild[pos] || wild[pos] == '?') {
                        if (wild[pos - 1] == '?') break;
                        if (this.match(text, off, wild, pos)) {
                            return true;
                        }
                    }
                    ++off;
                }
                if (text.length == off) {
                    return false;
                }
            }
            if (text[off++] == wild[pos++] || wild[pos - 1] == '?') continue;
            return false;
        }
        if (wild.length == pos) {
            return text.length == off;
        }
        while (wild[pos] == '*') {
            if (++pos < wild.length) continue;
            return true;
        }
        return false;
    }

    private class Stack
    extends LinkedList<M> {
        private Stack() {
        }

        @Override
        public void push(M match) {
            Resolver.this.cache.clear();
            this.addFirst(match);
        }

        public void purge(int index) {
            Resolver.this.cache.clear();
            this.remove(index);
        }

        public Iterator<M> sequence() {
            return new Sequence();
        }

        private class Sequence
        implements Iterator<M> {
            private int cursor;

            public Sequence() {
                this.cursor = Stack.this.size();
            }

            @Override
            public M next() {
                if (this.hasNext()) {
                    return (Match)Stack.this.get(--this.cursor);
                }
                return null;
            }

            @Override
            public boolean hasNext() {
                return this.cursor > 0;
            }

            @Override
            public void remove() {
                Stack.this.purge(this.cursor);
            }
        }
    }

    private class Cache
    extends LimitedCache<List<M>> {
        public Cache() {
            super(1024);
        }
    }
}

