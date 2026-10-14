/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.Descriptors;
import akka.protobufv3.internal.Message;
import akka.protobufv3.internal.MessageLiteOrBuilder;
import akka.protobufv3.internal.UnknownFieldSet;
import java.util.List;
import java.util.Map;

public interface MessageOrBuilder
extends MessageLiteOrBuilder {
    @Override
    public Message getDefaultInstanceForType();

    public List<String> findInitializationErrors();

    public String getInitializationErrorString();

    public Descriptors.Descriptor getDescriptorForType();

    public Map<Descriptors.FieldDescriptor, Object> getAllFields();

    public boolean hasOneof(Descriptors.OneofDescriptor var1);

    public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor var1);

    public boolean hasField(Descriptors.FieldDescriptor var1);

    public Object getField(Descriptors.FieldDescriptor var1);

    public int getRepeatedFieldCount(Descriptors.FieldDescriptor var1);

    public Object getRepeatedField(Descriptors.FieldDescriptor var1, int var2);

    public UnknownFieldSet getUnknownFields();
}

