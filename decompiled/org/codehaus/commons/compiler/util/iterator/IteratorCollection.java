/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.iterator;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.codehaus.commons.nullanalysis.Nullable;

public class IteratorCollection<T>
extends AbstractCollection<T> {
    private final Iterator<T> iterator;
    private final List<T> elements = new ArrayList<T>();

    public IteratorCollection(Iterator<T> iterator2) {
        this.iterator = iterator2;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>(){
            @Nullable
            private Iterator<T> elementsIterator;
            {
                this.elementsIterator = IteratorCollection.this.elements.iterator();
            }

            @Override
            public T next() {
                Iterator ei = this.elementsIterator;
                if (ei != null) {
                    if (ei.hasNext()) {
                        return ei.next();
                    }
                    this.elementsIterator = null;
                }
                Object o = IteratorCollection.this.iterator.next();
                IteratorCollection.this.elements.add(o);
                return o;
            }

            @Override
            public boolean hasNext() {
                return this.elementsIterator != null && this.elementsIterator.hasNext() || IteratorCollection.this.iterator.hasNext();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public int size() {
        int size = 0;
        for (T o : this) {
            ++size;
        }
        return size;
    }
}

