/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.AuditLogProto;
import com.google.cloud.audit.ResourceLocationOrBuilder;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ResourceLocation
extends GeneratedMessageV3
implements ResourceLocationOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int CURRENT_LOCATIONS_FIELD_NUMBER = 1;
    private LazyStringArrayList currentLocations_ = LazyStringArrayList.emptyList();
    public static final int ORIGINAL_LOCATIONS_FIELD_NUMBER = 2;
    private LazyStringArrayList originalLocations_ = LazyStringArrayList.emptyList();
    private byte memoizedIsInitialized = (byte)-1;
    private static final ResourceLocation DEFAULT_INSTANCE = new ResourceLocation();
    private static final Parser<ResourceLocation> PARSER = new AbstractParser<ResourceLocation>(){

        @Override
        public ResourceLocation parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = ResourceLocation.newBuilder();
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

    private ResourceLocation(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private ResourceLocation() {
        this.currentLocations_ = LazyStringArrayList.emptyList();
        this.originalLocations_ = LazyStringArrayList.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new ResourceLocation();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuditLogProto.internal_static_google_cloud_audit_ResourceLocation_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuditLogProto.internal_static_google_cloud_audit_ResourceLocation_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceLocation.class, Builder.class);
    }

    public ProtocolStringList getCurrentLocationsList() {
        return this.currentLocations_;
    }

    @Override
    public int getCurrentLocationsCount() {
        return this.currentLocations_.size();
    }

    @Override
    public String getCurrentLocations(int index) {
        return this.currentLocations_.get(index);
    }

    @Override
    public ByteString getCurrentLocationsBytes(int index) {
        return this.currentLocations_.getByteString(index);
    }

    public ProtocolStringList getOriginalLocationsList() {
        return this.originalLocations_;
    }

    @Override
    public int getOriginalLocationsCount() {
        return this.originalLocations_.size();
    }

    @Override
    public String getOriginalLocations(int index) {
        return this.originalLocations_.get(index);
    }

    @Override
    public ByteString getOriginalLocationsBytes(int index) {
        return this.originalLocations_.getByteString(index);
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
        for (i = 0; i < this.currentLocations_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 1, this.currentLocations_.getRaw(i));
        }
        for (i = 0; i < this.originalLocations_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 2, this.originalLocations_.getRaw(i));
        }
        this.getUnknownFields().writeTo(output);
    }

    @Override
    public int getSerializedSize() {
        int i;
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        int dataSize = 0;
        for (i = 0; i < this.currentLocations_.size(); ++i) {
            dataSize += ResourceLocation.computeStringSizeNoTag(this.currentLocations_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getCurrentLocationsList().size();
        dataSize = 0;
        for (i = 0; i < this.originalLocations_.size(); ++i) {
            dataSize += ResourceLocation.computeStringSizeNoTag(this.originalLocations_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getOriginalLocationsList().size();
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ResourceLocation)) {
            return super.equals(obj);
        }
        ResourceLocation other = (ResourceLocation)obj;
        if (!this.getCurrentLocationsList().equals(other.getCurrentLocationsList())) {
            return false;
        }
        if (!this.getOriginalLocationsList().equals(other.getOriginalLocationsList())) {
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
        hash = 19 * hash + ResourceLocation.getDescriptor().hashCode();
        if (this.getCurrentLocationsCount() > 0) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getCurrentLocationsList().hashCode();
        }
        if (this.getOriginalLocationsCount() > 0) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getOriginalLocationsList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static ResourceLocation parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ResourceLocation parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ResourceLocation parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ResourceLocation parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ResourceLocation parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ResourceLocation parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ResourceLocation parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ResourceLocation parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ResourceLocation parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ResourceLocation parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ResourceLocation parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ResourceLocation parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return ResourceLocation.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ResourceLocation prototype) {
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

    public static ResourceLocation getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ResourceLocation> parser() {
        return PARSER;
    }

    public Parser<ResourceLocation> getParserForType() {
        return PARSER;
    }

    @Override
    public ResourceLocation getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ResourceLocationOrBuilder {
        private int bitField0_;
        private LazyStringArrayList currentLocations_ = LazyStringArrayList.emptyList();
        private LazyStringArrayList originalLocations_ = LazyStringArrayList.emptyList();

        public static final Descriptors.Descriptor getDescriptor() {
            return AuditLogProto.internal_static_google_cloud_audit_ResourceLocation_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuditLogProto.internal_static_google_cloud_audit_ResourceLocation_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceLocation.class, Builder.class);
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
            this.currentLocations_ = LazyStringArrayList.emptyList();
            this.originalLocations_ = LazyStringArrayList.emptyList();
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuditLogProto.internal_static_google_cloud_audit_ResourceLocation_descriptor;
        }

        @Override
        public ResourceLocation getDefaultInstanceForType() {
            return ResourceLocation.getDefaultInstance();
        }

        @Override
        public ResourceLocation build() {
            ResourceLocation result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ResourceLocation buildPartial() {
            ResourceLocation result = new ResourceLocation(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(ResourceLocation result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                this.currentLocations_.makeImmutable();
                result.currentLocations_ = this.currentLocations_;
            }
            if ((from_bitField0_ & 2) != 0) {
                this.originalLocations_.makeImmutable();
                result.originalLocations_ = this.originalLocations_;
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
            if (other instanceof ResourceLocation) {
                return this.mergeFrom((ResourceLocation)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ResourceLocation other) {
            if (other == ResourceLocation.getDefaultInstance()) {
                return this;
            }
            if (!other.currentLocations_.isEmpty()) {
                if (this.currentLocations_.isEmpty()) {
                    this.currentLocations_ = other.currentLocations_;
                    this.bitField0_ |= 1;
                } else {
                    this.ensureCurrentLocationsIsMutable();
                    this.currentLocations_.addAll(other.currentLocations_);
                }
                this.onChanged();
            }
            if (!other.originalLocations_.isEmpty()) {
                if (this.originalLocations_.isEmpty()) {
                    this.originalLocations_ = other.originalLocations_;
                    this.bitField0_ |= 2;
                } else {
                    this.ensureOriginalLocationsIsMutable();
                    this.originalLocations_.addAll(other.originalLocations_);
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
                        case 10: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensureCurrentLocationsIsMutable();
                            this.currentLocations_.add(s2);
                            continue block10;
                        }
                        case 18: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensureOriginalLocationsIsMutable();
                            this.originalLocations_.add(s2);
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

        private void ensureCurrentLocationsIsMutable() {
            if (!this.currentLocations_.isModifiable()) {
                this.currentLocations_ = new LazyStringArrayList(this.currentLocations_);
            }
            this.bitField0_ |= 1;
        }

        public ProtocolStringList getCurrentLocationsList() {
            this.currentLocations_.makeImmutable();
            return this.currentLocations_;
        }

        @Override
        public int getCurrentLocationsCount() {
            return this.currentLocations_.size();
        }

        @Override
        public String getCurrentLocations(int index) {
            return this.currentLocations_.get(index);
        }

        @Override
        public ByteString getCurrentLocationsBytes(int index) {
            return this.currentLocations_.getByteString(index);
        }

        public Builder setCurrentLocations(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureCurrentLocationsIsMutable();
            this.currentLocations_.set(index, value);
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder addCurrentLocations(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureCurrentLocationsIsMutable();
            this.currentLocations_.add(value);
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder addAllCurrentLocations(Iterable<String> values2) {
            this.ensureCurrentLocationsIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.currentLocations_);
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearCurrentLocations() {
            this.currentLocations_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder addCurrentLocationsBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ResourceLocation.checkByteStringIsUtf8(value);
            this.ensureCurrentLocationsIsMutable();
            this.currentLocations_.add(value);
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        private void ensureOriginalLocationsIsMutable() {
            if (!this.originalLocations_.isModifiable()) {
                this.originalLocations_ = new LazyStringArrayList(this.originalLocations_);
            }
            this.bitField0_ |= 2;
        }

        public ProtocolStringList getOriginalLocationsList() {
            this.originalLocations_.makeImmutable();
            return this.originalLocations_;
        }

        @Override
        public int getOriginalLocationsCount() {
            return this.originalLocations_.size();
        }

        @Override
        public String getOriginalLocations(int index) {
            return this.originalLocations_.get(index);
        }

        @Override
        public ByteString getOriginalLocationsBytes(int index) {
            return this.originalLocations_.getByteString(index);
        }

        public Builder setOriginalLocations(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureOriginalLocationsIsMutable();
            this.originalLocations_.set(index, value);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder addOriginalLocations(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureOriginalLocationsIsMutable();
            this.originalLocations_.add(value);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder addAllOriginalLocations(Iterable<String> values2) {
            this.ensureOriginalLocationsIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.originalLocations_);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearOriginalLocations() {
            this.originalLocations_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder addOriginalLocationsBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ResourceLocation.checkByteStringIsUtf8(value);
            this.ensureOriginalLocationsIsMutable();
            this.originalLocations_.add(value);
            this.bitField0_ |= 2;
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

