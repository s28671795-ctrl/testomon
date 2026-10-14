/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.rpc.ErrorDetailsProto;
import com.google.rpc.RequestInfoOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class RequestInfo
extends GeneratedMessageV3
implements RequestInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int REQUEST_ID_FIELD_NUMBER = 1;
    private volatile Object requestId_ = "";
    public static final int SERVING_DATA_FIELD_NUMBER = 2;
    private volatile Object servingData_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final RequestInfo DEFAULT_INSTANCE = new RequestInfo();
    private static final Parser<RequestInfo> PARSER = new AbstractParser<RequestInfo>(){

        @Override
        public RequestInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = RequestInfo.newBuilder();
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

    private RequestInfo(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private RequestInfo() {
        this.requestId_ = "";
        this.servingData_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new RequestInfo();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ErrorDetailsProto.internal_static_google_rpc_RequestInfo_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ErrorDetailsProto.internal_static_google_rpc_RequestInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(RequestInfo.class, Builder.class);
    }

    @Override
    public String getRequestId() {
        Object ref = this.requestId_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.requestId_ = s2;
        return s2;
    }

    @Override
    public ByteString getRequestIdBytes() {
        Object ref = this.requestId_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.requestId_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getServingData() {
        Object ref = this.servingData_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.servingData_ = s2;
        return s2;
    }

    @Override
    public ByteString getServingDataBytes() {
        Object ref = this.servingData_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.servingData_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.requestId_)) {
            GeneratedMessageV3.writeString(output, 1, this.requestId_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.servingData_)) {
            GeneratedMessageV3.writeString(output, 2, this.servingData_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.requestId_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.requestId_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.servingData_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.servingData_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RequestInfo)) {
            return super.equals(obj);
        }
        RequestInfo other = (RequestInfo)obj;
        if (!this.getRequestId().equals(other.getRequestId())) {
            return false;
        }
        if (!this.getServingData().equals(other.getServingData())) {
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
        hash = 19 * hash + RequestInfo.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getRequestId().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getServingData().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static RequestInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RequestInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RequestInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RequestInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RequestInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RequestInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RequestInfo parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static RequestInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static RequestInfo parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static RequestInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static RequestInfo parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static RequestInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return RequestInfo.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(RequestInfo prototype) {
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

    public static RequestInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<RequestInfo> parser() {
        return PARSER;
    }

    public Parser<RequestInfo> getParserForType() {
        return PARSER;
    }

    @Override
    public RequestInfo getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements RequestInfoOrBuilder {
        private int bitField0_;
        private Object requestId_ = "";
        private Object servingData_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return ErrorDetailsProto.internal_static_google_rpc_RequestInfo_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ErrorDetailsProto.internal_static_google_rpc_RequestInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(RequestInfo.class, Builder.class);
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
            this.requestId_ = "";
            this.servingData_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ErrorDetailsProto.internal_static_google_rpc_RequestInfo_descriptor;
        }

        @Override
        public RequestInfo getDefaultInstanceForType() {
            return RequestInfo.getDefaultInstance();
        }

        @Override
        public RequestInfo build() {
            RequestInfo result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public RequestInfo buildPartial() {
            RequestInfo result = new RequestInfo(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(RequestInfo result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.requestId_ = this.requestId_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.servingData_ = this.servingData_;
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
            if (other instanceof RequestInfo) {
                return this.mergeFrom((RequestInfo)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(RequestInfo other) {
            if (other == RequestInfo.getDefaultInstance()) {
                return this;
            }
            if (!other.getRequestId().isEmpty()) {
                this.requestId_ = other.requestId_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getServingData().isEmpty()) {
                this.servingData_ = other.servingData_;
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
                            this.requestId_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            this.servingData_ = input.readStringRequireUtf8();
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
        public String getRequestId() {
            Object ref = this.requestId_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.requestId_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getRequestIdBytes() {
            Object ref = this.requestId_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.requestId_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setRequestId(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.requestId_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearRequestId() {
            this.requestId_ = RequestInfo.getDefaultInstance().getRequestId();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setRequestIdBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            RequestInfo.checkByteStringIsUtf8(value);
            this.requestId_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getServingData() {
            Object ref = this.servingData_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.servingData_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getServingDataBytes() {
            Object ref = this.servingData_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.servingData_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setServingData(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.servingData_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearServingData() {
            this.servingData_ = RequestInfo.getDefaultInstance().getServingData();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setServingDataBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            RequestInfo.checkByteStringIsUtf8(value);
            this.servingData_ = value;
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

