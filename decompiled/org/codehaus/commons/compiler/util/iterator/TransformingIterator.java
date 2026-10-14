/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.iterator;

import java.util.Iterator;

public abstract class TransformingIterator<T1, T2>
implements Iterator<T2> {
    private final Iterator<? extends T1> delegate;

    public TransformingIterator(Iterator<? extends T1> delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean hasNext() {
        return this.delegate.hasNext();
    }

    @Override
    public final T2 next() {
        return this.transform(this.delegate.next());
    }

    @Override
    public void remove() {
        this.delegate.remove();
    }

    protected abstract T2 transform(T1 var1);
}

