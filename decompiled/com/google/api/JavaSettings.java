/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ClientProto;
import com.google.api.CommonLanguageSettings;
import com.google.api.CommonLanguageSettingsOrBuilder;
import com.google.api.JavaSettingsOrBuilder;
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
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

public final class JavaSettings
extends GeneratedMessageV3
implements JavaSettingsOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int LIBRARY_PACKAGE_FIELD_NUMBER = 1;
    private volatile Object libraryPackage_ = "";
    public static final int SERVICE_CLASS_NAMES_FIELD_NUMBER = 2;
    private MapField<String, String> serviceClassNames_;
    public static final int COMMON_FIELD_NUMBER = 3;
    private CommonLanguageSettings common_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final JavaSettings DEFAULT_INSTANCE = new JavaSettings();
    private static final Parser<JavaSettings> PARSER = new AbstractParser<JavaSettings>(){

        @Override
        public JavaSettings parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = JavaSettings.newBuilder();
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

    private JavaSettings(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private JavaSettings() {
        this.libraryPackage_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new JavaSettings();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ClientProto.internal_static_google_api_JavaSettings_descriptor;
    }

    @Override
    protected MapField internalGetMapField(int number) {
        switch (number) {
            case 2: {
                return this.internalGetServiceClassNames();
            }
        }
        throw new RuntimeException("Invalid map field number: " + number);
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ClientProto.internal_static_google_api_JavaSettings_fieldAccessorTable.ensureFieldAccessorsInitialized(JavaSettings.class, Builder.class);
    }

    @Override
    public String getLibraryPackage() {
        Object ref = this.libraryPackage_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.libraryPackage_ = s2;
        return s2;
    }

    @Override
    public ByteString getLibraryPackageBytes() {
        Object ref = this.libraryPackage_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.libraryPackage_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    private MapField<String, String> internalGetServiceClassNames() {
        if (this.serviceClassNames_ == null) {
            return MapField.emptyMapField(ServiceClassNamesDefaultEntryHolder.defaultEntry);
        }
        return this.serviceClassNames_;
    }

    @Override
    public int getServiceClassNamesCount() {
        return this.internalGetServiceClassNames().getMap().size();
    }

    @Override
    public boolean containsServiceClassNames(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        return this.internalGetServiceClassNames().getMap().containsKey(key);
    }

    @Override
    @Deprecated
    public Map<String, String> getServiceClassNames() {
        return this.getServiceClassNamesMap();
    }

    @Override
    public Map<String, String> getServiceClassNamesMap() {
        return this.internalGetServiceClassNames().getMap();
    }

    @Override
    public String getServiceClassNamesOrDefault(String key, String defaultValue) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, String> map = this.internalGetServiceClassNames().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    @Override
    public String getServiceClassNamesOrThrow(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, String> map = this.internalGetServiceClassNames().getMap();
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return map.get(key);
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
        if (!GeneratedMessageV3.isStringEmpty(this.libraryPackage_)) {
            GeneratedMessageV3.writeString(output, 1, this.libraryPackage_);
        }
        GeneratedMessageV3.serializeStringMapTo(output, this.internalGetServiceClassNames(), ServiceClassNamesDefaultEntryHolder.defaultEntry, 2);
        if (this.common_ != null) {
            output.writeMessage(3, this.getCommon());
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
        if (!GeneratedMessageV3.isStringEmpty(this.libraryPackage_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.libraryPackage_);
        }
        for (Map.Entry<String, String> entry : this.internalGetServiceClassNames().getMap().entrySet()) {
            Message serviceClassNames__ = ((MapEntry.Builder)ServiceClassNamesDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
            size += CodedOutputStream.computeMessageSize(2, serviceClassNames__);
        }
        if (this.common_ != null) {
            size += CodedOutputStream.computeMessageSize(3, this.getCommon());
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof JavaSettings)) {
            return super.equals(obj);
        }
        JavaSettings other = (JavaSettings)obj;
        if (!this.getLibraryPackage().equals(other.getLibraryPackage())) {
            return false;
        }
        if (!this.internalGetServiceClassNames().equals(other.internalGetServiceClassNames())) {
            return false;
        }
        if (this.hasCommon() != other.hasCommon()) {
            return false;
        }
        if (this.hasCommon() && !this.getCommon().equals(other.getCommon())) {
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
        hash = 19 * hash + JavaSettings.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getLibraryPackage().hashCode();
        if (!this.internalGetServiceClassNames().getMap().isEmpty()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.internalGetServiceClassNames().hashCode();
        }
        if (this.hasCommon()) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getCommon().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static JavaSettings parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static JavaSettings parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static JavaSettings parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static JavaSettings parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static JavaSettings parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static JavaSettings parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static JavaSettings parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static JavaSettings parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static JavaSettings parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static JavaSettings parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static JavaSettings parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static JavaSettings parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return JavaSettings.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(JavaSettings prototype) {
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

    public static JavaSettings getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<JavaSettings> parser() {
        return PARSER;
    }

    public Parser<JavaSettings> getParserForType() {
        return PARSER;
    }

    @Override
    public JavaSettings getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements JavaSettingsOrBuilder {
        private int bitField0_;
        private Object libraryPackage_ = "";
        private MapField<String, String> serviceClassNames_;
        private CommonLanguageSettings common_;
        private SingleFieldBuilderV3<CommonLanguageSettings, CommonLanguageSettings.Builder, CommonLanguageSettingsOrBuilder> commonBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return ClientProto.internal_static_google_api_JavaSettings_descriptor;
        }

        @Override
        protected MapField internalGetMapField(int number) {
            switch (number) {
                case 2: {
                    return this.internalGetServiceClassNames();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected MapField internalGetMutableMapField(int number) {
            switch (number) {
                case 2: {
                    return this.internalGetMutableServiceClassNames();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ClientProto.internal_static_google_api_JavaSettings_fieldAccessorTable.ensureFieldAccessorsInitialized(JavaSettings.class, Builder.class);
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
            this.libraryPackage_ = "";
            this.internalGetMutableServiceClassNames().clear();
            this.common_ = null;
            if (this.commonBuilder_ != null) {
                this.commonBuilder_.dispose();
                this.commonBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ClientProto.internal_static_google_api_JavaSettings_descriptor;
        }

        @Override
        public JavaSettings getDefaultInstanceForType() {
            return JavaSettings.getDefaultInstance();
        }

        @Override
        public JavaSettings build() {
            JavaSettings result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public JavaSettings buildPartial() {
            JavaSettings result = new JavaSettings(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(JavaSettings result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.libraryPackage_ = this.libraryPackage_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.serviceClassNames_ = this.internalGetServiceClassNames();
                result.serviceClassNames_.makeImmutable();
            }
            if ((from_bitField0_ & 4) != 0) {
                result.common_ = this.commonBuilder_ == null ? this.common_ : this.commonBuilder_.build();
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
            if (other instanceof JavaSettings) {
                return this.mergeFrom((JavaSettings)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(JavaSettings other) {
            if (other == JavaSettings.getDefaultInstance()) {
                return this;
            }
            if (!other.getLibraryPackage().isEmpty()) {
                this.libraryPackage_ = other.libraryPackage_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            this.internalGetMutableServiceClassNames().mergeFrom(other.internalGetServiceClassNames());
            this.bitField0_ |= 2;
            if (other.hasCommon()) {
                this.mergeCommon(other.getCommon());
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
                            this.libraryPackage_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block11;
                        }
                        case 18: {
                            MapEntry<String, String> serviceClassNames__ = input.readMessage(ServiceClassNamesDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                            this.internalGetMutableServiceClassNames().getMutableMap().put(serviceClassNames__.getKey(), serviceClassNames__.getValue());
                            this.bitField0_ |= 2;
                            continue block11;
                        }
                        case 26: {
                            input.readMessage(this.getCommonFieldBuilder().getBuilder(), extensionRegistry);
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
        public String getLibraryPackage() {
            Object ref = this.libraryPackage_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.libraryPackage_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getLibraryPackageBytes() {
            Object ref = this.libraryPackage_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.libraryPackage_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setLibraryPackage(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.libraryPackage_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearLibraryPackage() {
            this.libraryPackage_ = JavaSettings.getDefaultInstance().getLibraryPackage();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setLibraryPackageBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            JavaSettings.checkByteStringIsUtf8(value);
            this.libraryPackage_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        private MapField<String, String> internalGetServiceClassNames() {
            if (this.serviceClassNames_ == null) {
                return MapField.emptyMapField(ServiceClassNamesDefaultEntryHolder.defaultEntry);
            }
            return this.serviceClassNames_;
        }

        private MapField<String, String> internalGetMutableServiceClassNames() {
            if (this.serviceClassNames_ == null) {
                this.serviceClassNames_ = MapField.newMapField(ServiceClassNamesDefaultEntryHolder.defaultEntry);
            }
            if (!this.serviceClassNames_.isMutable()) {
                this.serviceClassNames_ = this.serviceClassNames_.copy();
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this.serviceClassNames_;
        }

        @Override
        public int getServiceClassNamesCount() {
            return this.internalGetServiceClassNames().getMap().size();
        }

        @Override
        public boolean containsServiceClassNames(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetServiceClassNames().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, String> getServiceClassNames() {
            return this.getServiceClassNamesMap();
        }

        @Override
        public Map<String, String> getServiceClassNamesMap() {
            return this.internalGetServiceClassNames().getMap();
        }

        @Override
        public String getServiceClassNamesOrDefault(String key, String defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetServiceClassNames().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public String getServiceClassNamesOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetServiceClassNames().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        public Builder clearServiceClassNames() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.internalGetMutableServiceClassNames().getMutableMap().clear();
            return this;
        }

        public Builder removeServiceClassNames(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            this.internalGetMutableServiceClassNames().getMutableMap().remove(key);
            return this;
        }

        @Deprecated
        public Map<String, String> getMutableServiceClassNames() {
            this.bitField0_ |= 2;
            return this.internalGetMutableServiceClassNames().getMutableMap();
        }

        public Builder putServiceClassNames(String key, String value) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            if (value == null) {
                throw new NullPointerException("map value");
            }
            this.internalGetMutableServiceClassNames().getMutableMap().put(key, value);
            this.bitField0_ |= 2;
            return this;
        }

        public Builder putAllServiceClassNames(Map<String, String> values2) {
            this.internalGetMutableServiceClassNames().getMutableMap().putAll(values2);
            this.bitField0_ |= 2;
            return this;
        }

        @Override
        public boolean hasCommon() {
            return (this.bitField0_ & 4) != 0;
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
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder setCommon(CommonLanguageSettings.Builder builderForValue) {
            if (this.commonBuilder_ == null) {
                this.common_ = builderForValue.build();
            } else {
                this.commonBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder mergeCommon(CommonLanguageSettings value) {
            if (this.commonBuilder_ == null) {
                if ((this.bitField0_ & 4) != 0 && this.common_ != null && this.common_ != CommonLanguageSettings.getDefaultInstance()) {
                    this.getCommonBuilder().mergeFrom(value);
                } else {
                    this.common_ = value;
                }
            } else {
                this.commonBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearCommon() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.common_ = null;
            if (this.commonBuilder_ != null) {
                this.commonBuilder_.dispose();
                this.commonBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public CommonLanguageSettings.Builder getCommonBuilder() {
            this.bitField0_ |= 4;
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

        @Override
        public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder)super.setUnknownFields(unknownFields);
        }

        @Override
        public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder)super.mergeUnknownFields(unknownFields);
        }
    }

    private static final class ServiceClassNamesDefaultEntryHolder {
        static final MapEntry<String, String> defaultEntry = MapEntry.newDefaultInstance(ClientProto.internal_static_google_api_JavaSettings_ServiceClassNamesEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");

        private ServiceClassNamesDefaultEntryHolder() {
        }
    }
}

