/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.ActorSubscription;
import akka.stream.impl.Cancel;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class Cancel$
implements Serializable {
    public static final Cancel$ MODULE$ = new Cancel$();

    public final String toString() {
        return "Cancel";
    }

    public <T> Cancel<T> apply(ActorSubscription<T> subscription) {
        return new Cancel<T>(subscription);
    }

    public <T> Option<ActorSubscription<T>> unapply(Cancel<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorSubscription<T>>(x$0.subscription());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Cancel$.class);
    }

    private Cancel$() {
    }
}

