/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Product2$mcDJ$sp;
import scala.Tuple2;
import scala.Tuple2$mcJD$sp;

public final class Tuple2$mcDJ$sp
extends Tuple2<Object, Object>
implements Product2$mcDJ$sp {
    public final double _1$mcD$sp;
    public final long _2$mcJ$sp;

    @Override
    public double _1$mcD$sp() {
        return this._1$mcD$sp;
    }

    @Override
    public double _1() {
        return this._1$mcD$sp();
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
        return this.swap$mcDJ$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcDJ$sp() {
        return new Tuple2$mcJD$sp(this._2$mcJ$sp(), this._1$mcD$sp());
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

    public Tuple2$mcDJ$sp(double _1$mcD$sp, long _2$mcJ$sp) {
        this._1$mcD$sp = _1$mcD$sp;
        this._2$mcJ$sp = _2$mcJ$sp;
        super(null, null);
    }
}

