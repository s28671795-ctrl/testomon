/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.iterator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.codehaus.commons.compiler.util.iterator.EnumerationIterator;
import org.codehaus.commons.compiler.util.iterator.UniterableElementException;

public class MultiDimensionalIterator<T>
implements Iterator<T> {
    private final Iterator<?>[] nest;
    private static final Iterator<Object> EMPTY_ITERATOR = new Iterator<Object>(){

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    };

    public MultiDimensionalIterator(Iterator<?> delegate, int dimensionCount) {
        this.nest = new Iterator[dimensionCount];
        this.nest[0] = delegate;
        for (int i = 1; i < dimensionCount; ++i) {
            this.nest[i] = EMPTY_ITERATOR;
        }
    }

    @Override
    public boolean hasNext() {
        if (this.nest[this.nest.length - 1].hasNext()) {
            return true;
        }
        int i = this.nest.length - 2;
        if (i < 0) {
            return false;
        }
        while (true) {
            if (!this.nest[i].hasNext()) {
                if (i == 0) {
                    return false;
                }
                --i;
                continue;
            }
            if (i == this.nest.length - 1) {
                return true;
            }
            Object o = this.nest[i].next();
            if (o instanceof Iterator) {
                this.nest[++i] = (Iterator)o;
                continue;
            }
            if (o instanceof Object[]) {
                this.nest[++i] = Arrays.asList((Object[])o).iterator();
                continue;
            }
            if (o instanceof Collection) {
                this.nest[++i] = ((Collection)o).iterator();
                continue;
            }
            if (!(o instanceof Enumeration)) break;
            this.nest[++i] = new EnumerationIterator((Enumeration)o);
        }
        throw new UniterableElementException();
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        Object result = this.nest[this.nest.length - 1].next();
        return (T)result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}

