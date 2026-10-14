/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.MapFieldSchema;
import akka.protobufv3.internal.MapFieldSchemaLite;

final class MapFieldSchemas {
    private static final MapFieldSchema FULL_SCHEMA = MapFieldSchemas.loadSchemaForFullRuntime();
    private static final MapFieldSchema LITE_SCHEMA = new MapFieldSchemaLite();

    MapFieldSchemas() {
    }

    static MapFieldSchema full() {
        return FULL_SCHEMA;
    }

    static MapFieldSchema lite() {
        return LITE_SCHEMA;
    }

    private static MapFieldSchema loadSchemaForFullRuntime() {
        try {
            Class<?> clazz = Class.forName("akka.protobufv3.internal.MapFieldSchemaFull");
            return (MapFieldSchema)clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            return null;
        }
    }
}

