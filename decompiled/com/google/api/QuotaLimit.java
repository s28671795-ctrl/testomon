/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.QuotaLimitOrBuilder;
import com.google.api.QuotaProto;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntry;
import com.google.protobuf.MapField;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

public final class QuotaLimit
extends GeneratedMessageV3
implements QuotaLimitOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 6;
    private volatile Object name_ = "";
    public static final int DESCRIPTION_FIELD_NUMBER = 2;
    private volatile Object description_ = "";
    public static final int DEFAULT_LIMIT_FIELD_NUMBER = 3;
    private long defaultLimit_ = 0L;
    public static final int MAX_LIMIT_FIELD_NUMBER = 4;
    private long maxLimit_ = 0L;
    public static final int FREE_TIER_FIELD_NUMBER = 7;
    private long freeTier_ = 0L;
    public static final int DURATION_FIELD_NUMBER = 5;
    private volatile Object duration_ = "";
    public static final int METRIC_FIELD_NUMBER = 8;
    private volatile Object metric_ = "";
    public static final int UNIT_FIELD_NUMBER = 9;
    private volatile Object unit_ = "";
    public static final int VALUES_FIELD_NUMBER = 10;
    private MapField<String, Long> values_;
    public static final int DISPLAY_NAME_FIELD_NUMBER = 12;
    private volatile Object displayName_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final QuotaLimit DEFAULT_INSTANCE = new QuotaLimit();
    private static final Parser<QuotaLimit> PARSER = new AbstractParser<QuotaLimit>(){

        @Override
        public QuotaLimit parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = QuotaLimit.newBuilder();
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

    private QuotaLimit(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private QuotaLimit() {
        this.name_ = "";
        this.description_ = "";
        this.duration_ = "";
        this.metric_ = "";
        this.unit_ = "";
        this.displayName_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new QuotaLimit();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return QuotaProto.internal_static_google_api_QuotaLimit_descriptor;
    }

    @Override
    protected MapField internalGetMapField(int number) {
        switch (number) {
            case 10: {
                return this.internalGetValues();
            }
        }
        throw new RuntimeException("Invalid map field number: " + number);
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return QuotaProto.internal_static_google_api_QuotaLimit_fieldAccessorTable.ensureFieldAccessorsInitialized(QuotaLimit.class, Builder.class);
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
    public long getDefaultLimit() {
        return this.defaultLimit_;
    }

    @Override
    public long getMaxLimit() {
        return this.maxLimit_;
    }

    @Override
    public long getFreeTier() {
        return this.freeTier_;
    }

    @Override
    public String getDuration() {
        Object ref = this.duration_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.duration_ = s2;
        return s2;
    }

    @Override
    public ByteString getDurationBytes() {
        Object ref = this.duration_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.duration_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getMetric() {
        Object ref = this.metric_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.metric_ = s2;
        return s2;
    }

    @Override
    public ByteString getMetricBytes() {
        Object ref = this.metric_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.metric_ = b;
            return b;
        }
        return (ByteString)ref;
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

    private MapField<String, Long> internalGetValues() {
        if (this.values_ == null) {
            return MapField.emptyMapField(ValuesDefaultEntryHolder.defaultEntry);
        }
        return this.values_;
    }

    @Override
    public int getValuesCount() {
        return this.internalGetValues().getMap().size();
    }

    @Override
    public boolean containsValues(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        return this.internalGetValues().getMap().containsKey(key);
    }

    @Override
    @Deprecated
    public Map<String, Long> getValues() {
        return this.getValuesMap();
    }

    @Override
    public Map<String, Long> getValuesMap() {
        return this.internalGetValues().getMap();
    }

    @Override
    public long getValuesOrDefault(String key, long defaultValue) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, Long> map = this.internalGetValues().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    @Override
    public long getValuesOrThrow(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, Long> map = this.internalGetValues().getMap();
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return map.get(key);
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
        if (!GeneratedMessageV3.isStringEmpty(this.description_)) {
            GeneratedMessageV3.writeString(output, 2, this.description_);
        }
        if (this.defaultLimit_ != 0L) {
            output.writeInt64(3, this.defaultLimit_);
        }
        if (this.maxLimit_ != 0L) {
            output.writeInt64(4, this.maxLimit_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.duration_)) {
            GeneratedMessageV3.writeString(output, 5, this.duration_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            GeneratedMessageV3.writeString(output, 6, this.name_);
        }
        if (this.freeTier_ != 0L) {
            output.writeInt64(7, this.freeTier_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.metric_)) {
            GeneratedMessageV3.writeString(output, 8, this.metric_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.unit_)) {
            GeneratedMessageV3.writeString(output, 9, this.unit_);
        }
        GeneratedMessageV3.serializeStringMapTo(output, this.internalGetValues(), ValuesDefaultEntryHolder.defaultEntry, 10);
        if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
            GeneratedMessageV3.writeString(output, 12, this.displayName_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.description_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.description_);
        }
        if (this.defaultLimit_ != 0L) {
            size += CodedOutputStream.computeInt64Size(3, this.defaultLimit_);
        }
        if (this.maxLimit_ != 0L) {
            size += CodedOutputStream.computeInt64Size(4, this.maxLimit_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.duration_)) {
            size += GeneratedMessageV3.computeStringSize(5, this.duration_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            size += GeneratedMessageV3.computeStringSize(6, this.name_);
        }
        if (this.freeTier_ != 0L) {
            size += CodedOutputStream.computeInt64Size(7, this.freeTier_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.metric_)) {
            size += GeneratedMessageV3.computeStringSize(8, this.metric_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.unit_)) {
            size += GeneratedMessageV3.computeStringSize(9, this.unit_);
        }
        for (Map.Entry<String, Long> entry : this.internalGetValues().getMap().entrySet()) {
            Message values__ = ((MapEntry.Builder)ValuesDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
            size += CodedOutputStream.computeMessageSize(10, values__);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
            size += GeneratedMessageV3.computeStringSize(12, this.displayName_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof QuotaLimit)) {
            return super.equals(obj);
        }
        QuotaLimit other = (QuotaLimit)obj;
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (!this.getDescription().equals(other.getDescription())) {
            return false;
        }
        if (this.getDefaultLimit() != other.getDefaultLimit()) {
            return false;
        }
        if (this.getMaxLimit() != other.getMaxLimit()) {
            return false;
        }
        if (this.getFreeTier() != other.getFreeTier()) {
            return false;
        }
        if (!this.getDuration().equals(other.getDuration())) {
            return false;
        }
        if (!this.getMetric().equals(other.getMetric())) {
            return false;
        }
        if (!this.getUnit().equals(other.getUnit())) {
            return false;
        }
        if (!this.internalGetValues().equals(other.internalGetValues())) {
            return false;
        }
        if (!this.getDisplayName().equals(other.getDisplayName())) {
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
        hash = 19 * hash + QuotaLimit.getDescriptor().hashCode();
        hash = 37 * hash + 6;
        hash = 53 * hash + this.getName().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getDescription().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + Internal.hashLong(this.getDefaultLimit());
        hash = 37 * hash + 4;
        hash = 53 * hash + Internal.hashLong(this.getMaxLimit());
        hash = 37 * hash + 7;
        hash = 53 * hash + Internal.hashLong(this.getFreeTier());
        hash = 37 * hash + 5;
        hash = 53 * hash + this.getDuration().hashCode();
        hash = 37 * hash + 8;
        hash = 53 * hash + this.getMetric().hashCode();
        hash = 37 * hash + 9;
        hash = 53 * hash + this.getUnit().hashCode();
        if (!this.internalGetValues().getMap().isEmpty()) {
            hash = 37 * hash + 10;
            hash = 53 * hash + this.internalGetValues().hashCode();
        }
        hash = 37 * hash + 12;
        hash = 53 * hash + this.getDisplayName().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static QuotaLimit parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static QuotaLimit parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static QuotaLimit parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static QuotaLimit parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static QuotaLimit parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static QuotaLimit parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static QuotaLimit parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static QuotaLimit parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static QuotaLimit parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static QuotaLimit parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static QuotaLimit parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static QuotaLimit parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return QuotaLimit.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(QuotaLimit prototype) {
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

    public static QuotaLimit getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<QuotaLimit> parser() {
        return PARSER;
    }

    public Parser<QuotaLimit> getParserForType() {
        return PARSER;
    }

    @Override
    public QuotaLimit getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements QuotaLimitOrBuilder {
        private int bitField0_;
        private Object name_ = "";
        private Object description_ = "";
        private long defaultLimit_;
        private long maxLimit_;
        private long freeTier_;
        private Object duration_ = "";
        private Object metric_ = "";
        private Object unit_ = "";
        private MapField<String, Long> values_;
        private Object displayName_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return QuotaProto.internal_static_google_api_QuotaLimit_descriptor;
        }

        @Override
        protected MapField internalGetMapField(int number) {
            switch (number) {
                case 10: {
                    return this.internalGetValues();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected MapField internalGetMutableMapField(int number) {
            switch (number) {
                case 10: {
                    return this.internalGetMutableValues();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return QuotaProto.internal_static_google_api_QuotaLimit_fieldAccessorTable.ensureFieldAccessorsInitialized(QuotaLimit.class, Builder.class);
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
            this.description_ = "";
            this.defaultLimit_ = 0L;
            this.maxLimit_ = 0L;
            this.freeTier_ = 0L;
            this.duration_ = "";
            this.metric_ = "";
            this.unit_ = "";
            this.internalGetMutableValues().clear();
            this.displayName_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return QuotaProto.internal_static_google_api_QuotaLimit_descriptor;
        }

        @Override
        public QuotaLimit getDefaultInstanceForType() {
            return QuotaLimit.getDefaultInstance();
        }

        @Override
        public QuotaLimit build() {
            QuotaLimit result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public QuotaLimit buildPartial() {
            QuotaLimit result = new QuotaLimit(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(QuotaLimit result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.name_ = this.name_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.description_ = this.description_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.defaultLimit_ = this.defaultLimit_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.maxLimit_ = this.maxLimit_;
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.freeTier_ = this.freeTier_;
            }
            if ((from_bitField0_ & 0x20) != 0) {
                result.duration_ = this.duration_;
            }
            if ((from_bitField0_ & 0x40) != 0) {
                result.metric_ = this.metric_;
            }
            if ((from_bitField0_ & 0x80) != 0) {
                result.unit_ = this.unit_;
            }
            if ((from_bitField0_ & 0x100) != 0) {
                result.values_ = this.internalGetValues();
                result.values_.makeImmutable();
            }
            if ((from_bitField0_ & 0x200) != 0) {
                result.displayName_ = this.displayName_;
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
            if (other instanceof QuotaLimit) {
                return this.mergeFrom((QuotaLimit)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(QuotaLimit other) {
            if (other == QuotaLimit.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getDescription().isEmpty()) {
                this.description_ = other.description_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (other.getDefaultLimit() != 0L) {
                this.setDefaultLimit(other.getDefaultLimit());
            }
            if (other.getMaxLimit() != 0L) {
                this.setMaxLimit(other.getMaxLimit());
            }
            if (other.getFreeTier() != 0L) {
                this.setFreeTier(other.getFreeTier());
            }
            if (!other.getDuration().isEmpty()) {
                this.duration_ = other.duration_;
                this.bitField0_ |= 0x20;
                this.onChanged();
            }
            if (!other.getMetric().isEmpty()) {
                this.metric_ = other.metric_;
                this.bitField0_ |= 0x40;
                this.onChanged();
            }
            if (!other.getUnit().isEmpty()) {
                this.unit_ = other.unit_;
                this.bitField0_ |= 0x80;
                this.onChanged();
            }
            this.internalGetMutableValues().mergeFrom(other.internalGetValues());
            this.bitField0_ |= 0x100;
            if (!other.getDisplayName().isEmpty()) {
                this.displayName_ = other.displayName_;
                this.bitField0_ |= 0x200;
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
                block18: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block18;
                        }
                        case 18: {
                            this.description_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block18;
                        }
                        case 24: {
                            this.defaultLimit_ = input.readInt64();
                            this.bitField0_ |= 4;
                            continue block18;
                        }
                        case 32: {
                            this.maxLimit_ = input.readInt64();
                            this.bitField0_ |= 8;
                            continue block18;
                        }
                        case 42: {
                            this.duration_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x20;
                            continue block18;
                        }
                        case 50: {
                            this.name_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block18;
                        }
                        case 56: {
                            this.freeTier_ = input.readInt64();
                            this.bitField0_ |= 0x10;
                            continue block18;
                        }
                        case 66: {
                            this.metric_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x40;
                            continue block18;
                        }
                        case 74: {
                            this.unit_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x80;
                            continue block18;
                        }
                        case 82: {
                            MapEntry<String, Long> values__ = input.readMessage(ValuesDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                            this.internalGetMutableValues().getMutableMap().put(values__.getKey(), values__.getValue());
                            this.bitField0_ |= 0x100;
                            continue block18;
                        }
                        case 98: {
                            this.displayName_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x200;
                            continue block18;
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
            this.name_ = QuotaLimit.getDefaultInstance().getName();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            QuotaLimit.checkByteStringIsUtf8(value);
            this.name_ = value;
            this.bitField0_ |= 1;
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
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearDescription() {
            this.description_ = QuotaLimit.getDefaultInstance().getDescription();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setDescriptionBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            QuotaLimit.checkByteStringIsUtf8(value);
            this.description_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public long getDefaultLimit() {
            return this.defaultLimit_;
        }

        public Builder setDefaultLimit(long value) {
            this.defaultLimit_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearDefaultLimit() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.defaultLimit_ = 0L;
            this.onChanged();
            return this;
        }

        @Override
        public long getMaxLimit() {
            return this.maxLimit_;
        }

        public Builder setMaxLimit(long value) {
            this.maxLimit_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearMaxLimit() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.maxLimit_ = 0L;
            this.onChanged();
            return this;
        }

        @Override
        public long getFreeTier() {
            return this.freeTier_;
        }

        public Builder setFreeTier(long value) {
            this.freeTier_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearFreeTier() {
            this.bitField0_ &= 0xFFFFFFEF;
            this.freeTier_ = 0L;
            this.onChanged();
            return this;
        }

        @Override
        public String getDuration() {
            Object ref = this.duration_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.duration_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getDurationBytes() {
            Object ref = this.duration_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.duration_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setDuration(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.duration_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder clearDuration() {
            this.duration_ = QuotaLimit.getDefaultInstance().getDuration();
            this.bitField0_ &= 0xFFFFFFDF;
            this.onChanged();
            return this;
        }

        public Builder setDurationBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            QuotaLimit.checkByteStringIsUtf8(value);
            this.duration_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        @Override
        public String getMetric() {
            Object ref = this.metric_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.metric_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getMetricBytes() {
            Object ref = this.metric_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.metric_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setMetric(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.metric_ = value;
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder clearMetric() {
            this.metric_ = QuotaLimit.getDefaultInstance().getMetric();
            this.bitField0_ &= 0xFFFFFFBF;
            this.onChanged();
            return this;
        }

        public Builder setMetricBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            QuotaLimit.checkByteStringIsUtf8(value);
            this.metric_ = value;
            this.bitField0_ |= 0x40;
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
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        public Builder clearUnit() {
            this.unit_ = QuotaLimit.getDefaultInstance().getUnit();
            this.bitField0_ &= 0xFFFFFF7F;
            this.onChanged();
            return this;
        }

        public Builder setUnitBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            QuotaLimit.checkByteStringIsUtf8(value);
            this.unit_ = value;
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        private MapField<String, Long> internalGetValues() {
            if (this.values_ == null) {
                return MapField.emptyMapField(ValuesDefaultEntryHolder.defaultEntry);
            }
            return this.values_;
        }

        private MapField<String, Long> internalGetMutableValues() {
            if (this.values_ == null) {
                this.values_ = MapField.newMapField(ValuesDefaultEntryHolder.defaultEntry);
            }
            if (!this.values_.isMutable()) {
                this.values_ = this.values_.copy();
            }
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this.values_;
        }

        @Override
        public int getValuesCount() {
            return this.internalGetValues().getMap().size();
        }

        @Override
        public boolean containsValues(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetValues().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, Long> getValues() {
            return this.getValuesMap();
        }

        @Override
        public Map<String, Long> getValuesMap() {
            return this.internalGetValues().getMap();
        }

        @Override
        public long getValuesOrDefault(String key, long defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, Long> map = this.internalGetValues().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public long getValuesOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, Long> map = this.internalGetValues().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        public Builder clearValues() {
            this.bitField0_ &= 0xFFFFFEFF;
            this.internalGetMutableValues().getMutableMap().clear();
            return this;
        }

        public Builder removeValues(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            this.internalGetMutableValues().getMutableMap().remove(key);
            return this;
        }

        @Deprecated
        public Map<String, Long> getMutableValues() {
            this.bitField0_ |= 0x100;
            return this.internalGetMutableValues().getMutableMap();
        }

        public Builder putValues(String key, long value) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            this.internalGetMutableValues().getMutableMap().put(key, value);
            this.bitField0_ |= 0x100;
            return this;
        }

        public Builder putAllValues(Map<String, Long> values2) {
            this.internalGetMutableValues().getMutableMap().putAll(values2);
            this.bitField0_ |= 0x100;
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
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder clearDisplayName() {
            this.displayName_ = QuotaLimit.getDefaultInstance().getDisplayName();
            this.bitField0_ &= 0xFFFFFDFF;
            this.onChanged();
            return this;
        }

        public Builder setDisplayNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            QuotaLimit.checkByteStringIsUtf8(value);
            this.displayName_ = value;
            this.bitField0_ |= 0x200;
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

    private static final class ValuesDefaultEntryHolder {
        static final MapEntry<String, Long> defaultEntry = MapEntry.newDefaultInstance(QuotaProto.internal_static_google_api_QuotaLimit_ValuesEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.INT64, 0L);

        private ValuesDefaultEntryHolder() {
        }
    }
}

