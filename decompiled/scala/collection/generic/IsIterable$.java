/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.generic;

import scala.Function1;
import scala.collection.BitSet;
import scala.collection.BitSetOps;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.generic.IsIterable;
import scala.collection.generic.IsIterableLowPriority;
import scala.collection.generic.IsIterableOnce;
import scala.collection.generic.IsMap;
import scala.collection.generic.IsSeq;
import scala.runtime.Statics;

public final class IsIterable$
implements IsIterableLowPriority {
    public static final IsIterable$ MODULE$ = new IsIterable$();

    @Override
    public <Repr> IsIterable<Repr> isSeqLikeIsIterable(IsSeq<Repr> isSeqLike) {
        return IsIterableLowPriority.isSeqLikeIsIterable$(this, isSeqLike);
    }

    @Override
    public <Repr> IsIterable<Repr> isMapLikeIsIterable(IsMap<Repr> isMapLike) {
        return IsIterableLowPriority.isMapLikeIsIterable$(this, isMapLike);
    }

    public <A0, CC0 extends IterableOps<Object, Iterable, CC0>> IsIterable<CC0> iterableOpsIsIterable() {
        return new IsIterable<CC0>(){
            private Function1<CC0, IterableOps<A0, Iterable, CC0>> conversion;

            public Function1<CC0, IterableOps<A0, Iterable, CC0>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<CC0, IterableOps<A0, Iterable, CC0>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<CC0, IterableOnce<A0>> x$1) {
            }

            public IterableOps<A0, Iterable, CC0> apply(CC0 coll) {
                return coll;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public <C0 extends BitSet & BitSetOps<C0>> IsIterable<C0> bitSetOpsIsIterable() {
        return new IsIterable<C0>(){
            private Function1<C0, IterableOps<Object, Iterable, C0>> conversion;

            public Function1<C0, IterableOps<Object, Iterable, C0>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<C0, IterableOps<Object, Iterable, C0>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<C0, IterableOnce<Object>> x$1) {
            }

            public IterableOps<Object, Iterable, C0> apply(C0 coll) {
                return coll;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    private IsIterable$() {
    }
}

