/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.runtime.NonLocalReturnControl;

public class NonLocalReturnControl$mcB$sp
extends NonLocalReturnControl<Object> {
    public final byte value$mcB$sp;

    @Override
    public byte value$mcB$sp() {
        return this.value$mcB$sp;
    }

    @Override
    public byte value() {
        return this.value$mcB$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public NonLocalReturnControl$mcB$sp(Object key, byte value$mcB$sp) {
        this.value$mcB$sp = value$mcB$sp;
        super(key, null);
    }
}

