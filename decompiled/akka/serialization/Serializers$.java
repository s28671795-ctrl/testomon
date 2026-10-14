/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.serialization.Serializer;
import akka.serialization.SerializerWithStringManifest;

public final class Serializers$ {
    public static final Serializers$ MODULE$ = new Serializers$();

    public String manifestFor(Serializer s2, Object message) {
        Serializer serializer = s2;
        if (serializer instanceof SerializerWithStringManifest) {
            SerializerWithStringManifest serializerWithStringManifest = (SerializerWithStringManifest)serializer;
            return serializerWithStringManifest.manifest(message);
        }
        if (s2.includeManifest()) {
            return message.getClass().getName();
        }
        return "";
    }

    private Serializers$() {
    }
}

