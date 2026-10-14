/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.runtime.NonLocalReturnControl;

public class NonLocalReturnControl$mcI$sp
extends NonLocalReturnControl<Object> {
    public final int value$mcI$sp;

    @Override
    public int value$mcI$sp() {
        return this.value$mcI$sp;
    }

    @Override
    public int value() {
        return this.value$mcI$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public NonLocalReturnControl$mcI$sp(Object key, int value$mcI$sp) {
        this.value$mcI$sp = value$mcI$sp;
        super(key, null);
    }
}

