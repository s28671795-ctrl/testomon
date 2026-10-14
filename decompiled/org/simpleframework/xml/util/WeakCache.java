/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;
import org.simpleframework.xml.util.Cache;

public class WeakCache<T>
implements Cache<T> {
    private SegmentList list;

    public WeakCache() {
        this(10);
    }

    public WeakCache(int size) {
        this.list = new SegmentList(size);
    }

    @Override
    public boolean isEmpty() {
        for (Segment segment : this.list) {
            if (segment.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public void cache(Object key, T value) {
        this.map(key).cache(key, value);
    }

    @Override
    public T take(Object key) {
        return this.map(key).take(key);
    }

    @Override
    public T fetch(Object key) {
        return this.map(key).fetch(key);
    }

    @Override
    public boolean contains(Object key) {
        return this.map(key).contains(key);
    }

    private Segment map(Object key) {
        return this.list.get(key);
    }

    private class Segment
    extends WeakHashMap<Object, T> {
        private Segment() {
        }

        public synchronized void cache(Object key, T value) {
            this.put(key, value);
        }

        public synchronized T fetch(Object key) {
            return this.get(key);
        }

        public synchronized T take(Object key) {
            return this.remove(key);
        }

        public synchronized boolean contains(Object key) {
            return this.containsKey(key);
        }
    }

    private class SegmentList
    implements Iterable<Segment> {
        private List<Segment> list = new ArrayList<Segment>();
        private int size;

        public SegmentList(int size) {
            this.size = size;
            this.create(size);
        }

        @Override
        public Iterator<Segment> iterator() {
            return this.list.iterator();
        }

        public Segment get(Object key) {
            int segment = this.segment(key);
            if (segment < this.size) {
                return this.list.get(segment);
            }
            return null;
        }

        private void create(int size) {
            int count = size;
            while (count-- > 0) {
                this.list.add(new Segment());
            }
        }

        private int segment(Object key) {
            return Math.abs(key.hashCode() % this.size);
        }
    }
}

