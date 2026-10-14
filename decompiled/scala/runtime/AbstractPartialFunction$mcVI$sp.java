/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1$mcVI$sp;
import scala.PartialFunction$;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxedUnit;

public abstract class AbstractPartialFunction$mcVI$sp
extends AbstractPartialFunction<Object, BoxedUnit>
implements Function1$mcVI$sp {
    @Override
    public void apply(int x) {
        this.apply$mcVI$sp(x);
    }

    @Override
    public void apply$mcVI$sp(int x) {
        this.applyOrElse(x, PartialFunction$.MODULE$.empty());
    }
}

