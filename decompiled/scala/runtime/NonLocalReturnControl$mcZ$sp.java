/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.runtime.NonLocalReturnControl;

public class NonLocalReturnControl$mcZ$sp
extends NonLocalReturnControl<Object> {
    public final boolean value$mcZ$sp;

    @Override
    public boolean value$mcZ$sp() {
        return this.value$mcZ$sp;
    }

    @Override
    public boolean value() {
        return this.value$mcZ$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public NonLocalReturnControl$mcZ$sp(Object key, boolean value$mcZ$sp) {
        this.value$mcZ$sp = value$mcZ$sp;
        super(key, null);
    }
}

