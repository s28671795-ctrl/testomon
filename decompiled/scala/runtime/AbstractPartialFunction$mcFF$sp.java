/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1$mcFF$sp;
import scala.PartialFunction$;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxesRunTime;

public abstract class AbstractPartialFunction$mcFF$sp
extends AbstractPartialFunction<Object, Object>
implements Function1$mcFF$sp {
    @Override
    public float apply(float x) {
        return this.apply$mcFF$sp(x);
    }

    @Override
    public float apply$mcFF$sp(float x) {
        return BoxesRunTime.unboxToFloat(this.applyOrElse(Float.valueOf(x), PartialFunction$.MODULE$.empty()));
    }
}

