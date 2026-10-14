/*
 * Decompiled with CFR 0.152.
 */
package com.google.longrunning;

import com.google.longrunning.OperationOrBuilder;
import com.google.longrunning.OperationsProto;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.Any;
import com.google.protobuf.AnyOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.rpc.Status;
import com.google.rpc.StatusOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Operation
extends GeneratedMessageV3
implements OperationOrBuilder {
    private static final long serialVersionUID = 0L;
    private int resultCase_ = 0;
    private Object result_;
    public static final int NAME_FIELD_NUMBER = 1;
    private volatile Object name_ = "";
    public static final int METADATA_FIELD_NUMBER = 2;
    private Any metadata_;
    public static final int DONE_FIELD_NUMBER = 3;
    private boolean done_ = false;
    public static final int ERROR_FIELD_NUMBER = 4;
    public static final int RESPONSE_FIELD_NUMBER = 5;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Operation DEFAULT_INSTANCE = new Operation();
    private static final Parser<Operation> PARSER = new AbstractParser<Operation>(){

        @Override
        public Operation parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Operation.newBuilder();
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

    private Operation(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Operation() {
        this.name_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Operation();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return OperationsProto.internal_static_google_longrunning_Operation_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return OperationsProto.internal_static_google_longrunning_Operation_fieldAccessorTable.ensureFieldAccessorsInitialized(Operation.class, Builder.class);
    }

    @Override
    public ResultCase getResultCase() {
        return ResultCase.forNumber(this.resultCase_);
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
    public boolean hasMetadata() {
        return this.metadata_ != null;
    }

    @Override
    public Any getMetadata() {
        return this.metadata_ == null ? Any.getDefaultInstance() : this.metadata_;
    }

    @Override
    public AnyOrBuilder getMetadataOrBuilder() {
        return this.metadata_ == null ? Any.getDefaultInstance() : this.metadata_;
    }

    @Override
    public boolean getDone() {
        return this.done_;
    }

    @Override
    public boolean hasError() {
        return this.resultCase_ == 4;
    }

    @Override
    public Status getError() {
        if (this.resultCase_ == 4) {
            return (Status)this.result_;
        }
        return Status.getDefaultInstance();
    }

    @Override
    public StatusOrBuilder getErrorOrBuilder() {
        if (this.resultCase_ == 4) {
            return (Status)this.result_;
        }
        return Status.getDefaultInstance();
    }

    @Override
    public boolean hasResponse() {
        return this.resultCase_ == 5;
    }

    @Override
    public Any getResponse() {
        if (this.resultCase_ == 5) {
            return (Any)this.result_;
        }
        return Any.getDefaultInstance();
    }

    @Override
    public AnyOrBuilder getResponseOrBuilder() {
        if (this.resultCase_ == 5) {
            return (Any)this.result_;
        }
        return Any.getDefaultInstance();
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
        if (this.metadata_ != null) {
            output.writeMessage(2, this.getMetadata());
        }
        if (this.done_) {
            output.writeBool(3, this.done_);
        }
        if (this.resultCase_ == 4) {
            output.writeMessage(4, (Status)this.result_);
        }
        if (this.resultCase_ == 5) {
            output.writeMessage(5, (Any)this.result_);
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
        if (this.metadata_ != null) {
            size += CodedOutputStream.computeMessageSize(2, this.getMetadata());
        }
        if (this.done_) {
            size += CodedOutputStream.computeBoolSize(3, this.done_);
        }
        if (this.resultCase_ == 4) {
            size += CodedOutputStream.computeMessageSize(4, (Status)this.result_);
        }
        if (this.resultCase_ == 5) {
            size += CodedOutputStream.computeMessageSize(5, (Any)this.result_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Operation)) {
            return super.equals(obj);
        }
        Operation other = (Operation)obj;
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (this.hasMetadata() != other.hasMetadata()) {
            return false;
        }
        if (this.hasMetadata() && !this.getMetadata().equals(other.getMetadata())) {
            return false;
        }
        if (this.getDone() != other.getDone()) {
            return false;
        }
        if (!this.getResultCase().equals(other.getResultCase())) {
            return false;
        }
        switch (this.resultCase_) {
            case 4: {
                if (this.getError().equals(other.getError())) break;
                return false;
            }
            case 5: {
                if (this.getResponse().equals(other.getResponse())) break;
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
        hash = 19 * hash + Operation.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getName().hashCode();
        if (this.hasMetadata()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getMetadata().hashCode();
        }
        hash = 37 * hash + 3;
        hash = 53 * hash + Internal.hashBoolean(this.getDone());
        switch (this.resultCase_) {
            case 4: {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getError().hashCode();
                break;
            }
            case 5: {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getResponse().hashCode();
                break;
            }
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Operation parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Operation parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Operation parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Operation parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Operation parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Operation parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Operation parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Operation parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Operation parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Operation parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Operation parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Operation parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Operation.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Operation prototype) {
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

    public static Operation getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Operation> parser() {
        return PARSER;
    }

    public Parser<Operation> getParserForType() {
        return PARSER;
    }

    @Override
    public Operation getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements OperationOrBuilder {
        private int resultCase_ = 0;
        private Object result_;
        private int bitField0_;
        private Object name_ = "";
        private Any metadata_;
        private SingleFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> metadataBuilder_;
        private boolean done_;
        private SingleFieldBuilderV3<Status, Status.Builder, StatusOrBuilder> errorBuilder_;
        private SingleFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> responseBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return OperationsProto.internal_static_google_longrunning_Operation_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return OperationsProto.internal_static_google_longrunning_Operation_fieldAccessorTable.ensureFieldAccessorsInitialized(Operation.class, Builder.class);
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
            this.metadata_ = null;
            if (this.metadataBuilder_ != null) {
                this.metadataBuilder_.dispose();
                this.metadataBuilder_ = null;
            }
            this.done_ = false;
            if (this.errorBuilder_ != null) {
                this.errorBuilder_.clear();
            }
            if (this.responseBuilder_ != null) {
                this.responseBuilder_.clear();
            }
            this.resultCase_ = 0;
            this.result_ = null;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return OperationsProto.internal_static_google_longrunning_Operation_descriptor;
        }

        @Override
        public Operation getDefaultInstanceForType() {
            return Operation.getDefaultInstance();
        }

        @Override
        public Operation build() {
            Operation result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Operation buildPartial() {
            Operation result = new Operation(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.buildPartialOneofs(result);
            this.onBuilt();
            return result;
        }

        private void buildPartial0(Operation result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.name_ = this.name_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.metadata_ = this.metadataBuilder_ == null ? this.metadata_ : this.metadataBuilder_.build();
            }
            if ((from_bitField0_ & 4) != 0) {
                result.done_ = this.done_;
            }
        }

        private void buildPartialOneofs(Operation result) {
            result.resultCase_ = this.resultCase_;
            result.result_ = this.result_;
            if (this.resultCase_ == 4 && this.errorBuilder_ != null) {
                result.result_ = this.errorBuilder_.build();
            }
            if (this.resultCase_ == 5 && this.responseBuilder_ != null) {
                result.result_ = this.responseBuilder_.build();
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
            if (other instanceof Operation) {
                return this.mergeFrom((Operation)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Operation other) {
            if (other == Operation.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (other.hasMetadata()) {
                this.mergeMetadata(other.getMetadata());
            }
            if (other.getDone()) {
                this.setDone(other.getDone());
            }
            switch (other.getResultCase()) {
                case ERROR: {
                    this.mergeError(other.getError());
                    break;
                }
                case RESPONSE: {
                    this.mergeResponse(other.getResponse());
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
                block13: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block13;
                        }
                        case 10: {
                            this.name_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block13;
                        }
                        case 18: {
                            input.readMessage(this.getMetadataFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 2;
                            continue block13;
                        }
                        case 24: {
                            this.done_ = input.readBool();
                            this.bitField0_ |= 4;
                            continue block13;
                        }
                        case 34: {
                            input.readMessage(this.getErrorFieldBuilder().getBuilder(), extensionRegistry);
                            this.resultCase_ = 4;
                            continue block13;
                        }
                        case 42: {
                            input.readMessage(this.getResponseFieldBuilder().getBuilder(), extensionRegistry);
                            this.resultCase_ = 5;
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
        public ResultCase getResultCase() {
            return ResultCase.forNumber(this.resultCase_);
        }

        public Builder clearResult() {
            this.resultCase_ = 0;
            this.result_ = null;
            this.onChanged();
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
            this.name_ = Operation.getDefaultInstance().getName();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Operation.checkByteStringIsUtf8(value);
            this.name_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasMetadata() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override
        public Any getMetadata() {
            if (this.metadataBuilder_ == null) {
                return this.metadata_ == null ? Any.getDefaultInstance() : this.metadata_;
            }
            return this.metadataBuilder_.getMessage();
        }

        public Builder setMetadata(Any value) {
            if (this.metadataBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.metadata_ = value;
            } else {
                this.metadataBuilder_.setMessage(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder setMetadata(Any.Builder builderForValue) {
            if (this.metadataBuilder_ == null) {
                this.metadata_ = builderForValue.build();
            } else {
                this.metadataBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder mergeMetadata(Any value) {
            if (this.metadataBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0 && this.metadata_ != null && this.metadata_ != Any.getDefaultInstance()) {
                    this.getMetadataBuilder().mergeFrom(value);
                } else {
                    this.metadata_ = value;
                }
            } else {
                this.metadataBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearMetadata() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.metadata_ = null;
            if (this.metadataBuilder_ != null) {
                this.metadataBuilder_.dispose();
                this.metadataBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Any.Builder getMetadataBuilder() {
            this.bitField0_ |= 2;
            this.onChanged();
            return this.getMetadataFieldBuilder().getBuilder();
        }

        @Override
        public AnyOrBuilder getMetadataOrBuilder() {
            if (this.metadataBuilder_ != null) {
                return this.metadataBuilder_.getMessageOrBuilder();
            }
            return this.metadata_ == null ? Any.getDefaultInstance() : this.metadata_;
        }

        private SingleFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> getMetadataFieldBuilder() {
            if (this.metadataBuilder_ == null) {
                this.metadataBuilder_ = new SingleFieldBuilderV3(this.getMetadata(), this.getParentForChildren(), this.isClean());
                this.metadata_ = null;
            }
            return this.metadataBuilder_;
        }

        @Override
        public boolean getDone() {
            return this.done_;
        }

        public Builder setDone(boolean value) {
            this.done_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearDone() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.done_ = false;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasError() {
            return this.resultCase_ == 4;
        }

        @Override
        public Status getError() {
            if (this.errorBuilder_ == null) {
                if (this.resultCase_ == 4) {
                    return (Status)this.result_;
                }
                return Status.getDefaultInstance();
            }
            if (this.resultCase_ == 4) {
                return this.errorBuilder_.getMessage();
            }
            return Status.getDefaultInstance();
        }

        public Builder setError(Status value) {
            if (this.errorBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.result_ = value;
                this.onChanged();
            } else {
                this.errorBuilder_.setMessage(value);
            }
            this.resultCase_ = 4;
            return this;
        }

        public Builder setError(Status.Builder builderForValue) {
            if (this.errorBuilder_ == null) {
                this.result_ = builderForValue.build();
                this.onChanged();
            } else {
                this.errorBuilder_.setMessage(builderForValue.build());
            }
            this.resultCase_ = 4;
            return this;
        }

        public Builder mergeError(Status value) {
            if (this.errorBuilder_ == null) {
                this.result_ = this.resultCase_ == 4 && this.result_ != Status.getDefaultInstance() ? Status.newBuilder((Status)this.result_).mergeFrom(value).buildPartial() : value;
                this.onChanged();
            } else if (this.resultCase_ == 4) {
                this.errorBuilder_.mergeFrom(value);
            } else {
                this.errorBuilder_.setMessage(value);
            }
            this.resultCase_ = 4;
            return this;
        }

        public Builder clearError() {
            if (this.errorBuilder_ == null) {
                if (this.resultCase_ == 4) {
                    this.resultCase_ = 0;
                    this.result_ = null;
                    this.onChanged();
                }
            } else {
                if (this.resultCase_ == 4) {
                    this.resultCase_ = 0;
                    this.result_ = null;
                }
                this.errorBuilder_.clear();
            }
            return this;
        }

        public Status.Builder getErrorBuilder() {
            return this.getErrorFieldBuilder().getBuilder();
        }

        @Override
        public StatusOrBuilder getErrorOrBuilder() {
            if (this.resultCase_ == 4 && this.errorBuilder_ != null) {
                return this.errorBuilder_.getMessageOrBuilder();
            }
            if (this.resultCase_ == 4) {
                return (Status)this.result_;
            }
            return Status.getDefaultInstance();
        }

        private SingleFieldBuilderV3<Status, Status.Builder, StatusOrBuilder> getErrorFieldBuilder() {
            if (this.errorBuilder_ == null) {
                if (this.resultCase_ != 4) {
                    this.result_ = Status.getDefaultInstance();
                }
                this.errorBuilder_ = new SingleFieldBuilderV3((Status)this.result_, this.getParentForChildren(), this.isClean());
                this.result_ = null;
            }
            this.resultCase_ = 4;
            this.onChanged();
            return this.errorBuilder_;
        }

        @Override
        public boolean hasResponse() {
            return this.resultCase_ == 5;
        }

        @Override
        public Any getResponse() {
            if (this.responseBuilder_ == null) {
                if (this.resultCase_ == 5) {
                    return (Any)this.result_;
                }
                return Any.getDefaultInstance();
            }
            if (this.resultCase_ == 5) {
                return this.responseBuilder_.getMessage();
            }
            return Any.getDefaultInstance();
        }

        public Builder setResponse(Any value) {
            if (this.responseBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.result_ = value;
                this.onChanged();
            } else {
                this.responseBuilder_.setMessage(value);
            }
            this.resultCase_ = 5;
            return this;
        }

        public Builder setResponse(Any.Builder builderForValue) {
            if (this.responseBuilder_ == null) {
                this.result_ = builderForValue.build();
                this.onChanged();
            } else {
                this.responseBuilder_.setMessage(builderForValue.build());
            }
            this.resultCase_ = 5;
            return this;
        }

        public Builder mergeResponse(Any value) {
            if (this.responseBuilder_ == null) {
                this.result_ = this.resultCase_ == 5 && this.result_ != Any.getDefaultInstance() ? Any.newBuilder((Any)this.result_).mergeFrom(value).buildPartial() : value;
                this.onChanged();
            } else if (this.resultCase_ == 5) {
                this.responseBuilder_.mergeFrom(value);
            } else {
                this.responseBuilder_.setMessage(value);
            }
            this.resultCase_ = 5;
            return this;
        }

        public Builder clearResponse() {
            if (this.responseBuilder_ == null) {
                if (this.resultCase_ == 5) {
                    this.resultCase_ = 0;
                    this.result_ = null;
                    this.onChanged();
                }
            } else {
                if (this.resultCase_ == 5) {
                    this.resultCase_ = 0;
                    this.result_ = null;
                }
                this.responseBuilder_.clear();
            }
            return this;
        }

        public Any.Builder getResponseBuilder() {
            return this.getResponseFieldBuilder().getBuilder();
        }

        @Override
        public AnyOrBuilder getResponseOrBuilder() {
            if (this.resultCase_ == 5 && this.responseBuilder_ != null) {
                return this.responseBuilder_.getMessageOrBuilder();
            }
            if (this.resultCase_ == 5) {
                return (Any)this.result_;
            }
            return Any.getDefaultInstance();
        }

        private SingleFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> getResponseFieldBuilder() {
            if (this.responseBuilder_ == null) {
                if (this.resultCase_ != 5) {
                    this.result_ = Any.getDefaultInstance();
                }
                this.responseBuilder_ = new SingleFieldBuilderV3((Any)this.result_, this.getParentForChildren(), this.isClean());
                this.result_ = null;
            }
            this.resultCase_ = 5;
            this.onChanged();
            return this.responseBuilder_;
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

    public static enum ResultCase implements Internal.EnumLite,
    AbstractMessageLite.InternalOneOfEnum
    {
        ERROR(4),
        RESPONSE(5),
        RESULT_NOT_SET(0);

        private final int value;

        private ResultCase(int value) {
            this.value = value;
        }

        @Deprecated
        public static ResultCase valueOf(int value) {
            return ResultCase.forNumber(value);
        }

        public static ResultCase forNumber(int value) {
            switch (value) {
                case 4: {
                    return ERROR;
                }
                case 5: {
                    return RESPONSE;
                }
                case 0: {
                    return RESULT_NOT_SET;
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

