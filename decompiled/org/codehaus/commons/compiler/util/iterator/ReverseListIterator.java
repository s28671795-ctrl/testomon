/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.iterator;

import java.util.ListIterator;
import org.codehaus.commons.compiler.util.iterator.FilterListIterator;

public class ReverseListIterator<T>
extends FilterListIterator<T> {
    public ReverseListIterator(ListIterator<T> delegate) {
        super(delegate);
    }

    @Override
    public boolean hasNext() {
        return super.hasPrevious();
    }

    @Override
    public boolean hasPrevious() {
        return super.hasNext();
    }

    @Override
    public T next() {
        return super.previous();
    }

    @Override
    public T previous() {
        return super.next();
    }

    @Override
    public int nextIndex() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int previousIndex() {
        throw new UnsupportedOperationException();
    }
}

