/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Product2$mcDI$sp;
import scala.Tuple2;
import scala.Tuple2$mcID$sp;

public final class Tuple2$mcDI$sp
extends Tuple2<Object, Object>
implements Product2$mcDI$sp {
    public final double _1$mcD$sp;
    public final int _2$mcI$sp;

    @Override
    public double _1$mcD$sp() {
        return this._1$mcD$sp;
    }

    @Override
    public double _1() {
        return this._1$mcD$sp();
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
        return this.swap$mcDI$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcDI$sp() {
        return new Tuple2$mcID$sp(this._2$mcI$sp(), this._1$mcD$sp());
    }

    @Override
    public <T1, T2> double copy$default$1() {
        return this._1$mcD$sp();
    }

    @Override
    public <T1, T2> double copy$default$1$mcD$sp() {
        return this._1$mcD$sp();
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

    public Tuple2$mcDI$sp(double _1$mcD$sp, int _2$mcI$sp) {
        this._1$mcD$sp = _1$mcD$sp;
        this._2$mcI$sp = _2$mcI$sp;
        super(null, null);
    }
}

