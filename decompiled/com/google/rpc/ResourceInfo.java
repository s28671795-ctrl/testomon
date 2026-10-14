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
import com.google.rpc.ResourceInfoOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ResourceInfo
extends GeneratedMessageV3
implements ResourceInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int RESOURCE_TYPE_FIELD_NUMBER = 1;
    private volatile Object resourceType_ = "";
    public static final int RESOURCE_NAME_FIELD_NUMBER = 2;
    private volatile Object resourceName_ = "";
    public static final int OWNER_FIELD_NUMBER = 3;
    private volatile Object owner_ = "";
    public static final int DESCRIPTION_FIELD_NUMBER = 4;
    private volatile Object description_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final ResourceInfo DEFAULT_INSTANCE = new ResourceInfo();
    private static final Parser<ResourceInfo> PARSER = new AbstractParser<ResourceInfo>(){

        @Override
        public ResourceInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = ResourceInfo.newBuilder();
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

    private ResourceInfo(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private ResourceInfo() {
        this.resourceType_ = "";
        this.resourceName_ = "";
        this.owner_ = "";
        this.description_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new ResourceInfo();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ErrorDetailsProto.internal_static_google_rpc_ResourceInfo_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ErrorDetailsProto.internal_static_google_rpc_ResourceInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceInfo.class, Builder.class);
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
    public String getResourceName() {
        Object ref = this.resourceName_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.resourceName_ = s2;
        return s2;
    }

    @Override
    public ByteString getResourceNameBytes() {
        Object ref = this.resourceName_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.resourceName_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getOwner() {
        Object ref = this.owner_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.owner_ = s2;
        return s2;
    }

    @Override
    public ByteString getOwnerBytes() {
        Object ref = this.owner_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.owner_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getDescription() {
        Object ref = this.description_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.description_ = s2;
        return s2;
    }

    @Override
    public ByteString getDescriptionBytes() {
        Object ref = this.description_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.description_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.resourceType_)) {
            GeneratedMessageV3.writeString(output, 1, this.resourceType_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.resourceName_)) {
            GeneratedMessageV3.writeString(output, 2, this.resourceName_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.owner_)) {
            GeneratedMessageV3.writeString(output, 3, this.owner_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.description_)) {
            GeneratedMessageV3.writeString(output, 4, this.description_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.resourceType_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.resourceType_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.resourceName_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.resourceName_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.owner_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.owner_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.description_)) {
            size += GeneratedMessageV3.computeStringSize(4, this.description_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ResourceInfo)) {
            return super.equals(obj);
        }
        ResourceInfo other = (ResourceInfo)obj;
        if (!this.getResourceType().equals(other.getResourceType())) {
            return false;
        }
        if (!this.getResourceName().equals(other.getResourceName())) {
            return false;
        }
        if (!this.getOwner().equals(other.getOwner())) {
            return false;
        }
        if (!this.getDescription().equals(other.getDescription())) {
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
        hash = 19 * hash + ResourceInfo.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getResourceType().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getResourceName().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getOwner().hashCode();
        hash = 37 * hash + 4;
        hash = 53 * hash + this.getDescription().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static ResourceInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ResourceInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ResourceInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ResourceInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ResourceInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ResourceInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ResourceInfo parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ResourceInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ResourceInfo parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ResourceInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ResourceInfo parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ResourceInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return ResourceInfo.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ResourceInfo prototype) {
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

    public static ResourceInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ResourceInfo> parser() {
        return PARSER;
    }

    public Parser<ResourceInfo> getParserForType() {
        return PARSER;
    }

    @Override
    public ResourceInfo getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ResourceInfoOrBuilder {
        private int bitField0_;
        private Object resourceType_ = "";
        private Object resourceName_ = "";
        private Object owner_ = "";
        private Object description_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return ErrorDetailsProto.internal_static_google_rpc_ResourceInfo_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ErrorDetailsProto.internal_static_google_rpc_ResourceInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceInfo.class, Builder.class);
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
            this.resourceType_ = "";
            this.resourceName_ = "";
            this.owner_ = "";
            this.description_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ErrorDetailsProto.internal_static_google_rpc_ResourceInfo_descriptor;
        }

        @Override
        public ResourceInfo getDefaultInstanceForType() {
            return ResourceInfo.getDefaultInstance();
        }

        @Override
        public ResourceInfo build() {
            ResourceInfo result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ResourceInfo buildPartial() {
            ResourceInfo result = new ResourceInfo(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(ResourceInfo result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.resourceType_ = this.resourceType_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.resourceName_ = this.resourceName_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.owner_ = this.owner_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.description_ = this.description_;
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
            if (other instanceof ResourceInfo) {
                return this.mergeFrom((ResourceInfo)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ResourceInfo other) {
            if (other == ResourceInfo.getDefaultInstance()) {
                return this;
            }
            if (!other.getResourceType().isEmpty()) {
                this.resourceType_ = other.resourceType_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getResourceName().isEmpty()) {
                this.resourceName_ = other.resourceName_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (!other.getOwner().isEmpty()) {
                this.owner_ = other.owner_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            if (!other.getDescription().isEmpty()) {
                this.description_ = other.description_;
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
                            this.resourceType_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block12;
                        }
                        case 18: {
                            this.resourceName_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block12;
                        }
                        case 26: {
                            this.owner_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block12;
                        }
                        case 34: {
                            this.description_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 8;
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
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearResourceType() {
            this.resourceType_ = ResourceInfo.getDefaultInstance().getResourceType();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setResourceTypeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ResourceInfo.checkByteStringIsUtf8(value);
            this.resourceType_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getResourceName() {
            Object ref = this.resourceName_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.resourceName_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getResourceNameBytes() {
            Object ref = this.resourceName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.resourceName_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setResourceName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.resourceName_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearResourceName() {
            this.resourceName_ = ResourceInfo.getDefaultInstance().getResourceName();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setResourceNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ResourceInfo.checkByteStringIsUtf8(value);
            this.resourceName_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public String getOwner() {
            Object ref = this.owner_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.owner_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getOwnerBytes() {
            Object ref = this.owner_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.owner_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setOwner(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.owner_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearOwner() {
            this.owner_ = ResourceInfo.getDefaultInstance().getOwner();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setOwnerBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ResourceInfo.checkByteStringIsUtf8(value);
            this.owner_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        @Override
        public String getDescription() {
            Object ref = this.description_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.description_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getDescriptionBytes() {
            Object ref = this.description_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.description_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setDescription(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.description_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearDescription() {
            this.description_ = ResourceInfo.getDefaultInstance().getDescription();
            this.bitField0_ &= 0xFFFFFFF7;
            this.onChanged();
            return this;
        }

        public Builder setDescriptionBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ResourceInfo.checkByteStringIsUtf8(value);
            this.description_ = value;
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

