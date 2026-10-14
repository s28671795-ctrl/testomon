/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.wrappers;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

public class IteratorPrimitiveDoubleWrapper
implements PrimitiveIterator.OfDouble {
    private Iterator<Double> it;

    public IteratorPrimitiveDoubleWrapper(Iterator<Double> iterator2) {
        this.it = iterator2;
    }

    @Override
    public boolean hasNext() {
        return this.it.hasNext();
    }

    @Override
    public double nextDouble() {
        return this.it.next();
    }

    @Override
    public void forEachRemaining(Consumer<? super Double> consumer) {
        this.it.forEachRemaining(consumer);
    }
}

