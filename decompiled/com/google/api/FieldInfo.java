/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.FieldInfoOrBuilder;
import com.google.api.FieldInfoProto;
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
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class FieldInfo
extends GeneratedMessageV3
implements FieldInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int FORMAT_FIELD_NUMBER = 1;
    private int format_ = 0;
    private byte memoizedIsInitialized = (byte)-1;
    private static final FieldInfo DEFAULT_INSTANCE = new FieldInfo();
    private static final Parser<FieldInfo> PARSER = new AbstractParser<FieldInfo>(){

        @Override
        public FieldInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = FieldInfo.newBuilder();
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

    private FieldInfo(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private FieldInfo() {
        this.format_ = 0;
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new FieldInfo();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return FieldInfoProto.internal_static_google_api_FieldInfo_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return FieldInfoProto.internal_static_google_api_FieldInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldInfo.class, Builder.class);
    }

    @Override
    public int getFormatValue() {
        return this.format_;
    }

    @Override
    public Format getFormat() {
        Format result = Format.forNumber(this.format_);
        return result == null ? Format.UNRECOGNIZED : result;
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
        if (this.format_ != Format.FORMAT_UNSPECIFIED.getNumber()) {
            output.writeEnum(1, this.format_);
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
        if (this.format_ != Format.FORMAT_UNSPECIFIED.getNumber()) {
            size += CodedOutputStream.computeEnumSize(1, this.format_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FieldInfo)) {
            return super.equals(obj);
        }
        FieldInfo other = (FieldInfo)obj;
        if (this.format_ != other.format_) {
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
        hash = 19 * hash + FieldInfo.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.format_;
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static FieldInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static FieldInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static FieldInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static FieldInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static FieldInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static FieldInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static FieldInfo parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static FieldInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static FieldInfo parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static FieldInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static FieldInfo parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static FieldInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return FieldInfo.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(FieldInfo prototype) {
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

    public static FieldInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<FieldInfo> parser() {
        return PARSER;
    }

    public Parser<FieldInfo> getParserForType() {
        return PARSER;
    }

    @Override
    public FieldInfo getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements FieldInfoOrBuilder {
        private int bitField0_;
        private int format_ = 0;

        public static final Descriptors.Descriptor getDescriptor() {
            return FieldInfoProto.internal_static_google_api_FieldInfo_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return FieldInfoProto.internal_static_google_api_FieldInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldInfo.class, Builder.class);
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
            this.format_ = 0;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return FieldInfoProto.internal_static_google_api_FieldInfo_descriptor;
        }

        @Override
        public FieldInfo getDefaultInstanceForType() {
            return FieldInfo.getDefaultInstance();
        }

        @Override
        public FieldInfo build() {
            FieldInfo result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public FieldInfo buildPartial() {
            FieldInfo result = new FieldInfo(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(FieldInfo result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.format_ = this.format_;
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
            if (other instanceof FieldInfo) {
                return this.mergeFrom((FieldInfo)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(FieldInfo other) {
            if (other == FieldInfo.getDefaultInstance()) {
                return this;
            }
            if (other.format_ != 0) {
                this.setFormatValue(other.getFormatValue());
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
                block9: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block9;
                        }
                        case 8: {
                            this.format_ = input.readEnum();
                            this.bitField0_ |= 1;
                            continue block9;
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
        public int getFormatValue() {
            return this.format_;
        }

        public Builder setFormatValue(int value) {
            this.format_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public Format getFormat() {
            Format result = Format.forNumber(this.format_);
            return result == null ? Format.UNRECOGNIZED : result;
        }

        public Builder setFormat(Format value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.bitField0_ |= 1;
            this.format_ = value.getNumber();
            this.onChanged();
            return this;
        }

        public Builder clearFormat() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.format_ = 0;
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

    public static enum Format implements ProtocolMessageEnum
    {
        FORMAT_UNSPECIFIED(0),
        UUID4(1),
        IPV4(2),
        IPV6(3),
        IPV4_OR_IPV6(4),
        UNRECOGNIZED(-1);

        public static final int FORMAT_UNSPECIFIED_VALUE = 0;
        public static final int UUID4_VALUE = 1;
        public static final int IPV4_VALUE = 2;
        public static final int IPV6_VALUE = 3;
        public static final int IPV4_OR_IPV6_VALUE = 4;
        private static final Internal.EnumLiteMap<Format> internalValueMap;
        private static final Format[] VALUES;
        private final int value;

        @Override
        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }
            return this.value;
        }

        @Deprecated
        public static Format valueOf(int value) {
            return Format.forNumber(value);
        }

        public static Format forNumber(int value) {
            switch (value) {
                case 0: {
                    return FORMAT_UNSPECIFIED;
                }
                case 1: {
                    return UUID4;
                }
                case 2: {
                    return IPV4;
                }
                case 3: {
                    return IPV6;
                }
                case 4: {
                    return IPV4_OR_IPV6;
                }
            }
            return null;
        }

        public static Internal.EnumLiteMap<Format> internalGetValueMap() {
            return internalValueMap;
        }

        @Override
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            if (this == UNRECOGNIZED) {
                throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
            }
            return Format.getDescriptor().getValues().get(this.ordinal());
        }

        @Override
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return Format.getDescriptor();
        }

        public static final Descriptors.EnumDescriptor getDescriptor() {
            return FieldInfo.getDescriptor().getEnumTypes().get(0);
        }

        public static Format valueOf(Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != Format.getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            if (desc.getIndex() == -1) {
                return UNRECOGNIZED;
            }
            return VALUES[desc.getIndex()];
        }

        private Format(int value) {
            this.value = value;
        }

        static {
            internalValueMap = new Internal.EnumLiteMap<Format>(){

                @Override
                public Format findValueByNumber(int number) {
                    return Format.forNumber(number);
                }
            };
            VALUES = Format.values();
        }
    }
}

