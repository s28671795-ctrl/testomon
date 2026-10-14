/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.wrappers;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

public class IteratorPrimitiveLongWrapper
implements PrimitiveIterator.OfLong {
    private Iterator<Long> it;

    public IteratorPrimitiveLongWrapper(Iterator<Long> iterator2) {
        this.it = iterator2;
    }

    @Override
    public boolean hasNext() {
        return this.it.hasNext();
    }

    @Override
    public long nextLong() {
        return this.it.next();
    }

    @Override
    public void forEachRemaining(Consumer<? super Long> consumer) {
        this.it.forEachRemaining(consumer);
    }
}

