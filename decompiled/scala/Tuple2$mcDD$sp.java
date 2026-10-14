/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Product2$mcDD$sp;
import scala.Tuple2;

public final class Tuple2$mcDD$sp
extends Tuple2<Object, Object>
implements Product2$mcDD$sp {
    public final double _1$mcD$sp;
    public final double _2$mcD$sp;

    @Override
    public double _1$mcD$sp() {
        return this._1$mcD$sp;
    }

    @Override
    public double _1() {
        return this._1$mcD$sp();
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
        return this.swap$mcDD$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcDD$sp() {
        return new Tuple2$mcDD$sp(this._2$mcD$sp(), this._1$mcD$sp());
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

    public Tuple2$mcDD$sp(double _1$mcD$sp, double _2$mcD$sp) {
        this._1$mcD$sp = _1$mcD$sp;
        this._2$mcD$sp = _2$mcD$sp;
        super(null, null);
    }
}

