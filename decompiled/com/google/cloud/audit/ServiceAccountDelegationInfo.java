/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.AuditLogProto;
import com.google.cloud.audit.ServiceAccountDelegationInfoOrBuilder;
import com.google.protobuf.AbstractMessageLite;
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
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.Struct;
import com.google.protobuf.StructOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ServiceAccountDelegationInfo
extends GeneratedMessageV3
implements ServiceAccountDelegationInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    private int authorityCase_ = 0;
    private Object authority_;
    public static final int PRINCIPAL_SUBJECT_FIELD_NUMBER = 3;
    private volatile Object principalSubject_ = "";
    public static final int FIRST_PARTY_PRINCIPAL_FIELD_NUMBER = 1;
    public static final int THIRD_PARTY_PRINCIPAL_FIELD_NUMBER = 2;
    private byte memoizedIsInitialized = (byte)-1;
    private static final ServiceAccountDelegationInfo DEFAULT_INSTANCE = new ServiceAccountDelegationInfo();
    private static final Parser<ServiceAccountDelegationInfo> PARSER = new AbstractParser<ServiceAccountDelegationInfo>(){

        @Override
        public ServiceAccountDelegationInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = ServiceAccountDelegationInfo.newBuilder();
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

    private ServiceAccountDelegationInfo(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private ServiceAccountDelegationInfo() {
        this.principalSubject_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new ServiceAccountDelegationInfo();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(ServiceAccountDelegationInfo.class, Builder.class);
    }

    @Override
    public AuthorityCase getAuthorityCase() {
        return AuthorityCase.forNumber(this.authorityCase_);
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
    public boolean hasFirstPartyPrincipal() {
        return this.authorityCase_ == 1;
    }

    @Override
    public FirstPartyPrincipal getFirstPartyPrincipal() {
        if (this.authorityCase_ == 1) {
            return (FirstPartyPrincipal)this.authority_;
        }
        return FirstPartyPrincipal.getDefaultInstance();
    }

    @Override
    public FirstPartyPrincipalOrBuilder getFirstPartyPrincipalOrBuilder() {
        if (this.authorityCase_ == 1) {
            return (FirstPartyPrincipal)this.authority_;
        }
        return FirstPartyPrincipal.getDefaultInstance();
    }

    @Override
    public boolean hasThirdPartyPrincipal() {
        return this.authorityCase_ == 2;
    }

    @Override
    public ThirdPartyPrincipal getThirdPartyPrincipal() {
        if (this.authorityCase_ == 2) {
            return (ThirdPartyPrincipal)this.authority_;
        }
        return ThirdPartyPrincipal.getDefaultInstance();
    }

    @Override
    public ThirdPartyPrincipalOrBuilder getThirdPartyPrincipalOrBuilder() {
        if (this.authorityCase_ == 2) {
            return (ThirdPartyPrincipal)this.authority_;
        }
        return ThirdPartyPrincipal.getDefaultInstance();
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
        if (this.authorityCase_ == 1) {
            output.writeMessage(1, (FirstPartyPrincipal)this.authority_);
        }
        if (this.authorityCase_ == 2) {
            output.writeMessage(2, (ThirdPartyPrincipal)this.authority_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.principalSubject_)) {
            GeneratedMessageV3.writeString(output, 3, this.principalSubject_);
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
        if (this.authorityCase_ == 1) {
            size += CodedOutputStream.computeMessageSize(1, (FirstPartyPrincipal)this.authority_);
        }
        if (this.authorityCase_ == 2) {
            size += CodedOutputStream.computeMessageSize(2, (ThirdPartyPrincipal)this.authority_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.principalSubject_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.principalSubject_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ServiceAccountDelegationInfo)) {
            return super.equals(obj);
        }
        ServiceAccountDelegationInfo other = (ServiceAccountDelegationInfo)obj;
        if (!this.getPrincipalSubject().equals(other.getPrincipalSubject())) {
            return false;
        }
        if (!this.getAuthorityCase().equals(other.getAuthorityCase())) {
            return false;
        }
        switch (this.authorityCase_) {
            case 1: {
                if (this.getFirstPartyPrincipal().equals(other.getFirstPartyPrincipal())) break;
                return false;
            }
            case 2: {
                if (this.getThirdPartyPrincipal().equals(other.getThirdPartyPrincipal())) break;
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
        hash = 19 * hash + ServiceAccountDelegationInfo.getDescriptor().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getPrincipalSubject().hashCode();
        switch (this.authorityCase_) {
            case 1: {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getFirstPartyPrincipal().hashCode();
                break;
            }
            case 2: {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getThirdPartyPrincipal().hashCode();
                break;
            }
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static ServiceAccountDelegationInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ServiceAccountDelegationInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ServiceAccountDelegationInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ServiceAccountDelegationInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ServiceAccountDelegationInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ServiceAccountDelegationInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ServiceAccountDelegationInfo parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ServiceAccountDelegationInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ServiceAccountDelegationInfo parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ServiceAccountDelegationInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ServiceAccountDelegationInfo parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ServiceAccountDelegationInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return ServiceAccountDelegationInfo.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ServiceAccountDelegationInfo prototype) {
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

    public static ServiceAccountDelegationInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ServiceAccountDelegationInfo> parser() {
        return PARSER;
    }

    public Parser<ServiceAccountDelegationInfo> getParserForType() {
        return PARSER;
    }

    @Override
    public ServiceAccountDelegationInfo getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ServiceAccountDelegationInfoOrBuilder {
        private int authorityCase_ = 0;
        private Object authority_;
        private int bitField0_;
        private Object principalSubject_ = "";
        private SingleFieldBuilderV3<FirstPartyPrincipal, FirstPartyPrincipal.Builder, FirstPartyPrincipalOrBuilder> firstPartyPrincipalBuilder_;
        private SingleFieldBuilderV3<ThirdPartyPrincipal, ThirdPartyPrincipal.Builder, ThirdPartyPrincipalOrBuilder> thirdPartyPrincipalBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(ServiceAccountDelegationInfo.class, Builder.class);
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
            this.principalSubject_ = "";
            if (this.firstPartyPrincipalBuilder_ != null) {
                this.firstPartyPrincipalBuilder_.clear();
            }
            if (this.thirdPartyPrincipalBuilder_ != null) {
                this.thirdPartyPrincipalBuilder_.clear();
            }
            this.authorityCase_ = 0;
            this.authority_ = null;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_descriptor;
        }

        @Override
        public ServiceAccountDelegationInfo getDefaultInstanceForType() {
            return ServiceAccountDelegationInfo.getDefaultInstance();
        }

        @Override
        public ServiceAccountDelegationInfo build() {
            ServiceAccountDelegationInfo result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ServiceAccountDelegationInfo buildPartial() {
            ServiceAccountDelegationInfo result = new ServiceAccountDelegationInfo(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.buildPartialOneofs(result);
            this.onBuilt();
            return result;
        }

        private void buildPartial0(ServiceAccountDelegationInfo result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.principalSubject_ = this.principalSubject_;
            }
        }

        private void buildPartialOneofs(ServiceAccountDelegationInfo result) {
            result.authorityCase_ = this.authorityCase_;
            result.authority_ = this.authority_;
            if (this.authorityCase_ == 1 && this.firstPartyPrincipalBuilder_ != null) {
                result.authority_ = this.firstPartyPrincipalBuilder_.build();
            }
            if (this.authorityCase_ == 2 && this.thirdPartyPrincipalBuilder_ != null) {
                result.authority_ = this.thirdPartyPrincipalBuilder_.build();
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
            if (other instanceof ServiceAccountDelegationInfo) {
                return this.mergeFrom((ServiceAccountDelegationInfo)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ServiceAccountDelegationInfo other) {
            if (other == ServiceAccountDelegationInfo.getDefaultInstance()) {
                return this;
            }
            if (!other.getPrincipalSubject().isEmpty()) {
                this.principalSubject_ = other.principalSubject_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            switch (other.getAuthorityCase()) {
                case FIRST_PARTY_PRINCIPAL: {
                    this.mergeFirstPartyPrincipal(other.getFirstPartyPrincipal());
                    break;
                }
                case THIRD_PARTY_PRINCIPAL: {
                    this.mergeThirdPartyPrincipal(other.getThirdPartyPrincipal());
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
                block11: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block11;
                        }
                        case 10: {
                            input.readMessage(this.getFirstPartyPrincipalFieldBuilder().getBuilder(), extensionRegistry);
                            this.authorityCase_ = 1;
                            continue block11;
                        }
                        case 18: {
                            input.readMessage(this.getThirdPartyPrincipalFieldBuilder().getBuilder(), extensionRegistry);
                            this.authorityCase_ = 2;
                            continue block11;
                        }
                        case 26: {
                            this.principalSubject_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
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
        public AuthorityCase getAuthorityCase() {
            return AuthorityCase.forNumber(this.authorityCase_);
        }

        public Builder clearAuthority() {
            this.authorityCase_ = 0;
            this.authority_ = null;
            this.onChanged();
            return this;
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
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearPrincipalSubject() {
            this.principalSubject_ = ServiceAccountDelegationInfo.getDefaultInstance().getPrincipalSubject();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setPrincipalSubjectBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ServiceAccountDelegationInfo.checkByteStringIsUtf8(value);
            this.principalSubject_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasFirstPartyPrincipal() {
            return this.authorityCase_ == 1;
        }

        @Override
        public FirstPartyPrincipal getFirstPartyPrincipal() {
            if (this.firstPartyPrincipalBuilder_ == null) {
                if (this.authorityCase_ == 1) {
                    return (FirstPartyPrincipal)this.authority_;
                }
                return FirstPartyPrincipal.getDefaultInstance();
            }
            if (this.authorityCase_ == 1) {
                return this.firstPartyPrincipalBuilder_.getMessage();
            }
            return FirstPartyPrincipal.getDefaultInstance();
        }

        public Builder setFirstPartyPrincipal(FirstPartyPrincipal value) {
            if (this.firstPartyPrincipalBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.authority_ = value;
                this.onChanged();
            } else {
                this.firstPartyPrincipalBuilder_.setMessage(value);
            }
            this.authorityCase_ = 1;
            return this;
        }

        public Builder setFirstPartyPrincipal(FirstPartyPrincipal.Builder builderForValue) {
            if (this.firstPartyPrincipalBuilder_ == null) {
                this.authority_ = builderForValue.build();
                this.onChanged();
            } else {
                this.firstPartyPrincipalBuilder_.setMessage(builderForValue.build());
            }
            this.authorityCase_ = 1;
            return this;
        }

        public Builder mergeFirstPartyPrincipal(FirstPartyPrincipal value) {
            if (this.firstPartyPrincipalBuilder_ == null) {
                this.authority_ = this.authorityCase_ == 1 && this.authority_ != FirstPartyPrincipal.getDefaultInstance() ? FirstPartyPrincipal.newBuilder((FirstPartyPrincipal)this.authority_).mergeFrom(value).buildPartial() : value;
                this.onChanged();
            } else if (this.authorityCase_ == 1) {
                this.firstPartyPrincipalBuilder_.mergeFrom(value);
            } else {
                this.firstPartyPrincipalBuilder_.setMessage(value);
            }
            this.authorityCase_ = 1;
            return this;
        }

        public Builder clearFirstPartyPrincipal() {
            if (this.firstPartyPrincipalBuilder_ == null) {
                if (this.authorityCase_ == 1) {
                    this.authorityCase_ = 0;
                    this.authority_ = null;
                    this.onChanged();
                }
            } else {
                if (this.authorityCase_ == 1) {
                    this.authorityCase_ = 0;
                    this.authority_ = null;
                }
                this.firstPartyPrincipalBuilder_.clear();
            }
            return this;
        }

        public FirstPartyPrincipal.Builder getFirstPartyPrincipalBuilder() {
            return this.getFirstPartyPrincipalFieldBuilder().getBuilder();
        }

        @Override
        public FirstPartyPrincipalOrBuilder getFirstPartyPrincipalOrBuilder() {
            if (this.authorityCase_ == 1 && this.firstPartyPrincipalBuilder_ != null) {
                return this.firstPartyPrincipalBuilder_.getMessageOrBuilder();
            }
            if (this.authorityCase_ == 1) {
                return (FirstPartyPrincipal)this.authority_;
            }
            return FirstPartyPrincipal.getDefaultInstance();
        }

        private SingleFieldBuilderV3<FirstPartyPrincipal, FirstPartyPrincipal.Builder, FirstPartyPrincipalOrBuilder> getFirstPartyPrincipalFieldBuilder() {
            if (this.firstPartyPrincipalBuilder_ == null) {
                if (this.authorityCase_ != 1) {
                    this.authority_ = FirstPartyPrincipal.getDefaultInstance();
                }
                this.firstPartyPrincipalBuilder_ = new SingleFieldBuilderV3((FirstPartyPrincipal)this.authority_, this.getParentForChildren(), this.isClean());
                this.authority_ = null;
            }
            this.authorityCase_ = 1;
            this.onChanged();
            return this.firstPartyPrincipalBuilder_;
        }

        @Override
        public boolean hasThirdPartyPrincipal() {
            return this.authorityCase_ == 2;
        }

        @Override
        public ThirdPartyPrincipal getThirdPartyPrincipal() {
            if (this.thirdPartyPrincipalBuilder_ == null) {
                if (this.authorityCase_ == 2) {
                    return (ThirdPartyPrincipal)this.authority_;
                }
                return ThirdPartyPrincipal.getDefaultInstance();
            }
            if (this.authorityCase_ == 2) {
                return this.thirdPartyPrincipalBuilder_.getMessage();
            }
            return ThirdPartyPrincipal.getDefaultInstance();
        }

        public Builder setThirdPartyPrincipal(ThirdPartyPrincipal value) {
            if (this.thirdPartyPrincipalBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.authority_ = value;
                this.onChanged();
            } else {
                this.thirdPartyPrincipalBuilder_.setMessage(value);
            }
            this.authorityCase_ = 2;
            return this;
        }

        public Builder setThirdPartyPrincipal(ThirdPartyPrincipal.Builder builderForValue) {
            if (this.thirdPartyPrincipalBuilder_ == null) {
                this.authority_ = builderForValue.build();
                this.onChanged();
            } else {
                this.thirdPartyPrincipalBuilder_.setMessage(builderForValue.build());
            }
            this.authorityCase_ = 2;
            return this;
        }

        public Builder mergeThirdPartyPrincipal(ThirdPartyPrincipal value) {
            if (this.thirdPartyPrincipalBuilder_ == null) {
                this.authority_ = this.authorityCase_ == 2 && this.authority_ != ThirdPartyPrincipal.getDefaultInstance() ? ThirdPartyPrincipal.newBuilder((ThirdPartyPrincipal)this.authority_).mergeFrom(value).buildPartial() : value;
                this.onChanged();
            } else if (this.authorityCase_ == 2) {
                this.thirdPartyPrincipalBuilder_.mergeFrom(value);
            } else {
                this.thirdPartyPrincipalBuilder_.setMessage(value);
            }
            this.authorityCase_ = 2;
            return this;
        }

        public Builder clearThirdPartyPrincipal() {
            if (this.thirdPartyPrincipalBuilder_ == null) {
                if (this.authorityCase_ == 2) {
                    this.authorityCase_ = 0;
                    this.authority_ = null;
                    this.onChanged();
                }
            } else {
                if (this.authorityCase_ == 2) {
                    this.authorityCase_ = 0;
                    this.authority_ = null;
                }
                this.thirdPartyPrincipalBuilder_.clear();
            }
            return this;
        }

        public ThirdPartyPrincipal.Builder getThirdPartyPrincipalBuilder() {
            return this.getThirdPartyPrincipalFieldBuilder().getBuilder();
        }

        @Override
        public ThirdPartyPrincipalOrBuilder getThirdPartyPrincipalOrBuilder() {
            if (this.authorityCase_ == 2 && this.thirdPartyPrincipalBuilder_ != null) {
                return this.thirdPartyPrincipalBuilder_.getMessageOrBuilder();
            }
            if (this.authorityCase_ == 2) {
                return (ThirdPartyPrincipal)this.authority_;
            }
            return ThirdPartyPrincipal.getDefaultInstance();
        }

        private SingleFieldBuilderV3<ThirdPartyPrincipal, ThirdPartyPrincipal.Builder, ThirdPartyPrincipalOrBuilder> getThirdPartyPrincipalFieldBuilder() {
            if (this.thirdPartyPrincipalBuilder_ == null) {
                if (this.authorityCase_ != 2) {
                    this.authority_ = ThirdPartyPrincipal.getDefaultInstance();
                }
                this.thirdPartyPrincipalBuilder_ = new SingleFieldBuilderV3((ThirdPartyPrincipal)this.authority_, this.getParentForChildren(), this.isClean());
                this.authority_ = null;
            }
            this.authorityCase_ = 2;
            this.onChanged();
            return this.thirdPartyPrincipalBuilder_;
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

    public static enum AuthorityCase implements Internal.EnumLite,
    AbstractMessageLite.InternalOneOfEnum
    {
        FIRST_PARTY_PRINCIPAL(1),
        THIRD_PARTY_PRINCIPAL(2),
        AUTHORITY_NOT_SET(0);

        private final int value;

        private AuthorityCase(int value) {
            this.value = value;
        }

        @Deprecated
        public static AuthorityCase valueOf(int value) {
            return AuthorityCase.forNumber(value);
        }

        public static AuthorityCase forNumber(int value) {
            switch (value) {
                case 1: {
                    return FIRST_PARTY_PRINCIPAL;
                }
                case 2: {
                    return THIRD_PARTY_PRINCIPAL;
                }
                case 0: {
                    return AUTHORITY_NOT_SET;
                }
            }
            return null;
        }

        @Override
        public int getNumber() {
            return this.value;
        }
    }

    public static final class FirstPartyPrincipal
    extends GeneratedMessageV3
    implements FirstPartyPrincipalOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int PRINCIPAL_EMAIL_FIELD_NUMBER = 1;
        private volatile Object principalEmail_ = "";
        public static final int SERVICE_METADATA_FIELD_NUMBER = 2;
        private Struct serviceMetadata_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final FirstPartyPrincipal DEFAULT_INSTANCE = new FirstPartyPrincipal();
        private static final Parser<FirstPartyPrincipal> PARSER = new AbstractParser<FirstPartyPrincipal>(){

            @Override
            public FirstPartyPrincipal parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = FirstPartyPrincipal.newBuilder();
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

        private FirstPartyPrincipal(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private FirstPartyPrincipal() {
            this.principalEmail_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new FirstPartyPrincipal();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_FirstPartyPrincipal_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_FirstPartyPrincipal_fieldAccessorTable.ensureFieldAccessorsInitialized(FirstPartyPrincipal.class, Builder.class);
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
        public boolean hasServiceMetadata() {
            return this.serviceMetadata_ != null;
        }

        @Override
        public Struct getServiceMetadata() {
            return this.serviceMetadata_ == null ? Struct.getDefaultInstance() : this.serviceMetadata_;
        }

        @Override
        public StructOrBuilder getServiceMetadataOrBuilder() {
            return this.serviceMetadata_ == null ? Struct.getDefaultInstance() : this.serviceMetadata_;
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
            if (this.serviceMetadata_ != null) {
                output.writeMessage(2, this.getServiceMetadata());
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
            if (this.serviceMetadata_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getServiceMetadata());
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof FirstPartyPrincipal)) {
                return super.equals(obj);
            }
            FirstPartyPrincipal other = (FirstPartyPrincipal)obj;
            if (!this.getPrincipalEmail().equals(other.getPrincipalEmail())) {
                return false;
            }
            if (this.hasServiceMetadata() != other.hasServiceMetadata()) {
                return false;
            }
            if (this.hasServiceMetadata() && !this.getServiceMetadata().equals(other.getServiceMetadata())) {
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
            hash = 19 * hash + FirstPartyPrincipal.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getPrincipalEmail().hashCode();
            if (this.hasServiceMetadata()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getServiceMetadata().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static FirstPartyPrincipal parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FirstPartyPrincipal parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FirstPartyPrincipal parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FirstPartyPrincipal parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FirstPartyPrincipal parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FirstPartyPrincipal parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FirstPartyPrincipal parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FirstPartyPrincipal parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FirstPartyPrincipal parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FirstPartyPrincipal parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FirstPartyPrincipal parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FirstPartyPrincipal parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return FirstPartyPrincipal.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FirstPartyPrincipal prototype) {
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

        public static FirstPartyPrincipal getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FirstPartyPrincipal> parser() {
            return PARSER;
        }

        public Parser<FirstPartyPrincipal> getParserForType() {
            return PARSER;
        }

        @Override
        public FirstPartyPrincipal getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements FirstPartyPrincipalOrBuilder {
            private int bitField0_;
            private Object principalEmail_ = "";
            private Struct serviceMetadata_;
            private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> serviceMetadataBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_FirstPartyPrincipal_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_FirstPartyPrincipal_fieldAccessorTable.ensureFieldAccessorsInitialized(FirstPartyPrincipal.class, Builder.class);
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
                this.serviceMetadata_ = null;
                if (this.serviceMetadataBuilder_ != null) {
                    this.serviceMetadataBuilder_.dispose();
                    this.serviceMetadataBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_FirstPartyPrincipal_descriptor;
            }

            @Override
            public FirstPartyPrincipal getDefaultInstanceForType() {
                return FirstPartyPrincipal.getDefaultInstance();
            }

            @Override
            public FirstPartyPrincipal build() {
                FirstPartyPrincipal result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public FirstPartyPrincipal buildPartial() {
                FirstPartyPrincipal result = new FirstPartyPrincipal(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(FirstPartyPrincipal result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.principalEmail_ = this.principalEmail_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.serviceMetadata_ = this.serviceMetadataBuilder_ == null ? this.serviceMetadata_ : this.serviceMetadataBuilder_.build();
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
                if (other instanceof FirstPartyPrincipal) {
                    return this.mergeFrom((FirstPartyPrincipal)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FirstPartyPrincipal other) {
                if (other == FirstPartyPrincipal.getDefaultInstance()) {
                    return this;
                }
                if (!other.getPrincipalEmail().isEmpty()) {
                    this.principalEmail_ = other.principalEmail_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (other.hasServiceMetadata()) {
                    this.mergeServiceMetadata(other.getServiceMetadata());
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
                                this.principalEmail_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block10;
                            }
                            case 18: {
                                input.readMessage(this.getServiceMetadataFieldBuilder().getBuilder(), extensionRegistry);
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
                this.principalEmail_ = FirstPartyPrincipal.getDefaultInstance().getPrincipalEmail();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setPrincipalEmailBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FirstPartyPrincipal.checkByteStringIsUtf8(value);
                this.principalEmail_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasServiceMetadata() {
                return (this.bitField0_ & 2) != 0;
            }

            @Override
            public Struct getServiceMetadata() {
                if (this.serviceMetadataBuilder_ == null) {
                    return this.serviceMetadata_ == null ? Struct.getDefaultInstance() : this.serviceMetadata_;
                }
                return this.serviceMetadataBuilder_.getMessage();
            }

            public Builder setServiceMetadata(Struct value) {
                if (this.serviceMetadataBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.serviceMetadata_ = value;
                } else {
                    this.serviceMetadataBuilder_.setMessage(value);
                }
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder setServiceMetadata(Struct.Builder builderForValue) {
                if (this.serviceMetadataBuilder_ == null) {
                    this.serviceMetadata_ = builderForValue.build();
                } else {
                    this.serviceMetadataBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder mergeServiceMetadata(Struct value) {
                if (this.serviceMetadataBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 0 && this.serviceMetadata_ != null && this.serviceMetadata_ != Struct.getDefaultInstance()) {
                        this.getServiceMetadataBuilder().mergeFrom(value);
                    } else {
                        this.serviceMetadata_ = value;
                    }
                } else {
                    this.serviceMetadataBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearServiceMetadata() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.serviceMetadata_ = null;
                if (this.serviceMetadataBuilder_ != null) {
                    this.serviceMetadataBuilder_.dispose();
                    this.serviceMetadataBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Struct.Builder getServiceMetadataBuilder() {
                this.bitField0_ |= 2;
                this.onChanged();
                return this.getServiceMetadataFieldBuilder().getBuilder();
            }

            @Override
            public StructOrBuilder getServiceMetadataOrBuilder() {
                if (this.serviceMetadataBuilder_ != null) {
                    return this.serviceMetadataBuilder_.getMessageOrBuilder();
                }
                return this.serviceMetadata_ == null ? Struct.getDefaultInstance() : this.serviceMetadata_;
            }

            private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getServiceMetadataFieldBuilder() {
                if (this.serviceMetadataBuilder_ == null) {
                    this.serviceMetadataBuilder_ = new SingleFieldBuilderV3(this.getServiceMetadata(), this.getParentForChildren(), this.isClean());
                    this.serviceMetadata_ = null;
                }
                return this.serviceMetadataBuilder_;
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

    public static final class ThirdPartyPrincipal
    extends GeneratedMessageV3
    implements ThirdPartyPrincipalOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int THIRD_PARTY_CLAIMS_FIELD_NUMBER = 1;
        private Struct thirdPartyClaims_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ThirdPartyPrincipal DEFAULT_INSTANCE = new ThirdPartyPrincipal();
        private static final Parser<ThirdPartyPrincipal> PARSER = new AbstractParser<ThirdPartyPrincipal>(){

            @Override
            public ThirdPartyPrincipal parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = ThirdPartyPrincipal.newBuilder();
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

        private ThirdPartyPrincipal(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ThirdPartyPrincipal() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new ThirdPartyPrincipal();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_ThirdPartyPrincipal_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_ThirdPartyPrincipal_fieldAccessorTable.ensureFieldAccessorsInitialized(ThirdPartyPrincipal.class, Builder.class);
        }

        @Override
        public boolean hasThirdPartyClaims() {
            return this.thirdPartyClaims_ != null;
        }

        @Override
        public Struct getThirdPartyClaims() {
            return this.thirdPartyClaims_ == null ? Struct.getDefaultInstance() : this.thirdPartyClaims_;
        }

        @Override
        public StructOrBuilder getThirdPartyClaimsOrBuilder() {
            return this.thirdPartyClaims_ == null ? Struct.getDefaultInstance() : this.thirdPartyClaims_;
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
            if (this.thirdPartyClaims_ != null) {
                output.writeMessage(1, this.getThirdPartyClaims());
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
            if (this.thirdPartyClaims_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getThirdPartyClaims());
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ThirdPartyPrincipal)) {
                return super.equals(obj);
            }
            ThirdPartyPrincipal other = (ThirdPartyPrincipal)obj;
            if (this.hasThirdPartyClaims() != other.hasThirdPartyClaims()) {
                return false;
            }
            if (this.hasThirdPartyClaims() && !this.getThirdPartyClaims().equals(other.getThirdPartyClaims())) {
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
            hash = 19 * hash + ThirdPartyPrincipal.getDescriptor().hashCode();
            if (this.hasThirdPartyClaims()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getThirdPartyClaims().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static ThirdPartyPrincipal parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ThirdPartyPrincipal parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ThirdPartyPrincipal parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ThirdPartyPrincipal parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ThirdPartyPrincipal parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ThirdPartyPrincipal parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ThirdPartyPrincipal parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ThirdPartyPrincipal parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ThirdPartyPrincipal parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ThirdPartyPrincipal parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ThirdPartyPrincipal parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ThirdPartyPrincipal parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ThirdPartyPrincipal.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ThirdPartyPrincipal prototype) {
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

        public static ThirdPartyPrincipal getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ThirdPartyPrincipal> parser() {
            return PARSER;
        }

        public Parser<ThirdPartyPrincipal> getParserForType() {
            return PARSER;
        }

        @Override
        public ThirdPartyPrincipal getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ThirdPartyPrincipalOrBuilder {
            private int bitField0_;
            private Struct thirdPartyClaims_;
            private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> thirdPartyClaimsBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_ThirdPartyPrincipal_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_ThirdPartyPrincipal_fieldAccessorTable.ensureFieldAccessorsInitialized(ThirdPartyPrincipal.class, Builder.class);
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
                this.thirdPartyClaims_ = null;
                if (this.thirdPartyClaimsBuilder_ != null) {
                    this.thirdPartyClaimsBuilder_.dispose();
                    this.thirdPartyClaimsBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return AuditLogProto.internal_static_google_cloud_audit_ServiceAccountDelegationInfo_ThirdPartyPrincipal_descriptor;
            }

            @Override
            public ThirdPartyPrincipal getDefaultInstanceForType() {
                return ThirdPartyPrincipal.getDefaultInstance();
            }

            @Override
            public ThirdPartyPrincipal build() {
                ThirdPartyPrincipal result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ThirdPartyPrincipal buildPartial() {
                ThirdPartyPrincipal result = new ThirdPartyPrincipal(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(ThirdPartyPrincipal result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.thirdPartyClaims_ = this.thirdPartyClaimsBuilder_ == null ? this.thirdPartyClaims_ : this.thirdPartyClaimsBuilder_.build();
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
                if (other instanceof ThirdPartyPrincipal) {
                    return this.mergeFrom((ThirdPartyPrincipal)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ThirdPartyPrincipal other) {
                if (other == ThirdPartyPrincipal.getDefaultInstance()) {
                    return this;
                }
                if (other.hasThirdPartyClaims()) {
                    this.mergeThirdPartyClaims(other.getThirdPartyClaims());
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
                                input.readMessage(this.getThirdPartyClaimsFieldBuilder().getBuilder(), extensionRegistry);
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
            public boolean hasThirdPartyClaims() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public Struct getThirdPartyClaims() {
                if (this.thirdPartyClaimsBuilder_ == null) {
                    return this.thirdPartyClaims_ == null ? Struct.getDefaultInstance() : this.thirdPartyClaims_;
                }
                return this.thirdPartyClaimsBuilder_.getMessage();
            }

            public Builder setThirdPartyClaims(Struct value) {
                if (this.thirdPartyClaimsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.thirdPartyClaims_ = value;
                } else {
                    this.thirdPartyClaimsBuilder_.setMessage(value);
                }
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder setThirdPartyClaims(Struct.Builder builderForValue) {
                if (this.thirdPartyClaimsBuilder_ == null) {
                    this.thirdPartyClaims_ = builderForValue.build();
                } else {
                    this.thirdPartyClaimsBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder mergeThirdPartyClaims(Struct value) {
                if (this.thirdPartyClaimsBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 0 && this.thirdPartyClaims_ != null && this.thirdPartyClaims_ != Struct.getDefaultInstance()) {
                        this.getThirdPartyClaimsBuilder().mergeFrom(value);
                    } else {
                        this.thirdPartyClaims_ = value;
                    }
                } else {
                    this.thirdPartyClaimsBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearThirdPartyClaims() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.thirdPartyClaims_ = null;
                if (this.thirdPartyClaimsBuilder_ != null) {
                    this.thirdPartyClaimsBuilder_.dispose();
                    this.thirdPartyClaimsBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Struct.Builder getThirdPartyClaimsBuilder() {
                this.bitField0_ |= 1;
                this.onChanged();
                return this.getThirdPartyClaimsFieldBuilder().getBuilder();
            }

            @Override
            public StructOrBuilder getThirdPartyClaimsOrBuilder() {
                if (this.thirdPartyClaimsBuilder_ != null) {
                    return this.thirdPartyClaimsBuilder_.getMessageOrBuilder();
                }
                return this.thirdPartyClaims_ == null ? Struct.getDefaultInstance() : this.thirdPartyClaims_;
            }

            private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getThirdPartyClaimsFieldBuilder() {
                if (this.thirdPartyClaimsBuilder_ == null) {
                    this.thirdPartyClaimsBuilder_ = new SingleFieldBuilderV3(this.getThirdPartyClaims(), this.getParentForChildren(), this.isClean());
                    this.thirdPartyClaims_ = null;
                }
                return this.thirdPartyClaimsBuilder_;
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

    public static interface ThirdPartyPrincipalOrBuilder
    extends MessageOrBuilder {
        public boolean hasThirdPartyClaims();

        public Struct getThirdPartyClaims();

        public StructOrBuilder getThirdPartyClaimsOrBuilder();
    }

    public static interface FirstPartyPrincipalOrBuilder
    extends MessageOrBuilder {
        public String getPrincipalEmail();

        public ByteString getPrincipalEmailBytes();

        public boolean hasServiceMetadata();

        public Struct getServiceMetadata();

        public StructOrBuilder getServiceMetadataOrBuilder();
    }
}

