/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.iterator;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationIterator<T>
implements Iterator<T> {
    private final Enumeration<? extends T> e;

    public EnumerationIterator(Enumeration<? extends T> e) {
        this.e = e;
    }

    @Override
    public boolean hasNext() {
        return this.e.hasMoreElements();
    }

    @Override
    public T next() {
        return this.e.nextElement();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}

