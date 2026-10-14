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
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import io.qdrant.client.grpc.SnapshotsService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ShardSnapshotsService {
    private static final Descriptors.Descriptor internal_static_qdrant_CreateShardSnapshotRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_CreateShardSnapshotRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_ListShardSnapshotsRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_ListShardSnapshotsRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_DeleteShardSnapshotRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_DeleteShardSnapshotRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_RecoverShardSnapshotRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_RecoverShardSnapshotRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_ShardSnapshotLocation_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_ShardSnapshotLocation_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_RecoverSnapshotResponse_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_RecoverSnapshotResponse_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private ShardSnapshotsService() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        ShardSnapshotsService.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001dshard_snapshots_service.proto\u0012\u0006qdrant\u001a\u0017snapshots_service.proto\"G\n\u001aCreateShardSnapshotRequest\u0012\u0017\n\u000fcollection_name\u0018\u0001 \u0001(\t\u0012\u0010\n\bshard_id\u0018\u0002 \u0001(\r\"F\n\u0019ListShardSnapshotsRequest\u0012\u0017\n\u000fcollection_name\u0018\u0001 \u0001(\t\u0012\u0010\n\bshard_id\u0018\u0002 \u0001(\r\"^\n\u001aDeleteShardSnapshotRequest\u0012\u0017\n\u000fcollection_name\u0018\u0001 \u0001(\t\u0012\u0010\n\bshard_id\u0018\u0002 \u0001(\r\u0012\u0015\n\rsnapshot_name\u0018\u0003 \u0001(\t\"\u0082\u0002\n\u001bRecoverShardSnapshotRequest\u0012\u0017\n\u000fcollection_name\u0018\u0001 \u0001(\t\u0012\u0010\n\bshard_id\u0018\u0002 \u0001(\r\u00128\n\u0011snapshot_location\u0018\u0003 \u0001(\u000b2\u001d.qdrant.ShardSnapshotLocation\u00128\n\u0011snapshot_priority\u0018\u0004 \u0001(\u000e2\u001d.qdrant.ShardSnapshotPriority\u0012\u0015\n\bchecksum\u0018\u0005 \u0001(\tH\u0000\u0088\u0001\u0001\u0012\u0014\n\u0007api_key\u0018\u0006 \u0001(\tH\u0001\u0088\u0001\u0001B\u000b\n\t_checksumB\n\n\b_api_key\"B\n\u0015ShardSnapshotLocation\u0012\r\n\u0003url\u0018\u0001 \u0001(\tH\u0000\u0012\u000e\n\u0004path\u0018\u0002 \u0001(\tH\u0000B\n\n\blocation\"'\n\u0017RecoverSnapshotResponse\u0012\f\n\u0004time\u0018\u0001 \u0001(\u0001*\u00a5\u0001\n\u0015ShardSnapshotPriority\u0012\u001f\n\u001bShardSnapshotPriorityNoSync\u0010\u0000\u0012!\n\u001dShardSnapshotPrioritySnapshot\u0010\u0001\u0012 \n\u001cShardSnapshotPriorityReplica\u0010\u0002\u0012&\n\"ShardSnapshotPriorityShardTransfer\u0010\u00032\u00cf\u0002\n\u000eShardSnapshots\u0012N\n\u0006Create\u0012\".qdrant.CreateShardSnapshotRequest\u001a\u001e.qdrant.CreateSnapshotResponse\"\u0000\u0012J\n\u0004List\u0012!.qdrant.ListShardSnapshotsRequest\u001a\u001d.qdrant.ListSnapshotsResponse\"\u0000\u0012N\n\u0006Delete\u0012\".qdrant.DeleteShardSnapshotRequest\u001a\u001e.qdrant.DeleteSnapshotResponse\"\u0000\u0012Q\n\u0007Recover\u0012#.qdrant.RecoverShardSnapshotRequest\u001a\u001f.qdrant.RecoverSnapshotResponse\"\u0000B,\n\u0015io.qdrant.client.grpc\u00aa\u0002\u0012Qdrant.Client.Grpcb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{SnapshotsService.getDescriptor()});
        internal_static_qdrant_CreateShardSnapshotRequest_descriptor = ShardSnapshotsService.getDescriptor().getMessageTypes().get(0);
        internal_static_qdrant_CreateShardSnapshotRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_CreateShardSnapshotRequest_descriptor, new String[]{"CollectionName", "ShardId"});
        internal_static_qdrant_ListShardSnapshotsRequest_descriptor = ShardSnapshotsService.getDescriptor().getMessageTypes().get(1);
        internal_static_qdrant_ListShardSnapshotsRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_ListShardSnapshotsRequest_descriptor, new String[]{"CollectionName", "ShardId"});
        internal_static_qdrant_DeleteShardSnapshotRequest_descriptor = ShardSnapshotsService.getDescriptor().getMessageTypes().get(2);
        internal_static_qdrant_DeleteShardSnapshotRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_DeleteShardSnapshotRequest_descriptor, new String[]{"CollectionName", "ShardId", "SnapshotName"});
        internal_static_qdrant_RecoverShardSnapshotRequest_descriptor = ShardSnapshotsService.getDescriptor().getMessageTypes().get(3);
        internal_static_qdrant_RecoverShardSnapshotRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_RecoverShardSnapshotRequest_descriptor, new String[]{"CollectionName", "ShardId", "SnapshotLocation", "SnapshotPriority", "Checksum", "ApiKey"});
        internal_static_qdrant_ShardSnapshotLocation_descriptor = ShardSnapshotsService.getDescriptor().getMessageTypes().get(4);
        internal_static_qdrant_ShardSnapshotLocation_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_ShardSnapshotLocation_descriptor, new String[]{"Url", "Path", "Location"});
        internal_static_qdrant_RecoverSnapshotResponse_descriptor = ShardSnapshotsService.getDescriptor().getMessageTypes().get(5);
        internal_static_qdrant_RecoverSnapshotResponse_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_RecoverSnapshotResponse_descriptor, new String[]{"Time"});
        SnapshotsService.getDescriptor();
    }

    public static final class RecoverSnapshotResponse
    extends GeneratedMessageV3
    implements RecoverSnapshotResponseOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int TIME_FIELD_NUMBER = 1;
        private double time_ = 0.0;
        private byte memoizedIsInitialized = (byte)-1;
        private static final RecoverSnapshotResponse DEFAULT_INSTANCE = new RecoverSnapshotResponse();
        private static final Parser<RecoverSnapshotResponse> PARSER = new AbstractParser<RecoverSnapshotResponse>(){

            @Override
            public RecoverSnapshotResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = RecoverSnapshotResponse.newBuilder();
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

        private RecoverSnapshotResponse(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private RecoverSnapshotResponse() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new RecoverSnapshotResponse();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_RecoverSnapshotResponse_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_RecoverSnapshotResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(RecoverSnapshotResponse.class, Builder.class);
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
            if (Double.doubleToRawLongBits(this.time_) != 0L) {
                output.writeDouble(1, this.time_);
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
            if (Double.doubleToRawLongBits(this.time_) != 0L) {
                size += CodedOutputStream.computeDoubleSize(1, this.time_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof RecoverSnapshotResponse)) {
                return super.equals(obj);
            }
            RecoverSnapshotResponse other = (RecoverSnapshotResponse)obj;
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
            hash = 19 * hash + RecoverSnapshotResponse.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getTime()));
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static RecoverSnapshotResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RecoverSnapshotResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RecoverSnapshotResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RecoverSnapshotResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RecoverSnapshotResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RecoverSnapshotResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RecoverSnapshotResponse parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RecoverSnapshotResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RecoverSnapshotResponse parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static RecoverSnapshotResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RecoverSnapshotResponse parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RecoverSnapshotResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return RecoverSnapshotResponse.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RecoverSnapshotResponse prototype) {
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

        public static RecoverSnapshotResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RecoverSnapshotResponse> parser() {
            return PARSER;
        }

        public Parser<RecoverSnapshotResponse> getParserForType() {
            return PARSER;
        }

        @Override
        public RecoverSnapshotResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements RecoverSnapshotResponseOrBuilder {
            private int bitField0_;
            private double time_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_RecoverSnapshotResponse_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_RecoverSnapshotResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(RecoverSnapshotResponse.class, Builder.class);
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
                this.time_ = 0.0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_RecoverSnapshotResponse_descriptor;
            }

            @Override
            public RecoverSnapshotResponse getDefaultInstanceForType() {
                return RecoverSnapshotResponse.getDefaultInstance();
            }

            @Override
            public RecoverSnapshotResponse build() {
                RecoverSnapshotResponse result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public RecoverSnapshotResponse buildPartial() {
                RecoverSnapshotResponse result = new RecoverSnapshotResponse(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(RecoverSnapshotResponse result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.time_ = this.time_;
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
                if (other instanceof RecoverSnapshotResponse) {
                    return this.mergeFrom((RecoverSnapshotResponse)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(RecoverSnapshotResponse other) {
                if (other == RecoverSnapshotResponse.getDefaultInstance()) {
                    return this;
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
                    block9: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block9;
                            }
                            case 9: {
                                this.time_ = input.readDouble();
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
            public double getTime() {
                return this.time_;
            }

            public Builder setTime(double value) {
                this.time_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearTime() {
                this.bitField0_ &= 0xFFFFFFFE;
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

    public static interface RecoverSnapshotResponseOrBuilder
    extends MessageOrBuilder {
        public double getTime();
    }

    public static final class ShardSnapshotLocation
    extends GeneratedMessageV3
    implements ShardSnapshotLocationOrBuilder {
        private static final long serialVersionUID = 0L;
        private int locationCase_ = 0;
        private Object location_;
        public static final int URL_FIELD_NUMBER = 1;
        public static final int PATH_FIELD_NUMBER = 2;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ShardSnapshotLocation DEFAULT_INSTANCE = new ShardSnapshotLocation();
        private static final Parser<ShardSnapshotLocation> PARSER = new AbstractParser<ShardSnapshotLocation>(){

            @Override
            public ShardSnapshotLocation parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = ShardSnapshotLocation.newBuilder();
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

        private ShardSnapshotLocation(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ShardSnapshotLocation() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new ShardSnapshotLocation();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_ShardSnapshotLocation_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_ShardSnapshotLocation_fieldAccessorTable.ensureFieldAccessorsInitialized(ShardSnapshotLocation.class, Builder.class);
        }

        @Override
        public LocationCase getLocationCase() {
            return LocationCase.forNumber(this.locationCase_);
        }

        @Override
        public boolean hasUrl() {
            return this.locationCase_ == 1;
        }

        @Override
        public String getUrl() {
            Object ref = "";
            if (this.locationCase_ == 1) {
                ref = this.location_;
            }
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            if (this.locationCase_ == 1) {
                this.location_ = s2;
            }
            return s2;
        }

        @Override
        public ByteString getUrlBytes() {
            Object ref = "";
            if (this.locationCase_ == 1) {
                ref = this.location_;
            }
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                if (this.locationCase_ == 1) {
                    this.location_ = b;
                }
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasPath() {
            return this.locationCase_ == 2;
        }

        @Override
        public String getPath() {
            Object ref = "";
            if (this.locationCase_ == 2) {
                ref = this.location_;
            }
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            if (this.locationCase_ == 2) {
                this.location_ = s2;
            }
            return s2;
        }

        @Override
        public ByteString getPathBytes() {
            Object ref = "";
            if (this.locationCase_ == 2) {
                ref = this.location_;
            }
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                if (this.locationCase_ == 2) {
                    this.location_ = b;
                }
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
            if (this.locationCase_ == 1) {
                GeneratedMessageV3.writeString(output, 1, this.location_);
            }
            if (this.locationCase_ == 2) {
                GeneratedMessageV3.writeString(output, 2, this.location_);
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
            if (this.locationCase_ == 1) {
                size += GeneratedMessageV3.computeStringSize(1, this.location_);
            }
            if (this.locationCase_ == 2) {
                size += GeneratedMessageV3.computeStringSize(2, this.location_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ShardSnapshotLocation)) {
                return super.equals(obj);
            }
            ShardSnapshotLocation other = (ShardSnapshotLocation)obj;
            if (!this.getLocationCase().equals(other.getLocationCase())) {
                return false;
            }
            switch (this.locationCase_) {
                case 1: {
                    if (this.getUrl().equals(other.getUrl())) break;
                    return false;
                }
                case 2: {
                    if (this.getPath().equals(other.getPath())) break;
                    return false;
                }
            }
            return this.getUnknownFields().equals(other.getUnknownFields());
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ShardSnapshotLocation.getDescriptor().hashCode();
            switch (this.locationCase_) {
                case 1: {
                    hash = 37 * hash + 1;
                    hash = 53 * hash + this.getUrl().hashCode();
                    break;
                }
                case 2: {
                    hash = 37 * hash + 2;
                    hash = 53 * hash + this.getPath().hashCode();
                    break;
                }
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static ShardSnapshotLocation parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ShardSnapshotLocation parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ShardSnapshotLocation parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ShardSnapshotLocation parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ShardSnapshotLocation parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ShardSnapshotLocation parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ShardSnapshotLocation parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ShardSnapshotLocation parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ShardSnapshotLocation parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ShardSnapshotLocation parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ShardSnapshotLocation parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ShardSnapshotLocation parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ShardSnapshotLocation.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ShardSnapshotLocation prototype) {
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

        public static ShardSnapshotLocation getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ShardSnapshotLocation> parser() {
            return PARSER;
        }

        public Parser<ShardSnapshotLocation> getParserForType() {
            return PARSER;
        }

        @Override
        public ShardSnapshotLocation getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ShardSnapshotLocationOrBuilder {
            private int locationCase_ = 0;
            private Object location_;
            private int bitField0_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_ShardSnapshotLocation_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_ShardSnapshotLocation_fieldAccessorTable.ensureFieldAccessorsInitialized(ShardSnapshotLocation.class, Builder.class);
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
                this.locationCase_ = 0;
                this.location_ = null;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_ShardSnapshotLocation_descriptor;
            }

            @Override
            public ShardSnapshotLocation getDefaultInstanceForType() {
                return ShardSnapshotLocation.getDefaultInstance();
            }

            @Override
            public ShardSnapshotLocation build() {
                ShardSnapshotLocation result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ShardSnapshotLocation buildPartial() {
                ShardSnapshotLocation result = new ShardSnapshotLocation(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.buildPartialOneofs(result);
                this.onBuilt();
                return result;
            }

            private void buildPartial0(ShardSnapshotLocation result) {
                int from_bitField0_ = this.bitField0_;
            }

            private void buildPartialOneofs(ShardSnapshotLocation result) {
                result.locationCase_ = this.locationCase_;
                result.location_ = this.location_;
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
                if (other instanceof ShardSnapshotLocation) {
                    return this.mergeFrom((ShardSnapshotLocation)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ShardSnapshotLocation other) {
                if (other == ShardSnapshotLocation.getDefaultInstance()) {
                    return this;
                }
                switch (other.getLocationCase()) {
                    case URL: {
                        this.locationCase_ = 1;
                        this.location_ = other.location_;
                        this.onChanged();
                        break;
                    }
                    case PATH: {
                        this.locationCase_ = 2;
                        this.location_ = other.location_;
                        this.onChanged();
                        break;
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
                    block10: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block10;
                            }
                            case 10: {
                                String s2 = input.readStringRequireUtf8();
                                this.locationCase_ = 1;
                                this.location_ = s2;
                                continue block10;
                            }
                            case 18: {
                                String s2 = input.readStringRequireUtf8();
                                this.locationCase_ = 2;
                                this.location_ = s2;
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
            public LocationCase getLocationCase() {
                return LocationCase.forNumber(this.locationCase_);
            }

            public Builder clearLocation() {
                this.locationCase_ = 0;
                this.location_ = null;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasUrl() {
                return this.locationCase_ == 1;
            }

            @Override
            public String getUrl() {
                Object ref = "";
                if (this.locationCase_ == 1) {
                    ref = this.location_;
                }
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    if (this.locationCase_ == 1) {
                        this.location_ = s2;
                    }
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getUrlBytes() {
                Object ref = "";
                if (this.locationCase_ == 1) {
                    ref = this.location_;
                }
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    if (this.locationCase_ == 1) {
                        this.location_ = b;
                    }
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setUrl(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.locationCase_ = 1;
                this.location_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearUrl() {
                if (this.locationCase_ == 1) {
                    this.locationCase_ = 0;
                    this.location_ = null;
                    this.onChanged();
                }
                return this;
            }

            public Builder setUrlBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ShardSnapshotLocation.checkByteStringIsUtf8(value);
                this.locationCase_ = 1;
                this.location_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasPath() {
                return this.locationCase_ == 2;
            }

            @Override
            public String getPath() {
                Object ref = "";
                if (this.locationCase_ == 2) {
                    ref = this.location_;
                }
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    if (this.locationCase_ == 2) {
                        this.location_ = s2;
                    }
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getPathBytes() {
                Object ref = "";
                if (this.locationCase_ == 2) {
                    ref = this.location_;
                }
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    if (this.locationCase_ == 2) {
                        this.location_ = b;
                    }
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setPath(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.locationCase_ = 2;
                this.location_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearPath() {
                if (this.locationCase_ == 2) {
                    this.locationCase_ = 0;
                    this.location_ = null;
                    this.onChanged();
                }
                return this;
            }

            public Builder setPathBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ShardSnapshotLocation.checkByteStringIsUtf8(value);
                this.locationCase_ = 2;
                this.location_ = value;
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

        public static enum LocationCase implements Internal.EnumLite,
        AbstractMessageLite.InternalOneOfEnum
        {
            URL(1),
            PATH(2),
            LOCATION_NOT_SET(0);

            private final int value;

            private LocationCase(int value) {
                this.value = value;
            }

            @Deprecated
            public static LocationCase valueOf(int value) {
                return LocationCase.forNumber(value);
            }

            public static LocationCase forNumber(int value) {
                switch (value) {
                    case 1: {
                        return URL;
                    }
                    case 2: {
                        return PATH;
                    }
                    case 0: {
                        return LOCATION_NOT_SET;
                    }
                }
                return null;
            }

            @Override
            public int getNumber() {
                return this.value;
            }
        }
    }

    public static interface ShardSnapshotLocationOrBuilder
    extends MessageOrBuilder {
        public boolean hasUrl();

        public String getUrl();

        public ByteString getUrlBytes();

        public boolean hasPath();

        public String getPath();

        public ByteString getPathBytes();

        public ShardSnapshotLocation.LocationCase getLocationCase();
    }

    public static final class RecoverShardSnapshotRequest
    extends GeneratedMessageV3
    implements RecoverShardSnapshotRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int COLLECTION_NAME_FIELD_NUMBER = 1;
        private volatile Object collectionName_ = "";
        public static final int SHARD_ID_FIELD_NUMBER = 2;
        private int shardId_ = 0;
        public static final int SNAPSHOT_LOCATION_FIELD_NUMBER = 3;
        private ShardSnapshotLocation snapshotLocation_;
        public static final int SNAPSHOT_PRIORITY_FIELD_NUMBER = 4;
        private int snapshotPriority_ = 0;
        public static final int CHECKSUM_FIELD_NUMBER = 5;
        private volatile Object checksum_ = "";
        public static final int API_KEY_FIELD_NUMBER = 6;
        private volatile Object apiKey_ = "";
        private byte memoizedIsInitialized = (byte)-1;
        private static final RecoverShardSnapshotRequest DEFAULT_INSTANCE = new RecoverShardSnapshotRequest();
        private static final Parser<RecoverShardSnapshotRequest> PARSER = new AbstractParser<RecoverShardSnapshotRequest>(){

            @Override
            public RecoverShardSnapshotRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = RecoverShardSnapshotRequest.newBuilder();
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

        private RecoverShardSnapshotRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private RecoverShardSnapshotRequest() {
            this.collectionName_ = "";
            this.snapshotPriority_ = 0;
            this.checksum_ = "";
            this.apiKey_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new RecoverShardSnapshotRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_RecoverShardSnapshotRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_RecoverShardSnapshotRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(RecoverShardSnapshotRequest.class, Builder.class);
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
        public boolean hasSnapshotLocation() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public ShardSnapshotLocation getSnapshotLocation() {
            return this.snapshotLocation_ == null ? ShardSnapshotLocation.getDefaultInstance() : this.snapshotLocation_;
        }

        @Override
        public ShardSnapshotLocationOrBuilder getSnapshotLocationOrBuilder() {
            return this.snapshotLocation_ == null ? ShardSnapshotLocation.getDefaultInstance() : this.snapshotLocation_;
        }

        @Override
        public int getSnapshotPriorityValue() {
            return this.snapshotPriority_;
        }

        @Override
        public ShardSnapshotPriority getSnapshotPriority() {
            ShardSnapshotPriority result = ShardSnapshotPriority.forNumber(this.snapshotPriority_);
            return result == null ? ShardSnapshotPriority.UNRECOGNIZED : result;
        }

        @Override
        public boolean hasChecksum() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override
        public String getChecksum() {
            Object ref = this.checksum_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.checksum_ = s2;
            return s2;
        }

        @Override
        public ByteString getChecksumBytes() {
            Object ref = this.checksum_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.checksum_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasApiKey() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override
        public String getApiKey() {
            Object ref = this.apiKey_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.apiKey_ = s2;
            return s2;
        }

        @Override
        public ByteString getApiKeyBytes() {
            Object ref = this.apiKey_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.apiKey_ = b;
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
            if (!GeneratedMessageV3.isStringEmpty(this.collectionName_)) {
                GeneratedMessageV3.writeString(output, 1, this.collectionName_);
            }
            if (this.shardId_ != 0) {
                output.writeUInt32(2, this.shardId_);
            }
            if ((this.bitField0_ & 1) != 0) {
                output.writeMessage(3, this.getSnapshotLocation());
            }
            if (this.snapshotPriority_ != ShardSnapshotPriority.ShardSnapshotPriorityNoSync.getNumber()) {
                output.writeEnum(4, this.snapshotPriority_);
            }
            if ((this.bitField0_ & 2) != 0) {
                GeneratedMessageV3.writeString(output, 5, this.checksum_);
            }
            if ((this.bitField0_ & 4) != 0) {
                GeneratedMessageV3.writeString(output, 6, this.apiKey_);
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
                size += CodedOutputStream.computeMessageSize(3, this.getSnapshotLocation());
            }
            if (this.snapshotPriority_ != ShardSnapshotPriority.ShardSnapshotPriorityNoSync.getNumber()) {
                size += CodedOutputStream.computeEnumSize(4, this.snapshotPriority_);
            }
            if ((this.bitField0_ & 2) != 0) {
                size += GeneratedMessageV3.computeStringSize(5, this.checksum_);
            }
            if ((this.bitField0_ & 4) != 0) {
                size += GeneratedMessageV3.computeStringSize(6, this.apiKey_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof RecoverShardSnapshotRequest)) {
                return super.equals(obj);
            }
            RecoverShardSnapshotRequest other = (RecoverShardSnapshotRequest)obj;
            if (!this.getCollectionName().equals(other.getCollectionName())) {
                return false;
            }
            if (this.getShardId() != other.getShardId()) {
                return false;
            }
            if (this.hasSnapshotLocation() != other.hasSnapshotLocation()) {
                return false;
            }
            if (this.hasSnapshotLocation() && !this.getSnapshotLocation().equals(other.getSnapshotLocation())) {
                return false;
            }
            if (this.snapshotPriority_ != other.snapshotPriority_) {
                return false;
            }
            if (this.hasChecksum() != other.hasChecksum()) {
                return false;
            }
            if (this.hasChecksum() && !this.getChecksum().equals(other.getChecksum())) {
                return false;
            }
            if (this.hasApiKey() != other.hasApiKey()) {
                return false;
            }
            if (this.hasApiKey() && !this.getApiKey().equals(other.getApiKey())) {
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
            hash = 19 * hash + RecoverShardSnapshotRequest.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getCollectionName().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getShardId();
            if (this.hasSnapshotLocation()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getSnapshotLocation().hashCode();
            }
            hash = 37 * hash + 4;
            hash = 53 * hash + this.snapshotPriority_;
            if (this.hasChecksum()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getChecksum().hashCode();
            }
            if (this.hasApiKey()) {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.getApiKey().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static RecoverShardSnapshotRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RecoverShardSnapshotRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RecoverShardSnapshotRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RecoverShardSnapshotRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RecoverShardSnapshotRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RecoverShardSnapshotRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RecoverShardSnapshotRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RecoverShardSnapshotRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RecoverShardSnapshotRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static RecoverShardSnapshotRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RecoverShardSnapshotRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RecoverShardSnapshotRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return RecoverShardSnapshotRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RecoverShardSnapshotRequest prototype) {
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

        public static RecoverShardSnapshotRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RecoverShardSnapshotRequest> parser() {
            return PARSER;
        }

        public Parser<RecoverShardSnapshotRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public RecoverShardSnapshotRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements RecoverShardSnapshotRequestOrBuilder {
            private int bitField0_;
            private Object collectionName_ = "";
            private int shardId_;
            private ShardSnapshotLocation snapshotLocation_;
            private SingleFieldBuilderV3<ShardSnapshotLocation, ShardSnapshotLocation.Builder, ShardSnapshotLocationOrBuilder> snapshotLocationBuilder_;
            private int snapshotPriority_ = 0;
            private Object checksum_ = "";
            private Object apiKey_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_RecoverShardSnapshotRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_RecoverShardSnapshotRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(RecoverShardSnapshotRequest.class, Builder.class);
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
                    this.getSnapshotLocationFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                this.collectionName_ = "";
                this.shardId_ = 0;
                this.snapshotLocation_ = null;
                if (this.snapshotLocationBuilder_ != null) {
                    this.snapshotLocationBuilder_.dispose();
                    this.snapshotLocationBuilder_ = null;
                }
                this.snapshotPriority_ = 0;
                this.checksum_ = "";
                this.apiKey_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_RecoverShardSnapshotRequest_descriptor;
            }

            @Override
            public RecoverShardSnapshotRequest getDefaultInstanceForType() {
                return RecoverShardSnapshotRequest.getDefaultInstance();
            }

            @Override
            public RecoverShardSnapshotRequest build() {
                RecoverShardSnapshotRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public RecoverShardSnapshotRequest buildPartial() {
                RecoverShardSnapshotRequest result = new RecoverShardSnapshotRequest(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(RecoverShardSnapshotRequest result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.collectionName_ = this.collectionName_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.shardId_ = this.shardId_;
                }
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 4) != 0) {
                    result.snapshotLocation_ = this.snapshotLocationBuilder_ == null ? this.snapshotLocation_ : this.snapshotLocationBuilder_.build();
                    to_bitField0_ |= 1;
                }
                if ((from_bitField0_ & 8) != 0) {
                    result.snapshotPriority_ = this.snapshotPriority_;
                }
                if ((from_bitField0_ & 0x10) != 0) {
                    result.checksum_ = this.checksum_;
                    to_bitField0_ |= 2;
                }
                if ((from_bitField0_ & 0x20) != 0) {
                    result.apiKey_ = this.apiKey_;
                    to_bitField0_ |= 4;
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
                if (other instanceof RecoverShardSnapshotRequest) {
                    return this.mergeFrom((RecoverShardSnapshotRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(RecoverShardSnapshotRequest other) {
                if (other == RecoverShardSnapshotRequest.getDefaultInstance()) {
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
                if (other.hasSnapshotLocation()) {
                    this.mergeSnapshotLocation(other.getSnapshotLocation());
                }
                if (other.snapshotPriority_ != 0) {
                    this.setSnapshotPriorityValue(other.getSnapshotPriorityValue());
                }
                if (other.hasChecksum()) {
                    this.checksum_ = other.checksum_;
                    this.bitField0_ |= 0x10;
                    this.onChanged();
                }
                if (other.hasApiKey()) {
                    this.apiKey_ = other.apiKey_;
                    this.bitField0_ |= 0x20;
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
                    block14: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block14;
                            }
                            case 10: {
                                this.collectionName_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block14;
                            }
                            case 16: {
                                this.shardId_ = input.readUInt32();
                                this.bitField0_ |= 2;
                                continue block14;
                            }
                            case 26: {
                                input.readMessage(this.getSnapshotLocationFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 4;
                                continue block14;
                            }
                            case 32: {
                                this.snapshotPriority_ = input.readEnum();
                                this.bitField0_ |= 8;
                                continue block14;
                            }
                            case 42: {
                                this.checksum_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 0x10;
                                continue block14;
                            }
                            case 50: {
                                this.apiKey_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 0x20;
                                continue block14;
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
                this.collectionName_ = RecoverShardSnapshotRequest.getDefaultInstance().getCollectionName();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setCollectionNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                RecoverShardSnapshotRequest.checkByteStringIsUtf8(value);
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
            public boolean hasSnapshotLocation() {
                return (this.bitField0_ & 4) != 0;
            }

            @Override
            public ShardSnapshotLocation getSnapshotLocation() {
                if (this.snapshotLocationBuilder_ == null) {
                    return this.snapshotLocation_ == null ? ShardSnapshotLocation.getDefaultInstance() : this.snapshotLocation_;
                }
                return this.snapshotLocationBuilder_.getMessage();
            }

            public Builder setSnapshotLocation(ShardSnapshotLocation value) {
                if (this.snapshotLocationBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.snapshotLocation_ = value;
                } else {
                    this.snapshotLocationBuilder_.setMessage(value);
                }
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder setSnapshotLocation(ShardSnapshotLocation.Builder builderForValue) {
                if (this.snapshotLocationBuilder_ == null) {
                    this.snapshotLocation_ = builderForValue.build();
                } else {
                    this.snapshotLocationBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder mergeSnapshotLocation(ShardSnapshotLocation value) {
                if (this.snapshotLocationBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 0 && this.snapshotLocation_ != null && this.snapshotLocation_ != ShardSnapshotLocation.getDefaultInstance()) {
                        this.getSnapshotLocationBuilder().mergeFrom(value);
                    } else {
                        this.snapshotLocation_ = value;
                    }
                } else {
                    this.snapshotLocationBuilder_.mergeFrom(value);
                }
                if (this.snapshotLocation_ != null) {
                    this.bitField0_ |= 4;
                    this.onChanged();
                }
                return this;
            }

            public Builder clearSnapshotLocation() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.snapshotLocation_ = null;
                if (this.snapshotLocationBuilder_ != null) {
                    this.snapshotLocationBuilder_.dispose();
                    this.snapshotLocationBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public ShardSnapshotLocation.Builder getSnapshotLocationBuilder() {
                this.bitField0_ |= 4;
                this.onChanged();
                return this.getSnapshotLocationFieldBuilder().getBuilder();
            }

            @Override
            public ShardSnapshotLocationOrBuilder getSnapshotLocationOrBuilder() {
                if (this.snapshotLocationBuilder_ != null) {
                    return this.snapshotLocationBuilder_.getMessageOrBuilder();
                }
                return this.snapshotLocation_ == null ? ShardSnapshotLocation.getDefaultInstance() : this.snapshotLocation_;
            }

            private SingleFieldBuilderV3<ShardSnapshotLocation, ShardSnapshotLocation.Builder, ShardSnapshotLocationOrBuilder> getSnapshotLocationFieldBuilder() {
                if (this.snapshotLocationBuilder_ == null) {
                    this.snapshotLocationBuilder_ = new SingleFieldBuilderV3(this.getSnapshotLocation(), this.getParentForChildren(), this.isClean());
                    this.snapshotLocation_ = null;
                }
                return this.snapshotLocationBuilder_;
            }

            @Override
            public int getSnapshotPriorityValue() {
                return this.snapshotPriority_;
            }

            public Builder setSnapshotPriorityValue(int value) {
                this.snapshotPriority_ = value;
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            @Override
            public ShardSnapshotPriority getSnapshotPriority() {
                ShardSnapshotPriority result = ShardSnapshotPriority.forNumber(this.snapshotPriority_);
                return result == null ? ShardSnapshotPriority.UNRECOGNIZED : result;
            }

            public Builder setSnapshotPriority(ShardSnapshotPriority value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.snapshotPriority_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearSnapshotPriority() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.snapshotPriority_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasChecksum() {
                return (this.bitField0_ & 0x10) != 0;
            }

            @Override
            public String getChecksum() {
                Object ref = this.checksum_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.checksum_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getChecksumBytes() {
                Object ref = this.checksum_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.checksum_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setChecksum(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.checksum_ = value;
                this.bitField0_ |= 0x10;
                this.onChanged();
                return this;
            }

            public Builder clearChecksum() {
                this.checksum_ = RecoverShardSnapshotRequest.getDefaultInstance().getChecksum();
                this.bitField0_ &= 0xFFFFFFEF;
                this.onChanged();
                return this;
            }

            public Builder setChecksumBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                RecoverShardSnapshotRequest.checkByteStringIsUtf8(value);
                this.checksum_ = value;
                this.bitField0_ |= 0x10;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasApiKey() {
                return (this.bitField0_ & 0x20) != 0;
            }

            @Override
            public String getApiKey() {
                Object ref = this.apiKey_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.apiKey_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getApiKeyBytes() {
                Object ref = this.apiKey_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.apiKey_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setApiKey(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.apiKey_ = value;
                this.bitField0_ |= 0x20;
                this.onChanged();
                return this;
            }

            public Builder clearApiKey() {
                this.apiKey_ = RecoverShardSnapshotRequest.getDefaultInstance().getApiKey();
                this.bitField0_ &= 0xFFFFFFDF;
                this.onChanged();
                return this;
            }

            public Builder setApiKeyBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                RecoverShardSnapshotRequest.checkByteStringIsUtf8(value);
                this.apiKey_ = value;
                this.bitField0_ |= 0x20;
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

    public static interface RecoverShardSnapshotRequestOrBuilder
    extends MessageOrBuilder {
        public String getCollectionName();

        public ByteString getCollectionNameBytes();

        public int getShardId();

        public boolean hasSnapshotLocation();

        public ShardSnapshotLocation getSnapshotLocation();

        public ShardSnapshotLocationOrBuilder getSnapshotLocationOrBuilder();

        public int getSnapshotPriorityValue();

        public ShardSnapshotPriority getSnapshotPriority();

        public boolean hasChecksum();

        public String getChecksum();

        public ByteString getChecksumBytes();

        public boolean hasApiKey();

        public String getApiKey();

        public ByteString getApiKeyBytes();
    }

    public static final class DeleteShardSnapshotRequest
    extends GeneratedMessageV3
    implements DeleteShardSnapshotRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int COLLECTION_NAME_FIELD_NUMBER = 1;
        private volatile Object collectionName_ = "";
        public static final int SHARD_ID_FIELD_NUMBER = 2;
        private int shardId_ = 0;
        public static final int SNAPSHOT_NAME_FIELD_NUMBER = 3;
        private volatile Object snapshotName_ = "";
        private byte memoizedIsInitialized = (byte)-1;
        private static final DeleteShardSnapshotRequest DEFAULT_INSTANCE = new DeleteShardSnapshotRequest();
        private static final Parser<DeleteShardSnapshotRequest> PARSER = new AbstractParser<DeleteShardSnapshotRequest>(){

            @Override
            public DeleteShardSnapshotRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = DeleteShardSnapshotRequest.newBuilder();
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

        private DeleteShardSnapshotRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private DeleteShardSnapshotRequest() {
            this.collectionName_ = "";
            this.snapshotName_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new DeleteShardSnapshotRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_DeleteShardSnapshotRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_DeleteShardSnapshotRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(DeleteShardSnapshotRequest.class, Builder.class);
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
        public String getSnapshotName() {
            Object ref = this.snapshotName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.snapshotName_ = s2;
            return s2;
        }

        @Override
        public ByteString getSnapshotNameBytes() {
            Object ref = this.snapshotName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.snapshotName_ = b;
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
            if (!GeneratedMessageV3.isStringEmpty(this.collectionName_)) {
                GeneratedMessageV3.writeString(output, 1, this.collectionName_);
            }
            if (this.shardId_ != 0) {
                output.writeUInt32(2, this.shardId_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.snapshotName_)) {
                GeneratedMessageV3.writeString(output, 3, this.snapshotName_);
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
            if (!GeneratedMessageV3.isStringEmpty(this.snapshotName_)) {
                size += GeneratedMessageV3.computeStringSize(3, this.snapshotName_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DeleteShardSnapshotRequest)) {
                return super.equals(obj);
            }
            DeleteShardSnapshotRequest other = (DeleteShardSnapshotRequest)obj;
            if (!this.getCollectionName().equals(other.getCollectionName())) {
                return false;
            }
            if (this.getShardId() != other.getShardId()) {
                return false;
            }
            if (!this.getSnapshotName().equals(other.getSnapshotName())) {
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
            hash = 19 * hash + DeleteShardSnapshotRequest.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getCollectionName().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getShardId();
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getSnapshotName().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static DeleteShardSnapshotRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeleteShardSnapshotRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeleteShardSnapshotRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeleteShardSnapshotRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeleteShardSnapshotRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeleteShardSnapshotRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeleteShardSnapshotRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeleteShardSnapshotRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeleteShardSnapshotRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DeleteShardSnapshotRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeleteShardSnapshotRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeleteShardSnapshotRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return DeleteShardSnapshotRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DeleteShardSnapshotRequest prototype) {
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

        public static DeleteShardSnapshotRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeleteShardSnapshotRequest> parser() {
            return PARSER;
        }

        public Parser<DeleteShardSnapshotRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public DeleteShardSnapshotRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements DeleteShardSnapshotRequestOrBuilder {
            private int bitField0_;
            private Object collectionName_ = "";
            private int shardId_;
            private Object snapshotName_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_DeleteShardSnapshotRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_DeleteShardSnapshotRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(DeleteShardSnapshotRequest.class, Builder.class);
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
                this.snapshotName_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_DeleteShardSnapshotRequest_descriptor;
            }

            @Override
            public DeleteShardSnapshotRequest getDefaultInstanceForType() {
                return DeleteShardSnapshotRequest.getDefaultInstance();
            }

            @Override
            public DeleteShardSnapshotRequest build() {
                DeleteShardSnapshotRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public DeleteShardSnapshotRequest buildPartial() {
                DeleteShardSnapshotRequest result = new DeleteShardSnapshotRequest(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(DeleteShardSnapshotRequest result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.collectionName_ = this.collectionName_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.shardId_ = this.shardId_;
                }
                if ((from_bitField0_ & 4) != 0) {
                    result.snapshotName_ = this.snapshotName_;
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
                if (other instanceof DeleteShardSnapshotRequest) {
                    return this.mergeFrom((DeleteShardSnapshotRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DeleteShardSnapshotRequest other) {
                if (other == DeleteShardSnapshotRequest.getDefaultInstance()) {
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
                if (!other.getSnapshotName().isEmpty()) {
                    this.snapshotName_ = other.snapshotName_;
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
                                this.snapshotName_ = input.readStringRequireUtf8();
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
                this.collectionName_ = DeleteShardSnapshotRequest.getDefaultInstance().getCollectionName();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setCollectionNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                DeleteShardSnapshotRequest.checkByteStringIsUtf8(value);
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
            public String getSnapshotName() {
                Object ref = this.snapshotName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.snapshotName_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getSnapshotNameBytes() {
                Object ref = this.snapshotName_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.snapshotName_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setSnapshotName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.snapshotName_ = value;
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder clearSnapshotName() {
                this.snapshotName_ = DeleteShardSnapshotRequest.getDefaultInstance().getSnapshotName();
                this.bitField0_ &= 0xFFFFFFFB;
                this.onChanged();
                return this;
            }

            public Builder setSnapshotNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                DeleteShardSnapshotRequest.checkByteStringIsUtf8(value);
                this.snapshotName_ = value;
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

    public static interface DeleteShardSnapshotRequestOrBuilder
    extends MessageOrBuilder {
        public String getCollectionName();

        public ByteString getCollectionNameBytes();

        public int getShardId();

        public String getSnapshotName();

        public ByteString getSnapshotNameBytes();
    }

    public static final class ListShardSnapshotsRequest
    extends GeneratedMessageV3
    implements ListShardSnapshotsRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int COLLECTION_NAME_FIELD_NUMBER = 1;
        private volatile Object collectionName_ = "";
        public static final int SHARD_ID_FIELD_NUMBER = 2;
        private int shardId_ = 0;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ListShardSnapshotsRequest DEFAULT_INSTANCE = new ListShardSnapshotsRequest();
        private static final Parser<ListShardSnapshotsRequest> PARSER = new AbstractParser<ListShardSnapshotsRequest>(){

            @Override
            public ListShardSnapshotsRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = ListShardSnapshotsRequest.newBuilder();
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

        private ListShardSnapshotsRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ListShardSnapshotsRequest() {
            this.collectionName_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new ListShardSnapshotsRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_ListShardSnapshotsRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_ListShardSnapshotsRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(ListShardSnapshotsRequest.class, Builder.class);
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
            if (!(obj instanceof ListShardSnapshotsRequest)) {
                return super.equals(obj);
            }
            ListShardSnapshotsRequest other = (ListShardSnapshotsRequest)obj;
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
            hash = 19 * hash + ListShardSnapshotsRequest.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getCollectionName().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getShardId();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static ListShardSnapshotsRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListShardSnapshotsRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListShardSnapshotsRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListShardSnapshotsRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListShardSnapshotsRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListShardSnapshotsRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListShardSnapshotsRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ListShardSnapshotsRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ListShardSnapshotsRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ListShardSnapshotsRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ListShardSnapshotsRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ListShardSnapshotsRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ListShardSnapshotsRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ListShardSnapshotsRequest prototype) {
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

        public static ListShardSnapshotsRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ListShardSnapshotsRequest> parser() {
            return PARSER;
        }

        public Parser<ListShardSnapshotsRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public ListShardSnapshotsRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ListShardSnapshotsRequestOrBuilder {
            private int bitField0_;
            private Object collectionName_ = "";
            private int shardId_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_ListShardSnapshotsRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_ListShardSnapshotsRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(ListShardSnapshotsRequest.class, Builder.class);
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
                return internal_static_qdrant_ListShardSnapshotsRequest_descriptor;
            }

            @Override
            public ListShardSnapshotsRequest getDefaultInstanceForType() {
                return ListShardSnapshotsRequest.getDefaultInstance();
            }

            @Override
            public ListShardSnapshotsRequest build() {
                ListShardSnapshotsRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ListShardSnapshotsRequest buildPartial() {
                ListShardSnapshotsRequest result = new ListShardSnapshotsRequest(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(ListShardSnapshotsRequest result) {
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
                if (other instanceof ListShardSnapshotsRequest) {
                    return this.mergeFrom((ListShardSnapshotsRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ListShardSnapshotsRequest other) {
                if (other == ListShardSnapshotsRequest.getDefaultInstance()) {
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
                this.collectionName_ = ListShardSnapshotsRequest.getDefaultInstance().getCollectionName();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setCollectionNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ListShardSnapshotsRequest.checkByteStringIsUtf8(value);
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

    public static interface ListShardSnapshotsRequestOrBuilder
    extends MessageOrBuilder {
        public String getCollectionName();

        public ByteString getCollectionNameBytes();

        public int getShardId();
    }

    public static final class CreateShardSnapshotRequest
    extends GeneratedMessageV3
    implements CreateShardSnapshotRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int COLLECTION_NAME_FIELD_NUMBER = 1;
        private volatile Object collectionName_ = "";
        public static final int SHARD_ID_FIELD_NUMBER = 2;
        private int shardId_ = 0;
        private byte memoizedIsInitialized = (byte)-1;
        private static final CreateShardSnapshotRequest DEFAULT_INSTANCE = new CreateShardSnapshotRequest();
        private static final Parser<CreateShardSnapshotRequest> PARSER = new AbstractParser<CreateShardSnapshotRequest>(){

            @Override
            public CreateShardSnapshotRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = CreateShardSnapshotRequest.newBuilder();
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

        private CreateShardSnapshotRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private CreateShardSnapshotRequest() {
            this.collectionName_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new CreateShardSnapshotRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_CreateShardSnapshotRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_CreateShardSnapshotRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(CreateShardSnapshotRequest.class, Builder.class);
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
            if (!(obj instanceof CreateShardSnapshotRequest)) {
                return super.equals(obj);
            }
            CreateShardSnapshotRequest other = (CreateShardSnapshotRequest)obj;
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
            hash = 19 * hash + CreateShardSnapshotRequest.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getCollectionName().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getShardId();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static CreateShardSnapshotRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CreateShardSnapshotRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CreateShardSnapshotRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CreateShardSnapshotRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CreateShardSnapshotRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CreateShardSnapshotRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CreateShardSnapshotRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CreateShardSnapshotRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static CreateShardSnapshotRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static CreateShardSnapshotRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static CreateShardSnapshotRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CreateShardSnapshotRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return CreateShardSnapshotRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CreateShardSnapshotRequest prototype) {
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

        public static CreateShardSnapshotRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CreateShardSnapshotRequest> parser() {
            return PARSER;
        }

        public Parser<CreateShardSnapshotRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public CreateShardSnapshotRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements CreateShardSnapshotRequestOrBuilder {
            private int bitField0_;
            private Object collectionName_ = "";
            private int shardId_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_CreateShardSnapshotRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_CreateShardSnapshotRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(CreateShardSnapshotRequest.class, Builder.class);
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
                return internal_static_qdrant_CreateShardSnapshotRequest_descriptor;
            }

            @Override
            public CreateShardSnapshotRequest getDefaultInstanceForType() {
                return CreateShardSnapshotRequest.getDefaultInstance();
            }

            @Override
            public CreateShardSnapshotRequest build() {
                CreateShardSnapshotRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public CreateShardSnapshotRequest buildPartial() {
                CreateShardSnapshotRequest result = new CreateShardSnapshotRequest(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(CreateShardSnapshotRequest result) {
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
                if (other instanceof CreateShardSnapshotRequest) {
                    return this.mergeFrom((CreateShardSnapshotRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(CreateShardSnapshotRequest other) {
                if (other == CreateShardSnapshotRequest.getDefaultInstance()) {
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
                this.collectionName_ = CreateShardSnapshotRequest.getDefaultInstance().getCollectionName();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setCollectionNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                CreateShardSnapshotRequest.checkByteStringIsUtf8(value);
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

    public static interface CreateShardSnapshotRequestOrBuilder
    extends MessageOrBuilder {
        public String getCollectionName();

        public ByteString getCollectionNameBytes();

        public int getShardId();
    }

    public static enum ShardSnapshotPriority implements ProtocolMessageEnum
    {
        ShardSnapshotPriorityNoSync(0),
        ShardSnapshotPrioritySnapshot(1),
        ShardSnapshotPriorityReplica(2),
        ShardSnapshotPriorityShardTransfer(3),
        UNRECOGNIZED(-1);

        public static final int ShardSnapshotPriorityNoSync_VALUE = 0;
        public static final int ShardSnapshotPrioritySnapshot_VALUE = 1;
        public static final int ShardSnapshotPriorityReplica_VALUE = 2;
        public static final int ShardSnapshotPriorityShardTransfer_VALUE = 3;
        private static final Internal.EnumLiteMap<ShardSnapshotPriority> internalValueMap;
        private static final ShardSnapshotPriority[] VALUES;
        private final int value;

        @Override
        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }
            return this.value;
        }

        @Deprecated
        public static ShardSnapshotPriority valueOf(int value) {
            return ShardSnapshotPriority.forNumber(value);
        }

        public static ShardSnapshotPriority forNumber(int value) {
            switch (value) {
                case 0: {
                    return ShardSnapshotPriorityNoSync;
                }
                case 1: {
                    return ShardSnapshotPrioritySnapshot;
                }
                case 2: {
                    return ShardSnapshotPriorityReplica;
                }
                case 3: {
                    return ShardSnapshotPriorityShardTransfer;
                }
            }
            return null;
        }

        public static Internal.EnumLiteMap<ShardSnapshotPriority> internalGetValueMap() {
            return internalValueMap;
        }

        @Override
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            if (this == UNRECOGNIZED) {
                throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
            }
            return ShardSnapshotPriority.getDescriptor().getValues().get(this.ordinal());
        }

        @Override
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return ShardSnapshotPriority.getDescriptor();
        }

        public static final Descriptors.EnumDescriptor getDescriptor() {
            return ShardSnapshotsService.getDescriptor().getEnumTypes().get(0);
        }

        public static ShardSnapshotPriority valueOf(Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != ShardSnapshotPriority.getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            if (desc.getIndex() == -1) {
                return UNRECOGNIZED;
            }
            return VALUES[desc.getIndex()];
        }

        private ShardSnapshotPriority(int value) {
            this.value = value;
        }

        static {
            internalValueMap = new Internal.EnumLiteMap<ShardSnapshotPriority>(){

                @Override
                public ShardSnapshotPriority findValueByNumber(int number) {
                    return ShardSnapshotPriority.forNumber(number);
                }
            };
            VALUES = ShardSnapshotPriority.values();
        }
    }
}

