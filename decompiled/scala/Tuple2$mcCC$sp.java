/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;

public final class Tuple2$mcCC$sp
extends Tuple2<Object, Object> {
    public final char _1$mcC$sp;
    public final char _2$mcC$sp;

    @Override
    public char _1$mcC$sp() {
        return this._1$mcC$sp;
    }

    @Override
    public char _1() {
        return this._1$mcC$sp();
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
        return this.swap$mcCC$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcCC$sp() {
        return new Tuple2$mcCC$sp(this._2$mcC$sp(), this._1$mcC$sp());
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

    public Tuple2$mcCC$sp(char _1$mcC$sp, char _2$mcC$sp) {
        this._1$mcC$sp = _1$mcC$sp;
        this._2$mcC$sp = _2$mcC$sp;
        super(null, null);
    }
}

