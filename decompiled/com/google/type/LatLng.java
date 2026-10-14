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
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.type.LatLngOrBuilder;
import com.google.type.LatLngProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class LatLng
extends GeneratedMessageV3
implements LatLngOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int LATITUDE_FIELD_NUMBER = 1;
    private double latitude_ = 0.0;
    public static final int LONGITUDE_FIELD_NUMBER = 2;
    private double longitude_ = 0.0;
    private byte memoizedIsInitialized = (byte)-1;
    private static final LatLng DEFAULT_INSTANCE = new LatLng();
    private static final Parser<LatLng> PARSER = new AbstractParser<LatLng>(){

        @Override
        public LatLng parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = LatLng.newBuilder();
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

    private LatLng(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private LatLng() {
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new LatLng();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return LatLngProto.internal_static_google_type_LatLng_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return LatLngProto.internal_static_google_type_LatLng_fieldAccessorTable.ensureFieldAccessorsInitialized(LatLng.class, Builder.class);
    }

    @Override
    public double getLatitude() {
        return this.latitude_;
    }

    @Override
    public double getLongitude() {
        return this.longitude_;
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
        if (Double.doubleToRawLongBits(this.latitude_) != 0L) {
            output.writeDouble(1, this.latitude_);
        }
        if (Double.doubleToRawLongBits(this.longitude_) != 0L) {
            output.writeDouble(2, this.longitude_);
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
        if (Double.doubleToRawLongBits(this.latitude_) != 0L) {
            size += CodedOutputStream.computeDoubleSize(1, this.latitude_);
        }
        if (Double.doubleToRawLongBits(this.longitude_) != 0L) {
            size += CodedOutputStream.computeDoubleSize(2, this.longitude_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LatLng)) {
            return super.equals(obj);
        }
        LatLng other = (LatLng)obj;
        if (Double.doubleToLongBits(this.getLatitude()) != Double.doubleToLongBits(other.getLatitude())) {
            return false;
        }
        if (Double.doubleToLongBits(this.getLongitude()) != Double.doubleToLongBits(other.getLongitude())) {
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
        hash = 19 * hash + LatLng.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getLatitude()));
        hash = 37 * hash + 2;
        hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getLongitude()));
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static LatLng parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LatLng parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LatLng parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LatLng parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LatLng parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LatLng parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LatLng parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static LatLng parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static LatLng parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static LatLng parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static LatLng parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static LatLng parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return LatLng.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(LatLng prototype) {
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

    public static LatLng getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<LatLng> parser() {
        return PARSER;
    }

    public Parser<LatLng> getParserForType() {
        return PARSER;
    }

    @Override
    public LatLng getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements LatLngOrBuilder {
        private int bitField0_;
        private double latitude_;
        private double longitude_;

        public static final Descriptors.Descriptor getDescriptor() {
            return LatLngProto.internal_static_google_type_LatLng_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return LatLngProto.internal_static_google_type_LatLng_fieldAccessorTable.ensureFieldAccessorsInitialized(LatLng.class, Builder.class);
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
            this.latitude_ = 0.0;
            this.longitude_ = 0.0;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return LatLngProto.internal_static_google_type_LatLng_descriptor;
        }

        @Override
        public LatLng getDefaultInstanceForType() {
            return LatLng.getDefaultInstance();
        }

        @Override
        public LatLng build() {
            LatLng result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public LatLng buildPartial() {
            LatLng result = new LatLng(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(LatLng result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.latitude_ = this.latitude_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.longitude_ = this.longitude_;
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
            if (other instanceof LatLng) {
                return this.mergeFrom((LatLng)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(LatLng other) {
            if (other == LatLng.getDefaultInstance()) {
                return this;
            }
            if (other.getLatitude() != 0.0) {
                this.setLatitude(other.getLatitude());
            }
            if (other.getLongitude() != 0.0) {
                this.setLongitude(other.getLongitude());
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
                        case 9: {
                            this.latitude_ = input.readDouble();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 17: {
                            this.longitude_ = input.readDouble();
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
        public double getLatitude() {
            return this.latitude_;
        }

        public Builder setLatitude(double value) {
            this.latitude_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearLatitude() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.latitude_ = 0.0;
            this.onChanged();
            return this;
        }

        @Override
        public double getLongitude() {
            return this.longitude_;
        }

        public Builder setLongitude(double value) {
            this.longitude_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearLongitude() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.longitude_ = 0.0;
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

