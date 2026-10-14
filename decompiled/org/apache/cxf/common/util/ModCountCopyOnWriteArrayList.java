/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public final class ModCountCopyOnWriteArrayList<T>
extends CopyOnWriteArrayList<T> {
    private static final long serialVersionUID = 1783937035760941219L;
    private AtomicInteger modCount = new AtomicInteger();

    public ModCountCopyOnWriteArrayList() {
    }

    public ModCountCopyOnWriteArrayList(Collection<? extends T> c) {
        super(c);
        if (c instanceof ModCountCopyOnWriteArrayList) {
            this.modCount.set(((ModCountCopyOnWriteArrayList)c).getModCount());
        }
    }

    public int getModCount() {
        return this.modCount.get();
    }

    public void setModCount(int i) {
        this.modCount.set(i);
    }

    @Override
    public void add(int index, T element) {
        super.add(index, element);
        this.modCount.incrementAndGet();
    }

    @Override
    public boolean add(T element) {
        if (super.add(element)) {
            this.modCount.incrementAndGet();
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (super.addAll(c)) {
            this.modCount.incrementAndGet();
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (super.addAll(index, c)) {
            this.modCount.incrementAndGet();
            return true;
        }
        return false;
    }

    @Override
    public int addAllAbsent(Collection<? extends T> c) {
        int i = super.addAllAbsent(c);
        if (i > 0) {
            this.modCount.incrementAndGet();
        }
        return i;
    }

    @Override
    public boolean addIfAbsent(T element) {
        if (super.addIfAbsent(element)) {
            this.modCount.incrementAndGet();
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        super.clear();
        this.modCount.incrementAndGet();
    }

    @Override
    public T remove(int index) {
        Object t2 = super.remove(index);
        if (t2 != null) {
            this.modCount.incrementAndGet();
        }
        return (T)t2;
    }

    @Override
    public boolean remove(Object o) {
        if (super.remove(o)) {
            this.modCount.incrementAndGet();
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (super.removeAll(c)) {
            this.modCount.incrementAndGet();
            return true;
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (super.retainAll(c)) {
            this.modCount.incrementAndGet();
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.modCount.get();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ModCountCopyOnWriteArrayList) {
            return super.equals(o) && this.modCount.get() == ((ModCountCopyOnWriteArrayList)o).getModCount();
        }
        return false;
    }
}

