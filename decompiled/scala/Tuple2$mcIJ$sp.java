/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Product2$mcIJ$sp;
import scala.Tuple2;
import scala.Tuple2$mcJI$sp;

public final class Tuple2$mcIJ$sp
extends Tuple2<Object, Object>
implements Product2$mcIJ$sp {
    public final int _1$mcI$sp;
    public final long _2$mcJ$sp;

    @Override
    public int _1$mcI$sp() {
        return this._1$mcI$sp;
    }

    @Override
    public int _1() {
        return this._1$mcI$sp();
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
        return this.swap$mcIJ$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcIJ$sp() {
        return new Tuple2$mcJI$sp(this._2$mcJ$sp(), this._1$mcI$sp());
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

    public Tuple2$mcIJ$sp(int _1$mcI$sp, long _2$mcJ$sp) {
        this._1$mcI$sp = _1$mcI$sp;
        this._2$mcJ$sp = _2$mcJ$sp;
        super(null, null);
    }
}

