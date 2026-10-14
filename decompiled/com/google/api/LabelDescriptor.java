/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.LabelDescriptorOrBuilder;
import com.google.api.LabelProto;
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

public final class LabelDescriptor
extends GeneratedMessageV3
implements LabelDescriptorOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int KEY_FIELD_NUMBER = 1;
    private volatile Object key_ = "";
    public static final int VALUE_TYPE_FIELD_NUMBER = 2;
    private int valueType_ = 0;
    public static final int DESCRIPTION_FIELD_NUMBER = 3;
    private volatile Object description_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final LabelDescriptor DEFAULT_INSTANCE = new LabelDescriptor();
    private static final Parser<LabelDescriptor> PARSER = new AbstractParser<LabelDescriptor>(){

        @Override
        public LabelDescriptor parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = LabelDescriptor.newBuilder();
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

    private LabelDescriptor(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private LabelDescriptor() {
        this.key_ = "";
        this.valueType_ = 0;
        this.description_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new LabelDescriptor();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return LabelProto.internal_static_google_api_LabelDescriptor_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return LabelProto.internal_static_google_api_LabelDescriptor_fieldAccessorTable.ensureFieldAccessorsInitialized(LabelDescriptor.class, Builder.class);
    }

    @Override
    public String getKey() {
        Object ref = this.key_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.key_ = s2;
        return s2;
    }

    @Override
    public ByteString getKeyBytes() {
        Object ref = this.key_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.key_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public int getValueTypeValue() {
        return this.valueType_;
    }

    @Override
    public ValueType getValueType() {
        ValueType result = ValueType.forNumber(this.valueType_);
        return result == null ? ValueType.UNRECOGNIZED : result;
    }

    @Override
    public String getDescription() {
        Object ref = this.description_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.description_ = s2;
        return s2;
    }

    @Override
    public ByteString getDescriptionBytes() {
        Object ref = this.description_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.description_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.key_)) {
            GeneratedMessageV3.writeString(output, 1, this.key_);
        }
        if (this.valueType_ != ValueType.STRING.getNumber()) {
            output.writeEnum(2, this.valueType_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.description_)) {
            GeneratedMessageV3.writeString(output, 3, this.description_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.key_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.key_);
        }
        if (this.valueType_ != ValueType.STRING.getNumber()) {
            size += CodedOutputStream.computeEnumSize(2, this.valueType_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.description_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.description_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LabelDescriptor)) {
            return super.equals(obj);
        }
        LabelDescriptor other = (LabelDescriptor)obj;
        if (!this.getKey().equals(other.getKey())) {
            return false;
        }
        if (this.valueType_ != other.valueType_) {
            return false;
        }
        if (!this.getDescription().equals(other.getDescription())) {
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
        hash = 19 * hash + LabelDescriptor.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getKey().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.valueType_;
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getDescription().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static LabelDescriptor parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LabelDescriptor parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LabelDescriptor parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LabelDescriptor parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LabelDescriptor parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LabelDescriptor parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LabelDescriptor parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static LabelDescriptor parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static LabelDescriptor parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static LabelDescriptor parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static LabelDescriptor parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static LabelDescriptor parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return LabelDescriptor.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(LabelDescriptor prototype) {
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

    public static LabelDescriptor getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<LabelDescriptor> parser() {
        return PARSER;
    }

    public Parser<LabelDescriptor> getParserForType() {
        return PARSER;
    }

    @Override
    public LabelDescriptor getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements LabelDescriptorOrBuilder {
        private int bitField0_;
        private Object key_ = "";
        private int valueType_ = 0;
        private Object description_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return LabelProto.internal_static_google_api_LabelDescriptor_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return LabelProto.internal_static_google_api_LabelDescriptor_fieldAccessorTable.ensureFieldAccessorsInitialized(LabelDescriptor.class, Builder.class);
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
            this.key_ = "";
            this.valueType_ = 0;
            this.description_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return LabelProto.internal_static_google_api_LabelDescriptor_descriptor;
        }

        @Override
        public LabelDescriptor getDefaultInstanceForType() {
            return LabelDescriptor.getDefaultInstance();
        }

        @Override
        public LabelDescriptor build() {
            LabelDescriptor result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public LabelDescriptor buildPartial() {
            LabelDescriptor result = new LabelDescriptor(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(LabelDescriptor result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.key_ = this.key_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.valueType_ = this.valueType_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.description_ = this.description_;
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
            if (other instanceof LabelDescriptor) {
                return this.mergeFrom((LabelDescriptor)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(LabelDescriptor other) {
            if (other == LabelDescriptor.getDefaultInstance()) {
                return this;
            }
            if (!other.getKey().isEmpty()) {
                this.key_ = other.key_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (other.valueType_ != 0) {
                this.setValueTypeValue(other.getValueTypeValue());
            }
            if (!other.getDescription().isEmpty()) {
                this.description_ = other.description_;
                this.bitField0_ |= 4;
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
                            this.key_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block11;
                        }
                        case 16: {
                            this.valueType_ = input.readEnum();
                            this.bitField0_ |= 2;
                            continue block11;
                        }
                        case 26: {
                            this.description_ = input.readStringRequireUtf8();
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
        public String getKey() {
            Object ref = this.key_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.key_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getKeyBytes() {
            Object ref = this.key_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.key_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setKey(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.key_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearKey() {
            this.key_ = LabelDescriptor.getDefaultInstance().getKey();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setKeyBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            LabelDescriptor.checkByteStringIsUtf8(value);
            this.key_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public int getValueTypeValue() {
            return this.valueType_;
        }

        public Builder setValueTypeValue(int value) {
            this.valueType_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public ValueType getValueType() {
            ValueType result = ValueType.forNumber(this.valueType_);
            return result == null ? ValueType.UNRECOGNIZED : result;
        }

        public Builder setValueType(ValueType value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.bitField0_ |= 2;
            this.valueType_ = value.getNumber();
            this.onChanged();
            return this;
        }

        public Builder clearValueType() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.valueType_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public String getDescription() {
            Object ref = this.description_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.description_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getDescriptionBytes() {
            Object ref = this.description_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.description_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setDescription(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.description_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearDescription() {
            this.description_ = LabelDescriptor.getDefaultInstance().getDescription();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setDescriptionBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            LabelDescriptor.checkByteStringIsUtf8(value);
            this.description_ = value;
            this.bitField0_ |= 4;
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

    public static enum ValueType implements ProtocolMessageEnum
    {
        STRING(0),
        BOOL(1),
        INT64(2),
        UNRECOGNIZED(-1);

        public static final int STRING_VALUE = 0;
        public static final int BOOL_VALUE = 1;
        public static final int INT64_VALUE = 2;
        private static final Internal.EnumLiteMap<ValueType> internalValueMap;
        private static final ValueType[] VALUES;
        private final int value;

        @Override
        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }
            return this.value;
        }

        @Deprecated
        public static ValueType valueOf(int value) {
            return ValueType.forNumber(value);
        }

        public static ValueType forNumber(int value) {
            switch (value) {
                case 0: {
                    return STRING;
                }
                case 1: {
                    return BOOL;
                }
                case 2: {
                    return INT64;
                }
            }
            return null;
        }

        public static Internal.EnumLiteMap<ValueType> internalGetValueMap() {
            return internalValueMap;
        }

        @Override
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            if (this == UNRECOGNIZED) {
                throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
            }
            return ValueType.getDescriptor().getValues().get(this.ordinal());
        }

        @Override
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return ValueType.getDescriptor();
        }

        public static final Descriptors.EnumDescriptor getDescriptor() {
            return LabelDescriptor.getDescriptor().getEnumTypes().get(0);
        }

        public static ValueType valueOf(Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != ValueType.getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            if (desc.getIndex() == -1) {
                return UNRECOGNIZED;
            }
            return VALUES[desc.getIndex()];
        }

        private ValueType(int value) {
            this.value = value;
        }

        static {
            internalValueMap = new Internal.EnumLiteMap<ValueType>(){

                @Override
                public ValueType findValueByNumber(int number) {
                    return ValueType.forNumber(number);
                }
            };
            VALUES = ValueType.values();
        }
    }
}

