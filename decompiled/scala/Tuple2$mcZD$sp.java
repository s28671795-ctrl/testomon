/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;
import scala.Tuple2$mcDZ$sp;

public final class Tuple2$mcZD$sp
extends Tuple2<Object, Object> {
    public final boolean _1$mcZ$sp;
    public final double _2$mcD$sp;

    @Override
    public boolean _1$mcZ$sp() {
        return this._1$mcZ$sp;
    }

    @Override
    public boolean _1() {
        return this._1$mcZ$sp();
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
        return this.swap$mcZD$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcZD$sp() {
        return new Tuple2$mcDZ$sp(this._2$mcD$sp(), this._1$mcZ$sp());
    }

    @Override
    public <T1, T2> boolean copy$default$1() {
        return this._1$mcZ$sp();
    }

    @Override
    public <T1, T2> boolean copy$default$1$mcZ$sp() {
        return this._1$mcZ$sp();
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

    public Tuple2$mcZD$sp(boolean _1$mcZ$sp, double _2$mcD$sp) {
        this._1$mcZ$sp = _1$mcZ$sp;
        this._2$mcD$sp = _2$mcD$sp;
        super(null, null);
    }
}

