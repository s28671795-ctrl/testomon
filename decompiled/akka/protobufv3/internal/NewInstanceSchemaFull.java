/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.GeneratedMessageV3;
import akka.protobufv3.internal.NewInstanceSchema;

final class NewInstanceSchemaFull
implements NewInstanceSchema {
    NewInstanceSchemaFull() {
    }

    @Override
    public Object newInstance(Object defaultInstance) {
        return ((GeneratedMessageV3)defaultInstance).newInstance(GeneratedMessageV3.UnusedPrivateParameter.INSTANCE);
    }
}

