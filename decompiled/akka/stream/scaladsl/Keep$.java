/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.NotUsed$;
import java.io.Serializable;
import scala.Function2;
import scala.Tuple2;

public final class Keep$ {
    public static final Keep$ MODULE$ = new Keep$();
    private static final Function2<Object, Object, Object> _left = (Function2<Object, Object, Object> & Serializable)(l, x$1) -> l;
    private static final Function2<Object, Object, Object> _right = (Function2<Object, Object, Object> & Serializable)(x$2, r) -> r;
    private static final Function2<Object, Object, Tuple2<Object, Object>> _both = (Function2<Object, Object, Tuple2> & Serializable)(l, r) -> new Tuple2<Object, Object>(l, r);
    private static final Function2<Object, Object, NotUsed$> _none = (Function2<Object, Object, NotUsed$> & Serializable)(x$3, x$4) -> NotUsed$.MODULE$;

    private Function2<Object, Object, Object> _left() {
        return _left;
    }

    private Function2<Object, Object, Object> _right() {
        return _right;
    }

    private Function2<Object, Object, Tuple2<Object, Object>> _both() {
        return _both;
    }

    private Function2<Object, Object, NotUsed$> _none() {
        return _none;
    }

    public <L, R> Function2<L, R, L> left() {
        return this._left();
    }

    public <L, R> Function2<L, R, R> right() {
        return this._right();
    }

    public <L, R> Function2<L, R, Tuple2<L, R>> both() {
        return this._both();
    }

    public <L, R> Function2<L, R, NotUsed> none() {
        return this._none();
    }

    private Keep$() {
    }
}

