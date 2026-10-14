/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.IgnoreActorRef$;
import akka.annotation.InternalApi;
import java.io.ObjectStreamException;
import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class SerializedIgnore$
implements Serializable {
    public static final SerializedIgnore$ MODULE$ = new SerializedIgnore$();
    private static final long serialVersionUID = 1L;

    private Object readResolve() throws ObjectStreamException {
        return IgnoreActorRef$.MODULE$;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SerializedIgnore$.class);
    }

    private SerializedIgnore$() {
    }
}

