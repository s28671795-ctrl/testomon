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
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import io.qdrant.client.grpc.Collections;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class CollectionsInternalService {
    private static final Descriptors.Descriptor internal_static_qdrant_GetCollectionInfoRequestInternal_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_GetCollectionInfoRequestInternal_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_InitiateShardTransferRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_InitiateShardTransferRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_WaitForShardStateRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_WaitForShardStateRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_GetShardRecoveryPointRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_GetShardRecoveryPointRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_GetShardRecoveryPointResponse_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_GetShardRecoveryPointResponse_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_RecoveryPoint_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_RecoveryPoint_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_RecoveryPointClockTag_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_RecoveryPointClockTag_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_UpdateShardCutoffPointRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_UpdateShardCutoffPointRequest_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private CollectionsInternalService() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        CollectionsInternalService.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\"collections_internal_service.proto\u0012\u0006qdrant\u001a\u0011collections.proto\"y\n GetCollectionInfoRequestInternal\u0012C\n\u0019get_collectionInfoRequest\u0018\u0001 \u0001(\u000b2 .qdrant.GetCollectionInfoRequest\u0012\u0010\n\bshard_id\u0018\u0002 \u0001(\r\"I\n\u001cInitiateShardTransferRequest\u0012\u0017\n\u000fcollection_name\u0018\u0001 \u0001(\t\u0012\u0010\n\bshard_id\u0018\u0002 \u0001(\r\"{\n\u0018WaitForShardStateRequest\u0012\u0017\n\u000fcollection_name\u0018\u0001 \u0001(\t\u0012\u0010\n\bshard_id\u0018\u0002 \u0001(\r\u0012#\n\u0005state\u0018\u0003 \u0001(\u000e2\u0014.qdrant.ReplicaState\u0012\u000f\n\u0007timeout\u0018\u0004 \u0001(\u0004\"I\n\u001cGetShardRecoveryPointRequest\u0012\u0017\n\u000fcollection_name\u0018\u0001 \u0001(\t\u0012\u0010\n\bshard_id\u0018\u0002 \u0001(\r\"\\\n\u001dGetShardRecoveryPointResponse\u0012-\n\u000erecovery_point\u0018\u0001 \u0001(\u000b2\u0015.qdrant.RecoveryPoint\u0012\f\n\u0004time\u0018\u0002 \u0001(\u0001\">\n\rRecoveryPoint\u0012-\n\u0006clocks\u0018\u0001 \u0003(\u000b2\u001d.qdrant.RecoveryPointClockTag\"]\n\u0015RecoveryPointClockTag\u0012\u000f\n\u0007peer_id\u0018\u0001 \u0001(\u0004\u0012\u0010\n\bclock_id\u0018\u0002 \u0001(\r\u0012\u0012\n\nclock_tick\u0018\u0003 \u0001(\u0004\u0012\r\n\u0005token\u0018\u0004 \u0001(\u0004\"q\n\u001dUpdateShardCutoffPointRequest\u0012\u0017\n\u000fcollection_name\u0018\u0001 \u0001(\t\u0012\u0010\n\bshard_id\u0018\u0002 \u0001(\r\u0012%\n\u0006cutoff\u0018\u0003 \u0001(\u000b2\u0015.qdrant.RecoveryPoint2\u00f2\u0003\n\u0013CollectionsInternal\u0012T\n\u0003Get\u0012(.qdrant.GetCollectionInfoRequestInternal\u001a!.qdrant.GetCollectionInfoResponse\"\u0000\u0012W\n\bInitiate\u0012$.qdrant.InitiateShardTransferRequest\u001a#.qdrant.CollectionOperationResponse\"\u0000\u0012\\\n\u0011WaitForShardState\u0012 .qdrant.WaitForShardStateRequest\u001a#.qdrant.CollectionOperationResponse\"\u0000\u0012f\n\u0015GetShardRecoveryPoint\u0012$.qdrant.GetShardRecoveryPointRequest\u001a%.qdrant.GetShardRecoveryPointResponse\"\u0000\u0012f\n\u0016UpdateShardCutoffPoint\u0012%.qdrant.UpdateShardCutoffPointRequest\u001a#.qdrant.CollectionOperationResponse\"\u0000B,\n\u0015io.qdrant.client.grpc\u00aa\u0002\u0012Qdrant.Client.Grpcb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{Collections.getDescriptor()});
        internal_static_qdrant_GetCollectionInfoRequestInternal_descriptor = CollectionsInternalService.getDescriptor().getMessageTypes().get(0);
        internal_static_qdrant_GetCollectionInfoRequestInternal_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_GetCollectionInfoRequestInternal_descriptor, new String[]{"GetCollectionInfoRequest", "ShardId"});
        internal_static_qdrant_InitiateShardTransferRequest_descriptor = CollectionsInternalService.getDescriptor().getMessageTypes().get(1);
        internal_static_qdrant_InitiateShardTransferRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_InitiateShardTransferRequest_descriptor, new String[]{"CollectionName", "ShardId"});
        internal_static_qdrant_WaitForShardStateRequest_descriptor = CollectionsInternalService.getDescriptor().getMessageTypes().get(2);
        internal_static_qdrant_WaitForShardStateRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_WaitForShardStateRequest_descriptor, new String[]{"CollectionName", "ShardId", "State", "Timeout"});
        internal_static_qdrant_GetShardRecoveryPointRequest_descriptor = CollectionsInternalService.getDescriptor().getMessageTypes().get(3);
        internal_static_qdrant_GetShardRecoveryPointRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_GetShardRecoveryPointRequest_descriptor, new String[]{"CollectionName", "ShardId"});
        internal_static_qdrant_GetShardRecoveryPointResponse_descriptor = CollectionsInternalService.getDescriptor().getMessageTypes().get(4);
        internal_static_qdrant_GetShardRecoveryPointResponse_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_GetShardRecoveryPointResponse_descriptor, new String[]{"RecoveryPoint", "Time"});
        internal_static_qdrant_RecoveryPoint_descriptor = CollectionsInternalService.getDescriptor().getMessageTypes().get(5);
        internal_static_qdrant_RecoveryPoint_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_RecoveryPoint_descriptor, new String[]{"Clocks"});
        internal_static_qdrant_RecoveryPointClockTag_descriptor = CollectionsInternalService.getDescriptor().getMessageTypes().get(6);
        internal_static_qdrant_RecoveryPointClockTag_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_RecoveryPointClockTag_descriptor, new String[]{"PeerId", "ClockId", "ClockTick", "Token"});
        internal_static_qdrant_UpdateShardCutoffPointRequest_descriptor = CollectionsInternalService.getDescriptor().getMessageTypes().get(7);
        internal_static_qdrant_UpdateShardCutoffPointRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_UpdateShardCutoffPointRequest_descriptor, new String[]{"CollectionName", "ShardId", "Cutoff"});
        Collections.getDescriptor();
    }

    public static final class UpdateShardCutoffPointRequest
    extends GeneratedMessageV3
    implements UpdateShardCutoffPointRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int COLLECTION_NAME_FIELD_NUMBER = 1;
        private volatile Object collectionName_ = "";
        public static final int SHARD_ID_FIELD_NUMBER = 2;
        private int shardId_ = 0;
        public static final int CUTOFF_FIELD_NUMBER = 3;
        private RecoveryPoint cutoff_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final UpdateShardCutoffPointRequest DEFAULT_INSTANCE = new UpdateShardCutoffPointRequest();
        private static final Parser<UpdateShardCutoffPointRequest> PARSER = new AbstractParser<UpdateShardCutoffPointRequest>(){

            @Override
            public UpdateShardCutoffPointRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = UpdateShardCutoffPointRequest.newBuilder();
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

        private UpdateShardCutoffPointRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private UpdateShardCutoffPointRequest() {
            this.collectionName_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new UpdateShardCutoffPointRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_UpdateShardCutoffPointRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_UpdateShardCutoffPointRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(UpdateShardCutoffPointRequest.class, Builder.class);
        }

        @Override
        public String getCollectionName() {
            Object ref = this.collectionName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.collectionName_ = s2;
            return s2;
        }

        @Override
        public ByteString getCollectionNameBytes() {
            Object ref = this.collectionName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.collectionName_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public int getShardId() {
            return this.shardId_;
        }

        @Override
        public boolean hasCutoff() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public RecoveryPoint getCutoff() {
            return this.cutoff_ == null ? RecoveryPoint.getDefaultInstance() : this.cutoff_;
        }

        @Override
        public RecoveryPointOrBuilder getCutoffOrBuilder() {
            return this.cutoff_ == null ? RecoveryPoint.getDefaultInstance() : this.cutoff_;
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
            if (!GeneratedMessageV3.isStringEmpty(this.collectionName_)) {
                GeneratedMessageV3.writeString(output, 1, this.collectionName_);
            }
            if (this.shardId_ != 0) {
                output.writeUInt32(2, this.shardId_);
            }
            if ((this.bitField0_ & 1) != 0) {
                output.writeMessage(3, this.getCutoff());
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
            if (!GeneratedMessageV3.isStringEmpty(this.collectionName_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.collectionName_);
            }
            if (this.shardId_ != 0) {
                size += CodedOutputStream.computeUInt32Size(2, this.shardId_);
            }
            if ((this.bitField0_ & 1) != 0) {
                size += CodedOutputStream.computeMessageSize(3, this.getCutoff());
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof UpdateShardCutoffPointRequest)) {
                return super.equals(obj);
            }
            UpdateShardCutoffPointRequest other = (UpdateShardCutoffPointRequest)obj;
            if (!this.getCollectionName().equals(other.getCollectionName())) {
                return false;
            }
            if (this.getShardId() != other.getShardId()) {
                return false;
            }
            if (this.hasCutoff() != other.hasCutoff()) {
                return false;
            }
            if (this.hasCutoff() && !this.getCutoff().equals(other.getCutoff())) {
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
            hash = 19 * hash + UpdateShardCutoffPointRequest.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getCollectionName().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getShardId();
            if (this.hasCutoff()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getCutoff().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static UpdateShardCutoffPointRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static UpdateShardCutoffPointRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static UpdateShardCutoffPointRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static UpdateShardCutoffPointRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static UpdateShardCutoffPointRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static UpdateShardCutoffPointRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static UpdateShardCutoffPointRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static UpdateShardCutoffPointRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static UpdateShardCutoffPointRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static UpdateShardCutoffPointRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static UpdateShardCutoffPointRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static UpdateShardCutoffPointRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return UpdateShardCutoffPointRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(UpdateShardCutoffPointRequest prototype) {
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

        public static UpdateShardCutoffPointRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<UpdateShardCutoffPointRequest> parser() {
            return PARSER;
        }

        public Parser<UpdateShardCutoffPointRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public UpdateShardCutoffPointRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements UpdateShardCutoffPointRequestOrBuilder {
            private int bitField0_;
            private Object collectionName_ = "";
            private int shardId_;
            private RecoveryPoint cutoff_;
            private SingleFieldBuilderV3<RecoveryPoint, RecoveryPoint.Builder, RecoveryPointOrBuilder> cutoffBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_UpdateShardCutoffPointRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_UpdateShardCutoffPointRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(UpdateShardCutoffPointRequest.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    this.getCutoffFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                this.collectionName_ = "";
                this.shardId_ = 0;
                this.cutoff_ = null;
                if (this.cutoffBuilder_ != null) {
                    this.cutoffBuilder_.dispose();
                    this.cutoffBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_UpdateShardCutoffPointRequest_descriptor;
            }

            @Override
            public UpdateShardCutoffPointRequest getDefaultInstanceForType() {
                return UpdateShardCutoffPointRequest.getDefaultInstance();
            }

            @Override
            public UpdateShardCutoffPointRequest build() {
                UpdateShardCutoffPointRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public UpdateShardCutoffPointRequest buildPartial() {
                UpdateShardCutoffPointRequest result = new UpdateShardCutoffPointRequest(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(UpdateShardCutoffPointRequest result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.collectionName_ = this.collectionName_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.shardId_ = this.shardId_;
                }
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 4) != 0) {
                    result.cutoff_ = this.cutoffBuilder_ == null ? this.cutoff_ : this.cutoffBuilder_.build();
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
                if (other instanceof UpdateShardCutoffPointRequest) {
                    return this.mergeFrom((UpdateShardCutoffPointRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(UpdateShardCutoffPointRequest other) {
                if (other == UpdateShardCutoffPointRequest.getDefaultInstance()) {
                    return this;
                }
                if (!other.getCollectionName().isEmpty()) {
                    this.collectionName_ = other.collectionName_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (other.getShardId() != 0) {
                    this.setShardId(other.getShardId());
                }
                if (other.hasCutoff()) {
                    this.mergeCutoff(other.getCutoff());
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
                                this.collectionName_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block11;
                            }
                            case 16: {
                                this.shardId_ = input.readUInt32();
                                this.bitField0_ |= 2;
                                continue block11;
                            }
                            case 26: {
                                input.readMessage(this.getCutoffFieldBuilder().getBuilder(), extensionRegistry);
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
            public String getCollectionName() {
                Object ref = this.collectionName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.collectionName_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getCollectionNameBytes() {
                Object ref = this.collectionName_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.collectionName_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setCollectionName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.collectionName_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearCollectionName() {
                this.collectionName_ = UpdateShardCutoffPointRequest.getDefaultInstance().getCollectionName();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setCollectionNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                UpdateShardCutoffPointRequest.checkByteStringIsUtf8(value);
                this.collectionName_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public int getShardId() {
                return this.shardId_;
            }

            public Builder setShardId(int value) {
                this.shardId_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearShardId() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.shardId_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasCutoff() {
                return (this.bitField0_ & 4) != 0;
            }

            @Override
            public RecoveryPoint getCutoff() {
                if (this.cutoffBuilder_ == null) {
                    return this.cutoff_ == null ? RecoveryPoint.getDefaultInstance() : this.cutoff_;
                }
                return this.cutoffBuilder_.getMessage();
            }

            public Builder setCutoff(RecoveryPoint value) {
                if (this.cutoffBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.cutoff_ = value;
                } else {
                    this.cutoffBuilder_.setMessage(value);
                }
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder setCutoff(RecoveryPoint.Builder builderForValue) {
                if (this.cutoffBuilder_ == null) {
                    this.cutoff_ = builderForValue.build();
                } else {
                    this.cutoffBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder mergeCutoff(RecoveryPoint value) {
                if (this.cutoffBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 0 && this.cutoff_ != null && this.cutoff_ != RecoveryPoint.getDefaultInstance()) {
                        this.getCutoffBuilder().mergeFrom(value);
                    } else {
                        this.cutoff_ = value;
                    }
                } else {
                    this.cutoffBuilder_.mergeFrom(value);
                }
                if (this.cutoff_ != null) {
                    this.bitField0_ |= 4;
                    this.onChanged();
                }
                return this;
            }

            public Builder clearCutoff() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.cutoff_ = null;
                if (this.cutoffBuilder_ != null) {
                    this.cutoffBuilder_.dispose();
                    this.cutoffBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public RecoveryPoint.Builder getCutoffBuilder() {
                this.bitField0_ |= 4;
                this.onChanged();
                return this.getCutoffFieldBuilder().getBuilder();
            }

            @Override
            public RecoveryPointOrBuilder getCutoffOrBuilder() {
                if (this.cutoffBuilder_ != null) {
                    return this.cutoffBuilder_.getMessageOrBuilder();
                }
                return this.cutoff_ == null ? RecoveryPoint.getDefaultInstance() : this.cutoff_;
            }

            private SingleFieldBuilderV3<RecoveryPoint, RecoveryPoint.Builder, RecoveryPointOrBuilder> getCutoffFieldBuilder() {
                if (this.cutoffBuilder_ == null) {
                    this.cutoffBuilder_ = new SingleFieldBuilderV3(this.getCutoff(), this.getParentForChildren(), this.isClean());
                    this.cutoff_ = null;
                }
                return this.cutoffBuilder_;
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

    public static interface UpdateShardCutoffPointRequestOrBuilder
    extends MessageOrBuilder {
        public String getCollectionName();

        public ByteString getCollectionNameBytes();

        public int getShardId();

        public boolean hasCutoff();

        public RecoveryPoint getCutoff();

        public RecoveryPointOrBuilder getCutoffOrBuilder();
    }

    public static final class RecoveryPointClockTag
    extends GeneratedMessageV3
    implements RecoveryPointClockTagOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int PEER_ID_FIELD_NUMBER = 1;
        private long peerId_ = 0L;
        public static final int CLOCK_ID_FIELD_NUMBER = 2;
        private int clockId_ = 0;
        public static final int CLOCK_TICK_FIELD_NUMBER = 3;
        private long clockTick_ = 0L;
        public static final int TOKEN_FIELD_NUMBER = 4;
        private long token_ = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final RecoveryPointClockTag DEFAULT_INSTANCE = new RecoveryPointClockTag();
        private static final Parser<RecoveryPointClockTag> PARSER = new AbstractParser<RecoveryPointClockTag>(){

            @Override
            public RecoveryPointClockTag parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = RecoveryPointClockTag.newBuilder();
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

        private RecoveryPointClockTag(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private RecoveryPointClockTag() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new RecoveryPointClockTag();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_RecoveryPointClockTag_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_RecoveryPointClockTag_fieldAccessorTable.ensureFieldAccessorsInitialized(RecoveryPointClockTag.class, Builder.class);
        }

        @Override
        public long getPeerId() {
            return this.peerId_;
        }

        @Override
        public int getClockId() {
            return this.clockId_;
        }

        @Override
        public long getClockTick() {
            return this.clockTick_;
        }

        @Override
        public long getToken() {
            return this.token_;
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
            if (this.peerId_ != 0L) {
                output.writeUInt64(1, this.peerId_);
            }
            if (this.clockId_ != 0) {
                output.writeUInt32(2, this.clockId_);
            }
            if (this.clockTick_ != 0L) {
                output.writeUInt64(3, this.clockTick_);
            }
            if (this.token_ != 0L) {
                output.writeUInt64(4, this.token_);
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
            if (this.peerId_ != 0L) {
                size += CodedOutputStream.computeUInt64Size(1, this.peerId_);
            }
            if (this.clockId_ != 0) {
                size += CodedOutputStream.computeUInt32Size(2, this.clockId_);
            }
            if (this.clockTick_ != 0L) {
                size += CodedOutputStream.computeUInt64Size(3, this.clockTick_);
            }
            if (this.token_ != 0L) {
                size += CodedOutputStream.computeUInt64Size(4, this.token_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof RecoveryPointClockTag)) {
                return super.equals(obj);
            }
            RecoveryPointClockTag other = (RecoveryPointClockTag)obj;
            if (this.getPeerId() != other.getPeerId()) {
                return false;
            }
            if (this.getClockId() != other.getClockId()) {
                return false;
            }
            if (this.getClockTick() != other.getClockTick()) {
                return false;
            }
            if (this.getToken() != other.getToken()) {
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
            hash = 19 * hash + RecoveryPointClockTag.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashLong(this.getPeerId());
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getClockId();
            hash = 37 * hash + 3;
            hash = 53 * hash + Internal.hashLong(this.getClockTick());
            hash = 37 * hash + 4;
            hash = 53 * hash + Internal.hashLong(this.getToken());
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static RecoveryPointClockTag parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RecoveryPointClockTag parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RecoveryPointClockTag parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RecoveryPointClockTag parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RecoveryPointClockTag parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RecoveryPointClockTag parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RecoveryPointClockTag parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RecoveryPointClockTag parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RecoveryPointClockTag parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static RecoveryPointClockTag parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RecoveryPointClockTag parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RecoveryPointClockTag parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return RecoveryPointClockTag.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RecoveryPointClockTag prototype) {
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

        public static RecoveryPointClockTag getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RecoveryPointClockTag> parser() {
            return PARSER;
        }

        public Parser<RecoveryPointClockTag> getParserForType() {
            return PARSER;
        }

        @Override
        public RecoveryPointClockTag getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements RecoveryPointClockTagOrBuilder {
            private int bitField0_;
            private long peerId_;
            private int clockId_;
            private long clockTick_;
            private long token_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_RecoveryPointClockTag_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_RecoveryPointClockTag_fieldAccessorTable.ensureFieldAccessorsInitialized(RecoveryPointClockTag.class, Builder.class);
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
                this.peerId_ = 0L;
                this.clockId_ = 0;
                this.clockTick_ = 0L;
                this.token_ = 0L;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_RecoveryPointClockTag_descriptor;
            }

            @Override
            public RecoveryPointClockTag getDefaultInstanceForType() {
                return RecoveryPointClockTag.getDefaultInstance();
            }

            @Override
            public RecoveryPointClockTag build() {
                RecoveryPointClockTag result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public RecoveryPointClockTag buildPartial() {
                RecoveryPointClockTag result = new RecoveryPointClockTag(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(RecoveryPointClockTag result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.peerId_ = this.peerId_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.clockId_ = this.clockId_;
                }
                if ((from_bitField0_ & 4) != 0) {
                    result.clockTick_ = this.clockTick_;
                }
                if ((from_bitField0_ & 8) != 0) {
                    result.token_ = this.token_;
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
                if (other instanceof RecoveryPointClockTag) {
                    return this.mergeFrom((RecoveryPointClockTag)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(RecoveryPointClockTag other) {
                if (other == RecoveryPointClockTag.getDefaultInstance()) {
                    return this;
                }
                if (other.getPeerId() != 0L) {
                    this.setPeerId(other.getPeerId());
                }
                if (other.getClockId() != 0) {
                    this.setClockId(other.getClockId());
                }
                if (other.getClockTick() != 0L) {
                    this.setClockTick(other.getClockTick());
                }
                if (other.getToken() != 0L) {
                    this.setToken(other.getToken());
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
                    block12: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block12;
                            }
                            case 8: {
                                this.peerId_ = input.readUInt64();
                                this.bitField0_ |= 1;
                                continue block12;
                            }
                            case 16: {
                                this.clockId_ = input.readUInt32();
                                this.bitField0_ |= 2;
                                continue block12;
                            }
                            case 24: {
                                this.clockTick_ = input.readUInt64();
                                this.bitField0_ |= 4;
                                continue block12;
                            }
                            case 32: {
                                this.token_ = input.readUInt64();
                                this.bitField0_ |= 8;
                                continue block12;
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
            public long getPeerId() {
                return this.peerId_;
            }

            public Builder setPeerId(long value) {
                this.peerId_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearPeerId() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.peerId_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public int getClockId() {
                return this.clockId_;
            }

            public Builder setClockId(int value) {
                this.clockId_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearClockId() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.clockId_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public long getClockTick() {
                return this.clockTick_;
            }

            public Builder setClockTick(long value) {
                this.clockTick_ = value;
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder clearClockTick() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.clockTick_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public long getToken() {
                return this.token_;
            }

            public Builder setToken(long value) {
                this.token_ = value;
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder clearToken() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.token_ = 0L;
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

    public static interface RecoveryPointClockTagOrBuilder
    extends MessageOrBuilder {
        public long getPeerId();

        public int getClockId();

        public long getClockTick();

        public long getToken();
    }

    public static final class RecoveryPoint
    extends GeneratedMessageV3
    implements RecoveryPointOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int CLOCKS_FIELD_NUMBER = 1;
        private List<RecoveryPointClockTag> clocks_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final RecoveryPoint DEFAULT_INSTANCE = new RecoveryPoint();
        private static final Parser<RecoveryPoint> PARSER = new AbstractParser<RecoveryPoint>(){

            @Override
            public RecoveryPoint parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = RecoveryPoint.newBuilder();
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

        private RecoveryPoint(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private RecoveryPoint() {
            this.clocks_ = java.util.Collections.emptyList();
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new RecoveryPoint();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_RecoveryPoint_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_RecoveryPoint_fieldAccessorTable.ensureFieldAccessorsInitialized(RecoveryPoint.class, Builder.class);
        }

        @Override
        public List<RecoveryPointClockTag> getClocksList() {
            return this.clocks_;
        }

        @Override
        public List<? extends RecoveryPointClockTagOrBuilder> getClocksOrBuilderList() {
            return this.clocks_;
        }

        @Override
        public int getClocksCount() {
            return this.clocks_.size();
        }

        @Override
        public RecoveryPointClockTag getClocks(int index) {
            return this.clocks_.get(index);
        }

        @Override
        public RecoveryPointClockTagOrBuilder getClocksOrBuilder(int index) {
            return this.clocks_.get(index);
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
            for (int i = 0; i < this.clocks_.size(); ++i) {
                output.writeMessage(1, this.clocks_.get(i));
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
            for (int i = 0; i < this.clocks_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, this.clocks_.get(i));
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof RecoveryPoint)) {
                return super.equals(obj);
            }
            RecoveryPoint other = (RecoveryPoint)obj;
            if (!this.getClocksList().equals(other.getClocksList())) {
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
            hash = 19 * hash + RecoveryPoint.getDescriptor().hashCode();
            if (this.getClocksCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getClocksList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static RecoveryPoint parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RecoveryPoint parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RecoveryPoint parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RecoveryPoint parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RecoveryPoint parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RecoveryPoint parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RecoveryPoint parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RecoveryPoint parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RecoveryPoint parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static RecoveryPoint parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RecoveryPoint parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RecoveryPoint parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return RecoveryPoint.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RecoveryPoint prototype) {
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

        public static RecoveryPoint getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RecoveryPoint> parser() {
            return PARSER;
        }

        public Parser<RecoveryPoint> getParserForType() {
            return PARSER;
        }

        @Override
        public RecoveryPoint getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements RecoveryPointOrBuilder {
            private int bitField0_;
            private List<RecoveryPointClockTag> clocks_ = java.util.Collections.emptyList();
            private RepeatedFieldBuilderV3<RecoveryPointClockTag, RecoveryPointClockTag.Builder, RecoveryPointClockTagOrBuilder> clocksBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_RecoveryPoint_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_RecoveryPoint_fieldAccessorTable.ensureFieldAccessorsInitialized(RecoveryPoint.class, Builder.class);
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
                if (this.clocksBuilder_ == null) {
                    this.clocks_ = java.util.Collections.emptyList();
                } else {
                    this.clocks_ = null;
                    this.clocksBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_RecoveryPoint_descriptor;
            }

            @Override
            public RecoveryPoint getDefaultInstanceForType() {
                return RecoveryPoint.getDefaultInstance();
            }

            @Override
            public RecoveryPoint build() {
                RecoveryPoint result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public RecoveryPoint buildPartial() {
                RecoveryPoint result = new RecoveryPoint(this);
                this.buildPartialRepeatedFields(result);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartialRepeatedFields(RecoveryPoint result) {
                if (this.clocksBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 0) {
                        this.clocks_ = java.util.Collections.unmodifiableList(this.clocks_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.clocks_ = this.clocks_;
                } else {
                    result.clocks_ = this.clocksBuilder_.build();
                }
            }

            private void buildPartial0(RecoveryPoint result) {
                int from_bitField0_ = this.bitField0_;
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
                if (other instanceof RecoveryPoint) {
                    return this.mergeFrom((RecoveryPoint)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(RecoveryPoint other) {
                if (other == RecoveryPoint.getDefaultInstance()) {
                    return this;
                }
                if (this.clocksBuilder_ == null) {
                    if (!other.clocks_.isEmpty()) {
                        if (this.clocks_.isEmpty()) {
                            this.clocks_ = other.clocks_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureClocksIsMutable();
                            this.clocks_.addAll(other.clocks_);
                        }
                        this.onChanged();
                    }
                } else if (!other.clocks_.isEmpty()) {
                    if (this.clocksBuilder_.isEmpty()) {
                        this.clocksBuilder_.dispose();
                        this.clocksBuilder_ = null;
                        this.clocks_ = other.clocks_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.clocksBuilder_ = alwaysUseFieldBuilders ? this.getClocksFieldBuilder() : null;
                    } else {
                        this.clocksBuilder_.addAllMessages(other.clocks_);
                    }
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
                                RecoveryPointClockTag m4 = input.readMessage(RecoveryPointClockTag.parser(), extensionRegistry);
                                if (this.clocksBuilder_ == null) {
                                    this.ensureClocksIsMutable();
                                    this.clocks_.add(m4);
                                    continue block9;
                                }
                                this.clocksBuilder_.addMessage(m4);
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

            private void ensureClocksIsMutable() {
                if ((this.bitField0_ & 1) == 0) {
                    this.clocks_ = new ArrayList<RecoveryPointClockTag>(this.clocks_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<RecoveryPointClockTag> getClocksList() {
                if (this.clocksBuilder_ == null) {
                    return java.util.Collections.unmodifiableList(this.clocks_);
                }
                return this.clocksBuilder_.getMessageList();
            }

            @Override
            public int getClocksCount() {
                if (this.clocksBuilder_ == null) {
                    return this.clocks_.size();
                }
                return this.clocksBuilder_.getCount();
            }

            @Override
            public RecoveryPointClockTag getClocks(int index) {
                if (this.clocksBuilder_ == null) {
                    return this.clocks_.get(index);
                }
                return this.clocksBuilder_.getMessage(index);
            }

            public Builder setClocks(int index, RecoveryPointClockTag value) {
                if (this.clocksBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureClocksIsMutable();
                    this.clocks_.set(index, value);
                    this.onChanged();
                } else {
                    this.clocksBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setClocks(int index, RecoveryPointClockTag.Builder builderForValue) {
                if (this.clocksBuilder_ == null) {
                    this.ensureClocksIsMutable();
                    this.clocks_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.clocksBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addClocks(RecoveryPointClockTag value) {
                if (this.clocksBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureClocksIsMutable();
                    this.clocks_.add(value);
                    this.onChanged();
                } else {
                    this.clocksBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addClocks(int index, RecoveryPointClockTag value) {
                if (this.clocksBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureClocksIsMutable();
                    this.clocks_.add(index, value);
                    this.onChanged();
                } else {
                    this.clocksBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addClocks(RecoveryPointClockTag.Builder builderForValue) {
                if (this.clocksBuilder_ == null) {
                    this.ensureClocksIsMutable();
                    this.clocks_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.clocksBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addClocks(int index, RecoveryPointClockTag.Builder builderForValue) {
                if (this.clocksBuilder_ == null) {
                    this.ensureClocksIsMutable();
                    this.clocks_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.clocksBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllClocks(Iterable<? extends RecoveryPointClockTag> values2) {
                if (this.clocksBuilder_ == null) {
                    this.ensureClocksIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.clocks_);
                    this.onChanged();
                } else {
                    this.clocksBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearClocks() {
                if (this.clocksBuilder_ == null) {
                    this.clocks_ = java.util.Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.clocksBuilder_.clear();
                }
                return this;
            }

            public Builder removeClocks(int index) {
                if (this.clocksBuilder_ == null) {
                    this.ensureClocksIsMutable();
                    this.clocks_.remove(index);
                    this.onChanged();
                } else {
                    this.clocksBuilder_.remove(index);
                }
                return this;
            }

            public RecoveryPointClockTag.Builder getClocksBuilder(int index) {
                return this.getClocksFieldBuilder().getBuilder(index);
            }

            @Override
            public RecoveryPointClockTagOrBuilder getClocksOrBuilder(int index) {
                if (this.clocksBuilder_ == null) {
                    return this.clocks_.get(index);
                }
                return this.clocksBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends RecoveryPointClockTagOrBuilder> getClocksOrBuilderList() {
                if (this.clocksBuilder_ != null) {
                    return this.clocksBuilder_.getMessageOrBuilderList();
                }
                return java.util.Collections.unmodifiableList(this.clocks_);
            }

            public RecoveryPointClockTag.Builder addClocksBuilder() {
                return this.getClocksFieldBuilder().addBuilder(RecoveryPointClockTag.getDefaultInstance());
            }

            public RecoveryPointClockTag.Builder addClocksBuilder(int index) {
                return this.getClocksFieldBuilder().addBuilder(index, RecoveryPointClockTag.getDefaultInstance());
            }

            public List<RecoveryPointClockTag.Builder> getClocksBuilderList() {
                return this.getClocksFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<RecoveryPointClockTag, RecoveryPointClockTag.Builder, RecoveryPointClockTagOrBuilder> getClocksFieldBuilder() {
                if (this.clocksBuilder_ == null) {
                    this.clocksBuilder_ = new RepeatedFieldBuilderV3(this.clocks_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
                    this.clocks_ = null;
                }
                return this.clocksBuilder_;
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

    public static interface RecoveryPointOrBuilder
    extends MessageOrBuilder {
        public List<RecoveryPointClockTag> getClocksList();

        public RecoveryPointClockTag getClocks(int var1);

        public int getClocksCount();

        public List<? extends RecoveryPointClockTagOrBuilder> getClocksOrBuilderList();

        public RecoveryPointClockTagOrBuilder getClocksOrBuilder(int var1);
    }

    public static final class GetShardRecoveryPointResponse
    extends GeneratedMessageV3
    implements GetShardRecoveryPointResponseOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int RECOVERY_POINT_FIELD_NUMBER = 1;
        private RecoveryPoint recoveryPoint_;
        public static final int TIME_FIELD_NUMBER = 2;
        private double time_ = 0.0;
        private byte memoizedIsInitialized = (byte)-1;
        private static final GetShardRecoveryPointResponse DEFAULT_INSTANCE = new GetShardRecoveryPointResponse();
        private static final Parser<GetShardRecoveryPointResponse> PARSER = new AbstractParser<GetShardRecoveryPointResponse>(){

            @Override
            public GetShardRecoveryPointResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = GetShardRecoveryPointResponse.newBuilder();
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

        private GetShardRecoveryPointResponse(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private GetShardRecoveryPointResponse() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new GetShardRecoveryPointResponse();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_GetShardRecoveryPointResponse_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_GetShardRecoveryPointResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(GetShardRecoveryPointResponse.class, Builder.class);
        }

        @Override
        public boolean hasRecoveryPoint() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public RecoveryPoint getRecoveryPoint() {
            return this.recoveryPoint_ == null ? RecoveryPoint.getDefaultInstance() : this.recoveryPoint_;
        }

        @Override
        public RecoveryPointOrBuilder getRecoveryPointOrBuilder() {
            return this.recoveryPoint_ == null ? RecoveryPoint.getDefaultInstance() : this.recoveryPoint_;
        }

        @Override
        public double getTime() {
            return this.time_;
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
                output.writeMessage(1, this.getRecoveryPoint());
            }
            if (Double.doubleToRawLongBits(this.time_) != 0L) {
                output.writeDouble(2, this.time_);
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
                size += CodedOutputStream.computeMessageSize(1, this.getRecoveryPoint());
            }
            if (Double.doubleToRawLongBits(this.time_) != 0L) {
                size += CodedOutputStream.computeDoubleSize(2, this.time_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof GetShardRecoveryPointResponse)) {
                return super.equals(obj);
            }
            GetShardRecoveryPointResponse other = (GetShardRecoveryPointResponse)obj;
            if (this.hasRecoveryPoint() != other.hasRecoveryPoint()) {
                return false;
            }
            if (this.hasRecoveryPoint() && !this.getRecoveryPoint().equals(other.getRecoveryPoint())) {
                return false;
            }
            if (Double.doubleToLongBits(this.getTime()) != Double.doubleToLongBits(other.getTime())) {
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
            hash = 19 * hash + GetShardRecoveryPointResponse.getDescriptor().hashCode();
            if (this.hasRecoveryPoint()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getRecoveryPoint().hashCode();
            }
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getTime()));
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static GetShardRecoveryPointResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetShardRecoveryPointResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetShardRecoveryPointResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetShardRecoveryPointResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetShardRecoveryPointResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetShardRecoveryPointResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetShardRecoveryPointResponse parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GetShardRecoveryPointResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static GetShardRecoveryPointResponse parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static GetShardRecoveryPointResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static GetShardRecoveryPointResponse parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GetShardRecoveryPointResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return GetShardRecoveryPointResponse.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(GetShardRecoveryPointResponse prototype) {
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

        public static GetShardRecoveryPointResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GetShardRecoveryPointResponse> parser() {
            return PARSER;
        }

        public Parser<GetShardRecoveryPointResponse> getParserForType() {
            return PARSER;
        }

        @Override
        public GetShardRecoveryPointResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements GetShardRecoveryPointResponseOrBuilder {
            private int bitField0_;
            private RecoveryPoint recoveryPoint_;
            private SingleFieldBuilderV3<RecoveryPoint, RecoveryPoint.Builder, RecoveryPointOrBuilder> recoveryPointBuilder_;
            private double time_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_GetShardRecoveryPointResponse_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_GetShardRecoveryPointResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(GetShardRecoveryPointResponse.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    this.getRecoveryPointFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                this.recoveryPoint_ = null;
                if (this.recoveryPointBuilder_ != null) {
                    this.recoveryPointBuilder_.dispose();
                    this.recoveryPointBuilder_ = null;
                }
                this.time_ = 0.0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_GetShardRecoveryPointResponse_descriptor;
            }

            @Override
            public GetShardRecoveryPointResponse getDefaultInstanceForType() {
                return GetShardRecoveryPointResponse.getDefaultInstance();
            }

            @Override
            public GetShardRecoveryPointResponse build() {
                GetShardRecoveryPointResponse result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public GetShardRecoveryPointResponse buildPartial() {
                GetShardRecoveryPointResponse result = new GetShardRecoveryPointResponse(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(GetShardRecoveryPointResponse result) {
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) != 0) {
                    result.recoveryPoint_ = this.recoveryPointBuilder_ == null ? this.recoveryPoint_ : this.recoveryPointBuilder_.build();
                    to_bitField0_ |= 1;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.time_ = this.time_;
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
                if (other instanceof GetShardRecoveryPointResponse) {
                    return this.mergeFrom((GetShardRecoveryPointResponse)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(GetShardRecoveryPointResponse other) {
                if (other == GetShardRecoveryPointResponse.getDefaultInstance()) {
                    return this;
                }
                if (other.hasRecoveryPoint()) {
                    this.mergeRecoveryPoint(other.getRecoveryPoint());
                }
                if (other.getTime() != 0.0) {
                    this.setTime(other.getTime());
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
                                input.readMessage(this.getRecoveryPointFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 1;
                                continue block10;
                            }
                            case 17: {
                                this.time_ = input.readDouble();
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
            public boolean hasRecoveryPoint() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public RecoveryPoint getRecoveryPoint() {
                if (this.recoveryPointBuilder_ == null) {
                    return this.recoveryPoint_ == null ? RecoveryPoint.getDefaultInstance() : this.recoveryPoint_;
                }
                return this.recoveryPointBuilder_.getMessage();
            }

            public Builder setRecoveryPoint(RecoveryPoint value) {
                if (this.recoveryPointBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.recoveryPoint_ = value;
                } else {
                    this.recoveryPointBuilder_.setMessage(value);
                }
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder setRecoveryPoint(RecoveryPoint.Builder builderForValue) {
                if (this.recoveryPointBuilder_ == null) {
                    this.recoveryPoint_ = builderForValue.build();
                } else {
                    this.recoveryPointBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder mergeRecoveryPoint(RecoveryPoint value) {
                if (this.recoveryPointBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 0 && this.recoveryPoint_ != null && this.recoveryPoint_ != RecoveryPoint.getDefaultInstance()) {
                        this.getRecoveryPointBuilder().mergeFrom(value);
                    } else {
                        this.recoveryPoint_ = value;
                    }
                } else {
                    this.recoveryPointBuilder_.mergeFrom(value);
                }
                if (this.recoveryPoint_ != null) {
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                return this;
            }

            public Builder clearRecoveryPoint() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.recoveryPoint_ = null;
                if (this.recoveryPointBuilder_ != null) {
                    this.recoveryPointBuilder_.dispose();
                    this.recoveryPointBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public RecoveryPoint.Builder getRecoveryPointBuilder() {
                this.bitField0_ |= 1;
                this.onChanged();
                return this.getRecoveryPointFieldBuilder().getBuilder();
            }

            @Override
            public RecoveryPointOrBuilder getRecoveryPointOrBuilder() {
                if (this.recoveryPointBuilder_ != null) {
                    return this.recoveryPointBuilder_.getMessageOrBuilder();
                }
                return this.recoveryPoint_ == null ? RecoveryPoint.getDefaultInstance() : this.recoveryPoint_;
            }

            private SingleFieldBuilderV3<RecoveryPoint, RecoveryPoint.Builder, RecoveryPointOrBuilder> getRecoveryPointFieldBuilder() {
                if (this.recoveryPointBuilder_ == null) {
                    this.recoveryPointBuilder_ = new SingleFieldBuilderV3(this.getRecoveryPoint(), this.getParentForChildren(), this.isClean());
                    this.recoveryPoint_ = null;
                }
                return this.recoveryPointBuilder_;
            }

            @Override
            public double getTime() {
                return this.time_;
            }

            public Builder setTime(double value) {
                this.time_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearTime() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.time_ = 0.0;
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

    public static interface GetShardRecoveryPointResponseOrBuilder
    extends MessageOrBuilder {
        public boolean hasRecoveryPoint();

        public RecoveryPoint getRecoveryPoint();

        public RecoveryPointOrBuilder getRecoveryPointOrBuilder();

        public double getTime();
    }

    public static final class GetShardRecoveryPointRequest
    extends GeneratedMessageV3
    implements GetShardRecoveryPointRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int COLLECTION_NAME_FIELD_NUMBER = 1;
        private volatile Object collectionName_ = "";
        public static final int SHARD_ID_FIELD_NUMBER = 2;
        private int shardId_ = 0;
        private byte memoizedIsInitialized = (byte)-1;
        private static final GetShardRecoveryPointRequest DEFAULT_INSTANCE = new GetShardRecoveryPointRequest();
        private static final Parser<GetShardRecoveryPointRequest> PARSER = new AbstractParser<GetShardRecoveryPointRequest>(){

            @Override
            public GetShardRecoveryPointRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = GetShardRecoveryPointRequest.newBuilder();
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

        private GetShardRecoveryPointRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private GetShardRecoveryPointRequest() {
            this.collectionName_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new GetShardRecoveryPointRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_GetShardRecoveryPointRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_GetShardRecoveryPointRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(GetShardRecoveryPointRequest.class, Builder.class);
        }

        @Override
        public String getCollectionName() {
            Object ref = this.collectionName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.collectionName_ = s2;
            return s2;
        }

        @Override
        public ByteString getCollectionNameBytes() {
            Object ref = this.collectionName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.collectionName_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public int getShardId() {
            return this.shardId_;
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
            if (!GeneratedMessageV3.isStringEmpty(this.collectionName_)) {
                GeneratedMessageV3.writeString(output, 1, this.collectionName_);
            }
            if (this.shardId_ != 0) {
                output.writeUInt32(2, this.shardId_);
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
            if (!GeneratedMessageV3.isStringEmpty(this.collectionName_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.collectionName_);
            }
            if (this.shardId_ != 0) {
                size += CodedOutputStream.computeUInt32Size(2, this.shardId_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof GetShardRecoveryPointRequest)) {
                return super.equals(obj);
            }
            GetShardRecoveryPointRequest other = (GetShardRecoveryPointRequest)obj;
            if (!this.getCollectionName().equals(other.getCollectionName())) {
                return false;
            }
            if (this.getShardId() != other.getShardId()) {
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
            hash = 19 * hash + GetShardRecoveryPointRequest.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getCollectionName().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getShardId();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static GetShardRecoveryPointRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetShardRecoveryPointRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetShardRecoveryPointRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetShardRecoveryPointRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetShardRecoveryPointRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetShardRecoveryPointRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetShardRecoveryPointRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GetShardRecoveryPointRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static GetShardRecoveryPointRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static GetShardRecoveryPointRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static GetShardRecoveryPointRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GetShardRecoveryPointRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return GetShardRecoveryPointRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(GetShardRecoveryPointRequest prototype) {
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

        public static GetShardRecoveryPointRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GetShardRecoveryPointRequest> parser() {
            return PARSER;
        }

        public Parser<GetShardRecoveryPointRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public GetShardRecoveryPointRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements GetShardRecoveryPointRequestOrBuilder {
            private int bitField0_;
            private Object collectionName_ = "";
            private int shardId_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_GetShardRecoveryPointRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_GetShardRecoveryPointRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(GetShardRecoveryPointRequest.class, Builder.class);
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
                this.collectionName_ = "";
                this.shardId_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_GetShardRecoveryPointRequest_descriptor;
            }

            @Override
            public GetShardRecoveryPointRequest getDefaultInstanceForType() {
                return GetShardRecoveryPointRequest.getDefaultInstance();
            }

            @Override
            public GetShardRecoveryPointRequest build() {
                GetShardRecoveryPointRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public GetShardRecoveryPointRequest buildPartial() {
                GetShardRecoveryPointRequest result = new GetShardRecoveryPointRequest(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(GetShardRecoveryPointRequest result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.collectionName_ = this.collectionName_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.shardId_ = this.shardId_;
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
                if (other instanceof GetShardRecoveryPointRequest) {
                    return this.mergeFrom((GetShardRecoveryPointRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(GetShardRecoveryPointRequest other) {
                if (other == GetShardRecoveryPointRequest.getDefaultInstance()) {
                    return this;
                }
                if (!other.getCollectionName().isEmpty()) {
                    this.collectionName_ = other.collectionName_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (other.getShardId() != 0) {
                    this.setShardId(other.getShardId());
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
                                this.collectionName_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block10;
                            }
                            case 16: {
                                this.shardId_ = input.readUInt32();
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
            public String getCollectionName() {
                Object ref = this.collectionName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.collectionName_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getCollectionNameBytes() {
                Object ref = this.collectionName_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.collectionName_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setCollectionName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.collectionName_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearCollectionName() {
                this.collectionName_ = GetShardRecoveryPointRequest.getDefaultInstance().getCollectionName();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setCollectionNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                GetShardRecoveryPointRequest.checkByteStringIsUtf8(value);
                this.collectionName_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public int getShardId() {
                return this.shardId_;
            }

            public Builder setShardId(int value) {
                this.shardId_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearShardId() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.shardId_ = 0;
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

    public static interface GetShardRecoveryPointRequestOrBuilder
    extends MessageOrBuilder {
        public String getCollectionName();

        public ByteString getCollectionNameBytes();

        public int getShardId();
    }

    public static final class WaitForShardStateRequest
    extends GeneratedMessageV3
    implements WaitForShardStateRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int COLLECTION_NAME_FIELD_NUMBER = 1;
        private volatile Object collectionName_ = "";
        public static final int SHARD_ID_FIELD_NUMBER = 2;
        private int shardId_ = 0;
        public static final int STATE_FIELD_NUMBER = 3;
        private int state_ = 0;
        public static final int TIMEOUT_FIELD_NUMBER = 4;
        private long timeout_ = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final WaitForShardStateRequest DEFAULT_INSTANCE = new WaitForShardStateRequest();
        private static final Parser<WaitForShardStateRequest> PARSER = new AbstractParser<WaitForShardStateRequest>(){

            @Override
            public WaitForShardStateRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = WaitForShardStateRequest.newBuilder();
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

        private WaitForShardStateRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private WaitForShardStateRequest() {
            this.collectionName_ = "";
            this.state_ = 0;
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new WaitForShardStateRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_WaitForShardStateRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_WaitForShardStateRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(WaitForShardStateRequest.class, Builder.class);
        }

        @Override
        public String getCollectionName() {
            Object ref = this.collectionName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.collectionName_ = s2;
            return s2;
        }

        @Override
        public ByteString getCollectionNameBytes() {
            Object ref = this.collectionName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.collectionName_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public int getShardId() {
            return this.shardId_;
        }

        @Override
        public int getStateValue() {
            return this.state_;
        }

        @Override
        public Collections.ReplicaState getState() {
            Collections.ReplicaState result = Collections.ReplicaState.forNumber(this.state_);
            return result == null ? Collections.ReplicaState.UNRECOGNIZED : result;
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
            if (!GeneratedMessageV3.isStringEmpty(this.collectionName_)) {
                GeneratedMessageV3.writeString(output, 1, this.collectionName_);
            }
            if (this.shardId_ != 0) {
                output.writeUInt32(2, this.shardId_);
            }
            if (this.state_ != Collections.ReplicaState.Active.getNumber()) {
                output.writeEnum(3, this.state_);
            }
            if (this.timeout_ != 0L) {
                output.writeUInt64(4, this.timeout_);
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
            if (!GeneratedMessageV3.isStringEmpty(this.collectionName_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.collectionName_);
            }
            if (this.shardId_ != 0) {
                size += CodedOutputStream.computeUInt32Size(2, this.shardId_);
            }
            if (this.state_ != Collections.ReplicaState.Active.getNumber()) {
                size += CodedOutputStream.computeEnumSize(3, this.state_);
            }
            if (this.timeout_ != 0L) {
                size += CodedOutputStream.computeUInt64Size(4, this.timeout_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof WaitForShardStateRequest)) {
                return super.equals(obj);
            }
            WaitForShardStateRequest other = (WaitForShardStateRequest)obj;
            if (!this.getCollectionName().equals(other.getCollectionName())) {
                return false;
            }
            if (this.getShardId() != other.getShardId()) {
                return false;
            }
            if (this.state_ != other.state_) {
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
            hash = 19 * hash + WaitForShardStateRequest.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getCollectionName().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getShardId();
            hash = 37 * hash + 3;
            hash = 53 * hash + this.state_;
            hash = 37 * hash + 4;
            hash = 53 * hash + Internal.hashLong(this.getTimeout());
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static WaitForShardStateRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static WaitForShardStateRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static WaitForShardStateRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static WaitForShardStateRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static WaitForShardStateRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static WaitForShardStateRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static WaitForShardStateRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WaitForShardStateRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static WaitForShardStateRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static WaitForShardStateRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static WaitForShardStateRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static WaitForShardStateRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return WaitForShardStateRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(WaitForShardStateRequest prototype) {
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

        public static WaitForShardStateRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<WaitForShardStateRequest> parser() {
            return PARSER;
        }

        public Parser<WaitForShardStateRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public WaitForShardStateRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements WaitForShardStateRequestOrBuilder {
            private int bitField0_;
            private Object collectionName_ = "";
            private int shardId_;
            private int state_ = 0;
            private long timeout_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_WaitForShardStateRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_WaitForShardStateRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(WaitForShardStateRequest.class, Builder.class);
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
                this.collectionName_ = "";
                this.shardId_ = 0;
                this.state_ = 0;
                this.timeout_ = 0L;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_WaitForShardStateRequest_descriptor;
            }

            @Override
            public WaitForShardStateRequest getDefaultInstanceForType() {
                return WaitForShardStateRequest.getDefaultInstance();
            }

            @Override
            public WaitForShardStateRequest build() {
                WaitForShardStateRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public WaitForShardStateRequest buildPartial() {
                WaitForShardStateRequest result = new WaitForShardStateRequest(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(WaitForShardStateRequest result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.collectionName_ = this.collectionName_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.shardId_ = this.shardId_;
                }
                if ((from_bitField0_ & 4) != 0) {
                    result.state_ = this.state_;
                }
                if ((from_bitField0_ & 8) != 0) {
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
                if (other instanceof WaitForShardStateRequest) {
                    return this.mergeFrom((WaitForShardStateRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(WaitForShardStateRequest other) {
                if (other == WaitForShardStateRequest.getDefaultInstance()) {
                    return this;
                }
                if (!other.getCollectionName().isEmpty()) {
                    this.collectionName_ = other.collectionName_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (other.getShardId() != 0) {
                    this.setShardId(other.getShardId());
                }
                if (other.state_ != 0) {
                    this.setStateValue(other.getStateValue());
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
                    block12: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block12;
                            }
                            case 10: {
                                this.collectionName_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block12;
                            }
                            case 16: {
                                this.shardId_ = input.readUInt32();
                                this.bitField0_ |= 2;
                                continue block12;
                            }
                            case 24: {
                                this.state_ = input.readEnum();
                                this.bitField0_ |= 4;
                                continue block12;
                            }
                            case 32: {
                                this.timeout_ = input.readUInt64();
                                this.bitField0_ |= 8;
                                continue block12;
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
            public String getCollectionName() {
                Object ref = this.collectionName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.collectionName_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getCollectionNameBytes() {
                Object ref = this.collectionName_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.collectionName_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setCollectionName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.collectionName_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearCollectionName() {
                this.collectionName_ = WaitForShardStateRequest.getDefaultInstance().getCollectionName();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setCollectionNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                WaitForShardStateRequest.checkByteStringIsUtf8(value);
                this.collectionName_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public int getShardId() {
                return this.shardId_;
            }

            public Builder setShardId(int value) {
                this.shardId_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearShardId() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.shardId_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getStateValue() {
                return this.state_;
            }

            public Builder setStateValue(int value) {
                this.state_ = value;
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            @Override
            public Collections.ReplicaState getState() {
                Collections.ReplicaState result = Collections.ReplicaState.forNumber(this.state_);
                return result == null ? Collections.ReplicaState.UNRECOGNIZED : result;
            }

            public Builder setState(Collections.ReplicaState value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.state_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearState() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.state_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public long getTimeout() {
                return this.timeout_;
            }

            public Builder setTimeout(long value) {
                this.timeout_ = value;
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder clearTimeout() {
                this.bitField0_ &= 0xFFFFFFF7;
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

    public static interface WaitForShardStateRequestOrBuilder
    extends MessageOrBuilder {
        public String getCollectionName();

        public ByteString getCollectionNameBytes();

        public int getShardId();

        public int getStateValue();

        public Collections.ReplicaState getState();

        public long getTimeout();
    }

    public static final class InitiateShardTransferRequest
    extends GeneratedMessageV3
    implements InitiateShardTransferRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int COLLECTION_NAME_FIELD_NUMBER = 1;
        private volatile Object collectionName_ = "";
        public static final int SHARD_ID_FIELD_NUMBER = 2;
        private int shardId_ = 0;
        private byte memoizedIsInitialized = (byte)-1;
        private static final InitiateShardTransferRequest DEFAULT_INSTANCE = new InitiateShardTransferRequest();
        private static final Parser<InitiateShardTransferRequest> PARSER = new AbstractParser<InitiateShardTransferRequest>(){

            @Override
            public InitiateShardTransferRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = InitiateShardTransferRequest.newBuilder();
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

        private InitiateShardTransferRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private InitiateShardTransferRequest() {
            this.collectionName_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new InitiateShardTransferRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_InitiateShardTransferRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_InitiateShardTransferRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(InitiateShardTransferRequest.class, Builder.class);
        }

        @Override
        public String getCollectionName() {
            Object ref = this.collectionName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.collectionName_ = s2;
            return s2;
        }

        @Override
        public ByteString getCollectionNameBytes() {
            Object ref = this.collectionName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.collectionName_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public int getShardId() {
            return this.shardId_;
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
            if (!GeneratedMessageV3.isStringEmpty(this.collectionName_)) {
                GeneratedMessageV3.writeString(output, 1, this.collectionName_);
            }
            if (this.shardId_ != 0) {
                output.writeUInt32(2, this.shardId_);
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
            if (!GeneratedMessageV3.isStringEmpty(this.collectionName_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.collectionName_);
            }
            if (this.shardId_ != 0) {
                size += CodedOutputStream.computeUInt32Size(2, this.shardId_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof InitiateShardTransferRequest)) {
                return super.equals(obj);
            }
            InitiateShardTransferRequest other = (InitiateShardTransferRequest)obj;
            if (!this.getCollectionName().equals(other.getCollectionName())) {
                return false;
            }
            if (this.getShardId() != other.getShardId()) {
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
            hash = 19 * hash + InitiateShardTransferRequest.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getCollectionName().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getShardId();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static InitiateShardTransferRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static InitiateShardTransferRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static InitiateShardTransferRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static InitiateShardTransferRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static InitiateShardTransferRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static InitiateShardTransferRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static InitiateShardTransferRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static InitiateShardTransferRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static InitiateShardTransferRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static InitiateShardTransferRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static InitiateShardTransferRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static InitiateShardTransferRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return InitiateShardTransferRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(InitiateShardTransferRequest prototype) {
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

        public static InitiateShardTransferRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<InitiateShardTransferRequest> parser() {
            return PARSER;
        }

        public Parser<InitiateShardTransferRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public InitiateShardTransferRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements InitiateShardTransferRequestOrBuilder {
            private int bitField0_;
            private Object collectionName_ = "";
            private int shardId_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_InitiateShardTransferRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_InitiateShardTransferRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(InitiateShardTransferRequest.class, Builder.class);
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
                this.collectionName_ = "";
                this.shardId_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_InitiateShardTransferRequest_descriptor;
            }

            @Override
            public InitiateShardTransferRequest getDefaultInstanceForType() {
                return InitiateShardTransferRequest.getDefaultInstance();
            }

            @Override
            public InitiateShardTransferRequest build() {
                InitiateShardTransferRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public InitiateShardTransferRequest buildPartial() {
                InitiateShardTransferRequest result = new InitiateShardTransferRequest(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(InitiateShardTransferRequest result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.collectionName_ = this.collectionName_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.shardId_ = this.shardId_;
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
                if (other instanceof InitiateShardTransferRequest) {
                    return this.mergeFrom((InitiateShardTransferRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(InitiateShardTransferRequest other) {
                if (other == InitiateShardTransferRequest.getDefaultInstance()) {
                    return this;
                }
                if (!other.getCollectionName().isEmpty()) {
                    this.collectionName_ = other.collectionName_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (other.getShardId() != 0) {
                    this.setShardId(other.getShardId());
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
                                this.collectionName_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block10;
                            }
                            case 16: {
                                this.shardId_ = input.readUInt32();
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
            public String getCollectionName() {
                Object ref = this.collectionName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.collectionName_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getCollectionNameBytes() {
                Object ref = this.collectionName_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.collectionName_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setCollectionName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.collectionName_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearCollectionName() {
                this.collectionName_ = InitiateShardTransferRequest.getDefaultInstance().getCollectionName();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setCollectionNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                InitiateShardTransferRequest.checkByteStringIsUtf8(value);
                this.collectionName_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public int getShardId() {
                return this.shardId_;
            }

            public Builder setShardId(int value) {
                this.shardId_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearShardId() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.shardId_ = 0;
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

    public static interface InitiateShardTransferRequestOrBuilder
    extends MessageOrBuilder {
        public String getCollectionName();

        public ByteString getCollectionNameBytes();

        public int getShardId();
    }

    public static final class GetCollectionInfoRequestInternal
    extends GeneratedMessageV3
    implements GetCollectionInfoRequestInternalOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int GET_COLLECTIONINFOREQUEST_FIELD_NUMBER = 1;
        private Collections.GetCollectionInfoRequest getCollectionInfoRequest_;
        public static final int SHARD_ID_FIELD_NUMBER = 2;
        private int shardId_ = 0;
        private byte memoizedIsInitialized = (byte)-1;
        private static final GetCollectionInfoRequestInternal DEFAULT_INSTANCE = new GetCollectionInfoRequestInternal();
        private static final Parser<GetCollectionInfoRequestInternal> PARSER = new AbstractParser<GetCollectionInfoRequestInternal>(){

            @Override
            public GetCollectionInfoRequestInternal parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = GetCollectionInfoRequestInternal.newBuilder();
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

        private GetCollectionInfoRequestInternal(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private GetCollectionInfoRequestInternal() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new GetCollectionInfoRequestInternal();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_GetCollectionInfoRequestInternal_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_GetCollectionInfoRequestInternal_fieldAccessorTable.ensureFieldAccessorsInitialized(GetCollectionInfoRequestInternal.class, Builder.class);
        }

        @Override
        public boolean hasGetCollectionInfoRequest() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public Collections.GetCollectionInfoRequest getGetCollectionInfoRequest() {
            return this.getCollectionInfoRequest_ == null ? Collections.GetCollectionInfoRequest.getDefaultInstance() : this.getCollectionInfoRequest_;
        }

        @Override
        public Collections.GetCollectionInfoRequestOrBuilder getGetCollectionInfoRequestOrBuilder() {
            return this.getCollectionInfoRequest_ == null ? Collections.GetCollectionInfoRequest.getDefaultInstance() : this.getCollectionInfoRequest_;
        }

        @Override
        public int getShardId() {
            return this.shardId_;
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
                output.writeMessage(1, this.getGetCollectionInfoRequest());
            }
            if (this.shardId_ != 0) {
                output.writeUInt32(2, this.shardId_);
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
                size += CodedOutputStream.computeMessageSize(1, this.getGetCollectionInfoRequest());
            }
            if (this.shardId_ != 0) {
                size += CodedOutputStream.computeUInt32Size(2, this.shardId_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof GetCollectionInfoRequestInternal)) {
                return super.equals(obj);
            }
            GetCollectionInfoRequestInternal other = (GetCollectionInfoRequestInternal)obj;
            if (this.hasGetCollectionInfoRequest() != other.hasGetCollectionInfoRequest()) {
                return false;
            }
            if (this.hasGetCollectionInfoRequest() && !this.getGetCollectionInfoRequest().equals(other.getGetCollectionInfoRequest())) {
                return false;
            }
            if (this.getShardId() != other.getShardId()) {
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
            hash = 19 * hash + GetCollectionInfoRequestInternal.getDescriptor().hashCode();
            if (this.hasGetCollectionInfoRequest()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getGetCollectionInfoRequest().hashCode();
            }
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getShardId();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static GetCollectionInfoRequestInternal parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetCollectionInfoRequestInternal parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetCollectionInfoRequestInternal parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetCollectionInfoRequestInternal parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetCollectionInfoRequestInternal parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GetCollectionInfoRequestInternal parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GetCollectionInfoRequestInternal parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GetCollectionInfoRequestInternal parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static GetCollectionInfoRequestInternal parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static GetCollectionInfoRequestInternal parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static GetCollectionInfoRequestInternal parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GetCollectionInfoRequestInternal parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return GetCollectionInfoRequestInternal.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(GetCollectionInfoRequestInternal prototype) {
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

        public static GetCollectionInfoRequestInternal getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GetCollectionInfoRequestInternal> parser() {
            return PARSER;
        }

        public Parser<GetCollectionInfoRequestInternal> getParserForType() {
            return PARSER;
        }

        @Override
        public GetCollectionInfoRequestInternal getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements GetCollectionInfoRequestInternalOrBuilder {
            private int bitField0_;
            private Collections.GetCollectionInfoRequest getCollectionInfoRequest_;
            private SingleFieldBuilderV3<Collections.GetCollectionInfoRequest, Collections.GetCollectionInfoRequest.Builder, Collections.GetCollectionInfoRequestOrBuilder> getCollectionInfoRequestBuilder_;
            private int shardId_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_GetCollectionInfoRequestInternal_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_GetCollectionInfoRequestInternal_fieldAccessorTable.ensureFieldAccessorsInitialized(GetCollectionInfoRequestInternal.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    this.getGetCollectionInfoRequestFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                this.getCollectionInfoRequest_ = null;
                if (this.getCollectionInfoRequestBuilder_ != null) {
                    this.getCollectionInfoRequestBuilder_.dispose();
                    this.getCollectionInfoRequestBuilder_ = null;
                }
                this.shardId_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_GetCollectionInfoRequestInternal_descriptor;
            }

            @Override
            public GetCollectionInfoRequestInternal getDefaultInstanceForType() {
                return GetCollectionInfoRequestInternal.getDefaultInstance();
            }

            @Override
            public GetCollectionInfoRequestInternal build() {
                GetCollectionInfoRequestInternal result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public GetCollectionInfoRequestInternal buildPartial() {
                GetCollectionInfoRequestInternal result = new GetCollectionInfoRequestInternal(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(GetCollectionInfoRequestInternal result) {
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) != 0) {
                    result.getCollectionInfoRequest_ = this.getCollectionInfoRequestBuilder_ == null ? this.getCollectionInfoRequest_ : this.getCollectionInfoRequestBuilder_.build();
                    to_bitField0_ |= 1;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.shardId_ = this.shardId_;
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
                if (other instanceof GetCollectionInfoRequestInternal) {
                    return this.mergeFrom((GetCollectionInfoRequestInternal)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(GetCollectionInfoRequestInternal other) {
                if (other == GetCollectionInfoRequestInternal.getDefaultInstance()) {
                    return this;
                }
                if (other.hasGetCollectionInfoRequest()) {
                    this.mergeGetCollectionInfoRequest(other.getGetCollectionInfoRequest());
                }
                if (other.getShardId() != 0) {
                    this.setShardId(other.getShardId());
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
                                input.readMessage(this.getGetCollectionInfoRequestFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 1;
                                continue block10;
                            }
                            case 16: {
                                this.shardId_ = input.readUInt32();
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
            public boolean hasGetCollectionInfoRequest() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public Collections.GetCollectionInfoRequest getGetCollectionInfoRequest() {
                if (this.getCollectionInfoRequestBuilder_ == null) {
                    return this.getCollectionInfoRequest_ == null ? Collections.GetCollectionInfoRequest.getDefaultInstance() : this.getCollectionInfoRequest_;
                }
                return this.getCollectionInfoRequestBuilder_.getMessage();
            }

            public Builder setGetCollectionInfoRequest(Collections.GetCollectionInfoRequest value) {
                if (this.getCollectionInfoRequestBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.getCollectionInfoRequest_ = value;
                } else {
                    this.getCollectionInfoRequestBuilder_.setMessage(value);
                }
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder setGetCollectionInfoRequest(Collections.GetCollectionInfoRequest.Builder builderForValue) {
                if (this.getCollectionInfoRequestBuilder_ == null) {
                    this.getCollectionInfoRequest_ = builderForValue.build();
                } else {
                    this.getCollectionInfoRequestBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder mergeGetCollectionInfoRequest(Collections.GetCollectionInfoRequest value) {
                if (this.getCollectionInfoRequestBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 0 && this.getCollectionInfoRequest_ != null && this.getCollectionInfoRequest_ != Collections.GetCollectionInfoRequest.getDefaultInstance()) {
                        this.getGetCollectionInfoRequestBuilder().mergeFrom(value);
                    } else {
                        this.getCollectionInfoRequest_ = value;
                    }
                } else {
                    this.getCollectionInfoRequestBuilder_.mergeFrom(value);
                }
                if (this.getCollectionInfoRequest_ != null) {
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                return this;
            }

            public Builder clearGetCollectionInfoRequest() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.getCollectionInfoRequest_ = null;
                if (this.getCollectionInfoRequestBuilder_ != null) {
                    this.getCollectionInfoRequestBuilder_.dispose();
                    this.getCollectionInfoRequestBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Collections.GetCollectionInfoRequest.Builder getGetCollectionInfoRequestBuilder() {
                this.bitField0_ |= 1;
                this.onChanged();
                return this.getGetCollectionInfoRequestFieldBuilder().getBuilder();
            }

            @Override
            public Collections.GetCollectionInfoRequestOrBuilder getGetCollectionInfoRequestOrBuilder() {
                if (this.getCollectionInfoRequestBuilder_ != null) {
                    return this.getCollectionInfoRequestBuilder_.getMessageOrBuilder();
                }
                return this.getCollectionInfoRequest_ == null ? Collections.GetCollectionInfoRequest.getDefaultInstance() : this.getCollectionInfoRequest_;
            }

            private SingleFieldBuilderV3<Collections.GetCollectionInfoRequest, Collections.GetCollectionInfoRequest.Builder, Collections.GetCollectionInfoRequestOrBuilder> getGetCollectionInfoRequestFieldBuilder() {
                if (this.getCollectionInfoRequestBuilder_ == null) {
                    this.getCollectionInfoRequestBuilder_ = new SingleFieldBuilderV3(this.getGetCollectionInfoRequest(), this.getParentForChildren(), this.isClean());
                    this.getCollectionInfoRequest_ = null;
                }
                return this.getCollectionInfoRequestBuilder_;
            }

            @Override
            public int getShardId() {
                return this.shardId_;
            }

            public Builder setShardId(int value) {
                this.shardId_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearShardId() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.shardId_ = 0;
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

    public static interface GetCollectionInfoRequestInternalOrBuilder
    extends MessageOrBuilder {
        public boolean hasGetCollectionInfoRequest();

        public Collections.GetCollectionInfoRequest getGetCollectionInfoRequest();

        public Collections.GetCollectionInfoRequestOrBuilder getGetCollectionInfoRequestOrBuilder();

        public int getShardId();
    }
}

