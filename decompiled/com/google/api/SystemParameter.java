/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.SystemParameterOrBuilder;
import com.google.api.SystemParameterProto;
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

public final class SystemParameter
extends GeneratedMessageV3
implements SystemParameterOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 1;
    private volatile Object name_ = "";
    public static final int HTTP_HEADER_FIELD_NUMBER = 2;
    private volatile Object httpHeader_ = "";
    public static final int URL_QUERY_PARAMETER_FIELD_NUMBER = 3;
    private volatile Object urlQueryParameter_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final SystemParameter DEFAULT_INSTANCE = new SystemParameter();
    private static final Parser<SystemParameter> PARSER = new AbstractParser<SystemParameter>(){

        @Override
        public SystemParameter parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = SystemParameter.newBuilder();
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

    private SystemParameter(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private SystemParameter() {
        this.name_ = "";
        this.httpHeader_ = "";
        this.urlQueryParameter_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new SystemParameter();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return SystemParameterProto.internal_static_google_api_SystemParameter_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return SystemParameterProto.internal_static_google_api_SystemParameter_fieldAccessorTable.ensureFieldAccessorsInitialized(SystemParameter.class, Builder.class);
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

    @Override
    public String getHttpHeader() {
        Object ref = this.httpHeader_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.httpHeader_ = s2;
        return s2;
    }

    @Override
    public ByteString getHttpHeaderBytes() {
        Object ref = this.httpHeader_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.httpHeader_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getUrlQueryParameter() {
        Object ref = this.urlQueryParameter_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.urlQueryParameter_ = s2;
        return s2;
    }

    @Override
    public ByteString getUrlQueryParameterBytes() {
        Object ref = this.urlQueryParameter_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.urlQueryParameter_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            GeneratedMessageV3.writeString(output, 1, this.name_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.httpHeader_)) {
            GeneratedMessageV3.writeString(output, 2, this.httpHeader_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.urlQueryParameter_)) {
            GeneratedMessageV3.writeString(output, 3, this.urlQueryParameter_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.httpHeader_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.httpHeader_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.urlQueryParameter_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.urlQueryParameter_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SystemParameter)) {
            return super.equals(obj);
        }
        SystemParameter other = (SystemParameter)obj;
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (!this.getHttpHeader().equals(other.getHttpHeader())) {
            return false;
        }
        if (!this.getUrlQueryParameter().equals(other.getUrlQueryParameter())) {
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
        hash = 19 * hash + SystemParameter.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getName().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getHttpHeader().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getUrlQueryParameter().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static SystemParameter parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static SystemParameter parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static SystemParameter parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static SystemParameter parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static SystemParameter parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static SystemParameter parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static SystemParameter parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static SystemParameter parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static SystemParameter parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static SystemParameter parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static SystemParameter parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static SystemParameter parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return SystemParameter.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(SystemParameter prototype) {
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

    public static SystemParameter getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<SystemParameter> parser() {
        return PARSER;
    }

    public Parser<SystemParameter> getParserForType() {
        return PARSER;
    }

    @Override
    public SystemParameter getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements SystemParameterOrBuilder {
        private int bitField0_;
        private Object name_ = "";
        private Object httpHeader_ = "";
        private Object urlQueryParameter_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return SystemParameterProto.internal_static_google_api_SystemParameter_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return SystemParameterProto.internal_static_google_api_SystemParameter_fieldAccessorTable.ensureFieldAccessorsInitialized(SystemParameter.class, Builder.class);
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
            this.httpHeader_ = "";
            this.urlQueryParameter_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return SystemParameterProto.internal_static_google_api_SystemParameter_descriptor;
        }

        @Override
        public SystemParameter getDefaultInstanceForType() {
            return SystemParameter.getDefaultInstance();
        }

        @Override
        public SystemParameter build() {
            SystemParameter result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public SystemParameter buildPartial() {
            SystemParameter result = new SystemParameter(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(SystemParameter result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.name_ = this.name_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.httpHeader_ = this.httpHeader_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.urlQueryParameter_ = this.urlQueryParameter_;
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
            if (other instanceof SystemParameter) {
                return this.mergeFrom((SystemParameter)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(SystemParameter other) {
            if (other == SystemParameter.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getHttpHeader().isEmpty()) {
                this.httpHeader_ = other.httpHeader_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (!other.getUrlQueryParameter().isEmpty()) {
                this.urlQueryParameter_ = other.urlQueryParameter_;
                this.bitField0_ |= 4;
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
                block11: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block11;
                        }
                        case 10: {
                            this.name_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block11;
                        }
                        case 18: {
                            this.httpHeader_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block11;
                        }
                        case 26: {
                            this.urlQueryParameter_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
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
            this.name_ = SystemParameter.getDefaultInstance().getName();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            SystemParameter.checkByteStringIsUtf8(value);
            this.name_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getHttpHeader() {
            Object ref = this.httpHeader_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.httpHeader_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getHttpHeaderBytes() {
            Object ref = this.httpHeader_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.httpHeader_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setHttpHeader(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.httpHeader_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearHttpHeader() {
            this.httpHeader_ = SystemParameter.getDefaultInstance().getHttpHeader();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setHttpHeaderBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            SystemParameter.checkByteStringIsUtf8(value);
            this.httpHeader_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public String getUrlQueryParameter() {
            Object ref = this.urlQueryParameter_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.urlQueryParameter_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getUrlQueryParameterBytes() {
            Object ref = this.urlQueryParameter_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.urlQueryParameter_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setUrlQueryParameter(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.urlQueryParameter_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearUrlQueryParameter() {
            this.urlQueryParameter_ = SystemParameter.getDefaultInstance().getUrlQueryParameter();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setUrlQueryParameterBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            SystemParameter.checkByteStringIsUtf8(value);
            this.urlQueryParameter_ = value;
            this.bitField0_ |= 4;
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

