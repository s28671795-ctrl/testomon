/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.streamref;

import akka.stream.impl.streamref.StreamRefSettingsImpl;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.AbstractFunction4;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class StreamRefSettingsImpl$
extends AbstractFunction4<Object, FiniteDuration, FiniteDuration, FiniteDuration, StreamRefSettingsImpl>
implements Serializable {
    public static final StreamRefSettingsImpl$ MODULE$ = new StreamRefSettingsImpl$();

    @Override
    public final String toString() {
        return "StreamRefSettingsImpl";
    }

    @Override
    public StreamRefSettingsImpl apply(int bufferCapacity, FiniteDuration demandRedeliveryInterval, FiniteDuration subscriptionTimeout, FiniteDuration finalTerminationSignalDeadline) {
        return new StreamRefSettingsImpl(bufferCapacity, demandRedeliveryInterval, subscriptionTimeout, finalTerminationSignalDeadline);
    }

    public Option<Tuple4<Object, FiniteDuration, FiniteDuration, FiniteDuration>> unapply(StreamRefSettingsImpl x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<Object, FiniteDuration, FiniteDuration, FiniteDuration>>(new Tuple4<Integer, FiniteDuration, FiniteDuration, FiniteDuration>(BoxesRunTime.boxToInteger(x$0.bufferCapacity()), x$0.demandRedeliveryInterval(), x$0.subscriptionTimeout(), x$0.finalTerminationSignalDeadline()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(StreamRefSettingsImpl$.class);
    }

    private StreamRefSettingsImpl$() {
    }
}

