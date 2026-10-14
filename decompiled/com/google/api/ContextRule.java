/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ContextProto;
import com.google.api.ContextRuleOrBuilder;
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
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ContextRule
extends GeneratedMessageV3
implements ContextRuleOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int SELECTOR_FIELD_NUMBER = 1;
    private volatile Object selector_ = "";
    public static final int REQUESTED_FIELD_NUMBER = 2;
    private LazyStringArrayList requested_ = LazyStringArrayList.emptyList();
    public static final int PROVIDED_FIELD_NUMBER = 3;
    private LazyStringArrayList provided_ = LazyStringArrayList.emptyList();
    public static final int ALLOWED_REQUEST_EXTENSIONS_FIELD_NUMBER = 4;
    private LazyStringArrayList allowedRequestExtensions_ = LazyStringArrayList.emptyList();
    public static final int ALLOWED_RESPONSE_EXTENSIONS_FIELD_NUMBER = 5;
    private LazyStringArrayList allowedResponseExtensions_ = LazyStringArrayList.emptyList();
    private byte memoizedIsInitialized = (byte)-1;
    private static final ContextRule DEFAULT_INSTANCE = new ContextRule();
    private static final Parser<ContextRule> PARSER = new AbstractParser<ContextRule>(){

        @Override
        public ContextRule parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = ContextRule.newBuilder();
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

    private ContextRule(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private ContextRule() {
        this.selector_ = "";
        this.requested_ = LazyStringArrayList.emptyList();
        this.provided_ = LazyStringArrayList.emptyList();
        this.allowedRequestExtensions_ = LazyStringArrayList.emptyList();
        this.allowedResponseExtensions_ = LazyStringArrayList.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new ContextRule();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ContextProto.internal_static_google_api_ContextRule_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ContextProto.internal_static_google_api_ContextRule_fieldAccessorTable.ensureFieldAccessorsInitialized(ContextRule.class, Builder.class);
    }

    @Override
    public String getSelector() {
        Object ref = this.selector_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.selector_ = s2;
        return s2;
    }

    @Override
    public ByteString getSelectorBytes() {
        Object ref = this.selector_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.selector_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    public ProtocolStringList getRequestedList() {
        return this.requested_;
    }

    @Override
    public int getRequestedCount() {
        return this.requested_.size();
    }

    @Override
    public String getRequested(int index) {
        return this.requested_.get(index);
    }

    @Override
    public ByteString getRequestedBytes(int index) {
        return this.requested_.getByteString(index);
    }

    public ProtocolStringList getProvidedList() {
        return this.provided_;
    }

    @Override
    public int getProvidedCount() {
        return this.provided_.size();
    }

    @Override
    public String getProvided(int index) {
        return this.provided_.get(index);
    }

    @Override
    public ByteString getProvidedBytes(int index) {
        return this.provided_.getByteString(index);
    }

    public ProtocolStringList getAllowedRequestExtensionsList() {
        return this.allowedRequestExtensions_;
    }

    @Override
    public int getAllowedRequestExtensionsCount() {
        return this.allowedRequestExtensions_.size();
    }

    @Override
    public String getAllowedRequestExtensions(int index) {
        return this.allowedRequestExtensions_.get(index);
    }

    @Override
    public ByteString getAllowedRequestExtensionsBytes(int index) {
        return this.allowedRequestExtensions_.getByteString(index);
    }

    public ProtocolStringList getAllowedResponseExtensionsList() {
        return this.allowedResponseExtensions_;
    }

    @Override
    public int getAllowedResponseExtensionsCount() {
        return this.allowedResponseExtensions_.size();
    }

    @Override
    public String getAllowedResponseExtensions(int index) {
        return this.allowedResponseExtensions_.get(index);
    }

    @Override
    public ByteString getAllowedResponseExtensionsBytes(int index) {
        return this.allowedResponseExtensions_.getByteString(index);
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
        int i;
        if (!GeneratedMessageV3.isStringEmpty(this.selector_)) {
            GeneratedMessageV3.writeString(output, 1, this.selector_);
        }
        for (i = 0; i < this.requested_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 2, this.requested_.getRaw(i));
        }
        for (i = 0; i < this.provided_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 3, this.provided_.getRaw(i));
        }
        for (i = 0; i < this.allowedRequestExtensions_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 4, this.allowedRequestExtensions_.getRaw(i));
        }
        for (i = 0; i < this.allowedResponseExtensions_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 5, this.allowedResponseExtensions_.getRaw(i));
        }
        this.getUnknownFields().writeTo(output);
    }

    @Override
    public int getSerializedSize() {
        int i;
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        if (!GeneratedMessageV3.isStringEmpty(this.selector_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.selector_);
        }
        int dataSize = 0;
        for (i = 0; i < this.requested_.size(); ++i) {
            dataSize += ContextRule.computeStringSizeNoTag(this.requested_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getRequestedList().size();
        dataSize = 0;
        for (i = 0; i < this.provided_.size(); ++i) {
            dataSize += ContextRule.computeStringSizeNoTag(this.provided_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getProvidedList().size();
        dataSize = 0;
        for (i = 0; i < this.allowedRequestExtensions_.size(); ++i) {
            dataSize += ContextRule.computeStringSizeNoTag(this.allowedRequestExtensions_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getAllowedRequestExtensionsList().size();
        dataSize = 0;
        for (i = 0; i < this.allowedResponseExtensions_.size(); ++i) {
            dataSize += ContextRule.computeStringSizeNoTag(this.allowedResponseExtensions_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getAllowedResponseExtensionsList().size();
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ContextRule)) {
            return super.equals(obj);
        }
        ContextRule other = (ContextRule)obj;
        if (!this.getSelector().equals(other.getSelector())) {
            return false;
        }
        if (!this.getRequestedList().equals(other.getRequestedList())) {
            return false;
        }
        if (!this.getProvidedList().equals(other.getProvidedList())) {
            return false;
        }
        if (!this.getAllowedRequestExtensionsList().equals(other.getAllowedRequestExtensionsList())) {
            return false;
        }
        if (!this.getAllowedResponseExtensionsList().equals(other.getAllowedResponseExtensionsList())) {
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
        hash = 19 * hash + ContextRule.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getSelector().hashCode();
        if (this.getRequestedCount() > 0) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getRequestedList().hashCode();
        }
        if (this.getProvidedCount() > 0) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getProvidedList().hashCode();
        }
        if (this.getAllowedRequestExtensionsCount() > 0) {
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getAllowedRequestExtensionsList().hashCode();
        }
        if (this.getAllowedResponseExtensionsCount() > 0) {
            hash = 37 * hash + 5;
            hash = 53 * hash + this.getAllowedResponseExtensionsList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static ContextRule parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ContextRule parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ContextRule parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ContextRule parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ContextRule parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ContextRule parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ContextRule parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ContextRule parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ContextRule parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ContextRule parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ContextRule parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ContextRule parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return ContextRule.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ContextRule prototype) {
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

    public static ContextRule getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ContextRule> parser() {
        return PARSER;
    }

    public Parser<ContextRule> getParserForType() {
        return PARSER;
    }

    @Override
    public ContextRule getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ContextRuleOrBuilder {
        private int bitField0_;
        private Object selector_ = "";
        private LazyStringArrayList requested_ = LazyStringArrayList.emptyList();
        private LazyStringArrayList provided_ = LazyStringArrayList.emptyList();
        private LazyStringArrayList allowedRequestExtensions_ = LazyStringArrayList.emptyList();
        private LazyStringArrayList allowedResponseExtensions_ = LazyStringArrayList.emptyList();

        public static final Descriptors.Descriptor getDescriptor() {
            return ContextProto.internal_static_google_api_ContextRule_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ContextProto.internal_static_google_api_ContextRule_fieldAccessorTable.ensureFieldAccessorsInitialized(ContextRule.class, Builder.class);
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
            this.selector_ = "";
            this.requested_ = LazyStringArrayList.emptyList();
            this.provided_ = LazyStringArrayList.emptyList();
            this.allowedRequestExtensions_ = LazyStringArrayList.emptyList();
            this.allowedResponseExtensions_ = LazyStringArrayList.emptyList();
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ContextProto.internal_static_google_api_ContextRule_descriptor;
        }

        @Override
        public ContextRule getDefaultInstanceForType() {
            return ContextRule.getDefaultInstance();
        }

        @Override
        public ContextRule build() {
            ContextRule result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ContextRule buildPartial() {
            ContextRule result = new ContextRule(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(ContextRule result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.selector_ = this.selector_;
            }
            if ((from_bitField0_ & 2) != 0) {
                this.requested_.makeImmutable();
                result.requested_ = this.requested_;
            }
            if ((from_bitField0_ & 4) != 0) {
                this.provided_.makeImmutable();
                result.provided_ = this.provided_;
            }
            if ((from_bitField0_ & 8) != 0) {
                this.allowedRequestExtensions_.makeImmutable();
                result.allowedRequestExtensions_ = this.allowedRequestExtensions_;
            }
            if ((from_bitField0_ & 0x10) != 0) {
                this.allowedResponseExtensions_.makeImmutable();
                result.allowedResponseExtensions_ = this.allowedResponseExtensions_;
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
            if (other instanceof ContextRule) {
                return this.mergeFrom((ContextRule)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ContextRule other) {
            if (other == ContextRule.getDefaultInstance()) {
                return this;
            }
            if (!other.getSelector().isEmpty()) {
                this.selector_ = other.selector_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.requested_.isEmpty()) {
                if (this.requested_.isEmpty()) {
                    this.requested_ = other.requested_;
                    this.bitField0_ |= 2;
                } else {
                    this.ensureRequestedIsMutable();
                    this.requested_.addAll(other.requested_);
                }
                this.onChanged();
            }
            if (!other.provided_.isEmpty()) {
                if (this.provided_.isEmpty()) {
                    this.provided_ = other.provided_;
                    this.bitField0_ |= 4;
                } else {
                    this.ensureProvidedIsMutable();
                    this.provided_.addAll(other.provided_);
                }
                this.onChanged();
            }
            if (!other.allowedRequestExtensions_.isEmpty()) {
                if (this.allowedRequestExtensions_.isEmpty()) {
                    this.allowedRequestExtensions_ = other.allowedRequestExtensions_;
                    this.bitField0_ |= 8;
                } else {
                    this.ensureAllowedRequestExtensionsIsMutable();
                    this.allowedRequestExtensions_.addAll(other.allowedRequestExtensions_);
                }
                this.onChanged();
            }
            if (!other.allowedResponseExtensions_.isEmpty()) {
                if (this.allowedResponseExtensions_.isEmpty()) {
                    this.allowedResponseExtensions_ = other.allowedResponseExtensions_;
                    this.bitField0_ |= 0x10;
                } else {
                    this.ensureAllowedResponseExtensionsIsMutable();
                    this.allowedResponseExtensions_.addAll(other.allowedResponseExtensions_);
                }
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
                            this.selector_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block13;
                        }
                        case 18: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensureRequestedIsMutable();
                            this.requested_.add(s2);
                            continue block13;
                        }
                        case 26: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensureProvidedIsMutable();
                            this.provided_.add(s2);
                            continue block13;
                        }
                        case 34: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensureAllowedRequestExtensionsIsMutable();
                            this.allowedRequestExtensions_.add(s2);
                            continue block13;
                        }
                        case 42: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensureAllowedResponseExtensionsIsMutable();
                            this.allowedResponseExtensions_.add(s2);
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
        public String getSelector() {
            Object ref = this.selector_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.selector_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getSelectorBytes() {
            Object ref = this.selector_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.selector_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setSelector(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.selector_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearSelector() {
            this.selector_ = ContextRule.getDefaultInstance().getSelector();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setSelectorBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ContextRule.checkByteStringIsUtf8(value);
            this.selector_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        private void ensureRequestedIsMutable() {
            if (!this.requested_.isModifiable()) {
                this.requested_ = new LazyStringArrayList(this.requested_);
            }
            this.bitField0_ |= 2;
        }

        public ProtocolStringList getRequestedList() {
            this.requested_.makeImmutable();
            return this.requested_;
        }

        @Override
        public int getRequestedCount() {
            return this.requested_.size();
        }

        @Override
        public String getRequested(int index) {
            return this.requested_.get(index);
        }

        @Override
        public ByteString getRequestedBytes(int index) {
            return this.requested_.getByteString(index);
        }

        public Builder setRequested(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureRequestedIsMutable();
            this.requested_.set(index, value);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder addRequested(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureRequestedIsMutable();
            this.requested_.add(value);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder addAllRequested(Iterable<String> values2) {
            this.ensureRequestedIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.requested_);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearRequested() {
            this.requested_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder addRequestedBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ContextRule.checkByteStringIsUtf8(value);
            this.ensureRequestedIsMutable();
            this.requested_.add(value);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        private void ensureProvidedIsMutable() {
            if (!this.provided_.isModifiable()) {
                this.provided_ = new LazyStringArrayList(this.provided_);
            }
            this.bitField0_ |= 4;
        }

        public ProtocolStringList getProvidedList() {
            this.provided_.makeImmutable();
            return this.provided_;
        }

        @Override
        public int getProvidedCount() {
            return this.provided_.size();
        }

        @Override
        public String getProvided(int index) {
            return this.provided_.get(index);
        }

        @Override
        public ByteString getProvidedBytes(int index) {
            return this.provided_.getByteString(index);
        }

        public Builder setProvided(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureProvidedIsMutable();
            this.provided_.set(index, value);
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder addProvided(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureProvidedIsMutable();
            this.provided_.add(value);
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder addAllProvided(Iterable<String> values2) {
            this.ensureProvidedIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.provided_);
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearProvided() {
            this.provided_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder addProvidedBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ContextRule.checkByteStringIsUtf8(value);
            this.ensureProvidedIsMutable();
            this.provided_.add(value);
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        private void ensureAllowedRequestExtensionsIsMutable() {
            if (!this.allowedRequestExtensions_.isModifiable()) {
                this.allowedRequestExtensions_ = new LazyStringArrayList(this.allowedRequestExtensions_);
            }
            this.bitField0_ |= 8;
        }

        public ProtocolStringList getAllowedRequestExtensionsList() {
            this.allowedRequestExtensions_.makeImmutable();
            return this.allowedRequestExtensions_;
        }

        @Override
        public int getAllowedRequestExtensionsCount() {
            return this.allowedRequestExtensions_.size();
        }

        @Override
        public String getAllowedRequestExtensions(int index) {
            return this.allowedRequestExtensions_.get(index);
        }

        @Override
        public ByteString getAllowedRequestExtensionsBytes(int index) {
            return this.allowedRequestExtensions_.getByteString(index);
        }

        public Builder setAllowedRequestExtensions(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureAllowedRequestExtensionsIsMutable();
            this.allowedRequestExtensions_.set(index, value);
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder addAllowedRequestExtensions(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureAllowedRequestExtensionsIsMutable();
            this.allowedRequestExtensions_.add(value);
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder addAllAllowedRequestExtensions(Iterable<String> values2) {
            this.ensureAllowedRequestExtensionsIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.allowedRequestExtensions_);
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearAllowedRequestExtensions() {
            this.allowedRequestExtensions_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFFF7;
            this.onChanged();
            return this;
        }

        public Builder addAllowedRequestExtensionsBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ContextRule.checkByteStringIsUtf8(value);
            this.ensureAllowedRequestExtensionsIsMutable();
            this.allowedRequestExtensions_.add(value);
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        private void ensureAllowedResponseExtensionsIsMutable() {
            if (!this.allowedResponseExtensions_.isModifiable()) {
                this.allowedResponseExtensions_ = new LazyStringArrayList(this.allowedResponseExtensions_);
            }
            this.bitField0_ |= 0x10;
        }

        public ProtocolStringList getAllowedResponseExtensionsList() {
            this.allowedResponseExtensions_.makeImmutable();
            return this.allowedResponseExtensions_;
        }

        @Override
        public int getAllowedResponseExtensionsCount() {
            return this.allowedResponseExtensions_.size();
        }

        @Override
        public String getAllowedResponseExtensions(int index) {
            return this.allowedResponseExtensions_.get(index);
        }

        @Override
        public ByteString getAllowedResponseExtensionsBytes(int index) {
            return this.allowedResponseExtensions_.getByteString(index);
        }

        public Builder setAllowedResponseExtensions(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureAllowedResponseExtensionsIsMutable();
            this.allowedResponseExtensions_.set(index, value);
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder addAllowedResponseExtensions(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureAllowedResponseExtensionsIsMutable();
            this.allowedResponseExtensions_.add(value);
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder addAllAllowedResponseExtensions(Iterable<String> values2) {
            this.ensureAllowedResponseExtensionsIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.allowedResponseExtensions_);
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearAllowedResponseExtensions() {
            this.allowedResponseExtensions_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFFEF;
            this.onChanged();
            return this;
        }

        public Builder addAllowedResponseExtensionsBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ContextRule.checkByteStringIsUtf8(value);
            this.ensureAllowedResponseExtensionsIsMutable();
            this.allowedResponseExtensions_.add(value);
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

