/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.common;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.engio.mbassy.common.AbstractConcurrentSet;
import net.engio.mbassy.common.ISetEntry;

public class WeakConcurrentSet<T>
extends AbstractConcurrentSet<T> {
    public WeakConcurrentSet() {
        super(new WeakHashMap());
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>(){
            private ISetEntry<T> current;
            {
                this.current = WeakConcurrentSet.this.head;
            }

            private void removeOrphans() {
                ReentrantReadWriteLock.WriteLock writelock = WeakConcurrentSet.this.lock.writeLock();
                try {
                    writelock.lock();
                    do {
                        ISetEntry orphaned = this.current;
                        this.current = this.current.next();
                        if (orphaned == WeakConcurrentSet.this.head) {
                            WeakConcurrentSet.this.head = WeakConcurrentSet.this.head.next();
                        }
                        orphaned.remove();
                    } while (this.current != null && this.current.getValue() == null);
                }
                finally {
                    writelock.unlock();
                }
            }

            @Override
            public boolean hasNext() {
                if (this.current == null) {
                    return false;
                }
                if (this.current.getValue() == null) {
                    this.removeOrphans();
                    return this.current != null;
                }
                return true;
            }

            @Override
            public T next() {
                if (this.current == null) {
                    return null;
                }
                Object value = this.current.getValue();
                if (value == null) {
                    this.removeOrphans();
                    return this.next();
                }
                this.current = this.current.next();
                return value;
            }

            @Override
            public void remove() {
                if (this.current == null) {
                    return;
                }
                ISetEntry newCurrent = this.current.next();
                WeakConcurrentSet.this.remove(this.current.getValue());
                this.current = newCurrent;
            }
        };
    }

    @Override
    protected AbstractConcurrentSet.Entry<T> createEntry(T value, AbstractConcurrentSet.Entry<T> next) {
        return next != null ? new WeakEntry(value, next) : new WeakEntry((Object)value);
    }

    public static class WeakEntry<T>
    extends AbstractConcurrentSet.Entry<T> {
        private WeakReference<T> value;

        private WeakEntry(T value, AbstractConcurrentSet.Entry<T> next) {
            super(next);
            this.value = new WeakReference<T>(value);
        }

        private WeakEntry(T value) {
            this.value = new WeakReference<T>(value);
        }

        @Override
        public T getValue() {
            return this.value.get();
        }
    }
}

