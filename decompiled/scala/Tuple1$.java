/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple1;
import scala.Tuple1$mcD$sp;
import scala.Tuple1$mcI$sp;
import scala.Tuple1$mcJ$sp;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple1$
implements Serializable {
    public static final Tuple1$ MODULE$ = new Tuple1$();

    public final String toString() {
        return "Tuple1";
    }

    public <T1> Tuple1<T1> apply(T1 _1) {
        return new Tuple1<T1>(_1);
    }

    public <T1> Option<T1> unapply(Tuple1<T1> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<T1>(x$0._1());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple1$.class);
    }

    public Tuple1<Object> apply$mDc$sp(double _1) {
        return new Tuple1$mcD$sp(_1);
    }

    public Tuple1<Object> apply$mIc$sp(int _1) {
        return new Tuple1$mcI$sp(_1);
    }

    public Tuple1<Object> apply$mJc$sp(long _1) {
        return new Tuple1$mcJ$sp(_1);
    }

    public Option<Object> unapply$mDc$sp(Tuple1<Object> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Object>(x$0._1$mcD$sp());
    }

    public Option<Object> unapply$mIc$sp(Tuple1<Object> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Object>(x$0._1$mcI$sp());
    }

    public Option<Object> unapply$mJc$sp(Tuple1<Object> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Object>(x$0._1$mcJ$sp());
    }

    private Tuple1$() {
    }
}

