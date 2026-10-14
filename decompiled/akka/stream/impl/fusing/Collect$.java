/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.Collect;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class Collect$
implements Serializable {
    public static final Collect$ MODULE$ = new Collect$();
    private static final Function1<Object, Object> NotApplied = (Function1<Object, Function1> & Serializable)x$1 -> MODULE$.NotApplied();

    public final Function1<Object, Object> NotApplied() {
        return NotApplied;
    }

    public <In, Out> Collect<In, Out> apply(PartialFunction<In, Out> pf) {
        return new Collect<In, Out>(pf);
    }

    public <In, Out> Option<PartialFunction<In, Out>> unapply(Collect<In, Out> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<PartialFunction<In, Out>>(x$0.pf());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Collect$.class);
    }

    private Collect$() {
    }
}

