/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;
import scala.Tuple2$mcIC$sp;

public final class Tuple2$mcCI$sp
extends Tuple2<Object, Object> {
    public final char _1$mcC$sp;
    public final int _2$mcI$sp;

    @Override
    public char _1$mcC$sp() {
        return this._1$mcC$sp;
    }

    @Override
    public char _1() {
        return this._1$mcC$sp();
    }

    @Override
    public int _2$mcI$sp() {
        return this._2$mcI$sp;
    }

    @Override
    public int _2() {
        return this._2$mcI$sp();
    }

    @Override
    public Tuple2<Object, Object> swap() {
        return this.swap$mcCI$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcCI$sp() {
        return new Tuple2$mcIC$sp(this._2$mcI$sp(), this._1$mcC$sp());
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
    public <T1, T2> int copy$default$2() {
        return this._2$mcI$sp();
    }

    @Override
    public <T1, T2> int copy$default$2$mcI$sp() {
        return this._2$mcI$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public Tuple2$mcCI$sp(char _1$mcC$sp, int _2$mcI$sp) {
        this._1$mcC$sp = _1$mcC$sp;
        this._2$mcI$sp = _2$mcI$sp;
        super(null, null);
    }
}

