/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.EndpointOrBuilder;
import com.google.api.EndpointProto;
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
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Endpoint
extends GeneratedMessageV3
implements EndpointOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 1;
    private volatile Object name_ = "";
    public static final int ALIASES_FIELD_NUMBER = 2;
    private LazyStringArrayList aliases_ = LazyStringArrayList.emptyList();
    public static final int TARGET_FIELD_NUMBER = 101;
    private volatile Object target_ = "";
    public static final int ALLOW_CORS_FIELD_NUMBER = 5;
    private boolean allowCors_ = false;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Endpoint DEFAULT_INSTANCE = new Endpoint();
    private static final Parser<Endpoint> PARSER = new AbstractParser<Endpoint>(){

        @Override
        public Endpoint parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Endpoint.newBuilder();
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

    private Endpoint(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Endpoint() {
        this.name_ = "";
        this.aliases_ = LazyStringArrayList.emptyList();
        this.target_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Endpoint();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return EndpointProto.internal_static_google_api_Endpoint_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return EndpointProto.internal_static_google_api_Endpoint_fieldAccessorTable.ensureFieldAccessorsInitialized(Endpoint.class, Builder.class);
    }

    @Override
    public String getName() {
        Object ref = this.name_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.name_ = s2;
        return s2;
    }

    @Override
    public ByteString getNameBytes() {
        Object ref = this.name_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.name_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Deprecated
    public ProtocolStringList getAliasesList() {
        return this.aliases_;
    }

    @Override
    @Deprecated
    public int getAliasesCount() {
        return this.aliases_.size();
    }

    @Override
    @Deprecated
    public String getAliases(int index) {
        return this.aliases_.get(index);
    }

    @Override
    @Deprecated
    public ByteString getAliasesBytes(int index) {
        return this.aliases_.getByteString(index);
    }

    @Override
    public String getTarget() {
        Object ref = this.target_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.target_ = s2;
        return s2;
    }

    @Override
    public ByteString getTargetBytes() {
        Object ref = this.target_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.target_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean getAllowCors() {
        return this.allowCors_;
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
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            GeneratedMessageV3.writeString(output, 1, this.name_);
        }
        for (int i = 0; i < this.aliases_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 2, this.aliases_.getRaw(i));
        }
        if (this.allowCors_) {
            output.writeBool(5, this.allowCors_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.target_)) {
            GeneratedMessageV3.writeString(output, 101, this.target_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.name_);
        }
        int dataSize = 0;
        for (int i = 0; i < this.aliases_.size(); ++i) {
            dataSize += Endpoint.computeStringSizeNoTag(this.aliases_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getAliasesList().size();
        if (this.allowCors_) {
            size += CodedOutputStream.computeBoolSize(5, this.allowCors_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.target_)) {
            size += GeneratedMessageV3.computeStringSize(101, this.target_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Endpoint)) {
            return super.equals(obj);
        }
        Endpoint other = (Endpoint)obj;
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (!this.getAliasesList().equals(other.getAliasesList())) {
            return false;
        }
        if (!this.getTarget().equals(other.getTarget())) {
            return false;
        }
        if (this.getAllowCors() != other.getAllowCors()) {
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
        hash = 19 * hash + Endpoint.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getName().hashCode();
        if (this.getAliasesCount() > 0) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getAliasesList().hashCode();
        }
        hash = 37 * hash + 101;
        hash = 53 * hash + this.getTarget().hashCode();
        hash = 37 * hash + 5;
        hash = 53 * hash + Internal.hashBoolean(this.getAllowCors());
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Endpoint parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Endpoint parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Endpoint parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Endpoint parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Endpoint parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Endpoint parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Endpoint parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Endpoint parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Endpoint parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Endpoint parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Endpoint parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Endpoint parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Endpoint.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Endpoint prototype) {
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

    public static Endpoint getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Endpoint> parser() {
        return PARSER;
    }

    public Parser<Endpoint> getParserForType() {
        return PARSER;
    }

    @Override
    public Endpoint getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements EndpointOrBuilder {
        private int bitField0_;
        private Object name_ = "";
        private LazyStringArrayList aliases_ = LazyStringArrayList.emptyList();
        private Object target_ = "";
        private boolean allowCors_;

        public static final Descriptors.Descriptor getDescriptor() {
            return EndpointProto.internal_static_google_api_Endpoint_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return EndpointProto.internal_static_google_api_Endpoint_fieldAccessorTable.ensureFieldAccessorsInitialized(Endpoint.class, Builder.class);
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
            this.name_ = "";
            this.aliases_ = LazyStringArrayList.emptyList();
            this.target_ = "";
            this.allowCors_ = false;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return EndpointProto.internal_static_google_api_Endpoint_descriptor;
        }

        @Override
        public Endpoint getDefaultInstanceForType() {
            return Endpoint.getDefaultInstance();
        }

        @Override
        public Endpoint build() {
            Endpoint result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Endpoint buildPartial() {
            Endpoint result = new Endpoint(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(Endpoint result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.name_ = this.name_;
            }
            if ((from_bitField0_ & 2) != 0) {
                this.aliases_.makeImmutable();
                result.aliases_ = this.aliases_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.target_ = this.target_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.allowCors_ = this.allowCors_;
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
            if (other instanceof Endpoint) {
                return this.mergeFrom((Endpoint)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Endpoint other) {
            if (other == Endpoint.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.aliases_.isEmpty()) {
                if (this.aliases_.isEmpty()) {
                    this.aliases_ = other.aliases_;
                    this.bitField0_ |= 2;
                } else {
                    this.ensureAliasesIsMutable();
                    this.aliases_.addAll(other.aliases_);
                }
                this.onChanged();
            }
            if (!other.getTarget().isEmpty()) {
                this.target_ = other.target_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            if (other.getAllowCors()) {
                this.setAllowCors(other.getAllowCors());
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
                            this.name_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block12;
                        }
                        case 18: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensureAliasesIsMutable();
                            this.aliases_.add(s2);
                            continue block12;
                        }
                        case 40: {
                            this.allowCors_ = input.readBool();
                            this.bitField0_ |= 8;
                            continue block12;
                        }
                        case 810: {
                            this.target_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
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
        public String getName() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.name_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.name_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.name_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearName() {
            this.name_ = Endpoint.getDefaultInstance().getName();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Endpoint.checkByteStringIsUtf8(value);
            this.name_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        private void ensureAliasesIsMutable() {
            if (!this.aliases_.isModifiable()) {
                this.aliases_ = new LazyStringArrayList(this.aliases_);
            }
            this.bitField0_ |= 2;
        }

        @Deprecated
        public ProtocolStringList getAliasesList() {
            this.aliases_.makeImmutable();
            return this.aliases_;
        }

        @Override
        @Deprecated
        public int getAliasesCount() {
            return this.aliases_.size();
        }

        @Override
        @Deprecated
        public String getAliases(int index) {
            return this.aliases_.get(index);
        }

        @Override
        @Deprecated
        public ByteString getAliasesBytes(int index) {
            return this.aliases_.getByteString(index);
        }

        @Deprecated
        public Builder setAliases(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureAliasesIsMutable();
            this.aliases_.set(index, value);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Deprecated
        public Builder addAliases(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureAliasesIsMutable();
            this.aliases_.add(value);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Deprecated
        public Builder addAllAliases(Iterable<String> values2) {
            this.ensureAliasesIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.aliases_);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Deprecated
        public Builder clearAliases() {
            this.aliases_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        @Deprecated
        public Builder addAliasesBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Endpoint.checkByteStringIsUtf8(value);
            this.ensureAliasesIsMutable();
            this.aliases_.add(value);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public String getTarget() {
            Object ref = this.target_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.target_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getTargetBytes() {
            Object ref = this.target_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.target_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setTarget(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.target_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearTarget() {
            this.target_ = Endpoint.getDefaultInstance().getTarget();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setTargetBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Endpoint.checkByteStringIsUtf8(value);
            this.target_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        @Override
        public boolean getAllowCors() {
            return this.allowCors_;
        }

        public Builder setAllowCors(boolean value) {
            this.allowCors_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearAllowCors() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.allowCors_ = false;
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

