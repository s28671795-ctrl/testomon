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
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.Timestamp;
import com.google.protobuf.TimestampOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.type.IntervalOrBuilder;
import com.google.type.IntervalProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Interval
extends GeneratedMessageV3
implements IntervalOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int START_TIME_FIELD_NUMBER = 1;
    private Timestamp startTime_;
    public static final int END_TIME_FIELD_NUMBER = 2;
    private Timestamp endTime_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Interval DEFAULT_INSTANCE = new Interval();
    private static final Parser<Interval> PARSER = new AbstractParser<Interval>(){

        @Override
        public Interval parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Interval.newBuilder();
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

    private Interval(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Interval() {
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Interval();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return IntervalProto.internal_static_google_type_Interval_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return IntervalProto.internal_static_google_type_Interval_fieldAccessorTable.ensureFieldAccessorsInitialized(Interval.class, Builder.class);
    }

    @Override
    public boolean hasStartTime() {
        return this.startTime_ != null;
    }

    @Override
    public Timestamp getStartTime() {
        return this.startTime_ == null ? Timestamp.getDefaultInstance() : this.startTime_;
    }

    @Override
    public TimestampOrBuilder getStartTimeOrBuilder() {
        return this.startTime_ == null ? Timestamp.getDefaultInstance() : this.startTime_;
    }

    @Override
    public boolean hasEndTime() {
        return this.endTime_ != null;
    }

    @Override
    public Timestamp getEndTime() {
        return this.endTime_ == null ? Timestamp.getDefaultInstance() : this.endTime_;
    }

    @Override
    public TimestampOrBuilder getEndTimeOrBuilder() {
        return this.endTime_ == null ? Timestamp.getDefaultInstance() : this.endTime_;
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
        if (this.startTime_ != null) {
            output.writeMessage(1, this.getStartTime());
        }
        if (this.endTime_ != null) {
            output.writeMessage(2, this.getEndTime());
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
        if (this.startTime_ != null) {
            size += CodedOutputStream.computeMessageSize(1, this.getStartTime());
        }
        if (this.endTime_ != null) {
            size += CodedOutputStream.computeMessageSize(2, this.getEndTime());
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Interval)) {
            return super.equals(obj);
        }
        Interval other = (Interval)obj;
        if (this.hasStartTime() != other.hasStartTime()) {
            return false;
        }
        if (this.hasStartTime() && !this.getStartTime().equals(other.getStartTime())) {
            return false;
        }
        if (this.hasEndTime() != other.hasEndTime()) {
            return false;
        }
        if (this.hasEndTime() && !this.getEndTime().equals(other.getEndTime())) {
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
        hash = 19 * hash + Interval.getDescriptor().hashCode();
        if (this.hasStartTime()) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getStartTime().hashCode();
        }
        if (this.hasEndTime()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getEndTime().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Interval parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Interval parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Interval parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Interval parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Interval parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Interval parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Interval parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Interval parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Interval parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Interval parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Interval parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Interval parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Interval.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Interval prototype) {
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

    public static Interval getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Interval> parser() {
        return PARSER;
    }

    public Parser<Interval> getParserForType() {
        return PARSER;
    }

    @Override
    public Interval getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements IntervalOrBuilder {
        private int bitField0_;
        private Timestamp startTime_;
        private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> startTimeBuilder_;
        private Timestamp endTime_;
        private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> endTimeBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return IntervalProto.internal_static_google_type_Interval_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return IntervalProto.internal_static_google_type_Interval_fieldAccessorTable.ensureFieldAccessorsInitialized(Interval.class, Builder.class);
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
            this.startTime_ = null;
            if (this.startTimeBuilder_ != null) {
                this.startTimeBuilder_.dispose();
                this.startTimeBuilder_ = null;
            }
            this.endTime_ = null;
            if (this.endTimeBuilder_ != null) {
                this.endTimeBuilder_.dispose();
                this.endTimeBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return IntervalProto.internal_static_google_type_Interval_descriptor;
        }

        @Override
        public Interval getDefaultInstanceForType() {
            return Interval.getDefaultInstance();
        }

        @Override
        public Interval build() {
            Interval result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Interval buildPartial() {
            Interval result = new Interval(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(Interval result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.startTime_ = this.startTimeBuilder_ == null ? this.startTime_ : this.startTimeBuilder_.build();
            }
            if ((from_bitField0_ & 2) != 0) {
                result.endTime_ = this.endTimeBuilder_ == null ? this.endTime_ : this.endTimeBuilder_.build();
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
            if (other instanceof Interval) {
                return this.mergeFrom((Interval)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Interval other) {
            if (other == Interval.getDefaultInstance()) {
                return this;
            }
            if (other.hasStartTime()) {
                this.mergeStartTime(other.getStartTime());
            }
            if (other.hasEndTime()) {
                this.mergeEndTime(other.getEndTime());
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
                            input.readMessage(this.getStartTimeFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            input.readMessage(this.getEndTimeFieldBuilder().getBuilder(), extensionRegistry);
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
        public boolean hasStartTime() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public Timestamp getStartTime() {
            if (this.startTimeBuilder_ == null) {
                return this.startTime_ == null ? Timestamp.getDefaultInstance() : this.startTime_;
            }
            return this.startTimeBuilder_.getMessage();
        }

        public Builder setStartTime(Timestamp value) {
            if (this.startTimeBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.startTime_ = value;
            } else {
                this.startTimeBuilder_.setMessage(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder setStartTime(Timestamp.Builder builderForValue) {
            if (this.startTimeBuilder_ == null) {
                this.startTime_ = builderForValue.build();
            } else {
                this.startTimeBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder mergeStartTime(Timestamp value) {
            if (this.startTimeBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0 && this.startTime_ != null && this.startTime_ != Timestamp.getDefaultInstance()) {
                    this.getStartTimeBuilder().mergeFrom(value);
                } else {
                    this.startTime_ = value;
                }
            } else {
                this.startTimeBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearStartTime() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.startTime_ = null;
            if (this.startTimeBuilder_ != null) {
                this.startTimeBuilder_.dispose();
                this.startTimeBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Timestamp.Builder getStartTimeBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return this.getStartTimeFieldBuilder().getBuilder();
        }

        @Override
        public TimestampOrBuilder getStartTimeOrBuilder() {
            if (this.startTimeBuilder_ != null) {
                return this.startTimeBuilder_.getMessageOrBuilder();
            }
            return this.startTime_ == null ? Timestamp.getDefaultInstance() : this.startTime_;
        }

        private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> getStartTimeFieldBuilder() {
            if (this.startTimeBuilder_ == null) {
                this.startTimeBuilder_ = new SingleFieldBuilderV3(this.getStartTime(), this.getParentForChildren(), this.isClean());
                this.startTime_ = null;
            }
            return this.startTimeBuilder_;
        }

        @Override
        public boolean hasEndTime() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override
        public Timestamp getEndTime() {
            if (this.endTimeBuilder_ == null) {
                return this.endTime_ == null ? Timestamp.getDefaultInstance() : this.endTime_;
            }
            return this.endTimeBuilder_.getMessage();
        }

        public Builder setEndTime(Timestamp value) {
            if (this.endTimeBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.endTime_ = value;
            } else {
                this.endTimeBuilder_.setMessage(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder setEndTime(Timestamp.Builder builderForValue) {
            if (this.endTimeBuilder_ == null) {
                this.endTime_ = builderForValue.build();
            } else {
                this.endTimeBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder mergeEndTime(Timestamp value) {
            if (this.endTimeBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0 && this.endTime_ != null && this.endTime_ != Timestamp.getDefaultInstance()) {
                    this.getEndTimeBuilder().mergeFrom(value);
                } else {
                    this.endTime_ = value;
                }
            } else {
                this.endTimeBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearEndTime() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.endTime_ = null;
            if (this.endTimeBuilder_ != null) {
                this.endTimeBuilder_.dispose();
                this.endTimeBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Timestamp.Builder getEndTimeBuilder() {
            this.bitField0_ |= 2;
            this.onChanged();
            return this.getEndTimeFieldBuilder().getBuilder();
        }

        @Override
        public TimestampOrBuilder getEndTimeOrBuilder() {
            if (this.endTimeBuilder_ != null) {
                return this.endTimeBuilder_.getMessageOrBuilder();
            }
            return this.endTime_ == null ? Timestamp.getDefaultInstance() : this.endTime_;
        }

        private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> getEndTimeFieldBuilder() {
            if (this.endTimeBuilder_ == null) {
                this.endTimeBuilder_ = new SingleFieldBuilderV3(this.getEndTime(), this.getParentForChildren(), this.isClean());
                this.endTime_ = null;
            }
            return this.endTimeBuilder_;
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

