/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1$mcVJ$sp;
import scala.PartialFunction$;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxedUnit;

public abstract class AbstractPartialFunction$mcVJ$sp
extends AbstractPartialFunction<Object, BoxedUnit>
implements Function1$mcVJ$sp {
    @Override
    public void apply(long x) {
        this.apply$mcVJ$sp(x);
    }

    @Override
    public void apply$mcVJ$sp(long x) {
        this.applyOrElse(x, PartialFunction$.MODULE$.empty());
    }
}

