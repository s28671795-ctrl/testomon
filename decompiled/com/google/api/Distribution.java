/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.DistributionOrBuilder;
import com.google.api.DistributionProto;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.Any;
import com.google.protobuf.AnyOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.Timestamp;
import com.google.protobuf.TimestampOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Distribution
extends GeneratedMessageV3
implements DistributionOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int COUNT_FIELD_NUMBER = 1;
    private long count_ = 0L;
    public static final int MEAN_FIELD_NUMBER = 2;
    private double mean_ = 0.0;
    public static final int SUM_OF_SQUARED_DEVIATION_FIELD_NUMBER = 3;
    private double sumOfSquaredDeviation_ = 0.0;
    public static final int RANGE_FIELD_NUMBER = 4;
    private Range range_;
    public static final int BUCKET_OPTIONS_FIELD_NUMBER = 6;
    private BucketOptions bucketOptions_;
    public static final int BUCKET_COUNTS_FIELD_NUMBER = 7;
    private Internal.LongList bucketCounts_;
    private int bucketCountsMemoizedSerializedSize = -1;
    public static final int EXEMPLARS_FIELD_NUMBER = 10;
    private List<Exemplar> exemplars_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Distribution DEFAULT_INSTANCE = new Distribution();
    private static final Parser<Distribution> PARSER = new AbstractParser<Distribution>(){

        @Override
        public Distribution parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Distribution.newBuilder();
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

    private Distribution(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Distribution() {
        this.bucketCounts_ = Distribution.emptyLongList();
        this.exemplars_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Distribution();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return DistributionProto.internal_static_google_api_Distribution_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return DistributionProto.internal_static_google_api_Distribution_fieldAccessorTable.ensureFieldAccessorsInitialized(Distribution.class, Builder.class);
    }

    @Override
    public long getCount() {
        return this.count_;
    }

    @Override
    public double getMean() {
        return this.mean_;
    }

    @Override
    public double getSumOfSquaredDeviation() {
        return this.sumOfSquaredDeviation_;
    }

    @Override
    public boolean hasRange() {
        return this.range_ != null;
    }

    @Override
    public Range getRange() {
        return this.range_ == null ? Range.getDefaultInstance() : this.range_;
    }

    @Override
    public RangeOrBuilder getRangeOrBuilder() {
        return this.range_ == null ? Range.getDefaultInstance() : this.range_;
    }

    @Override
    public boolean hasBucketOptions() {
        return this.bucketOptions_ != null;
    }

    @Override
    public BucketOptions getBucketOptions() {
        return this.bucketOptions_ == null ? BucketOptions.getDefaultInstance() : this.bucketOptions_;
    }

    @Override
    public BucketOptionsOrBuilder getBucketOptionsOrBuilder() {
        return this.bucketOptions_ == null ? BucketOptions.getDefaultInstance() : this.bucketOptions_;
    }

    @Override
    public List<Long> getBucketCountsList() {
        return this.bucketCounts_;
    }

    @Override
    public int getBucketCountsCount() {
        return this.bucketCounts_.size();
    }

    @Override
    public long getBucketCounts(int index) {
        return this.bucketCounts_.getLong(index);
    }

    @Override
    public List<Exemplar> getExemplarsList() {
        return this.exemplars_;
    }

    @Override
    public List<? extends ExemplarOrBuilder> getExemplarsOrBuilderList() {
        return this.exemplars_;
    }

    @Override
    public int getExemplarsCount() {
        return this.exemplars_.size();
    }

    @Override
    public Exemplar getExemplars(int index) {
        return this.exemplars_.get(index);
    }

    @Override
    public ExemplarOrBuilder getExemplarsOrBuilder(int index) {
        return this.exemplars_.get(index);
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
        int i;
        this.getSerializedSize();
        if (this.count_ != 0L) {
            output.writeInt64(1, this.count_);
        }
        if (Double.doubleToRawLongBits(this.mean_) != 0L) {
            output.writeDouble(2, this.mean_);
        }
        if (Double.doubleToRawLongBits(this.sumOfSquaredDeviation_) != 0L) {
            output.writeDouble(3, this.sumOfSquaredDeviation_);
        }
        if (this.range_ != null) {
            output.writeMessage(4, this.getRange());
        }
        if (this.bucketOptions_ != null) {
            output.writeMessage(6, this.getBucketOptions());
        }
        if (this.getBucketCountsList().size() > 0) {
            output.writeUInt32NoTag(58);
            output.writeUInt32NoTag(this.bucketCountsMemoizedSerializedSize);
        }
        for (i = 0; i < this.bucketCounts_.size(); ++i) {
            output.writeInt64NoTag(this.bucketCounts_.getLong(i));
        }
        for (i = 0; i < this.exemplars_.size(); ++i) {
            output.writeMessage(10, this.exemplars_.get(i));
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
        if (this.count_ != 0L) {
            size += CodedOutputStream.computeInt64Size(1, this.count_);
        }
        if (Double.doubleToRawLongBits(this.mean_) != 0L) {
            size += CodedOutputStream.computeDoubleSize(2, this.mean_);
        }
        if (Double.doubleToRawLongBits(this.sumOfSquaredDeviation_) != 0L) {
            size += CodedOutputStream.computeDoubleSize(3, this.sumOfSquaredDeviation_);
        }
        if (this.range_ != null) {
            size += CodedOutputStream.computeMessageSize(4, this.getRange());
        }
        if (this.bucketOptions_ != null) {
            size += CodedOutputStream.computeMessageSize(6, this.getBucketOptions());
        }
        int dataSize = 0;
        for (int i = 0; i < this.bucketCounts_.size(); ++i) {
            dataSize += CodedOutputStream.computeInt64SizeNoTag(this.bucketCounts_.getLong(i));
        }
        size += dataSize;
        if (!this.getBucketCountsList().isEmpty()) {
            ++size;
            size += CodedOutputStream.computeInt32SizeNoTag(dataSize);
        }
        this.bucketCountsMemoizedSerializedSize = dataSize;
        for (int i = 0; i < this.exemplars_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(10, this.exemplars_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Distribution)) {
            return super.equals(obj);
        }
        Distribution other = (Distribution)obj;
        if (this.getCount() != other.getCount()) {
            return false;
        }
        if (Double.doubleToLongBits(this.getMean()) != Double.doubleToLongBits(other.getMean())) {
            return false;
        }
        if (Double.doubleToLongBits(this.getSumOfSquaredDeviation()) != Double.doubleToLongBits(other.getSumOfSquaredDeviation())) {
            return false;
        }
        if (this.hasRange() != other.hasRange()) {
            return false;
        }
        if (this.hasRange() && !this.getRange().equals(other.getRange())) {
            return false;
        }
        if (this.hasBucketOptions() != other.hasBucketOptions()) {
            return false;
        }
        if (this.hasBucketOptions() && !this.getBucketOptions().equals(other.getBucketOptions())) {
            return false;
        }
        if (!this.getBucketCountsList().equals(other.getBucketCountsList())) {
            return false;
        }
        if (!this.getExemplarsList().equals(other.getExemplarsList())) {
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
        hash = 19 * hash + Distribution.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + Internal.hashLong(this.getCount());
        hash = 37 * hash + 2;
        hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getMean()));
        hash = 37 * hash + 3;
        hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getSumOfSquaredDeviation()));
        if (this.hasRange()) {
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getRange().hashCode();
        }
        if (this.hasBucketOptions()) {
            hash = 37 * hash + 6;
            hash = 53 * hash + this.getBucketOptions().hashCode();
        }
        if (this.getBucketCountsCount() > 0) {
            hash = 37 * hash + 7;
            hash = 53 * hash + this.getBucketCountsList().hashCode();
        }
        if (this.getExemplarsCount() > 0) {
            hash = 37 * hash + 10;
            hash = 53 * hash + this.getExemplarsList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Distribution parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Distribution parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Distribution parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Distribution parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Distribution parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Distribution parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Distribution parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Distribution parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Distribution parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Distribution parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Distribution parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Distribution parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Distribution.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Distribution prototype) {
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

    public static Distribution getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Distribution> parser() {
        return PARSER;
    }

    public Parser<Distribution> getParserForType() {
        return PARSER;
    }

    @Override
    public Distribution getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    static /* synthetic */ Internal.LongList access$4900() {
        return Distribution.emptyLongList();
    }

    public static final class BucketOptions
    extends GeneratedMessageV3
    implements BucketOptionsOrBuilder {
        private static final long serialVersionUID = 0L;
        private int optionsCase_ = 0;
        private Object options_;
        public static final int LINEAR_BUCKETS_FIELD_NUMBER = 1;
        public static final int EXPONENTIAL_BUCKETS_FIELD_NUMBER = 2;
        public static final int EXPLICIT_BUCKETS_FIELD_NUMBER = 3;
        private byte memoizedIsInitialized = (byte)-1;
        private static final BucketOptions DEFAULT_INSTANCE = new BucketOptions();
        private static final Parser<BucketOptions> PARSER = new AbstractParser<BucketOptions>(){

            @Override
            public BucketOptions parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = BucketOptions.newBuilder();
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

        private BucketOptions(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private BucketOptions() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new BucketOptions();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DistributionProto.internal_static_google_api_Distribution_BucketOptions_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return DistributionProto.internal_static_google_api_Distribution_BucketOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(BucketOptions.class, Builder.class);
        }

        @Override
        public OptionsCase getOptionsCase() {
            return OptionsCase.forNumber(this.optionsCase_);
        }

        @Override
        public boolean hasLinearBuckets() {
            return this.optionsCase_ == 1;
        }

        @Override
        public Linear getLinearBuckets() {
            if (this.optionsCase_ == 1) {
                return (Linear)this.options_;
            }
            return Linear.getDefaultInstance();
        }

        @Override
        public LinearOrBuilder getLinearBucketsOrBuilder() {
            if (this.optionsCase_ == 1) {
                return (Linear)this.options_;
            }
            return Linear.getDefaultInstance();
        }

        @Override
        public boolean hasExponentialBuckets() {
            return this.optionsCase_ == 2;
        }

        @Override
        public Exponential getExponentialBuckets() {
            if (this.optionsCase_ == 2) {
                return (Exponential)this.options_;
            }
            return Exponential.getDefaultInstance();
        }

        @Override
        public ExponentialOrBuilder getExponentialBucketsOrBuilder() {
            if (this.optionsCase_ == 2) {
                return (Exponential)this.options_;
            }
            return Exponential.getDefaultInstance();
        }

        @Override
        public boolean hasExplicitBuckets() {
            return this.optionsCase_ == 3;
        }

        @Override
        public Explicit getExplicitBuckets() {
            if (this.optionsCase_ == 3) {
                return (Explicit)this.options_;
            }
            return Explicit.getDefaultInstance();
        }

        @Override
        public ExplicitOrBuilder getExplicitBucketsOrBuilder() {
            if (this.optionsCase_ == 3) {
                return (Explicit)this.options_;
            }
            return Explicit.getDefaultInstance();
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
            if (this.optionsCase_ == 1) {
                output.writeMessage(1, (Linear)this.options_);
            }
            if (this.optionsCase_ == 2) {
                output.writeMessage(2, (Exponential)this.options_);
            }
            if (this.optionsCase_ == 3) {
                output.writeMessage(3, (Explicit)this.options_);
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
            if (this.optionsCase_ == 1) {
                size += CodedOutputStream.computeMessageSize(1, (Linear)this.options_);
            }
            if (this.optionsCase_ == 2) {
                size += CodedOutputStream.computeMessageSize(2, (Exponential)this.options_);
            }
            if (this.optionsCase_ == 3) {
                size += CodedOutputStream.computeMessageSize(3, (Explicit)this.options_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof BucketOptions)) {
                return super.equals(obj);
            }
            BucketOptions other = (BucketOptions)obj;
            if (!this.getOptionsCase().equals(other.getOptionsCase())) {
                return false;
            }
            switch (this.optionsCase_) {
                case 1: {
                    if (this.getLinearBuckets().equals(other.getLinearBuckets())) break;
                    return false;
                }
                case 2: {
                    if (this.getExponentialBuckets().equals(other.getExponentialBuckets())) break;
                    return false;
                }
                case 3: {
                    if (this.getExplicitBuckets().equals(other.getExplicitBuckets())) break;
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
            hash = 19 * hash + BucketOptions.getDescriptor().hashCode();
            switch (this.optionsCase_) {
                case 1: {
                    hash = 37 * hash + 1;
                    hash = 53 * hash + this.getLinearBuckets().hashCode();
                    break;
                }
                case 2: {
                    hash = 37 * hash + 2;
                    hash = 53 * hash + this.getExponentialBuckets().hashCode();
                    break;
                }
                case 3: {
                    hash = 37 * hash + 3;
                    hash = 53 * hash + this.getExplicitBuckets().hashCode();
                    break;
                }
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static BucketOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BucketOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BucketOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BucketOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BucketOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BucketOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BucketOptions parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static BucketOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static BucketOptions parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static BucketOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static BucketOptions parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static BucketOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return BucketOptions.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(BucketOptions prototype) {
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

        public static BucketOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<BucketOptions> parser() {
            return PARSER;
        }

        public Parser<BucketOptions> getParserForType() {
            return PARSER;
        }

        @Override
        public BucketOptions getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements BucketOptionsOrBuilder {
            private int optionsCase_ = 0;
            private Object options_;
            private int bitField0_;
            private SingleFieldBuilderV3<Linear, Linear.Builder, LinearOrBuilder> linearBucketsBuilder_;
            private SingleFieldBuilderV3<Exponential, Exponential.Builder, ExponentialOrBuilder> exponentialBucketsBuilder_;
            private SingleFieldBuilderV3<Explicit, Explicit.Builder, ExplicitOrBuilder> explicitBucketsBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DistributionProto.internal_static_google_api_Distribution_BucketOptions_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return DistributionProto.internal_static_google_api_Distribution_BucketOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(BucketOptions.class, Builder.class);
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
                if (this.linearBucketsBuilder_ != null) {
                    this.linearBucketsBuilder_.clear();
                }
                if (this.exponentialBucketsBuilder_ != null) {
                    this.exponentialBucketsBuilder_.clear();
                }
                if (this.explicitBucketsBuilder_ != null) {
                    this.explicitBucketsBuilder_.clear();
                }
                this.optionsCase_ = 0;
                this.options_ = null;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return DistributionProto.internal_static_google_api_Distribution_BucketOptions_descriptor;
            }

            @Override
            public BucketOptions getDefaultInstanceForType() {
                return BucketOptions.getDefaultInstance();
            }

            @Override
            public BucketOptions build() {
                BucketOptions result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public BucketOptions buildPartial() {
                BucketOptions result = new BucketOptions(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.buildPartialOneofs(result);
                this.onBuilt();
                return result;
            }

            private void buildPartial0(BucketOptions result) {
                int from_bitField0_ = this.bitField0_;
            }

            private void buildPartialOneofs(BucketOptions result) {
                result.optionsCase_ = this.optionsCase_;
                result.options_ = this.options_;
                if (this.optionsCase_ == 1 && this.linearBucketsBuilder_ != null) {
                    result.options_ = this.linearBucketsBuilder_.build();
                }
                if (this.optionsCase_ == 2 && this.exponentialBucketsBuilder_ != null) {
                    result.options_ = this.exponentialBucketsBuilder_.build();
                }
                if (this.optionsCase_ == 3 && this.explicitBucketsBuilder_ != null) {
                    result.options_ = this.explicitBucketsBuilder_.build();
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
                if (other instanceof BucketOptions) {
                    return this.mergeFrom((BucketOptions)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(BucketOptions other) {
                if (other == BucketOptions.getDefaultInstance()) {
                    return this;
                }
                switch (other.getOptionsCase()) {
                    case LINEAR_BUCKETS: {
                        this.mergeLinearBuckets(other.getLinearBuckets());
                        break;
                    }
                    case EXPONENTIAL_BUCKETS: {
                        this.mergeExponentialBuckets(other.getExponentialBuckets());
                        break;
                    }
                    case EXPLICIT_BUCKETS: {
                        this.mergeExplicitBuckets(other.getExplicitBuckets());
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
                    block11: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block11;
                            }
                            case 10: {
                                input.readMessage(this.getLinearBucketsFieldBuilder().getBuilder(), extensionRegistry);
                                this.optionsCase_ = 1;
                                continue block11;
                            }
                            case 18: {
                                input.readMessage(this.getExponentialBucketsFieldBuilder().getBuilder(), extensionRegistry);
                                this.optionsCase_ = 2;
                                continue block11;
                            }
                            case 26: {
                                input.readMessage(this.getExplicitBucketsFieldBuilder().getBuilder(), extensionRegistry);
                                this.optionsCase_ = 3;
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
            public OptionsCase getOptionsCase() {
                return OptionsCase.forNumber(this.optionsCase_);
            }

            public Builder clearOptions() {
                this.optionsCase_ = 0;
                this.options_ = null;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasLinearBuckets() {
                return this.optionsCase_ == 1;
            }

            @Override
            public Linear getLinearBuckets() {
                if (this.linearBucketsBuilder_ == null) {
                    if (this.optionsCase_ == 1) {
                        return (Linear)this.options_;
                    }
                    return Linear.getDefaultInstance();
                }
                if (this.optionsCase_ == 1) {
                    return this.linearBucketsBuilder_.getMessage();
                }
                return Linear.getDefaultInstance();
            }

            public Builder setLinearBuckets(Linear value) {
                if (this.linearBucketsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.options_ = value;
                    this.onChanged();
                } else {
                    this.linearBucketsBuilder_.setMessage(value);
                }
                this.optionsCase_ = 1;
                return this;
            }

            public Builder setLinearBuckets(Linear.Builder builderForValue) {
                if (this.linearBucketsBuilder_ == null) {
                    this.options_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.linearBucketsBuilder_.setMessage(builderForValue.build());
                }
                this.optionsCase_ = 1;
                return this;
            }

            public Builder mergeLinearBuckets(Linear value) {
                if (this.linearBucketsBuilder_ == null) {
                    this.options_ = this.optionsCase_ == 1 && this.options_ != Linear.getDefaultInstance() ? Linear.newBuilder((Linear)this.options_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else if (this.optionsCase_ == 1) {
                    this.linearBucketsBuilder_.mergeFrom(value);
                } else {
                    this.linearBucketsBuilder_.setMessage(value);
                }
                this.optionsCase_ = 1;
                return this;
            }

            public Builder clearLinearBuckets() {
                if (this.linearBucketsBuilder_ == null) {
                    if (this.optionsCase_ == 1) {
                        this.optionsCase_ = 0;
                        this.options_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.optionsCase_ == 1) {
                        this.optionsCase_ = 0;
                        this.options_ = null;
                    }
                    this.linearBucketsBuilder_.clear();
                }
                return this;
            }

            public Linear.Builder getLinearBucketsBuilder() {
                return this.getLinearBucketsFieldBuilder().getBuilder();
            }

            @Override
            public LinearOrBuilder getLinearBucketsOrBuilder() {
                if (this.optionsCase_ == 1 && this.linearBucketsBuilder_ != null) {
                    return this.linearBucketsBuilder_.getMessageOrBuilder();
                }
                if (this.optionsCase_ == 1) {
                    return (Linear)this.options_;
                }
                return Linear.getDefaultInstance();
            }

            private SingleFieldBuilderV3<Linear, Linear.Builder, LinearOrBuilder> getLinearBucketsFieldBuilder() {
                if (this.linearBucketsBuilder_ == null) {
                    if (this.optionsCase_ != 1) {
                        this.options_ = Linear.getDefaultInstance();
                    }
                    this.linearBucketsBuilder_ = new SingleFieldBuilderV3((Linear)this.options_, this.getParentForChildren(), this.isClean());
                    this.options_ = null;
                }
                this.optionsCase_ = 1;
                this.onChanged();
                return this.linearBucketsBuilder_;
            }

            @Override
            public boolean hasExponentialBuckets() {
                return this.optionsCase_ == 2;
            }

            @Override
            public Exponential getExponentialBuckets() {
                if (this.exponentialBucketsBuilder_ == null) {
                    if (this.optionsCase_ == 2) {
                        return (Exponential)this.options_;
                    }
                    return Exponential.getDefaultInstance();
                }
                if (this.optionsCase_ == 2) {
                    return this.exponentialBucketsBuilder_.getMessage();
                }
                return Exponential.getDefaultInstance();
            }

            public Builder setExponentialBuckets(Exponential value) {
                if (this.exponentialBucketsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.options_ = value;
                    this.onChanged();
                } else {
                    this.exponentialBucketsBuilder_.setMessage(value);
                }
                this.optionsCase_ = 2;
                return this;
            }

            public Builder setExponentialBuckets(Exponential.Builder builderForValue) {
                if (this.exponentialBucketsBuilder_ == null) {
                    this.options_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.exponentialBucketsBuilder_.setMessage(builderForValue.build());
                }
                this.optionsCase_ = 2;
                return this;
            }

            public Builder mergeExponentialBuckets(Exponential value) {
                if (this.exponentialBucketsBuilder_ == null) {
                    this.options_ = this.optionsCase_ == 2 && this.options_ != Exponential.getDefaultInstance() ? Exponential.newBuilder((Exponential)this.options_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else if (this.optionsCase_ == 2) {
                    this.exponentialBucketsBuilder_.mergeFrom(value);
                } else {
                    this.exponentialBucketsBuilder_.setMessage(value);
                }
                this.optionsCase_ = 2;
                return this;
            }

            public Builder clearExponentialBuckets() {
                if (this.exponentialBucketsBuilder_ == null) {
                    if (this.optionsCase_ == 2) {
                        this.optionsCase_ = 0;
                        this.options_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.optionsCase_ == 2) {
                        this.optionsCase_ = 0;
                        this.options_ = null;
                    }
                    this.exponentialBucketsBuilder_.clear();
                }
                return this;
            }

            public Exponential.Builder getExponentialBucketsBuilder() {
                return this.getExponentialBucketsFieldBuilder().getBuilder();
            }

            @Override
            public ExponentialOrBuilder getExponentialBucketsOrBuilder() {
                if (this.optionsCase_ == 2 && this.exponentialBucketsBuilder_ != null) {
                    return this.exponentialBucketsBuilder_.getMessageOrBuilder();
                }
                if (this.optionsCase_ == 2) {
                    return (Exponential)this.options_;
                }
                return Exponential.getDefaultInstance();
            }

            private SingleFieldBuilderV3<Exponential, Exponential.Builder, ExponentialOrBuilder> getExponentialBucketsFieldBuilder() {
                if (this.exponentialBucketsBuilder_ == null) {
                    if (this.optionsCase_ != 2) {
                        this.options_ = Exponential.getDefaultInstance();
                    }
                    this.exponentialBucketsBuilder_ = new SingleFieldBuilderV3((Exponential)this.options_, this.getParentForChildren(), this.isClean());
                    this.options_ = null;
                }
                this.optionsCase_ = 2;
                this.onChanged();
                return this.exponentialBucketsBuilder_;
            }

            @Override
            public boolean hasExplicitBuckets() {
                return this.optionsCase_ == 3;
            }

            @Override
            public Explicit getExplicitBuckets() {
                if (this.explicitBucketsBuilder_ == null) {
                    if (this.optionsCase_ == 3) {
                        return (Explicit)this.options_;
                    }
                    return Explicit.getDefaultInstance();
                }
                if (this.optionsCase_ == 3) {
                    return this.explicitBucketsBuilder_.getMessage();
                }
                return Explicit.getDefaultInstance();
            }

            public Builder setExplicitBuckets(Explicit value) {
                if (this.explicitBucketsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.options_ = value;
                    this.onChanged();
                } else {
                    this.explicitBucketsBuilder_.setMessage(value);
                }
                this.optionsCase_ = 3;
                return this;
            }

            public Builder setExplicitBuckets(Explicit.Builder builderForValue) {
                if (this.explicitBucketsBuilder_ == null) {
                    this.options_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.explicitBucketsBuilder_.setMessage(builderForValue.build());
                }
                this.optionsCase_ = 3;
                return this;
            }

            public Builder mergeExplicitBuckets(Explicit value) {
                if (this.explicitBucketsBuilder_ == null) {
                    this.options_ = this.optionsCase_ == 3 && this.options_ != Explicit.getDefaultInstance() ? Explicit.newBuilder((Explicit)this.options_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else if (this.optionsCase_ == 3) {
                    this.explicitBucketsBuilder_.mergeFrom(value);
                } else {
                    this.explicitBucketsBuilder_.setMessage(value);
                }
                this.optionsCase_ = 3;
                return this;
            }

            public Builder clearExplicitBuckets() {
                if (this.explicitBucketsBuilder_ == null) {
                    if (this.optionsCase_ == 3) {
                        this.optionsCase_ = 0;
                        this.options_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.optionsCase_ == 3) {
                        this.optionsCase_ = 0;
                        this.options_ = null;
                    }
                    this.explicitBucketsBuilder_.clear();
                }
                return this;
            }

            public Explicit.Builder getExplicitBucketsBuilder() {
                return this.getExplicitBucketsFieldBuilder().getBuilder();
            }

            @Override
            public ExplicitOrBuilder getExplicitBucketsOrBuilder() {
                if (this.optionsCase_ == 3 && this.explicitBucketsBuilder_ != null) {
                    return this.explicitBucketsBuilder_.getMessageOrBuilder();
                }
                if (this.optionsCase_ == 3) {
                    return (Explicit)this.options_;
                }
                return Explicit.getDefaultInstance();
            }

            private SingleFieldBuilderV3<Explicit, Explicit.Builder, ExplicitOrBuilder> getExplicitBucketsFieldBuilder() {
                if (this.explicitBucketsBuilder_ == null) {
                    if (this.optionsCase_ != 3) {
                        this.options_ = Explicit.getDefaultInstance();
                    }
                    this.explicitBucketsBuilder_ = new SingleFieldBuilderV3((Explicit)this.options_, this.getParentForChildren(), this.isClean());
                    this.options_ = null;
                }
                this.optionsCase_ = 3;
                this.onChanged();
                return this.explicitBucketsBuilder_;
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

        public static enum OptionsCase implements Internal.EnumLite,
        AbstractMessageLite.InternalOneOfEnum
        {
            LINEAR_BUCKETS(1),
            EXPONENTIAL_BUCKETS(2),
            EXPLICIT_BUCKETS(3),
            OPTIONS_NOT_SET(0);

            private final int value;

            private OptionsCase(int value) {
                this.value = value;
            }

            @Deprecated
            public static OptionsCase valueOf(int value) {
                return OptionsCase.forNumber(value);
            }

            public static OptionsCase forNumber(int value) {
                switch (value) {
                    case 1: {
                        return LINEAR_BUCKETS;
                    }
                    case 2: {
                        return EXPONENTIAL_BUCKETS;
                    }
                    case 3: {
                        return EXPLICIT_BUCKETS;
                    }
                    case 0: {
                        return OPTIONS_NOT_SET;
                    }
                }
                return null;
            }

            @Override
            public int getNumber() {
                return this.value;
            }
        }

        public static final class Linear
        extends GeneratedMessageV3
        implements LinearOrBuilder {
            private static final long serialVersionUID = 0L;
            public static final int NUM_FINITE_BUCKETS_FIELD_NUMBER = 1;
            private int numFiniteBuckets_ = 0;
            public static final int WIDTH_FIELD_NUMBER = 2;
            private double width_ = 0.0;
            public static final int OFFSET_FIELD_NUMBER = 3;
            private double offset_ = 0.0;
            private byte memoizedIsInitialized = (byte)-1;
            private static final Linear DEFAULT_INSTANCE = new Linear();
            private static final Parser<Linear> PARSER = new AbstractParser<Linear>(){

                @Override
                public Linear parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    Builder builder = Linear.newBuilder();
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

            private Linear(GeneratedMessageV3.Builder<?> builder) {
                super(builder);
            }

            private Linear() {
            }

            @Override
            protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
                return new Linear();
            }

            public static final Descriptors.Descriptor getDescriptor() {
                return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Linear_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Linear_fieldAccessorTable.ensureFieldAccessorsInitialized(Linear.class, Builder.class);
            }

            @Override
            public int getNumFiniteBuckets() {
                return this.numFiniteBuckets_;
            }

            @Override
            public double getWidth() {
                return this.width_;
            }

            @Override
            public double getOffset() {
                return this.offset_;
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
                if (this.numFiniteBuckets_ != 0) {
                    output.writeInt32(1, this.numFiniteBuckets_);
                }
                if (Double.doubleToRawLongBits(this.width_) != 0L) {
                    output.writeDouble(2, this.width_);
                }
                if (Double.doubleToRawLongBits(this.offset_) != 0L) {
                    output.writeDouble(3, this.offset_);
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
                if (this.numFiniteBuckets_ != 0) {
                    size += CodedOutputStream.computeInt32Size(1, this.numFiniteBuckets_);
                }
                if (Double.doubleToRawLongBits(this.width_) != 0L) {
                    size += CodedOutputStream.computeDoubleSize(2, this.width_);
                }
                if (Double.doubleToRawLongBits(this.offset_) != 0L) {
                    size += CodedOutputStream.computeDoubleSize(3, this.offset_);
                }
                this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
                return size;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Linear)) {
                    return super.equals(obj);
                }
                Linear other = (Linear)obj;
                if (this.getNumFiniteBuckets() != other.getNumFiniteBuckets()) {
                    return false;
                }
                if (Double.doubleToLongBits(this.getWidth()) != Double.doubleToLongBits(other.getWidth())) {
                    return false;
                }
                if (Double.doubleToLongBits(this.getOffset()) != Double.doubleToLongBits(other.getOffset())) {
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
                hash = 19 * hash + Linear.getDescriptor().hashCode();
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getNumFiniteBuckets();
                hash = 37 * hash + 2;
                hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getWidth()));
                hash = 37 * hash + 3;
                hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getOffset()));
                this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
                return hash;
            }

            public static Linear parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Linear parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Linear parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Linear parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Linear parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Linear parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Linear parseFrom(InputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static Linear parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public static Linear parseDelimitedFrom(InputStream input) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
            }

            public static Linear parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
            }

            public static Linear parseFrom(CodedInputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static Linear parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            @Override
            public Builder newBuilderForType() {
                return Linear.newBuilder();
            }

            public static Builder newBuilder() {
                return DEFAULT_INSTANCE.toBuilder();
            }

            public static Builder newBuilder(Linear prototype) {
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

            public static Linear getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Linear> parser() {
                return PARSER;
            }

            public Parser<Linear> getParserForType() {
                return PARSER;
            }

            @Override
            public Linear getDefaultInstanceForType() {
                return DEFAULT_INSTANCE;
            }

            public static final class Builder
            extends GeneratedMessageV3.Builder<Builder>
            implements LinearOrBuilder {
                private int bitField0_;
                private int numFiniteBuckets_;
                private double width_;
                private double offset_;

                public static final Descriptors.Descriptor getDescriptor() {
                    return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Linear_descriptor;
                }

                @Override
                protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                    return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Linear_fieldAccessorTable.ensureFieldAccessorsInitialized(Linear.class, Builder.class);
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
                    this.numFiniteBuckets_ = 0;
                    this.width_ = 0.0;
                    this.offset_ = 0.0;
                    return this;
                }

                @Override
                public Descriptors.Descriptor getDescriptorForType() {
                    return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Linear_descriptor;
                }

                @Override
                public Linear getDefaultInstanceForType() {
                    return Linear.getDefaultInstance();
                }

                @Override
                public Linear build() {
                    Linear result = this.buildPartial();
                    if (!result.isInitialized()) {
                        throw Builder.newUninitializedMessageException(result);
                    }
                    return result;
                }

                @Override
                public Linear buildPartial() {
                    Linear result = new Linear(this);
                    if (this.bitField0_ != 0) {
                        this.buildPartial0(result);
                    }
                    this.onBuilt();
                    return result;
                }

                private void buildPartial0(Linear result) {
                    int from_bitField0_ = this.bitField0_;
                    if ((from_bitField0_ & 1) != 0) {
                        result.numFiniteBuckets_ = this.numFiniteBuckets_;
                    }
                    if ((from_bitField0_ & 2) != 0) {
                        result.width_ = this.width_;
                    }
                    if ((from_bitField0_ & 4) != 0) {
                        result.offset_ = this.offset_;
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
                    if (other instanceof Linear) {
                        return this.mergeFrom((Linear)other);
                    }
                    super.mergeFrom(other);
                    return this;
                }

                public Builder mergeFrom(Linear other) {
                    if (other == Linear.getDefaultInstance()) {
                        return this;
                    }
                    if (other.getNumFiniteBuckets() != 0) {
                        this.setNumFiniteBuckets(other.getNumFiniteBuckets());
                    }
                    if (other.getWidth() != 0.0) {
                        this.setWidth(other.getWidth());
                    }
                    if (other.getOffset() != 0.0) {
                        this.setOffset(other.getOffset());
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
                                    this.numFiniteBuckets_ = input.readInt32();
                                    this.bitField0_ |= 1;
                                    continue block11;
                                }
                                case 17: {
                                    this.width_ = input.readDouble();
                                    this.bitField0_ |= 2;
                                    continue block11;
                                }
                                case 25: {
                                    this.offset_ = input.readDouble();
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
                public int getNumFiniteBuckets() {
                    return this.numFiniteBuckets_;
                }

                public Builder setNumFiniteBuckets(int value) {
                    this.numFiniteBuckets_ = value;
                    this.bitField0_ |= 1;
                    this.onChanged();
                    return this;
                }

                public Builder clearNumFiniteBuckets() {
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.numFiniteBuckets_ = 0;
                    this.onChanged();
                    return this;
                }

                @Override
                public double getWidth() {
                    return this.width_;
                }

                public Builder setWidth(double value) {
                    this.width_ = value;
                    this.bitField0_ |= 2;
                    this.onChanged();
                    return this;
                }

                public Builder clearWidth() {
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.width_ = 0.0;
                    this.onChanged();
                    return this;
                }

                @Override
                public double getOffset() {
                    return this.offset_;
                }

                public Builder setOffset(double value) {
                    this.offset_ = value;
                    this.bitField0_ |= 4;
                    this.onChanged();
                    return this;
                }

                public Builder clearOffset() {
                    this.bitField0_ &= 0xFFFFFFFB;
                    this.offset_ = 0.0;
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

        public static final class Exponential
        extends GeneratedMessageV3
        implements ExponentialOrBuilder {
            private static final long serialVersionUID = 0L;
            public static final int NUM_FINITE_BUCKETS_FIELD_NUMBER = 1;
            private int numFiniteBuckets_ = 0;
            public static final int GROWTH_FACTOR_FIELD_NUMBER = 2;
            private double growthFactor_ = 0.0;
            public static final int SCALE_FIELD_NUMBER = 3;
            private double scale_ = 0.0;
            private byte memoizedIsInitialized = (byte)-1;
            private static final Exponential DEFAULT_INSTANCE = new Exponential();
            private static final Parser<Exponential> PARSER = new AbstractParser<Exponential>(){

                @Override
                public Exponential parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    Builder builder = Exponential.newBuilder();
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

            private Exponential(GeneratedMessageV3.Builder<?> builder) {
                super(builder);
            }

            private Exponential() {
            }

            @Override
            protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
                return new Exponential();
            }

            public static final Descriptors.Descriptor getDescriptor() {
                return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Exponential_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Exponential_fieldAccessorTable.ensureFieldAccessorsInitialized(Exponential.class, Builder.class);
            }

            @Override
            public int getNumFiniteBuckets() {
                return this.numFiniteBuckets_;
            }

            @Override
            public double getGrowthFactor() {
                return this.growthFactor_;
            }

            @Override
            public double getScale() {
                return this.scale_;
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
                if (this.numFiniteBuckets_ != 0) {
                    output.writeInt32(1, this.numFiniteBuckets_);
                }
                if (Double.doubleToRawLongBits(this.growthFactor_) != 0L) {
                    output.writeDouble(2, this.growthFactor_);
                }
                if (Double.doubleToRawLongBits(this.scale_) != 0L) {
                    output.writeDouble(3, this.scale_);
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
                if (this.numFiniteBuckets_ != 0) {
                    size += CodedOutputStream.computeInt32Size(1, this.numFiniteBuckets_);
                }
                if (Double.doubleToRawLongBits(this.growthFactor_) != 0L) {
                    size += CodedOutputStream.computeDoubleSize(2, this.growthFactor_);
                }
                if (Double.doubleToRawLongBits(this.scale_) != 0L) {
                    size += CodedOutputStream.computeDoubleSize(3, this.scale_);
                }
                this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
                return size;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Exponential)) {
                    return super.equals(obj);
                }
                Exponential other = (Exponential)obj;
                if (this.getNumFiniteBuckets() != other.getNumFiniteBuckets()) {
                    return false;
                }
                if (Double.doubleToLongBits(this.getGrowthFactor()) != Double.doubleToLongBits(other.getGrowthFactor())) {
                    return false;
                }
                if (Double.doubleToLongBits(this.getScale()) != Double.doubleToLongBits(other.getScale())) {
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
                hash = 19 * hash + Exponential.getDescriptor().hashCode();
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getNumFiniteBuckets();
                hash = 37 * hash + 2;
                hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getGrowthFactor()));
                hash = 37 * hash + 3;
                hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getScale()));
                this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
                return hash;
            }

            public static Exponential parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Exponential parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Exponential parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Exponential parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Exponential parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Exponential parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Exponential parseFrom(InputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static Exponential parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public static Exponential parseDelimitedFrom(InputStream input) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
            }

            public static Exponential parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
            }

            public static Exponential parseFrom(CodedInputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static Exponential parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            @Override
            public Builder newBuilderForType() {
                return Exponential.newBuilder();
            }

            public static Builder newBuilder() {
                return DEFAULT_INSTANCE.toBuilder();
            }

            public static Builder newBuilder(Exponential prototype) {
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

            public static Exponential getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Exponential> parser() {
                return PARSER;
            }

            public Parser<Exponential> getParserForType() {
                return PARSER;
            }

            @Override
            public Exponential getDefaultInstanceForType() {
                return DEFAULT_INSTANCE;
            }

            public static final class Builder
            extends GeneratedMessageV3.Builder<Builder>
            implements ExponentialOrBuilder {
                private int bitField0_;
                private int numFiniteBuckets_;
                private double growthFactor_;
                private double scale_;

                public static final Descriptors.Descriptor getDescriptor() {
                    return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Exponential_descriptor;
                }

                @Override
                protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                    return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Exponential_fieldAccessorTable.ensureFieldAccessorsInitialized(Exponential.class, Builder.class);
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
                    this.numFiniteBuckets_ = 0;
                    this.growthFactor_ = 0.0;
                    this.scale_ = 0.0;
                    return this;
                }

                @Override
                public Descriptors.Descriptor getDescriptorForType() {
                    return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Exponential_descriptor;
                }

                @Override
                public Exponential getDefaultInstanceForType() {
                    return Exponential.getDefaultInstance();
                }

                @Override
                public Exponential build() {
                    Exponential result = this.buildPartial();
                    if (!result.isInitialized()) {
                        throw Builder.newUninitializedMessageException(result);
                    }
                    return result;
                }

                @Override
                public Exponential buildPartial() {
                    Exponential result = new Exponential(this);
                    if (this.bitField0_ != 0) {
                        this.buildPartial0(result);
                    }
                    this.onBuilt();
                    return result;
                }

                private void buildPartial0(Exponential result) {
                    int from_bitField0_ = this.bitField0_;
                    if ((from_bitField0_ & 1) != 0) {
                        result.numFiniteBuckets_ = this.numFiniteBuckets_;
                    }
                    if ((from_bitField0_ & 2) != 0) {
                        result.growthFactor_ = this.growthFactor_;
                    }
                    if ((from_bitField0_ & 4) != 0) {
                        result.scale_ = this.scale_;
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
                    if (other instanceof Exponential) {
                        return this.mergeFrom((Exponential)other);
                    }
                    super.mergeFrom(other);
                    return this;
                }

                public Builder mergeFrom(Exponential other) {
                    if (other == Exponential.getDefaultInstance()) {
                        return this;
                    }
                    if (other.getNumFiniteBuckets() != 0) {
                        this.setNumFiniteBuckets(other.getNumFiniteBuckets());
                    }
                    if (other.getGrowthFactor() != 0.0) {
                        this.setGrowthFactor(other.getGrowthFactor());
                    }
                    if (other.getScale() != 0.0) {
                        this.setScale(other.getScale());
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
                                    this.numFiniteBuckets_ = input.readInt32();
                                    this.bitField0_ |= 1;
                                    continue block11;
                                }
                                case 17: {
                                    this.growthFactor_ = input.readDouble();
                                    this.bitField0_ |= 2;
                                    continue block11;
                                }
                                case 25: {
                                    this.scale_ = input.readDouble();
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
                public int getNumFiniteBuckets() {
                    return this.numFiniteBuckets_;
                }

                public Builder setNumFiniteBuckets(int value) {
                    this.numFiniteBuckets_ = value;
                    this.bitField0_ |= 1;
                    this.onChanged();
                    return this;
                }

                public Builder clearNumFiniteBuckets() {
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.numFiniteBuckets_ = 0;
                    this.onChanged();
                    return this;
                }

                @Override
                public double getGrowthFactor() {
                    return this.growthFactor_;
                }

                public Builder setGrowthFactor(double value) {
                    this.growthFactor_ = value;
                    this.bitField0_ |= 2;
                    this.onChanged();
                    return this;
                }

                public Builder clearGrowthFactor() {
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.growthFactor_ = 0.0;
                    this.onChanged();
                    return this;
                }

                @Override
                public double getScale() {
                    return this.scale_;
                }

                public Builder setScale(double value) {
                    this.scale_ = value;
                    this.bitField0_ |= 4;
                    this.onChanged();
                    return this;
                }

                public Builder clearScale() {
                    this.bitField0_ &= 0xFFFFFFFB;
                    this.scale_ = 0.0;
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

        public static final class Explicit
        extends GeneratedMessageV3
        implements ExplicitOrBuilder {
            private static final long serialVersionUID = 0L;
            public static final int BOUNDS_FIELD_NUMBER = 1;
            private Internal.DoubleList bounds_;
            private int boundsMemoizedSerializedSize = -1;
            private byte memoizedIsInitialized = (byte)-1;
            private static final Explicit DEFAULT_INSTANCE = new Explicit();
            private static final Parser<Explicit> PARSER = new AbstractParser<Explicit>(){

                @Override
                public Explicit parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    Builder builder = Explicit.newBuilder();
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

            private Explicit(GeneratedMessageV3.Builder<?> builder) {
                super(builder);
            }

            private Explicit() {
                this.bounds_ = Explicit.emptyDoubleList();
            }

            @Override
            protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
                return new Explicit();
            }

            public static final Descriptors.Descriptor getDescriptor() {
                return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Explicit_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Explicit_fieldAccessorTable.ensureFieldAccessorsInitialized(Explicit.class, Builder.class);
            }

            @Override
            public List<Double> getBoundsList() {
                return this.bounds_;
            }

            @Override
            public int getBoundsCount() {
                return this.bounds_.size();
            }

            @Override
            public double getBounds(int index) {
                return this.bounds_.getDouble(index);
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
                this.getSerializedSize();
                if (this.getBoundsList().size() > 0) {
                    output.writeUInt32NoTag(10);
                    output.writeUInt32NoTag(this.boundsMemoizedSerializedSize);
                }
                for (int i = 0; i < this.bounds_.size(); ++i) {
                    output.writeDoubleNoTag(this.bounds_.getDouble(i));
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
                int dataSize = 0;
                dataSize = 8 * this.getBoundsList().size();
                size += dataSize;
                if (!this.getBoundsList().isEmpty()) {
                    ++size;
                    size += CodedOutputStream.computeInt32SizeNoTag(dataSize);
                }
                this.boundsMemoizedSerializedSize = dataSize;
                this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
                return size;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Explicit)) {
                    return super.equals(obj);
                }
                Explicit other = (Explicit)obj;
                if (!this.getBoundsList().equals(other.getBoundsList())) {
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
                hash = 19 * hash + Explicit.getDescriptor().hashCode();
                if (this.getBoundsCount() > 0) {
                    hash = 37 * hash + 1;
                    hash = 53 * hash + this.getBoundsList().hashCode();
                }
                this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
                return hash;
            }

            public static Explicit parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Explicit parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Explicit parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Explicit parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Explicit parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Explicit parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Explicit parseFrom(InputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static Explicit parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public static Explicit parseDelimitedFrom(InputStream input) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
            }

            public static Explicit parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
            }

            public static Explicit parseFrom(CodedInputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static Explicit parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            @Override
            public Builder newBuilderForType() {
                return Explicit.newBuilder();
            }

            public static Builder newBuilder() {
                return DEFAULT_INSTANCE.toBuilder();
            }

            public static Builder newBuilder(Explicit prototype) {
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

            public static Explicit getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Explicit> parser() {
                return PARSER;
            }

            public Parser<Explicit> getParserForType() {
                return PARSER;
            }

            @Override
            public Explicit getDefaultInstanceForType() {
                return DEFAULT_INSTANCE;
            }

            static /* synthetic */ Internal.DoubleList access$2200() {
                return Explicit.emptyDoubleList();
            }

            public static final class Builder
            extends GeneratedMessageV3.Builder<Builder>
            implements ExplicitOrBuilder {
                private int bitField0_;
                private Internal.DoubleList bounds_ = Explicit.access$2200();

                public static final Descriptors.Descriptor getDescriptor() {
                    return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Explicit_descriptor;
                }

                @Override
                protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                    return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Explicit_fieldAccessorTable.ensureFieldAccessorsInitialized(Explicit.class, Builder.class);
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
                    this.bounds_ = Explicit.emptyDoubleList();
                    return this;
                }

                @Override
                public Descriptors.Descriptor getDescriptorForType() {
                    return DistributionProto.internal_static_google_api_Distribution_BucketOptions_Explicit_descriptor;
                }

                @Override
                public Explicit getDefaultInstanceForType() {
                    return Explicit.getDefaultInstance();
                }

                @Override
                public Explicit build() {
                    Explicit result = this.buildPartial();
                    if (!result.isInitialized()) {
                        throw Builder.newUninitializedMessageException(result);
                    }
                    return result;
                }

                @Override
                public Explicit buildPartial() {
                    Explicit result = new Explicit(this);
                    this.buildPartialRepeatedFields(result);
                    if (this.bitField0_ != 0) {
                        this.buildPartial0(result);
                    }
                    this.onBuilt();
                    return result;
                }

                private void buildPartialRepeatedFields(Explicit result) {
                    if ((this.bitField0_ & 1) != 0) {
                        this.bounds_.makeImmutable();
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.bounds_ = this.bounds_;
                }

                private void buildPartial0(Explicit result) {
                    int from_bitField0_ = this.bitField0_;
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
                    if (other instanceof Explicit) {
                        return this.mergeFrom((Explicit)other);
                    }
                    super.mergeFrom(other);
                    return this;
                }

                public Builder mergeFrom(Explicit other) {
                    if (other == Explicit.getDefaultInstance()) {
                        return this;
                    }
                    if (!other.bounds_.isEmpty()) {
                        if (this.bounds_.isEmpty()) {
                            this.bounds_ = other.bounds_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureBoundsIsMutable();
                            this.bounds_.addAll(other.bounds_);
                        }
                        this.onChanged();
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
                                    double v = input.readDouble();
                                    this.ensureBoundsIsMutable();
                                    this.bounds_.addDouble(v);
                                    continue block10;
                                }
                                case 10: {
                                    int length = input.readRawVarint32();
                                    int limit = input.pushLimit(length);
                                    this.ensureBoundsIsMutable();
                                    while (input.getBytesUntilLimit() > 0) {
                                        this.bounds_.addDouble(input.readDouble());
                                    }
                                    input.popLimit(limit);
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

                private void ensureBoundsIsMutable() {
                    if ((this.bitField0_ & 1) == 0) {
                        this.bounds_ = Explicit.mutableCopy(this.bounds_);
                        this.bitField0_ |= 1;
                    }
                }

                @Override
                public List<Double> getBoundsList() {
                    return (this.bitField0_ & 1) != 0 ? Collections.unmodifiableList(this.bounds_) : this.bounds_;
                }

                @Override
                public int getBoundsCount() {
                    return this.bounds_.size();
                }

                @Override
                public double getBounds(int index) {
                    return this.bounds_.getDouble(index);
                }

                public Builder setBounds(int index, double value) {
                    this.ensureBoundsIsMutable();
                    this.bounds_.setDouble(index, value);
                    this.onChanged();
                    return this;
                }

                public Builder addBounds(double value) {
                    this.ensureBoundsIsMutable();
                    this.bounds_.addDouble(value);
                    this.onChanged();
                    return this;
                }

                public Builder addAllBounds(Iterable<? extends Double> values2) {
                    this.ensureBoundsIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.bounds_);
                    this.onChanged();
                    return this;
                }

                public Builder clearBounds() {
                    this.bounds_ = Explicit.emptyDoubleList();
                    this.bitField0_ &= 0xFFFFFFFE;
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

        public static interface ExplicitOrBuilder
        extends MessageOrBuilder {
            public List<Double> getBoundsList();

            public int getBoundsCount();

            public double getBounds(int var1);
        }

        public static interface ExponentialOrBuilder
        extends MessageOrBuilder {
            public int getNumFiniteBuckets();

            public double getGrowthFactor();

            public double getScale();
        }

        public static interface LinearOrBuilder
        extends MessageOrBuilder {
            public int getNumFiniteBuckets();

            public double getWidth();

            public double getOffset();
        }
    }

    public static final class Range
    extends GeneratedMessageV3
    implements RangeOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MIN_FIELD_NUMBER = 1;
        private double min_ = 0.0;
        public static final int MAX_FIELD_NUMBER = 2;
        private double max_ = 0.0;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Range DEFAULT_INSTANCE = new Range();
        private static final Parser<Range> PARSER = new AbstractParser<Range>(){

            @Override
            public Range parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = Range.newBuilder();
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

        private Range(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Range() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new Range();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DistributionProto.internal_static_google_api_Distribution_Range_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return DistributionProto.internal_static_google_api_Distribution_Range_fieldAccessorTable.ensureFieldAccessorsInitialized(Range.class, Builder.class);
        }

        @Override
        public double getMin() {
            return this.min_;
        }

        @Override
        public double getMax() {
            return this.max_;
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
            if (Double.doubleToRawLongBits(this.min_) != 0L) {
                output.writeDouble(1, this.min_);
            }
            if (Double.doubleToRawLongBits(this.max_) != 0L) {
                output.writeDouble(2, this.max_);
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
            if (Double.doubleToRawLongBits(this.min_) != 0L) {
                size += CodedOutputStream.computeDoubleSize(1, this.min_);
            }
            if (Double.doubleToRawLongBits(this.max_) != 0L) {
                size += CodedOutputStream.computeDoubleSize(2, this.max_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Range)) {
                return super.equals(obj);
            }
            Range other = (Range)obj;
            if (Double.doubleToLongBits(this.getMin()) != Double.doubleToLongBits(other.getMin())) {
                return false;
            }
            if (Double.doubleToLongBits(this.getMax()) != Double.doubleToLongBits(other.getMax())) {
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
            hash = 19 * hash + Range.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getMin()));
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getMax()));
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static Range parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Range parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Range parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Range parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Range parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Range parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Range parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Range parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Range parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Range parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Range parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Range parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Range.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Range prototype) {
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

        public static Range getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Range> parser() {
            return PARSER;
        }

        public Parser<Range> getParserForType() {
            return PARSER;
        }

        @Override
        public Range getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements RangeOrBuilder {
            private int bitField0_;
            private double min_;
            private double max_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DistributionProto.internal_static_google_api_Distribution_Range_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return DistributionProto.internal_static_google_api_Distribution_Range_fieldAccessorTable.ensureFieldAccessorsInitialized(Range.class, Builder.class);
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
                this.min_ = 0.0;
                this.max_ = 0.0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return DistributionProto.internal_static_google_api_Distribution_Range_descriptor;
            }

            @Override
            public Range getDefaultInstanceForType() {
                return Range.getDefaultInstance();
            }

            @Override
            public Range build() {
                Range result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Range buildPartial() {
                Range result = new Range(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(Range result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.min_ = this.min_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.max_ = this.max_;
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
                if (other instanceof Range) {
                    return this.mergeFrom((Range)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Range other) {
                if (other == Range.getDefaultInstance()) {
                    return this;
                }
                if (other.getMin() != 0.0) {
                    this.setMin(other.getMin());
                }
                if (other.getMax() != 0.0) {
                    this.setMax(other.getMax());
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
                                this.min_ = input.readDouble();
                                this.bitField0_ |= 1;
                                continue block10;
                            }
                            case 17: {
                                this.max_ = input.readDouble();
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
            public double getMin() {
                return this.min_;
            }

            public Builder setMin(double value) {
                this.min_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearMin() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.min_ = 0.0;
                this.onChanged();
                return this;
            }

            @Override
            public double getMax() {
                return this.max_;
            }

            public Builder setMax(double value) {
                this.max_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearMax() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.max_ = 0.0;
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

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements DistributionOrBuilder {
        private int bitField0_;
        private long count_;
        private double mean_;
        private double sumOfSquaredDeviation_;
        private Range range_;
        private SingleFieldBuilderV3<Range, Range.Builder, RangeOrBuilder> rangeBuilder_;
        private BucketOptions bucketOptions_;
        private SingleFieldBuilderV3<BucketOptions, BucketOptions.Builder, BucketOptionsOrBuilder> bucketOptionsBuilder_;
        private Internal.LongList bucketCounts_ = Distribution.access$4900();
        private List<Exemplar> exemplars_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<Exemplar, Exemplar.Builder, ExemplarOrBuilder> exemplarsBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return DistributionProto.internal_static_google_api_Distribution_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return DistributionProto.internal_static_google_api_Distribution_fieldAccessorTable.ensureFieldAccessorsInitialized(Distribution.class, Builder.class);
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
            this.count_ = 0L;
            this.mean_ = 0.0;
            this.sumOfSquaredDeviation_ = 0.0;
            this.range_ = null;
            if (this.rangeBuilder_ != null) {
                this.rangeBuilder_.dispose();
                this.rangeBuilder_ = null;
            }
            this.bucketOptions_ = null;
            if (this.bucketOptionsBuilder_ != null) {
                this.bucketOptionsBuilder_.dispose();
                this.bucketOptionsBuilder_ = null;
            }
            this.bucketCounts_ = Distribution.emptyLongList();
            if (this.exemplarsBuilder_ == null) {
                this.exemplars_ = Collections.emptyList();
            } else {
                this.exemplars_ = null;
                this.exemplarsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFBF;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return DistributionProto.internal_static_google_api_Distribution_descriptor;
        }

        @Override
        public Distribution getDefaultInstanceForType() {
            return Distribution.getDefaultInstance();
        }

        @Override
        public Distribution build() {
            Distribution result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Distribution buildPartial() {
            Distribution result = new Distribution(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(Distribution result) {
            if ((this.bitField0_ & 0x20) != 0) {
                this.bucketCounts_.makeImmutable();
                this.bitField0_ &= 0xFFFFFFDF;
            }
            result.bucketCounts_ = this.bucketCounts_;
            if (this.exemplarsBuilder_ == null) {
                if ((this.bitField0_ & 0x40) != 0) {
                    this.exemplars_ = Collections.unmodifiableList(this.exemplars_);
                    this.bitField0_ &= 0xFFFFFFBF;
                }
                result.exemplars_ = this.exemplars_;
            } else {
                result.exemplars_ = this.exemplarsBuilder_.build();
            }
        }

        private void buildPartial0(Distribution result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.count_ = this.count_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.mean_ = this.mean_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.sumOfSquaredDeviation_ = this.sumOfSquaredDeviation_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.range_ = this.rangeBuilder_ == null ? this.range_ : this.rangeBuilder_.build();
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.bucketOptions_ = this.bucketOptionsBuilder_ == null ? this.bucketOptions_ : this.bucketOptionsBuilder_.build();
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
            if (other instanceof Distribution) {
                return this.mergeFrom((Distribution)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Distribution other) {
            if (other == Distribution.getDefaultInstance()) {
                return this;
            }
            if (other.getCount() != 0L) {
                this.setCount(other.getCount());
            }
            if (other.getMean() != 0.0) {
                this.setMean(other.getMean());
            }
            if (other.getSumOfSquaredDeviation() != 0.0) {
                this.setSumOfSquaredDeviation(other.getSumOfSquaredDeviation());
            }
            if (other.hasRange()) {
                this.mergeRange(other.getRange());
            }
            if (other.hasBucketOptions()) {
                this.mergeBucketOptions(other.getBucketOptions());
            }
            if (!other.bucketCounts_.isEmpty()) {
                if (this.bucketCounts_.isEmpty()) {
                    this.bucketCounts_ = other.bucketCounts_;
                    this.bitField0_ &= 0xFFFFFFDF;
                } else {
                    this.ensureBucketCountsIsMutable();
                    this.bucketCounts_.addAll(other.bucketCounts_);
                }
                this.onChanged();
            }
            if (this.exemplarsBuilder_ == null) {
                if (!other.exemplars_.isEmpty()) {
                    if (this.exemplars_.isEmpty()) {
                        this.exemplars_ = other.exemplars_;
                        this.bitField0_ &= 0xFFFFFFBF;
                    } else {
                        this.ensureExemplarsIsMutable();
                        this.exemplars_.addAll(other.exemplars_);
                    }
                    this.onChanged();
                }
            } else if (!other.exemplars_.isEmpty()) {
                if (this.exemplarsBuilder_.isEmpty()) {
                    this.exemplarsBuilder_.dispose();
                    this.exemplarsBuilder_ = null;
                    this.exemplars_ = other.exemplars_;
                    this.bitField0_ &= 0xFFFFFFBF;
                    this.exemplarsBuilder_ = alwaysUseFieldBuilders ? this.getExemplarsFieldBuilder() : null;
                } else {
                    this.exemplarsBuilder_.addAllMessages(other.exemplars_);
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
                block16: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block16;
                        }
                        case 8: {
                            this.count_ = input.readInt64();
                            this.bitField0_ |= 1;
                            continue block16;
                        }
                        case 17: {
                            this.mean_ = input.readDouble();
                            this.bitField0_ |= 2;
                            continue block16;
                        }
                        case 25: {
                            this.sumOfSquaredDeviation_ = input.readDouble();
                            this.bitField0_ |= 4;
                            continue block16;
                        }
                        case 34: {
                            input.readMessage(this.getRangeFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 8;
                            continue block16;
                        }
                        case 50: {
                            input.readMessage(this.getBucketOptionsFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x10;
                            continue block16;
                        }
                        case 56: {
                            long v = input.readInt64();
                            this.ensureBucketCountsIsMutable();
                            this.bucketCounts_.addLong(v);
                            continue block16;
                        }
                        case 58: {
                            int length = input.readRawVarint32();
                            int limit = input.pushLimit(length);
                            this.ensureBucketCountsIsMutable();
                            while (input.getBytesUntilLimit() > 0) {
                                this.bucketCounts_.addLong(input.readInt64());
                            }
                            input.popLimit(limit);
                            continue block16;
                        }
                        case 82: {
                            Exemplar m4 = input.readMessage(Exemplar.parser(), extensionRegistry);
                            if (this.exemplarsBuilder_ == null) {
                                this.ensureExemplarsIsMutable();
                                this.exemplars_.add(m4);
                                continue block16;
                            }
                            this.exemplarsBuilder_.addMessage(m4);
                            continue block16;
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
        public long getCount() {
            return this.count_;
        }

        public Builder setCount(long value) {
            this.count_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearCount() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.count_ = 0L;
            this.onChanged();
            return this;
        }

        @Override
        public double getMean() {
            return this.mean_;
        }

        public Builder setMean(double value) {
            this.mean_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearMean() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.mean_ = 0.0;
            this.onChanged();
            return this;
        }

        @Override
        public double getSumOfSquaredDeviation() {
            return this.sumOfSquaredDeviation_;
        }

        public Builder setSumOfSquaredDeviation(double value) {
            this.sumOfSquaredDeviation_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearSumOfSquaredDeviation() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.sumOfSquaredDeviation_ = 0.0;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasRange() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override
        public Range getRange() {
            if (this.rangeBuilder_ == null) {
                return this.range_ == null ? Range.getDefaultInstance() : this.range_;
            }
            return this.rangeBuilder_.getMessage();
        }

        public Builder setRange(Range value) {
            if (this.rangeBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.range_ = value;
            } else {
                this.rangeBuilder_.setMessage(value);
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder setRange(Range.Builder builderForValue) {
            if (this.rangeBuilder_ == null) {
                this.range_ = builderForValue.build();
            } else {
                this.rangeBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder mergeRange(Range value) {
            if (this.rangeBuilder_ == null) {
                if ((this.bitField0_ & 8) != 0 && this.range_ != null && this.range_ != Range.getDefaultInstance()) {
                    this.getRangeBuilder().mergeFrom(value);
                } else {
                    this.range_ = value;
                }
            } else {
                this.rangeBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearRange() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.range_ = null;
            if (this.rangeBuilder_ != null) {
                this.rangeBuilder_.dispose();
                this.rangeBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Range.Builder getRangeBuilder() {
            this.bitField0_ |= 8;
            this.onChanged();
            return this.getRangeFieldBuilder().getBuilder();
        }

        @Override
        public RangeOrBuilder getRangeOrBuilder() {
            if (this.rangeBuilder_ != null) {
                return this.rangeBuilder_.getMessageOrBuilder();
            }
            return this.range_ == null ? Range.getDefaultInstance() : this.range_;
        }

        private SingleFieldBuilderV3<Range, Range.Builder, RangeOrBuilder> getRangeFieldBuilder() {
            if (this.rangeBuilder_ == null) {
                this.rangeBuilder_ = new SingleFieldBuilderV3(this.getRange(), this.getParentForChildren(), this.isClean());
                this.range_ = null;
            }
            return this.rangeBuilder_;
        }

        @Override
        public boolean hasBucketOptions() {
            return (this.bitField0_ & 0x10) != 0;
        }

        @Override
        public BucketOptions getBucketOptions() {
            if (this.bucketOptionsBuilder_ == null) {
                return this.bucketOptions_ == null ? BucketOptions.getDefaultInstance() : this.bucketOptions_;
            }
            return this.bucketOptionsBuilder_.getMessage();
        }

        public Builder setBucketOptions(BucketOptions value) {
            if (this.bucketOptionsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bucketOptions_ = value;
            } else {
                this.bucketOptionsBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder setBucketOptions(BucketOptions.Builder builderForValue) {
            if (this.bucketOptionsBuilder_ == null) {
                this.bucketOptions_ = builderForValue.build();
            } else {
                this.bucketOptionsBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder mergeBucketOptions(BucketOptions value) {
            if (this.bucketOptionsBuilder_ == null) {
                if ((this.bitField0_ & 0x10) != 0 && this.bucketOptions_ != null && this.bucketOptions_ != BucketOptions.getDefaultInstance()) {
                    this.getBucketOptionsBuilder().mergeFrom(value);
                } else {
                    this.bucketOptions_ = value;
                }
            } else {
                this.bucketOptionsBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearBucketOptions() {
            this.bitField0_ &= 0xFFFFFFEF;
            this.bucketOptions_ = null;
            if (this.bucketOptionsBuilder_ != null) {
                this.bucketOptionsBuilder_.dispose();
                this.bucketOptionsBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public BucketOptions.Builder getBucketOptionsBuilder() {
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this.getBucketOptionsFieldBuilder().getBuilder();
        }

        @Override
        public BucketOptionsOrBuilder getBucketOptionsOrBuilder() {
            if (this.bucketOptionsBuilder_ != null) {
                return this.bucketOptionsBuilder_.getMessageOrBuilder();
            }
            return this.bucketOptions_ == null ? BucketOptions.getDefaultInstance() : this.bucketOptions_;
        }

        private SingleFieldBuilderV3<BucketOptions, BucketOptions.Builder, BucketOptionsOrBuilder> getBucketOptionsFieldBuilder() {
            if (this.bucketOptionsBuilder_ == null) {
                this.bucketOptionsBuilder_ = new SingleFieldBuilderV3(this.getBucketOptions(), this.getParentForChildren(), this.isClean());
                this.bucketOptions_ = null;
            }
            return this.bucketOptionsBuilder_;
        }

        private void ensureBucketCountsIsMutable() {
            if ((this.bitField0_ & 0x20) == 0) {
                this.bucketCounts_ = Distribution.mutableCopy(this.bucketCounts_);
                this.bitField0_ |= 0x20;
            }
        }

        @Override
        public List<Long> getBucketCountsList() {
            return (this.bitField0_ & 0x20) != 0 ? Collections.unmodifiableList(this.bucketCounts_) : this.bucketCounts_;
        }

        @Override
        public int getBucketCountsCount() {
            return this.bucketCounts_.size();
        }

        @Override
        public long getBucketCounts(int index) {
            return this.bucketCounts_.getLong(index);
        }

        public Builder setBucketCounts(int index, long value) {
            this.ensureBucketCountsIsMutable();
            this.bucketCounts_.setLong(index, value);
            this.onChanged();
            return this;
        }

        public Builder addBucketCounts(long value) {
            this.ensureBucketCountsIsMutable();
            this.bucketCounts_.addLong(value);
            this.onChanged();
            return this;
        }

        public Builder addAllBucketCounts(Iterable<? extends Long> values2) {
            this.ensureBucketCountsIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.bucketCounts_);
            this.onChanged();
            return this;
        }

        public Builder clearBucketCounts() {
            this.bucketCounts_ = Distribution.emptyLongList();
            this.bitField0_ &= 0xFFFFFFDF;
            this.onChanged();
            return this;
        }

        private void ensureExemplarsIsMutable() {
            if ((this.bitField0_ & 0x40) == 0) {
                this.exemplars_ = new ArrayList<Exemplar>(this.exemplars_);
                this.bitField0_ |= 0x40;
            }
        }

        @Override
        public List<Exemplar> getExemplarsList() {
            if (this.exemplarsBuilder_ == null) {
                return Collections.unmodifiableList(this.exemplars_);
            }
            return this.exemplarsBuilder_.getMessageList();
        }

        @Override
        public int getExemplarsCount() {
            if (this.exemplarsBuilder_ == null) {
                return this.exemplars_.size();
            }
            return this.exemplarsBuilder_.getCount();
        }

        @Override
        public Exemplar getExemplars(int index) {
            if (this.exemplarsBuilder_ == null) {
                return this.exemplars_.get(index);
            }
            return this.exemplarsBuilder_.getMessage(index);
        }

        public Builder setExemplars(int index, Exemplar value) {
            if (this.exemplarsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureExemplarsIsMutable();
                this.exemplars_.set(index, value);
                this.onChanged();
            } else {
                this.exemplarsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setExemplars(int index, Exemplar.Builder builderForValue) {
            if (this.exemplarsBuilder_ == null) {
                this.ensureExemplarsIsMutable();
                this.exemplars_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.exemplarsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addExemplars(Exemplar value) {
            if (this.exemplarsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureExemplarsIsMutable();
                this.exemplars_.add(value);
                this.onChanged();
            } else {
                this.exemplarsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addExemplars(int index, Exemplar value) {
            if (this.exemplarsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureExemplarsIsMutable();
                this.exemplars_.add(index, value);
                this.onChanged();
            } else {
                this.exemplarsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addExemplars(Exemplar.Builder builderForValue) {
            if (this.exemplarsBuilder_ == null) {
                this.ensureExemplarsIsMutable();
                this.exemplars_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.exemplarsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addExemplars(int index, Exemplar.Builder builderForValue) {
            if (this.exemplarsBuilder_ == null) {
                this.ensureExemplarsIsMutable();
                this.exemplars_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.exemplarsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllExemplars(Iterable<? extends Exemplar> values2) {
            if (this.exemplarsBuilder_ == null) {
                this.ensureExemplarsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.exemplars_);
                this.onChanged();
            } else {
                this.exemplarsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearExemplars() {
            if (this.exemplarsBuilder_ == null) {
                this.exemplars_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFBF;
                this.onChanged();
            } else {
                this.exemplarsBuilder_.clear();
            }
            return this;
        }

        public Builder removeExemplars(int index) {
            if (this.exemplarsBuilder_ == null) {
                this.ensureExemplarsIsMutable();
                this.exemplars_.remove(index);
                this.onChanged();
            } else {
                this.exemplarsBuilder_.remove(index);
            }
            return this;
        }

        public Exemplar.Builder getExemplarsBuilder(int index) {
            return this.getExemplarsFieldBuilder().getBuilder(index);
        }

        @Override
        public ExemplarOrBuilder getExemplarsOrBuilder(int index) {
            if (this.exemplarsBuilder_ == null) {
                return this.exemplars_.get(index);
            }
            return this.exemplarsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends ExemplarOrBuilder> getExemplarsOrBuilderList() {
            if (this.exemplarsBuilder_ != null) {
                return this.exemplarsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.exemplars_);
        }

        public Exemplar.Builder addExemplarsBuilder() {
            return this.getExemplarsFieldBuilder().addBuilder(Exemplar.getDefaultInstance());
        }

        public Exemplar.Builder addExemplarsBuilder(int index) {
            return this.getExemplarsFieldBuilder().addBuilder(index, Exemplar.getDefaultInstance());
        }

        public List<Exemplar.Builder> getExemplarsBuilderList() {
            return this.getExemplarsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Exemplar, Exemplar.Builder, ExemplarOrBuilder> getExemplarsFieldBuilder() {
            if (this.exemplarsBuilder_ == null) {
                this.exemplarsBuilder_ = new RepeatedFieldBuilderV3(this.exemplars_, (this.bitField0_ & 0x40) != 0, this.getParentForChildren(), this.isClean());
                this.exemplars_ = null;
            }
            return this.exemplarsBuilder_;
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

    public static final class Exemplar
    extends GeneratedMessageV3
    implements ExemplarOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int VALUE_FIELD_NUMBER = 1;
        private double value_ = 0.0;
        public static final int TIMESTAMP_FIELD_NUMBER = 2;
        private Timestamp timestamp_;
        public static final int ATTACHMENTS_FIELD_NUMBER = 3;
        private List<Any> attachments_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Exemplar DEFAULT_INSTANCE = new Exemplar();
        private static final Parser<Exemplar> PARSER = new AbstractParser<Exemplar>(){

            @Override
            public Exemplar parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = Exemplar.newBuilder();
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

        private Exemplar(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Exemplar() {
            this.attachments_ = Collections.emptyList();
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new Exemplar();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DistributionProto.internal_static_google_api_Distribution_Exemplar_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return DistributionProto.internal_static_google_api_Distribution_Exemplar_fieldAccessorTable.ensureFieldAccessorsInitialized(Exemplar.class, Builder.class);
        }

        @Override
        public double getValue() {
            return this.value_;
        }

        @Override
        public boolean hasTimestamp() {
            return this.timestamp_ != null;
        }

        @Override
        public Timestamp getTimestamp() {
            return this.timestamp_ == null ? Timestamp.getDefaultInstance() : this.timestamp_;
        }

        @Override
        public TimestampOrBuilder getTimestampOrBuilder() {
            return this.timestamp_ == null ? Timestamp.getDefaultInstance() : this.timestamp_;
        }

        @Override
        public List<Any> getAttachmentsList() {
            return this.attachments_;
        }

        @Override
        public List<? extends AnyOrBuilder> getAttachmentsOrBuilderList() {
            return this.attachments_;
        }

        @Override
        public int getAttachmentsCount() {
            return this.attachments_.size();
        }

        @Override
        public Any getAttachments(int index) {
            return this.attachments_.get(index);
        }

        @Override
        public AnyOrBuilder getAttachmentsOrBuilder(int index) {
            return this.attachments_.get(index);
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
            if (Double.doubleToRawLongBits(this.value_) != 0L) {
                output.writeDouble(1, this.value_);
            }
            if (this.timestamp_ != null) {
                output.writeMessage(2, this.getTimestamp());
            }
            for (int i = 0; i < this.attachments_.size(); ++i) {
                output.writeMessage(3, this.attachments_.get(i));
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
            if (Double.doubleToRawLongBits(this.value_) != 0L) {
                size += CodedOutputStream.computeDoubleSize(1, this.value_);
            }
            if (this.timestamp_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getTimestamp());
            }
            for (int i = 0; i < this.attachments_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(3, this.attachments_.get(i));
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Exemplar)) {
                return super.equals(obj);
            }
            Exemplar other = (Exemplar)obj;
            if (Double.doubleToLongBits(this.getValue()) != Double.doubleToLongBits(other.getValue())) {
                return false;
            }
            if (this.hasTimestamp() != other.hasTimestamp()) {
                return false;
            }
            if (this.hasTimestamp() && !this.getTimestamp().equals(other.getTimestamp())) {
                return false;
            }
            if (!this.getAttachmentsList().equals(other.getAttachmentsList())) {
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
            hash = 19 * hash + Exemplar.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getValue()));
            if (this.hasTimestamp()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getTimestamp().hashCode();
            }
            if (this.getAttachmentsCount() > 0) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getAttachmentsList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static Exemplar parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Exemplar parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Exemplar parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Exemplar parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Exemplar parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Exemplar parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Exemplar parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Exemplar parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Exemplar parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Exemplar parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Exemplar parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Exemplar parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Exemplar.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Exemplar prototype) {
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

        public static Exemplar getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Exemplar> parser() {
            return PARSER;
        }

        public Parser<Exemplar> getParserForType() {
            return PARSER;
        }

        @Override
        public Exemplar getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ExemplarOrBuilder {
            private int bitField0_;
            private double value_;
            private Timestamp timestamp_;
            private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> timestampBuilder_;
            private List<Any> attachments_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> attachmentsBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DistributionProto.internal_static_google_api_Distribution_Exemplar_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return DistributionProto.internal_static_google_api_Distribution_Exemplar_fieldAccessorTable.ensureFieldAccessorsInitialized(Exemplar.class, Builder.class);
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
                this.value_ = 0.0;
                this.timestamp_ = null;
                if (this.timestampBuilder_ != null) {
                    this.timestampBuilder_.dispose();
                    this.timestampBuilder_ = null;
                }
                if (this.attachmentsBuilder_ == null) {
                    this.attachments_ = Collections.emptyList();
                } else {
                    this.attachments_ = null;
                    this.attachmentsBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return DistributionProto.internal_static_google_api_Distribution_Exemplar_descriptor;
            }

            @Override
            public Exemplar getDefaultInstanceForType() {
                return Exemplar.getDefaultInstance();
            }

            @Override
            public Exemplar build() {
                Exemplar result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Exemplar buildPartial() {
                Exemplar result = new Exemplar(this);
                this.buildPartialRepeatedFields(result);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartialRepeatedFields(Exemplar result) {
                if (this.attachmentsBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 0) {
                        this.attachments_ = Collections.unmodifiableList(this.attachments_);
                        this.bitField0_ &= 0xFFFFFFFB;
                    }
                    result.attachments_ = this.attachments_;
                } else {
                    result.attachments_ = this.attachmentsBuilder_.build();
                }
            }

            private void buildPartial0(Exemplar result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.value_ = this.value_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.timestamp_ = this.timestampBuilder_ == null ? this.timestamp_ : this.timestampBuilder_.build();
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
                if (other instanceof Exemplar) {
                    return this.mergeFrom((Exemplar)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Exemplar other) {
                if (other == Exemplar.getDefaultInstance()) {
                    return this;
                }
                if (other.getValue() != 0.0) {
                    this.setValue(other.getValue());
                }
                if (other.hasTimestamp()) {
                    this.mergeTimestamp(other.getTimestamp());
                }
                if (this.attachmentsBuilder_ == null) {
                    if (!other.attachments_.isEmpty()) {
                        if (this.attachments_.isEmpty()) {
                            this.attachments_ = other.attachments_;
                            this.bitField0_ &= 0xFFFFFFFB;
                        } else {
                            this.ensureAttachmentsIsMutable();
                            this.attachments_.addAll(other.attachments_);
                        }
                        this.onChanged();
                    }
                } else if (!other.attachments_.isEmpty()) {
                    if (this.attachmentsBuilder_.isEmpty()) {
                        this.attachmentsBuilder_.dispose();
                        this.attachmentsBuilder_ = null;
                        this.attachments_ = other.attachments_;
                        this.bitField0_ &= 0xFFFFFFFB;
                        this.attachmentsBuilder_ = alwaysUseFieldBuilders ? this.getAttachmentsFieldBuilder() : null;
                    } else {
                        this.attachmentsBuilder_.addAllMessages(other.attachments_);
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
                    block11: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block11;
                            }
                            case 9: {
                                this.value_ = input.readDouble();
                                this.bitField0_ |= 1;
                                continue block11;
                            }
                            case 18: {
                                input.readMessage(this.getTimestampFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 2;
                                continue block11;
                            }
                            case 26: {
                                Any m4 = input.readMessage(Any.parser(), extensionRegistry);
                                if (this.attachmentsBuilder_ == null) {
                                    this.ensureAttachmentsIsMutable();
                                    this.attachments_.add(m4);
                                    continue block11;
                                }
                                this.attachmentsBuilder_.addMessage(m4);
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
            public double getValue() {
                return this.value_;
            }

            public Builder setValue(double value) {
                this.value_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearValue() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.value_ = 0.0;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasTimestamp() {
                return (this.bitField0_ & 2) != 0;
            }

            @Override
            public Timestamp getTimestamp() {
                if (this.timestampBuilder_ == null) {
                    return this.timestamp_ == null ? Timestamp.getDefaultInstance() : this.timestamp_;
                }
                return this.timestampBuilder_.getMessage();
            }

            public Builder setTimestamp(Timestamp value) {
                if (this.timestampBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.timestamp_ = value;
                } else {
                    this.timestampBuilder_.setMessage(value);
                }
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder setTimestamp(Timestamp.Builder builderForValue) {
                if (this.timestampBuilder_ == null) {
                    this.timestamp_ = builderForValue.build();
                } else {
                    this.timestampBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder mergeTimestamp(Timestamp value) {
                if (this.timestampBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 0 && this.timestamp_ != null && this.timestamp_ != Timestamp.getDefaultInstance()) {
                        this.getTimestampBuilder().mergeFrom(value);
                    } else {
                        this.timestamp_ = value;
                    }
                } else {
                    this.timestampBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearTimestamp() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.timestamp_ = null;
                if (this.timestampBuilder_ != null) {
                    this.timestampBuilder_.dispose();
                    this.timestampBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Timestamp.Builder getTimestampBuilder() {
                this.bitField0_ |= 2;
                this.onChanged();
                return this.getTimestampFieldBuilder().getBuilder();
            }

            @Override
            public TimestampOrBuilder getTimestampOrBuilder() {
                if (this.timestampBuilder_ != null) {
                    return this.timestampBuilder_.getMessageOrBuilder();
                }
                return this.timestamp_ == null ? Timestamp.getDefaultInstance() : this.timestamp_;
            }

            private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> getTimestampFieldBuilder() {
                if (this.timestampBuilder_ == null) {
                    this.timestampBuilder_ = new SingleFieldBuilderV3(this.getTimestamp(), this.getParentForChildren(), this.isClean());
                    this.timestamp_ = null;
                }
                return this.timestampBuilder_;
            }

            private void ensureAttachmentsIsMutable() {
                if ((this.bitField0_ & 4) == 0) {
                    this.attachments_ = new ArrayList<Any>(this.attachments_);
                    this.bitField0_ |= 4;
                }
            }

            @Override
            public List<Any> getAttachmentsList() {
                if (this.attachmentsBuilder_ == null) {
                    return Collections.unmodifiableList(this.attachments_);
                }
                return this.attachmentsBuilder_.getMessageList();
            }

            @Override
            public int getAttachmentsCount() {
                if (this.attachmentsBuilder_ == null) {
                    return this.attachments_.size();
                }
                return this.attachmentsBuilder_.getCount();
            }

            @Override
            public Any getAttachments(int index) {
                if (this.attachmentsBuilder_ == null) {
                    return this.attachments_.get(index);
                }
                return this.attachmentsBuilder_.getMessage(index);
            }

            public Builder setAttachments(int index, Any value) {
                if (this.attachmentsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAttachmentsIsMutable();
                    this.attachments_.set(index, value);
                    this.onChanged();
                } else {
                    this.attachmentsBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setAttachments(int index, Any.Builder builderForValue) {
                if (this.attachmentsBuilder_ == null) {
                    this.ensureAttachmentsIsMutable();
                    this.attachments_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.attachmentsBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAttachments(Any value) {
                if (this.attachmentsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAttachmentsIsMutable();
                    this.attachments_.add(value);
                    this.onChanged();
                } else {
                    this.attachmentsBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addAttachments(int index, Any value) {
                if (this.attachmentsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAttachmentsIsMutable();
                    this.attachments_.add(index, value);
                    this.onChanged();
                } else {
                    this.attachmentsBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addAttachments(Any.Builder builderForValue) {
                if (this.attachmentsBuilder_ == null) {
                    this.ensureAttachmentsIsMutable();
                    this.attachments_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.attachmentsBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addAttachments(int index, Any.Builder builderForValue) {
                if (this.attachmentsBuilder_ == null) {
                    this.ensureAttachmentsIsMutable();
                    this.attachments_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.attachmentsBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllAttachments(Iterable<? extends Any> values2) {
                if (this.attachmentsBuilder_ == null) {
                    this.ensureAttachmentsIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.attachments_);
                    this.onChanged();
                } else {
                    this.attachmentsBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearAttachments() {
                if (this.attachmentsBuilder_ == null) {
                    this.attachments_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFB;
                    this.onChanged();
                } else {
                    this.attachmentsBuilder_.clear();
                }
                return this;
            }

            public Builder removeAttachments(int index) {
                if (this.attachmentsBuilder_ == null) {
                    this.ensureAttachmentsIsMutable();
                    this.attachments_.remove(index);
                    this.onChanged();
                } else {
                    this.attachmentsBuilder_.remove(index);
                }
                return this;
            }

            public Any.Builder getAttachmentsBuilder(int index) {
                return this.getAttachmentsFieldBuilder().getBuilder(index);
            }

            @Override
            public AnyOrBuilder getAttachmentsOrBuilder(int index) {
                if (this.attachmentsBuilder_ == null) {
                    return this.attachments_.get(index);
                }
                return this.attachmentsBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends AnyOrBuilder> getAttachmentsOrBuilderList() {
                if (this.attachmentsBuilder_ != null) {
                    return this.attachmentsBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.attachments_);
            }

            public Any.Builder addAttachmentsBuilder() {
                return this.getAttachmentsFieldBuilder().addBuilder(Any.getDefaultInstance());
            }

            public Any.Builder addAttachmentsBuilder(int index) {
                return this.getAttachmentsFieldBuilder().addBuilder(index, Any.getDefaultInstance());
            }

            public List<Any.Builder> getAttachmentsBuilderList() {
                return this.getAttachmentsFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> getAttachmentsFieldBuilder() {
                if (this.attachmentsBuilder_ == null) {
                    this.attachmentsBuilder_ = new RepeatedFieldBuilderV3(this.attachments_, (this.bitField0_ & 4) != 0, this.getParentForChildren(), this.isClean());
                    this.attachments_ = null;
                }
                return this.attachmentsBuilder_;
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

    public static interface ExemplarOrBuilder
    extends MessageOrBuilder {
        public double getValue();

        public boolean hasTimestamp();

        public Timestamp getTimestamp();

        public TimestampOrBuilder getTimestampOrBuilder();

        public List<Any> getAttachmentsList();

        public Any getAttachments(int var1);

        public int getAttachmentsCount();

        public List<? extends AnyOrBuilder> getAttachmentsOrBuilderList();

        public AnyOrBuilder getAttachmentsOrBuilder(int var1);
    }

    public static interface BucketOptionsOrBuilder
    extends MessageOrBuilder {
        public boolean hasLinearBuckets();

        public BucketOptions.Linear getLinearBuckets();

        public BucketOptions.LinearOrBuilder getLinearBucketsOrBuilder();

        public boolean hasExponentialBuckets();

        public BucketOptions.Exponential getExponentialBuckets();

        public BucketOptions.ExponentialOrBuilder getExponentialBucketsOrBuilder();

        public boolean hasExplicitBuckets();

        public BucketOptions.Explicit getExplicitBuckets();

        public BucketOptions.ExplicitOrBuilder getExplicitBucketsOrBuilder();

        public BucketOptions.OptionsCase getOptionsCase();
    }

    public static interface RangeOrBuilder
    extends MessageOrBuilder {
        public double getMin();

        public double getMax();
    }
}

