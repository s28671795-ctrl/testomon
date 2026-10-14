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
import com.google.type.QuaternionOrBuilder;
import com.google.type.QuaternionProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Quaternion
extends GeneratedMessageV3
implements QuaternionOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int X_FIELD_NUMBER = 1;
    private double x_ = 0.0;
    public static final int Y_FIELD_NUMBER = 2;
    private double y_ = 0.0;
    public static final int Z_FIELD_NUMBER = 3;
    private double z_ = 0.0;
    public static final int W_FIELD_NUMBER = 4;
    private double w_ = 0.0;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Quaternion DEFAULT_INSTANCE = new Quaternion();
    private static final Parser<Quaternion> PARSER = new AbstractParser<Quaternion>(){

        @Override
        public Quaternion parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Quaternion.newBuilder();
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

    private Quaternion(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Quaternion() {
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Quaternion();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return QuaternionProto.internal_static_google_type_Quaternion_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return QuaternionProto.internal_static_google_type_Quaternion_fieldAccessorTable.ensureFieldAccessorsInitialized(Quaternion.class, Builder.class);
    }

    @Override
    public double getX() {
        return this.x_;
    }

    @Override
    public double getY() {
        return this.y_;
    }

    @Override
    public double getZ() {
        return this.z_;
    }

    @Override
    public double getW() {
        return this.w_;
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
        if (Double.doubleToRawLongBits(this.x_) != 0L) {
            output.writeDouble(1, this.x_);
        }
        if (Double.doubleToRawLongBits(this.y_) != 0L) {
            output.writeDouble(2, this.y_);
        }
        if (Double.doubleToRawLongBits(this.z_) != 0L) {
            output.writeDouble(3, this.z_);
        }
        if (Double.doubleToRawLongBits(this.w_) != 0L) {
            output.writeDouble(4, this.w_);
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
        if (Double.doubleToRawLongBits(this.x_) != 0L) {
            size += CodedOutputStream.computeDoubleSize(1, this.x_);
        }
        if (Double.doubleToRawLongBits(this.y_) != 0L) {
            size += CodedOutputStream.computeDoubleSize(2, this.y_);
        }
        if (Double.doubleToRawLongBits(this.z_) != 0L) {
            size += CodedOutputStream.computeDoubleSize(3, this.z_);
        }
        if (Double.doubleToRawLongBits(this.w_) != 0L) {
            size += CodedOutputStream.computeDoubleSize(4, this.w_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Quaternion)) {
            return super.equals(obj);
        }
        Quaternion other = (Quaternion)obj;
        if (Double.doubleToLongBits(this.getX()) != Double.doubleToLongBits(other.getX())) {
            return false;
        }
        if (Double.doubleToLongBits(this.getY()) != Double.doubleToLongBits(other.getY())) {
            return false;
        }
        if (Double.doubleToLongBits(this.getZ()) != Double.doubleToLongBits(other.getZ())) {
            return false;
        }
        if (Double.doubleToLongBits(this.getW()) != Double.doubleToLongBits(other.getW())) {
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
        hash = 19 * hash + Quaternion.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getX()));
        hash = 37 * hash + 2;
        hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getY()));
        hash = 37 * hash + 3;
        hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getZ()));
        hash = 37 * hash + 4;
        hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getW()));
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Quaternion parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Quaternion parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Quaternion parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Quaternion parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Quaternion parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Quaternion parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Quaternion parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Quaternion parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Quaternion parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Quaternion parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Quaternion parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Quaternion parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Quaternion.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Quaternion prototype) {
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

    public static Quaternion getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Quaternion> parser() {
        return PARSER;
    }

    public Parser<Quaternion> getParserForType() {
        return PARSER;
    }

    @Override
    public Quaternion getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements QuaternionOrBuilder {
        private int bitField0_;
        private double x_;
        private double y_;
        private double z_;
        private double w_;

        public static final Descriptors.Descriptor getDescriptor() {
            return QuaternionProto.internal_static_google_type_Quaternion_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return QuaternionProto.internal_static_google_type_Quaternion_fieldAccessorTable.ensureFieldAccessorsInitialized(Quaternion.class, Builder.class);
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
            this.x_ = 0.0;
            this.y_ = 0.0;
            this.z_ = 0.0;
            this.w_ = 0.0;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return QuaternionProto.internal_static_google_type_Quaternion_descriptor;
        }

        @Override
        public Quaternion getDefaultInstanceForType() {
            return Quaternion.getDefaultInstance();
        }

        @Override
        public Quaternion build() {
            Quaternion result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Quaternion buildPartial() {
            Quaternion result = new Quaternion(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(Quaternion result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.x_ = this.x_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.y_ = this.y_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.z_ = this.z_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.w_ = this.w_;
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
            if (other instanceof Quaternion) {
                return this.mergeFrom((Quaternion)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Quaternion other) {
            if (other == Quaternion.getDefaultInstance()) {
                return this;
            }
            if (other.getX() != 0.0) {
                this.setX(other.getX());
            }
            if (other.getY() != 0.0) {
                this.setY(other.getY());
            }
            if (other.getZ() != 0.0) {
                this.setZ(other.getZ());
            }
            if (other.getW() != 0.0) {
                this.setW(other.getW());
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
                        case 9: {
                            this.x_ = input.readDouble();
                            this.bitField0_ |= 1;
                            continue block12;
                        }
                        case 17: {
                            this.y_ = input.readDouble();
                            this.bitField0_ |= 2;
                            continue block12;
                        }
                        case 25: {
                            this.z_ = input.readDouble();
                            this.bitField0_ |= 4;
                            continue block12;
                        }
                        case 33: {
                            this.w_ = input.readDouble();
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
        public double getX() {
            return this.x_;
        }

        public Builder setX(double value) {
            this.x_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearX() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.x_ = 0.0;
            this.onChanged();
            return this;
        }

        @Override
        public double getY() {
            return this.y_;
        }

        public Builder setY(double value) {
            this.y_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearY() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.y_ = 0.0;
            this.onChanged();
            return this;
        }

        @Override
        public double getZ() {
            return this.z_;
        }

        public Builder setZ(double value) {
            this.z_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearZ() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.z_ = 0.0;
            this.onChanged();
            return this;
        }

        @Override
        public double getW() {
            return this.w_;
        }

        public Builder setW(double value) {
            this.w_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearW() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.w_ = 0.0;
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

