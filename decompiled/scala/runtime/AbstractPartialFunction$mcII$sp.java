/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1$mcII$sp;
import scala.PartialFunction$;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxesRunTime;

public abstract class AbstractPartialFunction$mcII$sp
extends AbstractPartialFunction<Object, Object>
implements Function1$mcII$sp {
    @Override
    public int apply(int x) {
        return this.apply$mcII$sp(x);
    }

    @Override
    public int apply$mcII$sp(int x) {
        return BoxesRunTime.unboxToInt(this.applyOrElse(x, PartialFunction$.MODULE$.empty()));
    }
}

