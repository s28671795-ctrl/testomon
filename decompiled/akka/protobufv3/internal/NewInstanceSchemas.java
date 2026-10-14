/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.NewInstanceSchema;
import akka.protobufv3.internal.NewInstanceSchemaLite;

final class NewInstanceSchemas {
    private static final NewInstanceSchema FULL_SCHEMA = NewInstanceSchemas.loadSchemaForFullRuntime();
    private static final NewInstanceSchema LITE_SCHEMA = new NewInstanceSchemaLite();

    NewInstanceSchemas() {
    }

    static NewInstanceSchema full() {
        return FULL_SCHEMA;
    }

    static NewInstanceSchema lite() {
        return LITE_SCHEMA;
    }

    private static NewInstanceSchema loadSchemaForFullRuntime() {
        try {
            Class<?> clazz = Class.forName("akka.protobufv3.internal.NewInstanceSchemaFull");
            return (NewInstanceSchema)clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            return null;
        }
    }
}

