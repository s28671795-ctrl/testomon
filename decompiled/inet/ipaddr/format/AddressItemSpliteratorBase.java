/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format;

import inet.ipaddr.format.AddressComponentRange;
import inet.ipaddr.format.SpliteratorBase;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

abstract class AddressItemSpliteratorBase<S extends AddressComponentRange, T>
extends SpliteratorBase<S, T> {
    private static final BigInteger INT_MAX = BigInteger.valueOf(Integer.MAX_VALUE);
    protected boolean isBig;
    protected BigInteger iteratedCountB = BigInteger.ZERO;
    protected long iteratedCountI;

    AddressItemSpliteratorBase() {
    }

    @Override
    boolean tryAdvance(Iterator<T> iterator2, Consumer<? super T> action) {
        if (this.isBig) {
            T next;
            try {
                next = iterator2.next();
                this.iteratedCountB = this.iteratedCountB.add(BigInteger.ONE);
            }
            catch (NoSuchElementException e) {
                return false;
            }
            action.accept(next);
            return true;
        }
        return super.tryAdvance(iterator2, action);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void forEachRemaining(Iterator<T> iterator2, Consumer<? super T> action, BigInteger bound2) {
        int intBound;
        boolean noIntBound;
        if (this.iteratedCountB.signum() > 0) {
            bound2 = bound2.subtract(this.iteratedCountB);
        }
        if (bound2.compareTo(INT_MAX) >= 0) {
            noIntBound = true;
            intBound = 0;
        } else {
            noIntBound = false;
            intBound = bound2.intValue();
        }
        try {
            while (noIntBound || this.iteratedCountI < (long)intBound) {
                T next;
                block10: {
                    try {
                        next = iterator2.next();
                        if (++this.iteratedCountI != Integer.MAX_VALUE) break block10;
                        this.iteratedCountI = 0L;
                        this.iteratedCountB = this.iteratedCountB.add(INT_MAX);
                        if ((bound2 = bound2.subtract(INT_MAX)).compareTo(INT_MAX) >= 0) break block10;
                        noIntBound = false;
                        intBound = bound2.intValue();
                    }
                    catch (NoSuchElementException e) {
                        break;
                    }
                }
                action.accept(next);
            }
        }
        finally {
            if (this.iteratedCountI != 0L) {
                this.iteratedCountB = this.iteratedCountB.add(BigInteger.valueOf(this.iteratedCountI));
                this.iteratedCountI = 0L;
            }
        }
    }
}

