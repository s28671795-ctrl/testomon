/*
 * Decompiled with CFR 0.152.
 */
package grpc.health.v1;

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
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class HealthCheck {
    private static final Descriptors.Descriptor internal_static_grpc_health_v1_HealthCheckRequest_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_grpc_health_v1_HealthCheckRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_grpc_health_v1_HealthCheckResponse_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_grpc_health_v1_HealthCheckResponse_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private HealthCheck() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        HealthCheck.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0012health_check.proto\u0012\u000egrpc.health.v1\"%\n\u0012HealthCheckRequest\u0012\u000f\n\u0007service\u0018\u0001 \u0001(\t\"\u00a9\u0001\n\u0013HealthCheckResponse\u0012A\n\u0006status\u0018\u0001 \u0001(\u000e21.grpc.health.v1.HealthCheckResponse.ServingStatus\"O\n\rServingStatus\u0012\u000b\n\u0007UNKNOWN\u0010\u0000\u0012\u000b\n\u0007SERVING\u0010\u0001\u0012\u000f\n\u000bNOT_SERVING\u0010\u0002\u0012\u0013\n\u000fSERVICE_UNKNOWN\u0010\u00032Z\n\u0006Health\u0012P\n\u0005Check\u0012\".grpc.health.v1.HealthCheckRequest\u001a#.grpc.health.v1.HealthCheckResponseB\u0015\u00aa\u0002\u0012Qdrant.Client.Grpcb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_grpc_health_v1_HealthCheckRequest_descriptor = HealthCheck.getDescriptor().getMessageTypes().get(0);
        internal_static_grpc_health_v1_HealthCheckRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_grpc_health_v1_HealthCheckRequest_descriptor, new String[]{"Service"});
        internal_static_grpc_health_v1_HealthCheckResponse_descriptor = HealthCheck.getDescriptor().getMessageTypes().get(1);
        internal_static_grpc_health_v1_HealthCheckResponse_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_grpc_health_v1_HealthCheckResponse_descriptor, new String[]{"Status"});
    }

    public static final class HealthCheckResponse
    extends GeneratedMessageV3
    implements HealthCheckResponseOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int STATUS_FIELD_NUMBER = 1;
        private int status_ = 0;
        private byte memoizedIsInitialized = (byte)-1;
        private static final HealthCheckResponse DEFAULT_INSTANCE = new HealthCheckResponse();
        private static final Parser<HealthCheckResponse> PARSER = new AbstractParser<HealthCheckResponse>(){

            @Override
            public HealthCheckResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = HealthCheckResponse.newBuilder();
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

        private HealthCheckResponse(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private HealthCheckResponse() {
            this.status_ = 0;
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new HealthCheckResponse();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_grpc_health_v1_HealthCheckResponse_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_grpc_health_v1_HealthCheckResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(HealthCheckResponse.class, Builder.class);
        }

        @Override
        public int getStatusValue() {
            return this.status_;
        }

        @Override
        public ServingStatus getStatus() {
            ServingStatus result = ServingStatus.forNumber(this.status_);
            return result == null ? ServingStatus.UNRECOGNIZED : result;
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
            if (this.status_ != ServingStatus.UNKNOWN.getNumber()) {
                output.writeEnum(1, this.status_);
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
            if (this.status_ != ServingStatus.UNKNOWN.getNumber()) {
                size += CodedOutputStream.computeEnumSize(1, this.status_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof HealthCheckResponse)) {
                return super.equals(obj);
            }
            HealthCheckResponse other = (HealthCheckResponse)obj;
            if (this.status_ != other.status_) {
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
            hash = 19 * hash + HealthCheckResponse.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.status_;
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static HealthCheckResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static HealthCheckResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static HealthCheckResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static HealthCheckResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static HealthCheckResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static HealthCheckResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static HealthCheckResponse parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HealthCheckResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HealthCheckResponse parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HealthCheckResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HealthCheckResponse parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HealthCheckResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return HealthCheckResponse.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HealthCheckResponse prototype) {
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

        public static HealthCheckResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HealthCheckResponse> parser() {
            return PARSER;
        }

        public Parser<HealthCheckResponse> getParserForType() {
            return PARSER;
        }

        @Override
        public HealthCheckResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements HealthCheckResponseOrBuilder {
            private int bitField0_;
            private int status_ = 0;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_grpc_health_v1_HealthCheckResponse_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_grpc_health_v1_HealthCheckResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(HealthCheckResponse.class, Builder.class);
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
                this.status_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_grpc_health_v1_HealthCheckResponse_descriptor;
            }

            @Override
            public HealthCheckResponse getDefaultInstanceForType() {
                return HealthCheckResponse.getDefaultInstance();
            }

            @Override
            public HealthCheckResponse build() {
                HealthCheckResponse result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public HealthCheckResponse buildPartial() {
                HealthCheckResponse result = new HealthCheckResponse(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(HealthCheckResponse result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.status_ = this.status_;
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
                if (other instanceof HealthCheckResponse) {
                    return this.mergeFrom((HealthCheckResponse)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HealthCheckResponse other) {
                if (other == HealthCheckResponse.getDefaultInstance()) {
                    return this;
                }
                if (other.status_ != 0) {
                    this.setStatusValue(other.getStatusValue());
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
                                this.status_ = input.readEnum();
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
            public int getStatusValue() {
                return this.status_;
            }

            public Builder setStatusValue(int value) {
                this.status_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public ServingStatus getStatus() {
                ServingStatus result = ServingStatus.forNumber(this.status_);
                return result == null ? ServingStatus.UNRECOGNIZED : result;
            }

            public Builder setStatus(ServingStatus value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.status_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearStatus() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.status_ = 0;
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

        public static enum ServingStatus implements ProtocolMessageEnum
        {
            UNKNOWN(0),
            SERVING(1),
            NOT_SERVING(2),
            SERVICE_UNKNOWN(3),
            UNRECOGNIZED(-1);

            public static final int UNKNOWN_VALUE = 0;
            public static final int SERVING_VALUE = 1;
            public static final int NOT_SERVING_VALUE = 2;
            public static final int SERVICE_UNKNOWN_VALUE = 3;
            private static final Internal.EnumLiteMap<ServingStatus> internalValueMap;
            private static final ServingStatus[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static ServingStatus valueOf(int value) {
                return ServingStatus.forNumber(value);
            }

            public static ServingStatus forNumber(int value) {
                switch (value) {
                    case 0: {
                        return UNKNOWN;
                    }
                    case 1: {
                        return SERVING;
                    }
                    case 2: {
                        return NOT_SERVING;
                    }
                    case 3: {
                        return SERVICE_UNKNOWN;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<ServingStatus> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
                }
                return ServingStatus.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return ServingStatus.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return HealthCheckResponse.getDescriptor().getEnumTypes().get(0);
            }

            public static ServingStatus valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != ServingStatus.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private ServingStatus(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<ServingStatus>(){

                    @Override
                    public ServingStatus findValueByNumber(int number) {
                        return ServingStatus.forNumber(number);
                    }
                };
                VALUES = ServingStatus.values();
            }
        }
    }

    public static interface HealthCheckResponseOrBuilder
    extends MessageOrBuilder {
        public int getStatusValue();

        public HealthCheckResponse.ServingStatus getStatus();
    }

    public static final class HealthCheckRequest
    extends GeneratedMessageV3
    implements HealthCheckRequestOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int SERVICE_FIELD_NUMBER = 1;
        private volatile Object service_ = "";
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
            this.service_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new HealthCheckRequest();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_grpc_health_v1_HealthCheckRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_grpc_health_v1_HealthCheckRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(HealthCheckRequest.class, Builder.class);
        }

        @Override
        public String getService() {
            Object ref = this.service_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.service_ = s2;
            return s2;
        }

        @Override
        public ByteString getServiceBytes() {
            Object ref = this.service_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.service_ = b;
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
            if (!GeneratedMessageV3.isStringEmpty(this.service_)) {
                GeneratedMessageV3.writeString(output, 1, this.service_);
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
            if (!GeneratedMessageV3.isStringEmpty(this.service_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.service_);
            }
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
            if (!this.getService().equals(other.getService())) {
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
            hash = 19 * hash + HealthCheckRequest.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getService().hashCode();
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
            private int bitField0_;
            private Object service_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_grpc_health_v1_HealthCheckRequest_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_grpc_health_v1_HealthCheckRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(HealthCheckRequest.class, Builder.class);
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
                this.service_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_grpc_health_v1_HealthCheckRequest_descriptor;
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
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(HealthCheckRequest result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.service_ = this.service_;
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
                if (!other.getService().isEmpty()) {
                    this.service_ = other.service_;
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
                                this.service_ = input.readStringRequireUtf8();
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
            public String getService() {
                Object ref = this.service_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.service_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getServiceBytes() {
                Object ref = this.service_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.service_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setService(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.service_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearService() {
                this.service_ = HealthCheckRequest.getDefaultInstance().getService();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setServiceBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                HealthCheckRequest.checkByteStringIsUtf8(value);
                this.service_ = value;
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

    public static interface HealthCheckRequestOrBuilder
    extends MessageOrBuilder {
        public String getService();

        public ByteString getServiceBytes();
    }
}

