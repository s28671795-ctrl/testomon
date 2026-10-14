/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;

public final class Tuple2$mcZZ$sp
extends Tuple2<Object, Object> {
    public final boolean _1$mcZ$sp;
    public final boolean _2$mcZ$sp;

    @Override
    public boolean _1$mcZ$sp() {
        return this._1$mcZ$sp;
    }

    @Override
    public boolean _1() {
        return this._1$mcZ$sp();
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
        return this.swap$mcZZ$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcZZ$sp() {
        return new Tuple2$mcZZ$sp(this._2$mcZ$sp(), this._1$mcZ$sp());
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

    public Tuple2$mcZZ$sp(boolean _1$mcZ$sp, boolean _2$mcZ$sp) {
        this._1$mcZ$sp = _1$mcZ$sp;
        this._2$mcZ$sp = _2$mcZ$sp;
        super(null, null);
    }
}

