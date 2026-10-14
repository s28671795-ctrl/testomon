/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;
import scala.Tuple2$mcCI$sp;

public final class Tuple2$mcIC$sp
extends Tuple2<Object, Object> {
    public final int _1$mcI$sp;
    public final char _2$mcC$sp;

    @Override
    public int _1$mcI$sp() {
        return this._1$mcI$sp;
    }

    @Override
    public int _1() {
        return this._1$mcI$sp();
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
        return this.swap$mcIC$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcIC$sp() {
        return new Tuple2$mcCI$sp(this._2$mcC$sp(), this._1$mcI$sp());
    }

    @Override
    public <T1, T2> int copy$default$1() {
        return this._1$mcI$sp();
    }

    @Override
    public <T1, T2> int copy$default$1$mcI$sp() {
        return this._1$mcI$sp();
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

    public Tuple2$mcIC$sp(int _1$mcI$sp, char _2$mcC$sp) {
        this._1$mcI$sp = _1$mcI$sp;
        this._2$mcC$sp = _2$mcC$sp;
        super(null, null);
    }
}

