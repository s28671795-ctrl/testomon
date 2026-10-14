/*
 * Decompiled with CFR 0.152.
 */
package com.google.longrunning;

import com.google.longrunning.ListOperationsResponseOrBuilder;
import com.google.longrunning.Operation;
import com.google.longrunning.OperationOrBuilder;
import com.google.longrunning.OperationsProto;
import com.google.protobuf.AbstractMessageLite;
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
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ListOperationsResponse
extends GeneratedMessageV3
implements ListOperationsResponseOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int OPERATIONS_FIELD_NUMBER = 1;
    private List<Operation> operations_;
    public static final int NEXT_PAGE_TOKEN_FIELD_NUMBER = 2;
    private volatile Object nextPageToken_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final ListOperationsResponse DEFAULT_INSTANCE = new ListOperationsResponse();
    private static final Parser<ListOperationsResponse> PARSER = new AbstractParser<ListOperationsResponse>(){

        @Override
        public ListOperationsResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = ListOperationsResponse.newBuilder();
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

    private ListOperationsResponse(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private ListOperationsResponse() {
        this.operations_ = Collections.emptyList();
        this.nextPageToken_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new ListOperationsResponse();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return OperationsProto.internal_static_google_longrunning_ListOperationsResponse_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return OperationsProto.internal_static_google_longrunning_ListOperationsResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(ListOperationsResponse.class, Builder.class);
    }

    @Override
    public List<Operation> getOperationsList() {
        return this.operations_;
    }

    @Override
    public List<? extends OperationOrBuilder> getOperationsOrBuilderList() {
        return this.operations_;
    }

    @Override
    public int getOperationsCount() {
        return this.operations_.size();
    }

    @Override
    public Operation getOperations(int index) {
        return this.operations_.get(index);
    }

    @Override
    public OperationOrBuilder getOperationsOrBuilder(int index) {
        return this.operations_.get(index);
    }

    @Override
    public String getNextPageToken() {
        Object ref = this.nextPageToken_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.nextPageToken_ = s2;
        return s2;
    }

    @Override
    public ByteString getNextPageTokenBytes() {
        Object ref = this.nextPageToken_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.nextPageToken_ = b;
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
        for (int i = 0; i < this.operations_.size(); ++i) {
            output.writeMessage(1, this.operations_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.nextPageToken_)) {
            GeneratedMessageV3.writeString(output, 2, this.nextPageToken_);
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
        for (int i = 0; i < this.operations_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(1, this.operations_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.nextPageToken_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.nextPageToken_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ListOperationsResponse)) {
            return super.equals(obj);
        }
        ListOperationsResponse other = (ListOperationsResponse)obj;
        if (!this.getOperationsList().equals(other.getOperationsList())) {
            return false;
        }
        if (!this.getNextPageToken().equals(other.getNextPageToken())) {
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
        hash = 19 * hash + ListOperationsResponse.getDescriptor().hashCode();
        if (this.getOperationsCount() > 0) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getOperationsList().hashCode();
        }
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getNextPageToken().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static ListOperationsResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ListOperationsResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ListOperationsResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ListOperationsResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ListOperationsResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ListOperationsResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ListOperationsResponse parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ListOperationsResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ListOperationsResponse parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ListOperationsResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ListOperationsResponse parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ListOperationsResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return ListOperationsResponse.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ListOperationsResponse prototype) {
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

    public static ListOperationsResponse getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ListOperationsResponse> parser() {
        return PARSER;
    }

    public Parser<ListOperationsResponse> getParserForType() {
        return PARSER;
    }

    @Override
    public ListOperationsResponse getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ListOperationsResponseOrBuilder {
        private int bitField0_;
        private List<Operation> operations_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<Operation, Operation.Builder, OperationOrBuilder> operationsBuilder_;
        private Object nextPageToken_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return OperationsProto.internal_static_google_longrunning_ListOperationsResponse_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return OperationsProto.internal_static_google_longrunning_ListOperationsResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(ListOperationsResponse.class, Builder.class);
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
            if (this.operationsBuilder_ == null) {
                this.operations_ = Collections.emptyList();
            } else {
                this.operations_ = null;
                this.operationsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFE;
            this.nextPageToken_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return OperationsProto.internal_static_google_longrunning_ListOperationsResponse_descriptor;
        }

        @Override
        public ListOperationsResponse getDefaultInstanceForType() {
            return ListOperationsResponse.getDefaultInstance();
        }

        @Override
        public ListOperationsResponse build() {
            ListOperationsResponse result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ListOperationsResponse buildPartial() {
            ListOperationsResponse result = new ListOperationsResponse(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(ListOperationsResponse result) {
            if (this.operationsBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0) {
                    this.operations_ = Collections.unmodifiableList(this.operations_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.operations_ = this.operations_;
            } else {
                result.operations_ = this.operationsBuilder_.build();
            }
        }

        private void buildPartial0(ListOperationsResponse result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 2) != 0) {
                result.nextPageToken_ = this.nextPageToken_;
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
            if (other instanceof ListOperationsResponse) {
                return this.mergeFrom((ListOperationsResponse)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ListOperationsResponse other) {
            if (other == ListOperationsResponse.getDefaultInstance()) {
                return this;
            }
            if (this.operationsBuilder_ == null) {
                if (!other.operations_.isEmpty()) {
                    if (this.operations_.isEmpty()) {
                        this.operations_ = other.operations_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureOperationsIsMutable();
                        this.operations_.addAll(other.operations_);
                    }
                    this.onChanged();
                }
            } else if (!other.operations_.isEmpty()) {
                if (this.operationsBuilder_.isEmpty()) {
                    this.operationsBuilder_.dispose();
                    this.operationsBuilder_ = null;
                    this.operations_ = other.operations_;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.operationsBuilder_ = alwaysUseFieldBuilders ? this.getOperationsFieldBuilder() : null;
                } else {
                    this.operationsBuilder_.addAllMessages(other.operations_);
                }
            }
            if (!other.getNextPageToken().isEmpty()) {
                this.nextPageToken_ = other.nextPageToken_;
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
                            Operation m4 = input.readMessage(Operation.parser(), extensionRegistry);
                            if (this.operationsBuilder_ == null) {
                                this.ensureOperationsIsMutable();
                                this.operations_.add(m4);
                                continue block10;
                            }
                            this.operationsBuilder_.addMessage(m4);
                            continue block10;
                        }
                        case 18: {
                            this.nextPageToken_ = input.readStringRequireUtf8();
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

        private void ensureOperationsIsMutable() {
            if ((this.bitField0_ & 1) == 0) {
                this.operations_ = new ArrayList<Operation>(this.operations_);
                this.bitField0_ |= 1;
            }
        }

        @Override
        public List<Operation> getOperationsList() {
            if (this.operationsBuilder_ == null) {
                return Collections.unmodifiableList(this.operations_);
            }
            return this.operationsBuilder_.getMessageList();
        }

        @Override
        public int getOperationsCount() {
            if (this.operationsBuilder_ == null) {
                return this.operations_.size();
            }
            return this.operationsBuilder_.getCount();
        }

        @Override
        public Operation getOperations(int index) {
            if (this.operationsBuilder_ == null) {
                return this.operations_.get(index);
            }
            return this.operationsBuilder_.getMessage(index);
        }

        public Builder setOperations(int index, Operation value) {
            if (this.operationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureOperationsIsMutable();
                this.operations_.set(index, value);
                this.onChanged();
            } else {
                this.operationsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setOperations(int index, Operation.Builder builderForValue) {
            if (this.operationsBuilder_ == null) {
                this.ensureOperationsIsMutable();
                this.operations_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.operationsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addOperations(Operation value) {
            if (this.operationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureOperationsIsMutable();
                this.operations_.add(value);
                this.onChanged();
            } else {
                this.operationsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addOperations(int index, Operation value) {
            if (this.operationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureOperationsIsMutable();
                this.operations_.add(index, value);
                this.onChanged();
            } else {
                this.operationsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addOperations(Operation.Builder builderForValue) {
            if (this.operationsBuilder_ == null) {
                this.ensureOperationsIsMutable();
                this.operations_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.operationsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addOperations(int index, Operation.Builder builderForValue) {
            if (this.operationsBuilder_ == null) {
                this.ensureOperationsIsMutable();
                this.operations_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.operationsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllOperations(Iterable<? extends Operation> values2) {
            if (this.operationsBuilder_ == null) {
                this.ensureOperationsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.operations_);
                this.onChanged();
            } else {
                this.operationsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearOperations() {
            if (this.operationsBuilder_ == null) {
                this.operations_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
            } else {
                this.operationsBuilder_.clear();
            }
            return this;
        }

        public Builder removeOperations(int index) {
            if (this.operationsBuilder_ == null) {
                this.ensureOperationsIsMutable();
                this.operations_.remove(index);
                this.onChanged();
            } else {
                this.operationsBuilder_.remove(index);
            }
            return this;
        }

        public Operation.Builder getOperationsBuilder(int index) {
            return this.getOperationsFieldBuilder().getBuilder(index);
        }

        @Override
        public OperationOrBuilder getOperationsOrBuilder(int index) {
            if (this.operationsBuilder_ == null) {
                return this.operations_.get(index);
            }
            return this.operationsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends OperationOrBuilder> getOperationsOrBuilderList() {
            if (this.operationsBuilder_ != null) {
                return this.operationsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.operations_);
        }

        public Operation.Builder addOperationsBuilder() {
            return this.getOperationsFieldBuilder().addBuilder(Operation.getDefaultInstance());
        }

        public Operation.Builder addOperationsBuilder(int index) {
            return this.getOperationsFieldBuilder().addBuilder(index, Operation.getDefaultInstance());
        }

        public List<Operation.Builder> getOperationsBuilderList() {
            return this.getOperationsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Operation, Operation.Builder, OperationOrBuilder> getOperationsFieldBuilder() {
            if (this.operationsBuilder_ == null) {
                this.operationsBuilder_ = new RepeatedFieldBuilderV3(this.operations_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
                this.operations_ = null;
            }
            return this.operationsBuilder_;
        }

        @Override
        public String getNextPageToken() {
            Object ref = this.nextPageToken_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.nextPageToken_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getNextPageTokenBytes() {
            Object ref = this.nextPageToken_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.nextPageToken_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setNextPageToken(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.nextPageToken_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearNextPageToken() {
            this.nextPageToken_ = ListOperationsResponse.getDefaultInstance().getNextPageToken();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setNextPageTokenBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ListOperationsResponse.checkByteStringIsUtf8(value);
            this.nextPageToken_ = value;
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

