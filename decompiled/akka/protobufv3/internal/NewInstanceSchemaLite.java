/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.GeneratedMessageLite;
import akka.protobufv3.internal.NewInstanceSchema;

final class NewInstanceSchemaLite
implements NewInstanceSchema {
    NewInstanceSchemaLite() {
    }

    @Override
    public Object newInstance(Object defaultInstance) {
        return ((GeneratedMessageLite)defaultInstance).dynamicMethod(GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE);
    }
}

