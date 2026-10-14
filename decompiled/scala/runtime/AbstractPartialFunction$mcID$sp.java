/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1$mcID$sp;
import scala.PartialFunction$;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxesRunTime;

public abstract class AbstractPartialFunction$mcID$sp
extends AbstractPartialFunction<Object, Object>
implements Function1$mcID$sp {
    @Override
    public int apply(double x) {
        return this.apply$mcID$sp(x);
    }

    @Override
    public int apply$mcID$sp(double x) {
        return BoxesRunTime.unboxToInt(this.applyOrElse(x, PartialFunction$.MODULE$.empty()));
    }
}

