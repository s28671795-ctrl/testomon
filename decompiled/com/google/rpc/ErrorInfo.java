/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

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
import com.google.rpc.ErrorDetailsProto;
import com.google.rpc.ErrorInfoOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

public final class ErrorInfo
extends GeneratedMessageV3
implements ErrorInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int REASON_FIELD_NUMBER = 1;
    private volatile Object reason_ = "";
    public static final int DOMAIN_FIELD_NUMBER = 2;
    private volatile Object domain_ = "";
    public static final int METADATA_FIELD_NUMBER = 3;
    private MapField<String, String> metadata_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final ErrorInfo DEFAULT_INSTANCE = new ErrorInfo();
    private static final Parser<ErrorInfo> PARSER = new AbstractParser<ErrorInfo>(){

        @Override
        public ErrorInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = ErrorInfo.newBuilder();
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

    private ErrorInfo(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private ErrorInfo() {
        this.reason_ = "";
        this.domain_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new ErrorInfo();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ErrorDetailsProto.internal_static_google_rpc_ErrorInfo_descriptor;
    }

    @Override
    protected MapField internalGetMapField(int number) {
        switch (number) {
            case 3: {
                return this.internalGetMetadata();
            }
        }
        throw new RuntimeException("Invalid map field number: " + number);
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ErrorDetailsProto.internal_static_google_rpc_ErrorInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(ErrorInfo.class, Builder.class);
    }

    @Override
    public String getReason() {
        Object ref = this.reason_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.reason_ = s2;
        return s2;
    }

    @Override
    public ByteString getReasonBytes() {
        Object ref = this.reason_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.reason_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getDomain() {
        Object ref = this.domain_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.domain_ = s2;
        return s2;
    }

    @Override
    public ByteString getDomainBytes() {
        Object ref = this.domain_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.domain_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    private MapField<String, String> internalGetMetadata() {
        if (this.metadata_ == null) {
            return MapField.emptyMapField(MetadataDefaultEntryHolder.defaultEntry);
        }
        return this.metadata_;
    }

    @Override
    public int getMetadataCount() {
        return this.internalGetMetadata().getMap().size();
    }

    @Override
    public boolean containsMetadata(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        return this.internalGetMetadata().getMap().containsKey(key);
    }

    @Override
    @Deprecated
    public Map<String, String> getMetadata() {
        return this.getMetadataMap();
    }

    @Override
    public Map<String, String> getMetadataMap() {
        return this.internalGetMetadata().getMap();
    }

    @Override
    public String getMetadataOrDefault(String key, String defaultValue) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, String> map = this.internalGetMetadata().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    @Override
    public String getMetadataOrThrow(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, String> map = this.internalGetMetadata().getMap();
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
        if (!GeneratedMessageV3.isStringEmpty(this.reason_)) {
            GeneratedMessageV3.writeString(output, 1, this.reason_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.domain_)) {
            GeneratedMessageV3.writeString(output, 2, this.domain_);
        }
        GeneratedMessageV3.serializeStringMapTo(output, this.internalGetMetadata(), MetadataDefaultEntryHolder.defaultEntry, 3);
        this.getUnknownFields().writeTo(output);
    }

    @Override
    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        if (!GeneratedMessageV3.isStringEmpty(this.reason_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.reason_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.domain_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.domain_);
        }
        for (Map.Entry<String, String> entry : this.internalGetMetadata().getMap().entrySet()) {
            Message metadata__ = ((MapEntry.Builder)MetadataDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
            size += CodedOutputStream.computeMessageSize(3, metadata__);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ErrorInfo)) {
            return super.equals(obj);
        }
        ErrorInfo other = (ErrorInfo)obj;
        if (!this.getReason().equals(other.getReason())) {
            return false;
        }
        if (!this.getDomain().equals(other.getDomain())) {
            return false;
        }
        if (!this.internalGetMetadata().equals(other.internalGetMetadata())) {
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
        hash = 19 * hash + ErrorInfo.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getReason().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getDomain().hashCode();
        if (!this.internalGetMetadata().getMap().isEmpty()) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.internalGetMetadata().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static ErrorInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ErrorInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ErrorInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ErrorInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ErrorInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ErrorInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ErrorInfo parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ErrorInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ErrorInfo parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ErrorInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ErrorInfo parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ErrorInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return ErrorInfo.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ErrorInfo prototype) {
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

    public static ErrorInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ErrorInfo> parser() {
        return PARSER;
    }

    public Parser<ErrorInfo> getParserForType() {
        return PARSER;
    }

    @Override
    public ErrorInfo getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ErrorInfoOrBuilder {
        private int bitField0_;
        private Object reason_ = "";
        private Object domain_ = "";
        private MapField<String, String> metadata_;

        public static final Descriptors.Descriptor getDescriptor() {
            return ErrorDetailsProto.internal_static_google_rpc_ErrorInfo_descriptor;
        }

        @Override
        protected MapField internalGetMapField(int number) {
            switch (number) {
                case 3: {
                    return this.internalGetMetadata();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected MapField internalGetMutableMapField(int number) {
            switch (number) {
                case 3: {
                    return this.internalGetMutableMetadata();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ErrorDetailsProto.internal_static_google_rpc_ErrorInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(ErrorInfo.class, Builder.class);
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
            this.reason_ = "";
            this.domain_ = "";
            this.internalGetMutableMetadata().clear();
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ErrorDetailsProto.internal_static_google_rpc_ErrorInfo_descriptor;
        }

        @Override
        public ErrorInfo getDefaultInstanceForType() {
            return ErrorInfo.getDefaultInstance();
        }

        @Override
        public ErrorInfo build() {
            ErrorInfo result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ErrorInfo buildPartial() {
            ErrorInfo result = new ErrorInfo(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(ErrorInfo result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.reason_ = this.reason_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.domain_ = this.domain_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.metadata_ = this.internalGetMetadata();
                result.metadata_.makeImmutable();
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
            if (other instanceof ErrorInfo) {
                return this.mergeFrom((ErrorInfo)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ErrorInfo other) {
            if (other == ErrorInfo.getDefaultInstance()) {
                return this;
            }
            if (!other.getReason().isEmpty()) {
                this.reason_ = other.reason_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getDomain().isEmpty()) {
                this.domain_ = other.domain_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            this.internalGetMutableMetadata().mergeFrom(other.internalGetMetadata());
            this.bitField0_ |= 4;
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
                            this.reason_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block11;
                        }
                        case 18: {
                            this.domain_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block11;
                        }
                        case 26: {
                            MapEntry<String, String> metadata__ = input.readMessage(MetadataDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                            this.internalGetMutableMetadata().getMutableMap().put(metadata__.getKey(), metadata__.getValue());
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
        public String getReason() {
            Object ref = this.reason_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.reason_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getReasonBytes() {
            Object ref = this.reason_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.reason_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setReason(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.reason_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearReason() {
            this.reason_ = ErrorInfo.getDefaultInstance().getReason();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setReasonBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ErrorInfo.checkByteStringIsUtf8(value);
            this.reason_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getDomain() {
            Object ref = this.domain_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.domain_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getDomainBytes() {
            Object ref = this.domain_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.domain_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setDomain(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.domain_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearDomain() {
            this.domain_ = ErrorInfo.getDefaultInstance().getDomain();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setDomainBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ErrorInfo.checkByteStringIsUtf8(value);
            this.domain_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        private MapField<String, String> internalGetMetadata() {
            if (this.metadata_ == null) {
                return MapField.emptyMapField(MetadataDefaultEntryHolder.defaultEntry);
            }
            return this.metadata_;
        }

        private MapField<String, String> internalGetMutableMetadata() {
            if (this.metadata_ == null) {
                this.metadata_ = MapField.newMapField(MetadataDefaultEntryHolder.defaultEntry);
            }
            if (!this.metadata_.isMutable()) {
                this.metadata_ = this.metadata_.copy();
            }
            this.bitField0_ |= 4;
            this.onChanged();
            return this.metadata_;
        }

        @Override
        public int getMetadataCount() {
            return this.internalGetMetadata().getMap().size();
        }

        @Override
        public boolean containsMetadata(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetMetadata().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, String> getMetadata() {
            return this.getMetadataMap();
        }

        @Override
        public Map<String, String> getMetadataMap() {
            return this.internalGetMetadata().getMap();
        }

        @Override
        public String getMetadataOrDefault(String key, String defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetMetadata().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public String getMetadataOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetMetadata().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        public Builder clearMetadata() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.internalGetMutableMetadata().getMutableMap().clear();
            return this;
        }

        public Builder removeMetadata(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            this.internalGetMutableMetadata().getMutableMap().remove(key);
            return this;
        }

        @Deprecated
        public Map<String, String> getMutableMetadata() {
            this.bitField0_ |= 4;
            return this.internalGetMutableMetadata().getMutableMap();
        }

        public Builder putMetadata(String key, String value) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            if (value == null) {
                throw new NullPointerException("map value");
            }
            this.internalGetMutableMetadata().getMutableMap().put(key, value);
            this.bitField0_ |= 4;
            return this;
        }

        public Builder putAllMetadata(Map<String, String> values2) {
            this.internalGetMutableMetadata().getMutableMap().putAll(values2);
            this.bitField0_ |= 4;
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

    private static final class MetadataDefaultEntryHolder {
        static final MapEntry<String, String> defaultEntry = MapEntry.newDefaultInstance(ErrorDetailsProto.internal_static_google_rpc_ErrorInfo_MetadataEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");

        private MetadataDefaultEntryHolder() {
        }
    }
}

