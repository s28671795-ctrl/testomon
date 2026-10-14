/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.RemoteStreamRefActorTerminatedException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class RemoteStreamRefActorTerminatedException$
extends AbstractFunction1<String, RemoteStreamRefActorTerminatedException>
implements Serializable {
    public static final RemoteStreamRefActorTerminatedException$ MODULE$ = new RemoteStreamRefActorTerminatedException$();

    @Override
    public final String toString() {
        return "RemoteStreamRefActorTerminatedException";
    }

    @Override
    public RemoteStreamRefActorTerminatedException apply(String msg) {
        return new RemoteStreamRefActorTerminatedException(msg);
    }

    public Option<String> unapply(RemoteStreamRefActorTerminatedException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<String>(x$0.msg());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RemoteStreamRefActorTerminatedException$.class);
    }

    private RemoteStreamRefActorTerminatedException$() {
    }
}

