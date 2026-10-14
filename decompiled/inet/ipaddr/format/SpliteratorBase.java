/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format;

import inet.ipaddr.format.AddressComponentRange;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

abstract class SpliteratorBase<S extends AddressComponentRange, T>
implements AddressComponentRangeSpliterator<S, T> {
    protected long iteratedCountL;
    protected boolean inForEach;

    SpliteratorBase() {
    }

    boolean tryAdvance(Iterator<T> iterator2, Consumer<? super T> action) {
        T next;
        try {
            next = iterator2.next();
            ++this.iteratedCountL;
        }
        catch (NoSuchElementException e) {
            return false;
        }
        action.accept(next);
        return true;
    }

    void forEachRemaining(Iterator<T> iterator2, Consumer<? super T> action, long bound2) {
        while (this.iteratedCountL < bound2) {
            T next;
            try {
                next = iterator2.next();
                ++this.iteratedCountL;
            }
            catch (NoSuchElementException e) {
                return;
            }
            action.accept(next);
        }
    }

    @Override
    public int characteristics() {
        return 20821;
    }

    public String toString() {
        return "spliterator for " + this.getAddressItem();
    }
}

