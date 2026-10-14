/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.location;

import com.google.cloud.location.LocationOrBuilder;
import com.google.cloud.location.LocationsProto;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.Any;
import com.google.protobuf.AnyOrBuilder;
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
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

public final class Location
extends GeneratedMessageV3
implements LocationOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 1;
    private volatile Object name_ = "";
    public static final int LOCATION_ID_FIELD_NUMBER = 4;
    private volatile Object locationId_ = "";
    public static final int DISPLAY_NAME_FIELD_NUMBER = 5;
    private volatile Object displayName_ = "";
    public static final int LABELS_FIELD_NUMBER = 2;
    private MapField<String, String> labels_;
    public static final int METADATA_FIELD_NUMBER = 3;
    private Any metadata_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Location DEFAULT_INSTANCE = new Location();
    private static final Parser<Location> PARSER = new AbstractParser<Location>(){

        @Override
        public Location parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Location.newBuilder();
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

    private Location(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Location() {
        this.name_ = "";
        this.locationId_ = "";
        this.displayName_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Location();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return LocationsProto.internal_static_google_cloud_location_Location_descriptor;
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
        return LocationsProto.internal_static_google_cloud_location_Location_fieldAccessorTable.ensureFieldAccessorsInitialized(Location.class, Builder.class);
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
    public String getLocationId() {
        Object ref = this.locationId_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.locationId_ = s2;
        return s2;
    }

    @Override
    public ByteString getLocationIdBytes() {
        Object ref = this.locationId_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.locationId_ = b;
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
    public boolean hasMetadata() {
        return this.metadata_ != null;
    }

    @Override
    public Any getMetadata() {
        return this.metadata_ == null ? Any.getDefaultInstance() : this.metadata_;
    }

    @Override
    public AnyOrBuilder getMetadataOrBuilder() {
        return this.metadata_ == null ? Any.getDefaultInstance() : this.metadata_;
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
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            GeneratedMessageV3.writeString(output, 1, this.name_);
        }
        GeneratedMessageV3.serializeStringMapTo(output, this.internalGetLabels(), LabelsDefaultEntryHolder.defaultEntry, 2);
        if (this.metadata_ != null) {
            output.writeMessage(3, this.getMetadata());
        }
        if (!GeneratedMessageV3.isStringEmpty(this.locationId_)) {
            GeneratedMessageV3.writeString(output, 4, this.locationId_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
            GeneratedMessageV3.writeString(output, 5, this.displayName_);
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
        for (Map.Entry<String, String> entry : this.internalGetLabels().getMap().entrySet()) {
            Message labels__ = ((MapEntry.Builder)LabelsDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
            size += CodedOutputStream.computeMessageSize(2, labels__);
        }
        if (this.metadata_ != null) {
            size += CodedOutputStream.computeMessageSize(3, this.getMetadata());
        }
        if (!GeneratedMessageV3.isStringEmpty(this.locationId_)) {
            size += GeneratedMessageV3.computeStringSize(4, this.locationId_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
            size += GeneratedMessageV3.computeStringSize(5, this.displayName_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Location)) {
            return super.equals(obj);
        }
        Location other = (Location)obj;
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (!this.getLocationId().equals(other.getLocationId())) {
            return false;
        }
        if (!this.getDisplayName().equals(other.getDisplayName())) {
            return false;
        }
        if (!this.internalGetLabels().equals(other.internalGetLabels())) {
            return false;
        }
        if (this.hasMetadata() != other.hasMetadata()) {
            return false;
        }
        if (this.hasMetadata() && !this.getMetadata().equals(other.getMetadata())) {
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
        hash = 19 * hash + Location.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getName().hashCode();
        hash = 37 * hash + 4;
        hash = 53 * hash + this.getLocationId().hashCode();
        hash = 37 * hash + 5;
        hash = 53 * hash + this.getDisplayName().hashCode();
        if (!this.internalGetLabels().getMap().isEmpty()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.internalGetLabels().hashCode();
        }
        if (this.hasMetadata()) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getMetadata().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Location parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Location parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Location parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Location parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Location parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Location parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Location parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Location parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Location parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Location parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Location parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Location parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Location.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Location prototype) {
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

    public static Location getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Location> parser() {
        return PARSER;
    }

    public Parser<Location> getParserForType() {
        return PARSER;
    }

    @Override
    public Location getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements LocationOrBuilder {
        private int bitField0_;
        private Object name_ = "";
        private Object locationId_ = "";
        private Object displayName_ = "";
        private MapField<String, String> labels_;
        private Any metadata_;
        private SingleFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> metadataBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return LocationsProto.internal_static_google_cloud_location_Location_descriptor;
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
            return LocationsProto.internal_static_google_cloud_location_Location_fieldAccessorTable.ensureFieldAccessorsInitialized(Location.class, Builder.class);
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
            this.locationId_ = "";
            this.displayName_ = "";
            this.internalGetMutableLabels().clear();
            this.metadata_ = null;
            if (this.metadataBuilder_ != null) {
                this.metadataBuilder_.dispose();
                this.metadataBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return LocationsProto.internal_static_google_cloud_location_Location_descriptor;
        }

        @Override
        public Location getDefaultInstanceForType() {
            return Location.getDefaultInstance();
        }

        @Override
        public Location build() {
            Location result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Location buildPartial() {
            Location result = new Location(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(Location result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.name_ = this.name_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.locationId_ = this.locationId_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.displayName_ = this.displayName_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.labels_ = this.internalGetLabels();
                result.labels_.makeImmutable();
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.metadata_ = this.metadataBuilder_ == null ? this.metadata_ : this.metadataBuilder_.build();
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
            if (other instanceof Location) {
                return this.mergeFrom((Location)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Location other) {
            if (other == Location.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getLocationId().isEmpty()) {
                this.locationId_ = other.locationId_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (!other.getDisplayName().isEmpty()) {
                this.displayName_ = other.displayName_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            this.internalGetMutableLabels().mergeFrom(other.internalGetLabels());
            this.bitField0_ |= 8;
            if (other.hasMetadata()) {
                this.mergeMetadata(other.getMetadata());
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
                block13: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block13;
                        }
                        case 10: {
                            this.name_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block13;
                        }
                        case 18: {
                            MapEntry<String, String> labels__ = input.readMessage(LabelsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                            this.internalGetMutableLabels().getMutableMap().put(labels__.getKey(), labels__.getValue());
                            this.bitField0_ |= 8;
                            continue block13;
                        }
                        case 26: {
                            input.readMessage(this.getMetadataFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x10;
                            continue block13;
                        }
                        case 34: {
                            this.locationId_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block13;
                        }
                        case 42: {
                            this.displayName_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block13;
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
            this.name_ = Location.getDefaultInstance().getName();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Location.checkByteStringIsUtf8(value);
            this.name_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getLocationId() {
            Object ref = this.locationId_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.locationId_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getLocationIdBytes() {
            Object ref = this.locationId_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.locationId_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setLocationId(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.locationId_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearLocationId() {
            this.locationId_ = Location.getDefaultInstance().getLocationId();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setLocationIdBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Location.checkByteStringIsUtf8(value);
            this.locationId_ = value;
            this.bitField0_ |= 2;
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
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearDisplayName() {
            this.displayName_ = Location.getDefaultInstance().getDisplayName();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setDisplayNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Location.checkByteStringIsUtf8(value);
            this.displayName_ = value;
            this.bitField0_ |= 4;
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
            this.bitField0_ |= 8;
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
            this.bitField0_ &= 0xFFFFFFF7;
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
            this.bitField0_ |= 8;
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
            this.bitField0_ |= 8;
            return this;
        }

        public Builder putAllLabels(Map<String, String> values2) {
            this.internalGetMutableLabels().getMutableMap().putAll(values2);
            this.bitField0_ |= 8;
            return this;
        }

        @Override
        public boolean hasMetadata() {
            return (this.bitField0_ & 0x10) != 0;
        }

        @Override
        public Any getMetadata() {
            if (this.metadataBuilder_ == null) {
                return this.metadata_ == null ? Any.getDefaultInstance() : this.metadata_;
            }
            return this.metadataBuilder_.getMessage();
        }

        public Builder setMetadata(Any value) {
            if (this.metadataBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.metadata_ = value;
            } else {
                this.metadataBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder setMetadata(Any.Builder builderForValue) {
            if (this.metadataBuilder_ == null) {
                this.metadata_ = builderForValue.build();
            } else {
                this.metadataBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder mergeMetadata(Any value) {
            if (this.metadataBuilder_ == null) {
                if ((this.bitField0_ & 0x10) != 0 && this.metadata_ != null && this.metadata_ != Any.getDefaultInstance()) {
                    this.getMetadataBuilder().mergeFrom(value);
                } else {
                    this.metadata_ = value;
                }
            } else {
                this.metadataBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearMetadata() {
            this.bitField0_ &= 0xFFFFFFEF;
            this.metadata_ = null;
            if (this.metadataBuilder_ != null) {
                this.metadataBuilder_.dispose();
                this.metadataBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Any.Builder getMetadataBuilder() {
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this.getMetadataFieldBuilder().getBuilder();
        }

        @Override
        public AnyOrBuilder getMetadataOrBuilder() {
            if (this.metadataBuilder_ != null) {
                return this.metadataBuilder_.getMessageOrBuilder();
            }
            return this.metadata_ == null ? Any.getDefaultInstance() : this.metadata_;
        }

        private SingleFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> getMetadataFieldBuilder() {
            if (this.metadataBuilder_ == null) {
                this.metadataBuilder_ = new SingleFieldBuilderV3(this.getMetadata(), this.getParentForChildren(), this.isClean());
                this.metadata_ = null;
            }
            return this.metadataBuilder_;
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
        static final MapEntry<String, String> defaultEntry = MapEntry.newDefaultInstance(LocationsProto.internal_static_google_cloud_location_Location_LabelsEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");

        private LabelsDefaultEntryHolder() {
        }
    }
}

