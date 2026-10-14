/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ExtendedActorSystem;
import akka.japi.Function;
import akka.serialization.SerializationSetup;
import akka.serialization.SerializerDetails;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.util.List;
import scala.Function1;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;

public final class SerializationSetup$ {
    public static final SerializationSetup$ MODULE$ = new SerializationSetup$();

    public SerializationSetup apply(Function1<ExtendedActorSystem, Seq<SerializerDetails>> createSerializers) {
        return new SerializationSetup(createSerializers);
    }

    public SerializationSetup create(Function<ExtendedActorSystem, List<SerializerDetails>> createSerializers) {
        return this.apply((Function1<ExtendedActorSystem, Seq<SerializerDetails>>)(Function1<ExtendedActorSystem, Vector> & Serializable)sys -> package$JavaConverters$.MODULE$.ListHasAsScala((List)createSerializers.apply((ExtendedActorSystem)sys)).asScala().toVector());
    }

    private SerializationSetup$() {
    }
}

