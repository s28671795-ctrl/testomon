/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.MonitoredResourceOrBuilder;
import com.google.api.MonitoredResourceProto;
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

public final class MonitoredResource
extends GeneratedMessageV3
implements MonitoredResourceOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int TYPE_FIELD_NUMBER = 1;
    private volatile Object type_ = "";
    public static final int LABELS_FIELD_NUMBER = 2;
    private MapField<String, String> labels_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final MonitoredResource DEFAULT_INSTANCE = new MonitoredResource();
    private static final Parser<MonitoredResource> PARSER = new AbstractParser<MonitoredResource>(){

        @Override
        public MonitoredResource parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = MonitoredResource.newBuilder();
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

    private MonitoredResource(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private MonitoredResource() {
        this.type_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new MonitoredResource();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return MonitoredResourceProto.internal_static_google_api_MonitoredResource_descriptor;
    }

    @Override
    protected MapField internalGetMapField(int number) {
        switch (number) {
            case 2: {
                return this.internalGetLabels();
            }
        }
        throw new RuntimeException("Invalid map field number: " + number);
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return MonitoredResourceProto.internal_static_google_api_MonitoredResource_fieldAccessorTable.ensureFieldAccessorsInitialized(MonitoredResource.class, Builder.class);
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

    private MapField<String, String> internalGetLabels() {
        if (this.labels_ == null) {
            return MapField.emptyMapField(LabelsDefaultEntryHolder.defaultEntry);
        }
        return this.labels_;
    }

    @Override
    public int getLabelsCount() {
        return this.internalGetLabels().getMap().size();
    }

    @Override
    public boolean containsLabels(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        return this.internalGetLabels().getMap().containsKey(key);
    }

    @Override
    @Deprecated
    public Map<String, String> getLabels() {
        return this.getLabelsMap();
    }

    @Override
    public Map<String, String> getLabelsMap() {
        return this.internalGetLabels().getMap();
    }

    @Override
    public String getLabelsOrDefault(String key, String defaultValue) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, String> map = this.internalGetLabels().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    @Override
    public String getLabelsOrThrow(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, String> map = this.internalGetLabels().getMap();
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
        if (!GeneratedMessageV3.isStringEmpty(this.type_)) {
            GeneratedMessageV3.writeString(output, 1, this.type_);
        }
        GeneratedMessageV3.serializeStringMapTo(output, this.internalGetLabels(), LabelsDefaultEntryHolder.defaultEntry, 2);
        this.getUnknownFields().writeTo(output);
    }

    @Override
    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        if (!GeneratedMessageV3.isStringEmpty(this.type_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.type_);
        }
        for (Map.Entry<String, String> entry : this.internalGetLabels().getMap().entrySet()) {
            Message labels__ = ((MapEntry.Builder)LabelsDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
            size += CodedOutputStream.computeMessageSize(2, labels__);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MonitoredResource)) {
            return super.equals(obj);
        }
        MonitoredResource other = (MonitoredResource)obj;
        if (!this.getType().equals(other.getType())) {
            return false;
        }
        if (!this.internalGetLabels().equals(other.internalGetLabels())) {
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
        hash = 19 * hash + MonitoredResource.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getType().hashCode();
        if (!this.internalGetLabels().getMap().isEmpty()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.internalGetLabels().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static MonitoredResource parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MonitoredResource parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MonitoredResource parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MonitoredResource parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MonitoredResource parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MonitoredResource parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MonitoredResource parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static MonitoredResource parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static MonitoredResource parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static MonitoredResource parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static MonitoredResource parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static MonitoredResource parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return MonitoredResource.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(MonitoredResource prototype) {
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

    public static MonitoredResource getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<MonitoredResource> parser() {
        return PARSER;
    }

    public Parser<MonitoredResource> getParserForType() {
        return PARSER;
    }

    @Override
    public MonitoredResource getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements MonitoredResourceOrBuilder {
        private int bitField0_;
        private Object type_ = "";
        private MapField<String, String> labels_;

        public static final Descriptors.Descriptor getDescriptor() {
            return MonitoredResourceProto.internal_static_google_api_MonitoredResource_descriptor;
        }

        @Override
        protected MapField internalGetMapField(int number) {
            switch (number) {
                case 2: {
                    return this.internalGetLabels();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected MapField internalGetMutableMapField(int number) {
            switch (number) {
                case 2: {
                    return this.internalGetMutableLabels();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MonitoredResourceProto.internal_static_google_api_MonitoredResource_fieldAccessorTable.ensureFieldAccessorsInitialized(MonitoredResource.class, Builder.class);
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
            this.type_ = "";
            this.internalGetMutableLabels().clear();
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return MonitoredResourceProto.internal_static_google_api_MonitoredResource_descriptor;
        }

        @Override
        public MonitoredResource getDefaultInstanceForType() {
            return MonitoredResource.getDefaultInstance();
        }

        @Override
        public MonitoredResource build() {
            MonitoredResource result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public MonitoredResource buildPartial() {
            MonitoredResource result = new MonitoredResource(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(MonitoredResource result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.type_ = this.type_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.labels_ = this.internalGetLabels();
                result.labels_.makeImmutable();
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
            if (other instanceof MonitoredResource) {
                return this.mergeFrom((MonitoredResource)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(MonitoredResource other) {
            if (other == MonitoredResource.getDefaultInstance()) {
                return this;
            }
            if (!other.getType().isEmpty()) {
                this.type_ = other.type_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            this.internalGetMutableLabels().mergeFrom(other.internalGetLabels());
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
                            this.type_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            MapEntry<String, String> labels__ = input.readMessage(LabelsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                            this.internalGetMutableLabels().getMutableMap().put(labels__.getKey(), labels__.getValue());
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
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearType() {
            this.type_ = MonitoredResource.getDefaultInstance().getType();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setTypeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            MonitoredResource.checkByteStringIsUtf8(value);
            this.type_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        private MapField<String, String> internalGetLabels() {
            if (this.labels_ == null) {
                return MapField.emptyMapField(LabelsDefaultEntryHolder.defaultEntry);
            }
            return this.labels_;
        }

        private MapField<String, String> internalGetMutableLabels() {
            if (this.labels_ == null) {
                this.labels_ = MapField.newMapField(LabelsDefaultEntryHolder.defaultEntry);
            }
            if (!this.labels_.isMutable()) {
                this.labels_ = this.labels_.copy();
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this.labels_;
        }

        @Override
        public int getLabelsCount() {
            return this.internalGetLabels().getMap().size();
        }

        @Override
        public boolean containsLabels(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetLabels().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, String> getLabels() {
            return this.getLabelsMap();
        }

        @Override
        public Map<String, String> getLabelsMap() {
            return this.internalGetLabels().getMap();
        }

        @Override
        public String getLabelsOrDefault(String key, String defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetLabels().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public String getLabelsOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetLabels().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        public Builder clearLabels() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.internalGetMutableLabels().getMutableMap().clear();
            return this;
        }

        public Builder removeLabels(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            this.internalGetMutableLabels().getMutableMap().remove(key);
            return this;
        }

        @Deprecated
        public Map<String, String> getMutableLabels() {
            this.bitField0_ |= 2;
            return this.internalGetMutableLabels().getMutableMap();
        }

        public Builder putLabels(String key, String value) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            if (value == null) {
                throw new NullPointerException("map value");
            }
            this.internalGetMutableLabels().getMutableMap().put(key, value);
            this.bitField0_ |= 2;
            return this;
        }

        public Builder putAllLabels(Map<String, String> values2) {
            this.internalGetMutableLabels().getMutableMap().putAll(values2);
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

    private static final class LabelsDefaultEntryHolder {
        static final MapEntry<String, String> defaultEntry = MapEntry.newDefaultInstance(MonitoredResourceProto.internal_static_google_api_MonitoredResource_LabelsEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");

        private LabelsDefaultEntryHolder() {
        }
    }
}

