/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicReference;

public final class SortedArraySet<T>
implements SortedSet<T> {
    final AtomicReference<T[]> data = new AtomicReference();

    @Override
    public void clear() {
        this.data.set(null);
    }

    @Override
    public boolean isEmpty() {
        T[] tmp = this.data.get();
        return tmp == null || tmp.length == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new SASIterator<T>(this.data.get());
    }

    @Override
    public int size() {
        T[] tmp = this.data.get();
        return tmp == null ? 0 : tmp.length;
    }

    private T[] newArray(int size) {
        return new Object[size];
    }

    @Override
    public boolean add(T o) {
        if (!this.contains(o)) {
            Object[] tmp2;
            T[] tmp = this.data.get();
            if (tmp == null) {
                tmp2 = this.newArray(1);
                tmp2[0] = o;
            } else {
                tmp2 = this.newArray(tmp.length + 1);
                System.arraycopy(tmp, 0, tmp2, 0, tmp.length);
                tmp2[tmp2.length - 1] = o;
                Arrays.sort(tmp2);
            }
            if (!this.data.compareAndSet(tmp, tmp2)) {
                return this.add(o);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean val = false;
        for (T t2 : c) {
            val |= this.add(t2);
        }
        return val;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean val = false;
        for (Object t2 : c) {
            val |= this.contains(t2);
        }
        return val;
    }

    @Override
    public boolean contains(Object o) {
        Object[] tmp = this.data.get();
        if (tmp == null) {
            return false;
        }
        return Arrays.binarySearch(tmp, o) >= 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean val = false;
        for (Object t2 : c) {
            val |= this.remove(t2);
        }
        return val;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean val = false;
        for (T t2 : this) {
            if (c.contains(t2)) continue;
            val |= this.remove(t2);
        }
        return val;
    }

    @Override
    public boolean remove(Object o) {
        Object[] tmp = this.data.get();
        if (tmp == null) {
            return false;
        }
        int idx = Arrays.binarySearch(tmp, o);
        if (idx != -1) {
            if (tmp.length == 1 && !this.data.compareAndSet(tmp, null)) {
                return this.remove(o);
            }
            T[] tmp2 = this.newArray(tmp.length - 1);
            System.arraycopy(tmp, 0, tmp2, 0, idx);
            System.arraycopy(tmp, idx + 1, tmp2, idx, tmp.length - 1 - idx);
            if (!this.data.compareAndSet(tmp, tmp2)) {
                return this.remove(o);
            }
            return true;
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        T[] tmp = this.data.get();
        if (tmp == null) {
            return new Object[0];
        }
        Object[] tmp2 = this.newArray(tmp.length);
        System.arraycopy(tmp, 0, tmp2, 0, tmp.length);
        return tmp2;
    }

    @Override
    public <X> X[] toArray(X[] a) {
        T[] tmp = this.data.get();
        if (tmp == null) {
            if (a.length != 0) {
                return (Object[])Array.newInstance(a.getClass().getComponentType(), 0);
            }
            return a;
        }
        if (a.length < tmp.length) {
            a = (Object[])Array.newInstance(a.getClass().getComponentType(), tmp.length);
        }
        System.arraycopy(tmp, 0, a, 0, tmp.length);
        if (a.length > tmp.length) {
            a[tmp.length] = null;
        }
        return a;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SortedArraySet)) {
            return false;
        }
        SortedArraySet as = (SortedArraySet)o;
        return Arrays.equals(this.data.get(), as.data.get());
    }

    public String toString() {
        return Arrays.toString(this.data.get());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.data.get());
    }

    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @Override
    public T first() {
        T[] tmp = this.data.get();
        if (tmp == null || tmp.length == 0) {
            return null;
        }
        return tmp[0];
    }

    @Override
    public T last() {
        T[] tmp = this.data.get();
        if (tmp == null || tmp.length == 0) {
            return null;
        }
        return tmp[tmp.length - 1];
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    private class SASIterator<X>
    implements Iterator<X> {
        final X[] data;
        int idx;

        SASIterator(X[] d) {
            this.data = d;
        }

        @Override
        public boolean hasNext() {
            return this.data != null && this.idx != this.data.length;
        }

        @Override
        public X next() {
            if (this.data == null || this.idx == this.data.length) {
                throw new NoSuchElementException();
            }
            return this.data[this.idx++];
        }

        @Override
        public void remove() {
            if (this.idx > 0) {
                SortedArraySet.this.remove(this.data[this.idx - 1]);
            }
        }
    }
}

