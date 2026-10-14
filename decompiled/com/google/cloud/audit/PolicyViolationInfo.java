/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.AuditLogProto;
import com.google.cloud.audit.OrgPolicyViolationInfo;
import com.google.cloud.audit.OrgPolicyViolationInfoOrBuilder;
import com.google.cloud.audit.PolicyViolationInfoOrBuilder;
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
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class PolicyViolationInfo
extends GeneratedMessageV3
implements PolicyViolationInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int ORG_POLICY_VIOLATION_INFO_FIELD_NUMBER = 1;
    private OrgPolicyViolationInfo orgPolicyViolationInfo_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final PolicyViolationInfo DEFAULT_INSTANCE = new PolicyViolationInfo();
    private static final Parser<PolicyViolationInfo> PARSER = new AbstractParser<PolicyViolationInfo>(){

        @Override
        public PolicyViolationInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = PolicyViolationInfo.newBuilder();
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

    private PolicyViolationInfo(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private PolicyViolationInfo() {
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new PolicyViolationInfo();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuditLogProto.internal_static_google_cloud_audit_PolicyViolationInfo_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuditLogProto.internal_static_google_cloud_audit_PolicyViolationInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(PolicyViolationInfo.class, Builder.class);
    }

    @Override
    public boolean hasOrgPolicyViolationInfo() {
        return this.orgPolicyViolationInfo_ != null;
    }

    @Override
    public OrgPolicyViolationInfo getOrgPolicyViolationInfo() {
        return this.orgPolicyViolationInfo_ == null ? OrgPolicyViolationInfo.getDefaultInstance() : this.orgPolicyViolationInfo_;
    }

    @Override
    public OrgPolicyViolationInfoOrBuilder getOrgPolicyViolationInfoOrBuilder() {
        return this.orgPolicyViolationInfo_ == null ? OrgPolicyViolationInfo.getDefaultInstance() : this.orgPolicyViolationInfo_;
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
        if (this.orgPolicyViolationInfo_ != null) {
            output.writeMessage(1, this.getOrgPolicyViolationInfo());
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
        if (this.orgPolicyViolationInfo_ != null) {
            size += CodedOutputStream.computeMessageSize(1, this.getOrgPolicyViolationInfo());
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PolicyViolationInfo)) {
            return super.equals(obj);
        }
        PolicyViolationInfo other = (PolicyViolationInfo)obj;
        if (this.hasOrgPolicyViolationInfo() != other.hasOrgPolicyViolationInfo()) {
            return false;
        }
        if (this.hasOrgPolicyViolationInfo() && !this.getOrgPolicyViolationInfo().equals(other.getOrgPolicyViolationInfo())) {
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
        hash = 19 * hash + PolicyViolationInfo.getDescriptor().hashCode();
        if (this.hasOrgPolicyViolationInfo()) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getOrgPolicyViolationInfo().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static PolicyViolationInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static PolicyViolationInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static PolicyViolationInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static PolicyViolationInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static PolicyViolationInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static PolicyViolationInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static PolicyViolationInfo parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static PolicyViolationInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static PolicyViolationInfo parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static PolicyViolationInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static PolicyViolationInfo parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static PolicyViolationInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return PolicyViolationInfo.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(PolicyViolationInfo prototype) {
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

    public static PolicyViolationInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<PolicyViolationInfo> parser() {
        return PARSER;
    }

    public Parser<PolicyViolationInfo> getParserForType() {
        return PARSER;
    }

    @Override
    public PolicyViolationInfo getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements PolicyViolationInfoOrBuilder {
        private int bitField0_;
        private OrgPolicyViolationInfo orgPolicyViolationInfo_;
        private SingleFieldBuilderV3<OrgPolicyViolationInfo, OrgPolicyViolationInfo.Builder, OrgPolicyViolationInfoOrBuilder> orgPolicyViolationInfoBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return AuditLogProto.internal_static_google_cloud_audit_PolicyViolationInfo_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuditLogProto.internal_static_google_cloud_audit_PolicyViolationInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(PolicyViolationInfo.class, Builder.class);
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
            this.orgPolicyViolationInfo_ = null;
            if (this.orgPolicyViolationInfoBuilder_ != null) {
                this.orgPolicyViolationInfoBuilder_.dispose();
                this.orgPolicyViolationInfoBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuditLogProto.internal_static_google_cloud_audit_PolicyViolationInfo_descriptor;
        }

        @Override
        public PolicyViolationInfo getDefaultInstanceForType() {
            return PolicyViolationInfo.getDefaultInstance();
        }

        @Override
        public PolicyViolationInfo build() {
            PolicyViolationInfo result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public PolicyViolationInfo buildPartial() {
            PolicyViolationInfo result = new PolicyViolationInfo(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(PolicyViolationInfo result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.orgPolicyViolationInfo_ = this.orgPolicyViolationInfoBuilder_ == null ? this.orgPolicyViolationInfo_ : this.orgPolicyViolationInfoBuilder_.build();
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
            if (other instanceof PolicyViolationInfo) {
                return this.mergeFrom((PolicyViolationInfo)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(PolicyViolationInfo other) {
            if (other == PolicyViolationInfo.getDefaultInstance()) {
                return this;
            }
            if (other.hasOrgPolicyViolationInfo()) {
                this.mergeOrgPolicyViolationInfo(other.getOrgPolicyViolationInfo());
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
                            input.readMessage(this.getOrgPolicyViolationInfoFieldBuilder().getBuilder(), extensionRegistry);
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
        public boolean hasOrgPolicyViolationInfo() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public OrgPolicyViolationInfo getOrgPolicyViolationInfo() {
            if (this.orgPolicyViolationInfoBuilder_ == null) {
                return this.orgPolicyViolationInfo_ == null ? OrgPolicyViolationInfo.getDefaultInstance() : this.orgPolicyViolationInfo_;
            }
            return this.orgPolicyViolationInfoBuilder_.getMessage();
        }

        public Builder setOrgPolicyViolationInfo(OrgPolicyViolationInfo value) {
            if (this.orgPolicyViolationInfoBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.orgPolicyViolationInfo_ = value;
            } else {
                this.orgPolicyViolationInfoBuilder_.setMessage(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder setOrgPolicyViolationInfo(OrgPolicyViolationInfo.Builder builderForValue) {
            if (this.orgPolicyViolationInfoBuilder_ == null) {
                this.orgPolicyViolationInfo_ = builderForValue.build();
            } else {
                this.orgPolicyViolationInfoBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder mergeOrgPolicyViolationInfo(OrgPolicyViolationInfo value) {
            if (this.orgPolicyViolationInfoBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0 && this.orgPolicyViolationInfo_ != null && this.orgPolicyViolationInfo_ != OrgPolicyViolationInfo.getDefaultInstance()) {
                    this.getOrgPolicyViolationInfoBuilder().mergeFrom(value);
                } else {
                    this.orgPolicyViolationInfo_ = value;
                }
            } else {
                this.orgPolicyViolationInfoBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearOrgPolicyViolationInfo() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.orgPolicyViolationInfo_ = null;
            if (this.orgPolicyViolationInfoBuilder_ != null) {
                this.orgPolicyViolationInfoBuilder_.dispose();
                this.orgPolicyViolationInfoBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public OrgPolicyViolationInfo.Builder getOrgPolicyViolationInfoBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return this.getOrgPolicyViolationInfoFieldBuilder().getBuilder();
        }

        @Override
        public OrgPolicyViolationInfoOrBuilder getOrgPolicyViolationInfoOrBuilder() {
            if (this.orgPolicyViolationInfoBuilder_ != null) {
                return this.orgPolicyViolationInfoBuilder_.getMessageOrBuilder();
            }
            return this.orgPolicyViolationInfo_ == null ? OrgPolicyViolationInfo.getDefaultInstance() : this.orgPolicyViolationInfo_;
        }

        private SingleFieldBuilderV3<OrgPolicyViolationInfo, OrgPolicyViolationInfo.Builder, OrgPolicyViolationInfoOrBuilder> getOrgPolicyViolationInfoFieldBuilder() {
            if (this.orgPolicyViolationInfoBuilder_ == null) {
                this.orgPolicyViolationInfoBuilder_ = new SingleFieldBuilderV3(this.getOrgPolicyViolationInfo(), this.getParentForChildren(), this.isClean());
                this.orgPolicyViolationInfo_ = null;
            }
            return this.orgPolicyViolationInfoBuilder_;
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

