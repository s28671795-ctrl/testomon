/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.AuditLogProto;
import com.google.cloud.audit.AuthorizationInfoOrBuilder;
import com.google.protobuf.AbstractParser;
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
import com.google.rpc.context.AttributeContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class AuthorizationInfo
extends GeneratedMessageV3
implements AuthorizationInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int RESOURCE_FIELD_NUMBER = 1;
    private volatile Object resource_ = "";
    public static final int PERMISSION_FIELD_NUMBER = 2;
    private volatile Object permission_ = "";
    public static final int GRANTED_FIELD_NUMBER = 3;
    private boolean granted_ = false;
    public static final int RESOURCE_ATTRIBUTES_FIELD_NUMBER = 5;
    private AttributeContext.Resource resourceAttributes_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final AuthorizationInfo DEFAULT_INSTANCE = new AuthorizationInfo();
    private static final Parser<AuthorizationInfo> PARSER = new AbstractParser<AuthorizationInfo>(){

        @Override
        public AuthorizationInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = AuthorizationInfo.newBuilder();
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

    private AuthorizationInfo(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private AuthorizationInfo() {
        this.resource_ = "";
        this.permission_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new AuthorizationInfo();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuditLogProto.internal_static_google_cloud_audit_AuthorizationInfo_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuditLogProto.internal_static_google_cloud_audit_AuthorizationInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthorizationInfo.class, Builder.class);
    }

    @Override
    public String getResource() {
        Object ref = this.resource_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.resource_ = s2;
        return s2;
    }

    @Override
    public ByteString getResourceBytes() {
        Object ref = this.resource_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.resource_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getPermission() {
        Object ref = this.permission_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.permission_ = s2;
        return s2;
    }

    @Override
    public ByteString getPermissionBytes() {
        Object ref = this.permission_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.permission_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean getGranted() {
        return this.granted_;
    }

    @Override
    public boolean hasResourceAttributes() {
        return this.resourceAttributes_ != null;
    }

    @Override
    public AttributeContext.Resource getResourceAttributes() {
        return this.resourceAttributes_ == null ? AttributeContext.Resource.getDefaultInstance() : this.resourceAttributes_;
    }

    @Override
    public AttributeContext.ResourceOrBuilder getResourceAttributesOrBuilder() {
        return this.resourceAttributes_ == null ? AttributeContext.Resource.getDefaultInstance() : this.resourceAttributes_;
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
        if (!GeneratedMessageV3.isStringEmpty(this.resource_)) {
            GeneratedMessageV3.writeString(output, 1, this.resource_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.permission_)) {
            GeneratedMessageV3.writeString(output, 2, this.permission_);
        }
        if (this.granted_) {
            output.writeBool(3, this.granted_);
        }
        if (this.resourceAttributes_ != null) {
            output.writeMessage(5, this.getResourceAttributes());
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
        if (!GeneratedMessageV3.isStringEmpty(this.resource_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.resource_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.permission_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.permission_);
        }
        if (this.granted_) {
            size += CodedOutputStream.computeBoolSize(3, this.granted_);
        }
        if (this.resourceAttributes_ != null) {
            size += CodedOutputStream.computeMessageSize(5, this.getResourceAttributes());
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AuthorizationInfo)) {
            return super.equals(obj);
        }
        AuthorizationInfo other = (AuthorizationInfo)obj;
        if (!this.getResource().equals(other.getResource())) {
            return false;
        }
        if (!this.getPermission().equals(other.getPermission())) {
            return false;
        }
        if (this.getGranted() != other.getGranted()) {
            return false;
        }
        if (this.hasResourceAttributes() != other.hasResourceAttributes()) {
            return false;
        }
        if (this.hasResourceAttributes() && !this.getResourceAttributes().equals(other.getResourceAttributes())) {
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
        hash = 19 * hash + AuthorizationInfo.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getResource().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getPermission().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + Internal.hashBoolean(this.getGranted());
        if (this.hasResourceAttributes()) {
            hash = 37 * hash + 5;
            hash = 53 * hash + this.getResourceAttributes().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static AuthorizationInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthorizationInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthorizationInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthorizationInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthorizationInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthorizationInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthorizationInfo parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AuthorizationInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static AuthorizationInfo parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static AuthorizationInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static AuthorizationInfo parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AuthorizationInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return AuthorizationInfo.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(AuthorizationInfo prototype) {
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

    public static AuthorizationInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AuthorizationInfo> parser() {
        return PARSER;
    }

    public Parser<AuthorizationInfo> getParserForType() {
        return PARSER;
    }

    @Override
    public AuthorizationInfo getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements AuthorizationInfoOrBuilder {
        private int bitField0_;
        private Object resource_ = "";
        private Object permission_ = "";
        private boolean granted_;
        private AttributeContext.Resource resourceAttributes_;
        private SingleFieldBuilderV3<AttributeContext.Resource, AttributeContext.Resource.Builder, AttributeContext.ResourceOrBuilder> resourceAttributesBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return AuditLogProto.internal_static_google_cloud_audit_AuthorizationInfo_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuditLogProto.internal_static_google_cloud_audit_AuthorizationInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthorizationInfo.class, Builder.class);
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
            this.resource_ = "";
            this.permission_ = "";
            this.granted_ = false;
            this.resourceAttributes_ = null;
            if (this.resourceAttributesBuilder_ != null) {
                this.resourceAttributesBuilder_.dispose();
                this.resourceAttributesBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuditLogProto.internal_static_google_cloud_audit_AuthorizationInfo_descriptor;
        }

        @Override
        public AuthorizationInfo getDefaultInstanceForType() {
            return AuthorizationInfo.getDefaultInstance();
        }

        @Override
        public AuthorizationInfo build() {
            AuthorizationInfo result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public AuthorizationInfo buildPartial() {
            AuthorizationInfo result = new AuthorizationInfo(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(AuthorizationInfo result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.resource_ = this.resource_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.permission_ = this.permission_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.granted_ = this.granted_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.resourceAttributes_ = this.resourceAttributesBuilder_ == null ? this.resourceAttributes_ : this.resourceAttributesBuilder_.build();
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
            if (other instanceof AuthorizationInfo) {
                return this.mergeFrom((AuthorizationInfo)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(AuthorizationInfo other) {
            if (other == AuthorizationInfo.getDefaultInstance()) {
                return this;
            }
            if (!other.getResource().isEmpty()) {
                this.resource_ = other.resource_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getPermission().isEmpty()) {
                this.permission_ = other.permission_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (other.getGranted()) {
                this.setGranted(other.getGranted());
            }
            if (other.hasResourceAttributes()) {
                this.mergeResourceAttributes(other.getResourceAttributes());
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
                            this.resource_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block12;
                        }
                        case 18: {
                            this.permission_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block12;
                        }
                        case 24: {
                            this.granted_ = input.readBool();
                            this.bitField0_ |= 4;
                            continue block12;
                        }
                        case 42: {
                            input.readMessage(this.getResourceAttributesFieldBuilder().getBuilder(), extensionRegistry);
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
        public String getResource() {
            Object ref = this.resource_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.resource_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getResourceBytes() {
            Object ref = this.resource_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.resource_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setResource(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.resource_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearResource() {
            this.resource_ = AuthorizationInfo.getDefaultInstance().getResource();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setResourceBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuthorizationInfo.checkByteStringIsUtf8(value);
            this.resource_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getPermission() {
            Object ref = this.permission_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.permission_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getPermissionBytes() {
            Object ref = this.permission_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.permission_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setPermission(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.permission_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearPermission() {
            this.permission_ = AuthorizationInfo.getDefaultInstance().getPermission();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setPermissionBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuthorizationInfo.checkByteStringIsUtf8(value);
            this.permission_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public boolean getGranted() {
            return this.granted_;
        }

        public Builder setGranted(boolean value) {
            this.granted_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearGranted() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.granted_ = false;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasResourceAttributes() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override
        public AttributeContext.Resource getResourceAttributes() {
            if (this.resourceAttributesBuilder_ == null) {
                return this.resourceAttributes_ == null ? AttributeContext.Resource.getDefaultInstance() : this.resourceAttributes_;
            }
            return this.resourceAttributesBuilder_.getMessage();
        }

        public Builder setResourceAttributes(AttributeContext.Resource value) {
            if (this.resourceAttributesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.resourceAttributes_ = value;
            } else {
                this.resourceAttributesBuilder_.setMessage(value);
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder setResourceAttributes(AttributeContext.Resource.Builder builderForValue) {
            if (this.resourceAttributesBuilder_ == null) {
                this.resourceAttributes_ = builderForValue.build();
            } else {
                this.resourceAttributesBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder mergeResourceAttributes(AttributeContext.Resource value) {
            if (this.resourceAttributesBuilder_ == null) {
                if ((this.bitField0_ & 8) != 0 && this.resourceAttributes_ != null && this.resourceAttributes_ != AttributeContext.Resource.getDefaultInstance()) {
                    this.getResourceAttributesBuilder().mergeFrom(value);
                } else {
                    this.resourceAttributes_ = value;
                }
            } else {
                this.resourceAttributesBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearResourceAttributes() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.resourceAttributes_ = null;
            if (this.resourceAttributesBuilder_ != null) {
                this.resourceAttributesBuilder_.dispose();
                this.resourceAttributesBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public AttributeContext.Resource.Builder getResourceAttributesBuilder() {
            this.bitField0_ |= 8;
            this.onChanged();
            return this.getResourceAttributesFieldBuilder().getBuilder();
        }

        @Override
        public AttributeContext.ResourceOrBuilder getResourceAttributesOrBuilder() {
            if (this.resourceAttributesBuilder_ != null) {
                return this.resourceAttributesBuilder_.getMessageOrBuilder();
            }
            return this.resourceAttributes_ == null ? AttributeContext.Resource.getDefaultInstance() : this.resourceAttributes_;
        }

        private SingleFieldBuilderV3<AttributeContext.Resource, AttributeContext.Resource.Builder, AttributeContext.ResourceOrBuilder> getResourceAttributesFieldBuilder() {
            if (this.resourceAttributesBuilder_ == null) {
                this.resourceAttributesBuilder_ = new SingleFieldBuilderV3(this.getResourceAttributes(), this.getParentForChildren(), this.isClean());
                this.resourceAttributes_ = null;
            }
            return this.resourceAttributesBuilder_;
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

