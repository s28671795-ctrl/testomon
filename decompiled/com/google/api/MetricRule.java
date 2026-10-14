/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.MetricRuleOrBuilder;
import com.google.api.QuotaProto;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
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

public final class MetricRule
extends GeneratedMessageV3
implements MetricRuleOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int SELECTOR_FIELD_NUMBER = 1;
    private volatile Object selector_ = "";
    public static final int METRIC_COSTS_FIELD_NUMBER = 2;
    private MapField<String, Long> metricCosts_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final MetricRule DEFAULT_INSTANCE = new MetricRule();
    private static final Parser<MetricRule> PARSER = new AbstractParser<MetricRule>(){

        @Override
        public MetricRule parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = MetricRule.newBuilder();
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

    private MetricRule(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private MetricRule() {
        this.selector_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new MetricRule();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return QuotaProto.internal_static_google_api_MetricRule_descriptor;
    }

    @Override
    protected MapField internalGetMapField(int number) {
        switch (number) {
            case 2: {
                return this.internalGetMetricCosts();
            }
        }
        throw new RuntimeException("Invalid map field number: " + number);
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return QuotaProto.internal_static_google_api_MetricRule_fieldAccessorTable.ensureFieldAccessorsInitialized(MetricRule.class, Builder.class);
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

    private MapField<String, Long> internalGetMetricCosts() {
        if (this.metricCosts_ == null) {
            return MapField.emptyMapField(MetricCostsDefaultEntryHolder.defaultEntry);
        }
        return this.metricCosts_;
    }

    @Override
    public int getMetricCostsCount() {
        return this.internalGetMetricCosts().getMap().size();
    }

    @Override
    public boolean containsMetricCosts(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        return this.internalGetMetricCosts().getMap().containsKey(key);
    }

    @Override
    @Deprecated
    public Map<String, Long> getMetricCosts() {
        return this.getMetricCostsMap();
    }

    @Override
    public Map<String, Long> getMetricCostsMap() {
        return this.internalGetMetricCosts().getMap();
    }

    @Override
    public long getMetricCostsOrDefault(String key, long defaultValue) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, Long> map = this.internalGetMetricCosts().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    @Override
    public long getMetricCostsOrThrow(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, Long> map = this.internalGetMetricCosts().getMap();
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return map.get(key);
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
        GeneratedMessageV3.serializeStringMapTo(output, this.internalGetMetricCosts(), MetricCostsDefaultEntryHolder.defaultEntry, 2);
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
        for (Map.Entry<String, Long> entry : this.internalGetMetricCosts().getMap().entrySet()) {
            Message metricCosts__ = ((MapEntry.Builder)MetricCostsDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
            size += CodedOutputStream.computeMessageSize(2, metricCosts__);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MetricRule)) {
            return super.equals(obj);
        }
        MetricRule other = (MetricRule)obj;
        if (!this.getSelector().equals(other.getSelector())) {
            return false;
        }
        if (!this.internalGetMetricCosts().equals(other.internalGetMetricCosts())) {
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
        hash = 19 * hash + MetricRule.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getSelector().hashCode();
        if (!this.internalGetMetricCosts().getMap().isEmpty()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.internalGetMetricCosts().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static MetricRule parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MetricRule parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MetricRule parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MetricRule parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MetricRule parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MetricRule parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MetricRule parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static MetricRule parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static MetricRule parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static MetricRule parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static MetricRule parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static MetricRule parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return MetricRule.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(MetricRule prototype) {
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

    public static MetricRule getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<MetricRule> parser() {
        return PARSER;
    }

    public Parser<MetricRule> getParserForType() {
        return PARSER;
    }

    @Override
    public MetricRule getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements MetricRuleOrBuilder {
        private int bitField0_;
        private Object selector_ = "";
        private MapField<String, Long> metricCosts_;

        public static final Descriptors.Descriptor getDescriptor() {
            return QuotaProto.internal_static_google_api_MetricRule_descriptor;
        }

        @Override
        protected MapField internalGetMapField(int number) {
            switch (number) {
                case 2: {
                    return this.internalGetMetricCosts();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected MapField internalGetMutableMapField(int number) {
            switch (number) {
                case 2: {
                    return this.internalGetMutableMetricCosts();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return QuotaProto.internal_static_google_api_MetricRule_fieldAccessorTable.ensureFieldAccessorsInitialized(MetricRule.class, Builder.class);
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
            this.internalGetMutableMetricCosts().clear();
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return QuotaProto.internal_static_google_api_MetricRule_descriptor;
        }

        @Override
        public MetricRule getDefaultInstanceForType() {
            return MetricRule.getDefaultInstance();
        }

        @Override
        public MetricRule build() {
            MetricRule result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public MetricRule buildPartial() {
            MetricRule result = new MetricRule(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(MetricRule result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.selector_ = this.selector_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.metricCosts_ = this.internalGetMetricCosts();
                result.metricCosts_.makeImmutable();
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
            if (other instanceof MetricRule) {
                return this.mergeFrom((MetricRule)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(MetricRule other) {
            if (other == MetricRule.getDefaultInstance()) {
                return this;
            }
            if (!other.getSelector().isEmpty()) {
                this.selector_ = other.selector_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            this.internalGetMutableMetricCosts().mergeFrom(other.internalGetMetricCosts());
            this.bitField0_ |= 2;
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
                            MapEntry<String, Long> metricCosts__ = input.readMessage(MetricCostsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                            this.internalGetMutableMetricCosts().getMutableMap().put(metricCosts__.getKey(), metricCosts__.getValue());
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
            this.selector_ = MetricRule.getDefaultInstance().getSelector();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setSelectorBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            MetricRule.checkByteStringIsUtf8(value);
            this.selector_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        private MapField<String, Long> internalGetMetricCosts() {
            if (this.metricCosts_ == null) {
                return MapField.emptyMapField(MetricCostsDefaultEntryHolder.defaultEntry);
            }
            return this.metricCosts_;
        }

        private MapField<String, Long> internalGetMutableMetricCosts() {
            if (this.metricCosts_ == null) {
                this.metricCosts_ = MapField.newMapField(MetricCostsDefaultEntryHolder.defaultEntry);
            }
            if (!this.metricCosts_.isMutable()) {
                this.metricCosts_ = this.metricCosts_.copy();
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this.metricCosts_;
        }

        @Override
        public int getMetricCostsCount() {
            return this.internalGetMetricCosts().getMap().size();
        }

        @Override
        public boolean containsMetricCosts(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetMetricCosts().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, Long> getMetricCosts() {
            return this.getMetricCostsMap();
        }

        @Override
        public Map<String, Long> getMetricCostsMap() {
            return this.internalGetMetricCosts().getMap();
        }

        @Override
        public long getMetricCostsOrDefault(String key, long defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, Long> map = this.internalGetMetricCosts().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public long getMetricCostsOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, Long> map = this.internalGetMetricCosts().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        public Builder clearMetricCosts() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.internalGetMutableMetricCosts().getMutableMap().clear();
            return this;
        }

        public Builder removeMetricCosts(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            this.internalGetMutableMetricCosts().getMutableMap().remove(key);
            return this;
        }

        @Deprecated
        public Map<String, Long> getMutableMetricCosts() {
            this.bitField0_ |= 2;
            return this.internalGetMutableMetricCosts().getMutableMap();
        }

        public Builder putMetricCosts(String key, long value) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            this.internalGetMutableMetricCosts().getMutableMap().put(key, value);
            this.bitField0_ |= 2;
            return this;
        }

        public Builder putAllMetricCosts(Map<String, Long> values2) {
            this.internalGetMutableMetricCosts().getMutableMap().putAll(values2);
            this.bitField0_ |= 2;
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

    private static final class MetricCostsDefaultEntryHolder {
        static final MapEntry<String, Long> defaultEntry = MapEntry.newDefaultInstance(QuotaProto.internal_static_google_api_MetricRule_MetricCostsEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.INT64, 0L);

        private MetricCostsDefaultEntryHolder() {
        }
    }
}

