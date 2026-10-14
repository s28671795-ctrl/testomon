/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.collection;

import java.util.AbstractSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class ClassIndexedSet<T>
extends AbstractSet<T>
implements Set<T> {
    private final HashSet<T> set = new HashSet();
    private final HashMap<Class<? extends T>, T> index = new HashMap();

    @Override
    public boolean add(@Nonnull T o) {
        return this.add(o, false);
    }

    public boolean add(@Nonnull T o, boolean replace) {
        Constraint.isNotNull(o, "Null elements are not allowed");
        boolean replacing = false;
        Class<T> indexClass = this.getIndexClass(o);
        T existing = this.get(indexClass);
        if (existing != null) {
            replacing = true;
            if (replace) {
                this.remove(existing);
            } else {
                throw new IllegalArgumentException("Set already contains a member of index class " + indexClass.getName());
            }
        }
        this.index.put(indexClass, o);
        this.set.add(o);
        return replacing;
    }

    @Override
    public void clear() {
        this.set.clear();
        this.index.clear();
    }

    @Override
    public boolean remove(@Nullable Object o) {
        if (o != null && this.set.contains(o)) {
            this.removeFromIndex(o);
            this.set.remove(o);
            return true;
        }
        return false;
    }

    @Override
    @Nonnull
    public Iterator<T> iterator() {
        return new ClassIndexedSetIterator(this, this.set.iterator());
    }

    @Override
    public int size() {
        return this.set.size();
    }

    public boolean contains(@Nullable Class<? extends T> clazz) {
        return this.get(clazz) != null;
    }

    @Nullable
    public <X extends T> X get(@Nullable Class<X> clazz) {
        return (X)this.index.get(clazz);
    }

    @Nonnull
    protected <X extends T> Class<X> getIndexClass(@Nonnull X o) {
        Constraint.isNotNull(o, "Object can not be null");
        return o.getClass();
    }

    private void removeFromIndex(T o) {
        this.index.remove(this.getIndexClass(o));
    }

    @Override
    public String toString() {
        return this.set.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return this.set.equals(((ClassIndexedSet)obj).set);
    }

    @Override
    public int hashCode() {
        return this.set.hashCode();
    }

    protected class ClassIndexedSetIterator
    implements Iterator<T> {
        private final ClassIndexedSet<T> set;
        private final Iterator<T> iterator;
        private boolean nextCalled;
        private boolean removeStateValid;
        private T current;

        protected ClassIndexedSetIterator(ClassIndexedSet<T> parentSet, Iterator<T> parentIterator) {
            this.set = parentSet;
            this.iterator = parentIterator;
            this.current = null;
            this.nextCalled = false;
            this.removeStateValid = false;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public T next() {
            this.current = this.iterator.next();
            this.nextCalled = true;
            this.removeStateValid = true;
            return this.current;
        }

        @Override
        public void remove() {
            if (!this.nextCalled) {
                throw new IllegalStateException("remove() was called before calling next()");
            }
            if (!this.removeStateValid) {
                throw new IllegalStateException("remove() has already been called since the last call to next()");
            }
            this.iterator.remove();
            this.set.removeFromIndex(this.current);
            this.removeStateValid = false;
        }
    }
}

