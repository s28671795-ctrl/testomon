/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.runtime.NonLocalReturnControl;

public class NonLocalReturnControl$mcF$sp
extends NonLocalReturnControl<Object> {
    public final float value$mcF$sp;

    @Override
    public float value$mcF$sp() {
        return this.value$mcF$sp;
    }

    @Override
    public float value() {
        return this.value$mcF$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public NonLocalReturnControl$mcF$sp(Object key, float value$mcF$sp) {
        this.value$mcF$sp = value$mcF$sp;
        super(key, null);
    }
}

