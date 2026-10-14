/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;
import scala.Tuple2$mcZI$sp;

public final class Tuple2$mcIZ$sp
extends Tuple2<Object, Object> {
    public final int _1$mcI$sp;
    public final boolean _2$mcZ$sp;

    @Override
    public int _1$mcI$sp() {
        return this._1$mcI$sp;
    }

    @Override
    public int _1() {
        return this._1$mcI$sp();
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
        return this.swap$mcIZ$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcIZ$sp() {
        return new Tuple2$mcZI$sp(this._2$mcZ$sp(), this._1$mcI$sp());
    }

    @Override
    public <T1, T2> int copy$default$1() {
        return this._1$mcI$sp();
    }

    @Override
    public <T1, T2> int copy$default$1$mcI$sp() {
        return this._1$mcI$sp();
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

    public Tuple2$mcIZ$sp(int _1$mcI$sp, boolean _2$mcZ$sp) {
        this._1$mcI$sp = _1$mcI$sp;
        this._2$mcZ$sp = _2$mcZ$sp;
        super(null, null);
    }
}

