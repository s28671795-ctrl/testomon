/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1$mcJI$sp;
import scala.PartialFunction$;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxesRunTime;

public abstract class AbstractPartialFunction$mcJI$sp
extends AbstractPartialFunction<Object, Object>
implements Function1$mcJI$sp {
    @Override
    public long apply(int x) {
        return this.apply$mcJI$sp(x);
    }

    @Override
    public long apply$mcJI$sp(int x) {
        return BoxesRunTime.unboxToLong(this.applyOrElse(x, PartialFunction$.MODULE$.empty()));
    }
}

