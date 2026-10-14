/*
 * Decompiled with CFR 0.152.
 */
package com.google.longrunning;

import com.google.longrunning.OperationsProto;
import com.google.longrunning.WaitOperationRequestOrBuilder;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Duration;
import com.google.protobuf.DurationOrBuilder;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class WaitOperationRequest
extends GeneratedMessageV3
implements WaitOperationRequestOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 1;
    private volatile Object name_ = "";
    public static final int TIMEOUT_FIELD_NUMBER = 2;
    private Duration timeout_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final WaitOperationRequest DEFAULT_INSTANCE = new WaitOperationRequest();
    private static final Parser<WaitOperationRequest> PARSER = new AbstractParser<WaitOperationRequest>(){

        @Override
        public WaitOperationRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = WaitOperationRequest.newBuilder();
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

    private WaitOperationRequest(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private WaitOperationRequest() {
        this.name_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new WaitOperationRequest();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return OperationsProto.internal_static_google_longrunning_WaitOperationRequest_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return OperationsProto.internal_static_google_longrunning_WaitOperationRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(WaitOperationRequest.class, Builder.class);
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
    public boolean hasTimeout() {
        return this.timeout_ != null;
    }

    @Override
    public Duration getTimeout() {
        return this.timeout_ == null ? Duration.getDefaultInstance() : this.timeout_;
    }

    @Override
    public DurationOrBuilder getTimeoutOrBuilder() {
        return this.timeout_ == null ? Duration.getDefaultInstance() : this.timeout_;
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
        if (this.timeout_ != null) {
            output.writeMessage(2, this.getTimeout());
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
        if (this.timeout_ != null) {
            size += CodedOutputStream.computeMessageSize(2, this.getTimeout());
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WaitOperationRequest)) {
            return super.equals(obj);
        }
        WaitOperationRequest other = (WaitOperationRequest)obj;
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (this.hasTimeout() != other.hasTimeout()) {
            return false;
        }
        if (this.hasTimeout() && !this.getTimeout().equals(other.getTimeout())) {
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
        hash = 19 * hash + WaitOperationRequest.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getName().hashCode();
        if (this.hasTimeout()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getTimeout().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static WaitOperationRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static WaitOperationRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static WaitOperationRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static WaitOperationRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static WaitOperationRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static WaitOperationRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static WaitOperationRequest parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static WaitOperationRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static WaitOperationRequest parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static WaitOperationRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static WaitOperationRequest parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static WaitOperationRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return WaitOperationRequest.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(WaitOperationRequest prototype) {
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

    public static WaitOperationRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<WaitOperationRequest> parser() {
        return PARSER;
    }

    public Parser<WaitOperationRequest> getParserForType() {
        return PARSER;
    }

    @Override
    public WaitOperationRequest getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements WaitOperationRequestOrBuilder {
        private int bitField0_;
        private Object name_ = "";
        private Duration timeout_;
        private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> timeoutBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return OperationsProto.internal_static_google_longrunning_WaitOperationRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return OperationsProto.internal_static_google_longrunning_WaitOperationRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(WaitOperationRequest.class, Builder.class);
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
            this.name_ = "";
            this.timeout_ = null;
            if (this.timeoutBuilder_ != null) {
                this.timeoutBuilder_.dispose();
                this.timeoutBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return OperationsProto.internal_static_google_longrunning_WaitOperationRequest_descriptor;
        }

        @Override
        public WaitOperationRequest getDefaultInstanceForType() {
            return WaitOperationRequest.getDefaultInstance();
        }

        @Override
        public WaitOperationRequest build() {
            WaitOperationRequest result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public WaitOperationRequest buildPartial() {
            WaitOperationRequest result = new WaitOperationRequest(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(WaitOperationRequest result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.name_ = this.name_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.timeout_ = this.timeoutBuilder_ == null ? this.timeout_ : this.timeoutBuilder_.build();
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
            if (other instanceof WaitOperationRequest) {
                return this.mergeFrom((WaitOperationRequest)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(WaitOperationRequest other) {
            if (other == WaitOperationRequest.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (other.hasTimeout()) {
                this.mergeTimeout(other.getTimeout());
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
                            this.name_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            input.readMessage(this.getTimeoutFieldBuilder().getBuilder(), extensionRegistry);
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
            this.name_ = WaitOperationRequest.getDefaultInstance().getName();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            WaitOperationRequest.checkByteStringIsUtf8(value);
            this.name_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasTimeout() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override
        public Duration getTimeout() {
            if (this.timeoutBuilder_ == null) {
                return this.timeout_ == null ? Duration.getDefaultInstance() : this.timeout_;
            }
            return this.timeoutBuilder_.getMessage();
        }

        public Builder setTimeout(Duration value) {
            if (this.timeoutBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.timeout_ = value;
            } else {
                this.timeoutBuilder_.setMessage(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder setTimeout(Duration.Builder builderForValue) {
            if (this.timeoutBuilder_ == null) {
                this.timeout_ = builderForValue.build();
            } else {
                this.timeoutBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder mergeTimeout(Duration value) {
            if (this.timeoutBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0 && this.timeout_ != null && this.timeout_ != Duration.getDefaultInstance()) {
                    this.getTimeoutBuilder().mergeFrom(value);
                } else {
                    this.timeout_ = value;
                }
            } else {
                this.timeoutBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearTimeout() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.timeout_ = null;
            if (this.timeoutBuilder_ != null) {
                this.timeoutBuilder_.dispose();
                this.timeoutBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Duration.Builder getTimeoutBuilder() {
            this.bitField0_ |= 2;
            this.onChanged();
            return this.getTimeoutFieldBuilder().getBuilder();
        }

        @Override
        public DurationOrBuilder getTimeoutOrBuilder() {
            if (this.timeoutBuilder_ != null) {
                return this.timeoutBuilder_.getMessageOrBuilder();
            }
            return this.timeout_ == null ? Duration.getDefaultInstance() : this.timeout_;
        }

        private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> getTimeoutFieldBuilder() {
            if (this.timeoutBuilder_ == null) {
                this.timeoutBuilder_ = new SingleFieldBuilderV3(this.getTimeout(), this.getParentForChildren(), this.isClean());
                this.timeout_ = null;
            }
            return this.timeoutBuilder_;
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

