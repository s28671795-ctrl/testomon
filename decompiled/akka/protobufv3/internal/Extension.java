/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.Descriptors;
import akka.protobufv3.internal.ExtensionLite;
import akka.protobufv3.internal.Message;
import akka.protobufv3.internal.MessageLite;

public abstract class Extension<ContainingType extends MessageLite, Type>
extends ExtensionLite<ContainingType, Type> {
    @Override
    public abstract Message getMessageDefaultInstance();

    public abstract Descriptors.FieldDescriptor getDescriptor();

    @Override
    final boolean isLite() {
        return false;
    }

    protected abstract ExtensionType getExtensionType();

    public MessageType getMessageType() {
        return MessageType.PROTO2;
    }

    protected abstract Object fromReflectionType(Object var1);

    protected abstract Object singularFromReflectionType(Object var1);

    protected abstract Object toReflectionType(Object var1);

    protected abstract Object singularToReflectionType(Object var1);

    public static enum MessageType {
        PROTO1,
        PROTO2;

    }

    protected static enum ExtensionType {
        IMMUTABLE,
        MUTABLE,
        PROTO1;

    }
}

