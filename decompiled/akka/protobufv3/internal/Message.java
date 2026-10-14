/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.CodedInputStream;
import akka.protobufv3.internal.Descriptors;
import akka.protobufv3.internal.ExtensionRegistryLite;
import akka.protobufv3.internal.InvalidProtocolBufferException;
import akka.protobufv3.internal.MessageLite;
import akka.protobufv3.internal.MessageOrBuilder;
import akka.protobufv3.internal.Parser;
import akka.protobufv3.internal.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;

public interface Message
extends MessageLite,
MessageOrBuilder {
    public Parser<? extends Message> getParserForType();

    public boolean equals(Object var1);

    public int hashCode();

    public String toString();

    @Override
    public Builder newBuilderForType();

    @Override
    public Builder toBuilder();

    public static interface Builder
    extends MessageLite.Builder,
    MessageOrBuilder {
        @Override
        public Builder clear();

        public Builder mergeFrom(Message var1);

        @Override
        public Message build();

        @Override
        public Message buildPartial();

        @Override
        public Builder clone();

        @Override
        public Builder mergeFrom(CodedInputStream var1) throws IOException;

        @Override
        public Builder mergeFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws IOException;

        @Override
        public Descriptors.Descriptor getDescriptorForType();

        public Builder newBuilderForField(Descriptors.FieldDescriptor var1);

        public Builder getFieldBuilder(Descriptors.FieldDescriptor var1);

        public Builder getRepeatedFieldBuilder(Descriptors.FieldDescriptor var1, int var2);

        public Builder setField(Descriptors.FieldDescriptor var1, Object var2);

        public Builder clearField(Descriptors.FieldDescriptor var1);

        public Builder clearOneof(Descriptors.OneofDescriptor var1);

        public Builder setRepeatedField(Descriptors.FieldDescriptor var1, int var2, Object var3);

        public Builder addRepeatedField(Descriptors.FieldDescriptor var1, Object var2);

        public Builder setUnknownFields(UnknownFieldSet var1);

        public Builder mergeUnknownFields(UnknownFieldSet var1);

        @Override
        public Builder mergeFrom(ByteString var1) throws InvalidProtocolBufferException;

        @Override
        public Builder mergeFrom(ByteString var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

        @Override
        public Builder mergeFrom(byte[] var1) throws InvalidProtocolBufferException;

        @Override
        public Builder mergeFrom(byte[] var1, int var2, int var3) throws InvalidProtocolBufferException;

        @Override
        public Builder mergeFrom(byte[] var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

        @Override
        public Builder mergeFrom(byte[] var1, int var2, int var3, ExtensionRegistryLite var4) throws InvalidProtocolBufferException;

        @Override
        public Builder mergeFrom(InputStream var1) throws IOException;

        @Override
        public Builder mergeFrom(InputStream var1, ExtensionRegistryLite var2) throws IOException;

        @Override
        public boolean mergeDelimitedFrom(InputStream var1) throws IOException;

        @Override
        public boolean mergeDelimitedFrom(InputStream var1, ExtensionRegistryLite var2) throws IOException;
    }
}

