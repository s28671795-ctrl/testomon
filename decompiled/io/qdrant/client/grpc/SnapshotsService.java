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
import com.google.protobuf.Timestamp;
import com.google.protobuf.TimestampOrBuilder;
import com.google.protobuf.TimestampProto;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SnapshotsService {
    private static final Descriptors.Descriptor internal_static_qdrant_CreateFullSnapshotRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_CreateFullSnapshotRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_ListFullSnapshotsRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_ListFullSnapshotsRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_DeleteFullSnapshotRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_DeleteFullSnapshotRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_CreateSnapshotRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_CreateSnapshotRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_ListSnapshotsRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_ListSnapshotsRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_DeleteSnapshotRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_DeleteSnapshotRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_SnapshotDescription_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_SnapshotDescription_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_CreateSnapshotResponse_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_CreateSnapshotResponse_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_ListSnapshotsResponse_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_ListSnapshotsResponse_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_DeleteSnapshotResponse_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_DeleteSnapshotResponse_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private SnapshotsService() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        SnapshotsService.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0017snapshots_service.proto\u0012\u0006qdrant\u001a\u001fgoogle/protobuf/timestamp.proto\"\u001b\n\u0019CreateFullSnapshotRequest\"\u001a\n\u0018ListFullSnapshotsRequest\"2\n\u0019DeleteFullSnapshotRequest\u0012\u0015\n\rsnapshot_name\u0018\u0001 \u0001(\t\"0\n\u0015CreateSnapshotRequest\u0012\u0017\n\u000fcollection_name\u0018\u0001 \u0001(\t\"/\n\u0014ListSnapshotsRequest\u0012\u0017\n\u000fcollection_name\u0018\u0001 \u0001(\t\"G\n\u0015DeleteSnapshotRequest\u0012\u0017\n\u000fcollection_name\u0018\u0001 \u0001(\t\u0012\u0015\n\rsnapshot_name\u0018\u0002 \u0001(\t\"\u0088\u0001\n\u0013SnapshotDescription\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u00121\n\rcreation_time\u0018\u0002 \u0001(\u000b2\u001a.google.protobuf.Timestamp\u0012\f\n\u0004size\u0018\u0003 \u0001(\u0003\u0012\u0015\n\bchecksum\u0018\u0004 \u0001(\tH\u0000\u0088\u0001\u0001B\u000b\n\t_checksum\"a\n\u0016CreateSnapshotResponse\u00129\n\u0014snapshot_description\u0018\u0001 \u0001(\u000b2\u001b.qdrant.SnapshotDescription\u0012\f\n\u0004time\u0018\u0002 \u0001(\u0001\"a\n\u0015ListSnapshotsResponse\u0012:\n\u0015snapshot_descriptions\u0018\u0001 \u0003(\u000b2\u001b.qdrant.SnapshotDescription\u0012\f\n\u0004time\u0018\u0002 \u0001(\u0001\"&\n\u0016DeleteSnapshotResponse\u0012\f\n\u0004time\u0018\u0001 \u0001(\u00012\u00dd\u0003\n\tSnapshots\u0012I\n\u0006Create\u0012\u001d.qdrant.CreateSnapshotRequest\u001a\u001e.qdrant.CreateSnapshotResponse\"\u0000\u0012E\n\u0004List\u0012\u001c.qdrant.ListSnapshotsRequest\u001a\u001d.qdrant.ListSnapshotsResponse\"\u0000\u0012I\n\u0006Delete\u0012\u001d.qdrant.DeleteSnapshotRequest\u001a\u001e.qdrant.DeleteSnapshotResponse\"\u0000\u0012Q\n\nCreateFull\u0012!.qdrant.CreateFullSnapshotRequest\u001a\u001e.qdrant.CreateSnapshotResponse\"\u0000\u0012M\n\bListFull\u0012 .qdrant.ListFullSnapshotsRequest\u001a\u001d.qdrant.ListSnapshotsResponse\"\u0000\u0012Q\n\nDeleteFull\u0012!.qdrant.DeleteFullSnapshotRequest\u001a\u001e.qdrant.DeleteSnapshotResponse\"\u0000B,\n\u0015io.qdrant.client.grpc\u00aa\u0002\u0012Qdrant.Client.Grpcb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{TimestampProto.getDescriptor()});
        internal_static_qdrant_CreateFullSnapshotRequest_descriptor = SnapshotsService.getDescriptor().getMessageTypes().get(0);
        internal_static_qdrant_CreateFullSnapshotRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_CreateFullSnapshotRequest_descriptor, new String[0]);
        internal_static_qdrant_ListFullSnapshotsRequest_descriptor = SnapshotsService.getDescriptor().getMessageTypes().get(1);
        internal_static_qdrant_ListFullSnapshotsRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_ListFullSnapshotsRequest_descriptor, new String[0]);
        internal_static_qdrant_DeleteFullSnapshotRequest_descriptor = SnapshotsService.getDescriptor().getMessageTypes().get(2);
        internal_static_qdrant_DeleteFullSnapshotRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_DeleteFullSnapshotRequest_descriptor, new String[]{"SnapshotName"});
        internal_static_qdrant_CreateSnapshotRequest_descriptor = SnapshotsService.getDescriptor().getMessageTypes().get(3);
        internal_static_qdrant_CreateSnapshotRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_CreateSnapshotRequest_descriptor, new String[]{"CollectionName"});
        internal_static_qdrant_ListSnapshotsRequest_descriptor = SnapshotsService.getDescriptor().getMessageTypes().get(4);
        internal_static_qdrant_ListSnapshotsRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_ListSnapshotsRequest_descriptor, new String[]{"CollectionName"});
        internal_static_qdrant_DeleteSnapshotRequest_descriptor = SnapshotsService.getDescriptor().getMessageTypes().get(5);
        internal_static_qdrant_DeleteSnapshotRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_DeleteSnapshotRequest_descriptor, new String[]{"CollectionName", "SnapshotName"});
        internal_static_qdrant_SnapshotDescription_descriptor = SnapshotsService.getDescriptor().getMessageTypes().get(6);
        internal_static_qdrant_SnapshotDescription_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_SnapshotDescription_descriptor, new String[]{"Name", "CreationTime", "Size", "Checksum"});
        internal_static_qdrant_CreateSnapshotResponse_descriptor = SnapshotsService.getDescriptor().getMessageTypes().get(7);
        internal_static_qdrant_CreateSnapshotResponse_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_CreateSnapshotResponse_descriptor, new String[]{"SnapshotDescription", "Time"});
        internal_static_qdrant_ListSnapshotsResponse_descriptor = SnapshotsService.getDescriptor().getMessageTypes().get(8);
        internal_static_qdrant_ListSnapshotsResponse_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_ListSnapshotsResponse_descriptor, new String[]{"SnapshotDescriptions", "Time"});
        internal_static_qdrant_DeleteSnapshotResponse_descriptor = SnapshotsService.getDescriptor().getMessageTypes().get(9);
        internal_static_qdrant_DeleteSnapshotResponse_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_DeleteSnapshotResponse_descriptor, new String[]{"Time"});
        TimestampProto.getDescriptor();
    }

    public static final class DeleteSnapshotResponse
    extends GeneratedMessageV3
    implements DeleteSnapshotResponseOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int TIME_FIELD_NUMBER = 1;
        private double time_ = 0.0;
        private byte memoizedIsInitialized = (byte)-1;
        private static final DeleteSnapshotResponse DEFAULT_INSTANCE = new DeleteSnapshotResponse();
        private static final Parser<DeleteSnapshotResponse> PARSER = new AbstractParser<DeleteSnapshotResponse>(){

            @Override
            public DeleteSnapshotResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = DeleteSnapshotResponse.newBuilder();
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

        private DeleteSnapshotResponse(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private DeleteSnapshotResponse() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new DeleteSnapshotResponse();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_DeleteSnapshotResponse_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_DeleteSnapshotResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(DeleteSnapshotResponse.class, Builder.class);
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
            if (!(obj instanceof DeleteSnapshotResponse)) {
                return super.equals(obj);
            }
            DeleteSnapshotResponse other = (DeleteSnapshotResponse)obj;
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
            hash = 19 * hash + DeleteSnapshotResponse.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getTime()));
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static DeleteSnapshotResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeleteSnapshotResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeleteSnapshotResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeleteSnapshotResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeleteSnapshotResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeleteSnapshotResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeleteSnapshotResponse parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeleteSnapshotResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeleteSnapshotResponse parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DeleteSnapshotResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeleteSnapshotResponse parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeleteSnapshotResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return DeleteSnapshotResponse.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DeleteSnapshotResponse prototype) {
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

        public static DeleteSnapshotResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeleteSnapshotResponse> parser() {
            return PARSER;
        }

        public Parser<DeleteSnapshotResponse> getParserForType() {
            return PARSER;
        }

        @Override
        public DeleteSnapshotResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements DeleteSnapshotResponseOrBuilder {
            private int bitField0_;
            private double time_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_DeleteSnapshotResponse_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_DeleteSnapshotResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(DeleteSnapshotResponse.class, Builder.class);
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
                return internal_static_qdrant_DeleteSnapshotResponse_descriptor;
            }

            @Override
            public DeleteSnapshotResponse getDefaultInstanceForType() {
                return DeleteSnapshotResponse.getDefaultInstance();
            }

            @Override
            public DeleteSnapshotResponse build() {
                DeleteSnapshotResponse result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public DeleteSnapshotResponse buildPartial() {
                DeleteSnapshotResponse result = new DeleteSnapshotResponse(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(DeleteSnapshotResponse result) {
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
                if (other instanceof DeleteSnapshotResponse) {
                    return this.mergeFrom((DeleteSnapshotResponse)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DeleteSnapshotResponse other) {
                if (other == DeleteSnapshotResponse.getDefaultInstance()) {
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

    public static interface DeleteSnapshotResponseOrBuilder
    extends MessageOrBuilder {
        public double getTime();
    }

    public static final class ListSnapshotsResponse
    extends GeneratedMessageV3
    implements ListSnapshotsResponseOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int SNAPSHOT_DESCRIPTIONS_FIELD_NUMBER = 1;
        private List<SnapshotDescription> snapshotDescriptions_;
        public static final int TIME_FIELD_NUMBER = 2;
        private double time_ = 0.0;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ListSnapshotsResponse DEFAULT_INSTANCE = new ListSnapshotsResponse();
        private static final Parser<ListSnapshotsResponse> PARSER = new AbstractParser<ListSnapshotsResponse>(){

            @Override
            public ListSnapshotsResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = ListSnapshotsResponse.newBuilder();
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

        private ListSnapshotsResponse(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ListSnapshotsResponse() {
            this.snapshotDescriptions_ = Collections.emptyList();
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new ListSnapshotsResponse();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_ListSnapshotsResponse_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_ListSnapshotsResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(ListSnapshotsResponse.class, Builder.class);
        }

        @Override
        public List<SnapshotDescription> getSnapshotDescriptionsList() {
            return this.snapshotDescriptions_;
        }

        @Override
        public List<? extends SnapshotDescriptionOrBuilder> getSnapshotDescriptionsOrBuilderList() {
            return this.snapshotDescriptions_;
        }

        @Override
        public int getSnapshotDescriptionsCount() {
            return this.snapshotDescriptions_.size();
        }

        @Override
        public SnapshotDescription getSnapshotDescriptions(int index) {
            return this.snapshotDescriptions_.get(index);
        }

        @Override
        public SnapshotDescriptionOrBuilder getSnapshotDescriptionsOrBuilder(int index) {
            return this.snapshotDescriptions_.get(index);
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
            for (int i = 0; i < this.snapshotDescriptions_.size(); ++i) {
                output.writeMessage(1, this.snapshotDescriptions_.get(i));
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
            for (int i = 0; i < this.snapshotDescriptions_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, this.snapshotDescriptions_.get(i));
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
            if (!(obj instanceof ListSnapshotsResponse)) {
                return super.equals(obj);
            }
            ListSnapshotsResponse other = (ListSnapshotsResponse)obj;
            if (!this.getSnapshotDescriptionsList().equals(other.getSnapshotDescriptionsList())) {
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
            hash = 19 * hash + ListSnapshotsResponse.getDescriptor().hashCode();
            if (this.getSnapshotDescriptionsCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getSnapshotDescriptionsList().hashCode();
            }
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getTime()));
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static ListSnapshotsResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListSnapshotsResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListSnapshotsResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListSnapshotsResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListSnapshotsResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListSnapshotsResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListSnapshotsResponse parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ListSnapshotsResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ListSnapshotsResponse parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ListSnapshotsResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ListSnapshotsResponse parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ListSnapshotsResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ListSnapshotsResponse.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ListSnapshotsResponse prototype) {
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

        public static ListSnapshotsResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ListSnapshotsResponse> parser() {
            return PARSER;
        }

        public Parser<ListSnapshotsResponse> getParserForType() {
            return PARSER;
        }

        @Override
        public ListSnapshotsResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ListSnapshotsResponseOrBuilder {
            private int bitField0_;
            private List<SnapshotDescription> snapshotDescriptions_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<SnapshotDescription, SnapshotDescription.Builder, SnapshotDescriptionOrBuilder> snapshotDescriptionsBuilder_;
            private double time_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_ListSnapshotsResponse_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_ListSnapshotsResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(ListSnapshotsResponse.class, Builder.class);
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
                if (this.snapshotDescriptionsBuilder_ == null) {
                    this.snapshotDescriptions_ = Collections.emptyList();
                } else {
                    this.snapshotDescriptions_ = null;
                    this.snapshotDescriptionsBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                this.time_ = 0.0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_ListSnapshotsResponse_descriptor;
            }

            @Override
            public ListSnapshotsResponse getDefaultInstanceForType() {
                return ListSnapshotsResponse.getDefaultInstance();
            }

            @Override
            public ListSnapshotsResponse build() {
                ListSnapshotsResponse result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ListSnapshotsResponse buildPartial() {
                ListSnapshotsResponse result = new ListSnapshotsResponse(this);
                this.buildPartialRepeatedFields(result);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartialRepeatedFields(ListSnapshotsResponse result) {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 0) {
                        this.snapshotDescriptions_ = Collections.unmodifiableList(this.snapshotDescriptions_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.snapshotDescriptions_ = this.snapshotDescriptions_;
                } else {
                    result.snapshotDescriptions_ = this.snapshotDescriptionsBuilder_.build();
                }
            }

            private void buildPartial0(ListSnapshotsResponse result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 2) != 0) {
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
                if (other instanceof ListSnapshotsResponse) {
                    return this.mergeFrom((ListSnapshotsResponse)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ListSnapshotsResponse other) {
                if (other == ListSnapshotsResponse.getDefaultInstance()) {
                    return this;
                }
                if (this.snapshotDescriptionsBuilder_ == null) {
                    if (!other.snapshotDescriptions_.isEmpty()) {
                        if (this.snapshotDescriptions_.isEmpty()) {
                            this.snapshotDescriptions_ = other.snapshotDescriptions_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureSnapshotDescriptionsIsMutable();
                            this.snapshotDescriptions_.addAll(other.snapshotDescriptions_);
                        }
                        this.onChanged();
                    }
                } else if (!other.snapshotDescriptions_.isEmpty()) {
                    if (this.snapshotDescriptionsBuilder_.isEmpty()) {
                        this.snapshotDescriptionsBuilder_.dispose();
                        this.snapshotDescriptionsBuilder_ = null;
                        this.snapshotDescriptions_ = other.snapshotDescriptions_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.snapshotDescriptionsBuilder_ = alwaysUseFieldBuilders ? this.getSnapshotDescriptionsFieldBuilder() : null;
                    } else {
                        this.snapshotDescriptionsBuilder_.addAllMessages(other.snapshotDescriptions_);
                    }
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
                                SnapshotDescription m4 = input.readMessage(SnapshotDescription.parser(), extensionRegistry);
                                if (this.snapshotDescriptionsBuilder_ == null) {
                                    this.ensureSnapshotDescriptionsIsMutable();
                                    this.snapshotDescriptions_.add(m4);
                                    continue block10;
                                }
                                this.snapshotDescriptionsBuilder_.addMessage(m4);
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

            private void ensureSnapshotDescriptionsIsMutable() {
                if ((this.bitField0_ & 1) == 0) {
                    this.snapshotDescriptions_ = new ArrayList<SnapshotDescription>(this.snapshotDescriptions_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<SnapshotDescription> getSnapshotDescriptionsList() {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    return Collections.unmodifiableList(this.snapshotDescriptions_);
                }
                return this.snapshotDescriptionsBuilder_.getMessageList();
            }

            @Override
            public int getSnapshotDescriptionsCount() {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    return this.snapshotDescriptions_.size();
                }
                return this.snapshotDescriptionsBuilder_.getCount();
            }

            @Override
            public SnapshotDescription getSnapshotDescriptions(int index) {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    return this.snapshotDescriptions_.get(index);
                }
                return this.snapshotDescriptionsBuilder_.getMessage(index);
            }

            public Builder setSnapshotDescriptions(int index, SnapshotDescription value) {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureSnapshotDescriptionsIsMutable();
                    this.snapshotDescriptions_.set(index, value);
                    this.onChanged();
                } else {
                    this.snapshotDescriptionsBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setSnapshotDescriptions(int index, SnapshotDescription.Builder builderForValue) {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    this.ensureSnapshotDescriptionsIsMutable();
                    this.snapshotDescriptions_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.snapshotDescriptionsBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addSnapshotDescriptions(SnapshotDescription value) {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureSnapshotDescriptionsIsMutable();
                    this.snapshotDescriptions_.add(value);
                    this.onChanged();
                } else {
                    this.snapshotDescriptionsBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addSnapshotDescriptions(int index, SnapshotDescription value) {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureSnapshotDescriptionsIsMutable();
                    this.snapshotDescriptions_.add(index, value);
                    this.onChanged();
                } else {
                    this.snapshotDescriptionsBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addSnapshotDescriptions(SnapshotDescription.Builder builderForValue) {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    this.ensureSnapshotDescriptionsIsMutable();
                    this.snapshotDescriptions_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.snapshotDescriptionsBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addSnapshotDescriptions(int index, SnapshotDescription.Builder builderForValue) {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    this.ensureSnapshotDescriptionsIsMutable();
                    this.snapshotDescriptions_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.snapshotDescriptionsBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllSnapshotDescriptions(Iterable<? extends SnapshotDescription> values2) {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    this.ensureSnapshotDescriptionsIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.snapshotDescriptions_);
                    this.onChanged();
                } else {
                    this.snapshotDescriptionsBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearSnapshotDescriptions() {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    this.snapshotDescriptions_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.snapshotDescriptionsBuilder_.clear();
                }
                return this;
            }

            public Builder removeSnapshotDescriptions(int index) {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    this.ensureSnapshotDescriptionsIsMutable();
                    this.snapshotDescriptions_.remove(index);
                    this.onChanged();
                } else {
                    this.snapshotDescriptionsBuilder_.remove(index);
                }
                return this;
            }

            public SnapshotDescription.Builder getSnapshotDescriptionsBuilder(int index) {
                return this.getSnapshotDescriptionsFieldBuilder().getBuilder(index);
            }

            @Override
            public SnapshotDescriptionOrBuilder getSnapshotDescriptionsOrBuilder(int index) {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    return this.snapshotDescriptions_.get(index);
                }
                return this.snapshotDescriptionsBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends SnapshotDescriptionOrBuilder> getSnapshotDescriptionsOrBuilderList() {
                if (this.snapshotDescriptionsBuilder_ != null) {
                    return this.snapshotDescriptionsBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.snapshotDescriptions_);
            }

            public SnapshotDescription.Builder addSnapshotDescriptionsBuilder() {
                return this.getSnapshotDescriptionsFieldBuilder().addBuilder(SnapshotDescription.getDefaultInstance());
            }

            public SnapshotDescription.Builder addSnapshotDescriptionsBuilder(int index) {
                return this.getSnapshotDescriptionsFieldBuilder().addBuilder(index, SnapshotDescription.getDefaultInstance());
            }

            public List<SnapshotDescription.Builder> getSnapshotDescriptionsBuilderList() {
                return this.getSnapshotDescriptionsFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<SnapshotDescription, SnapshotDescription.Builder, SnapshotDescriptionOrBuilder> getSnapshotDescriptionsFieldBuilder() {
                if (this.snapshotDescriptionsBuilder_ == null) {
                    this.snapshotDescriptionsBuilder_ = new RepeatedFieldBuilderV3(this.snapshotDescriptions_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
                    this.snapshotDescriptions_ = null;
                }
                return this.snapshotDescriptionsBuilder_;
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

    public static interface ListSnapshotsResponseOrBuilder
    extends MessageOrBuilder {
        public List<SnapshotDescription> getSnapshotDescriptionsList();

        public SnapshotDescription getSnapshotDescriptions(int var1);

        public int getSnapshotDescriptionsCount();

        public List<? extends SnapshotDescriptionOrBuilder> getSnapshotDescriptionsOrBuilderList();

        public SnapshotDescriptionOrBuilder getSnapshotDescriptionsOrBuilder(int var1);

        public double getTime();
    }

    public static final class CreateSnapshotResponse
    extends GeneratedMessageV3
    implements CreateSnapshotResponseOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int SNAPSHOT_DESCRIPTION_FIELD_NUMBER = 1;
        private SnapshotDescription snapshotDescription_;
        public static final int TIME_FIELD_NUMBER = 2;
        private double time_ = 0.0;
        private byte memoizedIsInitialized = (byte)-1;
        private static final CreateSnapshotResponse DEFAULT_INSTANCE = new CreateSnapshotResponse();
        private static final Parser<CreateSnapshotResponse> PARSER = new AbstractParser<CreateSnapshotResponse>(){

            @Override
            public CreateSnapshotResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = CreateSnapshotResponse.newBuilder();
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

        private CreateSnapshotResponse(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private CreateSnapshotResponse() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new CreateSnapshotResponse();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_CreateSnapshotResponse_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_CreateSnapshotResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(CreateSnapshotResponse.class, Builder.class);
        }

        @Override
        public boolean hasSnapshotDescription() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public SnapshotDescription getSnapshotDescription() {
            return this.snapshotDescription_ == null ? SnapshotDescription.getDefaultInstance() : this.snapshotDescription_;
        }

        @Override
        public SnapshotDescriptionOrBuilder getSnapshotDescriptionOrBuilder() {
            return this.snapshotDescription_ == null ? SnapshotDescription.getDefaultInstance() : this.snapshotDescription_;
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
                output.writeMessage(1, this.getSnapshotDescription());
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
                size += CodedOutputStream.computeMessageSize(1, this.getSnapshotDescription());
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
            if (!(obj instanceof CreateSnapshotResponse)) {
                return super.equals(obj);
            }
            CreateSnapshotResponse other = (CreateSnapshotResponse)obj;
            if (this.hasSnapshotDescription() != other.hasSnapshotDescription()) {
                return false;
            }
            if (this.hasSnapshotDescription() && !this.getSnapshotDescription().equals(other.getSnapshotDescription())) {
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
            hash = 19 * hash + CreateSnapshotResponse.getDescriptor().hashCode();
            if (this.hasSnapshotDescription()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getSnapshotDescription().hashCode();
            }
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getTime()));
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static CreateSnapshotResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CreateSnapshotResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CreateSnapshotResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CreateSnapshotResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CreateSnapshotResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CreateSnapshotResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CreateSnapshotResponse parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CreateSnapshotResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static CreateSnapshotResponse parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static CreateSnapshotResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static CreateSnapshotResponse parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CreateSnapshotResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return CreateSnapshotResponse.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CreateSnapshotResponse prototype) {
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

        public static CreateSnapshotResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CreateSnapshotResponse> parser() {
            return PARSER;
        }

        public Parser<CreateSnapshotResponse> getParserForType() {
            return PARSER;
        }

        @Override
        public CreateSnapshotResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements CreateSnapshotResponseOrBuilder {
            private int bitField0_;
            private SnapshotDescription snapshotDescription_;
            private SingleFieldBuilderV3<SnapshotDescription, SnapshotDescription.Builder, SnapshotDescriptionOrBuilder> snapshotDescriptionBuilder_;
            private double time_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_CreateSnapshotResponse_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_CreateSnapshotResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(CreateSnapshotResponse.class, Builder.class);
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
                    this.getSnapshotDescriptionFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                this.snapshotDescription_ = null;
                if (this.snapshotDescriptionBuilder_ != null) {
                    this.snapshotDescriptionBuilder_.dispose();
                    this.snapshotDescriptionBuilder_ = null;
                }
                this.time_ = 0.0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_CreateSnapshotResponse_descriptor;
            }

            @Override
            public CreateSnapshotResponse getDefaultInstanceForType() {
                return CreateSnapshotResponse.getDefaultInstance();
            }

            @Override
            public CreateSnapshotResponse build() {
                CreateSnapshotResponse result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public CreateSnapshotResponse buildPartial() {
                CreateSnapshotResponse result = new CreateSnapshotResponse(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(CreateSnapshotResponse result) {
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) != 0) {
                    result.snapshotDescription_ = this.snapshotDescriptionBuilder_ == null ? this.snapshotDescription_ : this.snapshotDescriptionBuilder_.build();
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
                if (other instanceof CreateSnapshotResponse) {
                    return this.mergeFrom((CreateSnapshotResponse)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(CreateSnapshotResponse other) {
                if (other == CreateSnapshotResponse.getDefaultInstance()) {
                    return this;
                }
                if (other.hasSnapshotDescription()) {
                    this.mergeSnapshotDescription(other.getSnapshotDescription());
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
                                input.readMessage(this.getSnapshotDescriptionFieldBuilder().getBuilder(), extensionRegistry);
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
            public boolean hasSnapshotDescription() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public SnapshotDescription getSnapshotDescription() {
                if (this.snapshotDescriptionBuilder_ == null) {
                    return this.snapshotDescription_ == null ? SnapshotDescription.getDefaultInstance() : this.snapshotDescription_;
                }
                return this.snapshotDescriptionBuilder_.getMessage();
            }

            public Builder setSnapshotDescription(SnapshotDescription value) {
                if (this.snapshotDescriptionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.snapshotDescription_ = value;
                } else {
                    this.snapshotDescriptionBuilder_.setMessage(value);
                }
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder setSnapshotDescription(SnapshotDescription.Builder builderForValue) {
                if (this.snapshotDescriptionBuilder_ == null) {
                    this.snapshotDescription_ = builderForValue.build();
                } else {
                    this.snapshotDescriptionBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder mergeSnapshotDescription(SnapshotDescription value) {
                if (this.snapshotDescriptionBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 0 && this.snapshotDescription_ != null && this.snapshotDescription_ != SnapshotDescription.getDefaultInstance()) {
                        this.getSnapshotDescriptionBuilder().mergeFrom(value);
                    } else {
                        this.snapshotDescription_ = value;
                    }
                } else {
                    this.snapshotDescriptionBuilder_.mergeFrom(value);
                }
                if (this.snapshotDescription_ != null) {
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                return this;
            }

            public Builder clearSnapshotDescription() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.snapshotDescription_ = null;
                if (this.snapshotDescriptionBuilder_ != null) {
                    this.snapshotDescriptionBuilder_.dispose();
                    this.snapshotDescriptionBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public SnapshotDescription.Builder getSnapshotDescriptionBuilder() {
                this.bitField0_ |= 1;
                this.onChanged();
                return this.getSnapshotDescriptionFieldBuilder().getBuilder();
            }

            @Override
            public SnapshotDescriptionOrBuilder getSnapshotDescriptionOrBuilder() {
                if (this.snapshotDescriptionBuilder_ != null) {
                    return this.snapshotDescriptionBuilder_.getMessageOrBuilder();
                }
                return this.snapshotDescription_ == null ? SnapshotDescription.getDefaultInstance() : this.snapshotDescription_;
            }

            private SingleFieldBuilderV3<SnapshotDescription, SnapshotDescription.Builder, SnapshotDescriptionOrBuilder> getSnapshotDescriptionFieldBuilder() {
                if (this.snapshotDescriptionBuilder_ == null) {
                    this.snapshotDescriptionBuilder_ = new SingleFieldBuilderV3(this.getSnapshotDescription(), this.getParentForChildren(), this.isClean());
                    this.snapshotDescription_ = null;
                }
                return this.snapshotDescriptionBuilder_;
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

    public static interface CreateSnapshotResponseOrBuilder
    extends MessageOrBuilder {
        public boolean hasSnapshotDescription();

        public SnapshotDescription getSnapshotDescription();

        public SnapshotDescriptionOrBuilder getSnapshotDescriptionOrBuilder();

        public double getTime();
    }

    public static final class SnapshotDescription
    extends GeneratedMessageV3
    implements SnapshotDescriptionOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int NAME_FIELD_NUMBER = 1;
        private volatile Object name_ = "";
        public static final int CREATION_TIME_FIELD_NUMBER = 2;
        private Timestamp creationTime_;
        public static final int SIZE_FIELD_NUMBER = 3;
        private long size_ = 0L;
        public static final int CHECKSUM_FIELD_NUMBER = 4;
        private volatile Object checksum_ = "";
        private byte memoizedIsInitialized = (byte)-1;
        private static final SnapshotDescription DEFAULT_INSTANCE = new SnapshotDescription();
        private static final Parser<SnapshotDescription> PARSER = new AbstractParser<SnapshotDescription>(){

            @Override
            public SnapshotDescription parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = SnapshotDescription.newBuilder();
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

        private SnapshotDescription(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private SnapshotDescription() {
            this.name_ = "";
            this.checksum_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new SnapshotDescription();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_SnapshotDescription_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_SnapshotDescription_fieldAccessorTable.ensureFieldAccessorsInitialized(SnapshotDescription.class, Builder.class);
        }

        @Override
        public String getName() {
            Object ref = this.name_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.name_ = s2;
            return s2;
        }

        @Override
        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.name_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasCreationTime() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public Timestamp getCreationTime() {
            return this.creationTime_ == null ? Timestamp.getDefaultInstance() : this.creationTime_;
        }

        @Override
        public TimestampOrBuilder getCreationTimeOrBuilder() {
            return this.creationTime_ == null ? Timestamp.getDefaultInstance() : this.creationTime_;
        }

        @Override
        public long getSize() {
            return this.size_;
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
            if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
                GeneratedMessageV3.writeString(output, 1, this.name_);
            }
            if ((this.bitField0_ & 1) != 0) {
                output.writeMessage(2, this.getCreationTime());
            }
            if (this.size_ != 0L) {
                output.writeInt64(3, this.size_);
            }
            if ((this.bitField0_ & 2) != 0) {
                GeneratedMessageV3.writeString(output, 4, this.checksum_);
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
            if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.name_);
            }
            if ((this.bitField0_ & 1) != 0) {
                size += CodedOutputStream.computeMessageSize(2, this.getCreationTime());
            }
            if (this.size_ != 0L) {
                size += CodedOutputStream.computeInt64Size(3, this.size_);
            }
            if ((this.bitField0_ & 2) != 0) {
                size += GeneratedMessageV3.computeStringSize(4, this.checksum_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SnapshotDescription)) {
                return super.equals(obj);
            }
            SnapshotDescription other = (SnapshotDescription)obj;
            if (!this.getName().equals(other.getName())) {
                return false;
            }
            if (this.hasCreationTime() != other.hasCreationTime()) {
                return false;
            }
            if (this.hasCreationTime() && !this.getCreationTime().equals(other.getCreationTime())) {
                return false;
            }
            if (this.getSize() != other.getSize()) {
                return false;
            }
            if (this.hasChecksum() != other.hasChecksum()) {
                return false;
            }
            if (this.hasChecksum() && !this.getChecksum().equals(other.getChecksum())) {
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
            hash = 19 * hash + SnapshotDescription.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getName().hashCode();
            if (this.hasCreationTime()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getCreationTime().hashCode();
            }
            hash = 37 * hash + 3;
            hash = 53 * hash + Internal.hashLong(this.getSize());
            if (this.hasChecksum()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getChecksum().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static SnapshotDescription parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SnapshotDescription parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SnapshotDescription parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SnapshotDescription parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SnapshotDescription parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SnapshotDescription parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SnapshotDescription parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SnapshotDescription parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SnapshotDescription parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SnapshotDescription parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SnapshotDescription parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SnapshotDescription parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return SnapshotDescription.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SnapshotDescription prototype) {
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

        public static SnapshotDescription getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SnapshotDescription> parser() {
            return PARSER;
        }

        public Parser<SnapshotDescription> getParserForType() {
            return PARSER;
        }

        @Override
        public SnapshotDescription getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements SnapshotDescriptionOrBuilder {
            private int bitField0_;
            private Object name_ = "";
            private Timestamp creationTime_;
            private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> creationTimeBuilder_;
            private long size_;
            private Object checksum_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_SnapshotDescription_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_SnapshotDescription_fieldAccessorTable.ensureFieldAccessorsInitialized(SnapshotDescription.class, Builder.class);
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
                    this.getCreationTimeFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                this.name_ = "";
                this.creationTime_ = null;
                if (this.creationTimeBuilder_ != null) {
                    this.creationTimeBuilder_.dispose();
                    this.creationTimeBuilder_ = null;
                }
                this.size_ = 0L;
                this.checksum_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_SnapshotDescription_descriptor;
            }

            @Override
            public SnapshotDescription getDefaultInstanceForType() {
                return SnapshotDescription.getDefaultInstance();
            }

            @Override
            public SnapshotDescription build() {
                SnapshotDescription result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public SnapshotDescription buildPartial() {
                SnapshotDescription result = new SnapshotDescription(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(SnapshotDescription result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.name_ = this.name_;
                }
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 2) != 0) {
                    result.creationTime_ = this.creationTimeBuilder_ == null ? this.creationTime_ : this.creationTimeBuilder_.build();
                    to_bitField0_ |= 1;
                }
                if ((from_bitField0_ & 4) != 0) {
                    result.size_ = this.size_;
                }
                if ((from_bitField0_ & 8) != 0) {
                    result.checksum_ = this.checksum_;
                    to_bitField0_ |= 2;
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
                if (other instanceof SnapshotDescription) {
                    return this.mergeFrom((SnapshotDescription)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SnapshotDescription other) {
                if (other == SnapshotDescription.getDefaultInstance()) {
                    return this;
                }
                if (!other.getName().isEmpty()) {
                    this.name_ = other.name_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (other.hasCreationTime()) {
                    this.mergeCreationTime(other.getCreationTime());
                }
                if (other.getSize() != 0L) {
                    this.setSize(other.getSize());
                }
                if (other.hasChecksum()) {
                    this.checksum_ = other.checksum_;
                    this.bitField0_ |= 8;
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
                    block12: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block12;
                            }
                            case 10: {
                                this.name_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block12;
                            }
                            case 18: {
                                input.readMessage(this.getCreationTimeFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 2;
                                continue block12;
                            }
                            case 24: {
                                this.size_ = input.readInt64();
                                this.bitField0_ |= 4;
                                continue block12;
                            }
                            case 34: {
                                this.checksum_ = input.readStringRequireUtf8();
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
            public String getName() {
                Object ref = this.name_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.name_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getNameBytes() {
                Object ref = this.name_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.name_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.name_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearName() {
                this.name_ = SnapshotDescription.getDefaultInstance().getName();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                SnapshotDescription.checkByteStringIsUtf8(value);
                this.name_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasCreationTime() {
                return (this.bitField0_ & 2) != 0;
            }

            @Override
            public Timestamp getCreationTime() {
                if (this.creationTimeBuilder_ == null) {
                    return this.creationTime_ == null ? Timestamp.getDefaultInstance() : this.creationTime_;
                }
                return this.creationTimeBuilder_.getMessage();
            }

            public Builder setCreationTime(Timestamp value) {
                if (this.creationTimeBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.creationTime_ = value;
                } else {
                    this.creationTimeBuilder_.setMessage(value);
                }
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder setCreationTime(Timestamp.Builder builderForValue) {
                if (this.creationTimeBuilder_ == null) {
                    this.creationTime_ = builderForValue.build();
                } else {
                    this.creationTimeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder mergeCreationTime(Timestamp value) {
                if (this.creationTimeBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 0 && this.creationTime_ != null && this.creationTime_ != Timestamp.getDefaultInstance()) {
                        this.getCreationTimeBuilder().mergeFrom(value);
                    } else {
                        this.creationTime_ = value;
                    }
                } else {
                    this.creationTimeBuilder_.mergeFrom(value);
                }
                if (this.creationTime_ != null) {
                    this.bitField0_ |= 2;
                    this.onChanged();
                }
                return this;
            }

            public Builder clearCreationTime() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.creationTime_ = null;
                if (this.creationTimeBuilder_ != null) {
                    this.creationTimeBuilder_.dispose();
                    this.creationTimeBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Timestamp.Builder getCreationTimeBuilder() {
                this.bitField0_ |= 2;
                this.onChanged();
                return this.getCreationTimeFieldBuilder().getBuilder();
            }

            @Override
            public TimestampOrBuilder getCreationTimeOrBuilder() {
                if (this.creationTimeBuilder_ != null) {
                    return this.creationTimeBuilder_.getMessageOrBuilder();
                }
                return this.creationTime_ == null ? Timestamp.getDefaultInstance() : this.creationTime_;
            }

            private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> getCreationTimeFieldBuilder() {
                if (this.creationTimeBuilder_ == null) {
                    this.creationTimeBuilder_ = new SingleFieldBuilderV3(this.getCreationTime(), this.getParentForChildren(), this.isClean());
                    this.creationTime_ = null;
                }
                return this.creationTimeBuilder_;
            }

            @Override
            public long getSize() {
                return this.size_;
            }

            public Builder setSize(long value) {
                this.size_ = value;
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder clearSize() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.size_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasChecksum() {
                return (this.bitField0_ & 8) != 0;
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
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder clearChecksum() {
                this.checksum_ = SnapshotDescription.getDefaultInstance().getChecksum();
                this.bitField0_ &= 0xFFFFFFF7;
                this.onChanged();
                return this;
            }

            public Builder setChecksumBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                SnapshotDescription.checkByteStringIsUtf8(value);
                this.checksum_ = value;
                this.bitField0_ |= 8;
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

    public static interface SnapshotDescriptionOrBuilder
    extends MessageOrBuilder {
        public String getName();

        public ByteString getNameBytes();

        public boolean hasCreationTime();

        public Timestamp getCreationTime();

        public TimestampOrBuilder getCreationTimeOrBuilder();

        public long getSize();

        public boolean hasChecksum();

        public String getChecksum();

        public ByteString getChecksumBytes();
    }

    public static final class DeleteSnapshotRequest
    extends GeneratedMessageV3
    implements DeleteSnapshotRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int COLLECTION_NAME_FIELD_NUMBER = 1;
        private volatile Object collectionName_ = "";
        public static final int SNAPSHOT_NAME_FIELD_NUMBER = 2;
        private volatile Object snapshotName_ = "";
        private byte memoizedIsInitialized = (byte)-1;
        private static final DeleteSnapshotRequest DEFAULT_INSTANCE = new DeleteSnapshotRequest();
        private static final Parser<DeleteSnapshotRequest> PARSER = new AbstractParser<DeleteSnapshotRequest>(){

            @Override
            public DeleteSnapshotRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = DeleteSnapshotRequest.newBuilder();
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

        private DeleteSnapshotRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private DeleteSnapshotRequest() {
            this.collectionName_ = "";
            this.snapshotName_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new DeleteSnapshotRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_DeleteSnapshotRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_DeleteSnapshotRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(DeleteSnapshotRequest.class, Builder.class);
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
            if (!GeneratedMessageV3.isStringEmpty(this.snapshotName_)) {
                GeneratedMessageV3.writeString(output, 2, this.snapshotName_);
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
            if (!GeneratedMessageV3.isStringEmpty(this.snapshotName_)) {
                size += GeneratedMessageV3.computeStringSize(2, this.snapshotName_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DeleteSnapshotRequest)) {
                return super.equals(obj);
            }
            DeleteSnapshotRequest other = (DeleteSnapshotRequest)obj;
            if (!this.getCollectionName().equals(other.getCollectionName())) {
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
            hash = 19 * hash + DeleteSnapshotRequest.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getCollectionName().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getSnapshotName().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static DeleteSnapshotRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeleteSnapshotRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeleteSnapshotRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeleteSnapshotRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeleteSnapshotRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeleteSnapshotRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeleteSnapshotRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeleteSnapshotRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeleteSnapshotRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DeleteSnapshotRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeleteSnapshotRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeleteSnapshotRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return DeleteSnapshotRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DeleteSnapshotRequest prototype) {
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

        public static DeleteSnapshotRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeleteSnapshotRequest> parser() {
            return PARSER;
        }

        public Parser<DeleteSnapshotRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public DeleteSnapshotRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements DeleteSnapshotRequestOrBuilder {
            private int bitField0_;
            private Object collectionName_ = "";
            private Object snapshotName_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_DeleteSnapshotRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_DeleteSnapshotRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(DeleteSnapshotRequest.class, Builder.class);
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
                this.snapshotName_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_DeleteSnapshotRequest_descriptor;
            }

            @Override
            public DeleteSnapshotRequest getDefaultInstanceForType() {
                return DeleteSnapshotRequest.getDefaultInstance();
            }

            @Override
            public DeleteSnapshotRequest build() {
                DeleteSnapshotRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public DeleteSnapshotRequest buildPartial() {
                DeleteSnapshotRequest result = new DeleteSnapshotRequest(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(DeleteSnapshotRequest result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.collectionName_ = this.collectionName_;
                }
                if ((from_bitField0_ & 2) != 0) {
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
                if (other instanceof DeleteSnapshotRequest) {
                    return this.mergeFrom((DeleteSnapshotRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DeleteSnapshotRequest other) {
                if (other == DeleteSnapshotRequest.getDefaultInstance()) {
                    return this;
                }
                if (!other.getCollectionName().isEmpty()) {
                    this.collectionName_ = other.collectionName_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (!other.getSnapshotName().isEmpty()) {
                    this.snapshotName_ = other.snapshotName_;
                    this.bitField0_ |= 2;
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
                            case 18: {
                                this.snapshotName_ = input.readStringRequireUtf8();
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
                this.collectionName_ = DeleteSnapshotRequest.getDefaultInstance().getCollectionName();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setCollectionNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                DeleteSnapshotRequest.checkByteStringIsUtf8(value);
                this.collectionName_ = value;
                this.bitField0_ |= 1;
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
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearSnapshotName() {
                this.snapshotName_ = DeleteSnapshotRequest.getDefaultInstance().getSnapshotName();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            public Builder setSnapshotNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                DeleteSnapshotRequest.checkByteStringIsUtf8(value);
                this.snapshotName_ = value;
                this.bitField0_ |= 2;
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

    public static interface DeleteSnapshotRequestOrBuilder
    extends MessageOrBuilder {
        public String getCollectionName();

        public ByteString getCollectionNameBytes();

        public String getSnapshotName();

        public ByteString getSnapshotNameBytes();
    }

    public static final class ListSnapshotsRequest
    extends GeneratedMessageV3
    implements ListSnapshotsRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int COLLECTION_NAME_FIELD_NUMBER = 1;
        private volatile Object collectionName_ = "";
        private byte memoizedIsInitialized = (byte)-1;
        private static final ListSnapshotsRequest DEFAULT_INSTANCE = new ListSnapshotsRequest();
        private static final Parser<ListSnapshotsRequest> PARSER = new AbstractParser<ListSnapshotsRequest>(){

            @Override
            public ListSnapshotsRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = ListSnapshotsRequest.newBuilder();
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

        private ListSnapshotsRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ListSnapshotsRequest() {
            this.collectionName_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new ListSnapshotsRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_ListSnapshotsRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_ListSnapshotsRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(ListSnapshotsRequest.class, Builder.class);
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
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ListSnapshotsRequest)) {
                return super.equals(obj);
            }
            ListSnapshotsRequest other = (ListSnapshotsRequest)obj;
            if (!this.getCollectionName().equals(other.getCollectionName())) {
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
            hash = 19 * hash + ListSnapshotsRequest.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getCollectionName().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static ListSnapshotsRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListSnapshotsRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListSnapshotsRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListSnapshotsRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListSnapshotsRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListSnapshotsRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListSnapshotsRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ListSnapshotsRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ListSnapshotsRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ListSnapshotsRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ListSnapshotsRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ListSnapshotsRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ListSnapshotsRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ListSnapshotsRequest prototype) {
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

        public static ListSnapshotsRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ListSnapshotsRequest> parser() {
            return PARSER;
        }

        public Parser<ListSnapshotsRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public ListSnapshotsRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ListSnapshotsRequestOrBuilder {
            private int bitField0_;
            private Object collectionName_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_ListSnapshotsRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_ListSnapshotsRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(ListSnapshotsRequest.class, Builder.class);
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
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_ListSnapshotsRequest_descriptor;
            }

            @Override
            public ListSnapshotsRequest getDefaultInstanceForType() {
                return ListSnapshotsRequest.getDefaultInstance();
            }

            @Override
            public ListSnapshotsRequest build() {
                ListSnapshotsRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ListSnapshotsRequest buildPartial() {
                ListSnapshotsRequest result = new ListSnapshotsRequest(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(ListSnapshotsRequest result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.collectionName_ = this.collectionName_;
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
                if (other instanceof ListSnapshotsRequest) {
                    return this.mergeFrom((ListSnapshotsRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ListSnapshotsRequest other) {
                if (other == ListSnapshotsRequest.getDefaultInstance()) {
                    return this;
                }
                if (!other.getCollectionName().isEmpty()) {
                    this.collectionName_ = other.collectionName_;
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
                                this.collectionName_ = input.readStringRequireUtf8();
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
                this.collectionName_ = ListSnapshotsRequest.getDefaultInstance().getCollectionName();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setCollectionNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ListSnapshotsRequest.checkByteStringIsUtf8(value);
                this.collectionName_ = value;
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

    public static interface ListSnapshotsRequestOrBuilder
    extends MessageOrBuilder {
        public String getCollectionName();

        public ByteString getCollectionNameBytes();
    }

    public static final class CreateSnapshotRequest
    extends GeneratedMessageV3
    implements CreateSnapshotRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int COLLECTION_NAME_FIELD_NUMBER = 1;
        private volatile Object collectionName_ = "";
        private byte memoizedIsInitialized = (byte)-1;
        private static final CreateSnapshotRequest DEFAULT_INSTANCE = new CreateSnapshotRequest();
        private static final Parser<CreateSnapshotRequest> PARSER = new AbstractParser<CreateSnapshotRequest>(){

            @Override
            public CreateSnapshotRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = CreateSnapshotRequest.newBuilder();
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

        private CreateSnapshotRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private CreateSnapshotRequest() {
            this.collectionName_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new CreateSnapshotRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_CreateSnapshotRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_CreateSnapshotRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(CreateSnapshotRequest.class, Builder.class);
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
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CreateSnapshotRequest)) {
                return super.equals(obj);
            }
            CreateSnapshotRequest other = (CreateSnapshotRequest)obj;
            if (!this.getCollectionName().equals(other.getCollectionName())) {
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
            hash = 19 * hash + CreateSnapshotRequest.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getCollectionName().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static CreateSnapshotRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CreateSnapshotRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CreateSnapshotRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CreateSnapshotRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CreateSnapshotRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CreateSnapshotRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CreateSnapshotRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CreateSnapshotRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static CreateSnapshotRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static CreateSnapshotRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static CreateSnapshotRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CreateSnapshotRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return CreateSnapshotRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CreateSnapshotRequest prototype) {
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

        public static CreateSnapshotRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CreateSnapshotRequest> parser() {
            return PARSER;
        }

        public Parser<CreateSnapshotRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public CreateSnapshotRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements CreateSnapshotRequestOrBuilder {
            private int bitField0_;
            private Object collectionName_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_CreateSnapshotRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_CreateSnapshotRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(CreateSnapshotRequest.class, Builder.class);
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
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_CreateSnapshotRequest_descriptor;
            }

            @Override
            public CreateSnapshotRequest getDefaultInstanceForType() {
                return CreateSnapshotRequest.getDefaultInstance();
            }

            @Override
            public CreateSnapshotRequest build() {
                CreateSnapshotRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public CreateSnapshotRequest buildPartial() {
                CreateSnapshotRequest result = new CreateSnapshotRequest(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(CreateSnapshotRequest result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.collectionName_ = this.collectionName_;
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
                if (other instanceof CreateSnapshotRequest) {
                    return this.mergeFrom((CreateSnapshotRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(CreateSnapshotRequest other) {
                if (other == CreateSnapshotRequest.getDefaultInstance()) {
                    return this;
                }
                if (!other.getCollectionName().isEmpty()) {
                    this.collectionName_ = other.collectionName_;
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
                                this.collectionName_ = input.readStringRequireUtf8();
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
                this.collectionName_ = CreateSnapshotRequest.getDefaultInstance().getCollectionName();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setCollectionNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                CreateSnapshotRequest.checkByteStringIsUtf8(value);
                this.collectionName_ = value;
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

    public static interface CreateSnapshotRequestOrBuilder
    extends MessageOrBuilder {
        public String getCollectionName();

        public ByteString getCollectionNameBytes();
    }

    public static final class DeleteFullSnapshotRequest
    extends GeneratedMessageV3
    implements DeleteFullSnapshotRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int SNAPSHOT_NAME_FIELD_NUMBER = 1;
        private volatile Object snapshotName_ = "";
        private byte memoizedIsInitialized = (byte)-1;
        private static final DeleteFullSnapshotRequest DEFAULT_INSTANCE = new DeleteFullSnapshotRequest();
        private static final Parser<DeleteFullSnapshotRequest> PARSER = new AbstractParser<DeleteFullSnapshotRequest>(){

            @Override
            public DeleteFullSnapshotRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = DeleteFullSnapshotRequest.newBuilder();
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

        private DeleteFullSnapshotRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private DeleteFullSnapshotRequest() {
            this.snapshotName_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new DeleteFullSnapshotRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_DeleteFullSnapshotRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_DeleteFullSnapshotRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(DeleteFullSnapshotRequest.class, Builder.class);
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
            if (!GeneratedMessageV3.isStringEmpty(this.snapshotName_)) {
                GeneratedMessageV3.writeString(output, 1, this.snapshotName_);
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
            if (!GeneratedMessageV3.isStringEmpty(this.snapshotName_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.snapshotName_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DeleteFullSnapshotRequest)) {
                return super.equals(obj);
            }
            DeleteFullSnapshotRequest other = (DeleteFullSnapshotRequest)obj;
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
            hash = 19 * hash + DeleteFullSnapshotRequest.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getSnapshotName().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static DeleteFullSnapshotRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeleteFullSnapshotRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeleteFullSnapshotRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeleteFullSnapshotRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeleteFullSnapshotRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeleteFullSnapshotRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeleteFullSnapshotRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeleteFullSnapshotRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeleteFullSnapshotRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DeleteFullSnapshotRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeleteFullSnapshotRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeleteFullSnapshotRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return DeleteFullSnapshotRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DeleteFullSnapshotRequest prototype) {
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

        public static DeleteFullSnapshotRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeleteFullSnapshotRequest> parser() {
            return PARSER;
        }

        public Parser<DeleteFullSnapshotRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public DeleteFullSnapshotRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements DeleteFullSnapshotRequestOrBuilder {
            private int bitField0_;
            private Object snapshotName_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_DeleteFullSnapshotRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_DeleteFullSnapshotRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(DeleteFullSnapshotRequest.class, Builder.class);
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
                this.snapshotName_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_DeleteFullSnapshotRequest_descriptor;
            }

            @Override
            public DeleteFullSnapshotRequest getDefaultInstanceForType() {
                return DeleteFullSnapshotRequest.getDefaultInstance();
            }

            @Override
            public DeleteFullSnapshotRequest build() {
                DeleteFullSnapshotRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public DeleteFullSnapshotRequest buildPartial() {
                DeleteFullSnapshotRequest result = new DeleteFullSnapshotRequest(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(DeleteFullSnapshotRequest result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
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
                if (other instanceof DeleteFullSnapshotRequest) {
                    return this.mergeFrom((DeleteFullSnapshotRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DeleteFullSnapshotRequest other) {
                if (other == DeleteFullSnapshotRequest.getDefaultInstance()) {
                    return this;
                }
                if (!other.getSnapshotName().isEmpty()) {
                    this.snapshotName_ = other.snapshotName_;
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
                                this.snapshotName_ = input.readStringRequireUtf8();
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
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearSnapshotName() {
                this.snapshotName_ = DeleteFullSnapshotRequest.getDefaultInstance().getSnapshotName();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setSnapshotNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                DeleteFullSnapshotRequest.checkByteStringIsUtf8(value);
                this.snapshotName_ = value;
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

    public static interface DeleteFullSnapshotRequestOrBuilder
    extends MessageOrBuilder {
        public String getSnapshotName();

        public ByteString getSnapshotNameBytes();
    }

    public static final class ListFullSnapshotsRequest
    extends GeneratedMessageV3
    implements ListFullSnapshotsRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ListFullSnapshotsRequest DEFAULT_INSTANCE = new ListFullSnapshotsRequest();
        private static final Parser<ListFullSnapshotsRequest> PARSER = new AbstractParser<ListFullSnapshotsRequest>(){

            @Override
            public ListFullSnapshotsRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = ListFullSnapshotsRequest.newBuilder();
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

        private ListFullSnapshotsRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ListFullSnapshotsRequest() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new ListFullSnapshotsRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_ListFullSnapshotsRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_ListFullSnapshotsRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(ListFullSnapshotsRequest.class, Builder.class);
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
            if (!(obj instanceof ListFullSnapshotsRequest)) {
                return super.equals(obj);
            }
            ListFullSnapshotsRequest other = (ListFullSnapshotsRequest)obj;
            return this.getUnknownFields().equals(other.getUnknownFields());
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ListFullSnapshotsRequest.getDescriptor().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static ListFullSnapshotsRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListFullSnapshotsRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListFullSnapshotsRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListFullSnapshotsRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListFullSnapshotsRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListFullSnapshotsRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListFullSnapshotsRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ListFullSnapshotsRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ListFullSnapshotsRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ListFullSnapshotsRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ListFullSnapshotsRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ListFullSnapshotsRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ListFullSnapshotsRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ListFullSnapshotsRequest prototype) {
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

        public static ListFullSnapshotsRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ListFullSnapshotsRequest> parser() {
            return PARSER;
        }

        public Parser<ListFullSnapshotsRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public ListFullSnapshotsRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ListFullSnapshotsRequestOrBuilder {
            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_ListFullSnapshotsRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_ListFullSnapshotsRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(ListFullSnapshotsRequest.class, Builder.class);
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
                return internal_static_qdrant_ListFullSnapshotsRequest_descriptor;
            }

            @Override
            public ListFullSnapshotsRequest getDefaultInstanceForType() {
                return ListFullSnapshotsRequest.getDefaultInstance();
            }

            @Override
            public ListFullSnapshotsRequest build() {
                ListFullSnapshotsRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ListFullSnapshotsRequest buildPartial() {
                ListFullSnapshotsRequest result = new ListFullSnapshotsRequest(this);
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
                if (other instanceof ListFullSnapshotsRequest) {
                    return this.mergeFrom((ListFullSnapshotsRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ListFullSnapshotsRequest other) {
                if (other == ListFullSnapshotsRequest.getDefaultInstance()) {
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

    public static interface ListFullSnapshotsRequestOrBuilder
    extends MessageOrBuilder {
    }

    public static final class CreateFullSnapshotRequest
    extends GeneratedMessageV3
    implements CreateFullSnapshotRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final CreateFullSnapshotRequest DEFAULT_INSTANCE = new CreateFullSnapshotRequest();
        private static final Parser<CreateFullSnapshotRequest> PARSER = new AbstractParser<CreateFullSnapshotRequest>(){

            @Override
            public CreateFullSnapshotRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = CreateFullSnapshotRequest.newBuilder();
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

        private CreateFullSnapshotRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private CreateFullSnapshotRequest() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new CreateFullSnapshotRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_CreateFullSnapshotRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_CreateFullSnapshotRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(CreateFullSnapshotRequest.class, Builder.class);
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
            if (!(obj instanceof CreateFullSnapshotRequest)) {
                return super.equals(obj);
            }
            CreateFullSnapshotRequest other = (CreateFullSnapshotRequest)obj;
            return this.getUnknownFields().equals(other.getUnknownFields());
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + CreateFullSnapshotRequest.getDescriptor().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static CreateFullSnapshotRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CreateFullSnapshotRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CreateFullSnapshotRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CreateFullSnapshotRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CreateFullSnapshotRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CreateFullSnapshotRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CreateFullSnapshotRequest parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CreateFullSnapshotRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static CreateFullSnapshotRequest parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static CreateFullSnapshotRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static CreateFullSnapshotRequest parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CreateFullSnapshotRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return CreateFullSnapshotRequest.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CreateFullSnapshotRequest prototype) {
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

        public static CreateFullSnapshotRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CreateFullSnapshotRequest> parser() {
            return PARSER;
        }

        public Parser<CreateFullSnapshotRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public CreateFullSnapshotRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements CreateFullSnapshotRequestOrBuilder {
            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_CreateFullSnapshotRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_CreateFullSnapshotRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(CreateFullSnapshotRequest.class, Builder.class);
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
                return internal_static_qdrant_CreateFullSnapshotRequest_descriptor;
            }

            @Override
            public CreateFullSnapshotRequest getDefaultInstanceForType() {
                return CreateFullSnapshotRequest.getDefaultInstance();
            }

            @Override
            public CreateFullSnapshotRequest build() {
                CreateFullSnapshotRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public CreateFullSnapshotRequest buildPartial() {
                CreateFullSnapshotRequest result = new CreateFullSnapshotRequest(this);
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
                if (other instanceof CreateFullSnapshotRequest) {
                    return this.mergeFrom((CreateFullSnapshotRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(CreateFullSnapshotRequest other) {
                if (other == CreateFullSnapshotRequest.getDefaultInstance()) {
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

    public static interface CreateFullSnapshotRequestOrBuilder
    extends MessageOrBuilder {
    }
}

