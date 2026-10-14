/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Tuple2;
import scala.Tuple2$mcCJ$sp;

public final class Tuple2$mcJC$sp
extends Tuple2<Object, Object> {
    public final long _1$mcJ$sp;
    public final char _2$mcC$sp;

    @Override
    public long _1$mcJ$sp() {
        return this._1$mcJ$sp;
    }

    @Override
    public long _1() {
        return this._1$mcJ$sp();
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
        return this.swap$mcJC$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcJC$sp() {
        return new Tuple2$mcCJ$sp(this._2$mcC$sp(), this._1$mcJ$sp());
    }

    @Override
    public <T1, T2> long copy$default$1() {
        return this._1$mcJ$sp();
    }

    @Override
    public <T1, T2> long copy$default$1$mcJ$sp() {
        return this._1$mcJ$sp();
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

    public Tuple2$mcJC$sp(long _1$mcJ$sp, char _2$mcC$sp) {
        this._1$mcJ$sp = _1$mcJ$sp;
        this._2$mcC$sp = _2$mcC$sp;
        super(null, null);
    }
}

