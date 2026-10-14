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
import com.google.type.TimeOfDayOrBuilder;
import com.google.type.TimeOfDayProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class TimeOfDay
extends GeneratedMessageV3
implements TimeOfDayOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int HOURS_FIELD_NUMBER = 1;
    private int hours_ = 0;
    public static final int MINUTES_FIELD_NUMBER = 2;
    private int minutes_ = 0;
    public static final int SECONDS_FIELD_NUMBER = 3;
    private int seconds_ = 0;
    public static final int NANOS_FIELD_NUMBER = 4;
    private int nanos_ = 0;
    private byte memoizedIsInitialized = (byte)-1;
    private static final TimeOfDay DEFAULT_INSTANCE = new TimeOfDay();
    private static final Parser<TimeOfDay> PARSER = new AbstractParser<TimeOfDay>(){

        @Override
        public TimeOfDay parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = TimeOfDay.newBuilder();
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

    private TimeOfDay(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private TimeOfDay() {
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new TimeOfDay();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return TimeOfDayProto.internal_static_google_type_TimeOfDay_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return TimeOfDayProto.internal_static_google_type_TimeOfDay_fieldAccessorTable.ensureFieldAccessorsInitialized(TimeOfDay.class, Builder.class);
    }

    @Override
    public int getHours() {
        return this.hours_;
    }

    @Override
    public int getMinutes() {
        return this.minutes_;
    }

    @Override
    public int getSeconds() {
        return this.seconds_;
    }

    @Override
    public int getNanos() {
        return this.nanos_;
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
        if (this.hours_ != 0) {
            output.writeInt32(1, this.hours_);
        }
        if (this.minutes_ != 0) {
            output.writeInt32(2, this.minutes_);
        }
        if (this.seconds_ != 0) {
            output.writeInt32(3, this.seconds_);
        }
        if (this.nanos_ != 0) {
            output.writeInt32(4, this.nanos_);
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
        if (this.hours_ != 0) {
            size += CodedOutputStream.computeInt32Size(1, this.hours_);
        }
        if (this.minutes_ != 0) {
            size += CodedOutputStream.computeInt32Size(2, this.minutes_);
        }
        if (this.seconds_ != 0) {
            size += CodedOutputStream.computeInt32Size(3, this.seconds_);
        }
        if (this.nanos_ != 0) {
            size += CodedOutputStream.computeInt32Size(4, this.nanos_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TimeOfDay)) {
            return super.equals(obj);
        }
        TimeOfDay other = (TimeOfDay)obj;
        if (this.getHours() != other.getHours()) {
            return false;
        }
        if (this.getMinutes() != other.getMinutes()) {
            return false;
        }
        if (this.getSeconds() != other.getSeconds()) {
            return false;
        }
        if (this.getNanos() != other.getNanos()) {
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
        hash = 19 * hash + TimeOfDay.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getHours();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getMinutes();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getSeconds();
        hash = 37 * hash + 4;
        hash = 53 * hash + this.getNanos();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static TimeOfDay parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static TimeOfDay parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static TimeOfDay parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static TimeOfDay parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static TimeOfDay parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static TimeOfDay parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static TimeOfDay parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static TimeOfDay parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static TimeOfDay parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static TimeOfDay parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static TimeOfDay parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static TimeOfDay parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return TimeOfDay.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(TimeOfDay prototype) {
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

    public static TimeOfDay getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<TimeOfDay> parser() {
        return PARSER;
    }

    public Parser<TimeOfDay> getParserForType() {
        return PARSER;
    }

    @Override
    public TimeOfDay getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements TimeOfDayOrBuilder {
        private int bitField0_;
        private int hours_;
        private int minutes_;
        private int seconds_;
        private int nanos_;

        public static final Descriptors.Descriptor getDescriptor() {
            return TimeOfDayProto.internal_static_google_type_TimeOfDay_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return TimeOfDayProto.internal_static_google_type_TimeOfDay_fieldAccessorTable.ensureFieldAccessorsInitialized(TimeOfDay.class, Builder.class);
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
            this.hours_ = 0;
            this.minutes_ = 0;
            this.seconds_ = 0;
            this.nanos_ = 0;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return TimeOfDayProto.internal_static_google_type_TimeOfDay_descriptor;
        }

        @Override
        public TimeOfDay getDefaultInstanceForType() {
            return TimeOfDay.getDefaultInstance();
        }

        @Override
        public TimeOfDay build() {
            TimeOfDay result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public TimeOfDay buildPartial() {
            TimeOfDay result = new TimeOfDay(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(TimeOfDay result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.hours_ = this.hours_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.minutes_ = this.minutes_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.seconds_ = this.seconds_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.nanos_ = this.nanos_;
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
            if (other instanceof TimeOfDay) {
                return this.mergeFrom((TimeOfDay)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(TimeOfDay other) {
            if (other == TimeOfDay.getDefaultInstance()) {
                return this;
            }
            if (other.getHours() != 0) {
                this.setHours(other.getHours());
            }
            if (other.getMinutes() != 0) {
                this.setMinutes(other.getMinutes());
            }
            if (other.getSeconds() != 0) {
                this.setSeconds(other.getSeconds());
            }
            if (other.getNanos() != 0) {
                this.setNanos(other.getNanos());
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
                        case 8: {
                            this.hours_ = input.readInt32();
                            this.bitField0_ |= 1;
                            continue block12;
                        }
                        case 16: {
                            this.minutes_ = input.readInt32();
                            this.bitField0_ |= 2;
                            continue block12;
                        }
                        case 24: {
                            this.seconds_ = input.readInt32();
                            this.bitField0_ |= 4;
                            continue block12;
                        }
                        case 32: {
                            this.nanos_ = input.readInt32();
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
        public int getHours() {
            return this.hours_;
        }

        public Builder setHours(int value) {
            this.hours_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearHours() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.hours_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public int getMinutes() {
            return this.minutes_;
        }

        public Builder setMinutes(int value) {
            this.minutes_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearMinutes() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.minutes_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public int getSeconds() {
            return this.seconds_;
        }

        public Builder setSeconds(int value) {
            this.seconds_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearSeconds() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.seconds_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public int getNanos() {
            return this.nanos_;
        }

        public Builder setNanos(int value) {
            this.nanos_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearNanos() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.nanos_ = 0;
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

