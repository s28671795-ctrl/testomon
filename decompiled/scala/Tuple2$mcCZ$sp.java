/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;
import scala.Tuple2$mcZC$sp;

public final class Tuple2$mcCZ$sp
extends Tuple2<Object, Object> {
    public final char _1$mcC$sp;
    public final boolean _2$mcZ$sp;

    @Override
    public char _1$mcC$sp() {
        return this._1$mcC$sp;
    }

    @Override
    public char _1() {
        return this._1$mcC$sp();
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
        return this.swap$mcCZ$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcCZ$sp() {
        return new Tuple2$mcZC$sp(this._2$mcZ$sp(), this._1$mcC$sp());
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

    public Tuple2$mcCZ$sp(char _1$mcC$sp, boolean _2$mcZ$sp) {
        this._1$mcC$sp = _1$mcC$sp;
        this._2$mcZ$sp = _2$mcZ$sp;
        super(null, null);
    }
}

