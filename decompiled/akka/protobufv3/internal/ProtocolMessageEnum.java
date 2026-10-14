/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.Descriptors;
import akka.protobufv3.internal.Internal;

public interface ProtocolMessageEnum
extends Internal.EnumLite {
    @Override
    public int getNumber();

    public Descriptors.EnumValueDescriptor getValueDescriptor();

    public Descriptors.EnumDescriptor getDescriptorForType();
}

