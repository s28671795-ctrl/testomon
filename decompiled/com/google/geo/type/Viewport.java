/*
 * Decompiled with CFR 0.152.
 */
package com.google.geo.type;

import com.google.geo.type.ViewportOrBuilder;
import com.google.geo.type.ViewportProto;
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
import com.google.type.LatLng;
import com.google.type.LatLngOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Viewport
extends GeneratedMessageV3
implements ViewportOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int LOW_FIELD_NUMBER = 1;
    private LatLng low_;
    public static final int HIGH_FIELD_NUMBER = 2;
    private LatLng high_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Viewport DEFAULT_INSTANCE = new Viewport();
    private static final Parser<Viewport> PARSER = new AbstractParser<Viewport>(){

        @Override
        public Viewport parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Viewport.newBuilder();
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

    private Viewport(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Viewport() {
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Viewport();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ViewportProto.internal_static_google_geo_type_Viewport_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ViewportProto.internal_static_google_geo_type_Viewport_fieldAccessorTable.ensureFieldAccessorsInitialized(Viewport.class, Builder.class);
    }

    @Override
    public boolean hasLow() {
        return this.low_ != null;
    }

    @Override
    public LatLng getLow() {
        return this.low_ == null ? LatLng.getDefaultInstance() : this.low_;
    }

    @Override
    public LatLngOrBuilder getLowOrBuilder() {
        return this.low_ == null ? LatLng.getDefaultInstance() : this.low_;
    }

    @Override
    public boolean hasHigh() {
        return this.high_ != null;
    }

    @Override
    public LatLng getHigh() {
        return this.high_ == null ? LatLng.getDefaultInstance() : this.high_;
    }

    @Override
    public LatLngOrBuilder getHighOrBuilder() {
        return this.high_ == null ? LatLng.getDefaultInstance() : this.high_;
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
        if (this.low_ != null) {
            output.writeMessage(1, this.getLow());
        }
        if (this.high_ != null) {
            output.writeMessage(2, this.getHigh());
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
        if (this.low_ != null) {
            size += CodedOutputStream.computeMessageSize(1, this.getLow());
        }
        if (this.high_ != null) {
            size += CodedOutputStream.computeMessageSize(2, this.getHigh());
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Viewport)) {
            return super.equals(obj);
        }
        Viewport other = (Viewport)obj;
        if (this.hasLow() != other.hasLow()) {
            return false;
        }
        if (this.hasLow() && !this.getLow().equals(other.getLow())) {
            return false;
        }
        if (this.hasHigh() != other.hasHigh()) {
            return false;
        }
        if (this.hasHigh() && !this.getHigh().equals(other.getHigh())) {
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
        hash = 19 * hash + Viewport.getDescriptor().hashCode();
        if (this.hasLow()) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getLow().hashCode();
        }
        if (this.hasHigh()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getHigh().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Viewport parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Viewport parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Viewport parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Viewport parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Viewport parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Viewport parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Viewport parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Viewport parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Viewport parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Viewport parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Viewport parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Viewport parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Viewport.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Viewport prototype) {
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

    public static Viewport getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Viewport> parser() {
        return PARSER;
    }

    public Parser<Viewport> getParserForType() {
        return PARSER;
    }

    @Override
    public Viewport getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ViewportOrBuilder {
        private int bitField0_;
        private LatLng low_;
        private SingleFieldBuilderV3<LatLng, LatLng.Builder, LatLngOrBuilder> lowBuilder_;
        private LatLng high_;
        private SingleFieldBuilderV3<LatLng, LatLng.Builder, LatLngOrBuilder> highBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return ViewportProto.internal_static_google_geo_type_Viewport_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ViewportProto.internal_static_google_geo_type_Viewport_fieldAccessorTable.ensureFieldAccessorsInitialized(Viewport.class, Builder.class);
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
            this.low_ = null;
            if (this.lowBuilder_ != null) {
                this.lowBuilder_.dispose();
                this.lowBuilder_ = null;
            }
            this.high_ = null;
            if (this.highBuilder_ != null) {
                this.highBuilder_.dispose();
                this.highBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ViewportProto.internal_static_google_geo_type_Viewport_descriptor;
        }

        @Override
        public Viewport getDefaultInstanceForType() {
            return Viewport.getDefaultInstance();
        }

        @Override
        public Viewport build() {
            Viewport result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Viewport buildPartial() {
            Viewport result = new Viewport(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(Viewport result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.low_ = this.lowBuilder_ == null ? this.low_ : this.lowBuilder_.build();
            }
            if ((from_bitField0_ & 2) != 0) {
                result.high_ = this.highBuilder_ == null ? this.high_ : this.highBuilder_.build();
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
            if (other instanceof Viewport) {
                return this.mergeFrom((Viewport)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Viewport other) {
            if (other == Viewport.getDefaultInstance()) {
                return this;
            }
            if (other.hasLow()) {
                this.mergeLow(other.getLow());
            }
            if (other.hasHigh()) {
                this.mergeHigh(other.getHigh());
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
                            input.readMessage(this.getLowFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            input.readMessage(this.getHighFieldBuilder().getBuilder(), extensionRegistry);
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
        public boolean hasLow() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public LatLng getLow() {
            if (this.lowBuilder_ == null) {
                return this.low_ == null ? LatLng.getDefaultInstance() : this.low_;
            }
            return this.lowBuilder_.getMessage();
        }

        public Builder setLow(LatLng value) {
            if (this.lowBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.low_ = value;
            } else {
                this.lowBuilder_.setMessage(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder setLow(LatLng.Builder builderForValue) {
            if (this.lowBuilder_ == null) {
                this.low_ = builderForValue.build();
            } else {
                this.lowBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder mergeLow(LatLng value) {
            if (this.lowBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0 && this.low_ != null && this.low_ != LatLng.getDefaultInstance()) {
                    this.getLowBuilder().mergeFrom(value);
                } else {
                    this.low_ = value;
                }
            } else {
                this.lowBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearLow() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.low_ = null;
            if (this.lowBuilder_ != null) {
                this.lowBuilder_.dispose();
                this.lowBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public LatLng.Builder getLowBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return this.getLowFieldBuilder().getBuilder();
        }

        @Override
        public LatLngOrBuilder getLowOrBuilder() {
            if (this.lowBuilder_ != null) {
                return this.lowBuilder_.getMessageOrBuilder();
            }
            return this.low_ == null ? LatLng.getDefaultInstance() : this.low_;
        }

        private SingleFieldBuilderV3<LatLng, LatLng.Builder, LatLngOrBuilder> getLowFieldBuilder() {
            if (this.lowBuilder_ == null) {
                this.lowBuilder_ = new SingleFieldBuilderV3(this.getLow(), this.getParentForChildren(), this.isClean());
                this.low_ = null;
            }
            return this.lowBuilder_;
        }

        @Override
        public boolean hasHigh() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override
        public LatLng getHigh() {
            if (this.highBuilder_ == null) {
                return this.high_ == null ? LatLng.getDefaultInstance() : this.high_;
            }
            return this.highBuilder_.getMessage();
        }

        public Builder setHigh(LatLng value) {
            if (this.highBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.high_ = value;
            } else {
                this.highBuilder_.setMessage(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder setHigh(LatLng.Builder builderForValue) {
            if (this.highBuilder_ == null) {
                this.high_ = builderForValue.build();
            } else {
                this.highBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder mergeHigh(LatLng value) {
            if (this.highBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0 && this.high_ != null && this.high_ != LatLng.getDefaultInstance()) {
                    this.getHighBuilder().mergeFrom(value);
                } else {
                    this.high_ = value;
                }
            } else {
                this.highBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearHigh() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.high_ = null;
            if (this.highBuilder_ != null) {
                this.highBuilder_.dispose();
                this.highBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public LatLng.Builder getHighBuilder() {
            this.bitField0_ |= 2;
            this.onChanged();
            return this.getHighFieldBuilder().getBuilder();
        }

        @Override
        public LatLngOrBuilder getHighOrBuilder() {
            if (this.highBuilder_ != null) {
                return this.highBuilder_.getMessageOrBuilder();
            }
            return this.high_ == null ? LatLng.getDefaultInstance() : this.high_;
        }

        private SingleFieldBuilderV3<LatLng, LatLng.Builder, LatLngOrBuilder> getHighFieldBuilder() {
            if (this.highBuilder_ == null) {
                this.highBuilder_ = new SingleFieldBuilderV3(this.getHigh(), this.getParentForChildren(), this.isClean());
                this.high_ = null;
            }
            return this.highBuilder_;
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

