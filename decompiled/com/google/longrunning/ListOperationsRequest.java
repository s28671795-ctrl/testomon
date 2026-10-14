/*
 * Decompiled with CFR 0.152.
 */
package com.google.longrunning;

import com.google.longrunning.ListOperationsRequestOrBuilder;
import com.google.longrunning.OperationsProto;
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
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ListOperationsRequest
extends GeneratedMessageV3
implements ListOperationsRequestOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 4;
    private volatile Object name_ = "";
    public static final int FILTER_FIELD_NUMBER = 1;
    private volatile Object filter_ = "";
    public static final int PAGE_SIZE_FIELD_NUMBER = 2;
    private int pageSize_ = 0;
    public static final int PAGE_TOKEN_FIELD_NUMBER = 3;
    private volatile Object pageToken_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final ListOperationsRequest DEFAULT_INSTANCE = new ListOperationsRequest();
    private static final Parser<ListOperationsRequest> PARSER = new AbstractParser<ListOperationsRequest>(){

        @Override
        public ListOperationsRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = ListOperationsRequest.newBuilder();
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

    private ListOperationsRequest(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private ListOperationsRequest() {
        this.name_ = "";
        this.filter_ = "";
        this.pageToken_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new ListOperationsRequest();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return OperationsProto.internal_static_google_longrunning_ListOperationsRequest_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return OperationsProto.internal_static_google_longrunning_ListOperationsRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(ListOperationsRequest.class, Builder.class);
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
    public String getFilter() {
        Object ref = this.filter_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.filter_ = s2;
        return s2;
    }

    @Override
    public ByteString getFilterBytes() {
        Object ref = this.filter_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.filter_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public int getPageSize() {
        return this.pageSize_;
    }

    @Override
    public String getPageToken() {
        Object ref = this.pageToken_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.pageToken_ = s2;
        return s2;
    }

    @Override
    public ByteString getPageTokenBytes() {
        Object ref = this.pageToken_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.pageToken_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.filter_)) {
            GeneratedMessageV3.writeString(output, 1, this.filter_);
        }
        if (this.pageSize_ != 0) {
            output.writeInt32(2, this.pageSize_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.pageToken_)) {
            GeneratedMessageV3.writeString(output, 3, this.pageToken_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            GeneratedMessageV3.writeString(output, 4, this.name_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.filter_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.filter_);
        }
        if (this.pageSize_ != 0) {
            size += CodedOutputStream.computeInt32Size(2, this.pageSize_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.pageToken_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.pageToken_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            size += GeneratedMessageV3.computeStringSize(4, this.name_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ListOperationsRequest)) {
            return super.equals(obj);
        }
        ListOperationsRequest other = (ListOperationsRequest)obj;
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (!this.getFilter().equals(other.getFilter())) {
            return false;
        }
        if (this.getPageSize() != other.getPageSize()) {
            return false;
        }
        if (!this.getPageToken().equals(other.getPageToken())) {
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
        hash = 19 * hash + ListOperationsRequest.getDescriptor().hashCode();
        hash = 37 * hash + 4;
        hash = 53 * hash + this.getName().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getFilter().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getPageSize();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getPageToken().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static ListOperationsRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ListOperationsRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ListOperationsRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ListOperationsRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ListOperationsRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ListOperationsRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ListOperationsRequest parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ListOperationsRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ListOperationsRequest parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ListOperationsRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ListOperationsRequest parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ListOperationsRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return ListOperationsRequest.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ListOperationsRequest prototype) {
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

    public static ListOperationsRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ListOperationsRequest> parser() {
        return PARSER;
    }

    public Parser<ListOperationsRequest> getParserForType() {
        return PARSER;
    }

    @Override
    public ListOperationsRequest getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ListOperationsRequestOrBuilder {
        private int bitField0_;
        private Object name_ = "";
        private Object filter_ = "";
        private int pageSize_;
        private Object pageToken_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return OperationsProto.internal_static_google_longrunning_ListOperationsRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return OperationsProto.internal_static_google_longrunning_ListOperationsRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(ListOperationsRequest.class, Builder.class);
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
            this.filter_ = "";
            this.pageSize_ = 0;
            this.pageToken_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return OperationsProto.internal_static_google_longrunning_ListOperationsRequest_descriptor;
        }

        @Override
        public ListOperationsRequest getDefaultInstanceForType() {
            return ListOperationsRequest.getDefaultInstance();
        }

        @Override
        public ListOperationsRequest build() {
            ListOperationsRequest result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ListOperationsRequest buildPartial() {
            ListOperationsRequest result = new ListOperationsRequest(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(ListOperationsRequest result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.name_ = this.name_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.filter_ = this.filter_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.pageSize_ = this.pageSize_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.pageToken_ = this.pageToken_;
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
            if (other instanceof ListOperationsRequest) {
                return this.mergeFrom((ListOperationsRequest)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ListOperationsRequest other) {
            if (other == ListOperationsRequest.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getFilter().isEmpty()) {
                this.filter_ = other.filter_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (other.getPageSize() != 0) {
                this.setPageSize(other.getPageSize());
            }
            if (!other.getPageToken().isEmpty()) {
                this.pageToken_ = other.pageToken_;
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
                            this.filter_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block12;
                        }
                        case 16: {
                            this.pageSize_ = input.readInt32();
                            this.bitField0_ |= 4;
                            continue block12;
                        }
                        case 26: {
                            this.pageToken_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 8;
                            continue block12;
                        }
                        case 34: {
                            this.name_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
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
            this.name_ = ListOperationsRequest.getDefaultInstance().getName();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ListOperationsRequest.checkByteStringIsUtf8(value);
            this.name_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getFilter() {
            Object ref = this.filter_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.filter_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getFilterBytes() {
            Object ref = this.filter_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.filter_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setFilter(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.filter_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearFilter() {
            this.filter_ = ListOperationsRequest.getDefaultInstance().getFilter();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setFilterBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ListOperationsRequest.checkByteStringIsUtf8(value);
            this.filter_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public int getPageSize() {
            return this.pageSize_;
        }

        public Builder setPageSize(int value) {
            this.pageSize_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearPageSize() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.pageSize_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public String getPageToken() {
            Object ref = this.pageToken_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.pageToken_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getPageTokenBytes() {
            Object ref = this.pageToken_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.pageToken_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setPageToken(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.pageToken_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearPageToken() {
            this.pageToken_ = ListOperationsRequest.getDefaultInstance().getPageToken();
            this.bitField0_ &= 0xFFFFFFF7;
            this.onChanged();
            return this;
        }

        public Builder setPageTokenBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ListOperationsRequest.checkByteStringIsUtf8(value);
            this.pageToken_ = value;
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

