/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1$mcIF$sp;
import scala.PartialFunction$;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxesRunTime;

public abstract class AbstractPartialFunction$mcIF$sp
extends AbstractPartialFunction<Object, Object>
implements Function1$mcIF$sp {
    @Override
    public int apply(float x) {
        return this.apply$mcIF$sp(x);
    }

    @Override
    public int apply$mcIF$sp(float x) {
        return BoxesRunTime.unboxToInt(this.applyOrElse(Float.valueOf(x), PartialFunction$.MODULE$.empty()));
    }
}

