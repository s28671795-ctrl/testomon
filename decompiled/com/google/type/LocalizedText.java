/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

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
import com.google.type.LocalizedTextOrBuilder;
import com.google.type.LocalizedTextProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class LocalizedText
extends GeneratedMessageV3
implements LocalizedTextOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int TEXT_FIELD_NUMBER = 1;
    private volatile Object text_ = "";
    public static final int LANGUAGE_CODE_FIELD_NUMBER = 2;
    private volatile Object languageCode_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final LocalizedText DEFAULT_INSTANCE = new LocalizedText();
    private static final Parser<LocalizedText> PARSER = new AbstractParser<LocalizedText>(){

        @Override
        public LocalizedText parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = LocalizedText.newBuilder();
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

    private LocalizedText(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private LocalizedText() {
        this.text_ = "";
        this.languageCode_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new LocalizedText();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return LocalizedTextProto.internal_static_google_type_LocalizedText_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return LocalizedTextProto.internal_static_google_type_LocalizedText_fieldAccessorTable.ensureFieldAccessorsInitialized(LocalizedText.class, Builder.class);
    }

    @Override
    public String getText() {
        Object ref = this.text_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.text_ = s2;
        return s2;
    }

    @Override
    public ByteString getTextBytes() {
        Object ref = this.text_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.text_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getLanguageCode() {
        Object ref = this.languageCode_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.languageCode_ = s2;
        return s2;
    }

    @Override
    public ByteString getLanguageCodeBytes() {
        Object ref = this.languageCode_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.languageCode_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.text_)) {
            GeneratedMessageV3.writeString(output, 1, this.text_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.languageCode_)) {
            GeneratedMessageV3.writeString(output, 2, this.languageCode_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.text_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.text_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.languageCode_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.languageCode_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LocalizedText)) {
            return super.equals(obj);
        }
        LocalizedText other = (LocalizedText)obj;
        if (!this.getText().equals(other.getText())) {
            return false;
        }
        if (!this.getLanguageCode().equals(other.getLanguageCode())) {
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
        hash = 19 * hash + LocalizedText.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getText().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getLanguageCode().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static LocalizedText parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LocalizedText parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LocalizedText parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LocalizedText parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LocalizedText parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LocalizedText parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LocalizedText parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static LocalizedText parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static LocalizedText parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static LocalizedText parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static LocalizedText parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static LocalizedText parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return LocalizedText.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(LocalizedText prototype) {
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

    public static LocalizedText getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<LocalizedText> parser() {
        return PARSER;
    }

    public Parser<LocalizedText> getParserForType() {
        return PARSER;
    }

    @Override
    public LocalizedText getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements LocalizedTextOrBuilder {
        private int bitField0_;
        private Object text_ = "";
        private Object languageCode_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return LocalizedTextProto.internal_static_google_type_LocalizedText_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return LocalizedTextProto.internal_static_google_type_LocalizedText_fieldAccessorTable.ensureFieldAccessorsInitialized(LocalizedText.class, Builder.class);
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
            this.text_ = "";
            this.languageCode_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return LocalizedTextProto.internal_static_google_type_LocalizedText_descriptor;
        }

        @Override
        public LocalizedText getDefaultInstanceForType() {
            return LocalizedText.getDefaultInstance();
        }

        @Override
        public LocalizedText build() {
            LocalizedText result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public LocalizedText buildPartial() {
            LocalizedText result = new LocalizedText(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(LocalizedText result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.text_ = this.text_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.languageCode_ = this.languageCode_;
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
            if (other instanceof LocalizedText) {
                return this.mergeFrom((LocalizedText)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(LocalizedText other) {
            if (other == LocalizedText.getDefaultInstance()) {
                return this;
            }
            if (!other.getText().isEmpty()) {
                this.text_ = other.text_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getLanguageCode().isEmpty()) {
                this.languageCode_ = other.languageCode_;
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
                            this.text_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            this.languageCode_ = input.readStringRequireUtf8();
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
        public String getText() {
            Object ref = this.text_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.text_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getTextBytes() {
            Object ref = this.text_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.text_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setText(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.text_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearText() {
            this.text_ = LocalizedText.getDefaultInstance().getText();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setTextBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            LocalizedText.checkByteStringIsUtf8(value);
            this.text_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getLanguageCode() {
            Object ref = this.languageCode_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.languageCode_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getLanguageCodeBytes() {
            Object ref = this.languageCode_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.languageCode_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setLanguageCode(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.languageCode_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearLanguageCode() {
            this.languageCode_ = LocalizedText.getDefaultInstance().getLanguageCode();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setLanguageCodeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            LocalizedText.checkByteStringIsUtf8(value);
            this.languageCode_ = value;
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

