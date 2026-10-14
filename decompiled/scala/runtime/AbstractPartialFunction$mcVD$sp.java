/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1$mcVD$sp;
import scala.PartialFunction$;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxedUnit;

public abstract class AbstractPartialFunction$mcVD$sp
extends AbstractPartialFunction<Object, BoxedUnit>
implements Function1$mcVD$sp {
    @Override
    public void apply(double x) {
        this.apply$mcVD$sp(x);
    }

    @Override
    public void apply$mcVD$sp(double x) {
        this.applyOrElse(x, PartialFunction$.MODULE$.empty());
    }
}

