/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1$mcFI$sp;
import scala.PartialFunction$;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxesRunTime;

public abstract class AbstractPartialFunction$mcFI$sp
extends AbstractPartialFunction<Object, Object>
implements Function1$mcFI$sp {
    @Override
    public float apply(int x) {
        return this.apply$mcFI$sp(x);
    }

    @Override
    public float apply$mcFI$sp(int x) {
        return BoxesRunTime.unboxToFloat(this.applyOrElse(x, PartialFunction$.MODULE$.empty()));
    }
}

