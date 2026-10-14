/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.Compose;
import java.io.Serializable;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

public final class Compose$
extends AbstractFunction2<Function2<Nothing$, Nothing$, Object>, Object, Compose>
implements Serializable {
    public static final Compose$ MODULE$ = new Compose$();

    public boolean $lessinit$greater$default$2() {
        return false;
    }

    @Override
    public final String toString() {
        return "Compose";
    }

    @Override
    public Compose apply(Function2<Nothing$, Nothing$, Object> composer, boolean reverse) {
        return new Compose(composer, reverse);
    }

    public boolean apply$default$2() {
        return false;
    }

    public Option<Tuple2<Function2<Nothing$, Nothing$, Object>, Object>> unapply(Compose x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Function2<Nothing$, Nothing$, Object>, Object>>(new Tuple2<Function2<Nothing$, Nothing$, Object>, Boolean>(x$0.composer(), BoxesRunTime.boxToBoolean(x$0.reverse())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Compose$.class);
    }

    private Compose$() {
    }
}

