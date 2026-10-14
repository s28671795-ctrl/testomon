/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;
import scala.Tuple2$mcDC$sp;

public final class Tuple2$mcCD$sp
extends Tuple2<Object, Object> {
    public final char _1$mcC$sp;
    public final double _2$mcD$sp;

    @Override
    public char _1$mcC$sp() {
        return this._1$mcC$sp;
    }

    @Override
    public char _1() {
        return this._1$mcC$sp();
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
        return this.swap$mcCD$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcCD$sp() {
        return new Tuple2$mcDC$sp(this._2$mcD$sp(), this._1$mcC$sp());
    }

    @Override
    public <T1, T2> char copy$default$1() {
        return this._1$mcC$sp();
    }

    @Override
    public <T1, T2> char copy$default$1$mcC$sp() {
        return this._1$mcC$sp();
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

    public Tuple2$mcCD$sp(char _1$mcC$sp, double _2$mcD$sp) {
        this._1$mcC$sp = _1$mcC$sp;
        this._2$mcD$sp = _2$mcD$sp;
        super(null, null);
    }
}

