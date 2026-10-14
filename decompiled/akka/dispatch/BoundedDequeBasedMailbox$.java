/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.BoundedDequeBasedMailbox;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class BoundedDequeBasedMailbox$
implements Serializable {
    public static final BoundedDequeBasedMailbox$ MODULE$ = new BoundedDequeBasedMailbox$();

    public BoundedDequeBasedMailbox apply(int capacity, FiniteDuration pushTimeOut) {
        return new BoundedDequeBasedMailbox(capacity, pushTimeOut);
    }

    public Option<Tuple2<Object, FiniteDuration>> unapply(BoundedDequeBasedMailbox x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, FiniteDuration>>(new Tuple2<Integer, FiniteDuration>(BoxesRunTime.boxToInteger(x$0.capacity()), x$0.pushTimeOut()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(BoundedDequeBasedMailbox$.class);
    }

    private BoundedDequeBasedMailbox$() {
    }
}

