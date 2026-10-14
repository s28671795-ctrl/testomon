/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.AbruptIOTerminationException;
import akka.stream.IOResult;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class AbruptIOTerminationException$
extends AbstractFunction2<IOResult, Throwable, AbruptIOTerminationException>
implements Serializable {
    public static final AbruptIOTerminationException$ MODULE$ = new AbruptIOTerminationException$();

    @Override
    public final String toString() {
        return "AbruptIOTerminationException";
    }

    @Override
    public AbruptIOTerminationException apply(IOResult ioResult, Throwable cause) {
        return new AbruptIOTerminationException(ioResult, cause);
    }

    public Option<Tuple2<IOResult, Throwable>> unapply(AbruptIOTerminationException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<IOResult, Throwable>>(new Tuple2<IOResult, Throwable>(x$0.ioResult(), x$0.cause()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AbruptIOTerminationException$.class);
    }

    private AbruptIOTerminationException$() {
    }
}

