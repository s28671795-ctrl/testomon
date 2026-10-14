/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.BoundedMailbox;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class BoundedMailbox$
implements Serializable {
    public static final BoundedMailbox$ MODULE$ = new BoundedMailbox$();

    public BoundedMailbox apply(int capacity, FiniteDuration pushTimeOut) {
        return new BoundedMailbox(capacity, pushTimeOut);
    }

    public Option<Tuple2<Object, FiniteDuration>> unapply(BoundedMailbox x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, FiniteDuration>>(new Tuple2<Integer, FiniteDuration>(BoxesRunTime.boxToInteger(x$0.capacity()), x$0.pushTimeOut()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(BoundedMailbox$.class);
    }

    private BoundedMailbox$() {
    }
}

