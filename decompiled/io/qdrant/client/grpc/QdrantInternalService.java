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
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class QdrantInternalService {
    private static final Descriptors.Descriptor internal_static_qdrant_GetConsensusCommitRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_GetConsensusCommitRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_GetConsensusCommitResponse_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_GetConsensusCommitResponse_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_WaitOnConsensusCommitRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_WaitOnConsensusCommitRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_WaitOnConsensusCommitResponse_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_WaitOnConsensusCommitResponse_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private QdrantInternalService() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        QdrantInternalService.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001dqdrant_internal_service.proto\u0012\u0006qdrant\"\u001b\n\u0019GetConsensusCommitRequest\":\n\u001aGetConsensusCommitResponse\u0012\u000e\n\u0006commit\u0018\u0001 \u0001(\u0003\u0012\f\n\u0004term\u0018\u0002 \u0001(\u0003\"M\n\u001cWaitOnConsensusCommitRequest\u0012\u000e\n\u0006commit\u0018\u0001 \u0001(\u0003\u0012\f\n\u0004term\u0018\u0002 \u0001(\u0003\u0012\u000f\n\u0007timeout\u0018\u0003 \u0001(\u0003\"+\n\u001dWaitOnConsensusCommitResponse\u0012\n\n\u0002ok\u0018\u0001 \u0001(\b2\u00d7\u0001\n\u000eQdrantInternal\u0012]\n\u0012GetConsensusCommit\u0012!.qdrant.GetConsensusCommitRequest\u001a\".qdrant.GetConsensusCommitResponse\"\u0000\u0012f\n\u0015WaitOnConsensusCommit\u0012$.qdrant.WaitOnConsensusCommitRequest\u001a%.qdrant.WaitOnConsensusCommitResponse\"\u0000B,\n\u0015io.qdrant.client.grpc\u00aa\u0002\u0012Qdrant.Client.Grpcb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_qdrant_GetConsensusCommitRequest_descriptor = QdrantInternalService.getDescriptor().getMessageTypes().get(0);
        internal_static_qdrant_GetConsensusCommitRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_GetConsensusCommitRequest_descriptor, new String[0]);
        internal_static_qdrant_GetConsensusCommitResponse_descriptor = QdrantInternalService.getDescriptor().getMessageTypes().get(1);
        internal_static_qdrant_GetConsensusCommitResponse_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_GetConsensusCommitResponse_descriptor, new String[]{"Commit", "Term"});
        internal_static_qdrant_WaitOnConsensusCommitRequest_descriptor = QdrantInternalService.getDescriptor().getMessageTypes().get(2);
        internal_static_qdrant_WaitOnConsensusCommitRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_WaitOnConsensusCommitRequest_descriptor, new String[]{"Commit", "Term", "Timeout"});
        internal_static_qdrant_WaitOnConsensusCommitResponse_descriptor = QdrantInternalService.getDescriptor().getMessageTypes().get(3);
        internal_static_qdrant_WaitOnConsensusCommitResponse_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_WaitOnConsensusCommitResponse_descriptor, new String[]{"Ok"});
    }

    public static final class WaitOnConsensusCommitResponse
    extends GeneratedMessageV3
    implements WaitOnConsensusCommitResponseOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int OK_FIELD_NUMBER = 1;
        private boolean ok_ = false;
        private byte memoizedIsInitialized = (byte)-1;
        private static final WaitOnConsensusCommitResponse DEFAULT_INSTANCE = new WaitOnConsensusCommitResponse();
        private static final Parser<WaitOnConsensusCommitResponse> PARSER = new AbstractParser<WaitOnConsensusCommitResponse>(){

            @Override
            public WaitOnConsensusCommitResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = WaitOnConsensusCommitResponse.newBuilder();
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

        private WaitOnConsensusCommitResponse(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private WaitOnConsensusCommitResponse() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new WaitOnConsensusCommitResponse();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_WaitOnConsensusCommitResponse_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_WaitOnConsensusCommitResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(WaitOnConsensusCommitResponse.class, Builder.class);
        }

        @Override
        public boolean getOk() {
            return this.ok_;
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
            if (this.ok_) {
                output.writeBool(1, this.ok_);
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
            if (this.ok_) {
                size += CodedOutputStream.computeBoolSize(1, this.ok_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof WaitOnConsensusCommitResponse)) {
                return super.equals(obj);
            }
            WaitOnConsensusCommitResponse other = (WaitOnConsensusCommitResponse)obj;
            if (this.getOk() != other.getOk()) {
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
            hash = 19 * hash + WaitOnConsensusCommitResponse.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashBoolean(this.getOk());
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static WaitOnConsensusCommitResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static WaitOnConsensusCommitResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static WaitOnConsensusCommitResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static WaitOnConsensusCommitResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static WaitOnConsensusCommitResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static WaitOnConsensusCommitResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static WaitOnConsensusCommitResponse parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WaitOnConsensusCommitResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static WaitOnConsensusCommitResponse parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static WaitOnConsensusCommitResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static WaitOnConsensusCommitResponse parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WaitOnConsensusCommitResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return WaitOnConsensusCommitResponse.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(WaitOnConsensusCommitResponse prototype) {
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

        public static WaitOnConsensusCommitResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<WaitOnConsensusCommitResponse> parser() {
            return PARSER;
        }

        public Parser<WaitOnConsensusCommitResponse> getParserForType() {
            return PARSER;
        }

        @Override
        public WaitOnConsensusCommitResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements WaitOnConsensusCommitResponseOrBuilder {
            private int bitField0_;
            private boolean ok_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_WaitOnConsensusCommitResponse_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_WaitOnConsensusCommitResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(WaitOnConsensusCommitResponse.class, Builder.class);
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
                this.ok_ = false;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_WaitOnConsensusCommitResponse_descriptor;
            }

            @Override
            public WaitOnConsensusCommitResponse getDefaultInstanceForType() {
                return WaitOnConsensusCommitResponse.getDefaultInstance();
            }

            @Override
            public WaitOnConsensusCommitResponse build() {
                WaitOnConsensusCommitResponse result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public WaitOnConsensusCommitResponse buildPartial() {
                WaitOnConsensusCommitResponse result = new WaitOnConsensusCommitResponse(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(WaitOnConsensusCommitResponse result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.ok_ = this.ok_;
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
                if (other instanceof WaitOnConsensusCommitResponse) {
                    return this.mergeFrom((WaitOnConsensusCommitResponse)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(WaitOnConsensusCommitResponse other) {
                if (other == WaitOnConsensusCommitResponse.getDefaultInstance()) {
                    return this;
                }
                if (other.getOk()) {
                    this.setOk(other.getOk());
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
                                this.ok_ = input.readBool();
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
            public boolean getOk() {
                return this.ok_;
            }

            public Builder setOk(boolean value) {
                this.ok_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearOk() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.ok_ = false;
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

    public static interface WaitOnConsensusCommitResponseOrBuilder
    extends MessageOrBuilder {
        public boolean getOk();
    }

    public static final class WaitOnConsensusCommitRequest
    extends GeneratedMessageV3
    implements WaitOnConsensusCommitRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int COMMIT_FIELD_NUMBER = 1;
        private long commit_ = 0L;
        public static final int TERM_FIELD_NUMBER = 2;
        private long term_ = 0L;
        public static final int TIMEOUT_FIELD_NUMBER = 3;
        private long timeout_ = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final WaitOnConsensusCommitRequest DEFAULT_INSTANCE = new WaitOnConsensusCommitRequest();
        private static final Parser<WaitOnConsensusCommitRequest> PARSER = new AbstractParser<WaitOnConsensusCommitRequest>(){

            @Override
            public WaitOnConsensusCommitRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = WaitOnConsensusCommitRequest.newBuilder();
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

        private WaitOnConsensusCommitRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private WaitOnConsensusCommitRequest() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new WaitOnConsensusCommitRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_WaitOnConsensusCommitRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_WaitOnConsensusCommitRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(WaitOnConsensusCommitRequest.class, Builder.class);
        }

        @Override
        public long getCommit() {
            return this.commit_;
        }

        @Override
        public long getTerm() {
            return this.term_;
        }

        @Override
        public long getTimeout() {
            return this.timeout_;
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
            if (this.commit_ != 0L) {
                output.writeInt64(1, this.commit_);
            }
            if (this.term_ != 0L) {
                output.writeInt64(2, this.term_);
            }
            if (this.timeout_ != 0L) {
                output.writeInt64(3, this.timeout_);
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
            if (this.commit_ != 0L) {
                size += CodedOutputStream.computeInt64Size(1, this.commit_);
            }
            if (this.term_ != 0L) {
                size += CodedOutputStream.computeInt64Size(2, this.term_);
            }
            if (this.timeout_ != 0L) {
                size += CodedOutputStream.computeInt64Size(3, this.timeout_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof WaitOnConsensusCommitRequest)) {
                return super.equals(obj);
            }
            WaitOnConsensusCommitRequest other = (WaitOnConsensusCommitRequest)obj;
            if (this.getCommit() != other.getCommit()) {
                return false;
            }
            if (this.getTerm() != other.getTerm()) {
                return false;
            }
            if (this.getTimeout() != other.getTimeout()) {
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
            hash = 19 * hash + WaitOnConsensusCommitRequest.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashLong(this.getCommit());
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashLong(this.getTerm());
            hash = 37 * hash + 3;
            hash = 53 * hash + Internal.hashLong(this.getTimeout());
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static WaitOnConsensusCommitRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static WaitOnConsensusCommitRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static WaitOnConsensusCommitRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static WaitOnConsensusCommitRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static WaitOnConsensusCommitRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static WaitOnConsensusCommitRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static WaitOnConsensusCommitRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WaitOnConsensusCommitRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static WaitOnConsensusCommitRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static WaitOnConsensusCommitRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static WaitOnConsensusCommitRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WaitOnConsensusCommitRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return WaitOnConsensusCommitRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(WaitOnConsensusCommitRequest prototype) {
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

        public static WaitOnConsensusCommitRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<WaitOnConsensusCommitRequest> parser() {
            return PARSER;
        }

        public Parser<WaitOnConsensusCommitRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public WaitOnConsensusCommitRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements WaitOnConsensusCommitRequestOrBuilder {
            private int bitField0_;
            private long commit_;
            private long term_;
            private long timeout_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_WaitOnConsensusCommitRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_WaitOnConsensusCommitRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(WaitOnConsensusCommitRequest.class, Builder.class);
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
                this.commit_ = 0L;
                this.term_ = 0L;
                this.timeout_ = 0L;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_WaitOnConsensusCommitRequest_descriptor;
            }

            @Override
            public WaitOnConsensusCommitRequest getDefaultInstanceForType() {
                return WaitOnConsensusCommitRequest.getDefaultInstance();
            }

            @Override
            public WaitOnConsensusCommitRequest build() {
                WaitOnConsensusCommitRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public WaitOnConsensusCommitRequest buildPartial() {
                WaitOnConsensusCommitRequest result = new WaitOnConsensusCommitRequest(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(WaitOnConsensusCommitRequest result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.commit_ = this.commit_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.term_ = this.term_;
                }
                if ((from_bitField0_ & 4) != 0) {
                    result.timeout_ = this.timeout_;
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
                if (other instanceof WaitOnConsensusCommitRequest) {
                    return this.mergeFrom((WaitOnConsensusCommitRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(WaitOnConsensusCommitRequest other) {
                if (other == WaitOnConsensusCommitRequest.getDefaultInstance()) {
                    return this;
                }
                if (other.getCommit() != 0L) {
                    this.setCommit(other.getCommit());
                }
                if (other.getTerm() != 0L) {
                    this.setTerm(other.getTerm());
                }
                if (other.getTimeout() != 0L) {
                    this.setTimeout(other.getTimeout());
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
                            case 8: {
                                this.commit_ = input.readInt64();
                                this.bitField0_ |= 1;
                                continue block11;
                            }
                            case 16: {
                                this.term_ = input.readInt64();
                                this.bitField0_ |= 2;
                                continue block11;
                            }
                            case 24: {
                                this.timeout_ = input.readInt64();
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
            public long getCommit() {
                return this.commit_;
            }

            public Builder setCommit(long value) {
                this.commit_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearCommit() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.commit_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public long getTerm() {
                return this.term_;
            }

            public Builder setTerm(long value) {
                this.term_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearTerm() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.term_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public long getTimeout() {
                return this.timeout_;
            }

            public Builder setTimeout(long value) {
                this.timeout_ = value;
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder clearTimeout() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.timeout_ = 0L;
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

    public static interface WaitOnConsensusCommitRequestOrBuilder
    extends MessageOrBuilder {
        public long getCommit();

        public long getTerm();

        public long getTimeout();
    }

    public static final class GetConsensusCommitResponse
    extends GeneratedMessageV3
    implements GetConsensusCommitResponseOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int COMMIT_FIELD_NUMBER = 1;
        private long commit_ = 0L;
        public static final int TERM_FIELD_NUMBER = 2;
        private long term_ = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final GetConsensusCommitResponse DEFAULT_INSTANCE = new GetConsensusCommitResponse();
        private static final Parser<GetConsensusCommitResponse> PARSER = new AbstractParser<GetConsensusCommitResponse>(){

            @Override
            public GetConsensusCommitResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = GetConsensusCommitResponse.newBuilder();
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

        private GetConsensusCommitResponse(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private GetConsensusCommitResponse() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new GetConsensusCommitResponse();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_GetConsensusCommitResponse_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_GetConsensusCommitResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(GetConsensusCommitResponse.class, Builder.class);
        }

        @Override
        public long getCommit() {
            return this.commit_;
        }

        @Override
        public long getTerm() {
            return this.term_;
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
            if (this.commit_ != 0L) {
                output.writeInt64(1, this.commit_);
            }
            if (this.term_ != 0L) {
                output.writeInt64(2, this.term_);
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
            if (this.commit_ != 0L) {
                size += CodedOutputStream.computeInt64Size(1, this.commit_);
            }
            if (this.term_ != 0L) {
                size += CodedOutputStream.computeInt64Size(2, this.term_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof GetConsensusCommitResponse)) {
                return super.equals(obj);
            }
            GetConsensusCommitResponse other = (GetConsensusCommitResponse)obj;
            if (this.getCommit() != other.getCommit()) {
                return false;
            }
            if (this.getTerm() != other.getTerm()) {
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
            hash = 19 * hash + GetConsensusCommitResponse.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashLong(this.getCommit());
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashLong(this.getTerm());
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static GetConsensusCommitResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetConsensusCommitResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetConsensusCommitResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetConsensusCommitResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetConsensusCommitResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetConsensusCommitResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetConsensusCommitResponse parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GetConsensusCommitResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static GetConsensusCommitResponse parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static GetConsensusCommitResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static GetConsensusCommitResponse parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GetConsensusCommitResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return GetConsensusCommitResponse.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(GetConsensusCommitResponse prototype) {
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

        public static GetConsensusCommitResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GetConsensusCommitResponse> parser() {
            return PARSER;
        }

        public Parser<GetConsensusCommitResponse> getParserForType() {
            return PARSER;
        }

        @Override
        public GetConsensusCommitResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements GetConsensusCommitResponseOrBuilder {
            private int bitField0_;
            private long commit_;
            private long term_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_GetConsensusCommitResponse_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_GetConsensusCommitResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(GetConsensusCommitResponse.class, Builder.class);
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
                this.commit_ = 0L;
                this.term_ = 0L;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_GetConsensusCommitResponse_descriptor;
            }

            @Override
            public GetConsensusCommitResponse getDefaultInstanceForType() {
                return GetConsensusCommitResponse.getDefaultInstance();
            }

            @Override
            public GetConsensusCommitResponse build() {
                GetConsensusCommitResponse result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public GetConsensusCommitResponse buildPartial() {
                GetConsensusCommitResponse result = new GetConsensusCommitResponse(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(GetConsensusCommitResponse result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.commit_ = this.commit_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.term_ = this.term_;
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
                if (other instanceof GetConsensusCommitResponse) {
                    return this.mergeFrom((GetConsensusCommitResponse)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(GetConsensusCommitResponse other) {
                if (other == GetConsensusCommitResponse.getDefaultInstance()) {
                    return this;
                }
                if (other.getCommit() != 0L) {
                    this.setCommit(other.getCommit());
                }
                if (other.getTerm() != 0L) {
                    this.setTerm(other.getTerm());
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
                            case 8: {
                                this.commit_ = input.readInt64();
                                this.bitField0_ |= 1;
                                continue block10;
                            }
                            case 16: {
                                this.term_ = input.readInt64();
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
            public long getCommit() {
                return this.commit_;
            }

            public Builder setCommit(long value) {
                this.commit_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearCommit() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.commit_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public long getTerm() {
                return this.term_;
            }

            public Builder setTerm(long value) {
                this.term_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearTerm() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.term_ = 0L;
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

    public static interface GetConsensusCommitResponseOrBuilder
    extends MessageOrBuilder {
        public long getCommit();

        public long getTerm();
    }

    public static final class GetConsensusCommitRequest
    extends GeneratedMessageV3
    implements GetConsensusCommitRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final GetConsensusCommitRequest DEFAULT_INSTANCE = new GetConsensusCommitRequest();
        private static final Parser<GetConsensusCommitRequest> PARSER = new AbstractParser<GetConsensusCommitRequest>(){

            @Override
            public GetConsensusCommitRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = GetConsensusCommitRequest.newBuilder();
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

        private GetConsensusCommitRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private GetConsensusCommitRequest() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new GetConsensusCommitRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_GetConsensusCommitRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_GetConsensusCommitRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(GetConsensusCommitRequest.class, Builder.class);
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
            if (!(obj instanceof GetConsensusCommitRequest)) {
                return super.equals(obj);
            }
            GetConsensusCommitRequest other = (GetConsensusCommitRequest)obj;
            return this.getUnknownFields().equals(other.getUnknownFields());
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + GetConsensusCommitRequest.getDescriptor().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static GetConsensusCommitRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetConsensusCommitRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetConsensusCommitRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetConsensusCommitRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetConsensusCommitRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetConsensusCommitRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetConsensusCommitRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GetConsensusCommitRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static GetConsensusCommitRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static GetConsensusCommitRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static GetConsensusCommitRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GetConsensusCommitRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return GetConsensusCommitRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(GetConsensusCommitRequest prototype) {
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

        public static GetConsensusCommitRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GetConsensusCommitRequest> parser() {
            return PARSER;
        }

        public Parser<GetConsensusCommitRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public GetConsensusCommitRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements GetConsensusCommitRequestOrBuilder {
            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_GetConsensusCommitRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_GetConsensusCommitRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(GetConsensusCommitRequest.class, Builder.class);
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
                return internal_static_qdrant_GetConsensusCommitRequest_descriptor;
            }

            @Override
            public GetConsensusCommitRequest getDefaultInstanceForType() {
                return GetConsensusCommitRequest.getDefaultInstance();
            }

            @Override
            public GetConsensusCommitRequest build() {
                GetConsensusCommitRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public GetConsensusCommitRequest buildPartial() {
                GetConsensusCommitRequest result = new GetConsensusCommitRequest(this);
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
                if (other instanceof GetConsensusCommitRequest) {
                    return this.mergeFrom((GetConsensusCommitRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(GetConsensusCommitRequest other) {
                if (other == GetConsensusCommitRequest.getDefaultInstance()) {
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

    public static interface GetConsensusCommitRequestOrBuilder
    extends MessageOrBuilder {
    }
}

