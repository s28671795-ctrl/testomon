/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ClientProto;
import com.google.api.CommonLanguageSettings;
import com.google.api.CommonLanguageSettingsOrBuilder;
import com.google.api.DotnetSettingsOrBuilder;
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
import com.google.protobuf.MapEntry;
import com.google.protobuf.MapField;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

public final class DotnetSettings
extends GeneratedMessageV3
implements DotnetSettingsOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int COMMON_FIELD_NUMBER = 1;
    private CommonLanguageSettings common_;
    public static final int RENAMED_SERVICES_FIELD_NUMBER = 2;
    private MapField<String, String> renamedServices_;
    public static final int RENAMED_RESOURCES_FIELD_NUMBER = 3;
    private MapField<String, String> renamedResources_;
    public static final int IGNORED_RESOURCES_FIELD_NUMBER = 4;
    private LazyStringArrayList ignoredResources_ = LazyStringArrayList.emptyList();
    public static final int FORCED_NAMESPACE_ALIASES_FIELD_NUMBER = 5;
    private LazyStringArrayList forcedNamespaceAliases_ = LazyStringArrayList.emptyList();
    public static final int HANDWRITTEN_SIGNATURES_FIELD_NUMBER = 6;
    private LazyStringArrayList handwrittenSignatures_ = LazyStringArrayList.emptyList();
    private byte memoizedIsInitialized = (byte)-1;
    private static final DotnetSettings DEFAULT_INSTANCE = new DotnetSettings();
    private static final Parser<DotnetSettings> PARSER = new AbstractParser<DotnetSettings>(){

        @Override
        public DotnetSettings parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = DotnetSettings.newBuilder();
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

    private DotnetSettings(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private DotnetSettings() {
        this.ignoredResources_ = LazyStringArrayList.emptyList();
        this.forcedNamespaceAliases_ = LazyStringArrayList.emptyList();
        this.handwrittenSignatures_ = LazyStringArrayList.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new DotnetSettings();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ClientProto.internal_static_google_api_DotnetSettings_descriptor;
    }

    @Override
    protected MapField internalGetMapField(int number) {
        switch (number) {
            case 2: {
                return this.internalGetRenamedServices();
            }
            case 3: {
                return this.internalGetRenamedResources();
            }
        }
        throw new RuntimeException("Invalid map field number: " + number);
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ClientProto.internal_static_google_api_DotnetSettings_fieldAccessorTable.ensureFieldAccessorsInitialized(DotnetSettings.class, Builder.class);
    }

    @Override
    public boolean hasCommon() {
        return this.common_ != null;
    }

    @Override
    public CommonLanguageSettings getCommon() {
        return this.common_ == null ? CommonLanguageSettings.getDefaultInstance() : this.common_;
    }

    @Override
    public CommonLanguageSettingsOrBuilder getCommonOrBuilder() {
        return this.common_ == null ? CommonLanguageSettings.getDefaultInstance() : this.common_;
    }

    private MapField<String, String> internalGetRenamedServices() {
        if (this.renamedServices_ == null) {
            return MapField.emptyMapField(RenamedServicesDefaultEntryHolder.defaultEntry);
        }
        return this.renamedServices_;
    }

    @Override
    public int getRenamedServicesCount() {
        return this.internalGetRenamedServices().getMap().size();
    }

    @Override
    public boolean containsRenamedServices(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        return this.internalGetRenamedServices().getMap().containsKey(key);
    }

    @Override
    @Deprecated
    public Map<String, String> getRenamedServices() {
        return this.getRenamedServicesMap();
    }

    @Override
    public Map<String, String> getRenamedServicesMap() {
        return this.internalGetRenamedServices().getMap();
    }

    @Override
    public String getRenamedServicesOrDefault(String key, String defaultValue) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, String> map = this.internalGetRenamedServices().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    @Override
    public String getRenamedServicesOrThrow(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, String> map = this.internalGetRenamedServices().getMap();
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return map.get(key);
    }

    private MapField<String, String> internalGetRenamedResources() {
        if (this.renamedResources_ == null) {
            return MapField.emptyMapField(RenamedResourcesDefaultEntryHolder.defaultEntry);
        }
        return this.renamedResources_;
    }

    @Override
    public int getRenamedResourcesCount() {
        return this.internalGetRenamedResources().getMap().size();
    }

    @Override
    public boolean containsRenamedResources(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        return this.internalGetRenamedResources().getMap().containsKey(key);
    }

    @Override
    @Deprecated
    public Map<String, String> getRenamedResources() {
        return this.getRenamedResourcesMap();
    }

    @Override
    public Map<String, String> getRenamedResourcesMap() {
        return this.internalGetRenamedResources().getMap();
    }

    @Override
    public String getRenamedResourcesOrDefault(String key, String defaultValue) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, String> map = this.internalGetRenamedResources().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    @Override
    public String getRenamedResourcesOrThrow(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, String> map = this.internalGetRenamedResources().getMap();
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return map.get(key);
    }

    public ProtocolStringList getIgnoredResourcesList() {
        return this.ignoredResources_;
    }

    @Override
    public int getIgnoredResourcesCount() {
        return this.ignoredResources_.size();
    }

    @Override
    public String getIgnoredResources(int index) {
        return this.ignoredResources_.get(index);
    }

    @Override
    public ByteString getIgnoredResourcesBytes(int index) {
        return this.ignoredResources_.getByteString(index);
    }

    public ProtocolStringList getForcedNamespaceAliasesList() {
        return this.forcedNamespaceAliases_;
    }

    @Override
    public int getForcedNamespaceAliasesCount() {
        return this.forcedNamespaceAliases_.size();
    }

    @Override
    public String getForcedNamespaceAliases(int index) {
        return this.forcedNamespaceAliases_.get(index);
    }

    @Override
    public ByteString getForcedNamespaceAliasesBytes(int index) {
        return this.forcedNamespaceAliases_.getByteString(index);
    }

    public ProtocolStringList getHandwrittenSignaturesList() {
        return this.handwrittenSignatures_;
    }

    @Override
    public int getHandwrittenSignaturesCount() {
        return this.handwrittenSignatures_.size();
    }

    @Override
    public String getHandwrittenSignatures(int index) {
        return this.handwrittenSignatures_.get(index);
    }

    @Override
    public ByteString getHandwrittenSignaturesBytes(int index) {
        return this.handwrittenSignatures_.getByteString(index);
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
        if (this.common_ != null) {
            output.writeMessage(1, this.getCommon());
        }
        GeneratedMessageV3.serializeStringMapTo(output, this.internalGetRenamedServices(), RenamedServicesDefaultEntryHolder.defaultEntry, 2);
        GeneratedMessageV3.serializeStringMapTo(output, this.internalGetRenamedResources(), RenamedResourcesDefaultEntryHolder.defaultEntry, 3);
        for (i = 0; i < this.ignoredResources_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 4, this.ignoredResources_.getRaw(i));
        }
        for (i = 0; i < this.forcedNamespaceAliases_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 5, this.forcedNamespaceAliases_.getRaw(i));
        }
        for (i = 0; i < this.handwrittenSignatures_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 6, this.handwrittenSignatures_.getRaw(i));
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
        if (this.common_ != null) {
            size += CodedOutputStream.computeMessageSize(1, this.getCommon());
        }
        for (Map.Entry<String, String> entry : this.internalGetRenamedServices().getMap().entrySet()) {
            Message renamedServices__ = ((MapEntry.Builder)RenamedServicesDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
            size += CodedOutputStream.computeMessageSize(2, renamedServices__);
        }
        for (Map.Entry<String, String> entry : this.internalGetRenamedResources().getMap().entrySet()) {
            Message renamedResources__ = ((MapEntry.Builder)RenamedResourcesDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
            size += CodedOutputStream.computeMessageSize(3, renamedResources__);
        }
        int dataSize = 0;
        for (i = 0; i < this.ignoredResources_.size(); ++i) {
            dataSize += DotnetSettings.computeStringSizeNoTag(this.ignoredResources_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getIgnoredResourcesList().size();
        dataSize = 0;
        for (i = 0; i < this.forcedNamespaceAliases_.size(); ++i) {
            dataSize += DotnetSettings.computeStringSizeNoTag(this.forcedNamespaceAliases_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getForcedNamespaceAliasesList().size();
        dataSize = 0;
        for (i = 0; i < this.handwrittenSignatures_.size(); ++i) {
            dataSize += DotnetSettings.computeStringSizeNoTag(this.handwrittenSignatures_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getHandwrittenSignaturesList().size();
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DotnetSettings)) {
            return super.equals(obj);
        }
        DotnetSettings other = (DotnetSettings)obj;
        if (this.hasCommon() != other.hasCommon()) {
            return false;
        }
        if (this.hasCommon() && !this.getCommon().equals(other.getCommon())) {
            return false;
        }
        if (!this.internalGetRenamedServices().equals(other.internalGetRenamedServices())) {
            return false;
        }
        if (!this.internalGetRenamedResources().equals(other.internalGetRenamedResources())) {
            return false;
        }
        if (!this.getIgnoredResourcesList().equals(other.getIgnoredResourcesList())) {
            return false;
        }
        if (!this.getForcedNamespaceAliasesList().equals(other.getForcedNamespaceAliasesList())) {
            return false;
        }
        if (!this.getHandwrittenSignaturesList().equals(other.getHandwrittenSignaturesList())) {
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
        hash = 19 * hash + DotnetSettings.getDescriptor().hashCode();
        if (this.hasCommon()) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getCommon().hashCode();
        }
        if (!this.internalGetRenamedServices().getMap().isEmpty()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.internalGetRenamedServices().hashCode();
        }
        if (!this.internalGetRenamedResources().getMap().isEmpty()) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.internalGetRenamedResources().hashCode();
        }
        if (this.getIgnoredResourcesCount() > 0) {
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getIgnoredResourcesList().hashCode();
        }
        if (this.getForcedNamespaceAliasesCount() > 0) {
            hash = 37 * hash + 5;
            hash = 53 * hash + this.getForcedNamespaceAliasesList().hashCode();
        }
        if (this.getHandwrittenSignaturesCount() > 0) {
            hash = 37 * hash + 6;
            hash = 53 * hash + this.getHandwrittenSignaturesList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static DotnetSettings parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static DotnetSettings parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static DotnetSettings parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static DotnetSettings parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static DotnetSettings parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static DotnetSettings parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static DotnetSettings parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static DotnetSettings parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static DotnetSettings parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static DotnetSettings parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static DotnetSettings parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static DotnetSettings parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return DotnetSettings.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(DotnetSettings prototype) {
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

    public static DotnetSettings getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<DotnetSettings> parser() {
        return PARSER;
    }

    public Parser<DotnetSettings> getParserForType() {
        return PARSER;
    }

    @Override
    public DotnetSettings getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements DotnetSettingsOrBuilder {
        private int bitField0_;
        private CommonLanguageSettings common_;
        private SingleFieldBuilderV3<CommonLanguageSettings, CommonLanguageSettings.Builder, CommonLanguageSettingsOrBuilder> commonBuilder_;
        private MapField<String, String> renamedServices_;
        private MapField<String, String> renamedResources_;
        private LazyStringArrayList ignoredResources_ = LazyStringArrayList.emptyList();
        private LazyStringArrayList forcedNamespaceAliases_ = LazyStringArrayList.emptyList();
        private LazyStringArrayList handwrittenSignatures_ = LazyStringArrayList.emptyList();

        public static final Descriptors.Descriptor getDescriptor() {
            return ClientProto.internal_static_google_api_DotnetSettings_descriptor;
        }

        @Override
        protected MapField internalGetMapField(int number) {
            switch (number) {
                case 2: {
                    return this.internalGetRenamedServices();
                }
                case 3: {
                    return this.internalGetRenamedResources();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected MapField internalGetMutableMapField(int number) {
            switch (number) {
                case 2: {
                    return this.internalGetMutableRenamedServices();
                }
                case 3: {
                    return this.internalGetMutableRenamedResources();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ClientProto.internal_static_google_api_DotnetSettings_fieldAccessorTable.ensureFieldAccessorsInitialized(DotnetSettings.class, Builder.class);
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
            this.common_ = null;
            if (this.commonBuilder_ != null) {
                this.commonBuilder_.dispose();
                this.commonBuilder_ = null;
            }
            this.internalGetMutableRenamedServices().clear();
            this.internalGetMutableRenamedResources().clear();
            this.ignoredResources_ = LazyStringArrayList.emptyList();
            this.forcedNamespaceAliases_ = LazyStringArrayList.emptyList();
            this.handwrittenSignatures_ = LazyStringArrayList.emptyList();
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ClientProto.internal_static_google_api_DotnetSettings_descriptor;
        }

        @Override
        public DotnetSettings getDefaultInstanceForType() {
            return DotnetSettings.getDefaultInstance();
        }

        @Override
        public DotnetSettings build() {
            DotnetSettings result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public DotnetSettings buildPartial() {
            DotnetSettings result = new DotnetSettings(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(DotnetSettings result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.common_ = this.commonBuilder_ == null ? this.common_ : this.commonBuilder_.build();
            }
            if ((from_bitField0_ & 2) != 0) {
                result.renamedServices_ = this.internalGetRenamedServices();
                result.renamedServices_.makeImmutable();
            }
            if ((from_bitField0_ & 4) != 0) {
                result.renamedResources_ = this.internalGetRenamedResources();
                result.renamedResources_.makeImmutable();
            }
            if ((from_bitField0_ & 8) != 0) {
                this.ignoredResources_.makeImmutable();
                result.ignoredResources_ = this.ignoredResources_;
            }
            if ((from_bitField0_ & 0x10) != 0) {
                this.forcedNamespaceAliases_.makeImmutable();
                result.forcedNamespaceAliases_ = this.forcedNamespaceAliases_;
            }
            if ((from_bitField0_ & 0x20) != 0) {
                this.handwrittenSignatures_.makeImmutable();
                result.handwrittenSignatures_ = this.handwrittenSignatures_;
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
            if (other instanceof DotnetSettings) {
                return this.mergeFrom((DotnetSettings)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(DotnetSettings other) {
            if (other == DotnetSettings.getDefaultInstance()) {
                return this;
            }
            if (other.hasCommon()) {
                this.mergeCommon(other.getCommon());
            }
            this.internalGetMutableRenamedServices().mergeFrom(other.internalGetRenamedServices());
            this.bitField0_ |= 2;
            this.internalGetMutableRenamedResources().mergeFrom(other.internalGetRenamedResources());
            this.bitField0_ |= 4;
            if (!other.ignoredResources_.isEmpty()) {
                if (this.ignoredResources_.isEmpty()) {
                    this.ignoredResources_ = other.ignoredResources_;
                    this.bitField0_ |= 8;
                } else {
                    this.ensureIgnoredResourcesIsMutable();
                    this.ignoredResources_.addAll(other.ignoredResources_);
                }
                this.onChanged();
            }
            if (!other.forcedNamespaceAliases_.isEmpty()) {
                if (this.forcedNamespaceAliases_.isEmpty()) {
                    this.forcedNamespaceAliases_ = other.forcedNamespaceAliases_;
                    this.bitField0_ |= 0x10;
                } else {
                    this.ensureForcedNamespaceAliasesIsMutable();
                    this.forcedNamespaceAliases_.addAll(other.forcedNamespaceAliases_);
                }
                this.onChanged();
            }
            if (!other.handwrittenSignatures_.isEmpty()) {
                if (this.handwrittenSignatures_.isEmpty()) {
                    this.handwrittenSignatures_ = other.handwrittenSignatures_;
                    this.bitField0_ |= 0x20;
                } else {
                    this.ensureHandwrittenSignaturesIsMutable();
                    this.handwrittenSignatures_.addAll(other.handwrittenSignatures_);
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
                block14: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block14;
                        }
                        case 10: {
                            input.readMessage(this.getCommonFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 1;
                            continue block14;
                        }
                        case 18: {
                            MapEntry<String, String> renamedServices__ = input.readMessage(RenamedServicesDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                            this.internalGetMutableRenamedServices().getMutableMap().put(renamedServices__.getKey(), renamedServices__.getValue());
                            this.bitField0_ |= 2;
                            continue block14;
                        }
                        case 26: {
                            MapEntry<String, String> renamedResources__ = input.readMessage(RenamedResourcesDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                            this.internalGetMutableRenamedResources().getMutableMap().put(renamedResources__.getKey(), renamedResources__.getValue());
                            this.bitField0_ |= 4;
                            continue block14;
                        }
                        case 34: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensureIgnoredResourcesIsMutable();
                            this.ignoredResources_.add(s2);
                            continue block14;
                        }
                        case 42: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensureForcedNamespaceAliasesIsMutable();
                            this.forcedNamespaceAliases_.add(s2);
                            continue block14;
                        }
                        case 50: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensureHandwrittenSignaturesIsMutable();
                            this.handwrittenSignatures_.add(s2);
                            continue block14;
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
        public boolean hasCommon() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public CommonLanguageSettings getCommon() {
            if (this.commonBuilder_ == null) {
                return this.common_ == null ? CommonLanguageSettings.getDefaultInstance() : this.common_;
            }
            return this.commonBuilder_.getMessage();
        }

        public Builder setCommon(CommonLanguageSettings value) {
            if (this.commonBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.common_ = value;
            } else {
                this.commonBuilder_.setMessage(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder setCommon(CommonLanguageSettings.Builder builderForValue) {
            if (this.commonBuilder_ == null) {
                this.common_ = builderForValue.build();
            } else {
                this.commonBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder mergeCommon(CommonLanguageSettings value) {
            if (this.commonBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0 && this.common_ != null && this.common_ != CommonLanguageSettings.getDefaultInstance()) {
                    this.getCommonBuilder().mergeFrom(value);
                } else {
                    this.common_ = value;
                }
            } else {
                this.commonBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearCommon() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.common_ = null;
            if (this.commonBuilder_ != null) {
                this.commonBuilder_.dispose();
                this.commonBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public CommonLanguageSettings.Builder getCommonBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return this.getCommonFieldBuilder().getBuilder();
        }

        @Override
        public CommonLanguageSettingsOrBuilder getCommonOrBuilder() {
            if (this.commonBuilder_ != null) {
                return this.commonBuilder_.getMessageOrBuilder();
            }
            return this.common_ == null ? CommonLanguageSettings.getDefaultInstance() : this.common_;
        }

        private SingleFieldBuilderV3<CommonLanguageSettings, CommonLanguageSettings.Builder, CommonLanguageSettingsOrBuilder> getCommonFieldBuilder() {
            if (this.commonBuilder_ == null) {
                this.commonBuilder_ = new SingleFieldBuilderV3(this.getCommon(), this.getParentForChildren(), this.isClean());
                this.common_ = null;
            }
            return this.commonBuilder_;
        }

        private MapField<String, String> internalGetRenamedServices() {
            if (this.renamedServices_ == null) {
                return MapField.emptyMapField(RenamedServicesDefaultEntryHolder.defaultEntry);
            }
            return this.renamedServices_;
        }

        private MapField<String, String> internalGetMutableRenamedServices() {
            if (this.renamedServices_ == null) {
                this.renamedServices_ = MapField.newMapField(RenamedServicesDefaultEntryHolder.defaultEntry);
            }
            if (!this.renamedServices_.isMutable()) {
                this.renamedServices_ = this.renamedServices_.copy();
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this.renamedServices_;
        }

        @Override
        public int getRenamedServicesCount() {
            return this.internalGetRenamedServices().getMap().size();
        }

        @Override
        public boolean containsRenamedServices(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetRenamedServices().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, String> getRenamedServices() {
            return this.getRenamedServicesMap();
        }

        @Override
        public Map<String, String> getRenamedServicesMap() {
            return this.internalGetRenamedServices().getMap();
        }

        @Override
        public String getRenamedServicesOrDefault(String key, String defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetRenamedServices().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public String getRenamedServicesOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetRenamedServices().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        public Builder clearRenamedServices() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.internalGetMutableRenamedServices().getMutableMap().clear();
            return this;
        }

        public Builder removeRenamedServices(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            this.internalGetMutableRenamedServices().getMutableMap().remove(key);
            return this;
        }

        @Deprecated
        public Map<String, String> getMutableRenamedServices() {
            this.bitField0_ |= 2;
            return this.internalGetMutableRenamedServices().getMutableMap();
        }

        public Builder putRenamedServices(String key, String value) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            if (value == null) {
                throw new NullPointerException("map value");
            }
            this.internalGetMutableRenamedServices().getMutableMap().put(key, value);
            this.bitField0_ |= 2;
            return this;
        }

        public Builder putAllRenamedServices(Map<String, String> values2) {
            this.internalGetMutableRenamedServices().getMutableMap().putAll(values2);
            this.bitField0_ |= 2;
            return this;
        }

        private MapField<String, String> internalGetRenamedResources() {
            if (this.renamedResources_ == null) {
                return MapField.emptyMapField(RenamedResourcesDefaultEntryHolder.defaultEntry);
            }
            return this.renamedResources_;
        }

        private MapField<String, String> internalGetMutableRenamedResources() {
            if (this.renamedResources_ == null) {
                this.renamedResources_ = MapField.newMapField(RenamedResourcesDefaultEntryHolder.defaultEntry);
            }
            if (!this.renamedResources_.isMutable()) {
                this.renamedResources_ = this.renamedResources_.copy();
            }
            this.bitField0_ |= 4;
            this.onChanged();
            return this.renamedResources_;
        }

        @Override
        public int getRenamedResourcesCount() {
            return this.internalGetRenamedResources().getMap().size();
        }

        @Override
        public boolean containsRenamedResources(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetRenamedResources().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, String> getRenamedResources() {
            return this.getRenamedResourcesMap();
        }

        @Override
        public Map<String, String> getRenamedResourcesMap() {
            return this.internalGetRenamedResources().getMap();
        }

        @Override
        public String getRenamedResourcesOrDefault(String key, String defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetRenamedResources().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public String getRenamedResourcesOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetRenamedResources().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        public Builder clearRenamedResources() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.internalGetMutableRenamedResources().getMutableMap().clear();
            return this;
        }

        public Builder removeRenamedResources(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            this.internalGetMutableRenamedResources().getMutableMap().remove(key);
            return this;
        }

        @Deprecated
        public Map<String, String> getMutableRenamedResources() {
            this.bitField0_ |= 4;
            return this.internalGetMutableRenamedResources().getMutableMap();
        }

        public Builder putRenamedResources(String key, String value) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            if (value == null) {
                throw new NullPointerException("map value");
            }
            this.internalGetMutableRenamedResources().getMutableMap().put(key, value);
            this.bitField0_ |= 4;
            return this;
        }

        public Builder putAllRenamedResources(Map<String, String> values2) {
            this.internalGetMutableRenamedResources().getMutableMap().putAll(values2);
            this.bitField0_ |= 4;
            return this;
        }

        private void ensureIgnoredResourcesIsMutable() {
            if (!this.ignoredResources_.isModifiable()) {
                this.ignoredResources_ = new LazyStringArrayList(this.ignoredResources_);
            }
            this.bitField0_ |= 8;
        }

        public ProtocolStringList getIgnoredResourcesList() {
            this.ignoredResources_.makeImmutable();
            return this.ignoredResources_;
        }

        @Override
        public int getIgnoredResourcesCount() {
            return this.ignoredResources_.size();
        }

        @Override
        public String getIgnoredResources(int index) {
            return this.ignoredResources_.get(index);
        }

        @Override
        public ByteString getIgnoredResourcesBytes(int index) {
            return this.ignoredResources_.getByteString(index);
        }

        public Builder setIgnoredResources(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureIgnoredResourcesIsMutable();
            this.ignoredResources_.set(index, value);
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder addIgnoredResources(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureIgnoredResourcesIsMutable();
            this.ignoredResources_.add(value);
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder addAllIgnoredResources(Iterable<String> values2) {
            this.ensureIgnoredResourcesIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.ignoredResources_);
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearIgnoredResources() {
            this.ignoredResources_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFFF7;
            this.onChanged();
            return this;
        }

        public Builder addIgnoredResourcesBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            DotnetSettings.checkByteStringIsUtf8(value);
            this.ensureIgnoredResourcesIsMutable();
            this.ignoredResources_.add(value);
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        private void ensureForcedNamespaceAliasesIsMutable() {
            if (!this.forcedNamespaceAliases_.isModifiable()) {
                this.forcedNamespaceAliases_ = new LazyStringArrayList(this.forcedNamespaceAliases_);
            }
            this.bitField0_ |= 0x10;
        }

        public ProtocolStringList getForcedNamespaceAliasesList() {
            this.forcedNamespaceAliases_.makeImmutable();
            return this.forcedNamespaceAliases_;
        }

        @Override
        public int getForcedNamespaceAliasesCount() {
            return this.forcedNamespaceAliases_.size();
        }

        @Override
        public String getForcedNamespaceAliases(int index) {
            return this.forcedNamespaceAliases_.get(index);
        }

        @Override
        public ByteString getForcedNamespaceAliasesBytes(int index) {
            return this.forcedNamespaceAliases_.getByteString(index);
        }

        public Builder setForcedNamespaceAliases(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureForcedNamespaceAliasesIsMutable();
            this.forcedNamespaceAliases_.set(index, value);
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder addForcedNamespaceAliases(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureForcedNamespaceAliasesIsMutable();
            this.forcedNamespaceAliases_.add(value);
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder addAllForcedNamespaceAliases(Iterable<String> values2) {
            this.ensureForcedNamespaceAliasesIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.forcedNamespaceAliases_);
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearForcedNamespaceAliases() {
            this.forcedNamespaceAliases_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFFEF;
            this.onChanged();
            return this;
        }

        public Builder addForcedNamespaceAliasesBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            DotnetSettings.checkByteStringIsUtf8(value);
            this.ensureForcedNamespaceAliasesIsMutable();
            this.forcedNamespaceAliases_.add(value);
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        private void ensureHandwrittenSignaturesIsMutable() {
            if (!this.handwrittenSignatures_.isModifiable()) {
                this.handwrittenSignatures_ = new LazyStringArrayList(this.handwrittenSignatures_);
            }
            this.bitField0_ |= 0x20;
        }

        public ProtocolStringList getHandwrittenSignaturesList() {
            this.handwrittenSignatures_.makeImmutable();
            return this.handwrittenSignatures_;
        }

        @Override
        public int getHandwrittenSignaturesCount() {
            return this.handwrittenSignatures_.size();
        }

        @Override
        public String getHandwrittenSignatures(int index) {
            return this.handwrittenSignatures_.get(index);
        }

        @Override
        public ByteString getHandwrittenSignaturesBytes(int index) {
            return this.handwrittenSignatures_.getByteString(index);
        }

        public Builder setHandwrittenSignatures(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureHandwrittenSignaturesIsMutable();
            this.handwrittenSignatures_.set(index, value);
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder addHandwrittenSignatures(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureHandwrittenSignaturesIsMutable();
            this.handwrittenSignatures_.add(value);
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder addAllHandwrittenSignatures(Iterable<String> values2) {
            this.ensureHandwrittenSignaturesIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.handwrittenSignatures_);
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder clearHandwrittenSignatures() {
            this.handwrittenSignatures_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFFDF;
            this.onChanged();
            return this;
        }

        public Builder addHandwrittenSignaturesBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            DotnetSettings.checkByteStringIsUtf8(value);
            this.ensureHandwrittenSignaturesIsMutable();
            this.handwrittenSignatures_.add(value);
            this.bitField0_ |= 0x20;
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

    private static final class RenamedServicesDefaultEntryHolder {
        static final MapEntry<String, String> defaultEntry = MapEntry.newDefaultInstance(ClientProto.internal_static_google_api_DotnetSettings_RenamedServicesEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");

        private RenamedServicesDefaultEntryHolder() {
        }
    }

    private static final class RenamedResourcesDefaultEntryHolder {
        static final MapEntry<String, String> defaultEntry = MapEntry.newDefaultInstance(ClientProto.internal_static_google_api_DotnetSettings_RenamedResourcesEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");

        private RenamedResourcesDefaultEntryHolder() {
        }
    }
}

