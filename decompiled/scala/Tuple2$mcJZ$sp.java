/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;
import scala.Tuple2$mcZJ$sp;

public final class Tuple2$mcJZ$sp
extends Tuple2<Object, Object> {
    public final long _1$mcJ$sp;
    public final boolean _2$mcZ$sp;

    @Override
    public long _1$mcJ$sp() {
        return this._1$mcJ$sp;
    }

    @Override
    public long _1() {
        return this._1$mcJ$sp();
    }

    @Override
    public boolean _2$mcZ$sp() {
        return this._2$mcZ$sp;
    }

    @Override
    public boolean _2() {
        return this._2$mcZ$sp();
    }

    @Override
    public Tuple2<Object, Object> swap() {
        return this.swap$mcJZ$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcJZ$sp() {
        return new Tuple2$mcZJ$sp(this._2$mcZ$sp(), this._1$mcJ$sp());
    }

    @Override
    public <T1, T2> long copy$default$1() {
        return this._1$mcJ$sp();
    }

    @Override
    public <T1, T2> long copy$default$1$mcJ$sp() {
        return this._1$mcJ$sp();
    }

    @Override
    public <T1, T2> boolean copy$default$2() {
        return this._2$mcZ$sp();
    }

    @Override
    public <T1, T2> boolean copy$default$2$mcZ$sp() {
        return this._2$mcZ$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public Tuple2$mcJZ$sp(long _1$mcJ$sp, boolean _2$mcZ$sp) {
        this._1$mcJ$sp = _1$mcJ$sp;
        this._2$mcZ$sp = _2$mcZ$sp;
        super(null, null);
    }
}

