/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.runtime.NonLocalReturnControl;

public class NonLocalReturnControl$mcC$sp
extends NonLocalReturnControl<Object> {
    public final char value$mcC$sp;

    @Override
    public char value$mcC$sp() {
        return this.value$mcC$sp;
    }

    @Override
    public char value() {
        return this.value$mcC$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public NonLocalReturnControl$mcC$sp(Object key, char value$mcC$sp) {
        this.value$mcC$sp = value$mcC$sp;
        super(key, null);
    }
}

