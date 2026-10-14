/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1$mcVF$sp;
import scala.PartialFunction$;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxedUnit;

public abstract class AbstractPartialFunction$mcVF$sp
extends AbstractPartialFunction<Object, BoxedUnit>
implements Function1$mcVF$sp {
    @Override
    public void apply(float x) {
        this.apply$mcVF$sp(x);
    }

    @Override
    public void apply$mcVF$sp(float x) {
        this.applyOrElse(Float.valueOf(x), PartialFunction$.MODULE$.empty());
    }
}

