/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.Recover;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class Recover$
implements Serializable {
    public static final Recover$ MODULE$ = new Recover$();

    public final String toString() {
        return "Recover";
    }

    public <T> Recover<T> apply(PartialFunction<Throwable, T> pf) {
        return new Recover<T>(pf);
    }

    public <T> Option<PartialFunction<Throwable, T>> unapply(Recover<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<PartialFunction<Throwable, T>>(x$0.pf());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Recover$.class);
    }

    private Recover$() {
    }
}

