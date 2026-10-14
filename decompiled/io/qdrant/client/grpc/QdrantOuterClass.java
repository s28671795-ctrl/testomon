/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client.grpc;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import grpc.health.v1.HealthCheck;
import io.qdrant.client.grpc.CollectionsInternalService;
import io.qdrant.client.grpc.CollectionsService;
import io.qdrant.client.grpc.PointsInternalService;
import io.qdrant.client.grpc.PointsService;
import io.qdrant.client.grpc.QdrantInternalService;
import io.qdrant.client.grpc.RaftService;
import io.qdrant.client.grpc.ShardSnapshotsService;
import io.qdrant.client.grpc.SnapshotsService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class QdrantOuterClass {
    private static final Descriptors.Descriptor internal_static_qdrant_HealthCheckRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_HealthCheckRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_HealthCheckReply_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_HealthCheckReply_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private QdrantOuterClass() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        QdrantOuterClass.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\fqdrant.proto\u0012\u0006qdrant\u001a\u0019collections_service.proto\u001a\"collections_internal_service.proto\u001a\u0012health_check.proto\u001a\u0014points_service.proto\u001a\u001dpoints_internal_service.proto\u001a\u001dqdrant_internal_service.proto\u001a\u0012raft_service.proto\u001a\u001dshard_snapshots_service.proto\u001a\u0017snapshots_service.proto\"\u0014\n\u0012HealthCheckRequest\"R\n\u0010HealthCheckReply\u0012\r\n\u0005title\u0018\u0001 \u0001(\t\u0012\u000f\n\u0007version\u0018\u0002 \u0001(\t\u0012\u0013\n\u0006commit\u0018\u0003 \u0001(\tH\u0000\u0088\u0001\u0001B\t\n\u0007_commit2O\n\u0006Qdrant\u0012E\n\u000bHealthCheck\u0012\u001a.qdrant.HealthCheckRequest\u001a\u0018.qdrant.HealthCheckReply\"\u0000B,\n\u0015io.qdrant.client.grpc\u00aa\u0002\u0012Qdrant.Client.Grpcb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{CollectionsService.getDescriptor(), CollectionsInternalService.getDescriptor(), HealthCheck.getDescriptor(), PointsService.getDescriptor(), PointsInternalService.getDescriptor(), QdrantInternalService.getDescriptor(), RaftService.getDescriptor(), ShardSnapshotsService.getDescriptor(), SnapshotsService.getDescriptor()});
        internal_static_qdrant_HealthCheckRequest_descriptor = QdrantOuterClass.getDescriptor().getMessageTypes().get(0);
        internal_static_qdrant_HealthCheckRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_HealthCheckRequest_descriptor, new String[0]);
        internal_static_qdrant_HealthCheckReply_descriptor = QdrantOuterClass.getDescriptor().getMessageTypes().get(1);
        internal_static_qdrant_HealthCheckReply_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_HealthCheckReply_descriptor, new String[]{"Title", "Version", "Commit"});
        CollectionsService.getDescriptor();
        CollectionsInternalService.getDescriptor();
        HealthCheck.getDescriptor();
        PointsService.getDescriptor();
        PointsInternalService.getDescriptor();
        QdrantInternalService.getDescriptor();
        RaftService.getDescriptor();
        ShardSnapshotsService.getDescriptor();
        SnapshotsService.getDescriptor();
    }

    public static final class HealthCheckReply
    extends GeneratedMessageV3
    implements HealthCheckReplyOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int TITLE_FIELD_NUMBER = 1;
        private volatile Object title_ = "";
        public static final int VERSION_FIELD_NUMBER = 2;
        private volatile Object version_ = "";
        public static final int COMMIT_FIELD_NUMBER = 3;
        private volatile Object commit_ = "";
        private byte memoizedIsInitialized = (byte)-1;
        private static final HealthCheckReply DEFAULT_INSTANCE = new HealthCheckReply();
        private static final Parser<HealthCheckReply> PARSER = new AbstractParser<HealthCheckReply>(){

            @Override
            public HealthCheckReply parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = HealthCheckReply.newBuilder();
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

        private HealthCheckReply(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private HealthCheckReply() {
            this.title_ = "";
            this.version_ = "";
            this.commit_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new HealthCheckReply();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_HealthCheckReply_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_HealthCheckReply_fieldAccessorTable.ensureFieldAccessorsInitialized(HealthCheckReply.class, Builder.class);
        }

        @Override
        public String getTitle() {
            Object ref = this.title_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.title_ = s2;
            return s2;
        }

        @Override
        public ByteString getTitleBytes() {
            Object ref = this.title_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.title_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public String getVersion() {
            Object ref = this.version_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.version_ = s2;
            return s2;
        }

        @Override
        public ByteString getVersionBytes() {
            Object ref = this.version_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.version_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasCommit() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public String getCommit() {
            Object ref = this.commit_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.commit_ = s2;
            return s2;
        }

        @Override
        public ByteString getCommitBytes() {
            Object ref = this.commit_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.commit_ = b;
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
            if (!GeneratedMessageV3.isStringEmpty(this.title_)) {
                GeneratedMessageV3.writeString(output, 1, this.title_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.version_)) {
                GeneratedMessageV3.writeString(output, 2, this.version_);
            }
            if ((this.bitField0_ & 1) != 0) {
                GeneratedMessageV3.writeString(output, 3, this.commit_);
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
            if (!GeneratedMessageV3.isStringEmpty(this.title_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.title_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.version_)) {
                size += GeneratedMessageV3.computeStringSize(2, this.version_);
            }
            if ((this.bitField0_ & 1) != 0) {
                size += GeneratedMessageV3.computeStringSize(3, this.commit_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof HealthCheckReply)) {
                return super.equals(obj);
            }
            HealthCheckReply other = (HealthCheckReply)obj;
            if (!this.getTitle().equals(other.getTitle())) {
                return false;
            }
            if (!this.getVersion().equals(other.getVersion())) {
                return false;
            }
            if (this.hasCommit() != other.hasCommit()) {
                return false;
            }
            if (this.hasCommit() && !this.getCommit().equals(other.getCommit())) {
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
            hash = 19 * hash + HealthCheckReply.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getTitle().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getVersion().hashCode();
            if (this.hasCommit()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getCommit().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static HealthCheckReply parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static HealthCheckReply parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static HealthCheckReply parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static HealthCheckReply parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static HealthCheckReply parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static HealthCheckReply parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static HealthCheckReply parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HealthCheckReply parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HealthCheckReply parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HealthCheckReply parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HealthCheckReply parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HealthCheckReply parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return HealthCheckReply.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HealthCheckReply prototype) {
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

        public static HealthCheckReply getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HealthCheckReply> parser() {
            return PARSER;
        }

        public Parser<HealthCheckReply> getParserForType() {
            return PARSER;
        }

        @Override
        public HealthCheckReply getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements HealthCheckReplyOrBuilder {
            private int bitField0_;
            private Object title_ = "";
            private Object version_ = "";
            private Object commit_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_HealthCheckReply_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_HealthCheckReply_fieldAccessorTable.ensureFieldAccessorsInitialized(HealthCheckReply.class, Builder.class);
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
                this.title_ = "";
                this.version_ = "";
                this.commit_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_HealthCheckReply_descriptor;
            }

            @Override
            public HealthCheckReply getDefaultInstanceForType() {
                return HealthCheckReply.getDefaultInstance();
            }

            @Override
            public HealthCheckReply build() {
                HealthCheckReply result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public HealthCheckReply buildPartial() {
                HealthCheckReply result = new HealthCheckReply(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(HealthCheckReply result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.title_ = this.title_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.version_ = this.version_;
                }
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 4) != 0) {
                    result.commit_ = this.commit_;
                    to_bitField0_ |= 1;
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
                if (other instanceof HealthCheckReply) {
                    return this.mergeFrom((HealthCheckReply)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HealthCheckReply other) {
                if (other == HealthCheckReply.getDefaultInstance()) {
                    return this;
                }
                if (!other.getTitle().isEmpty()) {
                    this.title_ = other.title_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (!other.getVersion().isEmpty()) {
                    this.version_ = other.version_;
                    this.bitField0_ |= 2;
                    this.onChanged();
                }
                if (other.hasCommit()) {
                    this.commit_ = other.commit_;
                    this.bitField0_ |= 4;
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
                    block11: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block11;
                            }
                            case 10: {
                                this.title_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block11;
                            }
                            case 18: {
                                this.version_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 2;
                                continue block11;
                            }
                            case 26: {
                                this.commit_ = input.readStringRequireUtf8();
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
            public String getTitle() {
                Object ref = this.title_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.title_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getTitleBytes() {
                Object ref = this.title_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.title_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setTitle(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.title_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearTitle() {
                this.title_ = HealthCheckReply.getDefaultInstance().getTitle();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setTitleBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                HealthCheckReply.checkByteStringIsUtf8(value);
                this.title_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public String getVersion() {
                Object ref = this.version_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.version_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getVersionBytes() {
                Object ref = this.version_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.version_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setVersion(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.version_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearVersion() {
                this.version_ = HealthCheckReply.getDefaultInstance().getVersion();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            public Builder setVersionBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                HealthCheckReply.checkByteStringIsUtf8(value);
                this.version_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasCommit() {
                return (this.bitField0_ & 4) != 0;
            }

            @Override
            public String getCommit() {
                Object ref = this.commit_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.commit_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getCommitBytes() {
                Object ref = this.commit_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.commit_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setCommit(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.commit_ = value;
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder clearCommit() {
                this.commit_ = HealthCheckReply.getDefaultInstance().getCommit();
                this.bitField0_ &= 0xFFFFFFFB;
                this.onChanged();
                return this;
            }

            public Builder setCommitBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                HealthCheckReply.checkByteStringIsUtf8(value);
                this.commit_ = value;
                this.bitField0_ |= 4;
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

    public static interface HealthCheckReplyOrBuilder
    extends MessageOrBuilder {
        public String getTitle();

        public ByteString getTitleBytes();

        public String getVersion();

        public ByteString getVersionBytes();

        public boolean hasCommit();

        public String getCommit();

        public ByteString getCommitBytes();
    }

    public static final class HealthCheckRequest
    extends GeneratedMessageV3
    implements HealthCheckRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final HealthCheckRequest DEFAULT_INSTANCE = new HealthCheckRequest();
        private static final Parser<HealthCheckRequest> PARSER = new AbstractParser<HealthCheckRequest>(){

            @Override
            public HealthCheckRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = HealthCheckRequest.newBuilder();
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

        private HealthCheckRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private HealthCheckRequest() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new HealthCheckRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_HealthCheckRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_HealthCheckRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(HealthCheckRequest.class, Builder.class);
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
            this.getUnknownFields().writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof HealthCheckRequest)) {
                return super.equals(obj);
            }
            HealthCheckRequest other = (HealthCheckRequest)obj;
            return this.getUnknownFields().equals(other.getUnknownFields());
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + HealthCheckRequest.getDescriptor().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static HealthCheckRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static HealthCheckRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static HealthCheckRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static HealthCheckRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static HealthCheckRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static HealthCheckRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static HealthCheckRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HealthCheckRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HealthCheckRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HealthCheckRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HealthCheckRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HealthCheckRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return HealthCheckRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HealthCheckRequest prototype) {
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

        public static HealthCheckRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HealthCheckRequest> parser() {
            return PARSER;
        }

        public Parser<HealthCheckRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public HealthCheckRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements HealthCheckRequestOrBuilder {
            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_HealthCheckRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_HealthCheckRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(HealthCheckRequest.class, Builder.class);
            }

            private Builder() {
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
            }

            @Override
            public Builder clear() {
                super.clear();
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_HealthCheckRequest_descriptor;
            }

            @Override
            public HealthCheckRequest getDefaultInstanceForType() {
                return HealthCheckRequest.getDefaultInstance();
            }

            @Override
            public HealthCheckRequest build() {
                HealthCheckRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public HealthCheckRequest buildPartial() {
                HealthCheckRequest result = new HealthCheckRequest(this);
                this.onBuilt();
                return result;
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
                if (other instanceof HealthCheckRequest) {
                    return this.mergeFrom((HealthCheckRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HealthCheckRequest other) {
                if (other == HealthCheckRequest.getDefaultInstance()) {
                    return this;
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
                    block8: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block8;
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
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface HealthCheckRequestOrBuilder
    extends MessageOrBuilder {
    }
}

