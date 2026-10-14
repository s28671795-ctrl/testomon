/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Duration;
import com.google.protobuf.DurationOrBuilder;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.type.DateTimeOrBuilder;
import com.google.type.DateTimeProto;
import com.google.type.TimeZone;
import com.google.type.TimeZoneOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class DateTime
extends GeneratedMessageV3
implements DateTimeOrBuilder {
    private static final long serialVersionUID = 0L;
    private int timeOffsetCase_ = 0;
    private Object timeOffset_;
    public static final int YEAR_FIELD_NUMBER = 1;
    private int year_ = 0;
    public static final int MONTH_FIELD_NUMBER = 2;
    private int month_ = 0;
    public static final int DAY_FIELD_NUMBER = 3;
    private int day_ = 0;
    public static final int HOURS_FIELD_NUMBER = 4;
    private int hours_ = 0;
    public static final int MINUTES_FIELD_NUMBER = 5;
    private int minutes_ = 0;
    public static final int SECONDS_FIELD_NUMBER = 6;
    private int seconds_ = 0;
    public static final int NANOS_FIELD_NUMBER = 7;
    private int nanos_ = 0;
    public static final int UTC_OFFSET_FIELD_NUMBER = 8;
    public static final int TIME_ZONE_FIELD_NUMBER = 9;
    private byte memoizedIsInitialized = (byte)-1;
    private static final DateTime DEFAULT_INSTANCE = new DateTime();
    private static final Parser<DateTime> PARSER = new AbstractParser<DateTime>(){

        @Override
        public DateTime parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = DateTime.newBuilder();
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

    private DateTime(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private DateTime() {
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new DateTime();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return DateTimeProto.internal_static_google_type_DateTime_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return DateTimeProto.internal_static_google_type_DateTime_fieldAccessorTable.ensureFieldAccessorsInitialized(DateTime.class, Builder.class);
    }

    @Override
    public TimeOffsetCase getTimeOffsetCase() {
        return TimeOffsetCase.forNumber(this.timeOffsetCase_);
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
    public boolean hasUtcOffset() {
        return this.timeOffsetCase_ == 8;
    }

    @Override
    public Duration getUtcOffset() {
        if (this.timeOffsetCase_ == 8) {
            return (Duration)this.timeOffset_;
        }
        return Duration.getDefaultInstance();
    }

    @Override
    public DurationOrBuilder getUtcOffsetOrBuilder() {
        if (this.timeOffsetCase_ == 8) {
            return (Duration)this.timeOffset_;
        }
        return Duration.getDefaultInstance();
    }

    @Override
    public boolean hasTimeZone() {
        return this.timeOffsetCase_ == 9;
    }

    @Override
    public TimeZone getTimeZone() {
        if (this.timeOffsetCase_ == 9) {
            return (TimeZone)this.timeOffset_;
        }
        return TimeZone.getDefaultInstance();
    }

    @Override
    public TimeZoneOrBuilder getTimeZoneOrBuilder() {
        if (this.timeOffsetCase_ == 9) {
            return (TimeZone)this.timeOffset_;
        }
        return TimeZone.getDefaultInstance();
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
        if (this.hours_ != 0) {
            output.writeInt32(4, this.hours_);
        }
        if (this.minutes_ != 0) {
            output.writeInt32(5, this.minutes_);
        }
        if (this.seconds_ != 0) {
            output.writeInt32(6, this.seconds_);
        }
        if (this.nanos_ != 0) {
            output.writeInt32(7, this.nanos_);
        }
        if (this.timeOffsetCase_ == 8) {
            output.writeMessage(8, (Duration)this.timeOffset_);
        }
        if (this.timeOffsetCase_ == 9) {
            output.writeMessage(9, (TimeZone)this.timeOffset_);
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
        if (this.hours_ != 0) {
            size += CodedOutputStream.computeInt32Size(4, this.hours_);
        }
        if (this.minutes_ != 0) {
            size += CodedOutputStream.computeInt32Size(5, this.minutes_);
        }
        if (this.seconds_ != 0) {
            size += CodedOutputStream.computeInt32Size(6, this.seconds_);
        }
        if (this.nanos_ != 0) {
            size += CodedOutputStream.computeInt32Size(7, this.nanos_);
        }
        if (this.timeOffsetCase_ == 8) {
            size += CodedOutputStream.computeMessageSize(8, (Duration)this.timeOffset_);
        }
        if (this.timeOffsetCase_ == 9) {
            size += CodedOutputStream.computeMessageSize(9, (TimeZone)this.timeOffset_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DateTime)) {
            return super.equals(obj);
        }
        DateTime other = (DateTime)obj;
        if (this.getYear() != other.getYear()) {
            return false;
        }
        if (this.getMonth() != other.getMonth()) {
            return false;
        }
        if (this.getDay() != other.getDay()) {
            return false;
        }
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
        if (!this.getTimeOffsetCase().equals(other.getTimeOffsetCase())) {
            return false;
        }
        switch (this.timeOffsetCase_) {
            case 8: {
                if (this.getUtcOffset().equals(other.getUtcOffset())) break;
                return false;
            }
            case 9: {
                if (this.getTimeZone().equals(other.getTimeZone())) break;
                return false;
            }
        }
        return this.getUnknownFields().equals(other.getUnknownFields());
    }

    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + DateTime.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getYear();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getMonth();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getDay();
        hash = 37 * hash + 4;
        hash = 53 * hash + this.getHours();
        hash = 37 * hash + 5;
        hash = 53 * hash + this.getMinutes();
        hash = 37 * hash + 6;
        hash = 53 * hash + this.getSeconds();
        hash = 37 * hash + 7;
        hash = 53 * hash + this.getNanos();
        switch (this.timeOffsetCase_) {
            case 8: {
                hash = 37 * hash + 8;
                hash = 53 * hash + this.getUtcOffset().hashCode();
                break;
            }
            case 9: {
                hash = 37 * hash + 9;
                hash = 53 * hash + this.getTimeZone().hashCode();
                break;
            }
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static DateTime parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static DateTime parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static DateTime parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static DateTime parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static DateTime parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static DateTime parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static DateTime parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static DateTime parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static DateTime parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static DateTime parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static DateTime parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static DateTime parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return DateTime.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(DateTime prototype) {
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

    public static DateTime getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<DateTime> parser() {
        return PARSER;
    }

    public Parser<DateTime> getParserForType() {
        return PARSER;
    }

    @Override
    public DateTime getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements DateTimeOrBuilder {
        private int timeOffsetCase_ = 0;
        private Object timeOffset_;
        private int bitField0_;
        private int year_;
        private int month_;
        private int day_;
        private int hours_;
        private int minutes_;
        private int seconds_;
        private int nanos_;
        private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> utcOffsetBuilder_;
        private SingleFieldBuilderV3<TimeZone, TimeZone.Builder, TimeZoneOrBuilder> timeZoneBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return DateTimeProto.internal_static_google_type_DateTime_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return DateTimeProto.internal_static_google_type_DateTime_fieldAccessorTable.ensureFieldAccessorsInitialized(DateTime.class, Builder.class);
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
            this.hours_ = 0;
            this.minutes_ = 0;
            this.seconds_ = 0;
            this.nanos_ = 0;
            if (this.utcOffsetBuilder_ != null) {
                this.utcOffsetBuilder_.clear();
            }
            if (this.timeZoneBuilder_ != null) {
                this.timeZoneBuilder_.clear();
            }
            this.timeOffsetCase_ = 0;
            this.timeOffset_ = null;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return DateTimeProto.internal_static_google_type_DateTime_descriptor;
        }

        @Override
        public DateTime getDefaultInstanceForType() {
            return DateTime.getDefaultInstance();
        }

        @Override
        public DateTime build() {
            DateTime result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public DateTime buildPartial() {
            DateTime result = new DateTime(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.buildPartialOneofs(result);
            this.onBuilt();
            return result;
        }

        private void buildPartial0(DateTime result) {
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
            if ((from_bitField0_ & 8) != 0) {
                result.hours_ = this.hours_;
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.minutes_ = this.minutes_;
            }
            if ((from_bitField0_ & 0x20) != 0) {
                result.seconds_ = this.seconds_;
            }
            if ((from_bitField0_ & 0x40) != 0) {
                result.nanos_ = this.nanos_;
            }
        }

        private void buildPartialOneofs(DateTime result) {
            result.timeOffsetCase_ = this.timeOffsetCase_;
            result.timeOffset_ = this.timeOffset_;
            if (this.timeOffsetCase_ == 8 && this.utcOffsetBuilder_ != null) {
                result.timeOffset_ = this.utcOffsetBuilder_.build();
            }
            if (this.timeOffsetCase_ == 9 && this.timeZoneBuilder_ != null) {
                result.timeOffset_ = this.timeZoneBuilder_.build();
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
            if (other instanceof DateTime) {
                return this.mergeFrom((DateTime)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(DateTime other) {
            if (other == DateTime.getDefaultInstance()) {
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
            switch (other.getTimeOffsetCase()) {
                case UTC_OFFSET: {
                    this.mergeUtcOffset(other.getUtcOffset());
                    break;
                }
                case TIME_ZONE: {
                    this.mergeTimeZone(other.getTimeZone());
                    break;
                }
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
                block17: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block17;
                        }
                        case 8: {
                            this.year_ = input.readInt32();
                            this.bitField0_ |= 1;
                            continue block17;
                        }
                        case 16: {
                            this.month_ = input.readInt32();
                            this.bitField0_ |= 2;
                            continue block17;
                        }
                        case 24: {
                            this.day_ = input.readInt32();
                            this.bitField0_ |= 4;
                            continue block17;
                        }
                        case 32: {
                            this.hours_ = input.readInt32();
                            this.bitField0_ |= 8;
                            continue block17;
                        }
                        case 40: {
                            this.minutes_ = input.readInt32();
                            this.bitField0_ |= 0x10;
                            continue block17;
                        }
                        case 48: {
                            this.seconds_ = input.readInt32();
                            this.bitField0_ |= 0x20;
                            continue block17;
                        }
                        case 56: {
                            this.nanos_ = input.readInt32();
                            this.bitField0_ |= 0x40;
                            continue block17;
                        }
                        case 66: {
                            input.readMessage(this.getUtcOffsetFieldBuilder().getBuilder(), extensionRegistry);
                            this.timeOffsetCase_ = 8;
                            continue block17;
                        }
                        case 74: {
                            input.readMessage(this.getTimeZoneFieldBuilder().getBuilder(), extensionRegistry);
                            this.timeOffsetCase_ = 9;
                            continue block17;
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
        public TimeOffsetCase getTimeOffsetCase() {
            return TimeOffsetCase.forNumber(this.timeOffsetCase_);
        }

        public Builder clearTimeOffset() {
            this.timeOffsetCase_ = 0;
            this.timeOffset_ = null;
            this.onChanged();
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
        public int getHours() {
            return this.hours_;
        }

        public Builder setHours(int value) {
            this.hours_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearHours() {
            this.bitField0_ &= 0xFFFFFFF7;
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
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearMinutes() {
            this.bitField0_ &= 0xFFFFFFEF;
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
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder clearSeconds() {
            this.bitField0_ &= 0xFFFFFFDF;
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
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder clearNanos() {
            this.bitField0_ &= 0xFFFFFFBF;
            this.nanos_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasUtcOffset() {
            return this.timeOffsetCase_ == 8;
        }

        @Override
        public Duration getUtcOffset() {
            if (this.utcOffsetBuilder_ == null) {
                if (this.timeOffsetCase_ == 8) {
                    return (Duration)this.timeOffset_;
                }
                return Duration.getDefaultInstance();
            }
            if (this.timeOffsetCase_ == 8) {
                return this.utcOffsetBuilder_.getMessage();
            }
            return Duration.getDefaultInstance();
        }

        public Builder setUtcOffset(Duration value) {
            if (this.utcOffsetBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.timeOffset_ = value;
                this.onChanged();
            } else {
                this.utcOffsetBuilder_.setMessage(value);
            }
            this.timeOffsetCase_ = 8;
            return this;
        }

        public Builder setUtcOffset(Duration.Builder builderForValue) {
            if (this.utcOffsetBuilder_ == null) {
                this.timeOffset_ = builderForValue.build();
                this.onChanged();
            } else {
                this.utcOffsetBuilder_.setMessage(builderForValue.build());
            }
            this.timeOffsetCase_ = 8;
            return this;
        }

        public Builder mergeUtcOffset(Duration value) {
            if (this.utcOffsetBuilder_ == null) {
                this.timeOffset_ = this.timeOffsetCase_ == 8 && this.timeOffset_ != Duration.getDefaultInstance() ? Duration.newBuilder((Duration)this.timeOffset_).mergeFrom(value).buildPartial() : value;
                this.onChanged();
            } else if (this.timeOffsetCase_ == 8) {
                this.utcOffsetBuilder_.mergeFrom(value);
            } else {
                this.utcOffsetBuilder_.setMessage(value);
            }
            this.timeOffsetCase_ = 8;
            return this;
        }

        public Builder clearUtcOffset() {
            if (this.utcOffsetBuilder_ == null) {
                if (this.timeOffsetCase_ == 8) {
                    this.timeOffsetCase_ = 0;
                    this.timeOffset_ = null;
                    this.onChanged();
                }
            } else {
                if (this.timeOffsetCase_ == 8) {
                    this.timeOffsetCase_ = 0;
                    this.timeOffset_ = null;
                }
                this.utcOffsetBuilder_.clear();
            }
            return this;
        }

        public Duration.Builder getUtcOffsetBuilder() {
            return this.getUtcOffsetFieldBuilder().getBuilder();
        }

        @Override
        public DurationOrBuilder getUtcOffsetOrBuilder() {
            if (this.timeOffsetCase_ == 8 && this.utcOffsetBuilder_ != null) {
                return this.utcOffsetBuilder_.getMessageOrBuilder();
            }
            if (this.timeOffsetCase_ == 8) {
                return (Duration)this.timeOffset_;
            }
            return Duration.getDefaultInstance();
        }

        private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> getUtcOffsetFieldBuilder() {
            if (this.utcOffsetBuilder_ == null) {
                if (this.timeOffsetCase_ != 8) {
                    this.timeOffset_ = Duration.getDefaultInstance();
                }
                this.utcOffsetBuilder_ = new SingleFieldBuilderV3((Duration)this.timeOffset_, this.getParentForChildren(), this.isClean());
                this.timeOffset_ = null;
            }
            this.timeOffsetCase_ = 8;
            this.onChanged();
            return this.utcOffsetBuilder_;
        }

        @Override
        public boolean hasTimeZone() {
            return this.timeOffsetCase_ == 9;
        }

        @Override
        public TimeZone getTimeZone() {
            if (this.timeZoneBuilder_ == null) {
                if (this.timeOffsetCase_ == 9) {
                    return (TimeZone)this.timeOffset_;
                }
                return TimeZone.getDefaultInstance();
            }
            if (this.timeOffsetCase_ == 9) {
                return this.timeZoneBuilder_.getMessage();
            }
            return TimeZone.getDefaultInstance();
        }

        public Builder setTimeZone(TimeZone value) {
            if (this.timeZoneBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.timeOffset_ = value;
                this.onChanged();
            } else {
                this.timeZoneBuilder_.setMessage(value);
            }
            this.timeOffsetCase_ = 9;
            return this;
        }

        public Builder setTimeZone(TimeZone.Builder builderForValue) {
            if (this.timeZoneBuilder_ == null) {
                this.timeOffset_ = builderForValue.build();
                this.onChanged();
            } else {
                this.timeZoneBuilder_.setMessage(builderForValue.build());
            }
            this.timeOffsetCase_ = 9;
            return this;
        }

        public Builder mergeTimeZone(TimeZone value) {
            if (this.timeZoneBuilder_ == null) {
                this.timeOffset_ = this.timeOffsetCase_ == 9 && this.timeOffset_ != TimeZone.getDefaultInstance() ? TimeZone.newBuilder((TimeZone)this.timeOffset_).mergeFrom(value).buildPartial() : value;
                this.onChanged();
            } else if (this.timeOffsetCase_ == 9) {
                this.timeZoneBuilder_.mergeFrom(value);
            } else {
                this.timeZoneBuilder_.setMessage(value);
            }
            this.timeOffsetCase_ = 9;
            return this;
        }

        public Builder clearTimeZone() {
            if (this.timeZoneBuilder_ == null) {
                if (this.timeOffsetCase_ == 9) {
                    this.timeOffsetCase_ = 0;
                    this.timeOffset_ = null;
                    this.onChanged();
                }
            } else {
                if (this.timeOffsetCase_ == 9) {
                    this.timeOffsetCase_ = 0;
                    this.timeOffset_ = null;
                }
                this.timeZoneBuilder_.clear();
            }
            return this;
        }

        public TimeZone.Builder getTimeZoneBuilder() {
            return this.getTimeZoneFieldBuilder().getBuilder();
        }

        @Override
        public TimeZoneOrBuilder getTimeZoneOrBuilder() {
            if (this.timeOffsetCase_ == 9 && this.timeZoneBuilder_ != null) {
                return this.timeZoneBuilder_.getMessageOrBuilder();
            }
            if (this.timeOffsetCase_ == 9) {
                return (TimeZone)this.timeOffset_;
            }
            return TimeZone.getDefaultInstance();
        }

        private SingleFieldBuilderV3<TimeZone, TimeZone.Builder, TimeZoneOrBuilder> getTimeZoneFieldBuilder() {
            if (this.timeZoneBuilder_ == null) {
                if (this.timeOffsetCase_ != 9) {
                    this.timeOffset_ = TimeZone.getDefaultInstance();
                }
                this.timeZoneBuilder_ = new SingleFieldBuilderV3((TimeZone)this.timeOffset_, this.getParentForChildren(), this.isClean());
                this.timeOffset_ = null;
            }
            this.timeOffsetCase_ = 9;
            this.onChanged();
            return this.timeZoneBuilder_;
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

    public static enum TimeOffsetCase implements Internal.EnumLite,
    AbstractMessageLite.InternalOneOfEnum
    {
        UTC_OFFSET(8),
        TIME_ZONE(9),
        TIMEOFFSET_NOT_SET(0);

        private final int value;

        private TimeOffsetCase(int value) {
            this.value = value;
        }

        @Deprecated
        public static TimeOffsetCase valueOf(int value) {
            return TimeOffsetCase.forNumber(value);
        }

        public static TimeOffsetCase forNumber(int value) {
            switch (value) {
                case 8: {
                    return UTC_OFFSET;
                }
                case 9: {
                    return TIME_ZONE;
                }
                case 0: {
                    return TIMEOFFSET_NOT_SET;
                }
            }
            return null;
        }

        @Override
        public int getNumber() {
            return this.value;
        }
    }
}

