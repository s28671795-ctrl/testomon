/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class LazyList<ElementType>
implements List<ElementType>,
Serializable {
    private static final long serialVersionUID = -7741904523916701817L;
    private List<ElementType> delegate = Collections.emptyList();

    @Override
    public boolean add(ElementType item) {
        if (this.delegate.isEmpty()) {
            this.delegate = Collections.singletonList(item);
            return true;
        }
        this.delegate = this.buildList();
        return this.delegate.add(item);
    }

    @Override
    public void add(int index, ElementType element) {
        this.delegate = this.buildList();
        this.delegate.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends ElementType> collection) {
        this.delegate = this.buildList();
        return this.delegate.addAll(collection);
    }

    @Override
    public boolean addAll(int index, Collection<? extends ElementType> collection) {
        this.delegate = this.buildList();
        return this.delegate.addAll(index, collection);
    }

    @Override
    public void clear() {
        this.delegate = Collections.emptyList();
    }

    @Override
    public boolean contains(Object element) {
        return this.delegate.contains(element);
    }

    @Override
    public boolean containsAll(Collection<?> collections) {
        return this.delegate.containsAll(collections);
    }

    @Override
    public ElementType get(int index) {
        return this.delegate.get(index);
    }

    @Override
    public int indexOf(Object element) {
        return this.delegate.indexOf(element);
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public Iterator<ElementType> iterator() {
        this.delegate = this.buildList();
        return this.delegate.iterator();
    }

    @Override
    public int lastIndexOf(Object element) {
        return this.delegate.lastIndexOf(element);
    }

    @Override
    public ListIterator<ElementType> listIterator() {
        this.delegate = this.buildList();
        return this.delegate.listIterator();
    }

    @Override
    public ListIterator<ElementType> listIterator(int index) {
        this.delegate = this.buildList();
        return this.delegate.listIterator(index);
    }

    @Override
    public boolean remove(Object element) {
        this.delegate = this.buildList();
        return this.delegate.remove(element);
    }

    @Override
    public ElementType remove(int index) {
        this.delegate = this.buildList();
        return this.delegate.remove(index);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        this.delegate = this.buildList();
        return this.delegate.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        this.delegate = this.buildList();
        return this.delegate.retainAll(collection);
    }

    @Override
    public ElementType set(int index, ElementType element) {
        this.delegate = this.buildList();
        return this.delegate.set(index, element);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public List<ElementType> subList(int fromIndex, int toIndex) {
        return this.delegate.subList(fromIndex, toIndex);
    }

    @Override
    public Object[] toArray() {
        return this.delegate.toArray();
    }

    @Override
    public <T> T[] toArray(T[] type) {
        return this.delegate.toArray(type);
    }

    protected List<ElementType> buildList() {
        if (this.delegate instanceof ArrayList) {
            return this.delegate;
        }
        return new ArrayList<ElementType>(this.delegate);
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
        return this.delegate.equals(((LazyList)obj).delegate);
    }
}

