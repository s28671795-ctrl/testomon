/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;
import scala.util.Failure;

public final class Failure$
implements Serializable {
    public static final Failure$ MODULE$ = new Failure$();

    public final String toString() {
        return "Failure";
    }

    public <T> Failure<T> apply(Throwable exception) {
        return new Failure(exception);
    }

    public <T> Option<Throwable> unapply(Failure<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Throwable>(x$0.exception());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Failure$.class);
    }

    private Failure$() {
    }
}

