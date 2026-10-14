/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client.grpc;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.EmptyProto;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RaftService {
    private static final Descriptors.Descriptor internal_static_qdrant_RaftMessage_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_RaftMessage_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_AllPeers_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_AllPeers_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_Peer_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_Peer_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_AddPeerToKnownMessage_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_AddPeerToKnownMessage_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_PeerId_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_PeerId_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_Uri_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_Uri_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private RaftService() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        RaftService.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0012raft_service.proto\u0012\u0006qdrant\u001a\u001bgoogle/protobuf/empty.proto\"\u001e\n\u000bRaftMessage\u0012\u000f\n\u0007message\u0018\u0001 \u0001(\f\"B\n\bAllPeers\u0012\u001f\n\tall_peers\u0018\u0001 \u0003(\u000b2\f.qdrant.Peer\u0012\u0015\n\rfirst_peer_id\u0018\u0002 \u0001(\u0004\"\u001f\n\u0004Peer\u0012\u000b\n\u0003uri\u0018\u0001 \u0001(\t\u0012\n\n\u0002id\u0018\u0002 \u0001(\u0004\"Y\n\u0015AddPeerToKnownMessage\u0012\u0010\n\u0003uri\u0018\u0001 \u0001(\tH\u0000\u0088\u0001\u0001\u0012\u0011\n\u0004port\u0018\u0002 \u0001(\rH\u0001\u0088\u0001\u0001\u0012\n\n\u0002id\u0018\u0003 \u0001(\u0004B\u0006\n\u0004_uriB\u0007\n\u0005_port\"\u0014\n\u0006PeerId\u0012\n\n\u0002id\u0018\u0001 \u0001(\u0004\"\u0012\n\u0003Uri\u0012\u000b\n\u0003uri\u0018\u0001 \u0001(\t2\u00e4\u0001\n\u0004Raft\u00123\n\u0004Send\u0012\u0013.qdrant.RaftMessage\u001a\u0016.google.protobuf.Empty\u0012$\n\u0005WhoIs\u0012\u000e.qdrant.PeerId\u001a\u000b.qdrant.Uri\u0012A\n\u000eAddPeerToKnown\u0012\u001d.qdrant.AddPeerToKnownMessage\u001a\u0010.qdrant.AllPeers\u0012>\n\u0014AddPeerAsParticipant\u0012\u000e.qdrant.PeerId\u001a\u0016.google.protobuf.EmptyB,\n\u0015io.qdrant.client.grpc\u00aa\u0002\u0012Qdrant.Client.Grpcb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{EmptyProto.getDescriptor()});
        internal_static_qdrant_RaftMessage_descriptor = RaftService.getDescriptor().getMessageTypes().get(0);
        internal_static_qdrant_RaftMessage_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_RaftMessage_descriptor, new String[]{"Message"});
        internal_static_qdrant_AllPeers_descriptor = RaftService.getDescriptor().getMessageTypes().get(1);
        internal_static_qdrant_AllPeers_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_AllPeers_descriptor, new String[]{"AllPeers", "FirstPeerId"});
        internal_static_qdrant_Peer_descriptor = RaftService.getDescriptor().getMessageTypes().get(2);
        internal_static_qdrant_Peer_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_Peer_descriptor, new String[]{"Uri", "Id"});
        internal_static_qdrant_AddPeerToKnownMessage_descriptor = RaftService.getDescriptor().getMessageTypes().get(3);
        internal_static_qdrant_AddPeerToKnownMessage_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_AddPeerToKnownMessage_descriptor, new String[]{"Uri", "Port", "Id"});
        internal_static_qdrant_PeerId_descriptor = RaftService.getDescriptor().getMessageTypes().get(4);
        internal_static_qdrant_PeerId_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_PeerId_descriptor, new String[]{"Id"});
        internal_static_qdrant_Uri_descriptor = RaftService.getDescriptor().getMessageTypes().get(5);
        internal_static_qdrant_Uri_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_Uri_descriptor, new String[]{"Uri"});
        EmptyProto.getDescriptor();
    }

    public static final class Uri
    extends GeneratedMessageV3
    implements UriOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int URI_FIELD_NUMBER = 1;
        private volatile Object uri_ = "";
        private byte memoizedIsInitialized = (byte)-1;
        private static final Uri DEFAULT_INSTANCE = new Uri();
        private static final Parser<Uri> PARSER = new AbstractParser<Uri>(){

            @Override
            public Uri parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = Uri.newBuilder();
                try {
                    builder.mergeFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(builder.buildPartial());
                }
                catch (UninitializedMessageException e) {
                    throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
                }
                catch (IOException e) {
                    throw new InvalidProtocolBufferException(e).setUnfinishedMessage(builder.buildPartial());
                }
                return builder.buildPartial();
            }
        };

        private Uri(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Uri() {
            this.uri_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new Uri();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_Uri_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_Uri_fieldAccessorTable.ensureFieldAccessorsInitialized(Uri.class, Builder.class);
        }

        @Override
        public String getUri() {
            Object ref = this.uri_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.uri_ = s2;
            return s2;
        }

        @Override
        public ByteString getUriBytes() {
            Object ref = this.uri_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.uri_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            if (!GeneratedMessageV3.isStringEmpty(this.uri_)) {
                GeneratedMessageV3.writeString(output, 1, this.uri_);
            }
            this.getUnknownFields().writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if (!GeneratedMessageV3.isStringEmpty(this.uri_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.uri_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Uri)) {
                return super.equals(obj);
            }
            Uri other = (Uri)obj;
            if (!this.getUri().equals(other.getUri())) {
                return false;
            }
            return this.getUnknownFields().equals(other.getUnknownFields());
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + Uri.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getUri().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static Uri parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Uri parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Uri parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Uri parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Uri parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Uri parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Uri parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Uri parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Uri parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Uri parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Uri parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Uri parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Uri.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Uri prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static Uri getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Uri> parser() {
            return PARSER;
        }

        public Parser<Uri> getParserForType() {
            return PARSER;
        }

        @Override
        public Uri getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements UriOrBuilder {
            private int bitField0_;
            private Object uri_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_Uri_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_Uri_fieldAccessorTable.ensureFieldAccessorsInitialized(Uri.class, Builder.class);
            }

            private Builder() {
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                this.uri_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_Uri_descriptor;
            }

            @Override
            public Uri getDefaultInstanceForType() {
                return Uri.getDefaultInstance();
            }

            @Override
            public Uri build() {
                Uri result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Uri buildPartial() {
                Uri result = new Uri(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(Uri result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.uri_ = this.uri_;
                }
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.setField(field2, value);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor field2) {
                return (Builder)super.clearField(field2);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor field2, int index, Object value) {
                return (Builder)super.setRepeatedField(field2, index, value);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.addRepeatedField(field2, value);
            }

            @Override
            public Builder mergeFrom(Message other) {
                if (other instanceof Uri) {
                    return this.mergeFrom((Uri)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Uri other) {
                if (other == Uri.getDefaultInstance()) {
                    return this;
                }
                if (!other.getUri().isEmpty()) {
                    this.uri_ = other.uri_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                this.mergeUnknownFields(other.getUnknownFields());
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                if (extensionRegistry == null) {
                    throw new NullPointerException();
                }
                try {
                    boolean done = false;
                    block9: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block9;
                            }
                            case 10: {
                                this.uri_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block9;
                            }
                        }
                        if (super.parseUnknownField(input, extensionRegistry, tag)) continue;
                        done = true;
                    }
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.unwrapIOException();
                }
                finally {
                    this.onChanged();
                }
                return this;
            }

            @Override
            public String getUri() {
                Object ref = this.uri_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.uri_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getUriBytes() {
                Object ref = this.uri_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.uri_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setUri(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.uri_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearUri() {
                this.uri_ = Uri.getDefaultInstance().getUri();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setUriBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Uri.checkByteStringIsUtf8(value);
                this.uri_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface UriOrBuilder
    extends MessageOrBuilder {
        public String getUri();

        public ByteString getUriBytes();
    }

    public static final class PeerId
    extends GeneratedMessageV3
    implements PeerIdOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ID_FIELD_NUMBER = 1;
        private long id_ = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final PeerId DEFAULT_INSTANCE = new PeerId();
        private static final Parser<PeerId> PARSER = new AbstractParser<PeerId>(){

            @Override
            public PeerId parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = PeerId.newBuilder();
                try {
                    builder.mergeFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(builder.buildPartial());
                }
                catch (UninitializedMessageException e) {
                    throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
                }
                catch (IOException e) {
                    throw new InvalidProtocolBufferException(e).setUnfinishedMessage(builder.buildPartial());
                }
                return builder.buildPartial();
            }
        };

        private PeerId(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private PeerId() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new PeerId();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_PeerId_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_PeerId_fieldAccessorTable.ensureFieldAccessorsInitialized(PeerId.class, Builder.class);
        }

        @Override
        public long getId() {
            return this.id_;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            if (this.id_ != 0L) {
                output.writeUInt64(1, this.id_);
            }
            this.getUnknownFields().writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if (this.id_ != 0L) {
                size += CodedOutputStream.computeUInt64Size(1, this.id_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PeerId)) {
                return super.equals(obj);
            }
            PeerId other = (PeerId)obj;
            if (this.getId() != other.getId()) {
                return false;
            }
            return this.getUnknownFields().equals(other.getUnknownFields());
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + PeerId.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashLong(this.getId());
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static PeerId parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static PeerId parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static PeerId parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static PeerId parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static PeerId parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static PeerId parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static PeerId parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static PeerId parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static PeerId parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static PeerId parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static PeerId parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static PeerId parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return PeerId.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(PeerId prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static PeerId getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PeerId> parser() {
            return PARSER;
        }

        public Parser<PeerId> getParserForType() {
            return PARSER;
        }

        @Override
        public PeerId getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements PeerIdOrBuilder {
            private int bitField0_;
            private long id_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_PeerId_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_PeerId_fieldAccessorTable.ensureFieldAccessorsInitialized(PeerId.class, Builder.class);
            }

            private Builder() {
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                this.id_ = 0L;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_PeerId_descriptor;
            }

            @Override
            public PeerId getDefaultInstanceForType() {
                return PeerId.getDefaultInstance();
            }

            @Override
            public PeerId build() {
                PeerId result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public PeerId buildPartial() {
                PeerId result = new PeerId(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(PeerId result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.id_ = this.id_;
                }
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.setField(field2, value);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor field2) {
                return (Builder)super.clearField(field2);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor field2, int index, Object value) {
                return (Builder)super.setRepeatedField(field2, index, value);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.addRepeatedField(field2, value);
            }

            @Override
            public Builder mergeFrom(Message other) {
                if (other instanceof PeerId) {
                    return this.mergeFrom((PeerId)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(PeerId other) {
                if (other == PeerId.getDefaultInstance()) {
                    return this;
                }
                if (other.getId() != 0L) {
                    this.setId(other.getId());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                if (extensionRegistry == null) {
                    throw new NullPointerException();
                }
                try {
                    boolean done = false;
                    block9: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block9;
                            }
                            case 8: {
                                this.id_ = input.readUInt64();
                                this.bitField0_ |= 1;
                                continue block9;
                            }
                        }
                        if (super.parseUnknownField(input, extensionRegistry, tag)) continue;
                        done = true;
                    }
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.unwrapIOException();
                }
                finally {
                    this.onChanged();
                }
                return this;
            }

            @Override
            public long getId() {
                return this.id_;
            }

            public Builder setId(long value) {
                this.id_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearId() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.id_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface PeerIdOrBuilder
    extends MessageOrBuilder {
        public long getId();
    }

    public static final class AddPeerToKnownMessage
    extends GeneratedMessageV3
    implements AddPeerToKnownMessageOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int URI_FIELD_NUMBER = 1;
        private volatile Object uri_ = "";
        public static final int PORT_FIELD_NUMBER = 2;
        private int port_ = 0;
        public static final int ID_FIELD_NUMBER = 3;
        private long id_ = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final AddPeerToKnownMessage DEFAULT_INSTANCE = new AddPeerToKnownMessage();
        private static final Parser<AddPeerToKnownMessage> PARSER = new AbstractParser<AddPeerToKnownMessage>(){

            @Override
            public AddPeerToKnownMessage parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = AddPeerToKnownMessage.newBuilder();
                try {
                    builder.mergeFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(builder.buildPartial());
                }
                catch (UninitializedMessageException e) {
                    throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
                }
                catch (IOException e) {
                    throw new InvalidProtocolBufferException(e).setUnfinishedMessage(builder.buildPartial());
                }
                return builder.buildPartial();
            }
        };

        private AddPeerToKnownMessage(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private AddPeerToKnownMessage() {
            this.uri_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new AddPeerToKnownMessage();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_AddPeerToKnownMessage_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_AddPeerToKnownMessage_fieldAccessorTable.ensureFieldAccessorsInitialized(AddPeerToKnownMessage.class, Builder.class);
        }

        @Override
        public boolean hasUri() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public String getUri() {
            Object ref = this.uri_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.uri_ = s2;
            return s2;
        }

        @Override
        public ByteString getUriBytes() {
            Object ref = this.uri_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.uri_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasPort() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override
        public int getPort() {
            return this.port_;
        }

        @Override
        public long getId() {
            return this.id_;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                GeneratedMessageV3.writeString(output, 1, this.uri_);
            }
            if ((this.bitField0_ & 2) != 0) {
                output.writeUInt32(2, this.port_);
            }
            if (this.id_ != 0L) {
                output.writeUInt64(3, this.id_);
            }
            this.getUnknownFields().writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) != 0) {
                size += GeneratedMessageV3.computeStringSize(1, this.uri_);
            }
            if ((this.bitField0_ & 2) != 0) {
                size += CodedOutputStream.computeUInt32Size(2, this.port_);
            }
            if (this.id_ != 0L) {
                size += CodedOutputStream.computeUInt64Size(3, this.id_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AddPeerToKnownMessage)) {
                return super.equals(obj);
            }
            AddPeerToKnownMessage other = (AddPeerToKnownMessage)obj;
            if (this.hasUri() != other.hasUri()) {
                return false;
            }
            if (this.hasUri() && !this.getUri().equals(other.getUri())) {
                return false;
            }
            if (this.hasPort() != other.hasPort()) {
                return false;
            }
            if (this.hasPort() && this.getPort() != other.getPort()) {
                return false;
            }
            if (this.getId() != other.getId()) {
                return false;
            }
            return this.getUnknownFields().equals(other.getUnknownFields());
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + AddPeerToKnownMessage.getDescriptor().hashCode();
            if (this.hasUri()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getUri().hashCode();
            }
            if (this.hasPort()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getPort();
            }
            hash = 37 * hash + 3;
            hash = 53 * hash + Internal.hashLong(this.getId());
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static AddPeerToKnownMessage parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AddPeerToKnownMessage parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AddPeerToKnownMessage parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AddPeerToKnownMessage parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AddPeerToKnownMessage parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AddPeerToKnownMessage parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AddPeerToKnownMessage parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AddPeerToKnownMessage parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AddPeerToKnownMessage parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AddPeerToKnownMessage parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AddPeerToKnownMessage parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AddPeerToKnownMessage parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return AddPeerToKnownMessage.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AddPeerToKnownMessage prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static AddPeerToKnownMessage getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AddPeerToKnownMessage> parser() {
            return PARSER;
        }

        public Parser<AddPeerToKnownMessage> getParserForType() {
            return PARSER;
        }

        @Override
        public AddPeerToKnownMessage getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements AddPeerToKnownMessageOrBuilder {
            private int bitField0_;
            private Object uri_ = "";
            private int port_;
            private long id_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_AddPeerToKnownMessage_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_AddPeerToKnownMessage_fieldAccessorTable.ensureFieldAccessorsInitialized(AddPeerToKnownMessage.class, Builder.class);
            }

            private Builder() {
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                this.uri_ = "";
                this.port_ = 0;
                this.id_ = 0L;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_AddPeerToKnownMessage_descriptor;
            }

            @Override
            public AddPeerToKnownMessage getDefaultInstanceForType() {
                return AddPeerToKnownMessage.getDefaultInstance();
            }

            @Override
            public AddPeerToKnownMessage build() {
                AddPeerToKnownMessage result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public AddPeerToKnownMessage buildPartial() {
                AddPeerToKnownMessage result = new AddPeerToKnownMessage(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(AddPeerToKnownMessage result) {
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) != 0) {
                    result.uri_ = this.uri_;
                    to_bitField0_ |= 1;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.port_ = this.port_;
                    to_bitField0_ |= 2;
                }
                if ((from_bitField0_ & 4) != 0) {
                    result.id_ = this.id_;
                }
                result.bitField0_ |= to_bitField0_;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.setField(field2, value);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor field2) {
                return (Builder)super.clearField(field2);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor field2, int index, Object value) {
                return (Builder)super.setRepeatedField(field2, index, value);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.addRepeatedField(field2, value);
            }

            @Override
            public Builder mergeFrom(Message other) {
                if (other instanceof AddPeerToKnownMessage) {
                    return this.mergeFrom((AddPeerToKnownMessage)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AddPeerToKnownMessage other) {
                if (other == AddPeerToKnownMessage.getDefaultInstance()) {
                    return this;
                }
                if (other.hasUri()) {
                    this.uri_ = other.uri_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (other.hasPort()) {
                    this.setPort(other.getPort());
                }
                if (other.getId() != 0L) {
                    this.setId(other.getId());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                if (extensionRegistry == null) {
                    throw new NullPointerException();
                }
                try {
                    boolean done = false;
                    block11: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block11;
                            }
                            case 10: {
                                this.uri_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block11;
                            }
                            case 16: {
                                this.port_ = input.readUInt32();
                                this.bitField0_ |= 2;
                                continue block11;
                            }
                            case 24: {
                                this.id_ = input.readUInt64();
                                this.bitField0_ |= 4;
                                continue block11;
                            }
                        }
                        if (super.parseUnknownField(input, extensionRegistry, tag)) continue;
                        done = true;
                    }
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.unwrapIOException();
                }
                finally {
                    this.onChanged();
                }
                return this;
            }

            @Override
            public boolean hasUri() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public String getUri() {
                Object ref = this.uri_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.uri_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getUriBytes() {
                Object ref = this.uri_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.uri_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setUri(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.uri_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearUri() {
                this.uri_ = AddPeerToKnownMessage.getDefaultInstance().getUri();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setUriBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                AddPeerToKnownMessage.checkByteStringIsUtf8(value);
                this.uri_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasPort() {
                return (this.bitField0_ & 2) != 0;
            }

            @Override
            public int getPort() {
                return this.port_;
            }

            public Builder setPort(int value) {
                this.port_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearPort() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.port_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public long getId() {
                return this.id_;
            }

            public Builder setId(long value) {
                this.id_ = value;
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder clearId() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.id_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface AddPeerToKnownMessageOrBuilder
    extends MessageOrBuilder {
        public boolean hasUri();

        public String getUri();

        public ByteString getUriBytes();

        public boolean hasPort();

        public int getPort();

        public long getId();
    }

    public static final class Peer
    extends GeneratedMessageV3
    implements PeerOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int URI_FIELD_NUMBER = 1;
        private volatile Object uri_ = "";
        public static final int ID_FIELD_NUMBER = 2;
        private long id_ = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Peer DEFAULT_INSTANCE = new Peer();
        private static final Parser<Peer> PARSER = new AbstractParser<Peer>(){

            @Override
            public Peer parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = Peer.newBuilder();
                try {
                    builder.mergeFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(builder.buildPartial());
                }
                catch (UninitializedMessageException e) {
                    throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
                }
                catch (IOException e) {
                    throw new InvalidProtocolBufferException(e).setUnfinishedMessage(builder.buildPartial());
                }
                return builder.buildPartial();
            }
        };

        private Peer(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Peer() {
            this.uri_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new Peer();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_Peer_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_Peer_fieldAccessorTable.ensureFieldAccessorsInitialized(Peer.class, Builder.class);
        }

        @Override
        public String getUri() {
            Object ref = this.uri_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.uri_ = s2;
            return s2;
        }

        @Override
        public ByteString getUriBytes() {
            Object ref = this.uri_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.uri_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public long getId() {
            return this.id_;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            if (!GeneratedMessageV3.isStringEmpty(this.uri_)) {
                GeneratedMessageV3.writeString(output, 1, this.uri_);
            }
            if (this.id_ != 0L) {
                output.writeUInt64(2, this.id_);
            }
            this.getUnknownFields().writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if (!GeneratedMessageV3.isStringEmpty(this.uri_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.uri_);
            }
            if (this.id_ != 0L) {
                size += CodedOutputStream.computeUInt64Size(2, this.id_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Peer)) {
                return super.equals(obj);
            }
            Peer other = (Peer)obj;
            if (!this.getUri().equals(other.getUri())) {
                return false;
            }
            if (this.getId() != other.getId()) {
                return false;
            }
            return this.getUnknownFields().equals(other.getUnknownFields());
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + Peer.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getUri().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashLong(this.getId());
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static Peer parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Peer parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Peer parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Peer parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Peer parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Peer parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Peer parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Peer parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Peer parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Peer parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Peer parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Peer parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Peer.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Peer prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static Peer getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Peer> parser() {
            return PARSER;
        }

        public Parser<Peer> getParserForType() {
            return PARSER;
        }

        @Override
        public Peer getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements PeerOrBuilder {
            private int bitField0_;
            private Object uri_ = "";
            private long id_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_Peer_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_Peer_fieldAccessorTable.ensureFieldAccessorsInitialized(Peer.class, Builder.class);
            }

            private Builder() {
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                this.uri_ = "";
                this.id_ = 0L;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_Peer_descriptor;
            }

            @Override
            public Peer getDefaultInstanceForType() {
                return Peer.getDefaultInstance();
            }

            @Override
            public Peer build() {
                Peer result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Peer buildPartial() {
                Peer result = new Peer(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(Peer result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.uri_ = this.uri_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.id_ = this.id_;
                }
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.setField(field2, value);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor field2) {
                return (Builder)super.clearField(field2);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor field2, int index, Object value) {
                return (Builder)super.setRepeatedField(field2, index, value);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.addRepeatedField(field2, value);
            }

            @Override
            public Builder mergeFrom(Message other) {
                if (other instanceof Peer) {
                    return this.mergeFrom((Peer)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Peer other) {
                if (other == Peer.getDefaultInstance()) {
                    return this;
                }
                if (!other.getUri().isEmpty()) {
                    this.uri_ = other.uri_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (other.getId() != 0L) {
                    this.setId(other.getId());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                if (extensionRegistry == null) {
                    throw new NullPointerException();
                }
                try {
                    boolean done = false;
                    block10: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block10;
                            }
                            case 10: {
                                this.uri_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block10;
                            }
                            case 16: {
                                this.id_ = input.readUInt64();
                                this.bitField0_ |= 2;
                                continue block10;
                            }
                        }
                        if (super.parseUnknownField(input, extensionRegistry, tag)) continue;
                        done = true;
                    }
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.unwrapIOException();
                }
                finally {
                    this.onChanged();
                }
                return this;
            }

            @Override
            public String getUri() {
                Object ref = this.uri_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.uri_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getUriBytes() {
                Object ref = this.uri_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.uri_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setUri(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.uri_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearUri() {
                this.uri_ = Peer.getDefaultInstance().getUri();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setUriBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Peer.checkByteStringIsUtf8(value);
                this.uri_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public long getId() {
                return this.id_;
            }

            public Builder setId(long value) {
                this.id_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearId() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.id_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface PeerOrBuilder
    extends MessageOrBuilder {
        public String getUri();

        public ByteString getUriBytes();

        public long getId();
    }

    public static final class AllPeers
    extends GeneratedMessageV3
    implements AllPeersOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ALL_PEERS_FIELD_NUMBER = 1;
        private List<Peer> allPeers_;
        public static final int FIRST_PEER_ID_FIELD_NUMBER = 2;
        private long firstPeerId_ = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final AllPeers DEFAULT_INSTANCE = new AllPeers();
        private static final Parser<AllPeers> PARSER = new AbstractParser<AllPeers>(){

            @Override
            public AllPeers parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = AllPeers.newBuilder();
                try {
                    builder.mergeFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(builder.buildPartial());
                }
                catch (UninitializedMessageException e) {
                    throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
                }
                catch (IOException e) {
                    throw new InvalidProtocolBufferException(e).setUnfinishedMessage(builder.buildPartial());
                }
                return builder.buildPartial();
            }
        };

        private AllPeers(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private AllPeers() {
            this.allPeers_ = Collections.emptyList();
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new AllPeers();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_AllPeers_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_AllPeers_fieldAccessorTable.ensureFieldAccessorsInitialized(AllPeers.class, Builder.class);
        }

        @Override
        public List<Peer> getAllPeersList() {
            return this.allPeers_;
        }

        @Override
        public List<? extends PeerOrBuilder> getAllPeersOrBuilderList() {
            return this.allPeers_;
        }

        @Override
        public int getAllPeersCount() {
            return this.allPeers_.size();
        }

        @Override
        public Peer getAllPeers(int index) {
            return this.allPeers_.get(index);
        }

        @Override
        public PeerOrBuilder getAllPeersOrBuilder(int index) {
            return this.allPeers_.get(index);
        }

        @Override
        public long getFirstPeerId() {
            return this.firstPeerId_;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            for (int i = 0; i < this.allPeers_.size(); ++i) {
                output.writeMessage(1, this.allPeers_.get(i));
            }
            if (this.firstPeerId_ != 0L) {
                output.writeUInt64(2, this.firstPeerId_);
            }
            this.getUnknownFields().writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (int i = 0; i < this.allPeers_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, this.allPeers_.get(i));
            }
            if (this.firstPeerId_ != 0L) {
                size += CodedOutputStream.computeUInt64Size(2, this.firstPeerId_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AllPeers)) {
                return super.equals(obj);
            }
            AllPeers other = (AllPeers)obj;
            if (!this.getAllPeersList().equals(other.getAllPeersList())) {
                return false;
            }
            if (this.getFirstPeerId() != other.getFirstPeerId()) {
                return false;
            }
            return this.getUnknownFields().equals(other.getUnknownFields());
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + AllPeers.getDescriptor().hashCode();
            if (this.getAllPeersCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getAllPeersList().hashCode();
            }
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashLong(this.getFirstPeerId());
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static AllPeers parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AllPeers parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AllPeers parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AllPeers parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AllPeers parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AllPeers parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AllPeers parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AllPeers parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AllPeers parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AllPeers parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AllPeers parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AllPeers parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return AllPeers.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AllPeers prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static AllPeers getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AllPeers> parser() {
            return PARSER;
        }

        public Parser<AllPeers> getParserForType() {
            return PARSER;
        }

        @Override
        public AllPeers getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements AllPeersOrBuilder {
            private int bitField0_;
            private List<Peer> allPeers_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<Peer, Peer.Builder, PeerOrBuilder> allPeersBuilder_;
            private long firstPeerId_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_AllPeers_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_AllPeers_fieldAccessorTable.ensureFieldAccessorsInitialized(AllPeers.class, Builder.class);
            }

            private Builder() {
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                if (this.allPeersBuilder_ == null) {
                    this.allPeers_ = Collections.emptyList();
                } else {
                    this.allPeers_ = null;
                    this.allPeersBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                this.firstPeerId_ = 0L;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_AllPeers_descriptor;
            }

            @Override
            public AllPeers getDefaultInstanceForType() {
                return AllPeers.getDefaultInstance();
            }

            @Override
            public AllPeers build() {
                AllPeers result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public AllPeers buildPartial() {
                AllPeers result = new AllPeers(this);
                this.buildPartialRepeatedFields(result);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartialRepeatedFields(AllPeers result) {
                if (this.allPeersBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 0) {
                        this.allPeers_ = Collections.unmodifiableList(this.allPeers_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.allPeers_ = this.allPeers_;
                } else {
                    result.allPeers_ = this.allPeersBuilder_.build();
                }
            }

            private void buildPartial0(AllPeers result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 2) != 0) {
                    result.firstPeerId_ = this.firstPeerId_;
                }
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.setField(field2, value);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor field2) {
                return (Builder)super.clearField(field2);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor field2, int index, Object value) {
                return (Builder)super.setRepeatedField(field2, index, value);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.addRepeatedField(field2, value);
            }

            @Override
            public Builder mergeFrom(Message other) {
                if (other instanceof AllPeers) {
                    return this.mergeFrom((AllPeers)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AllPeers other) {
                if (other == AllPeers.getDefaultInstance()) {
                    return this;
                }
                if (this.allPeersBuilder_ == null) {
                    if (!other.allPeers_.isEmpty()) {
                        if (this.allPeers_.isEmpty()) {
                            this.allPeers_ = other.allPeers_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureAllPeersIsMutable();
                            this.allPeers_.addAll(other.allPeers_);
                        }
                        this.onChanged();
                    }
                } else if (!other.allPeers_.isEmpty()) {
                    if (this.allPeersBuilder_.isEmpty()) {
                        this.allPeersBuilder_.dispose();
                        this.allPeersBuilder_ = null;
                        this.allPeers_ = other.allPeers_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.allPeersBuilder_ = alwaysUseFieldBuilders ? this.getAllPeersFieldBuilder() : null;
                    } else {
                        this.allPeersBuilder_.addAllMessages(other.allPeers_);
                    }
                }
                if (other.getFirstPeerId() != 0L) {
                    this.setFirstPeerId(other.getFirstPeerId());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                if (extensionRegistry == null) {
                    throw new NullPointerException();
                }
                try {
                    boolean done = false;
                    block10: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block10;
                            }
                            case 10: {
                                Peer m4 = input.readMessage(Peer.parser(), extensionRegistry);
                                if (this.allPeersBuilder_ == null) {
                                    this.ensureAllPeersIsMutable();
                                    this.allPeers_.add(m4);
                                    continue block10;
                                }
                                this.allPeersBuilder_.addMessage(m4);
                                continue block10;
                            }
                            case 16: {
                                this.firstPeerId_ = input.readUInt64();
                                this.bitField0_ |= 2;
                                continue block10;
                            }
                        }
                        if (super.parseUnknownField(input, extensionRegistry, tag)) continue;
                        done = true;
                    }
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.unwrapIOException();
                }
                finally {
                    this.onChanged();
                }
                return this;
            }

            private void ensureAllPeersIsMutable() {
                if ((this.bitField0_ & 1) == 0) {
                    this.allPeers_ = new ArrayList<Peer>(this.allPeers_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<Peer> getAllPeersList() {
                if (this.allPeersBuilder_ == null) {
                    return Collections.unmodifiableList(this.allPeers_);
                }
                return this.allPeersBuilder_.getMessageList();
            }

            @Override
            public int getAllPeersCount() {
                if (this.allPeersBuilder_ == null) {
                    return this.allPeers_.size();
                }
                return this.allPeersBuilder_.getCount();
            }

            @Override
            public Peer getAllPeers(int index) {
                if (this.allPeersBuilder_ == null) {
                    return this.allPeers_.get(index);
                }
                return this.allPeersBuilder_.getMessage(index);
            }

            public Builder setAllPeers(int index, Peer value) {
                if (this.allPeersBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAllPeersIsMutable();
                    this.allPeers_.set(index, value);
                    this.onChanged();
                } else {
                    this.allPeersBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setAllPeers(int index, Peer.Builder builderForValue) {
                if (this.allPeersBuilder_ == null) {
                    this.ensureAllPeersIsMutable();
                    this.allPeers_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.allPeersBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllPeers(Peer value) {
                if (this.allPeersBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAllPeersIsMutable();
                    this.allPeers_.add(value);
                    this.onChanged();
                } else {
                    this.allPeersBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addAllPeers(int index, Peer value) {
                if (this.allPeersBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAllPeersIsMutable();
                    this.allPeers_.add(index, value);
                    this.onChanged();
                } else {
                    this.allPeersBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addAllPeers(Peer.Builder builderForValue) {
                if (this.allPeersBuilder_ == null) {
                    this.ensureAllPeersIsMutable();
                    this.allPeers_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.allPeersBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addAllPeers(int index, Peer.Builder builderForValue) {
                if (this.allPeersBuilder_ == null) {
                    this.ensureAllPeersIsMutable();
                    this.allPeers_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.allPeersBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllAllPeers(Iterable<? extends Peer> values2) {
                if (this.allPeersBuilder_ == null) {
                    this.ensureAllPeersIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.allPeers_);
                    this.onChanged();
                } else {
                    this.allPeersBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearAllPeers() {
                if (this.allPeersBuilder_ == null) {
                    this.allPeers_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.allPeersBuilder_.clear();
                }
                return this;
            }

            public Builder removeAllPeers(int index) {
                if (this.allPeersBuilder_ == null) {
                    this.ensureAllPeersIsMutable();
                    this.allPeers_.remove(index);
                    this.onChanged();
                } else {
                    this.allPeersBuilder_.remove(index);
                }
                return this;
            }

            public Peer.Builder getAllPeersBuilder(int index) {
                return this.getAllPeersFieldBuilder().getBuilder(index);
            }

            @Override
            public PeerOrBuilder getAllPeersOrBuilder(int index) {
                if (this.allPeersBuilder_ == null) {
                    return this.allPeers_.get(index);
                }
                return this.allPeersBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends PeerOrBuilder> getAllPeersOrBuilderList() {
                if (this.allPeersBuilder_ != null) {
                    return this.allPeersBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.allPeers_);
            }

            public Peer.Builder addAllPeersBuilder() {
                return this.getAllPeersFieldBuilder().addBuilder(Peer.getDefaultInstance());
            }

            public Peer.Builder addAllPeersBuilder(int index) {
                return this.getAllPeersFieldBuilder().addBuilder(index, Peer.getDefaultInstance());
            }

            public List<Peer.Builder> getAllPeersBuilderList() {
                return this.getAllPeersFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<Peer, Peer.Builder, PeerOrBuilder> getAllPeersFieldBuilder() {
                if (this.allPeersBuilder_ == null) {
                    this.allPeersBuilder_ = new RepeatedFieldBuilderV3(this.allPeers_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
                    this.allPeers_ = null;
                }
                return this.allPeersBuilder_;
            }

            @Override
            public long getFirstPeerId() {
                return this.firstPeerId_;
            }

            public Builder setFirstPeerId(long value) {
                this.firstPeerId_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearFirstPeerId() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.firstPeerId_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface AllPeersOrBuilder
    extends MessageOrBuilder {
        public List<Peer> getAllPeersList();

        public Peer getAllPeers(int var1);

        public int getAllPeersCount();

        public List<? extends PeerOrBuilder> getAllPeersOrBuilderList();

        public PeerOrBuilder getAllPeersOrBuilder(int var1);

        public long getFirstPeerId();
    }

    public static final class RaftMessage
    extends GeneratedMessageV3
    implements RaftMessageOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MESSAGE_FIELD_NUMBER = 1;
        private ByteString message_ = ByteString.EMPTY;
        private byte memoizedIsInitialized = (byte)-1;
        private static final RaftMessage DEFAULT_INSTANCE = new RaftMessage();
        private static final Parser<RaftMessage> PARSER = new AbstractParser<RaftMessage>(){

            @Override
            public RaftMessage parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = RaftMessage.newBuilder();
                try {
                    builder.mergeFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(builder.buildPartial());
                }
                catch (UninitializedMessageException e) {
                    throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
                }
                catch (IOException e) {
                    throw new InvalidProtocolBufferException(e).setUnfinishedMessage(builder.buildPartial());
                }
                return builder.buildPartial();
            }
        };

        private RaftMessage(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private RaftMessage() {
            this.message_ = ByteString.EMPTY;
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new RaftMessage();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_RaftMessage_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_RaftMessage_fieldAccessorTable.ensureFieldAccessorsInitialized(RaftMessage.class, Builder.class);
        }

        @Override
        public ByteString getMessage() {
            return this.message_;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            if (!this.message_.isEmpty()) {
                output.writeBytes(1, this.message_);
            }
            this.getUnknownFields().writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if (!this.message_.isEmpty()) {
                size += CodedOutputStream.computeBytesSize(1, this.message_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof RaftMessage)) {
                return super.equals(obj);
            }
            RaftMessage other = (RaftMessage)obj;
            if (!this.getMessage().equals(other.getMessage())) {
                return false;
            }
            return this.getUnknownFields().equals(other.getUnknownFields());
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + RaftMessage.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getMessage().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static RaftMessage parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RaftMessage parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RaftMessage parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RaftMessage parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RaftMessage parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RaftMessage parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RaftMessage parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RaftMessage parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RaftMessage parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static RaftMessage parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RaftMessage parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RaftMessage parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return RaftMessage.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RaftMessage prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static RaftMessage getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RaftMessage> parser() {
            return PARSER;
        }

        public Parser<RaftMessage> getParserForType() {
            return PARSER;
        }

        @Override
        public RaftMessage getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements RaftMessageOrBuilder {
            private int bitField0_;
            private ByteString message_ = ByteString.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_RaftMessage_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_RaftMessage_fieldAccessorTable.ensureFieldAccessorsInitialized(RaftMessage.class, Builder.class);
            }

            private Builder() {
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                this.message_ = ByteString.EMPTY;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_RaftMessage_descriptor;
            }

            @Override
            public RaftMessage getDefaultInstanceForType() {
                return RaftMessage.getDefaultInstance();
            }

            @Override
            public RaftMessage build() {
                RaftMessage result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public RaftMessage buildPartial() {
                RaftMessage result = new RaftMessage(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(RaftMessage result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.message_ = this.message_;
                }
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.setField(field2, value);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor field2) {
                return (Builder)super.clearField(field2);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor field2, int index, Object value) {
                return (Builder)super.setRepeatedField(field2, index, value);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.addRepeatedField(field2, value);
            }

            @Override
            public Builder mergeFrom(Message other) {
                if (other instanceof RaftMessage) {
                    return this.mergeFrom((RaftMessage)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(RaftMessage other) {
                if (other == RaftMessage.getDefaultInstance()) {
                    return this;
                }
                if (other.getMessage() != ByteString.EMPTY) {
                    this.setMessage(other.getMessage());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                if (extensionRegistry == null) {
                    throw new NullPointerException();
                }
                try {
                    boolean done = false;
                    block9: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block9;
                            }
                            case 10: {
                                this.message_ = input.readBytes();
                                this.bitField0_ |= 1;
                                continue block9;
                            }
                        }
                        if (super.parseUnknownField(input, extensionRegistry, tag)) continue;
                        done = true;
                    }
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.unwrapIOException();
                }
                finally {
                    this.onChanged();
                }
                return this;
            }

            @Override
            public ByteString getMessage() {
                return this.message_;
            }

            public Builder setMessage(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.message_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearMessage() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.message_ = RaftMessage.getDefaultInstance().getMessage();
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface RaftMessageOrBuilder
    extends MessageOrBuilder {
        public ByteString getMessage();
    }
}

