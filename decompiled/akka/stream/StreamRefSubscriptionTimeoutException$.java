/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.StreamRefSubscriptionTimeoutException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class StreamRefSubscriptionTimeoutException$
extends AbstractFunction1<String, StreamRefSubscriptionTimeoutException>
implements Serializable {
    public static final StreamRefSubscriptionTimeoutException$ MODULE$ = new StreamRefSubscriptionTimeoutException$();

    @Override
    public final String toString() {
        return "StreamRefSubscriptionTimeoutException";
    }

    @Override
    public StreamRefSubscriptionTimeoutException apply(String msg) {
        return new StreamRefSubscriptionTimeoutException(msg);
    }

    public Option<String> unapply(StreamRefSubscriptionTimeoutException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<String>(x$0.msg());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(StreamRefSubscriptionTimeoutException$.class);
    }

    private StreamRefSubscriptionTimeoutException$() {
    }
}

