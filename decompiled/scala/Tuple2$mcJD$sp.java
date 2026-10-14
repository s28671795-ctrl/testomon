/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Product2$mcJD$sp;
import scala.Tuple2;
import scala.Tuple2$mcDJ$sp;

public final class Tuple2$mcJD$sp
extends Tuple2<Object, Object>
implements Product2$mcJD$sp {
    public final long _1$mcJ$sp;
    public final double _2$mcD$sp;

    @Override
    public long _1$mcJ$sp() {
        return this._1$mcJ$sp;
    }

    @Override
    public long _1() {
        return this._1$mcJ$sp();
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
        return this.swap$mcJD$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcJD$sp() {
        return new Tuple2$mcDJ$sp(this._2$mcD$sp(), this._1$mcJ$sp());
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

    public Tuple2$mcJD$sp(long _1$mcJ$sp, double _2$mcD$sp) {
        this._1$mcJ$sp = _1$mcJ$sp;
        this._2$mcD$sp = _2$mcD$sp;
        super(null, null);
    }
}

