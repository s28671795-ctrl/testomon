/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.RoutingParameterOrBuilder;
import com.google.api.RoutingProto;
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

public final class RoutingParameter
extends GeneratedMessageV3
implements RoutingParameterOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int FIELD_FIELD_NUMBER = 1;
    private volatile Object field_ = "";
    public static final int PATH_TEMPLATE_FIELD_NUMBER = 2;
    private volatile Object pathTemplate_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final RoutingParameter DEFAULT_INSTANCE = new RoutingParameter();
    private static final Parser<RoutingParameter> PARSER = new AbstractParser<RoutingParameter>(){

        @Override
        public RoutingParameter parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = RoutingParameter.newBuilder();
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

    private RoutingParameter(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private RoutingParameter() {
        this.field_ = "";
        this.pathTemplate_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new RoutingParameter();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return RoutingProto.internal_static_google_api_RoutingParameter_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return RoutingProto.internal_static_google_api_RoutingParameter_fieldAccessorTable.ensureFieldAccessorsInitialized(RoutingParameter.class, Builder.class);
    }

    @Override
    public String getField() {
        Object ref = this.field_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.field_ = s2;
        return s2;
    }

    @Override
    public ByteString getFieldBytes() {
        Object ref = this.field_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.field_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getPathTemplate() {
        Object ref = this.pathTemplate_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.pathTemplate_ = s2;
        return s2;
    }

    @Override
    public ByteString getPathTemplateBytes() {
        Object ref = this.pathTemplate_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.pathTemplate_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.field_)) {
            GeneratedMessageV3.writeString(output, 1, this.field_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.pathTemplate_)) {
            GeneratedMessageV3.writeString(output, 2, this.pathTemplate_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.field_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.field_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.pathTemplate_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.pathTemplate_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RoutingParameter)) {
            return super.equals(obj);
        }
        RoutingParameter other = (RoutingParameter)obj;
        if (!this.getField().equals(other.getField())) {
            return false;
        }
        if (!this.getPathTemplate().equals(other.getPathTemplate())) {
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
        hash = 19 * hash + RoutingParameter.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getField().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getPathTemplate().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static RoutingParameter parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RoutingParameter parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RoutingParameter parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RoutingParameter parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RoutingParameter parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RoutingParameter parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RoutingParameter parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static RoutingParameter parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static RoutingParameter parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static RoutingParameter parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static RoutingParameter parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static RoutingParameter parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return RoutingParameter.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(RoutingParameter prototype) {
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

    public static RoutingParameter getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<RoutingParameter> parser() {
        return PARSER;
    }

    public Parser<RoutingParameter> getParserForType() {
        return PARSER;
    }

    @Override
    public RoutingParameter getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements RoutingParameterOrBuilder {
        private int bitField0_;
        private Object field_ = "";
        private Object pathTemplate_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return RoutingProto.internal_static_google_api_RoutingParameter_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return RoutingProto.internal_static_google_api_RoutingParameter_fieldAccessorTable.ensureFieldAccessorsInitialized(RoutingParameter.class, Builder.class);
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
            this.field_ = "";
            this.pathTemplate_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return RoutingProto.internal_static_google_api_RoutingParameter_descriptor;
        }

        @Override
        public RoutingParameter getDefaultInstanceForType() {
            return RoutingParameter.getDefaultInstance();
        }

        @Override
        public RoutingParameter build() {
            RoutingParameter result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public RoutingParameter buildPartial() {
            RoutingParameter result = new RoutingParameter(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(RoutingParameter result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.field_ = this.field_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.pathTemplate_ = this.pathTemplate_;
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
            if (other instanceof RoutingParameter) {
                return this.mergeFrom((RoutingParameter)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(RoutingParameter other) {
            if (other == RoutingParameter.getDefaultInstance()) {
                return this;
            }
            if (!other.getField().isEmpty()) {
                this.field_ = other.field_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getPathTemplate().isEmpty()) {
                this.pathTemplate_ = other.pathTemplate_;
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
                            this.field_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            this.pathTemplate_ = input.readStringRequireUtf8();
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
        public String getField() {
            Object ref = this.field_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.field_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getFieldBytes() {
            Object ref = this.field_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.field_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setField(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.field_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearField() {
            this.field_ = RoutingParameter.getDefaultInstance().getField();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setFieldBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            RoutingParameter.checkByteStringIsUtf8(value);
            this.field_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getPathTemplate() {
            Object ref = this.pathTemplate_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.pathTemplate_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getPathTemplateBytes() {
            Object ref = this.pathTemplate_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.pathTemplate_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setPathTemplate(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.pathTemplate_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearPathTemplate() {
            this.pathTemplate_ = RoutingParameter.getDefaultInstance().getPathTemplate();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setPathTemplateBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            RoutingParameter.checkByteStringIsUtf8(value);
            this.pathTemplate_ = value;
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

