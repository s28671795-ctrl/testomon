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
import com.google.type.DateOrBuilder;
import com.google.type.DateProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Date
extends GeneratedMessageV3
implements DateOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int YEAR_FIELD_NUMBER = 1;
    private int year_ = 0;
    public static final int MONTH_FIELD_NUMBER = 2;
    private int month_ = 0;
    public static final int DAY_FIELD_NUMBER = 3;
    private int day_ = 0;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Date DEFAULT_INSTANCE = new Date();
    private static final Parser<Date> PARSER = new AbstractParser<Date>(){

        @Override
        public Date parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Date.newBuilder();
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

    private Date(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Date() {
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Date();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return DateProto.internal_static_google_type_Date_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return DateProto.internal_static_google_type_Date_fieldAccessorTable.ensureFieldAccessorsInitialized(Date.class, Builder.class);
    }

    @Override
    public int getYear() {
        return this.year_;
    }

    @Override
    public int getMonth() {
        return this.month_;
    }

    @Override
    public int getDay() {
        return this.day_;
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
        if (this.year_ != 0) {
            output.writeInt32(1, this.year_);
        }
        if (this.month_ != 0) {
            output.writeInt32(2, this.month_);
        }
        if (this.day_ != 0) {
            output.writeInt32(3, this.day_);
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
        if (this.year_ != 0) {
            size += CodedOutputStream.computeInt32Size(1, this.year_);
        }
        if (this.month_ != 0) {
            size += CodedOutputStream.computeInt32Size(2, this.month_);
        }
        if (this.day_ != 0) {
            size += CodedOutputStream.computeInt32Size(3, this.day_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Date)) {
            return super.equals(obj);
        }
        Date other = (Date)obj;
        if (this.getYear() != other.getYear()) {
            return false;
        }
        if (this.getMonth() != other.getMonth()) {
            return false;
        }
        if (this.getDay() != other.getDay()) {
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
        hash = 19 * hash + Date.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getYear();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getMonth();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getDay();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Date parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Date parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Date parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Date parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Date parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Date parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Date parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Date parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Date parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Date parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Date parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Date parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Date.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Date prototype) {
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

    public static Date getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Date> parser() {
        return PARSER;
    }

    public Parser<Date> getParserForType() {
        return PARSER;
    }

    @Override
    public Date getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements DateOrBuilder {
        private int bitField0_;
        private int year_;
        private int month_;
        private int day_;

        public static final Descriptors.Descriptor getDescriptor() {
            return DateProto.internal_static_google_type_Date_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return DateProto.internal_static_google_type_Date_fieldAccessorTable.ensureFieldAccessorsInitialized(Date.class, Builder.class);
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
            this.year_ = 0;
            this.month_ = 0;
            this.day_ = 0;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return DateProto.internal_static_google_type_Date_descriptor;
        }

        @Override
        public Date getDefaultInstanceForType() {
            return Date.getDefaultInstance();
        }

        @Override
        public Date build() {
            Date result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Date buildPartial() {
            Date result = new Date(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(Date result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.year_ = this.year_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.month_ = this.month_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.day_ = this.day_;
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
            if (other instanceof Date) {
                return this.mergeFrom((Date)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Date other) {
            if (other == Date.getDefaultInstance()) {
                return this;
            }
            if (other.getYear() != 0) {
                this.setYear(other.getYear());
            }
            if (other.getMonth() != 0) {
                this.setMonth(other.getMonth());
            }
            if (other.getDay() != 0) {
                this.setDay(other.getDay());
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
                        case 8: {
                            this.year_ = input.readInt32();
                            this.bitField0_ |= 1;
                            continue block11;
                        }
                        case 16: {
                            this.month_ = input.readInt32();
                            this.bitField0_ |= 2;
                            continue block11;
                        }
                        case 24: {
                            this.day_ = input.readInt32();
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
        public int getYear() {
            return this.year_;
        }

        public Builder setYear(int value) {
            this.year_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearYear() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.year_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public int getMonth() {
            return this.month_;
        }

        public Builder setMonth(int value) {
            this.month_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearMonth() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.month_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public int getDay() {
            return this.day_;
        }

        public Builder setDay(int value) {
            this.day_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearDay() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.day_ = 0;
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

