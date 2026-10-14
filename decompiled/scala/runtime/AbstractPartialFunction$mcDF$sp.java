/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1$mcDF$sp;
import scala.PartialFunction$;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxesRunTime;

public abstract class AbstractPartialFunction$mcDF$sp
extends AbstractPartialFunction<Object, Object>
implements Function1$mcDF$sp {
    @Override
    public double apply(float x) {
        return this.apply$mcDF$sp(x);
    }

    @Override
    public double apply$mcDF$sp(float x) {
        return BoxesRunTime.unboxToDouble(this.applyOrElse(Float.valueOf(x), PartialFunction$.MODULE$.empty()));
    }
}

