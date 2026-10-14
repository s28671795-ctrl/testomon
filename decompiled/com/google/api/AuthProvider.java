/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.AuthProto;
import com.google.api.AuthProviderOrBuilder;
import com.google.api.JwtLocation;
import com.google.api.JwtLocationOrBuilder;
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
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AuthProvider
extends GeneratedMessageV3
implements AuthProviderOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int ID_FIELD_NUMBER = 1;
    private volatile Object id_ = "";
    public static final int ISSUER_FIELD_NUMBER = 2;
    private volatile Object issuer_ = "";
    public static final int JWKS_URI_FIELD_NUMBER = 3;
    private volatile Object jwksUri_ = "";
    public static final int AUDIENCES_FIELD_NUMBER = 4;
    private volatile Object audiences_ = "";
    public static final int AUTHORIZATION_URL_FIELD_NUMBER = 5;
    private volatile Object authorizationUrl_ = "";
    public static final int JWT_LOCATIONS_FIELD_NUMBER = 6;
    private List<JwtLocation> jwtLocations_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final AuthProvider DEFAULT_INSTANCE = new AuthProvider();
    private static final Parser<AuthProvider> PARSER = new AbstractParser<AuthProvider>(){

        @Override
        public AuthProvider parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = AuthProvider.newBuilder();
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

    private AuthProvider(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private AuthProvider() {
        this.id_ = "";
        this.issuer_ = "";
        this.jwksUri_ = "";
        this.audiences_ = "";
        this.authorizationUrl_ = "";
        this.jwtLocations_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new AuthProvider();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuthProto.internal_static_google_api_AuthProvider_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuthProto.internal_static_google_api_AuthProvider_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthProvider.class, Builder.class);
    }

    @Override
    public String getId() {
        Object ref = this.id_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.id_ = s2;
        return s2;
    }

    @Override
    public ByteString getIdBytes() {
        Object ref = this.id_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.id_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getIssuer() {
        Object ref = this.issuer_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.issuer_ = s2;
        return s2;
    }

    @Override
    public ByteString getIssuerBytes() {
        Object ref = this.issuer_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.issuer_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getJwksUri() {
        Object ref = this.jwksUri_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.jwksUri_ = s2;
        return s2;
    }

    @Override
    public ByteString getJwksUriBytes() {
        Object ref = this.jwksUri_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.jwksUri_ = b;
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
    public String getAuthorizationUrl() {
        Object ref = this.authorizationUrl_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.authorizationUrl_ = s2;
        return s2;
    }

    @Override
    public ByteString getAuthorizationUrlBytes() {
        Object ref = this.authorizationUrl_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.authorizationUrl_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public List<JwtLocation> getJwtLocationsList() {
        return this.jwtLocations_;
    }

    @Override
    public List<? extends JwtLocationOrBuilder> getJwtLocationsOrBuilderList() {
        return this.jwtLocations_;
    }

    @Override
    public int getJwtLocationsCount() {
        return this.jwtLocations_.size();
    }

    @Override
    public JwtLocation getJwtLocations(int index) {
        return this.jwtLocations_.get(index);
    }

    @Override
    public JwtLocationOrBuilder getJwtLocationsOrBuilder(int index) {
        return this.jwtLocations_.get(index);
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
        if (!GeneratedMessageV3.isStringEmpty(this.id_)) {
            GeneratedMessageV3.writeString(output, 1, this.id_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.issuer_)) {
            GeneratedMessageV3.writeString(output, 2, this.issuer_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.jwksUri_)) {
            GeneratedMessageV3.writeString(output, 3, this.jwksUri_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.audiences_)) {
            GeneratedMessageV3.writeString(output, 4, this.audiences_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.authorizationUrl_)) {
            GeneratedMessageV3.writeString(output, 5, this.authorizationUrl_);
        }
        for (int i = 0; i < this.jwtLocations_.size(); ++i) {
            output.writeMessage(6, this.jwtLocations_.get(i));
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
        if (!GeneratedMessageV3.isStringEmpty(this.id_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.id_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.issuer_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.issuer_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.jwksUri_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.jwksUri_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.audiences_)) {
            size += GeneratedMessageV3.computeStringSize(4, this.audiences_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.authorizationUrl_)) {
            size += GeneratedMessageV3.computeStringSize(5, this.authorizationUrl_);
        }
        for (int i = 0; i < this.jwtLocations_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(6, this.jwtLocations_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AuthProvider)) {
            return super.equals(obj);
        }
        AuthProvider other = (AuthProvider)obj;
        if (!this.getId().equals(other.getId())) {
            return false;
        }
        if (!this.getIssuer().equals(other.getIssuer())) {
            return false;
        }
        if (!this.getJwksUri().equals(other.getJwksUri())) {
            return false;
        }
        if (!this.getAudiences().equals(other.getAudiences())) {
            return false;
        }
        if (!this.getAuthorizationUrl().equals(other.getAuthorizationUrl())) {
            return false;
        }
        if (!this.getJwtLocationsList().equals(other.getJwtLocationsList())) {
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
        hash = 19 * hash + AuthProvider.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getId().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getIssuer().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getJwksUri().hashCode();
        hash = 37 * hash + 4;
        hash = 53 * hash + this.getAudiences().hashCode();
        hash = 37 * hash + 5;
        hash = 53 * hash + this.getAuthorizationUrl().hashCode();
        if (this.getJwtLocationsCount() > 0) {
            hash = 37 * hash + 6;
            hash = 53 * hash + this.getJwtLocationsList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static AuthProvider parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthProvider parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthProvider parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthProvider parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthProvider parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuthProvider parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuthProvider parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AuthProvider parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static AuthProvider parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static AuthProvider parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static AuthProvider parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AuthProvider parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return AuthProvider.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(AuthProvider prototype) {
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

    public static AuthProvider getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AuthProvider> parser() {
        return PARSER;
    }

    public Parser<AuthProvider> getParserForType() {
        return PARSER;
    }

    @Override
    public AuthProvider getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements AuthProviderOrBuilder {
        private int bitField0_;
        private Object id_ = "";
        private Object issuer_ = "";
        private Object jwksUri_ = "";
        private Object audiences_ = "";
        private Object authorizationUrl_ = "";
        private List<JwtLocation> jwtLocations_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<JwtLocation, JwtLocation.Builder, JwtLocationOrBuilder> jwtLocationsBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return AuthProto.internal_static_google_api_AuthProvider_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuthProto.internal_static_google_api_AuthProvider_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthProvider.class, Builder.class);
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
            this.id_ = "";
            this.issuer_ = "";
            this.jwksUri_ = "";
            this.audiences_ = "";
            this.authorizationUrl_ = "";
            if (this.jwtLocationsBuilder_ == null) {
                this.jwtLocations_ = Collections.emptyList();
            } else {
                this.jwtLocations_ = null;
                this.jwtLocationsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFDF;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuthProto.internal_static_google_api_AuthProvider_descriptor;
        }

        @Override
        public AuthProvider getDefaultInstanceForType() {
            return AuthProvider.getDefaultInstance();
        }

        @Override
        public AuthProvider build() {
            AuthProvider result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public AuthProvider buildPartial() {
            AuthProvider result = new AuthProvider(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(AuthProvider result) {
            if (this.jwtLocationsBuilder_ == null) {
                if ((this.bitField0_ & 0x20) != 0) {
                    this.jwtLocations_ = Collections.unmodifiableList(this.jwtLocations_);
                    this.bitField0_ &= 0xFFFFFFDF;
                }
                result.jwtLocations_ = this.jwtLocations_;
            } else {
                result.jwtLocations_ = this.jwtLocationsBuilder_.build();
            }
        }

        private void buildPartial0(AuthProvider result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.id_ = this.id_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.issuer_ = this.issuer_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.jwksUri_ = this.jwksUri_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.audiences_ = this.audiences_;
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.authorizationUrl_ = this.authorizationUrl_;
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
            if (other instanceof AuthProvider) {
                return this.mergeFrom((AuthProvider)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(AuthProvider other) {
            if (other == AuthProvider.getDefaultInstance()) {
                return this;
            }
            if (!other.getId().isEmpty()) {
                this.id_ = other.id_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getIssuer().isEmpty()) {
                this.issuer_ = other.issuer_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (!other.getJwksUri().isEmpty()) {
                this.jwksUri_ = other.jwksUri_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            if (!other.getAudiences().isEmpty()) {
                this.audiences_ = other.audiences_;
                this.bitField0_ |= 8;
                this.onChanged();
            }
            if (!other.getAuthorizationUrl().isEmpty()) {
                this.authorizationUrl_ = other.authorizationUrl_;
                this.bitField0_ |= 0x10;
                this.onChanged();
            }
            if (this.jwtLocationsBuilder_ == null) {
                if (!other.jwtLocations_.isEmpty()) {
                    if (this.jwtLocations_.isEmpty()) {
                        this.jwtLocations_ = other.jwtLocations_;
                        this.bitField0_ &= 0xFFFFFFDF;
                    } else {
                        this.ensureJwtLocationsIsMutable();
                        this.jwtLocations_.addAll(other.jwtLocations_);
                    }
                    this.onChanged();
                }
            } else if (!other.jwtLocations_.isEmpty()) {
                if (this.jwtLocationsBuilder_.isEmpty()) {
                    this.jwtLocationsBuilder_.dispose();
                    this.jwtLocationsBuilder_ = null;
                    this.jwtLocations_ = other.jwtLocations_;
                    this.bitField0_ &= 0xFFFFFFDF;
                    this.jwtLocationsBuilder_ = alwaysUseFieldBuilders ? this.getJwtLocationsFieldBuilder() : null;
                } else {
                    this.jwtLocationsBuilder_.addAllMessages(other.jwtLocations_);
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
                block14: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block14;
                        }
                        case 10: {
                            this.id_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block14;
                        }
                        case 18: {
                            this.issuer_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block14;
                        }
                        case 26: {
                            this.jwksUri_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block14;
                        }
                        case 34: {
                            this.audiences_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 8;
                            continue block14;
                        }
                        case 42: {
                            this.authorizationUrl_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x10;
                            continue block14;
                        }
                        case 50: {
                            JwtLocation m4 = input.readMessage(JwtLocation.parser(), extensionRegistry);
                            if (this.jwtLocationsBuilder_ == null) {
                                this.ensureJwtLocationsIsMutable();
                                this.jwtLocations_.add(m4);
                                continue block14;
                            }
                            this.jwtLocationsBuilder_.addMessage(m4);
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
        public String getId() {
            Object ref = this.id_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.id_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getIdBytes() {
            Object ref = this.id_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.id_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setId(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.id_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearId() {
            this.id_ = AuthProvider.getDefaultInstance().getId();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setIdBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuthProvider.checkByteStringIsUtf8(value);
            this.id_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getIssuer() {
            Object ref = this.issuer_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.issuer_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getIssuerBytes() {
            Object ref = this.issuer_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.issuer_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setIssuer(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.issuer_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearIssuer() {
            this.issuer_ = AuthProvider.getDefaultInstance().getIssuer();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setIssuerBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuthProvider.checkByteStringIsUtf8(value);
            this.issuer_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public String getJwksUri() {
            Object ref = this.jwksUri_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.jwksUri_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getJwksUriBytes() {
            Object ref = this.jwksUri_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.jwksUri_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setJwksUri(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.jwksUri_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearJwksUri() {
            this.jwksUri_ = AuthProvider.getDefaultInstance().getJwksUri();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setJwksUriBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuthProvider.checkByteStringIsUtf8(value);
            this.jwksUri_ = value;
            this.bitField0_ |= 4;
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
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearAudiences() {
            this.audiences_ = AuthProvider.getDefaultInstance().getAudiences();
            this.bitField0_ &= 0xFFFFFFF7;
            this.onChanged();
            return this;
        }

        public Builder setAudiencesBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuthProvider.checkByteStringIsUtf8(value);
            this.audiences_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        @Override
        public String getAuthorizationUrl() {
            Object ref = this.authorizationUrl_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.authorizationUrl_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getAuthorizationUrlBytes() {
            Object ref = this.authorizationUrl_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.authorizationUrl_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setAuthorizationUrl(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.authorizationUrl_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearAuthorizationUrl() {
            this.authorizationUrl_ = AuthProvider.getDefaultInstance().getAuthorizationUrl();
            this.bitField0_ &= 0xFFFFFFEF;
            this.onChanged();
            return this;
        }

        public Builder setAuthorizationUrlBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuthProvider.checkByteStringIsUtf8(value);
            this.authorizationUrl_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        private void ensureJwtLocationsIsMutable() {
            if ((this.bitField0_ & 0x20) == 0) {
                this.jwtLocations_ = new ArrayList<JwtLocation>(this.jwtLocations_);
                this.bitField0_ |= 0x20;
            }
        }

        @Override
        public List<JwtLocation> getJwtLocationsList() {
            if (this.jwtLocationsBuilder_ == null) {
                return Collections.unmodifiableList(this.jwtLocations_);
            }
            return this.jwtLocationsBuilder_.getMessageList();
        }

        @Override
        public int getJwtLocationsCount() {
            if (this.jwtLocationsBuilder_ == null) {
                return this.jwtLocations_.size();
            }
            return this.jwtLocationsBuilder_.getCount();
        }

        @Override
        public JwtLocation getJwtLocations(int index) {
            if (this.jwtLocationsBuilder_ == null) {
                return this.jwtLocations_.get(index);
            }
            return this.jwtLocationsBuilder_.getMessage(index);
        }

        public Builder setJwtLocations(int index, JwtLocation value) {
            if (this.jwtLocationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureJwtLocationsIsMutable();
                this.jwtLocations_.set(index, value);
                this.onChanged();
            } else {
                this.jwtLocationsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setJwtLocations(int index, JwtLocation.Builder builderForValue) {
            if (this.jwtLocationsBuilder_ == null) {
                this.ensureJwtLocationsIsMutable();
                this.jwtLocations_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.jwtLocationsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addJwtLocations(JwtLocation value) {
            if (this.jwtLocationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureJwtLocationsIsMutable();
                this.jwtLocations_.add(value);
                this.onChanged();
            } else {
                this.jwtLocationsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addJwtLocations(int index, JwtLocation value) {
            if (this.jwtLocationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureJwtLocationsIsMutable();
                this.jwtLocations_.add(index, value);
                this.onChanged();
            } else {
                this.jwtLocationsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addJwtLocations(JwtLocation.Builder builderForValue) {
            if (this.jwtLocationsBuilder_ == null) {
                this.ensureJwtLocationsIsMutable();
                this.jwtLocations_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.jwtLocationsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addJwtLocations(int index, JwtLocation.Builder builderForValue) {
            if (this.jwtLocationsBuilder_ == null) {
                this.ensureJwtLocationsIsMutable();
                this.jwtLocations_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.jwtLocationsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllJwtLocations(Iterable<? extends JwtLocation> values2) {
            if (this.jwtLocationsBuilder_ == null) {
                this.ensureJwtLocationsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.jwtLocations_);
                this.onChanged();
            } else {
                this.jwtLocationsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearJwtLocations() {
            if (this.jwtLocationsBuilder_ == null) {
                this.jwtLocations_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFDF;
                this.onChanged();
            } else {
                this.jwtLocationsBuilder_.clear();
            }
            return this;
        }

        public Builder removeJwtLocations(int index) {
            if (this.jwtLocationsBuilder_ == null) {
                this.ensureJwtLocationsIsMutable();
                this.jwtLocations_.remove(index);
                this.onChanged();
            } else {
                this.jwtLocationsBuilder_.remove(index);
            }
            return this;
        }

        public JwtLocation.Builder getJwtLocationsBuilder(int index) {
            return this.getJwtLocationsFieldBuilder().getBuilder(index);
        }

        @Override
        public JwtLocationOrBuilder getJwtLocationsOrBuilder(int index) {
            if (this.jwtLocationsBuilder_ == null) {
                return this.jwtLocations_.get(index);
            }
            return this.jwtLocationsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends JwtLocationOrBuilder> getJwtLocationsOrBuilderList() {
            if (this.jwtLocationsBuilder_ != null) {
                return this.jwtLocationsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.jwtLocations_);
        }

        public JwtLocation.Builder addJwtLocationsBuilder() {
            return this.getJwtLocationsFieldBuilder().addBuilder(JwtLocation.getDefaultInstance());
        }

        public JwtLocation.Builder addJwtLocationsBuilder(int index) {
            return this.getJwtLocationsFieldBuilder().addBuilder(index, JwtLocation.getDefaultInstance());
        }

        public List<JwtLocation.Builder> getJwtLocationsBuilderList() {
            return this.getJwtLocationsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<JwtLocation, JwtLocation.Builder, JwtLocationOrBuilder> getJwtLocationsFieldBuilder() {
            if (this.jwtLocationsBuilder_ == null) {
                this.jwtLocationsBuilder_ = new RepeatedFieldBuilderV3(this.jwtLocations_, (this.bitField0_ & 0x20) != 0, this.getParentForChildren(), this.isClean());
                this.jwtLocations_ = null;
            }
            return this.jwtLocationsBuilder_;
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

