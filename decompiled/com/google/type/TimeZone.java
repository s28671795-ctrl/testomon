/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.type.DateTimeProto;
import com.google.type.TimeZoneOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class TimeZone
extends GeneratedMessageV3
implements TimeZoneOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int ID_FIELD_NUMBER = 1;
    private volatile Object id_ = "";
    public static final int VERSION_FIELD_NUMBER = 2;
    private volatile Object version_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final TimeZone DEFAULT_INSTANCE = new TimeZone();
    private static final Parser<TimeZone> PARSER = new AbstractParser<TimeZone>(){

        @Override
        public TimeZone parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = TimeZone.newBuilder();
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

    private TimeZone(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private TimeZone() {
        this.id_ = "";
        this.version_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new TimeZone();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return DateTimeProto.internal_static_google_type_TimeZone_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return DateTimeProto.internal_static_google_type_TimeZone_fieldAccessorTable.ensureFieldAccessorsInitialized(TimeZone.class, Builder.class);
    }

    @Override
    public String getId() {
        Object ref = this.id_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.id_ = s2;
        return s2;
    }

    @Override
    public ByteString getIdBytes() {
        Object ref = this.id_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.id_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getVersion() {
        Object ref = this.version_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.version_ = s2;
        return s2;
    }

    @Override
    public ByteString getVersionBytes() {
        Object ref = this.version_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.version_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.id_)) {
            GeneratedMessageV3.writeString(output, 1, this.id_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.version_)) {
            GeneratedMessageV3.writeString(output, 2, this.version_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.id_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.id_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.version_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.version_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TimeZone)) {
            return super.equals(obj);
        }
        TimeZone other = (TimeZone)obj;
        if (!this.getId().equals(other.getId())) {
            return false;
        }
        if (!this.getVersion().equals(other.getVersion())) {
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
        hash = 19 * hash + TimeZone.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getId().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getVersion().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static TimeZone parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static TimeZone parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static TimeZone parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static TimeZone parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static TimeZone parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static TimeZone parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static TimeZone parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static TimeZone parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static TimeZone parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static TimeZone parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static TimeZone parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static TimeZone parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return TimeZone.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(TimeZone prototype) {
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

    public static TimeZone getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<TimeZone> parser() {
        return PARSER;
    }

    public Parser<TimeZone> getParserForType() {
        return PARSER;
    }

    @Override
    public TimeZone getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements TimeZoneOrBuilder {
        private int bitField0_;
        private Object id_ = "";
        private Object version_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return DateTimeProto.internal_static_google_type_TimeZone_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return DateTimeProto.internal_static_google_type_TimeZone_fieldAccessorTable.ensureFieldAccessorsInitialized(TimeZone.class, Builder.class);
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
            this.id_ = "";
            this.version_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return DateTimeProto.internal_static_google_type_TimeZone_descriptor;
        }

        @Override
        public TimeZone getDefaultInstanceForType() {
            return TimeZone.getDefaultInstance();
        }

        @Override
        public TimeZone build() {
            TimeZone result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public TimeZone buildPartial() {
            TimeZone result = new TimeZone(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(TimeZone result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.id_ = this.id_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.version_ = this.version_;
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
            if (other instanceof TimeZone) {
                return this.mergeFrom((TimeZone)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(TimeZone other) {
            if (other == TimeZone.getDefaultInstance()) {
                return this;
            }
            if (!other.getId().isEmpty()) {
                this.id_ = other.id_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getVersion().isEmpty()) {
                this.version_ = other.version_;
                this.bitField0_ |= 2;
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
                            this.id_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            this.version_ = input.readStringRequireUtf8();
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
        public String getId() {
            Object ref = this.id_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.id_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getIdBytes() {
            Object ref = this.id_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.id_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setId(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.id_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearId() {
            this.id_ = TimeZone.getDefaultInstance().getId();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setIdBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            TimeZone.checkByteStringIsUtf8(value);
            this.id_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getVersion() {
            Object ref = this.version_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.version_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getVersionBytes() {
            Object ref = this.version_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.version_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setVersion(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.version_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearVersion() {
            this.version_ = TimeZone.getDefaultInstance().getVersion();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setVersionBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            TimeZone.checkByteStringIsUtf8(value);
            this.version_ = value;
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

