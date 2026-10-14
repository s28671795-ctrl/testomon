/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Product2$mcJJ$sp;
import scala.Tuple2;

public final class Tuple2$mcJJ$sp
extends Tuple2<Object, Object>
implements Product2$mcJJ$sp {
    public final long _1$mcJ$sp;
    public final long _2$mcJ$sp;

    @Override
    public long _1$mcJ$sp() {
        return this._1$mcJ$sp;
    }

    @Override
    public long _1() {
        return this._1$mcJ$sp();
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
        return this.swap$mcJJ$sp();
    }

    @Override
    public Tuple2<Object, Object> swap$mcJJ$sp() {
        return new Tuple2$mcJJ$sp(this._2$mcJ$sp(), this._1$mcJ$sp());
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

    public Tuple2$mcJJ$sp(long _1$mcJ$sp, long _2$mcJ$sp) {
        this._1$mcJ$sp = _1$mcJ$sp;
        this._2$mcJ$sp = _2$mcJ$sp;
        super(null, null);
    }
}

