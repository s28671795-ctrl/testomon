/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.util;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.collection.LazyList;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;

class ListView<ElementType extends XMLObject>
extends AbstractList<ElementType> {
    private final IndexedXMLObjectChildrenList<ElementType> backingList;
    @Nonnull
    private final QName index;
    @Nonnull
    private List<ElementType> indexList;

    public ListView(@Nonnull IndexedXMLObjectChildrenList<ElementType> newBackingList, @Nonnull QName newIndex) {
        this.backingList = newBackingList;
        this.index = newIndex;
        this.indexList = this.backingList.get(this.index);
    }

    @Override
    public boolean add(@Nullable ElementType o) {
        boolean result = this.backingList.add(o);
        this.indexList = this.backingList.get(this.index);
        return result;
    }

    @Override
    public void add(int newIndex, @Nullable ElementType element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends ElementType> c) {
        boolean result = this.backingList.addAll(c);
        this.indexList = this.backingList.get(this.index);
        return result;
    }

    @Override
    public boolean addAll(int i, @Nonnull Collection<? extends ElementType> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        LazyList<ElementType> copy = new LazyList<ElementType>();
        copy.addAll((Collection<ElementType>)this.indexList);
        this.backingList.removeAll(copy);
        this.indexList = this.backingList.get(this.index);
    }

    @Override
    public boolean contains(Object element) {
        return this.indexList.contains(element);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.indexList.containsAll(c);
    }

    @Override
    public ElementType get(int newIndex) {
        return (ElementType)((XMLObject)this.indexList.get(newIndex));
    }

    @Override
    public int indexOf(Object o) {
        return this.indexList.indexOf(o);
    }

    @Override
    public boolean isEmpty() {
        return this.indexList.isEmpty();
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.indexList.lastIndexOf(o);
    }

    @Override
    @Nonnull
    public ElementType remove(int newIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(@Nullable Object o) {
        boolean result = this.backingList.remove(o);
        this.indexList = this.backingList.get(this.index);
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = this.backingList.removeAll(c);
        this.indexList = this.backingList.get(this.index);
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = this.backingList.retainAll(c);
        this.indexList = this.backingList.get(this.index);
        return result;
    }

    @Override
    public ElementType set(int newIndex, @Nonnull ElementType element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return this.indexList.size();
    }

    @Override
    public Object[] toArray() {
        return this.indexList.toArray();
    }

    @Override
    public <T> T[] toArray(@Nonnull T[] a) {
        return this.indexList.toArray(a);
    }
}

