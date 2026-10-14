/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.runtime.BoxedUnit;
import scala.runtime.NonLocalReturnControl;

public class NonLocalReturnControl$mcV$sp
extends NonLocalReturnControl<BoxedUnit> {
    public final BoxedUnit value$mcV$sp;

    @Override
    public void value$mcV$sp() {
    }

    @Override
    public void value() {
        this.value$mcV$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public NonLocalReturnControl$mcV$sp(Object key, BoxedUnit value$mcV$sp) {
        this.value$mcV$sp = value$mcV$sp;
        super(key, BoxedUnit.UNIT);
    }
}

