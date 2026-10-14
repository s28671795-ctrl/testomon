/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Product2$mcII$sp;
import scala.Tuple2;

public final class Tuple2$mcII$sp
extends Tuple2<Object, Object>
implements Product2$mcII$sp {
    public final int _1$mcI$sp;
    public final int _2$mcI$sp;

    @Override
    public int _1$mcI$sp() {
        return this._1$mcI$sp;
    }

    @Override
    public int _1() {
        return this._1$mcI$sp();
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
        return this.swap$mcII$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcII$sp() {
        return new Tuple2$mcII$sp(this._2$mcI$sp(), this._1$mcI$sp());
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

    public Tuple2$mcII$sp(int _1$mcI$sp, int _2$mcI$sp) {
        this._1$mcI$sp = _1$mcI$sp;
        this._2$mcI$sp = _2$mcI$sp;
        super(null, null);
    }
}

