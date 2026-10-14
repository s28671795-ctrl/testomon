/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.AuthProto;
import com.google.api.AuthRequirement;
import com.google.api.AuthRequirementOrBuilder;
import com.google.api.AuthenticationRuleOrBuilder;
import com.google.api.OAuthRequirements;
import com.google.api.OAuthRequirementsOrBuilder;
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
import com.google.protobuf.Parser;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AuthenticationRule
extends GeneratedMessageV3
implements AuthenticationRuleOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int SELECTOR_FIELD_NUMBER = 1;
    private volatile Object selector_ = "";
    public static final int OAUTH_FIELD_NUMBER = 2;
    private OAuthRequirements oauth_;
    public static final int ALLOW_WITHOUT_CREDENTIAL_FIELD_NUMBER = 5;
    private boolean allowWithoutCredential_ = false;
    public static final int REQUIREMENTS_FIELD_NUMBER = 7;
    private List<AuthRequirement> requirements_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final AuthenticationRule DEFAULT_INSTANCE = new AuthenticationRule();
    private static final Parser<AuthenticationRule> PARSER = new AbstractParser<AuthenticationRule>(){

        @Override
        public AuthenticationRule parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = AuthenticationRule.newBuilder();
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

    private AuthenticationRule(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private AuthenticationRule() {
        this.selector_ = "";
        this.requirements_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new AuthenticationRule();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuthProto.internal_static_google_api_AuthenticationRule_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuthProto.internal_static_google_api_AuthenticationRule_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthenticationRule.class, Builder.class);
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
    public boolean hasOauth() {
        return this.oauth_ != null;
    }

    @Override
    public OAuthRequirements getOauth() {
        return this.oauth_ == null ? OAuthRequirements.getDefaultInstance() : this.oauth_;
    }

    @Override
    public OAuthRequirementsOrBuilder getOauthOrBuilder() {
        return this.oauth_ == null ? OAuthRequirements.getDefaultInstance() : this.oauth_;
    }

    @Override
    public boolean getAllowWithoutCredential() {
        return this.allowWithoutCredential_;
    }

    @Override
    public List<AuthRequirement> getRequirementsList() {
        return this.requirements_;
    }

    @Override
    public List<? extends AuthRequirementOrBuilder> getRequirementsOrBuilderList() {
        return this.requirements_;
    }

    @Override
    public int getRequirementsCount() {
        return this.requirements_.size();
    }

    @Override
    public AuthRequirement getRequirements(int index) {
        return this.requirements_.get(index);
    }

    @Override
    public AuthRequirementOrBuilder getRequirementsOrBuilder(int index) {
        return this.requirements_.get(index);
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
        if (this.oauth_ != null) {
            output.writeMessage(2, this.getOauth());
        }
        if (this.allowWithoutCredential_) {
            output.writeBool(5, this.allowWithoutCredential_);
        }
        for (int i = 0; i < this.requirements_.size(); ++i) {
            output.writeMessage(7, this.requirements_.get(i));
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
        if (this.oauth_ != null) {
            size += CodedOutputStream.computeMessageSize(2, this.getOauth());
        }
        if (this.allowWithoutCredential_) {
            size += CodedOutputStream.computeBoolSize(5, this.allowWithoutCredential_);
        }
        for (int i = 0; i < this.requirements_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(7, this.requirements_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AuthenticationRule)) {
            return super.equals(obj);
        }
        AuthenticationRule other = (AuthenticationRule)obj;
        if (!this.getSelector().equals(other.getSelector())) {
            return false;
        }
        if (this.hasOauth() != other.hasOauth()) {
            return false;
        }
        if (this.hasOauth() && !this.getOauth().equals(other.getOauth())) {
            return false;
        }
        if (this.getAllowWithoutCredential() != other.getAllowWithoutCredential()) {
            return false;
        }
        if (!this.getRequirementsList().equals(other.getRequirementsList())) {
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
        hash = 19 * hash + AuthenticationRule.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getSelector().hashCode();
        if (this.hasOauth()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getOauth().hashCode();
        }
        hash = 37 * hash + 5;
        hash = 53 * hash + Internal.hashBoolean(this.getAllowWithoutCredential());
        if (this.getRequirementsCount() > 0) {
            hash = 37 * hash + 7;
            hash = 53 * hash + this.getRequirementsList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static AuthenticationRule parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthenticationRule parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthenticationRule parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthenticationRule parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthenticationRule parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthenticationRule parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthenticationRule parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AuthenticationRule parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static AuthenticationRule parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static AuthenticationRule parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static AuthenticationRule parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AuthenticationRule parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return AuthenticationRule.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(AuthenticationRule prototype) {
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

    public static AuthenticationRule getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AuthenticationRule> parser() {
        return PARSER;
    }

    public Parser<AuthenticationRule> getParserForType() {
        return PARSER;
    }

    @Override
    public AuthenticationRule getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements AuthenticationRuleOrBuilder {
        private int bitField0_;
        private Object selector_ = "";
        private OAuthRequirements oauth_;
        private SingleFieldBuilderV3<OAuthRequirements, OAuthRequirements.Builder, OAuthRequirementsOrBuilder> oauthBuilder_;
        private boolean allowWithoutCredential_;
        private List<AuthRequirement> requirements_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<AuthRequirement, AuthRequirement.Builder, AuthRequirementOrBuilder> requirementsBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return AuthProto.internal_static_google_api_AuthenticationRule_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuthProto.internal_static_google_api_AuthenticationRule_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthenticationRule.class, Builder.class);
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
            this.oauth_ = null;
            if (this.oauthBuilder_ != null) {
                this.oauthBuilder_.dispose();
                this.oauthBuilder_ = null;
            }
            this.allowWithoutCredential_ = false;
            if (this.requirementsBuilder_ == null) {
                this.requirements_ = Collections.emptyList();
            } else {
                this.requirements_ = null;
                this.requirementsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFF7;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuthProto.internal_static_google_api_AuthenticationRule_descriptor;
        }

        @Override
        public AuthenticationRule getDefaultInstanceForType() {
            return AuthenticationRule.getDefaultInstance();
        }

        @Override
        public AuthenticationRule build() {
            AuthenticationRule result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public AuthenticationRule buildPartial() {
            AuthenticationRule result = new AuthenticationRule(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(AuthenticationRule result) {
            if (this.requirementsBuilder_ == null) {
                if ((this.bitField0_ & 8) != 0) {
                    this.requirements_ = Collections.unmodifiableList(this.requirements_);
                    this.bitField0_ &= 0xFFFFFFF7;
                }
                result.requirements_ = this.requirements_;
            } else {
                result.requirements_ = this.requirementsBuilder_.build();
            }
        }

        private void buildPartial0(AuthenticationRule result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.selector_ = this.selector_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.oauth_ = this.oauthBuilder_ == null ? this.oauth_ : this.oauthBuilder_.build();
            }
            if ((from_bitField0_ & 4) != 0) {
                result.allowWithoutCredential_ = this.allowWithoutCredential_;
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
            if (other instanceof AuthenticationRule) {
                return this.mergeFrom((AuthenticationRule)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(AuthenticationRule other) {
            if (other == AuthenticationRule.getDefaultInstance()) {
                return this;
            }
            if (!other.getSelector().isEmpty()) {
                this.selector_ = other.selector_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (other.hasOauth()) {
                this.mergeOauth(other.getOauth());
            }
            if (other.getAllowWithoutCredential()) {
                this.setAllowWithoutCredential(other.getAllowWithoutCredential());
            }
            if (this.requirementsBuilder_ == null) {
                if (!other.requirements_.isEmpty()) {
                    if (this.requirements_.isEmpty()) {
                        this.requirements_ = other.requirements_;
                        this.bitField0_ &= 0xFFFFFFF7;
                    } else {
                        this.ensureRequirementsIsMutable();
                        this.requirements_.addAll(other.requirements_);
                    }
                    this.onChanged();
                }
            } else if (!other.requirements_.isEmpty()) {
                if (this.requirementsBuilder_.isEmpty()) {
                    this.requirementsBuilder_.dispose();
                    this.requirementsBuilder_ = null;
                    this.requirements_ = other.requirements_;
                    this.bitField0_ &= 0xFFFFFFF7;
                    this.requirementsBuilder_ = alwaysUseFieldBuilders ? this.getRequirementsFieldBuilder() : null;
                } else {
                    this.requirementsBuilder_.addAllMessages(other.requirements_);
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
                block12: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block12;
                        }
                        case 10: {
                            this.selector_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block12;
                        }
                        case 18: {
                            input.readMessage(this.getOauthFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 2;
                            continue block12;
                        }
                        case 40: {
                            this.allowWithoutCredential_ = input.readBool();
                            this.bitField0_ |= 4;
                            continue block12;
                        }
                        case 58: {
                            AuthRequirement m4 = input.readMessage(AuthRequirement.parser(), extensionRegistry);
                            if (this.requirementsBuilder_ == null) {
                                this.ensureRequirementsIsMutable();
                                this.requirements_.add(m4);
                                continue block12;
                            }
                            this.requirementsBuilder_.addMessage(m4);
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
            this.selector_ = AuthenticationRule.getDefaultInstance().getSelector();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setSelectorBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuthenticationRule.checkByteStringIsUtf8(value);
            this.selector_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasOauth() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override
        public OAuthRequirements getOauth() {
            if (this.oauthBuilder_ == null) {
                return this.oauth_ == null ? OAuthRequirements.getDefaultInstance() : this.oauth_;
            }
            return this.oauthBuilder_.getMessage();
        }

        public Builder setOauth(OAuthRequirements value) {
            if (this.oauthBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.oauth_ = value;
            } else {
                this.oauthBuilder_.setMessage(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder setOauth(OAuthRequirements.Builder builderForValue) {
            if (this.oauthBuilder_ == null) {
                this.oauth_ = builderForValue.build();
            } else {
                this.oauthBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder mergeOauth(OAuthRequirements value) {
            if (this.oauthBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0 && this.oauth_ != null && this.oauth_ != OAuthRequirements.getDefaultInstance()) {
                    this.getOauthBuilder().mergeFrom(value);
                } else {
                    this.oauth_ = value;
                }
            } else {
                this.oauthBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearOauth() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.oauth_ = null;
            if (this.oauthBuilder_ != null) {
                this.oauthBuilder_.dispose();
                this.oauthBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public OAuthRequirements.Builder getOauthBuilder() {
            this.bitField0_ |= 2;
            this.onChanged();
            return this.getOauthFieldBuilder().getBuilder();
        }

        @Override
        public OAuthRequirementsOrBuilder getOauthOrBuilder() {
            if (this.oauthBuilder_ != null) {
                return this.oauthBuilder_.getMessageOrBuilder();
            }
            return this.oauth_ == null ? OAuthRequirements.getDefaultInstance() : this.oauth_;
        }

        private SingleFieldBuilderV3<OAuthRequirements, OAuthRequirements.Builder, OAuthRequirementsOrBuilder> getOauthFieldBuilder() {
            if (this.oauthBuilder_ == null) {
                this.oauthBuilder_ = new SingleFieldBuilderV3(this.getOauth(), this.getParentForChildren(), this.isClean());
                this.oauth_ = null;
            }
            return this.oauthBuilder_;
        }

        @Override
        public boolean getAllowWithoutCredential() {
            return this.allowWithoutCredential_;
        }

        public Builder setAllowWithoutCredential(boolean value) {
            this.allowWithoutCredential_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearAllowWithoutCredential() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.allowWithoutCredential_ = false;
            this.onChanged();
            return this;
        }

        private void ensureRequirementsIsMutable() {
            if ((this.bitField0_ & 8) == 0) {
                this.requirements_ = new ArrayList<AuthRequirement>(this.requirements_);
                this.bitField0_ |= 8;
            }
        }

        @Override
        public List<AuthRequirement> getRequirementsList() {
            if (this.requirementsBuilder_ == null) {
                return Collections.unmodifiableList(this.requirements_);
            }
            return this.requirementsBuilder_.getMessageList();
        }

        @Override
        public int getRequirementsCount() {
            if (this.requirementsBuilder_ == null) {
                return this.requirements_.size();
            }
            return this.requirementsBuilder_.getCount();
        }

        @Override
        public AuthRequirement getRequirements(int index) {
            if (this.requirementsBuilder_ == null) {
                return this.requirements_.get(index);
            }
            return this.requirementsBuilder_.getMessage(index);
        }

        public Builder setRequirements(int index, AuthRequirement value) {
            if (this.requirementsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRequirementsIsMutable();
                this.requirements_.set(index, value);
                this.onChanged();
            } else {
                this.requirementsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setRequirements(int index, AuthRequirement.Builder builderForValue) {
            if (this.requirementsBuilder_ == null) {
                this.ensureRequirementsIsMutable();
                this.requirements_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.requirementsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addRequirements(AuthRequirement value) {
            if (this.requirementsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRequirementsIsMutable();
                this.requirements_.add(value);
                this.onChanged();
            } else {
                this.requirementsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addRequirements(int index, AuthRequirement value) {
            if (this.requirementsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRequirementsIsMutable();
                this.requirements_.add(index, value);
                this.onChanged();
            } else {
                this.requirementsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addRequirements(AuthRequirement.Builder builderForValue) {
            if (this.requirementsBuilder_ == null) {
                this.ensureRequirementsIsMutable();
                this.requirements_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.requirementsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addRequirements(int index, AuthRequirement.Builder builderForValue) {
            if (this.requirementsBuilder_ == null) {
                this.ensureRequirementsIsMutable();
                this.requirements_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.requirementsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllRequirements(Iterable<? extends AuthRequirement> values2) {
            if (this.requirementsBuilder_ == null) {
                this.ensureRequirementsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.requirements_);
                this.onChanged();
            } else {
                this.requirementsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearRequirements() {
            if (this.requirementsBuilder_ == null) {
                this.requirements_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFF7;
                this.onChanged();
            } else {
                this.requirementsBuilder_.clear();
            }
            return this;
        }

        public Builder removeRequirements(int index) {
            if (this.requirementsBuilder_ == null) {
                this.ensureRequirementsIsMutable();
                this.requirements_.remove(index);
                this.onChanged();
            } else {
                this.requirementsBuilder_.remove(index);
            }
            return this;
        }

        public AuthRequirement.Builder getRequirementsBuilder(int index) {
            return this.getRequirementsFieldBuilder().getBuilder(index);
        }

        @Override
        public AuthRequirementOrBuilder getRequirementsOrBuilder(int index) {
            if (this.requirementsBuilder_ == null) {
                return this.requirements_.get(index);
            }
            return this.requirementsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends AuthRequirementOrBuilder> getRequirementsOrBuilderList() {
            if (this.requirementsBuilder_ != null) {
                return this.requirementsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.requirements_);
        }

        public AuthRequirement.Builder addRequirementsBuilder() {
            return this.getRequirementsFieldBuilder().addBuilder(AuthRequirement.getDefaultInstance());
        }

        public AuthRequirement.Builder addRequirementsBuilder(int index) {
            return this.getRequirementsFieldBuilder().addBuilder(index, AuthRequirement.getDefaultInstance());
        }

        public List<AuthRequirement.Builder> getRequirementsBuilderList() {
            return this.getRequirementsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<AuthRequirement, AuthRequirement.Builder, AuthRequirementOrBuilder> getRequirementsFieldBuilder() {
            if (this.requirementsBuilder_ == null) {
                this.requirementsBuilder_ = new RepeatedFieldBuilderV3(this.requirements_, (this.bitField0_ & 8) != 0, this.getParentForChildren(), this.isClean());
                this.requirements_ = null;
            }
            return this.requirementsBuilder_;
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

