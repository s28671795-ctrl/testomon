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
import com.google.protobuf.FloatValue;
import com.google.protobuf.FloatValueOrBuilder;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.type.ColorOrBuilder;
import com.google.type.ColorProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Color
extends GeneratedMessageV3
implements ColorOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int RED_FIELD_NUMBER = 1;
    private float red_ = 0.0f;
    public static final int GREEN_FIELD_NUMBER = 2;
    private float green_ = 0.0f;
    public static final int BLUE_FIELD_NUMBER = 3;
    private float blue_ = 0.0f;
    public static final int ALPHA_FIELD_NUMBER = 4;
    private FloatValue alpha_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Color DEFAULT_INSTANCE = new Color();
    private static final Parser<Color> PARSER = new AbstractParser<Color>(){

        @Override
        public Color parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Color.newBuilder();
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

    private Color(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Color() {
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Color();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ColorProto.internal_static_google_type_Color_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ColorProto.internal_static_google_type_Color_fieldAccessorTable.ensureFieldAccessorsInitialized(Color.class, Builder.class);
    }

    @Override
    public float getRed() {
        return this.red_;
    }

    @Override
    public float getGreen() {
        return this.green_;
    }

    @Override
    public float getBlue() {
        return this.blue_;
    }

    @Override
    public boolean hasAlpha() {
        return this.alpha_ != null;
    }

    @Override
    public FloatValue getAlpha() {
        return this.alpha_ == null ? FloatValue.getDefaultInstance() : this.alpha_;
    }

    @Override
    public FloatValueOrBuilder getAlphaOrBuilder() {
        return this.alpha_ == null ? FloatValue.getDefaultInstance() : this.alpha_;
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
        if (Float.floatToRawIntBits(this.red_) != 0) {
            output.writeFloat(1, this.red_);
        }
        if (Float.floatToRawIntBits(this.green_) != 0) {
            output.writeFloat(2, this.green_);
        }
        if (Float.floatToRawIntBits(this.blue_) != 0) {
            output.writeFloat(3, this.blue_);
        }
        if (this.alpha_ != null) {
            output.writeMessage(4, this.getAlpha());
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
        if (Float.floatToRawIntBits(this.red_) != 0) {
            size += CodedOutputStream.computeFloatSize(1, this.red_);
        }
        if (Float.floatToRawIntBits(this.green_) != 0) {
            size += CodedOutputStream.computeFloatSize(2, this.green_);
        }
        if (Float.floatToRawIntBits(this.blue_) != 0) {
            size += CodedOutputStream.computeFloatSize(3, this.blue_);
        }
        if (this.alpha_ != null) {
            size += CodedOutputStream.computeMessageSize(4, this.getAlpha());
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Color)) {
            return super.equals(obj);
        }
        Color other = (Color)obj;
        if (Float.floatToIntBits(this.getRed()) != Float.floatToIntBits(other.getRed())) {
            return false;
        }
        if (Float.floatToIntBits(this.getGreen()) != Float.floatToIntBits(other.getGreen())) {
            return false;
        }
        if (Float.floatToIntBits(this.getBlue()) != Float.floatToIntBits(other.getBlue())) {
            return false;
        }
        if (this.hasAlpha() != other.hasAlpha()) {
            return false;
        }
        if (this.hasAlpha() && !this.getAlpha().equals(other.getAlpha())) {
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
        hash = 19 * hash + Color.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + Float.floatToIntBits(this.getRed());
        hash = 37 * hash + 2;
        hash = 53 * hash + Float.floatToIntBits(this.getGreen());
        hash = 37 * hash + 3;
        hash = 53 * hash + Float.floatToIntBits(this.getBlue());
        if (this.hasAlpha()) {
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getAlpha().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Color parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Color parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Color parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Color parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Color parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Color parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Color parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Color parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Color parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Color parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Color parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Color parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Color.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Color prototype) {
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

    public static Color getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Color> parser() {
        return PARSER;
    }

    public Parser<Color> getParserForType() {
        return PARSER;
    }

    @Override
    public Color getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ColorOrBuilder {
        private int bitField0_;
        private float red_;
        private float green_;
        private float blue_;
        private FloatValue alpha_;
        private SingleFieldBuilderV3<FloatValue, FloatValue.Builder, FloatValueOrBuilder> alphaBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return ColorProto.internal_static_google_type_Color_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ColorProto.internal_static_google_type_Color_fieldAccessorTable.ensureFieldAccessorsInitialized(Color.class, Builder.class);
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
            this.red_ = 0.0f;
            this.green_ = 0.0f;
            this.blue_ = 0.0f;
            this.alpha_ = null;
            if (this.alphaBuilder_ != null) {
                this.alphaBuilder_.dispose();
                this.alphaBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ColorProto.internal_static_google_type_Color_descriptor;
        }

        @Override
        public Color getDefaultInstanceForType() {
            return Color.getDefaultInstance();
        }

        @Override
        public Color build() {
            Color result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Color buildPartial() {
            Color result = new Color(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(Color result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.red_ = this.red_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.green_ = this.green_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.blue_ = this.blue_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.alpha_ = this.alphaBuilder_ == null ? this.alpha_ : this.alphaBuilder_.build();
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
            if (other instanceof Color) {
                return this.mergeFrom((Color)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Color other) {
            if (other == Color.getDefaultInstance()) {
                return this;
            }
            if (other.getRed() != 0.0f) {
                this.setRed(other.getRed());
            }
            if (other.getGreen() != 0.0f) {
                this.setGreen(other.getGreen());
            }
            if (other.getBlue() != 0.0f) {
                this.setBlue(other.getBlue());
            }
            if (other.hasAlpha()) {
                this.mergeAlpha(other.getAlpha());
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
                        case 13: {
                            this.red_ = input.readFloat();
                            this.bitField0_ |= 1;
                            continue block12;
                        }
                        case 21: {
                            this.green_ = input.readFloat();
                            this.bitField0_ |= 2;
                            continue block12;
                        }
                        case 29: {
                            this.blue_ = input.readFloat();
                            this.bitField0_ |= 4;
                            continue block12;
                        }
                        case 34: {
                            input.readMessage(this.getAlphaFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 8;
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
        public float getRed() {
            return this.red_;
        }

        public Builder setRed(float value) {
            this.red_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearRed() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.red_ = 0.0f;
            this.onChanged();
            return this;
        }

        @Override
        public float getGreen() {
            return this.green_;
        }

        public Builder setGreen(float value) {
            this.green_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearGreen() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.green_ = 0.0f;
            this.onChanged();
            return this;
        }

        @Override
        public float getBlue() {
            return this.blue_;
        }

        public Builder setBlue(float value) {
            this.blue_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearBlue() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.blue_ = 0.0f;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasAlpha() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override
        public FloatValue getAlpha() {
            if (this.alphaBuilder_ == null) {
                return this.alpha_ == null ? FloatValue.getDefaultInstance() : this.alpha_;
            }
            return this.alphaBuilder_.getMessage();
        }

        public Builder setAlpha(FloatValue value) {
            if (this.alphaBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.alpha_ = value;
            } else {
                this.alphaBuilder_.setMessage(value);
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder setAlpha(FloatValue.Builder builderForValue) {
            if (this.alphaBuilder_ == null) {
                this.alpha_ = builderForValue.build();
            } else {
                this.alphaBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder mergeAlpha(FloatValue value) {
            if (this.alphaBuilder_ == null) {
                if ((this.bitField0_ & 8) != 0 && this.alpha_ != null && this.alpha_ != FloatValue.getDefaultInstance()) {
                    this.getAlphaBuilder().mergeFrom(value);
                } else {
                    this.alpha_ = value;
                }
            } else {
                this.alphaBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearAlpha() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.alpha_ = null;
            if (this.alphaBuilder_ != null) {
                this.alphaBuilder_.dispose();
                this.alphaBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public FloatValue.Builder getAlphaBuilder() {
            this.bitField0_ |= 8;
            this.onChanged();
            return this.getAlphaFieldBuilder().getBuilder();
        }

        @Override
        public FloatValueOrBuilder getAlphaOrBuilder() {
            if (this.alphaBuilder_ != null) {
                return this.alphaBuilder_.getMessageOrBuilder();
            }
            return this.alpha_ == null ? FloatValue.getDefaultInstance() : this.alpha_;
        }

        private SingleFieldBuilderV3<FloatValue, FloatValue.Builder, FloatValueOrBuilder> getAlphaFieldBuilder() {
            if (this.alphaBuilder_ == null) {
                this.alphaBuilder_ = new SingleFieldBuilderV3(this.getAlpha(), this.getParentForChildren(), this.isClean());
                this.alpha_ = null;
            }
            return this.alphaBuilder_;
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

