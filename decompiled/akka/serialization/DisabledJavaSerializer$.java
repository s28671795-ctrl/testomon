/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ExtendedActorSystem;
import akka.serialization.DisabledJavaSerializer;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class DisabledJavaSerializer$
implements Serializable {
    public static final DisabledJavaSerializer$ MODULE$ = new DisabledJavaSerializer$();
    private static final DisabledJavaSerializer.JavaSerializationException IllegalSerialization = new DisabledJavaSerializer.JavaSerializationException("Attempted to serialize message using Java serialization while `akka.actor.allow-java-serialization` was disabled. Check WARNING logs for more details.");
    private static final DisabledJavaSerializer.JavaSerializationException IllegalDeserialization = new DisabledJavaSerializer.JavaSerializationException("Attempted to deserialize message using Java serialization while `akka.actor.allow-java-serialization` was disabled. Check WARNING logs for more details.");

    public final DisabledJavaSerializer.JavaSerializationException IllegalSerialization() {
        return IllegalSerialization;
    }

    public final DisabledJavaSerializer.JavaSerializationException IllegalDeserialization() {
        return IllegalDeserialization;
    }

    public DisabledJavaSerializer apply(ExtendedActorSystem system) {
        return new DisabledJavaSerializer(system);
    }

    public Option<ExtendedActorSystem> unapply(DisabledJavaSerializer x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ExtendedActorSystem>(x$0.system());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DisabledJavaSerializer$.class);
    }

    private DisabledJavaSerializer$() {
    }
}

