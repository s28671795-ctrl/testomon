/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.common;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.engio.mbassy.common.ISetEntry;

public abstract class AbstractConcurrentSet<T>
implements Set<T> {
    private static final AtomicLong id = new AtomicLong();
    private final long ID = id.getAndIncrement();
    protected final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<T, ISetEntry<T>> entries;
    protected Entry<T> head;

    protected AbstractConcurrentSet(Map<T, ISetEntry<T>> entries) {
        this.entries = entries;
    }

    protected abstract Entry<T> createEntry(T var1, Entry<T> var2);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean add(T element) {
        boolean changed;
        if (element == null) {
            return false;
        }
        ReentrantReadWriteLock.WriteLock writeLock = this.lock.writeLock();
        try {
            writeLock.lock();
            changed = this.insert(element);
        }
        finally {
            writeLock.unlock();
        }
        return changed;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean contains(Object element) {
        ISetEntry<T> entry;
        ReentrantReadWriteLock.ReadLock readLock = this.lock.readLock();
        try {
            readLock.lock();
            entry = this.entries.get(element);
        }
        finally {
            readLock.unlock();
        }
        return entry != null && entry.getValue() != null;
    }

    private boolean insert(T element) {
        if (!this.entries.containsKey(element)) {
            this.head = this.createEntry(element, this.head);
            this.entries.put(element, this.head);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return this.entries.size();
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean addAll(Collection<? extends T> elements) {
        boolean changed = false;
        ReentrantReadWriteLock.WriteLock writeLock = this.lock.writeLock();
        try {
            writeLock.lock();
            for (T element : elements) {
                if (element == null) continue;
                changed |= this.insert(element);
            }
        }
        finally {
            writeLock.unlock();
        }
        return changed;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean remove(Object element) {
        if (!this.contains(element)) {
            return false;
        }
        ReentrantReadWriteLock.WriteLock writeLock = this.lock.writeLock();
        try {
            writeLock.lock();
            ISetEntry<T> listelement = this.entries.get(element);
            if (listelement == null) {
                boolean bl = false;
                return bl;
            }
            if (listelement != this.head) {
                listelement.remove();
            } else {
                this.head = this.head.next();
            }
            this.entries.remove(element);
        }
        finally {
            writeLock.unlock();
        }
        return true;
    }

    @Override
    public Object[] toArray() {
        return this.entries.entrySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.entries.entrySet().toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void clear() {
        ReentrantReadWriteLock.WriteLock writeLock = this.lock.writeLock();
        try {
            writeLock.lock();
            this.head = null;
            this.entries.clear();
        }
        finally {
            writeLock.unlock();
        }
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (int)(this.ID ^ this.ID >>> 32);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        AbstractConcurrentSet other = (AbstractConcurrentSet)obj;
        return this.ID == other.ID;
    }

    public static abstract class Entry<T>
    implements ISetEntry<T> {
        private Entry<T> next;
        private Entry<T> predecessor;

        protected Entry(Entry<T> next) {
            this.next = next;
            next.predecessor = this;
        }

        protected Entry() {
        }

        @Override
        public void remove() {
            if (this.predecessor != null) {
                this.predecessor.next = this.next;
                if (this.next != null) {
                    this.next.predecessor = this.predecessor;
                }
            } else if (this.next != null) {
                this.next.predecessor = null;
            }
        }

        @Override
        public Entry<T> next() {
            return this.next;
        }

        @Override
        public void clear() {
            this.next = null;
        }
    }
}

