/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.iterator;

import java.util.ListIterator;
import org.codehaus.commons.nullanalysis.Nullable;

public abstract class FilterListIterator<T>
implements ListIterator<T> {
    protected final ListIterator<T> delegate;

    public FilterListIterator(ListIterator<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean hasNext() {
        return this.delegate.hasNext();
    }

    @Override
    public T next() {
        return this.delegate.next();
    }

    @Override
    public boolean hasPrevious() {
        return this.delegate.hasPrevious();
    }

    @Override
    public T previous() {
        return this.delegate.previous();
    }

    @Override
    public int nextIndex() {
        return this.delegate.nextIndex();
    }

    @Override
    public int previousIndex() {
        return this.delegate.previousIndex();
    }

    @Override
    public void remove() {
        this.delegate.remove();
    }

    @Override
    public void set(@Nullable T o) {
        this.delegate.set(o);
    }

    @Override
    public void add(@Nullable T o) {
        this.delegate.add(o);
    }
}

