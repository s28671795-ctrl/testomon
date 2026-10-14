/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.CustomHttpPatternOrBuilder;
import com.google.api.HttpProto;
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
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class CustomHttpPattern
extends GeneratedMessageV3
implements CustomHttpPatternOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int KIND_FIELD_NUMBER = 1;
    private volatile Object kind_ = "";
    public static final int PATH_FIELD_NUMBER = 2;
    private volatile Object path_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final CustomHttpPattern DEFAULT_INSTANCE = new CustomHttpPattern();
    private static final Parser<CustomHttpPattern> PARSER = new AbstractParser<CustomHttpPattern>(){

        @Override
        public CustomHttpPattern parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = CustomHttpPattern.newBuilder();
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

    private CustomHttpPattern(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private CustomHttpPattern() {
        this.kind_ = "";
        this.path_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new CustomHttpPattern();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return HttpProto.internal_static_google_api_CustomHttpPattern_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return HttpProto.internal_static_google_api_CustomHttpPattern_fieldAccessorTable.ensureFieldAccessorsInitialized(CustomHttpPattern.class, Builder.class);
    }

    @Override
    public String getKind() {
        Object ref = this.kind_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.kind_ = s2;
        return s2;
    }

    @Override
    public ByteString getKindBytes() {
        Object ref = this.kind_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.kind_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getPath() {
        Object ref = this.path_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.path_ = s2;
        return s2;
    }

    @Override
    public ByteString getPathBytes() {
        Object ref = this.path_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.path_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.kind_)) {
            GeneratedMessageV3.writeString(output, 1, this.kind_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.path_)) {
            GeneratedMessageV3.writeString(output, 2, this.path_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.kind_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.kind_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.path_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.path_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CustomHttpPattern)) {
            return super.equals(obj);
        }
        CustomHttpPattern other = (CustomHttpPattern)obj;
        if (!this.getKind().equals(other.getKind())) {
            return false;
        }
        if (!this.getPath().equals(other.getPath())) {
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
        hash = 19 * hash + CustomHttpPattern.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getKind().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getPath().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static CustomHttpPattern parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static CustomHttpPattern parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static CustomHttpPattern parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static CustomHttpPattern parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static CustomHttpPattern parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static CustomHttpPattern parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static CustomHttpPattern parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static CustomHttpPattern parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static CustomHttpPattern parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static CustomHttpPattern parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static CustomHttpPattern parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static CustomHttpPattern parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return CustomHttpPattern.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(CustomHttpPattern prototype) {
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

    public static CustomHttpPattern getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<CustomHttpPattern> parser() {
        return PARSER;
    }

    public Parser<CustomHttpPattern> getParserForType() {
        return PARSER;
    }

    @Override
    public CustomHttpPattern getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements CustomHttpPatternOrBuilder {
        private int bitField0_;
        private Object kind_ = "";
        private Object path_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return HttpProto.internal_static_google_api_CustomHttpPattern_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return HttpProto.internal_static_google_api_CustomHttpPattern_fieldAccessorTable.ensureFieldAccessorsInitialized(CustomHttpPattern.class, Builder.class);
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
            this.kind_ = "";
            this.path_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return HttpProto.internal_static_google_api_CustomHttpPattern_descriptor;
        }

        @Override
        public CustomHttpPattern getDefaultInstanceForType() {
            return CustomHttpPattern.getDefaultInstance();
        }

        @Override
        public CustomHttpPattern build() {
            CustomHttpPattern result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public CustomHttpPattern buildPartial() {
            CustomHttpPattern result = new CustomHttpPattern(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(CustomHttpPattern result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.kind_ = this.kind_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.path_ = this.path_;
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
            if (other instanceof CustomHttpPattern) {
                return this.mergeFrom((CustomHttpPattern)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(CustomHttpPattern other) {
            if (other == CustomHttpPattern.getDefaultInstance()) {
                return this;
            }
            if (!other.getKind().isEmpty()) {
                this.kind_ = other.kind_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getPath().isEmpty()) {
                this.path_ = other.path_;
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
                            this.kind_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            this.path_ = input.readStringRequireUtf8();
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
        public String getKind() {
            Object ref = this.kind_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.kind_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getKindBytes() {
            Object ref = this.kind_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.kind_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setKind(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.kind_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearKind() {
            this.kind_ = CustomHttpPattern.getDefaultInstance().getKind();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setKindBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            CustomHttpPattern.checkByteStringIsUtf8(value);
            this.kind_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getPath() {
            Object ref = this.path_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.path_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getPathBytes() {
            Object ref = this.path_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.path_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setPath(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.path_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearPath() {
            this.path_ = CustomHttpPattern.getDefaultInstance().getPath();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setPathBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            CustomHttpPattern.checkByteStringIsUtf8(value);
            this.path_ = value;
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

