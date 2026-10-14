/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ExtensionSchema;
import akka.protobufv3.internal.ExtensionSchemaLite;

final class ExtensionSchemas {
    private static final ExtensionSchema<?> LITE_SCHEMA = new ExtensionSchemaLite();
    private static final ExtensionSchema<?> FULL_SCHEMA = ExtensionSchemas.loadSchemaForFullRuntime();

    ExtensionSchemas() {
    }

    private static ExtensionSchema<?> loadSchemaForFullRuntime() {
        try {
            Class<?> clazz = Class.forName("akka.protobufv3.internal.ExtensionSchemaFull");
            return (ExtensionSchema)clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            return null;
        }
    }

    static ExtensionSchema<?> lite() {
        return LITE_SCHEMA;
    }

    static ExtensionSchema<?> full() {
        if (FULL_SCHEMA == null) {
            throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
        }
        return FULL_SCHEMA;
    }
}

