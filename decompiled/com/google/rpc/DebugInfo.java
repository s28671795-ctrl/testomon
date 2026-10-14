/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.rpc.DebugInfoOrBuilder;
import com.google.rpc.ErrorDetailsProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class DebugInfo
extends GeneratedMessageV3
implements DebugInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int STACK_ENTRIES_FIELD_NUMBER = 1;
    private LazyStringArrayList stackEntries_ = LazyStringArrayList.emptyList();
    public static final int DETAIL_FIELD_NUMBER = 2;
    private volatile Object detail_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final DebugInfo DEFAULT_INSTANCE = new DebugInfo();
    private static final Parser<DebugInfo> PARSER = new AbstractParser<DebugInfo>(){

        @Override
        public DebugInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = DebugInfo.newBuilder();
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

    private DebugInfo(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private DebugInfo() {
        this.stackEntries_ = LazyStringArrayList.emptyList();
        this.detail_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new DebugInfo();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ErrorDetailsProto.internal_static_google_rpc_DebugInfo_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ErrorDetailsProto.internal_static_google_rpc_DebugInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(DebugInfo.class, Builder.class);
    }

    public ProtocolStringList getStackEntriesList() {
        return this.stackEntries_;
    }

    @Override
    public int getStackEntriesCount() {
        return this.stackEntries_.size();
    }

    @Override
    public String getStackEntries(int index) {
        return this.stackEntries_.get(index);
    }

    @Override
    public ByteString getStackEntriesBytes(int index) {
        return this.stackEntries_.getByteString(index);
    }

    @Override
    public String getDetail() {
        Object ref = this.detail_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.detail_ = s2;
        return s2;
    }

    @Override
    public ByteString getDetailBytes() {
        Object ref = this.detail_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.detail_ = b;
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
        for (int i = 0; i < this.stackEntries_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 1, this.stackEntries_.getRaw(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.detail_)) {
            GeneratedMessageV3.writeString(output, 2, this.detail_);
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
        int dataSize = 0;
        for (int i = 0; i < this.stackEntries_.size(); ++i) {
            dataSize += DebugInfo.computeStringSizeNoTag(this.stackEntries_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getStackEntriesList().size();
        if (!GeneratedMessageV3.isStringEmpty(this.detail_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.detail_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DebugInfo)) {
            return super.equals(obj);
        }
        DebugInfo other = (DebugInfo)obj;
        if (!this.getStackEntriesList().equals(other.getStackEntriesList())) {
            return false;
        }
        if (!this.getDetail().equals(other.getDetail())) {
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
        hash = 19 * hash + DebugInfo.getDescriptor().hashCode();
        if (this.getStackEntriesCount() > 0) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getStackEntriesList().hashCode();
        }
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getDetail().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static DebugInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static DebugInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static DebugInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static DebugInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static DebugInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static DebugInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static DebugInfo parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static DebugInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static DebugInfo parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static DebugInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static DebugInfo parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static DebugInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return DebugInfo.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(DebugInfo prototype) {
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

    public static DebugInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<DebugInfo> parser() {
        return PARSER;
    }

    public Parser<DebugInfo> getParserForType() {
        return PARSER;
    }

    @Override
    public DebugInfo getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements DebugInfoOrBuilder {
        private int bitField0_;
        private LazyStringArrayList stackEntries_ = LazyStringArrayList.emptyList();
        private Object detail_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return ErrorDetailsProto.internal_static_google_rpc_DebugInfo_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ErrorDetailsProto.internal_static_google_rpc_DebugInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(DebugInfo.class, Builder.class);
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
            this.stackEntries_ = LazyStringArrayList.emptyList();
            this.detail_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ErrorDetailsProto.internal_static_google_rpc_DebugInfo_descriptor;
        }

        @Override
        public DebugInfo getDefaultInstanceForType() {
            return DebugInfo.getDefaultInstance();
        }

        @Override
        public DebugInfo build() {
            DebugInfo result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public DebugInfo buildPartial() {
            DebugInfo result = new DebugInfo(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(DebugInfo result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                this.stackEntries_.makeImmutable();
                result.stackEntries_ = this.stackEntries_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.detail_ = this.detail_;
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
            if (other instanceof DebugInfo) {
                return this.mergeFrom((DebugInfo)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(DebugInfo other) {
            if (other == DebugInfo.getDefaultInstance()) {
                return this;
            }
            if (!other.stackEntries_.isEmpty()) {
                if (this.stackEntries_.isEmpty()) {
                    this.stackEntries_ = other.stackEntries_;
                    this.bitField0_ |= 1;
                } else {
                    this.ensureStackEntriesIsMutable();
                    this.stackEntries_.addAll(other.stackEntries_);
                }
                this.onChanged();
            }
            if (!other.getDetail().isEmpty()) {
                this.detail_ = other.detail_;
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
                            String s2 = input.readStringRequireUtf8();
                            this.ensureStackEntriesIsMutable();
                            this.stackEntries_.add(s2);
                            continue block10;
                        }
                        case 18: {
                            this.detail_ = input.readStringRequireUtf8();
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

        private void ensureStackEntriesIsMutable() {
            if (!this.stackEntries_.isModifiable()) {
                this.stackEntries_ = new LazyStringArrayList(this.stackEntries_);
            }
            this.bitField0_ |= 1;
        }

        public ProtocolStringList getStackEntriesList() {
            this.stackEntries_.makeImmutable();
            return this.stackEntries_;
        }

        @Override
        public int getStackEntriesCount() {
            return this.stackEntries_.size();
        }

        @Override
        public String getStackEntries(int index) {
            return this.stackEntries_.get(index);
        }

        @Override
        public ByteString getStackEntriesBytes(int index) {
            return this.stackEntries_.getByteString(index);
        }

        public Builder setStackEntries(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureStackEntriesIsMutable();
            this.stackEntries_.set(index, value);
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder addStackEntries(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureStackEntriesIsMutable();
            this.stackEntries_.add(value);
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder addAllStackEntries(Iterable<String> values2) {
            this.ensureStackEntriesIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.stackEntries_);
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearStackEntries() {
            this.stackEntries_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder addStackEntriesBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            DebugInfo.checkByteStringIsUtf8(value);
            this.ensureStackEntriesIsMutable();
            this.stackEntries_.add(value);
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getDetail() {
            Object ref = this.detail_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.detail_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getDetailBytes() {
            Object ref = this.detail_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.detail_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setDetail(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.detail_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearDetail() {
            this.detail_ = DebugInfo.getDefaultInstance().getDetail();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setDetailBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            DebugInfo.checkByteStringIsUtf8(value);
            this.detail_ = value;
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

