/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1$mcZF$sp;
import scala.PartialFunction$;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxesRunTime;

public abstract class AbstractPartialFunction$mcZF$sp
extends AbstractPartialFunction<Object, Object>
implements Function1$mcZF$sp {
    @Override
    public boolean apply(float x) {
        return this.apply$mcZF$sp(x);
    }

    @Override
    public boolean apply$mcZF$sp(float x) {
        return BoxesRunTime.unboxToBoolean(this.applyOrElse(Float.valueOf(x), PartialFunction$.MODULE$.empty()));
    }
}

