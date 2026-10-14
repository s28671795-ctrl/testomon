/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.ErrorPublisher;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class ErrorPublisher$
extends AbstractFunction2<Throwable, String, ErrorPublisher>
implements Serializable {
    public static final ErrorPublisher$ MODULE$ = new ErrorPublisher$();

    @Override
    public final String toString() {
        return "ErrorPublisher";
    }

    @Override
    public ErrorPublisher apply(Throwable t2, String name) {
        return new ErrorPublisher(t2, name);
    }

    public Option<Tuple2<Throwable, String>> unapply(ErrorPublisher x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Throwable, String>>(new Tuple2<Throwable, String>(x$0.t(), x$0.name()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ErrorPublisher$.class);
    }

    private ErrorPublisher$() {
    }
}

