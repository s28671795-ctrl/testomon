/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ClientProto;
import com.google.api.MethodSettingsOrBuilder;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Duration;
import com.google.protobuf.DurationOrBuilder;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class MethodSettings
extends GeneratedMessageV3
implements MethodSettingsOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int SELECTOR_FIELD_NUMBER = 1;
    private volatile Object selector_ = "";
    public static final int LONG_RUNNING_FIELD_NUMBER = 2;
    private LongRunning longRunning_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final MethodSettings DEFAULT_INSTANCE = new MethodSettings();
    private static final Parser<MethodSettings> PARSER = new AbstractParser<MethodSettings>(){

        @Override
        public MethodSettings parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = MethodSettings.newBuilder();
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

    private MethodSettings(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private MethodSettings() {
        this.selector_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new MethodSettings();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ClientProto.internal_static_google_api_MethodSettings_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ClientProto.internal_static_google_api_MethodSettings_fieldAccessorTable.ensureFieldAccessorsInitialized(MethodSettings.class, Builder.class);
    }

    @Override
    public String getSelector() {
        Object ref = this.selector_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.selector_ = s2;
        return s2;
    }

    @Override
    public ByteString getSelectorBytes() {
        Object ref = this.selector_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.selector_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasLongRunning() {
        return this.longRunning_ != null;
    }

    @Override
    public LongRunning getLongRunning() {
        return this.longRunning_ == null ? LongRunning.getDefaultInstance() : this.longRunning_;
    }

    @Override
    public LongRunningOrBuilder getLongRunningOrBuilder() {
        return this.longRunning_ == null ? LongRunning.getDefaultInstance() : this.longRunning_;
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
        if (!GeneratedMessageV3.isStringEmpty(this.selector_)) {
            GeneratedMessageV3.writeString(output, 1, this.selector_);
        }
        if (this.longRunning_ != null) {
            output.writeMessage(2, this.getLongRunning());
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
        if (!GeneratedMessageV3.isStringEmpty(this.selector_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.selector_);
        }
        if (this.longRunning_ != null) {
            size += CodedOutputStream.computeMessageSize(2, this.getLongRunning());
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MethodSettings)) {
            return super.equals(obj);
        }
        MethodSettings other = (MethodSettings)obj;
        if (!this.getSelector().equals(other.getSelector())) {
            return false;
        }
        if (this.hasLongRunning() != other.hasLongRunning()) {
            return false;
        }
        if (this.hasLongRunning() && !this.getLongRunning().equals(other.getLongRunning())) {
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
        hash = 19 * hash + MethodSettings.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getSelector().hashCode();
        if (this.hasLongRunning()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getLongRunning().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static MethodSettings parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MethodSettings parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MethodSettings parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MethodSettings parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MethodSettings parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MethodSettings parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MethodSettings parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static MethodSettings parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static MethodSettings parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static MethodSettings parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static MethodSettings parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static MethodSettings parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return MethodSettings.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(MethodSettings prototype) {
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

    public static MethodSettings getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<MethodSettings> parser() {
        return PARSER;
    }

    public Parser<MethodSettings> getParserForType() {
        return PARSER;
    }

    @Override
    public MethodSettings getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class LongRunning
    extends GeneratedMessageV3
    implements LongRunningOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int INITIAL_POLL_DELAY_FIELD_NUMBER = 1;
        private Duration initialPollDelay_;
        public static final int POLL_DELAY_MULTIPLIER_FIELD_NUMBER = 2;
        private float pollDelayMultiplier_ = 0.0f;
        public static final int MAX_POLL_DELAY_FIELD_NUMBER = 3;
        private Duration maxPollDelay_;
        public static final int TOTAL_POLL_TIMEOUT_FIELD_NUMBER = 4;
        private Duration totalPollTimeout_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final LongRunning DEFAULT_INSTANCE = new LongRunning();
        private static final Parser<LongRunning> PARSER = new AbstractParser<LongRunning>(){

            @Override
            public LongRunning parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = LongRunning.newBuilder();
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

        private LongRunning(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private LongRunning() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new LongRunning();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return ClientProto.internal_static_google_api_MethodSettings_LongRunning_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ClientProto.internal_static_google_api_MethodSettings_LongRunning_fieldAccessorTable.ensureFieldAccessorsInitialized(LongRunning.class, Builder.class);
        }

        @Override
        public boolean hasInitialPollDelay() {
            return this.initialPollDelay_ != null;
        }

        @Override
        public Duration getInitialPollDelay() {
            return this.initialPollDelay_ == null ? Duration.getDefaultInstance() : this.initialPollDelay_;
        }

        @Override
        public DurationOrBuilder getInitialPollDelayOrBuilder() {
            return this.initialPollDelay_ == null ? Duration.getDefaultInstance() : this.initialPollDelay_;
        }

        @Override
        public float getPollDelayMultiplier() {
            return this.pollDelayMultiplier_;
        }

        @Override
        public boolean hasMaxPollDelay() {
            return this.maxPollDelay_ != null;
        }

        @Override
        public Duration getMaxPollDelay() {
            return this.maxPollDelay_ == null ? Duration.getDefaultInstance() : this.maxPollDelay_;
        }

        @Override
        public DurationOrBuilder getMaxPollDelayOrBuilder() {
            return this.maxPollDelay_ == null ? Duration.getDefaultInstance() : this.maxPollDelay_;
        }

        @Override
        public boolean hasTotalPollTimeout() {
            return this.totalPollTimeout_ != null;
        }

        @Override
        public Duration getTotalPollTimeout() {
            return this.totalPollTimeout_ == null ? Duration.getDefaultInstance() : this.totalPollTimeout_;
        }

        @Override
        public DurationOrBuilder getTotalPollTimeoutOrBuilder() {
            return this.totalPollTimeout_ == null ? Duration.getDefaultInstance() : this.totalPollTimeout_;
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
            if (this.initialPollDelay_ != null) {
                output.writeMessage(1, this.getInitialPollDelay());
            }
            if (Float.floatToRawIntBits(this.pollDelayMultiplier_) != 0) {
                output.writeFloat(2, this.pollDelayMultiplier_);
            }
            if (this.maxPollDelay_ != null) {
                output.writeMessage(3, this.getMaxPollDelay());
            }
            if (this.totalPollTimeout_ != null) {
                output.writeMessage(4, this.getTotalPollTimeout());
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
            if (this.initialPollDelay_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getInitialPollDelay());
            }
            if (Float.floatToRawIntBits(this.pollDelayMultiplier_) != 0) {
                size += CodedOutputStream.computeFloatSize(2, this.pollDelayMultiplier_);
            }
            if (this.maxPollDelay_ != null) {
                size += CodedOutputStream.computeMessageSize(3, this.getMaxPollDelay());
            }
            if (this.totalPollTimeout_ != null) {
                size += CodedOutputStream.computeMessageSize(4, this.getTotalPollTimeout());
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof LongRunning)) {
                return super.equals(obj);
            }
            LongRunning other = (LongRunning)obj;
            if (this.hasInitialPollDelay() != other.hasInitialPollDelay()) {
                return false;
            }
            if (this.hasInitialPollDelay() && !this.getInitialPollDelay().equals(other.getInitialPollDelay())) {
                return false;
            }
            if (Float.floatToIntBits(this.getPollDelayMultiplier()) != Float.floatToIntBits(other.getPollDelayMultiplier())) {
                return false;
            }
            if (this.hasMaxPollDelay() != other.hasMaxPollDelay()) {
                return false;
            }
            if (this.hasMaxPollDelay() && !this.getMaxPollDelay().equals(other.getMaxPollDelay())) {
                return false;
            }
            if (this.hasTotalPollTimeout() != other.hasTotalPollTimeout()) {
                return false;
            }
            if (this.hasTotalPollTimeout() && !this.getTotalPollTimeout().equals(other.getTotalPollTimeout())) {
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
            hash = 19 * hash + LongRunning.getDescriptor().hashCode();
            if (this.hasInitialPollDelay()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getInitialPollDelay().hashCode();
            }
            hash = 37 * hash + 2;
            hash = 53 * hash + Float.floatToIntBits(this.getPollDelayMultiplier());
            if (this.hasMaxPollDelay()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getMaxPollDelay().hashCode();
            }
            if (this.hasTotalPollTimeout()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getTotalPollTimeout().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static LongRunning parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LongRunning parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LongRunning parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LongRunning parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LongRunning parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LongRunning parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LongRunning parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static LongRunning parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static LongRunning parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static LongRunning parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static LongRunning parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static LongRunning parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return LongRunning.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(LongRunning prototype) {
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

        public static LongRunning getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<LongRunning> parser() {
            return PARSER;
        }

        public Parser<LongRunning> getParserForType() {
            return PARSER;
        }

        @Override
        public LongRunning getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements LongRunningOrBuilder {
            private int bitField0_;
            private Duration initialPollDelay_;
            private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> initialPollDelayBuilder_;
            private float pollDelayMultiplier_;
            private Duration maxPollDelay_;
            private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> maxPollDelayBuilder_;
            private Duration totalPollTimeout_;
            private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> totalPollTimeoutBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return ClientProto.internal_static_google_api_MethodSettings_LongRunning_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return ClientProto.internal_static_google_api_MethodSettings_LongRunning_fieldAccessorTable.ensureFieldAccessorsInitialized(LongRunning.class, Builder.class);
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
                this.initialPollDelay_ = null;
                if (this.initialPollDelayBuilder_ != null) {
                    this.initialPollDelayBuilder_.dispose();
                    this.initialPollDelayBuilder_ = null;
                }
                this.pollDelayMultiplier_ = 0.0f;
                this.maxPollDelay_ = null;
                if (this.maxPollDelayBuilder_ != null) {
                    this.maxPollDelayBuilder_.dispose();
                    this.maxPollDelayBuilder_ = null;
                }
                this.totalPollTimeout_ = null;
                if (this.totalPollTimeoutBuilder_ != null) {
                    this.totalPollTimeoutBuilder_.dispose();
                    this.totalPollTimeoutBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return ClientProto.internal_static_google_api_MethodSettings_LongRunning_descriptor;
            }

            @Override
            public LongRunning getDefaultInstanceForType() {
                return LongRunning.getDefaultInstance();
            }

            @Override
            public LongRunning build() {
                LongRunning result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public LongRunning buildPartial() {
                LongRunning result = new LongRunning(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(LongRunning result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.initialPollDelay_ = this.initialPollDelayBuilder_ == null ? this.initialPollDelay_ : this.initialPollDelayBuilder_.build();
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.pollDelayMultiplier_ = this.pollDelayMultiplier_;
                }
                if ((from_bitField0_ & 4) != 0) {
                    result.maxPollDelay_ = this.maxPollDelayBuilder_ == null ? this.maxPollDelay_ : this.maxPollDelayBuilder_.build();
                }
                if ((from_bitField0_ & 8) != 0) {
                    result.totalPollTimeout_ = this.totalPollTimeoutBuilder_ == null ? this.totalPollTimeout_ : this.totalPollTimeoutBuilder_.build();
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
                if (other instanceof LongRunning) {
                    return this.mergeFrom((LongRunning)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(LongRunning other) {
                if (other == LongRunning.getDefaultInstance()) {
                    return this;
                }
                if (other.hasInitialPollDelay()) {
                    this.mergeInitialPollDelay(other.getInitialPollDelay());
                }
                if (other.getPollDelayMultiplier() != 0.0f) {
                    this.setPollDelayMultiplier(other.getPollDelayMultiplier());
                }
                if (other.hasMaxPollDelay()) {
                    this.mergeMaxPollDelay(other.getMaxPollDelay());
                }
                if (other.hasTotalPollTimeout()) {
                    this.mergeTotalPollTimeout(other.getTotalPollTimeout());
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
                            case 10: {
                                input.readMessage(this.getInitialPollDelayFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 1;
                                continue block12;
                            }
                            case 21: {
                                this.pollDelayMultiplier_ = input.readFloat();
                                this.bitField0_ |= 2;
                                continue block12;
                            }
                            case 26: {
                                input.readMessage(this.getMaxPollDelayFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 4;
                                continue block12;
                            }
                            case 34: {
                                input.readMessage(this.getTotalPollTimeoutFieldBuilder().getBuilder(), extensionRegistry);
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
            public boolean hasInitialPollDelay() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public Duration getInitialPollDelay() {
                if (this.initialPollDelayBuilder_ == null) {
                    return this.initialPollDelay_ == null ? Duration.getDefaultInstance() : this.initialPollDelay_;
                }
                return this.initialPollDelayBuilder_.getMessage();
            }

            public Builder setInitialPollDelay(Duration value) {
                if (this.initialPollDelayBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.initialPollDelay_ = value;
                } else {
                    this.initialPollDelayBuilder_.setMessage(value);
                }
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder setInitialPollDelay(Duration.Builder builderForValue) {
                if (this.initialPollDelayBuilder_ == null) {
                    this.initialPollDelay_ = builderForValue.build();
                } else {
                    this.initialPollDelayBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder mergeInitialPollDelay(Duration value) {
                if (this.initialPollDelayBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 0 && this.initialPollDelay_ != null && this.initialPollDelay_ != Duration.getDefaultInstance()) {
                        this.getInitialPollDelayBuilder().mergeFrom(value);
                    } else {
                        this.initialPollDelay_ = value;
                    }
                } else {
                    this.initialPollDelayBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearInitialPollDelay() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.initialPollDelay_ = null;
                if (this.initialPollDelayBuilder_ != null) {
                    this.initialPollDelayBuilder_.dispose();
                    this.initialPollDelayBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Duration.Builder getInitialPollDelayBuilder() {
                this.bitField0_ |= 1;
                this.onChanged();
                return this.getInitialPollDelayFieldBuilder().getBuilder();
            }

            @Override
            public DurationOrBuilder getInitialPollDelayOrBuilder() {
                if (this.initialPollDelayBuilder_ != null) {
                    return this.initialPollDelayBuilder_.getMessageOrBuilder();
                }
                return this.initialPollDelay_ == null ? Duration.getDefaultInstance() : this.initialPollDelay_;
            }

            private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> getInitialPollDelayFieldBuilder() {
                if (this.initialPollDelayBuilder_ == null) {
                    this.initialPollDelayBuilder_ = new SingleFieldBuilderV3(this.getInitialPollDelay(), this.getParentForChildren(), this.isClean());
                    this.initialPollDelay_ = null;
                }
                return this.initialPollDelayBuilder_;
            }

            @Override
            public float getPollDelayMultiplier() {
                return this.pollDelayMultiplier_;
            }

            public Builder setPollDelayMultiplier(float value) {
                this.pollDelayMultiplier_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearPollDelayMultiplier() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.pollDelayMultiplier_ = 0.0f;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasMaxPollDelay() {
                return (this.bitField0_ & 4) != 0;
            }

            @Override
            public Duration getMaxPollDelay() {
                if (this.maxPollDelayBuilder_ == null) {
                    return this.maxPollDelay_ == null ? Duration.getDefaultInstance() : this.maxPollDelay_;
                }
                return this.maxPollDelayBuilder_.getMessage();
            }

            public Builder setMaxPollDelay(Duration value) {
                if (this.maxPollDelayBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.maxPollDelay_ = value;
                } else {
                    this.maxPollDelayBuilder_.setMessage(value);
                }
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder setMaxPollDelay(Duration.Builder builderForValue) {
                if (this.maxPollDelayBuilder_ == null) {
                    this.maxPollDelay_ = builderForValue.build();
                } else {
                    this.maxPollDelayBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder mergeMaxPollDelay(Duration value) {
                if (this.maxPollDelayBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 0 && this.maxPollDelay_ != null && this.maxPollDelay_ != Duration.getDefaultInstance()) {
                        this.getMaxPollDelayBuilder().mergeFrom(value);
                    } else {
                        this.maxPollDelay_ = value;
                    }
                } else {
                    this.maxPollDelayBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder clearMaxPollDelay() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.maxPollDelay_ = null;
                if (this.maxPollDelayBuilder_ != null) {
                    this.maxPollDelayBuilder_.dispose();
                    this.maxPollDelayBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Duration.Builder getMaxPollDelayBuilder() {
                this.bitField0_ |= 4;
                this.onChanged();
                return this.getMaxPollDelayFieldBuilder().getBuilder();
            }

            @Override
            public DurationOrBuilder getMaxPollDelayOrBuilder() {
                if (this.maxPollDelayBuilder_ != null) {
                    return this.maxPollDelayBuilder_.getMessageOrBuilder();
                }
                return this.maxPollDelay_ == null ? Duration.getDefaultInstance() : this.maxPollDelay_;
            }

            private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> getMaxPollDelayFieldBuilder() {
                if (this.maxPollDelayBuilder_ == null) {
                    this.maxPollDelayBuilder_ = new SingleFieldBuilderV3(this.getMaxPollDelay(), this.getParentForChildren(), this.isClean());
                    this.maxPollDelay_ = null;
                }
                return this.maxPollDelayBuilder_;
            }

            @Override
            public boolean hasTotalPollTimeout() {
                return (this.bitField0_ & 8) != 0;
            }

            @Override
            public Duration getTotalPollTimeout() {
                if (this.totalPollTimeoutBuilder_ == null) {
                    return this.totalPollTimeout_ == null ? Duration.getDefaultInstance() : this.totalPollTimeout_;
                }
                return this.totalPollTimeoutBuilder_.getMessage();
            }

            public Builder setTotalPollTimeout(Duration value) {
                if (this.totalPollTimeoutBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.totalPollTimeout_ = value;
                } else {
                    this.totalPollTimeoutBuilder_.setMessage(value);
                }
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder setTotalPollTimeout(Duration.Builder builderForValue) {
                if (this.totalPollTimeoutBuilder_ == null) {
                    this.totalPollTimeout_ = builderForValue.build();
                } else {
                    this.totalPollTimeoutBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder mergeTotalPollTimeout(Duration value) {
                if (this.totalPollTimeoutBuilder_ == null) {
                    if ((this.bitField0_ & 8) != 0 && this.totalPollTimeout_ != null && this.totalPollTimeout_ != Duration.getDefaultInstance()) {
                        this.getTotalPollTimeoutBuilder().mergeFrom(value);
                    } else {
                        this.totalPollTimeout_ = value;
                    }
                } else {
                    this.totalPollTimeoutBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder clearTotalPollTimeout() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.totalPollTimeout_ = null;
                if (this.totalPollTimeoutBuilder_ != null) {
                    this.totalPollTimeoutBuilder_.dispose();
                    this.totalPollTimeoutBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Duration.Builder getTotalPollTimeoutBuilder() {
                this.bitField0_ |= 8;
                this.onChanged();
                return this.getTotalPollTimeoutFieldBuilder().getBuilder();
            }

            @Override
            public DurationOrBuilder getTotalPollTimeoutOrBuilder() {
                if (this.totalPollTimeoutBuilder_ != null) {
                    return this.totalPollTimeoutBuilder_.getMessageOrBuilder();
                }
                return this.totalPollTimeout_ == null ? Duration.getDefaultInstance() : this.totalPollTimeout_;
            }

            private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> getTotalPollTimeoutFieldBuilder() {
                if (this.totalPollTimeoutBuilder_ == null) {
                    this.totalPollTimeoutBuilder_ = new SingleFieldBuilderV3(this.getTotalPollTimeout(), this.getParentForChildren(), this.isClean());
                    this.totalPollTimeout_ = null;
                }
                return this.totalPollTimeoutBuilder_;
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

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements MethodSettingsOrBuilder {
        private int bitField0_;
        private Object selector_ = "";
        private LongRunning longRunning_;
        private SingleFieldBuilderV3<LongRunning, LongRunning.Builder, LongRunningOrBuilder> longRunningBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return ClientProto.internal_static_google_api_MethodSettings_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ClientProto.internal_static_google_api_MethodSettings_fieldAccessorTable.ensureFieldAccessorsInitialized(MethodSettings.class, Builder.class);
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
            this.selector_ = "";
            this.longRunning_ = null;
            if (this.longRunningBuilder_ != null) {
                this.longRunningBuilder_.dispose();
                this.longRunningBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ClientProto.internal_static_google_api_MethodSettings_descriptor;
        }

        @Override
        public MethodSettings getDefaultInstanceForType() {
            return MethodSettings.getDefaultInstance();
        }

        @Override
        public MethodSettings build() {
            MethodSettings result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public MethodSettings buildPartial() {
            MethodSettings result = new MethodSettings(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(MethodSettings result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.selector_ = this.selector_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.longRunning_ = this.longRunningBuilder_ == null ? this.longRunning_ : this.longRunningBuilder_.build();
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
            if (other instanceof MethodSettings) {
                return this.mergeFrom((MethodSettings)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(MethodSettings other) {
            if (other == MethodSettings.getDefaultInstance()) {
                return this;
            }
            if (!other.getSelector().isEmpty()) {
                this.selector_ = other.selector_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (other.hasLongRunning()) {
                this.mergeLongRunning(other.getLongRunning());
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
                            this.selector_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            input.readMessage(this.getLongRunningFieldBuilder().getBuilder(), extensionRegistry);
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
        public String getSelector() {
            Object ref = this.selector_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.selector_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getSelectorBytes() {
            Object ref = this.selector_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.selector_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setSelector(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.selector_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearSelector() {
            this.selector_ = MethodSettings.getDefaultInstance().getSelector();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setSelectorBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            MethodSettings.checkByteStringIsUtf8(value);
            this.selector_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasLongRunning() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override
        public LongRunning getLongRunning() {
            if (this.longRunningBuilder_ == null) {
                return this.longRunning_ == null ? LongRunning.getDefaultInstance() : this.longRunning_;
            }
            return this.longRunningBuilder_.getMessage();
        }

        public Builder setLongRunning(LongRunning value) {
            if (this.longRunningBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.longRunning_ = value;
            } else {
                this.longRunningBuilder_.setMessage(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder setLongRunning(LongRunning.Builder builderForValue) {
            if (this.longRunningBuilder_ == null) {
                this.longRunning_ = builderForValue.build();
            } else {
                this.longRunningBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder mergeLongRunning(LongRunning value) {
            if (this.longRunningBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0 && this.longRunning_ != null && this.longRunning_ != LongRunning.getDefaultInstance()) {
                    this.getLongRunningBuilder().mergeFrom(value);
                } else {
                    this.longRunning_ = value;
                }
            } else {
                this.longRunningBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearLongRunning() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.longRunning_ = null;
            if (this.longRunningBuilder_ != null) {
                this.longRunningBuilder_.dispose();
                this.longRunningBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public LongRunning.Builder getLongRunningBuilder() {
            this.bitField0_ |= 2;
            this.onChanged();
            return this.getLongRunningFieldBuilder().getBuilder();
        }

        @Override
        public LongRunningOrBuilder getLongRunningOrBuilder() {
            if (this.longRunningBuilder_ != null) {
                return this.longRunningBuilder_.getMessageOrBuilder();
            }
            return this.longRunning_ == null ? LongRunning.getDefaultInstance() : this.longRunning_;
        }

        private SingleFieldBuilderV3<LongRunning, LongRunning.Builder, LongRunningOrBuilder> getLongRunningFieldBuilder() {
            if (this.longRunningBuilder_ == null) {
                this.longRunningBuilder_ = new SingleFieldBuilderV3(this.getLongRunning(), this.getParentForChildren(), this.isClean());
                this.longRunning_ = null;
            }
            return this.longRunningBuilder_;
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

    public static interface LongRunningOrBuilder
    extends MessageOrBuilder {
        public boolean hasInitialPollDelay();

        public Duration getInitialPollDelay();

        public DurationOrBuilder getInitialPollDelayOrBuilder();

        public float getPollDelayMultiplier();

        public boolean hasMaxPollDelay();

        public Duration getMaxPollDelay();

        public DurationOrBuilder getMaxPollDelayOrBuilder();

        public boolean hasTotalPollTimeout();

        public Duration getTotalPollTimeout();

        public DurationOrBuilder getTotalPollTimeoutOrBuilder();
    }
}

