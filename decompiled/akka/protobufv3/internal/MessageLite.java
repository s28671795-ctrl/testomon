/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.CodedInputStream;
import akka.protobufv3.internal.CodedOutputStream;
import akka.protobufv3.internal.ExtensionRegistryLite;
import akka.protobufv3.internal.InvalidProtocolBufferException;
import akka.protobufv3.internal.MessageLiteOrBuilder;
import akka.protobufv3.internal.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface MessageLite
extends MessageLiteOrBuilder {
    public void writeTo(CodedOutputStream var1) throws IOException;

    public int getSerializedSize();

    public Parser<? extends MessageLite> getParserForType();

    public ByteString toByteString();

    public byte[] toByteArray();

    public void writeTo(OutputStream var1) throws IOException;

    public void writeDelimitedTo(OutputStream var1) throws IOException;

    public Builder newBuilderForType();

    public Builder toBuilder();

    public static interface Builder
    extends MessageLiteOrBuilder,
    Cloneable {
        public Builder clear();

        public MessageLite build();

        public MessageLite buildPartial();

        public Builder clone();

        public Builder mergeFrom(CodedInputStream var1) throws IOException;

        public Builder mergeFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws IOException;

        public Builder mergeFrom(ByteString var1) throws InvalidProtocolBufferException;

        public Builder mergeFrom(ByteString var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

        public Builder mergeFrom(byte[] var1) throws InvalidProtocolBufferException;

        public Builder mergeFrom(byte[] var1, int var2, int var3) throws InvalidProtocolBufferException;

        public Builder mergeFrom(byte[] var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

        public Builder mergeFrom(byte[] var1, int var2, int var3, ExtensionRegistryLite var4) throws InvalidProtocolBufferException;

        public Builder mergeFrom(InputStream var1) throws IOException;

        public Builder mergeFrom(InputStream var1, ExtensionRegistryLite var2) throws IOException;

        public Builder mergeFrom(MessageLite var1);

        public boolean mergeDelimitedFrom(InputStream var1) throws IOException;

        public boolean mergeDelimitedFrom(InputStream var1, ExtensionRegistryLite var2) throws IOException;
    }
}

