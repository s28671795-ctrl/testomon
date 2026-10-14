/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

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
import com.google.rpc.ErrorDetailsProto;
import com.google.rpc.RetryInfoOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class RetryInfo
extends GeneratedMessageV3
implements RetryInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int RETRY_DELAY_FIELD_NUMBER = 1;
    private Duration retryDelay_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final RetryInfo DEFAULT_INSTANCE = new RetryInfo();
    private static final Parser<RetryInfo> PARSER = new AbstractParser<RetryInfo>(){

        @Override
        public RetryInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = RetryInfo.newBuilder();
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

    private RetryInfo(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private RetryInfo() {
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new RetryInfo();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ErrorDetailsProto.internal_static_google_rpc_RetryInfo_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ErrorDetailsProto.internal_static_google_rpc_RetryInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(RetryInfo.class, Builder.class);
    }

    @Override
    public boolean hasRetryDelay() {
        return this.retryDelay_ != null;
    }

    @Override
    public Duration getRetryDelay() {
        return this.retryDelay_ == null ? Duration.getDefaultInstance() : this.retryDelay_;
    }

    @Override
    public DurationOrBuilder getRetryDelayOrBuilder() {
        return this.retryDelay_ == null ? Duration.getDefaultInstance() : this.retryDelay_;
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
        if (this.retryDelay_ != null) {
            output.writeMessage(1, this.getRetryDelay());
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
        if (this.retryDelay_ != null) {
            size += CodedOutputStream.computeMessageSize(1, this.getRetryDelay());
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RetryInfo)) {
            return super.equals(obj);
        }
        RetryInfo other = (RetryInfo)obj;
        if (this.hasRetryDelay() != other.hasRetryDelay()) {
            return false;
        }
        if (this.hasRetryDelay() && !this.getRetryDelay().equals(other.getRetryDelay())) {
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
        hash = 19 * hash + RetryInfo.getDescriptor().hashCode();
        if (this.hasRetryDelay()) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getRetryDelay().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static RetryInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RetryInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RetryInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RetryInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RetryInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RetryInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RetryInfo parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static RetryInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static RetryInfo parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static RetryInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static RetryInfo parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static RetryInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return RetryInfo.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(RetryInfo prototype) {
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

    public static RetryInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<RetryInfo> parser() {
        return PARSER;
    }

    public Parser<RetryInfo> getParserForType() {
        return PARSER;
    }

    @Override
    public RetryInfo getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements RetryInfoOrBuilder {
        private int bitField0_;
        private Duration retryDelay_;
        private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> retryDelayBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return ErrorDetailsProto.internal_static_google_rpc_RetryInfo_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ErrorDetailsProto.internal_static_google_rpc_RetryInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(RetryInfo.class, Builder.class);
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
            this.retryDelay_ = null;
            if (this.retryDelayBuilder_ != null) {
                this.retryDelayBuilder_.dispose();
                this.retryDelayBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ErrorDetailsProto.internal_static_google_rpc_RetryInfo_descriptor;
        }

        @Override
        public RetryInfo getDefaultInstanceForType() {
            return RetryInfo.getDefaultInstance();
        }

        @Override
        public RetryInfo build() {
            RetryInfo result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public RetryInfo buildPartial() {
            RetryInfo result = new RetryInfo(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(RetryInfo result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.retryDelay_ = this.retryDelayBuilder_ == null ? this.retryDelay_ : this.retryDelayBuilder_.build();
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
            if (other instanceof RetryInfo) {
                return this.mergeFrom((RetryInfo)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(RetryInfo other) {
            if (other == RetryInfo.getDefaultInstance()) {
                return this;
            }
            if (other.hasRetryDelay()) {
                this.mergeRetryDelay(other.getRetryDelay());
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
                            input.readMessage(this.getRetryDelayFieldBuilder().getBuilder(), extensionRegistry);
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
        public boolean hasRetryDelay() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public Duration getRetryDelay() {
            if (this.retryDelayBuilder_ == null) {
                return this.retryDelay_ == null ? Duration.getDefaultInstance() : this.retryDelay_;
            }
            return this.retryDelayBuilder_.getMessage();
        }

        public Builder setRetryDelay(Duration value) {
            if (this.retryDelayBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.retryDelay_ = value;
            } else {
                this.retryDelayBuilder_.setMessage(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder setRetryDelay(Duration.Builder builderForValue) {
            if (this.retryDelayBuilder_ == null) {
                this.retryDelay_ = builderForValue.build();
            } else {
                this.retryDelayBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder mergeRetryDelay(Duration value) {
            if (this.retryDelayBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0 && this.retryDelay_ != null && this.retryDelay_ != Duration.getDefaultInstance()) {
                    this.getRetryDelayBuilder().mergeFrom(value);
                } else {
                    this.retryDelay_ = value;
                }
            } else {
                this.retryDelayBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearRetryDelay() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.retryDelay_ = null;
            if (this.retryDelayBuilder_ != null) {
                this.retryDelayBuilder_.dispose();
                this.retryDelayBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Duration.Builder getRetryDelayBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return this.getRetryDelayFieldBuilder().getBuilder();
        }

        @Override
        public DurationOrBuilder getRetryDelayOrBuilder() {
            if (this.retryDelayBuilder_ != null) {
                return this.retryDelayBuilder_.getMessageOrBuilder();
            }
            return this.retryDelay_ == null ? Duration.getDefaultInstance() : this.retryDelay_;
        }

        private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> getRetryDelayFieldBuilder() {
            if (this.retryDelayBuilder_ == null) {
                this.retryDelayBuilder_ = new SingleFieldBuilderV3(this.getRetryDelay(), this.getParentForChildren(), this.isClean());
                this.retryDelay_ = null;
            }
            return this.retryDelayBuilder_;
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

