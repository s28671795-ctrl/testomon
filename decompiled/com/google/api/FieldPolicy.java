/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.FieldPolicyOrBuilder;
import com.google.api.PolicyProto;
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

public final class FieldPolicy
extends GeneratedMessageV3
implements FieldPolicyOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int SELECTOR_FIELD_NUMBER = 1;
    private volatile Object selector_ = "";
    public static final int RESOURCE_PERMISSION_FIELD_NUMBER = 2;
    private volatile Object resourcePermission_ = "";
    public static final int RESOURCE_TYPE_FIELD_NUMBER = 3;
    private volatile Object resourceType_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final FieldPolicy DEFAULT_INSTANCE = new FieldPolicy();
    private static final Parser<FieldPolicy> PARSER = new AbstractParser<FieldPolicy>(){

        @Override
        public FieldPolicy parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = FieldPolicy.newBuilder();
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

    private FieldPolicy(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private FieldPolicy() {
        this.selector_ = "";
        this.resourcePermission_ = "";
        this.resourceType_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new FieldPolicy();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return PolicyProto.internal_static_google_api_FieldPolicy_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return PolicyProto.internal_static_google_api_FieldPolicy_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldPolicy.class, Builder.class);
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

    @Override
    public String getResourcePermission() {
        Object ref = this.resourcePermission_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.resourcePermission_ = s2;
        return s2;
    }

    @Override
    public ByteString getResourcePermissionBytes() {
        Object ref = this.resourcePermission_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.resourcePermission_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getResourceType() {
        Object ref = this.resourceType_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.resourceType_ = s2;
        return s2;
    }

    @Override
    public ByteString getResourceTypeBytes() {
        Object ref = this.resourceType_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.resourceType_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.selector_)) {
            GeneratedMessageV3.writeString(output, 1, this.selector_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.resourcePermission_)) {
            GeneratedMessageV3.writeString(output, 2, this.resourcePermission_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.resourceType_)) {
            GeneratedMessageV3.writeString(output, 3, this.resourceType_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.selector_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.selector_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.resourcePermission_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.resourcePermission_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.resourceType_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.resourceType_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FieldPolicy)) {
            return super.equals(obj);
        }
        FieldPolicy other = (FieldPolicy)obj;
        if (!this.getSelector().equals(other.getSelector())) {
            return false;
        }
        if (!this.getResourcePermission().equals(other.getResourcePermission())) {
            return false;
        }
        if (!this.getResourceType().equals(other.getResourceType())) {
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
        hash = 19 * hash + FieldPolicy.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getSelector().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getResourcePermission().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getResourceType().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static FieldPolicy parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static FieldPolicy parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static FieldPolicy parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static FieldPolicy parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static FieldPolicy parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static FieldPolicy parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static FieldPolicy parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static FieldPolicy parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static FieldPolicy parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static FieldPolicy parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static FieldPolicy parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static FieldPolicy parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return FieldPolicy.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(FieldPolicy prototype) {
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

    public static FieldPolicy getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<FieldPolicy> parser() {
        return PARSER;
    }

    public Parser<FieldPolicy> getParserForType() {
        return PARSER;
    }

    @Override
    public FieldPolicy getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements FieldPolicyOrBuilder {
        private int bitField0_;
        private Object selector_ = "";
        private Object resourcePermission_ = "";
        private Object resourceType_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return PolicyProto.internal_static_google_api_FieldPolicy_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return PolicyProto.internal_static_google_api_FieldPolicy_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldPolicy.class, Builder.class);
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
            this.resourcePermission_ = "";
            this.resourceType_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return PolicyProto.internal_static_google_api_FieldPolicy_descriptor;
        }

        @Override
        public FieldPolicy getDefaultInstanceForType() {
            return FieldPolicy.getDefaultInstance();
        }

        @Override
        public FieldPolicy build() {
            FieldPolicy result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public FieldPolicy buildPartial() {
            FieldPolicy result = new FieldPolicy(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(FieldPolicy result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.selector_ = this.selector_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.resourcePermission_ = this.resourcePermission_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.resourceType_ = this.resourceType_;
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
            if (other instanceof FieldPolicy) {
                return this.mergeFrom((FieldPolicy)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(FieldPolicy other) {
            if (other == FieldPolicy.getDefaultInstance()) {
                return this;
            }
            if (!other.getSelector().isEmpty()) {
                this.selector_ = other.selector_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getResourcePermission().isEmpty()) {
                this.resourcePermission_ = other.resourcePermission_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (!other.getResourceType().isEmpty()) {
                this.resourceType_ = other.resourceType_;
                this.bitField0_ |= 4;
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
                block11: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block11;
                        }
                        case 10: {
                            this.selector_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block11;
                        }
                        case 18: {
                            this.resourcePermission_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block11;
                        }
                        case 26: {
                            this.resourceType_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block11;
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
            this.selector_ = FieldPolicy.getDefaultInstance().getSelector();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setSelectorBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            FieldPolicy.checkByteStringIsUtf8(value);
            this.selector_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getResourcePermission() {
            Object ref = this.resourcePermission_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.resourcePermission_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getResourcePermissionBytes() {
            Object ref = this.resourcePermission_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.resourcePermission_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setResourcePermission(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.resourcePermission_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearResourcePermission() {
            this.resourcePermission_ = FieldPolicy.getDefaultInstance().getResourcePermission();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setResourcePermissionBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            FieldPolicy.checkByteStringIsUtf8(value);
            this.resourcePermission_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public String getResourceType() {
            Object ref = this.resourceType_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.resourceType_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getResourceTypeBytes() {
            Object ref = this.resourceType_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.resourceType_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setResourceType(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.resourceType_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearResourceType() {
            this.resourceType_ = FieldPolicy.getDefaultInstance().getResourceType();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setResourceTypeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            FieldPolicy.checkByteStringIsUtf8(value);
            this.resourceType_ = value;
            this.bitField0_ |= 4;
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

