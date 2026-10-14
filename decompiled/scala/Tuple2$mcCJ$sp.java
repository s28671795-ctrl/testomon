/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;
import scala.Tuple2$mcJC$sp;

public final class Tuple2$mcCJ$sp
extends Tuple2<Object, Object> {
    public final char _1$mcC$sp;
    public final long _2$mcJ$sp;

    @Override
    public char _1$mcC$sp() {
        return this._1$mcC$sp;
    }

    @Override
    public char _1() {
        return this._1$mcC$sp();
    }

    @Override
    public long _2$mcJ$sp() {
        return this._2$mcJ$sp;
    }

    @Override
    public long _2() {
        return this._2$mcJ$sp();
    }

    @Override
    public Tuple2<Object, Object> swap() {
        return this.swap$mcCJ$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcCJ$sp() {
        return new Tuple2$mcJC$sp(this._2$mcJ$sp(), this._1$mcC$sp());
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
    public <T1, T2> long copy$default$2() {
        return this._2$mcJ$sp();
    }

    @Override
    public <T1, T2> long copy$default$2$mcJ$sp() {
        return this._2$mcJ$sp();
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public Tuple2$mcCJ$sp(char _1$mcC$sp, long _2$mcJ$sp) {
        this._1$mcC$sp = _1$mcC$sp;
        this._2$mcJ$sp = _2$mcJ$sp;
        super(null, null);
    }
}

