/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.Schema;

interface SchemaFactory {
    public <T> Schema<T> createSchema(Class<T> var1);
}

