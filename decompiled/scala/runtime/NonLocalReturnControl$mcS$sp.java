/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.runtime.NonLocalReturnControl;

public class NonLocalReturnControl$mcS$sp
extends NonLocalReturnControl<Object> {
    public final short value$mcS$sp;

    @Override
    public short value$mcS$sp() {
        return this.value$mcS$sp;
    }

    @Override
    public short value() {
        return this.value$mcS$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public NonLocalReturnControl$mcS$sp(Object key, short value$mcS$sp) {
        this.value$mcS$sp = value$mcS$sp;
        super(key, null);
    }
}

