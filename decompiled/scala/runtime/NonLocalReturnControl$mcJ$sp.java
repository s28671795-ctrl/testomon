/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.runtime.NonLocalReturnControl;

public class NonLocalReturnControl$mcJ$sp
extends NonLocalReturnControl<Object> {
    public final long value$mcJ$sp;

    @Override
    public long value$mcJ$sp() {
        return this.value$mcJ$sp;
    }

    @Override
    public long value() {
        return this.value$mcJ$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public NonLocalReturnControl$mcJ$sp(Object key, long value$mcJ$sp) {
        this.value$mcJ$sp = value$mcJ$sp;
        super(key, null);
    }
}

