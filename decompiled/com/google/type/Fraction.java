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
import com.google.type.FractionOrBuilder;
import com.google.type.FractionProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Fraction
extends GeneratedMessageV3
implements FractionOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int NUMERATOR_FIELD_NUMBER = 1;
    private long numerator_ = 0L;
    public static final int DENOMINATOR_FIELD_NUMBER = 2;
    private long denominator_ = 0L;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Fraction DEFAULT_INSTANCE = new Fraction();
    private static final Parser<Fraction> PARSER = new AbstractParser<Fraction>(){

        @Override
        public Fraction parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Fraction.newBuilder();
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

    private Fraction(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Fraction() {
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Fraction();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return FractionProto.internal_static_google_type_Fraction_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return FractionProto.internal_static_google_type_Fraction_fieldAccessorTable.ensureFieldAccessorsInitialized(Fraction.class, Builder.class);
    }

    @Override
    public long getNumerator() {
        return this.numerator_;
    }

    @Override
    public long getDenominator() {
        return this.denominator_;
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
        if (this.numerator_ != 0L) {
            output.writeInt64(1, this.numerator_);
        }
        if (this.denominator_ != 0L) {
            output.writeInt64(2, this.denominator_);
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
        if (this.numerator_ != 0L) {
            size += CodedOutputStream.computeInt64Size(1, this.numerator_);
        }
        if (this.denominator_ != 0L) {
            size += CodedOutputStream.computeInt64Size(2, this.denominator_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Fraction)) {
            return super.equals(obj);
        }
        Fraction other = (Fraction)obj;
        if (this.getNumerator() != other.getNumerator()) {
            return false;
        }
        if (this.getDenominator() != other.getDenominator()) {
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
        hash = 19 * hash + Fraction.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + Internal.hashLong(this.getNumerator());
        hash = 37 * hash + 2;
        hash = 53 * hash + Internal.hashLong(this.getDenominator());
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Fraction parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Fraction parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Fraction parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Fraction parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Fraction parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Fraction parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Fraction parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Fraction parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Fraction parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Fraction parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Fraction parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Fraction parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Fraction.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Fraction prototype) {
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

    public static Fraction getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Fraction> parser() {
        return PARSER;
    }

    public Parser<Fraction> getParserForType() {
        return PARSER;
    }

    @Override
    public Fraction getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements FractionOrBuilder {
        private int bitField0_;
        private long numerator_;
        private long denominator_;

        public static final Descriptors.Descriptor getDescriptor() {
            return FractionProto.internal_static_google_type_Fraction_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return FractionProto.internal_static_google_type_Fraction_fieldAccessorTable.ensureFieldAccessorsInitialized(Fraction.class, Builder.class);
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
            this.numerator_ = 0L;
            this.denominator_ = 0L;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return FractionProto.internal_static_google_type_Fraction_descriptor;
        }

        @Override
        public Fraction getDefaultInstanceForType() {
            return Fraction.getDefaultInstance();
        }

        @Override
        public Fraction build() {
            Fraction result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Fraction buildPartial() {
            Fraction result = new Fraction(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(Fraction result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.numerator_ = this.numerator_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.denominator_ = this.denominator_;
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
            if (other instanceof Fraction) {
                return this.mergeFrom((Fraction)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Fraction other) {
            if (other == Fraction.getDefaultInstance()) {
                return this;
            }
            if (other.getNumerator() != 0L) {
                this.setNumerator(other.getNumerator());
            }
            if (other.getDenominator() != 0L) {
                this.setDenominator(other.getDenominator());
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
                        case 8: {
                            this.numerator_ = input.readInt64();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 16: {
                            this.denominator_ = input.readInt64();
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
        public long getNumerator() {
            return this.numerator_;
        }

        public Builder setNumerator(long value) {
            this.numerator_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearNumerator() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.numerator_ = 0L;
            this.onChanged();
            return this;
        }

        @Override
        public long getDenominator() {
            return this.denominator_;
        }

        public Builder setDenominator(long value) {
            this.denominator_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearDenominator() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.denominator_ = 0L;
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

