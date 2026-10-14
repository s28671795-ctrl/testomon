/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;
import scala.Tuple2$mcCD$sp;

public final class Tuple2$mcDC$sp
extends Tuple2<Object, Object> {
    public final double _1$mcD$sp;
    public final char _2$mcC$sp;

    @Override
    public double _1$mcD$sp() {
        return this._1$mcD$sp;
    }

    @Override
    public double _1() {
        return this._1$mcD$sp();
    }

    @Override
    public char _2$mcC$sp() {
        return this._2$mcC$sp;
    }

    @Override
    public char _2() {
        return this._2$mcC$sp();
    }

    @Override
    public Tuple2<Object, Object> swap() {
        return this.swap$mcDC$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcDC$sp() {
        return new Tuple2$mcCD$sp(this._2$mcC$sp(), this._1$mcD$sp());
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
    public <T1, T2> char copy$default$2() {
        return this._2$mcC$sp();
    }

    @Override
    public <T1, T2> char copy$default$2$mcC$sp() {
        return this._2$mcC$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public Tuple2$mcDC$sp(double _1$mcD$sp, char _2$mcC$sp) {
        this._1$mcD$sp = _1$mcD$sp;
        this._2$mcC$sp = _2$mcC$sp;
        super(null, null);
    }
}

