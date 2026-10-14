/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.runtime.NonLocalReturnControl;

public class NonLocalReturnControl$mcD$sp
extends NonLocalReturnControl<Object> {
    public final double value$mcD$sp;

    @Override
    public double value$mcD$sp() {
        return this.value$mcD$sp;
    }

    @Override
    public double value() {
        return this.value$mcD$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public NonLocalReturnControl$mcD$sp(Object key, double value$mcD$sp) {
        this.value$mcD$sp = value$mcD$sp;
        super(key, null);
    }
}

