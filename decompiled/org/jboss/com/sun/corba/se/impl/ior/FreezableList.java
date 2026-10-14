/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import java.util.AbstractList;
import java.util.List;
import org.jboss.com.sun.corba.se.spi.ior.MakeImmutable;

public class FreezableList
extends AbstractList<Object> {
    private List<Object> delegate = null;
    private boolean immutable = false;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FreezableList)) {
            return false;
        }
        FreezableList other = (FreezableList)obj;
        return this.delegate.equals(other.delegate) && this.immutable == other.immutable;
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }

    public FreezableList(List<Object> delegate, boolean immutable) {
        this.delegate = delegate;
        this.immutable = immutable;
    }

    public FreezableList(List<Object> delegate) {
        this(delegate, false);
    }

    public void makeImmutable() {
        this.immutable = true;
    }

    public boolean isImmutable() {
        return this.immutable;
    }

    public void makeElementsImmutable() {
        for (Object obj : this) {
            if (!(obj instanceof MakeImmutable)) continue;
            MakeImmutable element = (MakeImmutable)obj;
            element.makeImmutable();
        }
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public Object get(int index) {
        return this.delegate.get(index);
    }

    @Override
    public Object set(int index, Object element) {
        if (this.immutable) {
            throw new UnsupportedOperationException();
        }
        return this.delegate.set(index, element);
    }

    @Override
    public void add(int index, Object element) {
        if (this.immutable) {
            throw new UnsupportedOperationException();
        }
        this.delegate.add(index, element);
    }

    @Override
    public Object remove(int index) {
        if (this.immutable) {
            throw new UnsupportedOperationException();
        }
        return this.delegate.remove(index);
    }

    @Override
    public List<Object> subList(int fromIndex, int toIndex) {
        List<Object> list = this.delegate.subList(fromIndex, toIndex);
        FreezableList result = new FreezableList(list, this.immutable);
        return result;
    }
}

