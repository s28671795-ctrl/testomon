/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.MonitoredResourceMetadataOrBuilder;
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
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.Struct;
import com.google.protobuf.StructOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

public final class MonitoredResourceMetadata
extends GeneratedMessageV3
implements MonitoredResourceMetadataOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int SYSTEM_LABELS_FIELD_NUMBER = 1;
    private Struct systemLabels_;
    public static final int USER_LABELS_FIELD_NUMBER = 2;
    private MapField<String, String> userLabels_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final MonitoredResourceMetadata DEFAULT_INSTANCE = new MonitoredResourceMetadata();
    private static final Parser<MonitoredResourceMetadata> PARSER = new AbstractParser<MonitoredResourceMetadata>(){

        @Override
        public MonitoredResourceMetadata parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = MonitoredResourceMetadata.newBuilder();
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

    private MonitoredResourceMetadata(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private MonitoredResourceMetadata() {
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new MonitoredResourceMetadata();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return MonitoredResourceProto.internal_static_google_api_MonitoredResourceMetadata_descriptor;
    }

    @Override
    protected MapField internalGetMapField(int number) {
        switch (number) {
            case 2: {
                return this.internalGetUserLabels();
            }
        }
        throw new RuntimeException("Invalid map field number: " + number);
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return MonitoredResourceProto.internal_static_google_api_MonitoredResourceMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(MonitoredResourceMetadata.class, Builder.class);
    }

    @Override
    public boolean hasSystemLabels() {
        return this.systemLabels_ != null;
    }

    @Override
    public Struct getSystemLabels() {
        return this.systemLabels_ == null ? Struct.getDefaultInstance() : this.systemLabels_;
    }

    @Override
    public StructOrBuilder getSystemLabelsOrBuilder() {
        return this.systemLabels_ == null ? Struct.getDefaultInstance() : this.systemLabels_;
    }

    private MapField<String, String> internalGetUserLabels() {
        if (this.userLabels_ == null) {
            return MapField.emptyMapField(UserLabelsDefaultEntryHolder.defaultEntry);
        }
        return this.userLabels_;
    }

    @Override
    public int getUserLabelsCount() {
        return this.internalGetUserLabels().getMap().size();
    }

    @Override
    public boolean containsUserLabels(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        return this.internalGetUserLabels().getMap().containsKey(key);
    }

    @Override
    @Deprecated
    public Map<String, String> getUserLabels() {
        return this.getUserLabelsMap();
    }

    @Override
    public Map<String, String> getUserLabelsMap() {
        return this.internalGetUserLabels().getMap();
    }

    @Override
    public String getUserLabelsOrDefault(String key, String defaultValue) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, String> map = this.internalGetUserLabels().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    @Override
    public String getUserLabelsOrThrow(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, String> map = this.internalGetUserLabels().getMap();
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
        if (this.systemLabels_ != null) {
            output.writeMessage(1, this.getSystemLabels());
        }
        GeneratedMessageV3.serializeStringMapTo(output, this.internalGetUserLabels(), UserLabelsDefaultEntryHolder.defaultEntry, 2);
        this.getUnknownFields().writeTo(output);
    }

    @Override
    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        if (this.systemLabels_ != null) {
            size += CodedOutputStream.computeMessageSize(1, this.getSystemLabels());
        }
        for (Map.Entry<String, String> entry : this.internalGetUserLabels().getMap().entrySet()) {
            Message userLabels__ = ((MapEntry.Builder)UserLabelsDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
            size += CodedOutputStream.computeMessageSize(2, userLabels__);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MonitoredResourceMetadata)) {
            return super.equals(obj);
        }
        MonitoredResourceMetadata other = (MonitoredResourceMetadata)obj;
        if (this.hasSystemLabels() != other.hasSystemLabels()) {
            return false;
        }
        if (this.hasSystemLabels() && !this.getSystemLabels().equals(other.getSystemLabels())) {
            return false;
        }
        if (!this.internalGetUserLabels().equals(other.internalGetUserLabels())) {
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
        hash = 19 * hash + MonitoredResourceMetadata.getDescriptor().hashCode();
        if (this.hasSystemLabels()) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getSystemLabels().hashCode();
        }
        if (!this.internalGetUserLabels().getMap().isEmpty()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.internalGetUserLabels().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static MonitoredResourceMetadata parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MonitoredResourceMetadata parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MonitoredResourceMetadata parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MonitoredResourceMetadata parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MonitoredResourceMetadata parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MonitoredResourceMetadata parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MonitoredResourceMetadata parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static MonitoredResourceMetadata parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static MonitoredResourceMetadata parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static MonitoredResourceMetadata parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static MonitoredResourceMetadata parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static MonitoredResourceMetadata parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return MonitoredResourceMetadata.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(MonitoredResourceMetadata prototype) {
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

    public static MonitoredResourceMetadata getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<MonitoredResourceMetadata> parser() {
        return PARSER;
    }

    public Parser<MonitoredResourceMetadata> getParserForType() {
        return PARSER;
    }

    @Override
    public MonitoredResourceMetadata getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements MonitoredResourceMetadataOrBuilder {
        private int bitField0_;
        private Struct systemLabels_;
        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> systemLabelsBuilder_;
        private MapField<String, String> userLabels_;

        public static final Descriptors.Descriptor getDescriptor() {
            return MonitoredResourceProto.internal_static_google_api_MonitoredResourceMetadata_descriptor;
        }

        @Override
        protected MapField internalGetMapField(int number) {
            switch (number) {
                case 2: {
                    return this.internalGetUserLabels();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected MapField internalGetMutableMapField(int number) {
            switch (number) {
                case 2: {
                    return this.internalGetMutableUserLabels();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MonitoredResourceProto.internal_static_google_api_MonitoredResourceMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(MonitoredResourceMetadata.class, Builder.class);
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
            this.systemLabels_ = null;
            if (this.systemLabelsBuilder_ != null) {
                this.systemLabelsBuilder_.dispose();
                this.systemLabelsBuilder_ = null;
            }
            this.internalGetMutableUserLabels().clear();
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return MonitoredResourceProto.internal_static_google_api_MonitoredResourceMetadata_descriptor;
        }

        @Override
        public MonitoredResourceMetadata getDefaultInstanceForType() {
            return MonitoredResourceMetadata.getDefaultInstance();
        }

        @Override
        public MonitoredResourceMetadata build() {
            MonitoredResourceMetadata result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public MonitoredResourceMetadata buildPartial() {
            MonitoredResourceMetadata result = new MonitoredResourceMetadata(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(MonitoredResourceMetadata result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.systemLabels_ = this.systemLabelsBuilder_ == null ? this.systemLabels_ : this.systemLabelsBuilder_.build();
            }
            if ((from_bitField0_ & 2) != 0) {
                result.userLabels_ = this.internalGetUserLabels();
                result.userLabels_.makeImmutable();
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
            if (other instanceof MonitoredResourceMetadata) {
                return this.mergeFrom((MonitoredResourceMetadata)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(MonitoredResourceMetadata other) {
            if (other == MonitoredResourceMetadata.getDefaultInstance()) {
                return this;
            }
            if (other.hasSystemLabels()) {
                this.mergeSystemLabels(other.getSystemLabels());
            }
            this.internalGetMutableUserLabels().mergeFrom(other.internalGetUserLabels());
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
                            input.readMessage(this.getSystemLabelsFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            MapEntry<String, String> userLabels__ = input.readMessage(UserLabelsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                            this.internalGetMutableUserLabels().getMutableMap().put(userLabels__.getKey(), userLabels__.getValue());
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
        public boolean hasSystemLabels() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public Struct getSystemLabels() {
            if (this.systemLabelsBuilder_ == null) {
                return this.systemLabels_ == null ? Struct.getDefaultInstance() : this.systemLabels_;
            }
            return this.systemLabelsBuilder_.getMessage();
        }

        public Builder setSystemLabels(Struct value) {
            if (this.systemLabelsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.systemLabels_ = value;
            } else {
                this.systemLabelsBuilder_.setMessage(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder setSystemLabels(Struct.Builder builderForValue) {
            if (this.systemLabelsBuilder_ == null) {
                this.systemLabels_ = builderForValue.build();
            } else {
                this.systemLabelsBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder mergeSystemLabels(Struct value) {
            if (this.systemLabelsBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0 && this.systemLabels_ != null && this.systemLabels_ != Struct.getDefaultInstance()) {
                    this.getSystemLabelsBuilder().mergeFrom(value);
                } else {
                    this.systemLabels_ = value;
                }
            } else {
                this.systemLabelsBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearSystemLabels() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.systemLabels_ = null;
            if (this.systemLabelsBuilder_ != null) {
                this.systemLabelsBuilder_.dispose();
                this.systemLabelsBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Struct.Builder getSystemLabelsBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return this.getSystemLabelsFieldBuilder().getBuilder();
        }

        @Override
        public StructOrBuilder getSystemLabelsOrBuilder() {
            if (this.systemLabelsBuilder_ != null) {
                return this.systemLabelsBuilder_.getMessageOrBuilder();
            }
            return this.systemLabels_ == null ? Struct.getDefaultInstance() : this.systemLabels_;
        }

        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getSystemLabelsFieldBuilder() {
            if (this.systemLabelsBuilder_ == null) {
                this.systemLabelsBuilder_ = new SingleFieldBuilderV3(this.getSystemLabels(), this.getParentForChildren(), this.isClean());
                this.systemLabels_ = null;
            }
            return this.systemLabelsBuilder_;
        }

        private MapField<String, String> internalGetUserLabels() {
            if (this.userLabels_ == null) {
                return MapField.emptyMapField(UserLabelsDefaultEntryHolder.defaultEntry);
            }
            return this.userLabels_;
        }

        private MapField<String, String> internalGetMutableUserLabels() {
            if (this.userLabels_ == null) {
                this.userLabels_ = MapField.newMapField(UserLabelsDefaultEntryHolder.defaultEntry);
            }
            if (!this.userLabels_.isMutable()) {
                this.userLabels_ = this.userLabels_.copy();
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this.userLabels_;
        }

        @Override
        public int getUserLabelsCount() {
            return this.internalGetUserLabels().getMap().size();
        }

        @Override
        public boolean containsUserLabels(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetUserLabels().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, String> getUserLabels() {
            return this.getUserLabelsMap();
        }

        @Override
        public Map<String, String> getUserLabelsMap() {
            return this.internalGetUserLabels().getMap();
        }

        @Override
        public String getUserLabelsOrDefault(String key, String defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetUserLabels().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public String getUserLabelsOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetUserLabels().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        public Builder clearUserLabels() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.internalGetMutableUserLabels().getMutableMap().clear();
            return this;
        }

        public Builder removeUserLabels(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            this.internalGetMutableUserLabels().getMutableMap().remove(key);
            return this;
        }

        @Deprecated
        public Map<String, String> getMutableUserLabels() {
            this.bitField0_ |= 2;
            return this.internalGetMutableUserLabels().getMutableMap();
        }

        public Builder putUserLabels(String key, String value) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            if (value == null) {
                throw new NullPointerException("map value");
            }
            this.internalGetMutableUserLabels().getMutableMap().put(key, value);
            this.bitField0_ |= 2;
            return this;
        }

        public Builder putAllUserLabels(Map<String, String> values2) {
            this.internalGetMutableUserLabels().getMutableMap().putAll(values2);
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

    private static final class UserLabelsDefaultEntryHolder {
        static final MapEntry<String, String> defaultEntry = MapEntry.newDefaultInstance(MonitoredResourceProto.internal_static_google_api_MonitoredResourceMetadata_UserLabelsEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");

        private UserLabelsDefaultEntryHolder() {
        }
    }
}

