/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.serialization.Serializer;
import akka.serialization.SerializerDetails;
import akka.util.ccompat.package$JavaConverters$;
import java.util.List;
import scala.collection.immutable.Seq;

public final class SerializerDetails$ {
    public static final SerializerDetails$ MODULE$ = new SerializerDetails$();

    public SerializerDetails apply(String alias, Serializer serializer, Seq<Class<?>> useFor) {
        return new SerializerDetails(alias, serializer, useFor);
    }

    public SerializerDetails create(String alias, Serializer serializer, List<Class<?>> useFor) {
        return this.apply(alias, serializer, package$JavaConverters$.MODULE$.ListHasAsScala(useFor).asScala().toVector());
    }

    private SerializerDetails$() {
    }
}

