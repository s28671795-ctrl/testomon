/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;
import scala.Tuple2$mcCZ$sp;

public final class Tuple2$mcZC$sp
extends Tuple2<Object, Object> {
    public final boolean _1$mcZ$sp;
    public final char _2$mcC$sp;

    @Override
    public boolean _1$mcZ$sp() {
        return this._1$mcZ$sp;
    }

    @Override
    public boolean _1() {
        return this._1$mcZ$sp();
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
        return this.swap$mcZC$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcZC$sp() {
        return new Tuple2$mcCZ$sp(this._2$mcC$sp(), this._1$mcZ$sp());
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

    public Tuple2$mcZC$sp(boolean _1$mcZ$sp, char _2$mcC$sp) {
        this._1$mcZ$sp = _1$mcZ$sp;
        this._2$mcC$sp = _2$mcC$sp;
        super(null, null);
    }
}

