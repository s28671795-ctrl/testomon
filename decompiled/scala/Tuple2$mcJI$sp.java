/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Product2$mcJI$sp;
import scala.Tuple2;
import scala.Tuple2$mcIJ$sp;

public final class Tuple2$mcJI$sp
extends Tuple2<Object, Object>
implements Product2$mcJI$sp {
    public final long _1$mcJ$sp;
    public final int _2$mcI$sp;

    @Override
    public long _1$mcJ$sp() {
        return this._1$mcJ$sp;
    }

    @Override
    public long _1() {
        return this._1$mcJ$sp();
    }

    @Override
    public int _2$mcI$sp() {
        return this._2$mcI$sp;
    }

    @Override
    public int _2() {
        return this._2$mcI$sp();
    }

    @Override
    public Tuple2<Object, Object> swap() {
        return this.swap$mcJI$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcJI$sp() {
        return new Tuple2$mcIJ$sp(this._2$mcI$sp(), this._1$mcJ$sp());
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
    public <T1, T2> int copy$default$2() {
        return this._2$mcI$sp();
    }

    @Override
    public <T1, T2> int copy$default$2$mcI$sp() {
        return this._2$mcI$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public Tuple2$mcJI$sp(long _1$mcJ$sp, int _2$mcI$sp) {
        this._1$mcJ$sp = _1$mcJ$sp;
        this._2$mcI$sp = _2$mcI$sp;
        super(null, null);
    }
}

