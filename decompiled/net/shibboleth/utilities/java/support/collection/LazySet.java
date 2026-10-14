/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class LazySet<ElementType>
implements Set<ElementType>,
Serializable {
    private static final long serialVersionUID = -1596445680460115174L;
    private Set<ElementType> delegate = Collections.emptySet();

    @Override
    public boolean add(ElementType element) {
        if (this.delegate.isEmpty()) {
            this.delegate = Collections.singleton(element);
            return true;
        }
        this.delegate = this.createImplementation();
        return this.delegate.add(element);
    }

    @Override
    public boolean addAll(Collection<? extends ElementType> collection) {
        this.delegate = this.createImplementation();
        return this.delegate.addAll(collection);
    }

    @Override
    public void clear() {
        this.delegate = Collections.emptySet();
    }

    @Override
    public boolean contains(Object element) {
        return this.delegate.contains(element);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.delegate.containsAll(collection);
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public Iterator<ElementType> iterator() {
        this.delegate = this.createImplementation();
        return this.delegate.iterator();
    }

    @Override
    public boolean remove(Object element) {
        this.delegate = this.createImplementation();
        return this.delegate.remove(element);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        this.delegate = this.createImplementation();
        return this.delegate.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        this.delegate = this.createImplementation();
        return this.delegate.retainAll(collection);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public Object[] toArray() {
        return this.delegate.toArray();
    }

    @Override
    public <T> T[] toArray(T[] type) {
        return this.delegate.toArray(type);
    }

    private Set<ElementType> createImplementation() {
        if (this.delegate instanceof HashSet) {
            return this.delegate;
        }
        return new HashSet<ElementType>(this.delegate);
    }

    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return this.delegate.equals(((LazySet)obj).delegate);
    }
}

