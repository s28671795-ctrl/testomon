/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ClientProto;
import com.google.api.CommonLanguageSettings;
import com.google.api.CommonLanguageSettingsOrBuilder;
import com.google.api.PythonSettingsOrBuilder;
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

public final class PythonSettings
extends GeneratedMessageV3
implements PythonSettingsOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int COMMON_FIELD_NUMBER = 1;
    private CommonLanguageSettings common_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final PythonSettings DEFAULT_INSTANCE = new PythonSettings();
    private static final Parser<PythonSettings> PARSER = new AbstractParser<PythonSettings>(){

        @Override
        public PythonSettings parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = PythonSettings.newBuilder();
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

    private PythonSettings(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private PythonSettings() {
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new PythonSettings();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ClientProto.internal_static_google_api_PythonSettings_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ClientProto.internal_static_google_api_PythonSettings_fieldAccessorTable.ensureFieldAccessorsInitialized(PythonSettings.class, Builder.class);
    }

    @Override
    public boolean hasCommon() {
        return this.common_ != null;
    }

    @Override
    public CommonLanguageSettings getCommon() {
        return this.common_ == null ? CommonLanguageSettings.getDefaultInstance() : this.common_;
    }

    @Override
    public CommonLanguageSettingsOrBuilder getCommonOrBuilder() {
        return this.common_ == null ? CommonLanguageSettings.getDefaultInstance() : this.common_;
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
        if (this.common_ != null) {
            output.writeMessage(1, this.getCommon());
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
        if (this.common_ != null) {
            size += CodedOutputStream.computeMessageSize(1, this.getCommon());
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PythonSettings)) {
            return super.equals(obj);
        }
        PythonSettings other = (PythonSettings)obj;
        if (this.hasCommon() != other.hasCommon()) {
            return false;
        }
        if (this.hasCommon() && !this.getCommon().equals(other.getCommon())) {
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
        hash = 19 * hash + PythonSettings.getDescriptor().hashCode();
        if (this.hasCommon()) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getCommon().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static PythonSettings parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static PythonSettings parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static PythonSettings parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static PythonSettings parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static PythonSettings parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static PythonSettings parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static PythonSettings parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static PythonSettings parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static PythonSettings parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static PythonSettings parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static PythonSettings parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static PythonSettings parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return PythonSettings.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(PythonSettings prototype) {
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

    public static PythonSettings getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<PythonSettings> parser() {
        return PARSER;
    }

    public Parser<PythonSettings> getParserForType() {
        return PARSER;
    }

    @Override
    public PythonSettings getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements PythonSettingsOrBuilder {
        private int bitField0_;
        private CommonLanguageSettings common_;
        private SingleFieldBuilderV3<CommonLanguageSettings, CommonLanguageSettings.Builder, CommonLanguageSettingsOrBuilder> commonBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return ClientProto.internal_static_google_api_PythonSettings_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ClientProto.internal_static_google_api_PythonSettings_fieldAccessorTable.ensureFieldAccessorsInitialized(PythonSettings.class, Builder.class);
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
            this.common_ = null;
            if (this.commonBuilder_ != null) {
                this.commonBuilder_.dispose();
                this.commonBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ClientProto.internal_static_google_api_PythonSettings_descriptor;
        }

        @Override
        public PythonSettings getDefaultInstanceForType() {
            return PythonSettings.getDefaultInstance();
        }

        @Override
        public PythonSettings build() {
            PythonSettings result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public PythonSettings buildPartial() {
            PythonSettings result = new PythonSettings(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(PythonSettings result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.common_ = this.commonBuilder_ == null ? this.common_ : this.commonBuilder_.build();
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
            if (other instanceof PythonSettings) {
                return this.mergeFrom((PythonSettings)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(PythonSettings other) {
            if (other == PythonSettings.getDefaultInstance()) {
                return this;
            }
            if (other.hasCommon()) {
                this.mergeCommon(other.getCommon());
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
                            input.readMessage(this.getCommonFieldBuilder().getBuilder(), extensionRegistry);
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
        public boolean hasCommon() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public CommonLanguageSettings getCommon() {
            if (this.commonBuilder_ == null) {
                return this.common_ == null ? CommonLanguageSettings.getDefaultInstance() : this.common_;
            }
            return this.commonBuilder_.getMessage();
        }

        public Builder setCommon(CommonLanguageSettings value) {
            if (this.commonBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.common_ = value;
            } else {
                this.commonBuilder_.setMessage(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder setCommon(CommonLanguageSettings.Builder builderForValue) {
            if (this.commonBuilder_ == null) {
                this.common_ = builderForValue.build();
            } else {
                this.commonBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder mergeCommon(CommonLanguageSettings value) {
            if (this.commonBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0 && this.common_ != null && this.common_ != CommonLanguageSettings.getDefaultInstance()) {
                    this.getCommonBuilder().mergeFrom(value);
                } else {
                    this.common_ = value;
                }
            } else {
                this.commonBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearCommon() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.common_ = null;
            if (this.commonBuilder_ != null) {
                this.commonBuilder_.dispose();
                this.commonBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public CommonLanguageSettings.Builder getCommonBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return this.getCommonFieldBuilder().getBuilder();
        }

        @Override
        public CommonLanguageSettingsOrBuilder getCommonOrBuilder() {
            if (this.commonBuilder_ != null) {
                return this.commonBuilder_.getMessageOrBuilder();
            }
            return this.common_ == null ? CommonLanguageSettings.getDefaultInstance() : this.common_;
        }

        private SingleFieldBuilderV3<CommonLanguageSettings, CommonLanguageSettings.Builder, CommonLanguageSettingsOrBuilder> getCommonFieldBuilder() {
            if (this.commonBuilder_ == null) {
                this.commonBuilder_ = new SingleFieldBuilderV3(this.getCommon(), this.getParentForChildren(), this.isClean());
                this.common_ = null;
            }
            return this.commonBuilder_;
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

