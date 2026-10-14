/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1$mcDJ$sp;
import scala.PartialFunction$;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxesRunTime;

public abstract class AbstractPartialFunction$mcDJ$sp
extends AbstractPartialFunction<Object, Object>
implements Function1$mcDJ$sp {
    @Override
    public double apply(long x) {
        return this.apply$mcDJ$sp(x);
    }

    @Override
    public double apply$mcDJ$sp(long x) {
        return BoxesRunTime.unboxToDouble(this.applyOrElse(x, PartialFunction$.MODULE$.empty()));
    }
}

