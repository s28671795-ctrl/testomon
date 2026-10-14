/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.AuditLogProto;
import com.google.cloud.audit.AuthenticationInfoOrBuilder;
import com.google.cloud.audit.ServiceAccountDelegationInfo;
import com.google.cloud.audit.ServiceAccountDelegationInfoOrBuilder;
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
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.Struct;
import com.google.protobuf.StructOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AuthenticationInfo
extends GeneratedMessageV3
implements AuthenticationInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int PRINCIPAL_EMAIL_FIELD_NUMBER = 1;
    private volatile Object principalEmail_ = "";
    public static final int AUTHORITY_SELECTOR_FIELD_NUMBER = 2;
    private volatile Object authoritySelector_ = "";
    public static final int THIRD_PARTY_PRINCIPAL_FIELD_NUMBER = 4;
    private Struct thirdPartyPrincipal_;
    public static final int SERVICE_ACCOUNT_KEY_NAME_FIELD_NUMBER = 5;
    private volatile Object serviceAccountKeyName_ = "";
    public static final int SERVICE_ACCOUNT_DELEGATION_INFO_FIELD_NUMBER = 6;
    private List<ServiceAccountDelegationInfo> serviceAccountDelegationInfo_;
    public static final int PRINCIPAL_SUBJECT_FIELD_NUMBER = 8;
    private volatile Object principalSubject_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final AuthenticationInfo DEFAULT_INSTANCE = new AuthenticationInfo();
    private static final Parser<AuthenticationInfo> PARSER = new AbstractParser<AuthenticationInfo>(){

        @Override
        public AuthenticationInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = AuthenticationInfo.newBuilder();
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

    private AuthenticationInfo(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private AuthenticationInfo() {
        this.principalEmail_ = "";
        this.authoritySelector_ = "";
        this.serviceAccountKeyName_ = "";
        this.serviceAccountDelegationInfo_ = Collections.emptyList();
        this.principalSubject_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new AuthenticationInfo();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuditLogProto.internal_static_google_cloud_audit_AuthenticationInfo_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuditLogProto.internal_static_google_cloud_audit_AuthenticationInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthenticationInfo.class, Builder.class);
    }

    @Override
    public String getPrincipalEmail() {
        Object ref = this.principalEmail_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.principalEmail_ = s2;
        return s2;
    }

    @Override
    public ByteString getPrincipalEmailBytes() {
        Object ref = this.principalEmail_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.principalEmail_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getAuthoritySelector() {
        Object ref = this.authoritySelector_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.authoritySelector_ = s2;
        return s2;
    }

    @Override
    public ByteString getAuthoritySelectorBytes() {
        Object ref = this.authoritySelector_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.authoritySelector_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasThirdPartyPrincipal() {
        return this.thirdPartyPrincipal_ != null;
    }

    @Override
    public Struct getThirdPartyPrincipal() {
        return this.thirdPartyPrincipal_ == null ? Struct.getDefaultInstance() : this.thirdPartyPrincipal_;
    }

    @Override
    public StructOrBuilder getThirdPartyPrincipalOrBuilder() {
        return this.thirdPartyPrincipal_ == null ? Struct.getDefaultInstance() : this.thirdPartyPrincipal_;
    }

    @Override
    public String getServiceAccountKeyName() {
        Object ref = this.serviceAccountKeyName_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.serviceAccountKeyName_ = s2;
        return s2;
    }

    @Override
    public ByteString getServiceAccountKeyNameBytes() {
        Object ref = this.serviceAccountKeyName_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.serviceAccountKeyName_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public List<ServiceAccountDelegationInfo> getServiceAccountDelegationInfoList() {
        return this.serviceAccountDelegationInfo_;
    }

    @Override
    public List<? extends ServiceAccountDelegationInfoOrBuilder> getServiceAccountDelegationInfoOrBuilderList() {
        return this.serviceAccountDelegationInfo_;
    }

    @Override
    public int getServiceAccountDelegationInfoCount() {
        return this.serviceAccountDelegationInfo_.size();
    }

    @Override
    public ServiceAccountDelegationInfo getServiceAccountDelegationInfo(int index) {
        return this.serviceAccountDelegationInfo_.get(index);
    }

    @Override
    public ServiceAccountDelegationInfoOrBuilder getServiceAccountDelegationInfoOrBuilder(int index) {
        return this.serviceAccountDelegationInfo_.get(index);
    }

    @Override
    public String getPrincipalSubject() {
        Object ref = this.principalSubject_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.principalSubject_ = s2;
        return s2;
    }

    @Override
    public ByteString getPrincipalSubjectBytes() {
        Object ref = this.principalSubject_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.principalSubject_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.principalEmail_)) {
            GeneratedMessageV3.writeString(output, 1, this.principalEmail_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.authoritySelector_)) {
            GeneratedMessageV3.writeString(output, 2, this.authoritySelector_);
        }
        if (this.thirdPartyPrincipal_ != null) {
            output.writeMessage(4, this.getThirdPartyPrincipal());
        }
        if (!GeneratedMessageV3.isStringEmpty(this.serviceAccountKeyName_)) {
            GeneratedMessageV3.writeString(output, 5, this.serviceAccountKeyName_);
        }
        for (int i = 0; i < this.serviceAccountDelegationInfo_.size(); ++i) {
            output.writeMessage(6, this.serviceAccountDelegationInfo_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.principalSubject_)) {
            GeneratedMessageV3.writeString(output, 8, this.principalSubject_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.principalEmail_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.principalEmail_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.authoritySelector_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.authoritySelector_);
        }
        if (this.thirdPartyPrincipal_ != null) {
            size += CodedOutputStream.computeMessageSize(4, this.getThirdPartyPrincipal());
        }
        if (!GeneratedMessageV3.isStringEmpty(this.serviceAccountKeyName_)) {
            size += GeneratedMessageV3.computeStringSize(5, this.serviceAccountKeyName_);
        }
        for (int i = 0; i < this.serviceAccountDelegationInfo_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(6, this.serviceAccountDelegationInfo_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.principalSubject_)) {
            size += GeneratedMessageV3.computeStringSize(8, this.principalSubject_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AuthenticationInfo)) {
            return super.equals(obj);
        }
        AuthenticationInfo other = (AuthenticationInfo)obj;
        if (!this.getPrincipalEmail().equals(other.getPrincipalEmail())) {
            return false;
        }
        if (!this.getAuthoritySelector().equals(other.getAuthoritySelector())) {
            return false;
        }
        if (this.hasThirdPartyPrincipal() != other.hasThirdPartyPrincipal()) {
            return false;
        }
        if (this.hasThirdPartyPrincipal() && !this.getThirdPartyPrincipal().equals(other.getThirdPartyPrincipal())) {
            return false;
        }
        if (!this.getServiceAccountKeyName().equals(other.getServiceAccountKeyName())) {
            return false;
        }
        if (!this.getServiceAccountDelegationInfoList().equals(other.getServiceAccountDelegationInfoList())) {
            return false;
        }
        if (!this.getPrincipalSubject().equals(other.getPrincipalSubject())) {
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
        hash = 19 * hash + AuthenticationInfo.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getPrincipalEmail().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getAuthoritySelector().hashCode();
        if (this.hasThirdPartyPrincipal()) {
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getThirdPartyPrincipal().hashCode();
        }
        hash = 37 * hash + 5;
        hash = 53 * hash + this.getServiceAccountKeyName().hashCode();
        if (this.getServiceAccountDelegationInfoCount() > 0) {
            hash = 37 * hash + 6;
            hash = 53 * hash + this.getServiceAccountDelegationInfoList().hashCode();
        }
        hash = 37 * hash + 8;
        hash = 53 * hash + this.getPrincipalSubject().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static AuthenticationInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthenticationInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthenticationInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthenticationInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthenticationInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthenticationInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthenticationInfo parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AuthenticationInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static AuthenticationInfo parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static AuthenticationInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static AuthenticationInfo parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AuthenticationInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return AuthenticationInfo.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(AuthenticationInfo prototype) {
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

    public static AuthenticationInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AuthenticationInfo> parser() {
        return PARSER;
    }

    public Parser<AuthenticationInfo> getParserForType() {
        return PARSER;
    }

    @Override
    public AuthenticationInfo getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements AuthenticationInfoOrBuilder {
        private int bitField0_;
        private Object principalEmail_ = "";
        private Object authoritySelector_ = "";
        private Struct thirdPartyPrincipal_;
        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> thirdPartyPrincipalBuilder_;
        private Object serviceAccountKeyName_ = "";
        private List<ServiceAccountDelegationInfo> serviceAccountDelegationInfo_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<ServiceAccountDelegationInfo, ServiceAccountDelegationInfo.Builder, ServiceAccountDelegationInfoOrBuilder> serviceAccountDelegationInfoBuilder_;
        private Object principalSubject_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return AuditLogProto.internal_static_google_cloud_audit_AuthenticationInfo_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuditLogProto.internal_static_google_cloud_audit_AuthenticationInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthenticationInfo.class, Builder.class);
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
            this.principalEmail_ = "";
            this.authoritySelector_ = "";
            this.thirdPartyPrincipal_ = null;
            if (this.thirdPartyPrincipalBuilder_ != null) {
                this.thirdPartyPrincipalBuilder_.dispose();
                this.thirdPartyPrincipalBuilder_ = null;
            }
            this.serviceAccountKeyName_ = "";
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                this.serviceAccountDelegationInfo_ = Collections.emptyList();
            } else {
                this.serviceAccountDelegationInfo_ = null;
                this.serviceAccountDelegationInfoBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFEF;
            this.principalSubject_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuditLogProto.internal_static_google_cloud_audit_AuthenticationInfo_descriptor;
        }

        @Override
        public AuthenticationInfo getDefaultInstanceForType() {
            return AuthenticationInfo.getDefaultInstance();
        }

        @Override
        public AuthenticationInfo build() {
            AuthenticationInfo result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public AuthenticationInfo buildPartial() {
            AuthenticationInfo result = new AuthenticationInfo(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(AuthenticationInfo result) {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                if ((this.bitField0_ & 0x10) != 0) {
                    this.serviceAccountDelegationInfo_ = Collections.unmodifiableList(this.serviceAccountDelegationInfo_);
                    this.bitField0_ &= 0xFFFFFFEF;
                }
                result.serviceAccountDelegationInfo_ = this.serviceAccountDelegationInfo_;
            } else {
                result.serviceAccountDelegationInfo_ = this.serviceAccountDelegationInfoBuilder_.build();
            }
        }

        private void buildPartial0(AuthenticationInfo result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.principalEmail_ = this.principalEmail_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.authoritySelector_ = this.authoritySelector_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.thirdPartyPrincipal_ = this.thirdPartyPrincipalBuilder_ == null ? this.thirdPartyPrincipal_ : this.thirdPartyPrincipalBuilder_.build();
            }
            if ((from_bitField0_ & 8) != 0) {
                result.serviceAccountKeyName_ = this.serviceAccountKeyName_;
            }
            if ((from_bitField0_ & 0x20) != 0) {
                result.principalSubject_ = this.principalSubject_;
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
            if (other instanceof AuthenticationInfo) {
                return this.mergeFrom((AuthenticationInfo)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(AuthenticationInfo other) {
            if (other == AuthenticationInfo.getDefaultInstance()) {
                return this;
            }
            if (!other.getPrincipalEmail().isEmpty()) {
                this.principalEmail_ = other.principalEmail_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getAuthoritySelector().isEmpty()) {
                this.authoritySelector_ = other.authoritySelector_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (other.hasThirdPartyPrincipal()) {
                this.mergeThirdPartyPrincipal(other.getThirdPartyPrincipal());
            }
            if (!other.getServiceAccountKeyName().isEmpty()) {
                this.serviceAccountKeyName_ = other.serviceAccountKeyName_;
                this.bitField0_ |= 8;
                this.onChanged();
            }
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                if (!other.serviceAccountDelegationInfo_.isEmpty()) {
                    if (this.serviceAccountDelegationInfo_.isEmpty()) {
                        this.serviceAccountDelegationInfo_ = other.serviceAccountDelegationInfo_;
                        this.bitField0_ &= 0xFFFFFFEF;
                    } else {
                        this.ensureServiceAccountDelegationInfoIsMutable();
                        this.serviceAccountDelegationInfo_.addAll(other.serviceAccountDelegationInfo_);
                    }
                    this.onChanged();
                }
            } else if (!other.serviceAccountDelegationInfo_.isEmpty()) {
                if (this.serviceAccountDelegationInfoBuilder_.isEmpty()) {
                    this.serviceAccountDelegationInfoBuilder_.dispose();
                    this.serviceAccountDelegationInfoBuilder_ = null;
                    this.serviceAccountDelegationInfo_ = other.serviceAccountDelegationInfo_;
                    this.bitField0_ &= 0xFFFFFFEF;
                    this.serviceAccountDelegationInfoBuilder_ = alwaysUseFieldBuilders ? this.getServiceAccountDelegationInfoFieldBuilder() : null;
                } else {
                    this.serviceAccountDelegationInfoBuilder_.addAllMessages(other.serviceAccountDelegationInfo_);
                }
            }
            if (!other.getPrincipalSubject().isEmpty()) {
                this.principalSubject_ = other.principalSubject_;
                this.bitField0_ |= 0x20;
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
                block14: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block14;
                        }
                        case 10: {
                            this.principalEmail_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block14;
                        }
                        case 18: {
                            this.authoritySelector_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block14;
                        }
                        case 34: {
                            input.readMessage(this.getThirdPartyPrincipalFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 4;
                            continue block14;
                        }
                        case 42: {
                            this.serviceAccountKeyName_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 8;
                            continue block14;
                        }
                        case 50: {
                            ServiceAccountDelegationInfo m4 = input.readMessage(ServiceAccountDelegationInfo.parser(), extensionRegistry);
                            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                                this.ensureServiceAccountDelegationInfoIsMutable();
                                this.serviceAccountDelegationInfo_.add(m4);
                                continue block14;
                            }
                            this.serviceAccountDelegationInfoBuilder_.addMessage(m4);
                            continue block14;
                        }
                        case 66: {
                            this.principalSubject_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x20;
                            continue block14;
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
        public String getPrincipalEmail() {
            Object ref = this.principalEmail_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.principalEmail_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getPrincipalEmailBytes() {
            Object ref = this.principalEmail_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.principalEmail_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setPrincipalEmail(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.principalEmail_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearPrincipalEmail() {
            this.principalEmail_ = AuthenticationInfo.getDefaultInstance().getPrincipalEmail();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setPrincipalEmailBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuthenticationInfo.checkByteStringIsUtf8(value);
            this.principalEmail_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getAuthoritySelector() {
            Object ref = this.authoritySelector_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.authoritySelector_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getAuthoritySelectorBytes() {
            Object ref = this.authoritySelector_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.authoritySelector_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setAuthoritySelector(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.authoritySelector_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearAuthoritySelector() {
            this.authoritySelector_ = AuthenticationInfo.getDefaultInstance().getAuthoritySelector();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setAuthoritySelectorBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuthenticationInfo.checkByteStringIsUtf8(value);
            this.authoritySelector_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasThirdPartyPrincipal() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override
        public Struct getThirdPartyPrincipal() {
            if (this.thirdPartyPrincipalBuilder_ == null) {
                return this.thirdPartyPrincipal_ == null ? Struct.getDefaultInstance() : this.thirdPartyPrincipal_;
            }
            return this.thirdPartyPrincipalBuilder_.getMessage();
        }

        public Builder setThirdPartyPrincipal(Struct value) {
            if (this.thirdPartyPrincipalBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.thirdPartyPrincipal_ = value;
            } else {
                this.thirdPartyPrincipalBuilder_.setMessage(value);
            }
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder setThirdPartyPrincipal(Struct.Builder builderForValue) {
            if (this.thirdPartyPrincipalBuilder_ == null) {
                this.thirdPartyPrincipal_ = builderForValue.build();
            } else {
                this.thirdPartyPrincipalBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder mergeThirdPartyPrincipal(Struct value) {
            if (this.thirdPartyPrincipalBuilder_ == null) {
                if ((this.bitField0_ & 4) != 0 && this.thirdPartyPrincipal_ != null && this.thirdPartyPrincipal_ != Struct.getDefaultInstance()) {
                    this.getThirdPartyPrincipalBuilder().mergeFrom(value);
                } else {
                    this.thirdPartyPrincipal_ = value;
                }
            } else {
                this.thirdPartyPrincipalBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearThirdPartyPrincipal() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.thirdPartyPrincipal_ = null;
            if (this.thirdPartyPrincipalBuilder_ != null) {
                this.thirdPartyPrincipalBuilder_.dispose();
                this.thirdPartyPrincipalBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Struct.Builder getThirdPartyPrincipalBuilder() {
            this.bitField0_ |= 4;
            this.onChanged();
            return this.getThirdPartyPrincipalFieldBuilder().getBuilder();
        }

        @Override
        public StructOrBuilder getThirdPartyPrincipalOrBuilder() {
            if (this.thirdPartyPrincipalBuilder_ != null) {
                return this.thirdPartyPrincipalBuilder_.getMessageOrBuilder();
            }
            return this.thirdPartyPrincipal_ == null ? Struct.getDefaultInstance() : this.thirdPartyPrincipal_;
        }

        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getThirdPartyPrincipalFieldBuilder() {
            if (this.thirdPartyPrincipalBuilder_ == null) {
                this.thirdPartyPrincipalBuilder_ = new SingleFieldBuilderV3(this.getThirdPartyPrincipal(), this.getParentForChildren(), this.isClean());
                this.thirdPartyPrincipal_ = null;
            }
            return this.thirdPartyPrincipalBuilder_;
        }

        @Override
        public String getServiceAccountKeyName() {
            Object ref = this.serviceAccountKeyName_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.serviceAccountKeyName_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getServiceAccountKeyNameBytes() {
            Object ref = this.serviceAccountKeyName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.serviceAccountKeyName_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setServiceAccountKeyName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.serviceAccountKeyName_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearServiceAccountKeyName() {
            this.serviceAccountKeyName_ = AuthenticationInfo.getDefaultInstance().getServiceAccountKeyName();
            this.bitField0_ &= 0xFFFFFFF7;
            this.onChanged();
            return this;
        }

        public Builder setServiceAccountKeyNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuthenticationInfo.checkByteStringIsUtf8(value);
            this.serviceAccountKeyName_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        private void ensureServiceAccountDelegationInfoIsMutable() {
            if ((this.bitField0_ & 0x10) == 0) {
                this.serviceAccountDelegationInfo_ = new ArrayList<ServiceAccountDelegationInfo>(this.serviceAccountDelegationInfo_);
                this.bitField0_ |= 0x10;
            }
        }

        @Override
        public List<ServiceAccountDelegationInfo> getServiceAccountDelegationInfoList() {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                return Collections.unmodifiableList(this.serviceAccountDelegationInfo_);
            }
            return this.serviceAccountDelegationInfoBuilder_.getMessageList();
        }

        @Override
        public int getServiceAccountDelegationInfoCount() {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                return this.serviceAccountDelegationInfo_.size();
            }
            return this.serviceAccountDelegationInfoBuilder_.getCount();
        }

        @Override
        public ServiceAccountDelegationInfo getServiceAccountDelegationInfo(int index) {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                return this.serviceAccountDelegationInfo_.get(index);
            }
            return this.serviceAccountDelegationInfoBuilder_.getMessage(index);
        }

        public Builder setServiceAccountDelegationInfo(int index, ServiceAccountDelegationInfo value) {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureServiceAccountDelegationInfoIsMutable();
                this.serviceAccountDelegationInfo_.set(index, value);
                this.onChanged();
            } else {
                this.serviceAccountDelegationInfoBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setServiceAccountDelegationInfo(int index, ServiceAccountDelegationInfo.Builder builderForValue) {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                this.ensureServiceAccountDelegationInfoIsMutable();
                this.serviceAccountDelegationInfo_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.serviceAccountDelegationInfoBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addServiceAccountDelegationInfo(ServiceAccountDelegationInfo value) {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureServiceAccountDelegationInfoIsMutable();
                this.serviceAccountDelegationInfo_.add(value);
                this.onChanged();
            } else {
                this.serviceAccountDelegationInfoBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addServiceAccountDelegationInfo(int index, ServiceAccountDelegationInfo value) {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureServiceAccountDelegationInfoIsMutable();
                this.serviceAccountDelegationInfo_.add(index, value);
                this.onChanged();
            } else {
                this.serviceAccountDelegationInfoBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addServiceAccountDelegationInfo(ServiceAccountDelegationInfo.Builder builderForValue) {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                this.ensureServiceAccountDelegationInfoIsMutable();
                this.serviceAccountDelegationInfo_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.serviceAccountDelegationInfoBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addServiceAccountDelegationInfo(int index, ServiceAccountDelegationInfo.Builder builderForValue) {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                this.ensureServiceAccountDelegationInfoIsMutable();
                this.serviceAccountDelegationInfo_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.serviceAccountDelegationInfoBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllServiceAccountDelegationInfo(Iterable<? extends ServiceAccountDelegationInfo> values2) {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                this.ensureServiceAccountDelegationInfoIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.serviceAccountDelegationInfo_);
                this.onChanged();
            } else {
                this.serviceAccountDelegationInfoBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearServiceAccountDelegationInfo() {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                this.serviceAccountDelegationInfo_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFEF;
                this.onChanged();
            } else {
                this.serviceAccountDelegationInfoBuilder_.clear();
            }
            return this;
        }

        public Builder removeServiceAccountDelegationInfo(int index) {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                this.ensureServiceAccountDelegationInfoIsMutable();
                this.serviceAccountDelegationInfo_.remove(index);
                this.onChanged();
            } else {
                this.serviceAccountDelegationInfoBuilder_.remove(index);
            }
            return this;
        }

        public ServiceAccountDelegationInfo.Builder getServiceAccountDelegationInfoBuilder(int index) {
            return this.getServiceAccountDelegationInfoFieldBuilder().getBuilder(index);
        }

        @Override
        public ServiceAccountDelegationInfoOrBuilder getServiceAccountDelegationInfoOrBuilder(int index) {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                return this.serviceAccountDelegationInfo_.get(index);
            }
            return this.serviceAccountDelegationInfoBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends ServiceAccountDelegationInfoOrBuilder> getServiceAccountDelegationInfoOrBuilderList() {
            if (this.serviceAccountDelegationInfoBuilder_ != null) {
                return this.serviceAccountDelegationInfoBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.serviceAccountDelegationInfo_);
        }

        public ServiceAccountDelegationInfo.Builder addServiceAccountDelegationInfoBuilder() {
            return this.getServiceAccountDelegationInfoFieldBuilder().addBuilder(ServiceAccountDelegationInfo.getDefaultInstance());
        }

        public ServiceAccountDelegationInfo.Builder addServiceAccountDelegationInfoBuilder(int index) {
            return this.getServiceAccountDelegationInfoFieldBuilder().addBuilder(index, ServiceAccountDelegationInfo.getDefaultInstance());
        }

        public List<ServiceAccountDelegationInfo.Builder> getServiceAccountDelegationInfoBuilderList() {
            return this.getServiceAccountDelegationInfoFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<ServiceAccountDelegationInfo, ServiceAccountDelegationInfo.Builder, ServiceAccountDelegationInfoOrBuilder> getServiceAccountDelegationInfoFieldBuilder() {
            if (this.serviceAccountDelegationInfoBuilder_ == null) {
                this.serviceAccountDelegationInfoBuilder_ = new RepeatedFieldBuilderV3(this.serviceAccountDelegationInfo_, (this.bitField0_ & 0x10) != 0, this.getParentForChildren(), this.isClean());
                this.serviceAccountDelegationInfo_ = null;
            }
            return this.serviceAccountDelegationInfoBuilder_;
        }

        @Override
        public String getPrincipalSubject() {
            Object ref = this.principalSubject_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.principalSubject_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getPrincipalSubjectBytes() {
            Object ref = this.principalSubject_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.principalSubject_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setPrincipalSubject(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.principalSubject_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder clearPrincipalSubject() {
            this.principalSubject_ = AuthenticationInfo.getDefaultInstance().getPrincipalSubject();
            this.bitField0_ &= 0xFFFFFFDF;
            this.onChanged();
            return this;
        }

        public Builder setPrincipalSubjectBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuthenticationInfo.checkByteStringIsUtf8(value);
            this.principalSubject_ = value;
            this.bitField0_ |= 0x20;
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

