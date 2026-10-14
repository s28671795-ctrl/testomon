/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;
import scala.Tuple2$mcZD$sp;

public final class Tuple2$mcDZ$sp
extends Tuple2<Object, Object> {
    public final double _1$mcD$sp;
    public final boolean _2$mcZ$sp;

    @Override
    public double _1$mcD$sp() {
        return this._1$mcD$sp;
    }

    @Override
    public double _1() {
        return this._1$mcD$sp();
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
        return this.swap$mcDZ$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcDZ$sp() {
        return new Tuple2$mcZD$sp(this._2$mcZ$sp(), this._1$mcD$sp());
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

    public Tuple2$mcDZ$sp(double _1$mcD$sp, boolean _2$mcZ$sp) {
        this._1$mcD$sp = _1$mcD$sp;
        this._2$mcZ$sp = _2$mcZ$sp;
        super(null, null);
    }
}

