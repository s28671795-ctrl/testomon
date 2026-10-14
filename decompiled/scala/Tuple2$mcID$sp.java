/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Product2$mcID$sp;
import scala.Tuple2;
import scala.Tuple2$mcDI$sp;

public final class Tuple2$mcID$sp
extends Tuple2<Object, Object>
implements Product2$mcID$sp {
    public final int _1$mcI$sp;
    public final double _2$mcD$sp;

    @Override
    public int _1$mcI$sp() {
        return this._1$mcI$sp;
    }

    @Override
    public int _1() {
        return this._1$mcI$sp();
    }

    @Override
    public double _2$mcD$sp() {
        return this._2$mcD$sp;
    }

    @Override
    public double _2() {
        return this._2$mcD$sp();
    }

    @Override
    public Tuple2<Object, Object> swap() {
        return this.swap$mcID$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcID$sp() {
        return new Tuple2$mcDI$sp(this._2$mcD$sp(), this._1$mcI$sp());
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
    public <T1, T2> double copy$default$2() {
        return this._2$mcD$sp();
    }

    @Override
    public <T1, T2> double copy$default$2$mcD$sp() {
        return this._2$mcD$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public Tuple2$mcID$sp(int _1$mcI$sp, double _2$mcD$sp) {
        this._1$mcI$sp = _1$mcI$sp;
        this._2$mcD$sp = _2$mcD$sp;
        super(null, null);
    }
}

