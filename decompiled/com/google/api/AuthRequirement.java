/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.AuthProto;
import com.google.api.AuthRequirementOrBuilder;
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

public final class AuthRequirement
extends GeneratedMessageV3
implements AuthRequirementOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int PROVIDER_ID_FIELD_NUMBER = 1;
    private volatile Object providerId_ = "";
    public static final int AUDIENCES_FIELD_NUMBER = 2;
    private volatile Object audiences_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final AuthRequirement DEFAULT_INSTANCE = new AuthRequirement();
    private static final Parser<AuthRequirement> PARSER = new AbstractParser<AuthRequirement>(){

        @Override
        public AuthRequirement parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = AuthRequirement.newBuilder();
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

    private AuthRequirement(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private AuthRequirement() {
        this.providerId_ = "";
        this.audiences_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new AuthRequirement();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuthProto.internal_static_google_api_AuthRequirement_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuthProto.internal_static_google_api_AuthRequirement_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthRequirement.class, Builder.class);
    }

    @Override
    public String getProviderId() {
        Object ref = this.providerId_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.providerId_ = s2;
        return s2;
    }

    @Override
    public ByteString getProviderIdBytes() {
        Object ref = this.providerId_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.providerId_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getAudiences() {
        Object ref = this.audiences_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.audiences_ = s2;
        return s2;
    }

    @Override
    public ByteString getAudiencesBytes() {
        Object ref = this.audiences_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.audiences_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.providerId_)) {
            GeneratedMessageV3.writeString(output, 1, this.providerId_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.audiences_)) {
            GeneratedMessageV3.writeString(output, 2, this.audiences_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.providerId_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.providerId_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.audiences_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.audiences_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AuthRequirement)) {
            return super.equals(obj);
        }
        AuthRequirement other = (AuthRequirement)obj;
        if (!this.getProviderId().equals(other.getProviderId())) {
            return false;
        }
        if (!this.getAudiences().equals(other.getAudiences())) {
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
        hash = 19 * hash + AuthRequirement.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getProviderId().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getAudiences().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static AuthRequirement parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthRequirement parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthRequirement parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthRequirement parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthRequirement parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthRequirement parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthRequirement parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AuthRequirement parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static AuthRequirement parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static AuthRequirement parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static AuthRequirement parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AuthRequirement parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return AuthRequirement.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(AuthRequirement prototype) {
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

    public static AuthRequirement getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AuthRequirement> parser() {
        return PARSER;
    }

    public Parser<AuthRequirement> getParserForType() {
        return PARSER;
    }

    @Override
    public AuthRequirement getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements AuthRequirementOrBuilder {
        private int bitField0_;
        private Object providerId_ = "";
        private Object audiences_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return AuthProto.internal_static_google_api_AuthRequirement_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuthProto.internal_static_google_api_AuthRequirement_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthRequirement.class, Builder.class);
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
            this.providerId_ = "";
            this.audiences_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuthProto.internal_static_google_api_AuthRequirement_descriptor;
        }

        @Override
        public AuthRequirement getDefaultInstanceForType() {
            return AuthRequirement.getDefaultInstance();
        }

        @Override
        public AuthRequirement build() {
            AuthRequirement result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public AuthRequirement buildPartial() {
            AuthRequirement result = new AuthRequirement(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(AuthRequirement result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.providerId_ = this.providerId_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.audiences_ = this.audiences_;
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
            if (other instanceof AuthRequirement) {
                return this.mergeFrom((AuthRequirement)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(AuthRequirement other) {
            if (other == AuthRequirement.getDefaultInstance()) {
                return this;
            }
            if (!other.getProviderId().isEmpty()) {
                this.providerId_ = other.providerId_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getAudiences().isEmpty()) {
                this.audiences_ = other.audiences_;
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
                            this.providerId_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            this.audiences_ = input.readStringRequireUtf8();
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
        public String getProviderId() {
            Object ref = this.providerId_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.providerId_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getProviderIdBytes() {
            Object ref = this.providerId_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.providerId_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setProviderId(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.providerId_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearProviderId() {
            this.providerId_ = AuthRequirement.getDefaultInstance().getProviderId();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setProviderIdBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuthRequirement.checkByteStringIsUtf8(value);
            this.providerId_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getAudiences() {
            Object ref = this.audiences_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.audiences_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getAudiencesBytes() {
            Object ref = this.audiences_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.audiences_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setAudiences(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.audiences_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearAudiences() {
            this.audiences_ = AuthRequirement.getDefaultInstance().getAudiences();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setAudiencesBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuthRequirement.checkByteStringIsUtf8(value);
            this.audiences_ = value;
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

