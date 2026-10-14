/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.Done;
import akka.Done$;
import akka.stream.IOResult;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

public final class IOResult$
implements Serializable {
    public static final IOResult$ MODULE$ = new IOResult$();

    public IOResult apply(long count) {
        return new IOResult(count, new Success<Done>(Done$.MODULE$));
    }

    public IOResult createSuccessful(long count) {
        return new IOResult(count, new Success<Done>(Done$.MODULE$));
    }

    public IOResult createFailed(long count, Throwable ex) {
        return new IOResult(count, new Failure<Done>(ex));
    }

    public IOResult apply(long count, Try<Done> status) {
        return new IOResult(count, status);
    }

    public Option<Tuple2<Object, Try<Done>>> unapply(IOResult x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, Try<Done>>>(new Tuple2<Long, Try<Done>>(BoxesRunTime.boxToLong(x$0.count()), x$0.status()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(IOResult$.class);
    }

    private IOResult$() {
    }
}

