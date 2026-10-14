/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.common;

import java.util.HashMap;
import java.util.Iterator;
import net.engio.mbassy.common.AbstractConcurrentSet;
import net.engio.mbassy.common.ISetEntry;

public class StrongConcurrentSet<T>
extends AbstractConcurrentSet<T> {
    public StrongConcurrentSet() {
        super(new HashMap());
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>(){
            private ISetEntry<T> current;
            {
                this.current = StrongConcurrentSet.this.head;
            }

            @Override
            public boolean hasNext() {
                return this.current != null;
            }

            @Override
            public T next() {
                if (this.current == null) {
                    return null;
                }
                Object value = this.current.getValue();
                this.current = this.current.next();
                return value;
            }

            @Override
            public void remove() {
                if (this.current == null) {
                    return;
                }
                ISetEntry newCurrent = this.current.next();
                StrongConcurrentSet.this.remove(this.current.getValue());
                this.current = newCurrent;
            }
        };
    }

    @Override
    protected AbstractConcurrentSet.Entry<T> createEntry(T value, AbstractConcurrentSet.Entry<T> next) {
        return next != null ? new StrongEntry(value, next) : new StrongEntry((Object)value);
    }

    public static class StrongEntry<T>
    extends AbstractConcurrentSet.Entry<T> {
        private T value;

        private StrongEntry(T value, AbstractConcurrentSet.Entry<T> next) {
            super(next);
            this.value = value;
        }

        private StrongEntry(T value) {
            this.value = value;
        }

        @Override
        public T getValue() {
            return this.value;
        }
    }
}

