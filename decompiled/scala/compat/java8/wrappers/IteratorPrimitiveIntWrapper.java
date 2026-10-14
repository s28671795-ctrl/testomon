/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.wrappers;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

public class IteratorPrimitiveIntWrapper
implements PrimitiveIterator.OfInt {
    private Iterator<Integer> it;

    public IteratorPrimitiveIntWrapper(Iterator<Integer> iterator2) {
        this.it = iterator2;
    }

    @Override
    public boolean hasNext() {
        return this.it.hasNext();
    }

    @Override
    public int nextInt() {
        return this.it.next();
    }

    @Override
    public void forEachRemaining(Consumer<? super Integer> consumer) {
        this.it.forEachRemaining(consumer);
    }
}

