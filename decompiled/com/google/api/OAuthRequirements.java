/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.AuthProto;
import com.google.api.OAuthRequirementsOrBuilder;
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

public final class OAuthRequirements
extends GeneratedMessageV3
implements OAuthRequirementsOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int CANONICAL_SCOPES_FIELD_NUMBER = 1;
    private volatile Object canonicalScopes_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final OAuthRequirements DEFAULT_INSTANCE = new OAuthRequirements();
    private static final Parser<OAuthRequirements> PARSER = new AbstractParser<OAuthRequirements>(){

        @Override
        public OAuthRequirements parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = OAuthRequirements.newBuilder();
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

    private OAuthRequirements(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private OAuthRequirements() {
        this.canonicalScopes_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new OAuthRequirements();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuthProto.internal_static_google_api_OAuthRequirements_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuthProto.internal_static_google_api_OAuthRequirements_fieldAccessorTable.ensureFieldAccessorsInitialized(OAuthRequirements.class, Builder.class);
    }

    @Override
    public String getCanonicalScopes() {
        Object ref = this.canonicalScopes_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.canonicalScopes_ = s2;
        return s2;
    }

    @Override
    public ByteString getCanonicalScopesBytes() {
        Object ref = this.canonicalScopes_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.canonicalScopes_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.canonicalScopes_)) {
            GeneratedMessageV3.writeString(output, 1, this.canonicalScopes_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.canonicalScopes_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.canonicalScopes_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OAuthRequirements)) {
            return super.equals(obj);
        }
        OAuthRequirements other = (OAuthRequirements)obj;
        if (!this.getCanonicalScopes().equals(other.getCanonicalScopes())) {
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
        hash = 19 * hash + OAuthRequirements.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getCanonicalScopes().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static OAuthRequirements parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static OAuthRequirements parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static OAuthRequirements parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static OAuthRequirements parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static OAuthRequirements parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static OAuthRequirements parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static OAuthRequirements parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static OAuthRequirements parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static OAuthRequirements parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static OAuthRequirements parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static OAuthRequirements parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static OAuthRequirements parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return OAuthRequirements.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(OAuthRequirements prototype) {
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

    public static OAuthRequirements getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<OAuthRequirements> parser() {
        return PARSER;
    }

    public Parser<OAuthRequirements> getParserForType() {
        return PARSER;
    }

    @Override
    public OAuthRequirements getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements OAuthRequirementsOrBuilder {
        private int bitField0_;
        private Object canonicalScopes_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return AuthProto.internal_static_google_api_OAuthRequirements_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuthProto.internal_static_google_api_OAuthRequirements_fieldAccessorTable.ensureFieldAccessorsInitialized(OAuthRequirements.class, Builder.class);
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
            this.canonicalScopes_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuthProto.internal_static_google_api_OAuthRequirements_descriptor;
        }

        @Override
        public OAuthRequirements getDefaultInstanceForType() {
            return OAuthRequirements.getDefaultInstance();
        }

        @Override
        public OAuthRequirements build() {
            OAuthRequirements result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public OAuthRequirements buildPartial() {
            OAuthRequirements result = new OAuthRequirements(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(OAuthRequirements result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.canonicalScopes_ = this.canonicalScopes_;
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
            if (other instanceof OAuthRequirements) {
                return this.mergeFrom((OAuthRequirements)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(OAuthRequirements other) {
            if (other == OAuthRequirements.getDefaultInstance()) {
                return this;
            }
            if (!other.getCanonicalScopes().isEmpty()) {
                this.canonicalScopes_ = other.canonicalScopes_;
                this.bitField0_ |= 1;
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
                block9: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block9;
                        }
                        case 10: {
                            this.canonicalScopes_ = input.readStringRequireUtf8();
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
        public String getCanonicalScopes() {
            Object ref = this.canonicalScopes_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.canonicalScopes_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getCanonicalScopesBytes() {
            Object ref = this.canonicalScopes_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.canonicalScopes_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setCanonicalScopes(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.canonicalScopes_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearCanonicalScopes() {
            this.canonicalScopes_ = OAuthRequirements.getDefaultInstance().getCanonicalScopes();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setCanonicalScopesBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            OAuthRequirements.checkByteStringIsUtf8(value);
            this.canonicalScopes_ = value;
            this.bitField0_ |= 1;
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

