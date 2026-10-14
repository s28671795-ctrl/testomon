/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.generic;

import scala.Function1;
import scala.collection.IterableOnce;
import scala.collection.generic.IsIterable;
import scala.collection.generic.IsIterableOnce;
import scala.collection.generic.IsIterableOnceLowPriority;
import scala.runtime.Statics;

public final class IsIterableOnce$
implements IsIterableOnceLowPriority {
    public static final IsIterableOnce$ MODULE$ = new IsIterableOnce$();

    @Override
    public <Repr> IsIterableOnce<Repr> isIterableLikeIsIterableOnce(IsIterable<Repr> isIterableLike) {
        return IsIterableOnceLowPriority.isIterableLikeIsIterableOnce$(this, isIterableLike);
    }

    public <CC0 extends IterableOnce<Object>, A0> IsIterableOnce<CC0> iterableOnceIsIterableOnce() {
        return new IsIterableOnce<CC0>(){
            private Function1<CC0, IterableOnce<A0>> conversion;

            public Function1<CC0, IterableOnce<A0>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<CC0, IterableOnce<A0>> x$1) {
                this.conversion = x$1;
            }

            public IterableOnce<A0> apply(CC0 coll) {
                return coll;
            }
            {
                IsIterableOnce.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    private IsIterableOnce$() {
    }
}

