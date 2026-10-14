/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;
import scala.Tuple2$mcJZ$sp;

public final class Tuple2$mcZJ$sp
extends Tuple2<Object, Object> {
    public final boolean _1$mcZ$sp;
    public final long _2$mcJ$sp;

    @Override
    public boolean _1$mcZ$sp() {
        return this._1$mcZ$sp;
    }

    @Override
    public boolean _1() {
        return this._1$mcZ$sp();
    }

    @Override
    public long _2$mcJ$sp() {
        return this._2$mcJ$sp;
    }

    @Override
    public long _2() {
        return this._2$mcJ$sp();
    }

    @Override
    public Tuple2<Object, Object> swap() {
        return this.swap$mcZJ$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcZJ$sp() {
        return new Tuple2$mcJZ$sp(this._2$mcJ$sp(), this._1$mcZ$sp());
    }

    @Override
    public <T1, T2> boolean copy$default$1() {
        return this._1$mcZ$sp();
    }

    @Override
    public <T1, T2> boolean copy$default$1$mcZ$sp() {
        return this._1$mcZ$sp();
    }

    @Override
    public <T1, T2> long copy$default$2() {
        return this._2$mcJ$sp();
    }

    @Override
    public <T1, T2> long copy$default$2$mcJ$sp() {
        return this._2$mcJ$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public Tuple2$mcZJ$sp(boolean _1$mcZ$sp, long _2$mcJ$sp) {
        this._1$mcZ$sp = _1$mcZ$sp;
        this._2$mcJ$sp = _2$mcJ$sp;
        super(null, null);
    }
}

