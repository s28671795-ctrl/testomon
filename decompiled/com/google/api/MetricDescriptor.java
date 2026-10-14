/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.LabelDescriptor;
import com.google.api.LabelDescriptorOrBuilder;
import com.google.api.LaunchStage;
import com.google.api.MetricDescriptorOrBuilder;
import com.google.api.MetricProto;
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
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MetricDescriptor
extends GeneratedMessageV3
implements MetricDescriptorOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 1;
    private volatile Object name_ = "";
    public static final int TYPE_FIELD_NUMBER = 8;
    private volatile Object type_ = "";
    public static final int LABELS_FIELD_NUMBER = 2;
    private List<LabelDescriptor> labels_;
    public static final int METRIC_KIND_FIELD_NUMBER = 3;
    private int metricKind_ = 0;
    public static final int VALUE_TYPE_FIELD_NUMBER = 4;
    private int valueType_ = 0;
    public static final int UNIT_FIELD_NUMBER = 5;
    private volatile Object unit_ = "";
    public static final int DESCRIPTION_FIELD_NUMBER = 6;
    private volatile Object description_ = "";
    public static final int DISPLAY_NAME_FIELD_NUMBER = 7;
    private volatile Object displayName_ = "";
    public static final int METADATA_FIELD_NUMBER = 10;
    private MetricDescriptorMetadata metadata_;
    public static final int LAUNCH_STAGE_FIELD_NUMBER = 12;
    private int launchStage_ = 0;
    public static final int MONITORED_RESOURCE_TYPES_FIELD_NUMBER = 13;
    private LazyStringArrayList monitoredResourceTypes_ = LazyStringArrayList.emptyList();
    private byte memoizedIsInitialized = (byte)-1;
    private static final MetricDescriptor DEFAULT_INSTANCE = new MetricDescriptor();
    private static final Parser<MetricDescriptor> PARSER = new AbstractParser<MetricDescriptor>(){

        @Override
        public MetricDescriptor parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = MetricDescriptor.newBuilder();
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

    private MetricDescriptor(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private MetricDescriptor() {
        this.name_ = "";
        this.type_ = "";
        this.labels_ = Collections.emptyList();
        this.metricKind_ = 0;
        this.valueType_ = 0;
        this.unit_ = "";
        this.description_ = "";
        this.displayName_ = "";
        this.launchStage_ = 0;
        this.monitoredResourceTypes_ = LazyStringArrayList.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new MetricDescriptor();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return MetricProto.internal_static_google_api_MetricDescriptor_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return MetricProto.internal_static_google_api_MetricDescriptor_fieldAccessorTable.ensureFieldAccessorsInitialized(MetricDescriptor.class, Builder.class);
    }

    @Override
    public String getName() {
        Object ref = this.name_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.name_ = s2;
        return s2;
    }

    @Override
    public ByteString getNameBytes() {
        Object ref = this.name_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.name_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getType() {
        Object ref = this.type_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.type_ = s2;
        return s2;
    }

    @Override
    public ByteString getTypeBytes() {
        Object ref = this.type_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.type_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public List<LabelDescriptor> getLabelsList() {
        return this.labels_;
    }

    @Override
    public List<? extends LabelDescriptorOrBuilder> getLabelsOrBuilderList() {
        return this.labels_;
    }

    @Override
    public int getLabelsCount() {
        return this.labels_.size();
    }

    @Override
    public LabelDescriptor getLabels(int index) {
        return this.labels_.get(index);
    }

    @Override
    public LabelDescriptorOrBuilder getLabelsOrBuilder(int index) {
        return this.labels_.get(index);
    }

    @Override
    public int getMetricKindValue() {
        return this.metricKind_;
    }

    @Override
    public MetricKind getMetricKind() {
        MetricKind result = MetricKind.forNumber(this.metricKind_);
        return result == null ? MetricKind.UNRECOGNIZED : result;
    }

    @Override
    public int getValueTypeValue() {
        return this.valueType_;
    }

    @Override
    public ValueType getValueType() {
        ValueType result = ValueType.forNumber(this.valueType_);
        return result == null ? ValueType.UNRECOGNIZED : result;
    }

    @Override
    public String getUnit() {
        Object ref = this.unit_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.unit_ = s2;
        return s2;
    }

    @Override
    public ByteString getUnitBytes() {
        Object ref = this.unit_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.unit_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getDescription() {
        Object ref = this.description_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.description_ = s2;
        return s2;
    }

    @Override
    public ByteString getDescriptionBytes() {
        Object ref = this.description_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.description_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getDisplayName() {
        Object ref = this.displayName_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.displayName_ = s2;
        return s2;
    }

    @Override
    public ByteString getDisplayNameBytes() {
        Object ref = this.displayName_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.displayName_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasMetadata() {
        return this.metadata_ != null;
    }

    @Override
    public MetricDescriptorMetadata getMetadata() {
        return this.metadata_ == null ? MetricDescriptorMetadata.getDefaultInstance() : this.metadata_;
    }

    @Override
    public MetricDescriptorMetadataOrBuilder getMetadataOrBuilder() {
        return this.metadata_ == null ? MetricDescriptorMetadata.getDefaultInstance() : this.metadata_;
    }

    @Override
    public int getLaunchStageValue() {
        return this.launchStage_;
    }

    @Override
    public LaunchStage getLaunchStage() {
        LaunchStage result = LaunchStage.forNumber(this.launchStage_);
        return result == null ? LaunchStage.UNRECOGNIZED : result;
    }

    public ProtocolStringList getMonitoredResourceTypesList() {
        return this.monitoredResourceTypes_;
    }

    @Override
    public int getMonitoredResourceTypesCount() {
        return this.monitoredResourceTypes_.size();
    }

    @Override
    public String getMonitoredResourceTypes(int index) {
        return this.monitoredResourceTypes_.get(index);
    }

    @Override
    public ByteString getMonitoredResourceTypesBytes(int index) {
        return this.monitoredResourceTypes_.getByteString(index);
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
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            GeneratedMessageV3.writeString(output, 1, this.name_);
        }
        for (i = 0; i < this.labels_.size(); ++i) {
            output.writeMessage(2, this.labels_.get(i));
        }
        if (this.metricKind_ != MetricKind.METRIC_KIND_UNSPECIFIED.getNumber()) {
            output.writeEnum(3, this.metricKind_);
        }
        if (this.valueType_ != ValueType.VALUE_TYPE_UNSPECIFIED.getNumber()) {
            output.writeEnum(4, this.valueType_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.unit_)) {
            GeneratedMessageV3.writeString(output, 5, this.unit_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.description_)) {
            GeneratedMessageV3.writeString(output, 6, this.description_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
            GeneratedMessageV3.writeString(output, 7, this.displayName_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.type_)) {
            GeneratedMessageV3.writeString(output, 8, this.type_);
        }
        if (this.metadata_ != null) {
            output.writeMessage(10, this.getMetadata());
        }
        if (this.launchStage_ != LaunchStage.LAUNCH_STAGE_UNSPECIFIED.getNumber()) {
            output.writeEnum(12, this.launchStage_);
        }
        for (i = 0; i < this.monitoredResourceTypes_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 13, this.monitoredResourceTypes_.getRaw(i));
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
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.name_);
        }
        for (int i = 0; i < this.labels_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(2, this.labels_.get(i));
        }
        if (this.metricKind_ != MetricKind.METRIC_KIND_UNSPECIFIED.getNumber()) {
            size += CodedOutputStream.computeEnumSize(3, this.metricKind_);
        }
        if (this.valueType_ != ValueType.VALUE_TYPE_UNSPECIFIED.getNumber()) {
            size += CodedOutputStream.computeEnumSize(4, this.valueType_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.unit_)) {
            size += GeneratedMessageV3.computeStringSize(5, this.unit_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.description_)) {
            size += GeneratedMessageV3.computeStringSize(6, this.description_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
            size += GeneratedMessageV3.computeStringSize(7, this.displayName_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.type_)) {
            size += GeneratedMessageV3.computeStringSize(8, this.type_);
        }
        if (this.metadata_ != null) {
            size += CodedOutputStream.computeMessageSize(10, this.getMetadata());
        }
        if (this.launchStage_ != LaunchStage.LAUNCH_STAGE_UNSPECIFIED.getNumber()) {
            size += CodedOutputStream.computeEnumSize(12, this.launchStage_);
        }
        int dataSize = 0;
        for (int i = 0; i < this.monitoredResourceTypes_.size(); ++i) {
            dataSize += MetricDescriptor.computeStringSizeNoTag(this.monitoredResourceTypes_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getMonitoredResourceTypesList().size();
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MetricDescriptor)) {
            return super.equals(obj);
        }
        MetricDescriptor other = (MetricDescriptor)obj;
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (!this.getType().equals(other.getType())) {
            return false;
        }
        if (!this.getLabelsList().equals(other.getLabelsList())) {
            return false;
        }
        if (this.metricKind_ != other.metricKind_) {
            return false;
        }
        if (this.valueType_ != other.valueType_) {
            return false;
        }
        if (!this.getUnit().equals(other.getUnit())) {
            return false;
        }
        if (!this.getDescription().equals(other.getDescription())) {
            return false;
        }
        if (!this.getDisplayName().equals(other.getDisplayName())) {
            return false;
        }
        if (this.hasMetadata() != other.hasMetadata()) {
            return false;
        }
        if (this.hasMetadata() && !this.getMetadata().equals(other.getMetadata())) {
            return false;
        }
        if (this.launchStage_ != other.launchStage_) {
            return false;
        }
        if (!this.getMonitoredResourceTypesList().equals(other.getMonitoredResourceTypesList())) {
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
        hash = 19 * hash + MetricDescriptor.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getName().hashCode();
        hash = 37 * hash + 8;
        hash = 53 * hash + this.getType().hashCode();
        if (this.getLabelsCount() > 0) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getLabelsList().hashCode();
        }
        hash = 37 * hash + 3;
        hash = 53 * hash + this.metricKind_;
        hash = 37 * hash + 4;
        hash = 53 * hash + this.valueType_;
        hash = 37 * hash + 5;
        hash = 53 * hash + this.getUnit().hashCode();
        hash = 37 * hash + 6;
        hash = 53 * hash + this.getDescription().hashCode();
        hash = 37 * hash + 7;
        hash = 53 * hash + this.getDisplayName().hashCode();
        if (this.hasMetadata()) {
            hash = 37 * hash + 10;
            hash = 53 * hash + this.getMetadata().hashCode();
        }
        hash = 37 * hash + 12;
        hash = 53 * hash + this.launchStage_;
        if (this.getMonitoredResourceTypesCount() > 0) {
            hash = 37 * hash + 13;
            hash = 53 * hash + this.getMonitoredResourceTypesList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static MetricDescriptor parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MetricDescriptor parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MetricDescriptor parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MetricDescriptor parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MetricDescriptor parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MetricDescriptor parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MetricDescriptor parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static MetricDescriptor parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static MetricDescriptor parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static MetricDescriptor parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static MetricDescriptor parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static MetricDescriptor parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return MetricDescriptor.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(MetricDescriptor prototype) {
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

    public static MetricDescriptor getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<MetricDescriptor> parser() {
        return PARSER;
    }

    public Parser<MetricDescriptor> getParserForType() {
        return PARSER;
    }

    @Override
    public MetricDescriptor getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class MetricDescriptorMetadata
    extends GeneratedMessageV3
    implements MetricDescriptorMetadataOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int LAUNCH_STAGE_FIELD_NUMBER = 1;
        private int launchStage_ = 0;
        public static final int SAMPLE_PERIOD_FIELD_NUMBER = 2;
        private Duration samplePeriod_;
        public static final int INGEST_DELAY_FIELD_NUMBER = 3;
        private Duration ingestDelay_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final MetricDescriptorMetadata DEFAULT_INSTANCE = new MetricDescriptorMetadata();
        private static final Parser<MetricDescriptorMetadata> PARSER = new AbstractParser<MetricDescriptorMetadata>(){

            @Override
            public MetricDescriptorMetadata parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = MetricDescriptorMetadata.newBuilder();
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

        private MetricDescriptorMetadata(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private MetricDescriptorMetadata() {
            this.launchStage_ = 0;
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new MetricDescriptorMetadata();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return MetricProto.internal_static_google_api_MetricDescriptor_MetricDescriptorMetadata_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MetricProto.internal_static_google_api_MetricDescriptor_MetricDescriptorMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(MetricDescriptorMetadata.class, Builder.class);
        }

        @Override
        @Deprecated
        public int getLaunchStageValue() {
            return this.launchStage_;
        }

        @Override
        @Deprecated
        public LaunchStage getLaunchStage() {
            LaunchStage result = LaunchStage.forNumber(this.launchStage_);
            return result == null ? LaunchStage.UNRECOGNIZED : result;
        }

        @Override
        public boolean hasSamplePeriod() {
            return this.samplePeriod_ != null;
        }

        @Override
        public Duration getSamplePeriod() {
            return this.samplePeriod_ == null ? Duration.getDefaultInstance() : this.samplePeriod_;
        }

        @Override
        public DurationOrBuilder getSamplePeriodOrBuilder() {
            return this.samplePeriod_ == null ? Duration.getDefaultInstance() : this.samplePeriod_;
        }

        @Override
        public boolean hasIngestDelay() {
            return this.ingestDelay_ != null;
        }

        @Override
        public Duration getIngestDelay() {
            return this.ingestDelay_ == null ? Duration.getDefaultInstance() : this.ingestDelay_;
        }

        @Override
        public DurationOrBuilder getIngestDelayOrBuilder() {
            return this.ingestDelay_ == null ? Duration.getDefaultInstance() : this.ingestDelay_;
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
            if (this.launchStage_ != LaunchStage.LAUNCH_STAGE_UNSPECIFIED.getNumber()) {
                output.writeEnum(1, this.launchStage_);
            }
            if (this.samplePeriod_ != null) {
                output.writeMessage(2, this.getSamplePeriod());
            }
            if (this.ingestDelay_ != null) {
                output.writeMessage(3, this.getIngestDelay());
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
            if (this.launchStage_ != LaunchStage.LAUNCH_STAGE_UNSPECIFIED.getNumber()) {
                size += CodedOutputStream.computeEnumSize(1, this.launchStage_);
            }
            if (this.samplePeriod_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getSamplePeriod());
            }
            if (this.ingestDelay_ != null) {
                size += CodedOutputStream.computeMessageSize(3, this.getIngestDelay());
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MetricDescriptorMetadata)) {
                return super.equals(obj);
            }
            MetricDescriptorMetadata other = (MetricDescriptorMetadata)obj;
            if (this.launchStage_ != other.launchStage_) {
                return false;
            }
            if (this.hasSamplePeriod() != other.hasSamplePeriod()) {
                return false;
            }
            if (this.hasSamplePeriod() && !this.getSamplePeriod().equals(other.getSamplePeriod())) {
                return false;
            }
            if (this.hasIngestDelay() != other.hasIngestDelay()) {
                return false;
            }
            if (this.hasIngestDelay() && !this.getIngestDelay().equals(other.getIngestDelay())) {
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
            hash = 19 * hash + MetricDescriptorMetadata.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.launchStage_;
            if (this.hasSamplePeriod()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getSamplePeriod().hashCode();
            }
            if (this.hasIngestDelay()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getIngestDelay().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static MetricDescriptorMetadata parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MetricDescriptorMetadata parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MetricDescriptorMetadata parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MetricDescriptorMetadata parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MetricDescriptorMetadata parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MetricDescriptorMetadata parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MetricDescriptorMetadata parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MetricDescriptorMetadata parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MetricDescriptorMetadata parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MetricDescriptorMetadata parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MetricDescriptorMetadata parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MetricDescriptorMetadata parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return MetricDescriptorMetadata.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MetricDescriptorMetadata prototype) {
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

        public static MetricDescriptorMetadata getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MetricDescriptorMetadata> parser() {
            return PARSER;
        }

        public Parser<MetricDescriptorMetadata> getParserForType() {
            return PARSER;
        }

        @Override
        public MetricDescriptorMetadata getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements MetricDescriptorMetadataOrBuilder {
            private int bitField0_;
            private int launchStage_ = 0;
            private Duration samplePeriod_;
            private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> samplePeriodBuilder_;
            private Duration ingestDelay_;
            private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> ingestDelayBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return MetricProto.internal_static_google_api_MetricDescriptor_MetricDescriptorMetadata_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MetricProto.internal_static_google_api_MetricDescriptor_MetricDescriptorMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(MetricDescriptorMetadata.class, Builder.class);
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
                this.launchStage_ = 0;
                this.samplePeriod_ = null;
                if (this.samplePeriodBuilder_ != null) {
                    this.samplePeriodBuilder_.dispose();
                    this.samplePeriodBuilder_ = null;
                }
                this.ingestDelay_ = null;
                if (this.ingestDelayBuilder_ != null) {
                    this.ingestDelayBuilder_.dispose();
                    this.ingestDelayBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MetricProto.internal_static_google_api_MetricDescriptor_MetricDescriptorMetadata_descriptor;
            }

            @Override
            public MetricDescriptorMetadata getDefaultInstanceForType() {
                return MetricDescriptorMetadata.getDefaultInstance();
            }

            @Override
            public MetricDescriptorMetadata build() {
                MetricDescriptorMetadata result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public MetricDescriptorMetadata buildPartial() {
                MetricDescriptorMetadata result = new MetricDescriptorMetadata(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(MetricDescriptorMetadata result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.launchStage_ = this.launchStage_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.samplePeriod_ = this.samplePeriodBuilder_ == null ? this.samplePeriod_ : this.samplePeriodBuilder_.build();
                }
                if ((from_bitField0_ & 4) != 0) {
                    result.ingestDelay_ = this.ingestDelayBuilder_ == null ? this.ingestDelay_ : this.ingestDelayBuilder_.build();
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
                if (other instanceof MetricDescriptorMetadata) {
                    return this.mergeFrom((MetricDescriptorMetadata)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MetricDescriptorMetadata other) {
                if (other == MetricDescriptorMetadata.getDefaultInstance()) {
                    return this;
                }
                if (other.launchStage_ != 0) {
                    this.setLaunchStageValue(other.getLaunchStageValue());
                }
                if (other.hasSamplePeriod()) {
                    this.mergeSamplePeriod(other.getSamplePeriod());
                }
                if (other.hasIngestDelay()) {
                    this.mergeIngestDelay(other.getIngestDelay());
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
                                this.launchStage_ = input.readEnum();
                                this.bitField0_ |= 1;
                                continue block11;
                            }
                            case 18: {
                                input.readMessage(this.getSamplePeriodFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 2;
                                continue block11;
                            }
                            case 26: {
                                input.readMessage(this.getIngestDelayFieldBuilder().getBuilder(), extensionRegistry);
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
            @Deprecated
            public int getLaunchStageValue() {
                return this.launchStage_;
            }

            @Deprecated
            public Builder setLaunchStageValue(int value) {
                this.launchStage_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            @Deprecated
            public LaunchStage getLaunchStage() {
                LaunchStage result = LaunchStage.forNumber(this.launchStage_);
                return result == null ? LaunchStage.UNRECOGNIZED : result;
            }

            @Deprecated
            public Builder setLaunchStage(LaunchStage value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.launchStage_ = value.getNumber();
                this.onChanged();
                return this;
            }

            @Deprecated
            public Builder clearLaunchStage() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.launchStage_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasSamplePeriod() {
                return (this.bitField0_ & 2) != 0;
            }

            @Override
            public Duration getSamplePeriod() {
                if (this.samplePeriodBuilder_ == null) {
                    return this.samplePeriod_ == null ? Duration.getDefaultInstance() : this.samplePeriod_;
                }
                return this.samplePeriodBuilder_.getMessage();
            }

            public Builder setSamplePeriod(Duration value) {
                if (this.samplePeriodBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.samplePeriod_ = value;
                } else {
                    this.samplePeriodBuilder_.setMessage(value);
                }
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder setSamplePeriod(Duration.Builder builderForValue) {
                if (this.samplePeriodBuilder_ == null) {
                    this.samplePeriod_ = builderForValue.build();
                } else {
                    this.samplePeriodBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder mergeSamplePeriod(Duration value) {
                if (this.samplePeriodBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 0 && this.samplePeriod_ != null && this.samplePeriod_ != Duration.getDefaultInstance()) {
                        this.getSamplePeriodBuilder().mergeFrom(value);
                    } else {
                        this.samplePeriod_ = value;
                    }
                } else {
                    this.samplePeriodBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearSamplePeriod() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.samplePeriod_ = null;
                if (this.samplePeriodBuilder_ != null) {
                    this.samplePeriodBuilder_.dispose();
                    this.samplePeriodBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Duration.Builder getSamplePeriodBuilder() {
                this.bitField0_ |= 2;
                this.onChanged();
                return this.getSamplePeriodFieldBuilder().getBuilder();
            }

            @Override
            public DurationOrBuilder getSamplePeriodOrBuilder() {
                if (this.samplePeriodBuilder_ != null) {
                    return this.samplePeriodBuilder_.getMessageOrBuilder();
                }
                return this.samplePeriod_ == null ? Duration.getDefaultInstance() : this.samplePeriod_;
            }

            private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> getSamplePeriodFieldBuilder() {
                if (this.samplePeriodBuilder_ == null) {
                    this.samplePeriodBuilder_ = new SingleFieldBuilderV3(this.getSamplePeriod(), this.getParentForChildren(), this.isClean());
                    this.samplePeriod_ = null;
                }
                return this.samplePeriodBuilder_;
            }

            @Override
            public boolean hasIngestDelay() {
                return (this.bitField0_ & 4) != 0;
            }

            @Override
            public Duration getIngestDelay() {
                if (this.ingestDelayBuilder_ == null) {
                    return this.ingestDelay_ == null ? Duration.getDefaultInstance() : this.ingestDelay_;
                }
                return this.ingestDelayBuilder_.getMessage();
            }

            public Builder setIngestDelay(Duration value) {
                if (this.ingestDelayBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ingestDelay_ = value;
                } else {
                    this.ingestDelayBuilder_.setMessage(value);
                }
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder setIngestDelay(Duration.Builder builderForValue) {
                if (this.ingestDelayBuilder_ == null) {
                    this.ingestDelay_ = builderForValue.build();
                } else {
                    this.ingestDelayBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder mergeIngestDelay(Duration value) {
                if (this.ingestDelayBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 0 && this.ingestDelay_ != null && this.ingestDelay_ != Duration.getDefaultInstance()) {
                        this.getIngestDelayBuilder().mergeFrom(value);
                    } else {
                        this.ingestDelay_ = value;
                    }
                } else {
                    this.ingestDelayBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder clearIngestDelay() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.ingestDelay_ = null;
                if (this.ingestDelayBuilder_ != null) {
                    this.ingestDelayBuilder_.dispose();
                    this.ingestDelayBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Duration.Builder getIngestDelayBuilder() {
                this.bitField0_ |= 4;
                this.onChanged();
                return this.getIngestDelayFieldBuilder().getBuilder();
            }

            @Override
            public DurationOrBuilder getIngestDelayOrBuilder() {
                if (this.ingestDelayBuilder_ != null) {
                    return this.ingestDelayBuilder_.getMessageOrBuilder();
                }
                return this.ingestDelay_ == null ? Duration.getDefaultInstance() : this.ingestDelay_;
            }

            private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> getIngestDelayFieldBuilder() {
                if (this.ingestDelayBuilder_ == null) {
                    this.ingestDelayBuilder_ = new SingleFieldBuilderV3(this.getIngestDelay(), this.getParentForChildren(), this.isClean());
                    this.ingestDelay_ = null;
                }
                return this.ingestDelayBuilder_;
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
    implements MetricDescriptorOrBuilder {
        private int bitField0_;
        private Object name_ = "";
        private Object type_ = "";
        private List<LabelDescriptor> labels_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<LabelDescriptor, LabelDescriptor.Builder, LabelDescriptorOrBuilder> labelsBuilder_;
        private int metricKind_ = 0;
        private int valueType_ = 0;
        private Object unit_ = "";
        private Object description_ = "";
        private Object displayName_ = "";
        private MetricDescriptorMetadata metadata_;
        private SingleFieldBuilderV3<MetricDescriptorMetadata, MetricDescriptorMetadata.Builder, MetricDescriptorMetadataOrBuilder> metadataBuilder_;
        private int launchStage_ = 0;
        private LazyStringArrayList monitoredResourceTypes_ = LazyStringArrayList.emptyList();

        public static final Descriptors.Descriptor getDescriptor() {
            return MetricProto.internal_static_google_api_MetricDescriptor_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MetricProto.internal_static_google_api_MetricDescriptor_fieldAccessorTable.ensureFieldAccessorsInitialized(MetricDescriptor.class, Builder.class);
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
            this.name_ = "";
            this.type_ = "";
            if (this.labelsBuilder_ == null) {
                this.labels_ = Collections.emptyList();
            } else {
                this.labels_ = null;
                this.labelsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFB;
            this.metricKind_ = 0;
            this.valueType_ = 0;
            this.unit_ = "";
            this.description_ = "";
            this.displayName_ = "";
            this.metadata_ = null;
            if (this.metadataBuilder_ != null) {
                this.metadataBuilder_.dispose();
                this.metadataBuilder_ = null;
            }
            this.launchStage_ = 0;
            this.monitoredResourceTypes_ = LazyStringArrayList.emptyList();
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return MetricProto.internal_static_google_api_MetricDescriptor_descriptor;
        }

        @Override
        public MetricDescriptor getDefaultInstanceForType() {
            return MetricDescriptor.getDefaultInstance();
        }

        @Override
        public MetricDescriptor build() {
            MetricDescriptor result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public MetricDescriptor buildPartial() {
            MetricDescriptor result = new MetricDescriptor(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(MetricDescriptor result) {
            if (this.labelsBuilder_ == null) {
                if ((this.bitField0_ & 4) != 0) {
                    this.labels_ = Collections.unmodifiableList(this.labels_);
                    this.bitField0_ &= 0xFFFFFFFB;
                }
                result.labels_ = this.labels_;
            } else {
                result.labels_ = this.labelsBuilder_.build();
            }
        }

        private void buildPartial0(MetricDescriptor result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.name_ = this.name_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.type_ = this.type_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.metricKind_ = this.metricKind_;
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.valueType_ = this.valueType_;
            }
            if ((from_bitField0_ & 0x20) != 0) {
                result.unit_ = this.unit_;
            }
            if ((from_bitField0_ & 0x40) != 0) {
                result.description_ = this.description_;
            }
            if ((from_bitField0_ & 0x80) != 0) {
                result.displayName_ = this.displayName_;
            }
            if ((from_bitField0_ & 0x100) != 0) {
                result.metadata_ = this.metadataBuilder_ == null ? this.metadata_ : this.metadataBuilder_.build();
            }
            if ((from_bitField0_ & 0x200) != 0) {
                result.launchStage_ = this.launchStage_;
            }
            if ((from_bitField0_ & 0x400) != 0) {
                this.monitoredResourceTypes_.makeImmutable();
                result.monitoredResourceTypes_ = this.monitoredResourceTypes_;
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
            if (other instanceof MetricDescriptor) {
                return this.mergeFrom((MetricDescriptor)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(MetricDescriptor other) {
            if (other == MetricDescriptor.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getType().isEmpty()) {
                this.type_ = other.type_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (this.labelsBuilder_ == null) {
                if (!other.labels_.isEmpty()) {
                    if (this.labels_.isEmpty()) {
                        this.labels_ = other.labels_;
                        this.bitField0_ &= 0xFFFFFFFB;
                    } else {
                        this.ensureLabelsIsMutable();
                        this.labels_.addAll(other.labels_);
                    }
                    this.onChanged();
                }
            } else if (!other.labels_.isEmpty()) {
                if (this.labelsBuilder_.isEmpty()) {
                    this.labelsBuilder_.dispose();
                    this.labelsBuilder_ = null;
                    this.labels_ = other.labels_;
                    this.bitField0_ &= 0xFFFFFFFB;
                    this.labelsBuilder_ = alwaysUseFieldBuilders ? this.getLabelsFieldBuilder() : null;
                } else {
                    this.labelsBuilder_.addAllMessages(other.labels_);
                }
            }
            if (other.metricKind_ != 0) {
                this.setMetricKindValue(other.getMetricKindValue());
            }
            if (other.valueType_ != 0) {
                this.setValueTypeValue(other.getValueTypeValue());
            }
            if (!other.getUnit().isEmpty()) {
                this.unit_ = other.unit_;
                this.bitField0_ |= 0x20;
                this.onChanged();
            }
            if (!other.getDescription().isEmpty()) {
                this.description_ = other.description_;
                this.bitField0_ |= 0x40;
                this.onChanged();
            }
            if (!other.getDisplayName().isEmpty()) {
                this.displayName_ = other.displayName_;
                this.bitField0_ |= 0x80;
                this.onChanged();
            }
            if (other.hasMetadata()) {
                this.mergeMetadata(other.getMetadata());
            }
            if (other.launchStage_ != 0) {
                this.setLaunchStageValue(other.getLaunchStageValue());
            }
            if (!other.monitoredResourceTypes_.isEmpty()) {
                if (this.monitoredResourceTypes_.isEmpty()) {
                    this.monitoredResourceTypes_ = other.monitoredResourceTypes_;
                    this.bitField0_ |= 0x400;
                } else {
                    this.ensureMonitoredResourceTypesIsMutable();
                    this.monitoredResourceTypes_.addAll(other.monitoredResourceTypes_);
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
                block19: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block19;
                        }
                        case 10: {
                            this.name_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block19;
                        }
                        case 18: {
                            LabelDescriptor m4 = input.readMessage(LabelDescriptor.parser(), extensionRegistry);
                            if (this.labelsBuilder_ == null) {
                                this.ensureLabelsIsMutable();
                                this.labels_.add(m4);
                                continue block19;
                            }
                            this.labelsBuilder_.addMessage(m4);
                            continue block19;
                        }
                        case 24: {
                            this.metricKind_ = input.readEnum();
                            this.bitField0_ |= 8;
                            continue block19;
                        }
                        case 32: {
                            this.valueType_ = input.readEnum();
                            this.bitField0_ |= 0x10;
                            continue block19;
                        }
                        case 42: {
                            this.unit_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x20;
                            continue block19;
                        }
                        case 50: {
                            this.description_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x40;
                            continue block19;
                        }
                        case 58: {
                            this.displayName_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x80;
                            continue block19;
                        }
                        case 66: {
                            this.type_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block19;
                        }
                        case 82: {
                            input.readMessage(this.getMetadataFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x100;
                            continue block19;
                        }
                        case 96: {
                            this.launchStage_ = input.readEnum();
                            this.bitField0_ |= 0x200;
                            continue block19;
                        }
                        case 106: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensureMonitoredResourceTypesIsMutable();
                            this.monitoredResourceTypes_.add(s2);
                            continue block19;
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
        public String getName() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.name_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.name_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.name_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearName() {
            this.name_ = MetricDescriptor.getDefaultInstance().getName();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            MetricDescriptor.checkByteStringIsUtf8(value);
            this.name_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getType() {
            Object ref = this.type_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.type_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getTypeBytes() {
            Object ref = this.type_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.type_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setType(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.type_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearType() {
            this.type_ = MetricDescriptor.getDefaultInstance().getType();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setTypeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            MetricDescriptor.checkByteStringIsUtf8(value);
            this.type_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        private void ensureLabelsIsMutable() {
            if ((this.bitField0_ & 4) == 0) {
                this.labels_ = new ArrayList<LabelDescriptor>(this.labels_);
                this.bitField0_ |= 4;
            }
        }

        @Override
        public List<LabelDescriptor> getLabelsList() {
            if (this.labelsBuilder_ == null) {
                return Collections.unmodifiableList(this.labels_);
            }
            return this.labelsBuilder_.getMessageList();
        }

        @Override
        public int getLabelsCount() {
            if (this.labelsBuilder_ == null) {
                return this.labels_.size();
            }
            return this.labelsBuilder_.getCount();
        }

        @Override
        public LabelDescriptor getLabels(int index) {
            if (this.labelsBuilder_ == null) {
                return this.labels_.get(index);
            }
            return this.labelsBuilder_.getMessage(index);
        }

        public Builder setLabels(int index, LabelDescriptor value) {
            if (this.labelsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLabelsIsMutable();
                this.labels_.set(index, value);
                this.onChanged();
            } else {
                this.labelsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setLabels(int index, LabelDescriptor.Builder builderForValue) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                this.labels_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.labelsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addLabels(LabelDescriptor value) {
            if (this.labelsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLabelsIsMutable();
                this.labels_.add(value);
                this.onChanged();
            } else {
                this.labelsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addLabels(int index, LabelDescriptor value) {
            if (this.labelsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLabelsIsMutable();
                this.labels_.add(index, value);
                this.onChanged();
            } else {
                this.labelsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addLabels(LabelDescriptor.Builder builderForValue) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                this.labels_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.labelsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addLabels(int index, LabelDescriptor.Builder builderForValue) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                this.labels_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.labelsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllLabels(Iterable<? extends LabelDescriptor> values2) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.labels_);
                this.onChanged();
            } else {
                this.labelsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearLabels() {
            if (this.labelsBuilder_ == null) {
                this.labels_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFB;
                this.onChanged();
            } else {
                this.labelsBuilder_.clear();
            }
            return this;
        }

        public Builder removeLabels(int index) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                this.labels_.remove(index);
                this.onChanged();
            } else {
                this.labelsBuilder_.remove(index);
            }
            return this;
        }

        public LabelDescriptor.Builder getLabelsBuilder(int index) {
            return this.getLabelsFieldBuilder().getBuilder(index);
        }

        @Override
        public LabelDescriptorOrBuilder getLabelsOrBuilder(int index) {
            if (this.labelsBuilder_ == null) {
                return this.labels_.get(index);
            }
            return this.labelsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends LabelDescriptorOrBuilder> getLabelsOrBuilderList() {
            if (this.labelsBuilder_ != null) {
                return this.labelsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.labels_);
        }

        public LabelDescriptor.Builder addLabelsBuilder() {
            return this.getLabelsFieldBuilder().addBuilder(LabelDescriptor.getDefaultInstance());
        }

        public LabelDescriptor.Builder addLabelsBuilder(int index) {
            return this.getLabelsFieldBuilder().addBuilder(index, LabelDescriptor.getDefaultInstance());
        }

        public List<LabelDescriptor.Builder> getLabelsBuilderList() {
            return this.getLabelsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<LabelDescriptor, LabelDescriptor.Builder, LabelDescriptorOrBuilder> getLabelsFieldBuilder() {
            if (this.labelsBuilder_ == null) {
                this.labelsBuilder_ = new RepeatedFieldBuilderV3(this.labels_, (this.bitField0_ & 4) != 0, this.getParentForChildren(), this.isClean());
                this.labels_ = null;
            }
            return this.labelsBuilder_;
        }

        @Override
        public int getMetricKindValue() {
            return this.metricKind_;
        }

        public Builder setMetricKindValue(int value) {
            this.metricKind_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        @Override
        public MetricKind getMetricKind() {
            MetricKind result = MetricKind.forNumber(this.metricKind_);
            return result == null ? MetricKind.UNRECOGNIZED : result;
        }

        public Builder setMetricKind(MetricKind value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.bitField0_ |= 8;
            this.metricKind_ = value.getNumber();
            this.onChanged();
            return this;
        }

        public Builder clearMetricKind() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.metricKind_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public int getValueTypeValue() {
            return this.valueType_;
        }

        public Builder setValueTypeValue(int value) {
            this.valueType_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        @Override
        public ValueType getValueType() {
            ValueType result = ValueType.forNumber(this.valueType_);
            return result == null ? ValueType.UNRECOGNIZED : result;
        }

        public Builder setValueType(ValueType value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.bitField0_ |= 0x10;
            this.valueType_ = value.getNumber();
            this.onChanged();
            return this;
        }

        public Builder clearValueType() {
            this.bitField0_ &= 0xFFFFFFEF;
            this.valueType_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public String getUnit() {
            Object ref = this.unit_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.unit_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getUnitBytes() {
            Object ref = this.unit_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.unit_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setUnit(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.unit_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder clearUnit() {
            this.unit_ = MetricDescriptor.getDefaultInstance().getUnit();
            this.bitField0_ &= 0xFFFFFFDF;
            this.onChanged();
            return this;
        }

        public Builder setUnitBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            MetricDescriptor.checkByteStringIsUtf8(value);
            this.unit_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        @Override
        public String getDescription() {
            Object ref = this.description_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.description_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getDescriptionBytes() {
            Object ref = this.description_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.description_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setDescription(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.description_ = value;
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder clearDescription() {
            this.description_ = MetricDescriptor.getDefaultInstance().getDescription();
            this.bitField0_ &= 0xFFFFFFBF;
            this.onChanged();
            return this;
        }

        public Builder setDescriptionBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            MetricDescriptor.checkByteStringIsUtf8(value);
            this.description_ = value;
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        @Override
        public String getDisplayName() {
            Object ref = this.displayName_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.displayName_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getDisplayNameBytes() {
            Object ref = this.displayName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.displayName_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setDisplayName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.displayName_ = value;
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        public Builder clearDisplayName() {
            this.displayName_ = MetricDescriptor.getDefaultInstance().getDisplayName();
            this.bitField0_ &= 0xFFFFFF7F;
            this.onChanged();
            return this;
        }

        public Builder setDisplayNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            MetricDescriptor.checkByteStringIsUtf8(value);
            this.displayName_ = value;
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasMetadata() {
            return (this.bitField0_ & 0x100) != 0;
        }

        @Override
        public MetricDescriptorMetadata getMetadata() {
            if (this.metadataBuilder_ == null) {
                return this.metadata_ == null ? MetricDescriptorMetadata.getDefaultInstance() : this.metadata_;
            }
            return this.metadataBuilder_.getMessage();
        }

        public Builder setMetadata(MetricDescriptorMetadata value) {
            if (this.metadataBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.metadata_ = value;
            } else {
                this.metadataBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder setMetadata(MetricDescriptorMetadata.Builder builderForValue) {
            if (this.metadataBuilder_ == null) {
                this.metadata_ = builderForValue.build();
            } else {
                this.metadataBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder mergeMetadata(MetricDescriptorMetadata value) {
            if (this.metadataBuilder_ == null) {
                if ((this.bitField0_ & 0x100) != 0 && this.metadata_ != null && this.metadata_ != MetricDescriptorMetadata.getDefaultInstance()) {
                    this.getMetadataBuilder().mergeFrom(value);
                } else {
                    this.metadata_ = value;
                }
            } else {
                this.metadataBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder clearMetadata() {
            this.bitField0_ &= 0xFFFFFEFF;
            this.metadata_ = null;
            if (this.metadataBuilder_ != null) {
                this.metadataBuilder_.dispose();
                this.metadataBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public MetricDescriptorMetadata.Builder getMetadataBuilder() {
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this.getMetadataFieldBuilder().getBuilder();
        }

        @Override
        public MetricDescriptorMetadataOrBuilder getMetadataOrBuilder() {
            if (this.metadataBuilder_ != null) {
                return this.metadataBuilder_.getMessageOrBuilder();
            }
            return this.metadata_ == null ? MetricDescriptorMetadata.getDefaultInstance() : this.metadata_;
        }

        private SingleFieldBuilderV3<MetricDescriptorMetadata, MetricDescriptorMetadata.Builder, MetricDescriptorMetadataOrBuilder> getMetadataFieldBuilder() {
            if (this.metadataBuilder_ == null) {
                this.metadataBuilder_ = new SingleFieldBuilderV3(this.getMetadata(), this.getParentForChildren(), this.isClean());
                this.metadata_ = null;
            }
            return this.metadataBuilder_;
        }

        @Override
        public int getLaunchStageValue() {
            return this.launchStage_;
        }

        public Builder setLaunchStageValue(int value) {
            this.launchStage_ = value;
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        @Override
        public LaunchStage getLaunchStage() {
            LaunchStage result = LaunchStage.forNumber(this.launchStage_);
            return result == null ? LaunchStage.UNRECOGNIZED : result;
        }

        public Builder setLaunchStage(LaunchStage value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.bitField0_ |= 0x200;
            this.launchStage_ = value.getNumber();
            this.onChanged();
            return this;
        }

        public Builder clearLaunchStage() {
            this.bitField0_ &= 0xFFFFFDFF;
            this.launchStage_ = 0;
            this.onChanged();
            return this;
        }

        private void ensureMonitoredResourceTypesIsMutable() {
            if (!this.monitoredResourceTypes_.isModifiable()) {
                this.monitoredResourceTypes_ = new LazyStringArrayList(this.monitoredResourceTypes_);
            }
            this.bitField0_ |= 0x400;
        }

        public ProtocolStringList getMonitoredResourceTypesList() {
            this.monitoredResourceTypes_.makeImmutable();
            return this.monitoredResourceTypes_;
        }

        @Override
        public int getMonitoredResourceTypesCount() {
            return this.monitoredResourceTypes_.size();
        }

        @Override
        public String getMonitoredResourceTypes(int index) {
            return this.monitoredResourceTypes_.get(index);
        }

        @Override
        public ByteString getMonitoredResourceTypesBytes(int index) {
            return this.monitoredResourceTypes_.getByteString(index);
        }

        public Builder setMonitoredResourceTypes(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureMonitoredResourceTypesIsMutable();
            this.monitoredResourceTypes_.set(index, value);
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this;
        }

        public Builder addMonitoredResourceTypes(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureMonitoredResourceTypesIsMutable();
            this.monitoredResourceTypes_.add(value);
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this;
        }

        public Builder addAllMonitoredResourceTypes(Iterable<String> values2) {
            this.ensureMonitoredResourceTypesIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.monitoredResourceTypes_);
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this;
        }

        public Builder clearMonitoredResourceTypes() {
            this.monitoredResourceTypes_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFBFF;
            this.onChanged();
            return this;
        }

        public Builder addMonitoredResourceTypesBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            MetricDescriptor.checkByteStringIsUtf8(value);
            this.ensureMonitoredResourceTypesIsMutable();
            this.monitoredResourceTypes_.add(value);
            this.bitField0_ |= 0x400;
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

    public static enum MetricKind implements ProtocolMessageEnum
    {
        METRIC_KIND_UNSPECIFIED(0),
        GAUGE(1),
        DELTA(2),
        CUMULATIVE(3),
        UNRECOGNIZED(-1);

        public static final int METRIC_KIND_UNSPECIFIED_VALUE = 0;
        public static final int GAUGE_VALUE = 1;
        public static final int DELTA_VALUE = 2;
        public static final int CUMULATIVE_VALUE = 3;
        private static final Internal.EnumLiteMap<MetricKind> internalValueMap;
        private static final MetricKind[] VALUES;
        private final int value;

        @Override
        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }
            return this.value;
        }

        @Deprecated
        public static MetricKind valueOf(int value) {
            return MetricKind.forNumber(value);
        }

        public static MetricKind forNumber(int value) {
            switch (value) {
                case 0: {
                    return METRIC_KIND_UNSPECIFIED;
                }
                case 1: {
                    return GAUGE;
                }
                case 2: {
                    return DELTA;
                }
                case 3: {
                    return CUMULATIVE;
                }
            }
            return null;
        }

        public static Internal.EnumLiteMap<MetricKind> internalGetValueMap() {
            return internalValueMap;
        }

        @Override
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            if (this == UNRECOGNIZED) {
                throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
            }
            return MetricKind.getDescriptor().getValues().get(this.ordinal());
        }

        @Override
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return MetricKind.getDescriptor();
        }

        public static final Descriptors.EnumDescriptor getDescriptor() {
            return MetricDescriptor.getDescriptor().getEnumTypes().get(0);
        }

        public static MetricKind valueOf(Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != MetricKind.getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            if (desc.getIndex() == -1) {
                return UNRECOGNIZED;
            }
            return VALUES[desc.getIndex()];
        }

        private MetricKind(int value) {
            this.value = value;
        }

        static {
            internalValueMap = new Internal.EnumLiteMap<MetricKind>(){

                @Override
                public MetricKind findValueByNumber(int number) {
                    return MetricKind.forNumber(number);
                }
            };
            VALUES = MetricKind.values();
        }
    }

    public static enum ValueType implements ProtocolMessageEnum
    {
        VALUE_TYPE_UNSPECIFIED(0),
        BOOL(1),
        INT64(2),
        DOUBLE(3),
        STRING(4),
        DISTRIBUTION(5),
        MONEY(6),
        UNRECOGNIZED(-1);

        public static final int VALUE_TYPE_UNSPECIFIED_VALUE = 0;
        public static final int BOOL_VALUE = 1;
        public static final int INT64_VALUE = 2;
        public static final int DOUBLE_VALUE = 3;
        public static final int STRING_VALUE = 4;
        public static final int DISTRIBUTION_VALUE = 5;
        public static final int MONEY_VALUE = 6;
        private static final Internal.EnumLiteMap<ValueType> internalValueMap;
        private static final ValueType[] VALUES;
        private final int value;

        @Override
        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }
            return this.value;
        }

        @Deprecated
        public static ValueType valueOf(int value) {
            return ValueType.forNumber(value);
        }

        public static ValueType forNumber(int value) {
            switch (value) {
                case 0: {
                    return VALUE_TYPE_UNSPECIFIED;
                }
                case 1: {
                    return BOOL;
                }
                case 2: {
                    return INT64;
                }
                case 3: {
                    return DOUBLE;
                }
                case 4: {
                    return STRING;
                }
                case 5: {
                    return DISTRIBUTION;
                }
                case 6: {
                    return MONEY;
                }
            }
            return null;
        }

        public static Internal.EnumLiteMap<ValueType> internalGetValueMap() {
            return internalValueMap;
        }

        @Override
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            if (this == UNRECOGNIZED) {
                throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
            }
            return ValueType.getDescriptor().getValues().get(this.ordinal());
        }

        @Override
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return ValueType.getDescriptor();
        }

        public static final Descriptors.EnumDescriptor getDescriptor() {
            return MetricDescriptor.getDescriptor().getEnumTypes().get(1);
        }

        public static ValueType valueOf(Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != ValueType.getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            if (desc.getIndex() == -1) {
                return UNRECOGNIZED;
            }
            return VALUES[desc.getIndex()];
        }

        private ValueType(int value) {
            this.value = value;
        }

        static {
            internalValueMap = new Internal.EnumLiteMap<ValueType>(){

                @Override
                public ValueType findValueByNumber(int number) {
                    return ValueType.forNumber(number);
                }
            };
            VALUES = ValueType.values();
        }
    }

    public static interface MetricDescriptorMetadataOrBuilder
    extends MessageOrBuilder {
        @Deprecated
        public int getLaunchStageValue();

        @Deprecated
        public LaunchStage getLaunchStage();

        public boolean hasSamplePeriod();

        public Duration getSamplePeriod();

        public DurationOrBuilder getSamplePeriodOrBuilder();

        public boolean hasIngestDelay();

        public Duration getIngestDelay();

        public DurationOrBuilder getIngestDelayOrBuilder();
    }
}

