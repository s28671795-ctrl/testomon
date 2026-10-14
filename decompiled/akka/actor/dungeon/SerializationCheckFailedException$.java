/*
 * Decompiled with CFR 0.152.
 */
package akka.actor.dungeon;

import akka.actor.dungeon.SerializationCheckFailedException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class SerializationCheckFailedException$
extends AbstractFunction2<Object, Throwable, SerializationCheckFailedException>
implements Serializable {
    public static final SerializationCheckFailedException$ MODULE$ = new SerializationCheckFailedException$();

    @Override
    public final String toString() {
        return "SerializationCheckFailedException";
    }

    @Override
    public SerializationCheckFailedException apply(Object msg, Throwable cause) {
        return new SerializationCheckFailedException(msg, cause);
    }

    public Option<Tuple2<Object, Throwable>> unapply(SerializationCheckFailedException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, Throwable>>(new Tuple2<Object, Throwable>(x$0.msg(), x$0.cause()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SerializationCheckFailedException$.class);
    }

    private SerializationCheckFailedException$() {
    }
}

