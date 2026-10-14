/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ClientLibraryDestination;
import com.google.api.ClientProto;
import com.google.api.CommonLanguageSettingsOrBuilder;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CommonLanguageSettings
extends GeneratedMessageV3
implements CommonLanguageSettingsOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int REFERENCE_DOCS_URI_FIELD_NUMBER = 1;
    private volatile Object referenceDocsUri_ = "";
    public static final int DESTINATIONS_FIELD_NUMBER = 2;
    private List<Integer> destinations_;
    private static final Internal.ListAdapter.Converter<Integer, ClientLibraryDestination> destinations_converter_ = new Internal.ListAdapter.Converter<Integer, ClientLibraryDestination>(){

        @Override
        public ClientLibraryDestination convert(Integer from) {
            ClientLibraryDestination result = ClientLibraryDestination.forNumber(from);
            return result == null ? ClientLibraryDestination.UNRECOGNIZED : result;
        }
    };
    private int destinationsMemoizedSerializedSize;
    private byte memoizedIsInitialized = (byte)-1;
    private static final CommonLanguageSettings DEFAULT_INSTANCE = new CommonLanguageSettings();
    private static final Parser<CommonLanguageSettings> PARSER = new AbstractParser<CommonLanguageSettings>(){

        @Override
        public CommonLanguageSettings parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = CommonLanguageSettings.newBuilder();
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

    private CommonLanguageSettings(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private CommonLanguageSettings() {
        this.referenceDocsUri_ = "";
        this.destinations_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new CommonLanguageSettings();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ClientProto.internal_static_google_api_CommonLanguageSettings_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ClientProto.internal_static_google_api_CommonLanguageSettings_fieldAccessorTable.ensureFieldAccessorsInitialized(CommonLanguageSettings.class, Builder.class);
    }

    @Override
    @Deprecated
    public String getReferenceDocsUri() {
        Object ref = this.referenceDocsUri_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.referenceDocsUri_ = s2;
        return s2;
    }

    @Override
    @Deprecated
    public ByteString getReferenceDocsUriBytes() {
        Object ref = this.referenceDocsUri_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.referenceDocsUri_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public List<ClientLibraryDestination> getDestinationsList() {
        return new Internal.ListAdapter<Integer, ClientLibraryDestination>(this.destinations_, destinations_converter_);
    }

    @Override
    public int getDestinationsCount() {
        return this.destinations_.size();
    }

    @Override
    public ClientLibraryDestination getDestinations(int index) {
        return destinations_converter_.convert(this.destinations_.get(index));
    }

    @Override
    public List<Integer> getDestinationsValueList() {
        return this.destinations_;
    }

    @Override
    public int getDestinationsValue(int index) {
        return this.destinations_.get(index);
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
        if (!GeneratedMessageV3.isStringEmpty(this.referenceDocsUri_)) {
            GeneratedMessageV3.writeString(output, 1, this.referenceDocsUri_);
        }
        if (this.getDestinationsList().size() > 0) {
            output.writeUInt32NoTag(18);
            output.writeUInt32NoTag(this.destinationsMemoizedSerializedSize);
        }
        for (int i = 0; i < this.destinations_.size(); ++i) {
            output.writeEnumNoTag(this.destinations_.get(i));
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
        if (!GeneratedMessageV3.isStringEmpty(this.referenceDocsUri_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.referenceDocsUri_);
        }
        int dataSize = 0;
        for (int i = 0; i < this.destinations_.size(); ++i) {
            dataSize += CodedOutputStream.computeEnumSizeNoTag(this.destinations_.get(i));
        }
        size += dataSize;
        if (!this.getDestinationsList().isEmpty()) {
            ++size;
            size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        }
        this.destinationsMemoizedSerializedSize = dataSize;
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CommonLanguageSettings)) {
            return super.equals(obj);
        }
        CommonLanguageSettings other = (CommonLanguageSettings)obj;
        if (!this.getReferenceDocsUri().equals(other.getReferenceDocsUri())) {
            return false;
        }
        if (!this.destinations_.equals(other.destinations_)) {
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
        hash = 19 * hash + CommonLanguageSettings.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getReferenceDocsUri().hashCode();
        if (this.getDestinationsCount() > 0) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.destinations_.hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static CommonLanguageSettings parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static CommonLanguageSettings parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static CommonLanguageSettings parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static CommonLanguageSettings parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static CommonLanguageSettings parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static CommonLanguageSettings parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static CommonLanguageSettings parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static CommonLanguageSettings parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static CommonLanguageSettings parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static CommonLanguageSettings parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static CommonLanguageSettings parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static CommonLanguageSettings parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return CommonLanguageSettings.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(CommonLanguageSettings prototype) {
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

    public static CommonLanguageSettings getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<CommonLanguageSettings> parser() {
        return PARSER;
    }

    public Parser<CommonLanguageSettings> getParserForType() {
        return PARSER;
    }

    @Override
    public CommonLanguageSettings getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements CommonLanguageSettingsOrBuilder {
        private int bitField0_;
        private Object referenceDocsUri_ = "";
        private List<Integer> destinations_ = Collections.emptyList();

        public static final Descriptors.Descriptor getDescriptor() {
            return ClientProto.internal_static_google_api_CommonLanguageSettings_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ClientProto.internal_static_google_api_CommonLanguageSettings_fieldAccessorTable.ensureFieldAccessorsInitialized(CommonLanguageSettings.class, Builder.class);
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
            this.referenceDocsUri_ = "";
            this.destinations_ = Collections.emptyList();
            this.bitField0_ &= 0xFFFFFFFD;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ClientProto.internal_static_google_api_CommonLanguageSettings_descriptor;
        }

        @Override
        public CommonLanguageSettings getDefaultInstanceForType() {
            return CommonLanguageSettings.getDefaultInstance();
        }

        @Override
        public CommonLanguageSettings build() {
            CommonLanguageSettings result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public CommonLanguageSettings buildPartial() {
            CommonLanguageSettings result = new CommonLanguageSettings(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(CommonLanguageSettings result) {
            if ((this.bitField0_ & 2) != 0) {
                this.destinations_ = Collections.unmodifiableList(this.destinations_);
                this.bitField0_ &= 0xFFFFFFFD;
            }
            result.destinations_ = this.destinations_;
        }

        private void buildPartial0(CommonLanguageSettings result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.referenceDocsUri_ = this.referenceDocsUri_;
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
            if (other instanceof CommonLanguageSettings) {
                return this.mergeFrom((CommonLanguageSettings)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(CommonLanguageSettings other) {
            if (other == CommonLanguageSettings.getDefaultInstance()) {
                return this;
            }
            if (!other.getReferenceDocsUri().isEmpty()) {
                this.referenceDocsUri_ = other.referenceDocsUri_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.destinations_.isEmpty()) {
                if (this.destinations_.isEmpty()) {
                    this.destinations_ = other.destinations_;
                    this.bitField0_ &= 0xFFFFFFFD;
                } else {
                    this.ensureDestinationsIsMutable();
                    this.destinations_.addAll(other.destinations_);
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
                block11: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block11;
                        }
                        case 10: {
                            this.referenceDocsUri_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block11;
                        }
                        case 16: {
                            int tmpRaw = input.readEnum();
                            this.ensureDestinationsIsMutable();
                            this.destinations_.add(tmpRaw);
                            continue block11;
                        }
                        case 18: {
                            int length = input.readRawVarint32();
                            int oldLimit = input.pushLimit(length);
                            while (input.getBytesUntilLimit() > 0) {
                                int tmpRaw = input.readEnum();
                                this.ensureDestinationsIsMutable();
                                this.destinations_.add(tmpRaw);
                            }
                            input.popLimit(oldLimit);
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
        public String getReferenceDocsUri() {
            Object ref = this.referenceDocsUri_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.referenceDocsUri_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        @Deprecated
        public ByteString getReferenceDocsUriBytes() {
            Object ref = this.referenceDocsUri_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.referenceDocsUri_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Deprecated
        public Builder setReferenceDocsUri(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.referenceDocsUri_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Deprecated
        public Builder clearReferenceDocsUri() {
            this.referenceDocsUri_ = CommonLanguageSettings.getDefaultInstance().getReferenceDocsUri();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        @Deprecated
        public Builder setReferenceDocsUriBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            CommonLanguageSettings.checkByteStringIsUtf8(value);
            this.referenceDocsUri_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        private void ensureDestinationsIsMutable() {
            if ((this.bitField0_ & 2) == 0) {
                this.destinations_ = new ArrayList<Integer>(this.destinations_);
                this.bitField0_ |= 2;
            }
        }

        @Override
        public List<ClientLibraryDestination> getDestinationsList() {
            return new Internal.ListAdapter(this.destinations_, destinations_converter_);
        }

        @Override
        public int getDestinationsCount() {
            return this.destinations_.size();
        }

        @Override
        public ClientLibraryDestination getDestinations(int index) {
            return (ClientLibraryDestination)destinations_converter_.convert(this.destinations_.get(index));
        }

        public Builder setDestinations(int index, ClientLibraryDestination value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureDestinationsIsMutable();
            this.destinations_.set(index, value.getNumber());
            this.onChanged();
            return this;
        }

        public Builder addDestinations(ClientLibraryDestination value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureDestinationsIsMutable();
            this.destinations_.add(value.getNumber());
            this.onChanged();
            return this;
        }

        public Builder addAllDestinations(Iterable<? extends ClientLibraryDestination> values2) {
            this.ensureDestinationsIsMutable();
            for (ClientLibraryDestination clientLibraryDestination : values2) {
                this.destinations_.add(clientLibraryDestination.getNumber());
            }
            this.onChanged();
            return this;
        }

        public Builder clearDestinations() {
            this.destinations_ = Collections.emptyList();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        @Override
        public List<Integer> getDestinationsValueList() {
            return Collections.unmodifiableList(this.destinations_);
        }

        @Override
        public int getDestinationsValue(int index) {
            return this.destinations_.get(index);
        }

        public Builder setDestinationsValue(int index, int value) {
            this.ensureDestinationsIsMutable();
            this.destinations_.set(index, value);
            this.onChanged();
            return this;
        }

        public Builder addDestinationsValue(int value) {
            this.ensureDestinationsIsMutable();
            this.destinations_.add(value);
            this.onChanged();
            return this;
        }

        public Builder addAllDestinationsValue(Iterable<Integer> values2) {
            this.ensureDestinationsIsMutable();
            for (int value : values2) {
                this.destinations_.add(value);
            }
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

