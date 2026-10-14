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
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.type.MoneyOrBuilder;
import com.google.type.MoneyProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Money
extends GeneratedMessageV3
implements MoneyOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int CURRENCY_CODE_FIELD_NUMBER = 1;
    private volatile Object currencyCode_ = "";
    public static final int UNITS_FIELD_NUMBER = 2;
    private long units_ = 0L;
    public static final int NANOS_FIELD_NUMBER = 3;
    private int nanos_ = 0;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Money DEFAULT_INSTANCE = new Money();
    private static final Parser<Money> PARSER = new AbstractParser<Money>(){

        @Override
        public Money parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Money.newBuilder();
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

    private Money(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Money() {
        this.currencyCode_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Money();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return MoneyProto.internal_static_google_type_Money_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return MoneyProto.internal_static_google_type_Money_fieldAccessorTable.ensureFieldAccessorsInitialized(Money.class, Builder.class);
    }

    @Override
    public String getCurrencyCode() {
        Object ref = this.currencyCode_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.currencyCode_ = s2;
        return s2;
    }

    @Override
    public ByteString getCurrencyCodeBytes() {
        Object ref = this.currencyCode_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.currencyCode_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public long getUnits() {
        return this.units_;
    }

    @Override
    public int getNanos() {
        return this.nanos_;
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
        if (!GeneratedMessageV3.isStringEmpty(this.currencyCode_)) {
            GeneratedMessageV3.writeString(output, 1, this.currencyCode_);
        }
        if (this.units_ != 0L) {
            output.writeInt64(2, this.units_);
        }
        if (this.nanos_ != 0) {
            output.writeInt32(3, this.nanos_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.currencyCode_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.currencyCode_);
        }
        if (this.units_ != 0L) {
            size += CodedOutputStream.computeInt64Size(2, this.units_);
        }
        if (this.nanos_ != 0) {
            size += CodedOutputStream.computeInt32Size(3, this.nanos_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Money)) {
            return super.equals(obj);
        }
        Money other = (Money)obj;
        if (!this.getCurrencyCode().equals(other.getCurrencyCode())) {
            return false;
        }
        if (this.getUnits() != other.getUnits()) {
            return false;
        }
        if (this.getNanos() != other.getNanos()) {
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
        hash = 19 * hash + Money.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getCurrencyCode().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + Internal.hashLong(this.getUnits());
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getNanos();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Money parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Money parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Money parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Money parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Money parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Money parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Money parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Money parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Money parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Money parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Money parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Money parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Money.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Money prototype) {
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

    public static Money getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Money> parser() {
        return PARSER;
    }

    public Parser<Money> getParserForType() {
        return PARSER;
    }

    @Override
    public Money getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements MoneyOrBuilder {
        private int bitField0_;
        private Object currencyCode_ = "";
        private long units_;
        private int nanos_;

        public static final Descriptors.Descriptor getDescriptor() {
            return MoneyProto.internal_static_google_type_Money_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MoneyProto.internal_static_google_type_Money_fieldAccessorTable.ensureFieldAccessorsInitialized(Money.class, Builder.class);
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
            this.currencyCode_ = "";
            this.units_ = 0L;
            this.nanos_ = 0;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return MoneyProto.internal_static_google_type_Money_descriptor;
        }

        @Override
        public Money getDefaultInstanceForType() {
            return Money.getDefaultInstance();
        }

        @Override
        public Money build() {
            Money result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Money buildPartial() {
            Money result = new Money(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(Money result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.currencyCode_ = this.currencyCode_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.units_ = this.units_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.nanos_ = this.nanos_;
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
            if (other instanceof Money) {
                return this.mergeFrom((Money)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Money other) {
            if (other == Money.getDefaultInstance()) {
                return this;
            }
            if (!other.getCurrencyCode().isEmpty()) {
                this.currencyCode_ = other.currencyCode_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (other.getUnits() != 0L) {
                this.setUnits(other.getUnits());
            }
            if (other.getNanos() != 0) {
                this.setNanos(other.getNanos());
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
                            this.currencyCode_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block11;
                        }
                        case 16: {
                            this.units_ = input.readInt64();
                            this.bitField0_ |= 2;
                            continue block11;
                        }
                        case 24: {
                            this.nanos_ = input.readInt32();
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
        public String getCurrencyCode() {
            Object ref = this.currencyCode_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.currencyCode_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getCurrencyCodeBytes() {
            Object ref = this.currencyCode_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.currencyCode_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setCurrencyCode(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.currencyCode_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearCurrencyCode() {
            this.currencyCode_ = Money.getDefaultInstance().getCurrencyCode();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setCurrencyCodeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Money.checkByteStringIsUtf8(value);
            this.currencyCode_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public long getUnits() {
            return this.units_;
        }

        public Builder setUnits(long value) {
            this.units_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearUnits() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.units_ = 0L;
            this.onChanged();
            return this;
        }

        @Override
        public int getNanos() {
            return this.nanos_;
        }

        public Builder setNanos(int value) {
            this.nanos_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearNanos() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.nanos_ = 0;
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

