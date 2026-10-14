/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.codehaus.commons.compiler.util.Producer;
import org.codehaus.commons.nullanalysis.Nullable;

public class ProducerIterator<T>
implements Iterator<T> {
    private final Producer<T> producer;
    private static final Object UNKNOWN = new Object();
    @Nullable
    private static final Object AT_END = null;
    @Nullable
    private Object nextElement = UNKNOWN;

    public ProducerIterator(Producer<T> producer) {
        this.producer = producer;
    }

    @Override
    public boolean hasNext() {
        if (this.nextElement == UNKNOWN) {
            this.nextElement = this.producer.produce();
        }
        return this.nextElement != AT_END;
    }

    @Override
    public T next() {
        if (this.nextElement == UNKNOWN) {
            this.nextElement = this.producer.produce();
        }
        if (this.nextElement == AT_END) {
            throw new NoSuchElementException();
        }
        Object result = this.nextElement;
        this.nextElement = UNKNOWN;
        assert (result != null);
        return (T)result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}

