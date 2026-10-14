/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.annotation.InternalApi;
import akka.io.SimpleDnsCache;
import scala.Function1;
import scala.Predef$;
import scala.Some;
import scala.math.Ordering;
import scala.math.PartialOrdering;

public final class SimpleDnsCache$ {
    public static final SimpleDnsCache$ MODULE$ = new SimpleDnsCache$();

    @InternalApi
    public <K> Ordering<SimpleDnsCache.ExpiryEntry<K>> expiryEntryOrdering() {
        return new Ordering<SimpleDnsCache.ExpiryEntry<K>>(){

            public Some tryCompare(Object x, Object y) {
                return Ordering.tryCompare$(this, x, y);
            }

            public boolean lteq(Object x, Object y) {
                return Ordering.lteq$(this, x, y);
            }

            public boolean gteq(Object x, Object y) {
                return Ordering.gteq$(this, x, y);
            }

            public boolean lt(Object x, Object y) {
                return Ordering.lt$(this, x, y);
            }

            public boolean gt(Object x, Object y) {
                return Ordering.gt$(this, x, y);
            }

            public boolean equiv(Object x, Object y) {
                return Ordering.equiv$(this, x, y);
            }

            public Object max(Object x, Object y) {
                return Ordering.max$(this, x, y);
            }

            public Object min(Object x, Object y) {
                return Ordering.min$(this, x, y);
            }

            public Ordering<SimpleDnsCache.ExpiryEntry<K>> reverse() {
                return Ordering.reverse$(this);
            }

            public boolean isReverseOf(Ordering<?> other) {
                return Ordering.isReverseOf$(this, other);
            }

            public <U> Ordering<U> on(Function1<U, SimpleDnsCache.ExpiryEntry<K>> f) {
                return Ordering.on$(this, f);
            }

            public Ordering<SimpleDnsCache.ExpiryEntry<K>> orElse(Ordering<SimpleDnsCache.ExpiryEntry<K>> other) {
                return Ordering.orElse$(this, other);
            }

            public <S> Ordering<SimpleDnsCache.ExpiryEntry<K>> orElseBy(Function1<SimpleDnsCache.ExpiryEntry<K>, S> f, Ordering<S> ord) {
                return Ordering.orElseBy$(this, f, ord);
            }

            public Ordering.OrderingOps mkOrderingOps(Object lhs) {
                return Ordering.mkOrderingOps$(this, lhs);
            }

            public int compare(SimpleDnsCache.ExpiryEntry<K> x, SimpleDnsCache.ExpiryEntry<K> y) {
                return Predef$.MODULE$.long2Long(x.until()).compareTo(Predef$.MODULE$.long2Long(y.until()));
            }
            {
                PartialOrdering.$init$(this);
                Ordering.$init$(this);
            }
        };
    }

    private SimpleDnsCache$() {
    }
}

