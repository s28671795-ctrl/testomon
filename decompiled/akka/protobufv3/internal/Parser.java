/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.CodedInputStream;
import akka.protobufv3.internal.ExtensionRegistryLite;
import akka.protobufv3.internal.InvalidProtocolBufferException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public interface Parser<MessageType> {
    public MessageType parseFrom(CodedInputStream var1) throws InvalidProtocolBufferException;

    public MessageType parseFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(CodedInputStream var1) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parseFrom(ByteBuffer var1) throws InvalidProtocolBufferException;

    public MessageType parseFrom(ByteBuffer var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parseFrom(ByteString var1) throws InvalidProtocolBufferException;

    public MessageType parseFrom(ByteString var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(ByteString var1) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(ByteString var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parseFrom(byte[] var1, int var2, int var3) throws InvalidProtocolBufferException;

    public MessageType parseFrom(byte[] var1, int var2, int var3, ExtensionRegistryLite var4) throws InvalidProtocolBufferException;

    public MessageType parseFrom(byte[] var1) throws InvalidProtocolBufferException;

    public MessageType parseFrom(byte[] var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(byte[] var1, int var2, int var3) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(byte[] var1, int var2, int var3, ExtensionRegistryLite var4) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(byte[] var1) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(byte[] var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parseFrom(InputStream var1) throws InvalidProtocolBufferException;

    public MessageType parseFrom(InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(InputStream var1) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parseDelimitedFrom(InputStream var1) throws InvalidProtocolBufferException;

    public MessageType parseDelimitedFrom(InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parsePartialDelimitedFrom(InputStream var1) throws InvalidProtocolBufferException;

    public MessageType parsePartialDelimitedFrom(InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;
}

