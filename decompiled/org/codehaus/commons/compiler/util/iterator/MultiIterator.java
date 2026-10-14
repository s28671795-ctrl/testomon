/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.iterator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.codehaus.commons.compiler.InternalCompilerException;

public class MultiIterator<T>
implements Iterator<T> {
    private final Iterator<?> outer;
    private Iterator<T> inner = Collections.emptyList().iterator();

    public MultiIterator(Iterator<T>[] iterators) {
        this.outer = Arrays.asList(iterators).iterator();
    }

    public MultiIterator(Collection<T>[] collections) {
        this.outer = Arrays.asList(collections).iterator();
    }

    public MultiIterator(Object[][] arrays) {
        this.outer = Arrays.asList(arrays).iterator();
    }

    public MultiIterator(Collection<?> collection) {
        this.outer = collection.iterator();
    }

    public MultiIterator(Iterator<?> iterator2) {
        this.outer = iterator2;
    }

    public MultiIterator(Object[] array) {
        this.outer = Arrays.asList(array).iterator();
    }

    public MultiIterator(Object object, Collection<T> collection) {
        this.outer = Arrays.asList(new Object[]{object}, collection).iterator();
    }

    public MultiIterator(Collection<T> collection, Object object) {
        this.outer = Arrays.asList(collection, new Object[]{object}).iterator();
    }

    public MultiIterator(Object prefix, Iterator<T> iterator2) {
        this.outer = Arrays.asList(new Object[]{prefix}, iterator2).iterator();
    }

    public MultiIterator(Iterator<T> iterator2, Object suffix) {
        this.outer = Arrays.asList(iterator2, new Object[]{suffix}).iterator();
    }

    @Override
    public boolean hasNext() {
        Object o;
        while (true) {
            Object[] tmp;
            if (this.inner.hasNext()) {
                return true;
            }
            if (!this.outer.hasNext()) {
                return false;
            }
            o = this.outer.next();
            if (o instanceof Iterator) {
                tmp = (Iterator)o;
                this.inner = tmp;
                continue;
            }
            if (o instanceof Collection) {
                tmp = (Collection)o;
                this.inner = tmp.iterator();
                continue;
            }
            if (!(o instanceof Object[])) break;
            tmp = (Object[])o;
            this.inner = Arrays.asList(tmp).iterator();
        }
        throw new InternalCompilerException("Unexpected element type \"" + o.getClass().getName() + "\"");
    }

    @Override
    public T next() {
        if (this.hasNext()) {
            return this.inner.next();
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        this.inner.remove();
    }
}

