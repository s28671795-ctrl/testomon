/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc.context;

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
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.Struct;
import com.google.protobuf.StructOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.rpc.context.AuditContextOrBuilder;
import com.google.rpc.context.AuditContextProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class AuditContext
extends GeneratedMessageV3
implements AuditContextOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int AUDIT_LOG_FIELD_NUMBER = 1;
    private ByteString auditLog_ = ByteString.EMPTY;
    public static final int SCRUBBED_REQUEST_FIELD_NUMBER = 2;
    private Struct scrubbedRequest_;
    public static final int SCRUBBED_RESPONSE_FIELD_NUMBER = 3;
    private Struct scrubbedResponse_;
    public static final int SCRUBBED_RESPONSE_ITEM_COUNT_FIELD_NUMBER = 4;
    private int scrubbedResponseItemCount_ = 0;
    public static final int TARGET_RESOURCE_FIELD_NUMBER = 5;
    private volatile Object targetResource_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final AuditContext DEFAULT_INSTANCE = new AuditContext();
    private static final Parser<AuditContext> PARSER = new AbstractParser<AuditContext>(){

        @Override
        public AuditContext parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = AuditContext.newBuilder();
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

    private AuditContext(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private AuditContext() {
        this.auditLog_ = ByteString.EMPTY;
        this.targetResource_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new AuditContext();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuditContextProto.internal_static_google_rpc_context_AuditContext_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuditContextProto.internal_static_google_rpc_context_AuditContext_fieldAccessorTable.ensureFieldAccessorsInitialized(AuditContext.class, Builder.class);
    }

    @Override
    public ByteString getAuditLog() {
        return this.auditLog_;
    }

    @Override
    public boolean hasScrubbedRequest() {
        return this.scrubbedRequest_ != null;
    }

    @Override
    public Struct getScrubbedRequest() {
        return this.scrubbedRequest_ == null ? Struct.getDefaultInstance() : this.scrubbedRequest_;
    }

    @Override
    public StructOrBuilder getScrubbedRequestOrBuilder() {
        return this.scrubbedRequest_ == null ? Struct.getDefaultInstance() : this.scrubbedRequest_;
    }

    @Override
    public boolean hasScrubbedResponse() {
        return this.scrubbedResponse_ != null;
    }

    @Override
    public Struct getScrubbedResponse() {
        return this.scrubbedResponse_ == null ? Struct.getDefaultInstance() : this.scrubbedResponse_;
    }

    @Override
    public StructOrBuilder getScrubbedResponseOrBuilder() {
        return this.scrubbedResponse_ == null ? Struct.getDefaultInstance() : this.scrubbedResponse_;
    }

    @Override
    public int getScrubbedResponseItemCount() {
        return this.scrubbedResponseItemCount_;
    }

    @Override
    public String getTargetResource() {
        Object ref = this.targetResource_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.targetResource_ = s2;
        return s2;
    }

    @Override
    public ByteString getTargetResourceBytes() {
        Object ref = this.targetResource_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.targetResource_ = b;
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
        if (!this.auditLog_.isEmpty()) {
            output.writeBytes(1, this.auditLog_);
        }
        if (this.scrubbedRequest_ != null) {
            output.writeMessage(2, this.getScrubbedRequest());
        }
        if (this.scrubbedResponse_ != null) {
            output.writeMessage(3, this.getScrubbedResponse());
        }
        if (this.scrubbedResponseItemCount_ != 0) {
            output.writeInt32(4, this.scrubbedResponseItemCount_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.targetResource_)) {
            GeneratedMessageV3.writeString(output, 5, this.targetResource_);
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
        if (!this.auditLog_.isEmpty()) {
            size += CodedOutputStream.computeBytesSize(1, this.auditLog_);
        }
        if (this.scrubbedRequest_ != null) {
            size += CodedOutputStream.computeMessageSize(2, this.getScrubbedRequest());
        }
        if (this.scrubbedResponse_ != null) {
            size += CodedOutputStream.computeMessageSize(3, this.getScrubbedResponse());
        }
        if (this.scrubbedResponseItemCount_ != 0) {
            size += CodedOutputStream.computeInt32Size(4, this.scrubbedResponseItemCount_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.targetResource_)) {
            size += GeneratedMessageV3.computeStringSize(5, this.targetResource_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AuditContext)) {
            return super.equals(obj);
        }
        AuditContext other = (AuditContext)obj;
        if (!this.getAuditLog().equals(other.getAuditLog())) {
            return false;
        }
        if (this.hasScrubbedRequest() != other.hasScrubbedRequest()) {
            return false;
        }
        if (this.hasScrubbedRequest() && !this.getScrubbedRequest().equals(other.getScrubbedRequest())) {
            return false;
        }
        if (this.hasScrubbedResponse() != other.hasScrubbedResponse()) {
            return false;
        }
        if (this.hasScrubbedResponse() && !this.getScrubbedResponse().equals(other.getScrubbedResponse())) {
            return false;
        }
        if (this.getScrubbedResponseItemCount() != other.getScrubbedResponseItemCount()) {
            return false;
        }
        if (!this.getTargetResource().equals(other.getTargetResource())) {
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
        hash = 19 * hash + AuditContext.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getAuditLog().hashCode();
        if (this.hasScrubbedRequest()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getScrubbedRequest().hashCode();
        }
        if (this.hasScrubbedResponse()) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getScrubbedResponse().hashCode();
        }
        hash = 37 * hash + 4;
        hash = 53 * hash + this.getScrubbedResponseItemCount();
        hash = 37 * hash + 5;
        hash = 53 * hash + this.getTargetResource().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static AuditContext parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuditContext parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuditContext parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuditContext parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuditContext parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuditContext parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuditContext parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AuditContext parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static AuditContext parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static AuditContext parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static AuditContext parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AuditContext parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return AuditContext.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(AuditContext prototype) {
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

    public static AuditContext getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AuditContext> parser() {
        return PARSER;
    }

    public Parser<AuditContext> getParserForType() {
        return PARSER;
    }

    @Override
    public AuditContext getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements AuditContextOrBuilder {
        private int bitField0_;
        private ByteString auditLog_ = ByteString.EMPTY;
        private Struct scrubbedRequest_;
        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> scrubbedRequestBuilder_;
        private Struct scrubbedResponse_;
        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> scrubbedResponseBuilder_;
        private int scrubbedResponseItemCount_;
        private Object targetResource_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return AuditContextProto.internal_static_google_rpc_context_AuditContext_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuditContextProto.internal_static_google_rpc_context_AuditContext_fieldAccessorTable.ensureFieldAccessorsInitialized(AuditContext.class, Builder.class);
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
            this.auditLog_ = ByteString.EMPTY;
            this.scrubbedRequest_ = null;
            if (this.scrubbedRequestBuilder_ != null) {
                this.scrubbedRequestBuilder_.dispose();
                this.scrubbedRequestBuilder_ = null;
            }
            this.scrubbedResponse_ = null;
            if (this.scrubbedResponseBuilder_ != null) {
                this.scrubbedResponseBuilder_.dispose();
                this.scrubbedResponseBuilder_ = null;
            }
            this.scrubbedResponseItemCount_ = 0;
            this.targetResource_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuditContextProto.internal_static_google_rpc_context_AuditContext_descriptor;
        }

        @Override
        public AuditContext getDefaultInstanceForType() {
            return AuditContext.getDefaultInstance();
        }

        @Override
        public AuditContext build() {
            AuditContext result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public AuditContext buildPartial() {
            AuditContext result = new AuditContext(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(AuditContext result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.auditLog_ = this.auditLog_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.scrubbedRequest_ = this.scrubbedRequestBuilder_ == null ? this.scrubbedRequest_ : this.scrubbedRequestBuilder_.build();
            }
            if ((from_bitField0_ & 4) != 0) {
                result.scrubbedResponse_ = this.scrubbedResponseBuilder_ == null ? this.scrubbedResponse_ : this.scrubbedResponseBuilder_.build();
            }
            if ((from_bitField0_ & 8) != 0) {
                result.scrubbedResponseItemCount_ = this.scrubbedResponseItemCount_;
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.targetResource_ = this.targetResource_;
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
            if (other instanceof AuditContext) {
                return this.mergeFrom((AuditContext)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(AuditContext other) {
            if (other == AuditContext.getDefaultInstance()) {
                return this;
            }
            if (other.getAuditLog() != ByteString.EMPTY) {
                this.setAuditLog(other.getAuditLog());
            }
            if (other.hasScrubbedRequest()) {
                this.mergeScrubbedRequest(other.getScrubbedRequest());
            }
            if (other.hasScrubbedResponse()) {
                this.mergeScrubbedResponse(other.getScrubbedResponse());
            }
            if (other.getScrubbedResponseItemCount() != 0) {
                this.setScrubbedResponseItemCount(other.getScrubbedResponseItemCount());
            }
            if (!other.getTargetResource().isEmpty()) {
                this.targetResource_ = other.targetResource_;
                this.bitField0_ |= 0x10;
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
                block13: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block13;
                        }
                        case 10: {
                            this.auditLog_ = input.readBytes();
                            this.bitField0_ |= 1;
                            continue block13;
                        }
                        case 18: {
                            input.readMessage(this.getScrubbedRequestFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 2;
                            continue block13;
                        }
                        case 26: {
                            input.readMessage(this.getScrubbedResponseFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 4;
                            continue block13;
                        }
                        case 32: {
                            this.scrubbedResponseItemCount_ = input.readInt32();
                            this.bitField0_ |= 8;
                            continue block13;
                        }
                        case 42: {
                            this.targetResource_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x10;
                            continue block13;
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
        public ByteString getAuditLog() {
            return this.auditLog_;
        }

        public Builder setAuditLog(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.auditLog_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearAuditLog() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.auditLog_ = AuditContext.getDefaultInstance().getAuditLog();
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasScrubbedRequest() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override
        public Struct getScrubbedRequest() {
            if (this.scrubbedRequestBuilder_ == null) {
                return this.scrubbedRequest_ == null ? Struct.getDefaultInstance() : this.scrubbedRequest_;
            }
            return this.scrubbedRequestBuilder_.getMessage();
        }

        public Builder setScrubbedRequest(Struct value) {
            if (this.scrubbedRequestBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.scrubbedRequest_ = value;
            } else {
                this.scrubbedRequestBuilder_.setMessage(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder setScrubbedRequest(Struct.Builder builderForValue) {
            if (this.scrubbedRequestBuilder_ == null) {
                this.scrubbedRequest_ = builderForValue.build();
            } else {
                this.scrubbedRequestBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder mergeScrubbedRequest(Struct value) {
            if (this.scrubbedRequestBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0 && this.scrubbedRequest_ != null && this.scrubbedRequest_ != Struct.getDefaultInstance()) {
                    this.getScrubbedRequestBuilder().mergeFrom(value);
                } else {
                    this.scrubbedRequest_ = value;
                }
            } else {
                this.scrubbedRequestBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearScrubbedRequest() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.scrubbedRequest_ = null;
            if (this.scrubbedRequestBuilder_ != null) {
                this.scrubbedRequestBuilder_.dispose();
                this.scrubbedRequestBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Struct.Builder getScrubbedRequestBuilder() {
            this.bitField0_ |= 2;
            this.onChanged();
            return this.getScrubbedRequestFieldBuilder().getBuilder();
        }

        @Override
        public StructOrBuilder getScrubbedRequestOrBuilder() {
            if (this.scrubbedRequestBuilder_ != null) {
                return this.scrubbedRequestBuilder_.getMessageOrBuilder();
            }
            return this.scrubbedRequest_ == null ? Struct.getDefaultInstance() : this.scrubbedRequest_;
        }

        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getScrubbedRequestFieldBuilder() {
            if (this.scrubbedRequestBuilder_ == null) {
                this.scrubbedRequestBuilder_ = new SingleFieldBuilderV3(this.getScrubbedRequest(), this.getParentForChildren(), this.isClean());
                this.scrubbedRequest_ = null;
            }
            return this.scrubbedRequestBuilder_;
        }

        @Override
        public boolean hasScrubbedResponse() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override
        public Struct getScrubbedResponse() {
            if (this.scrubbedResponseBuilder_ == null) {
                return this.scrubbedResponse_ == null ? Struct.getDefaultInstance() : this.scrubbedResponse_;
            }
            return this.scrubbedResponseBuilder_.getMessage();
        }

        public Builder setScrubbedResponse(Struct value) {
            if (this.scrubbedResponseBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.scrubbedResponse_ = value;
            } else {
                this.scrubbedResponseBuilder_.setMessage(value);
            }
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder setScrubbedResponse(Struct.Builder builderForValue) {
            if (this.scrubbedResponseBuilder_ == null) {
                this.scrubbedResponse_ = builderForValue.build();
            } else {
                this.scrubbedResponseBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder mergeScrubbedResponse(Struct value) {
            if (this.scrubbedResponseBuilder_ == null) {
                if ((this.bitField0_ & 4) != 0 && this.scrubbedResponse_ != null && this.scrubbedResponse_ != Struct.getDefaultInstance()) {
                    this.getScrubbedResponseBuilder().mergeFrom(value);
                } else {
                    this.scrubbedResponse_ = value;
                }
            } else {
                this.scrubbedResponseBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearScrubbedResponse() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.scrubbedResponse_ = null;
            if (this.scrubbedResponseBuilder_ != null) {
                this.scrubbedResponseBuilder_.dispose();
                this.scrubbedResponseBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Struct.Builder getScrubbedResponseBuilder() {
            this.bitField0_ |= 4;
            this.onChanged();
            return this.getScrubbedResponseFieldBuilder().getBuilder();
        }

        @Override
        public StructOrBuilder getScrubbedResponseOrBuilder() {
            if (this.scrubbedResponseBuilder_ != null) {
                return this.scrubbedResponseBuilder_.getMessageOrBuilder();
            }
            return this.scrubbedResponse_ == null ? Struct.getDefaultInstance() : this.scrubbedResponse_;
        }

        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getScrubbedResponseFieldBuilder() {
            if (this.scrubbedResponseBuilder_ == null) {
                this.scrubbedResponseBuilder_ = new SingleFieldBuilderV3(this.getScrubbedResponse(), this.getParentForChildren(), this.isClean());
                this.scrubbedResponse_ = null;
            }
            return this.scrubbedResponseBuilder_;
        }

        @Override
        public int getScrubbedResponseItemCount() {
            return this.scrubbedResponseItemCount_;
        }

        public Builder setScrubbedResponseItemCount(int value) {
            this.scrubbedResponseItemCount_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearScrubbedResponseItemCount() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.scrubbedResponseItemCount_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public String getTargetResource() {
            Object ref = this.targetResource_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.targetResource_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getTargetResourceBytes() {
            Object ref = this.targetResource_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.targetResource_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setTargetResource(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.targetResource_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearTargetResource() {
            this.targetResource_ = AuditContext.getDefaultInstance().getTargetResource();
            this.bitField0_ &= 0xFFFFFFEF;
            this.onChanged();
            return this;
        }

        public Builder setTargetResourceBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuditContext.checkByteStringIsUtf8(value);
            this.targetResource_ = value;
            this.bitField0_ |= 0x10;
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

