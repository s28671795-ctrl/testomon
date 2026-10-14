/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.AbstractMessageLite;
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
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.type.PhoneNumberOrBuilder;
import com.google.type.PhoneNumberProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class PhoneNumber
extends GeneratedMessageV3
implements PhoneNumberOrBuilder {
    private static final long serialVersionUID = 0L;
    private int kindCase_ = 0;
    private Object kind_;
    public static final int E164_NUMBER_FIELD_NUMBER = 1;
    public static final int SHORT_CODE_FIELD_NUMBER = 2;
    public static final int EXTENSION_FIELD_NUMBER = 3;
    private volatile Object extension_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final PhoneNumber DEFAULT_INSTANCE = new PhoneNumber();
    private static final Parser<PhoneNumber> PARSER = new AbstractParser<PhoneNumber>(){

        @Override
        public PhoneNumber parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = PhoneNumber.newBuilder();
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

    private PhoneNumber(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private PhoneNumber() {
        this.extension_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new PhoneNumber();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return PhoneNumberProto.internal_static_google_type_PhoneNumber_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return PhoneNumberProto.internal_static_google_type_PhoneNumber_fieldAccessorTable.ensureFieldAccessorsInitialized(PhoneNumber.class, Builder.class);
    }

    @Override
    public KindCase getKindCase() {
        return KindCase.forNumber(this.kindCase_);
    }

    @Override
    public boolean hasE164Number() {
        return this.kindCase_ == 1;
    }

    @Override
    public String getE164Number() {
        Object ref = "";
        if (this.kindCase_ == 1) {
            ref = this.kind_;
        }
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        if (this.kindCase_ == 1) {
            this.kind_ = s2;
        }
        return s2;
    }

    @Override
    public ByteString getE164NumberBytes() {
        Object ref = "";
        if (this.kindCase_ == 1) {
            ref = this.kind_;
        }
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            if (this.kindCase_ == 1) {
                this.kind_ = b;
            }
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasShortCode() {
        return this.kindCase_ == 2;
    }

    @Override
    public ShortCode getShortCode() {
        if (this.kindCase_ == 2) {
            return (ShortCode)this.kind_;
        }
        return ShortCode.getDefaultInstance();
    }

    @Override
    public ShortCodeOrBuilder getShortCodeOrBuilder() {
        if (this.kindCase_ == 2) {
            return (ShortCode)this.kind_;
        }
        return ShortCode.getDefaultInstance();
    }

    @Override
    public String getExtension() {
        Object ref = this.extension_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.extension_ = s2;
        return s2;
    }

    @Override
    public ByteString getExtensionBytes() {
        Object ref = this.extension_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.extension_ = b;
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
        if (this.kindCase_ == 1) {
            GeneratedMessageV3.writeString(output, 1, this.kind_);
        }
        if (this.kindCase_ == 2) {
            output.writeMessage(2, (ShortCode)this.kind_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.extension_)) {
            GeneratedMessageV3.writeString(output, 3, this.extension_);
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
        if (this.kindCase_ == 1) {
            size += GeneratedMessageV3.computeStringSize(1, this.kind_);
        }
        if (this.kindCase_ == 2) {
            size += CodedOutputStream.computeMessageSize(2, (ShortCode)this.kind_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.extension_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.extension_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PhoneNumber)) {
            return super.equals(obj);
        }
        PhoneNumber other = (PhoneNumber)obj;
        if (!this.getExtension().equals(other.getExtension())) {
            return false;
        }
        if (!this.getKindCase().equals(other.getKindCase())) {
            return false;
        }
        switch (this.kindCase_) {
            case 1: {
                if (this.getE164Number().equals(other.getE164Number())) break;
                return false;
            }
            case 2: {
                if (this.getShortCode().equals(other.getShortCode())) break;
                return false;
            }
        }
        return this.getUnknownFields().equals(other.getUnknownFields());
    }

    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + PhoneNumber.getDescriptor().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getExtension().hashCode();
        switch (this.kindCase_) {
            case 1: {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getE164Number().hashCode();
                break;
            }
            case 2: {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getShortCode().hashCode();
                break;
            }
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static PhoneNumber parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static PhoneNumber parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static PhoneNumber parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static PhoneNumber parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static PhoneNumber parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static PhoneNumber parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static PhoneNumber parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static PhoneNumber parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static PhoneNumber parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static PhoneNumber parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static PhoneNumber parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static PhoneNumber parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return PhoneNumber.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(PhoneNumber prototype) {
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

    public static PhoneNumber getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<PhoneNumber> parser() {
        return PARSER;
    }

    public Parser<PhoneNumber> getParserForType() {
        return PARSER;
    }

    @Override
    public PhoneNumber getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements PhoneNumberOrBuilder {
        private int kindCase_ = 0;
        private Object kind_;
        private int bitField0_;
        private SingleFieldBuilderV3<ShortCode, ShortCode.Builder, ShortCodeOrBuilder> shortCodeBuilder_;
        private Object extension_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return PhoneNumberProto.internal_static_google_type_PhoneNumber_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return PhoneNumberProto.internal_static_google_type_PhoneNumber_fieldAccessorTable.ensureFieldAccessorsInitialized(PhoneNumber.class, Builder.class);
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
            if (this.shortCodeBuilder_ != null) {
                this.shortCodeBuilder_.clear();
            }
            this.extension_ = "";
            this.kindCase_ = 0;
            this.kind_ = null;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return PhoneNumberProto.internal_static_google_type_PhoneNumber_descriptor;
        }

        @Override
        public PhoneNumber getDefaultInstanceForType() {
            return PhoneNumber.getDefaultInstance();
        }

        @Override
        public PhoneNumber build() {
            PhoneNumber result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public PhoneNumber buildPartial() {
            PhoneNumber result = new PhoneNumber(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.buildPartialOneofs(result);
            this.onBuilt();
            return result;
        }

        private void buildPartial0(PhoneNumber result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 4) != 0) {
                result.extension_ = this.extension_;
            }
        }

        private void buildPartialOneofs(PhoneNumber result) {
            result.kindCase_ = this.kindCase_;
            result.kind_ = this.kind_;
            if (this.kindCase_ == 2 && this.shortCodeBuilder_ != null) {
                result.kind_ = this.shortCodeBuilder_.build();
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
            if (other instanceof PhoneNumber) {
                return this.mergeFrom((PhoneNumber)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(PhoneNumber other) {
            if (other == PhoneNumber.getDefaultInstance()) {
                return this;
            }
            if (!other.getExtension().isEmpty()) {
                this.extension_ = other.extension_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            switch (other.getKindCase()) {
                case E164_NUMBER: {
                    this.kindCase_ = 1;
                    this.kind_ = other.kind_;
                    this.onChanged();
                    break;
                }
                case SHORT_CODE: {
                    this.mergeShortCode(other.getShortCode());
                    break;
                }
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
                            String s2 = input.readStringRequireUtf8();
                            this.kindCase_ = 1;
                            this.kind_ = s2;
                            continue block11;
                        }
                        case 18: {
                            input.readMessage(this.getShortCodeFieldBuilder().getBuilder(), extensionRegistry);
                            this.kindCase_ = 2;
                            continue block11;
                        }
                        case 26: {
                            this.extension_ = input.readStringRequireUtf8();
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
        public KindCase getKindCase() {
            return KindCase.forNumber(this.kindCase_);
        }

        public Builder clearKind() {
            this.kindCase_ = 0;
            this.kind_ = null;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasE164Number() {
            return this.kindCase_ == 1;
        }

        @Override
        public String getE164Number() {
            Object ref = "";
            if (this.kindCase_ == 1) {
                ref = this.kind_;
            }
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                if (this.kindCase_ == 1) {
                    this.kind_ = s2;
                }
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getE164NumberBytes() {
            Object ref = "";
            if (this.kindCase_ == 1) {
                ref = this.kind_;
            }
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                if (this.kindCase_ == 1) {
                    this.kind_ = b;
                }
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setE164Number(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.kindCase_ = 1;
            this.kind_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearE164Number() {
            if (this.kindCase_ == 1) {
                this.kindCase_ = 0;
                this.kind_ = null;
                this.onChanged();
            }
            return this;
        }

        public Builder setE164NumberBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            PhoneNumber.checkByteStringIsUtf8(value);
            this.kindCase_ = 1;
            this.kind_ = value;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasShortCode() {
            return this.kindCase_ == 2;
        }

        @Override
        public ShortCode getShortCode() {
            if (this.shortCodeBuilder_ == null) {
                if (this.kindCase_ == 2) {
                    return (ShortCode)this.kind_;
                }
                return ShortCode.getDefaultInstance();
            }
            if (this.kindCase_ == 2) {
                return this.shortCodeBuilder_.getMessage();
            }
            return ShortCode.getDefaultInstance();
        }

        public Builder setShortCode(ShortCode value) {
            if (this.shortCodeBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.kind_ = value;
                this.onChanged();
            } else {
                this.shortCodeBuilder_.setMessage(value);
            }
            this.kindCase_ = 2;
            return this;
        }

        public Builder setShortCode(ShortCode.Builder builderForValue) {
            if (this.shortCodeBuilder_ == null) {
                this.kind_ = builderForValue.build();
                this.onChanged();
            } else {
                this.shortCodeBuilder_.setMessage(builderForValue.build());
            }
            this.kindCase_ = 2;
            return this;
        }

        public Builder mergeShortCode(ShortCode value) {
            if (this.shortCodeBuilder_ == null) {
                this.kind_ = this.kindCase_ == 2 && this.kind_ != ShortCode.getDefaultInstance() ? ShortCode.newBuilder((ShortCode)this.kind_).mergeFrom(value).buildPartial() : value;
                this.onChanged();
            } else if (this.kindCase_ == 2) {
                this.shortCodeBuilder_.mergeFrom(value);
            } else {
                this.shortCodeBuilder_.setMessage(value);
            }
            this.kindCase_ = 2;
            return this;
        }

        public Builder clearShortCode() {
            if (this.shortCodeBuilder_ == null) {
                if (this.kindCase_ == 2) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                    this.onChanged();
                }
            } else {
                if (this.kindCase_ == 2) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                }
                this.shortCodeBuilder_.clear();
            }
            return this;
        }

        public ShortCode.Builder getShortCodeBuilder() {
            return this.getShortCodeFieldBuilder().getBuilder();
        }

        @Override
        public ShortCodeOrBuilder getShortCodeOrBuilder() {
            if (this.kindCase_ == 2 && this.shortCodeBuilder_ != null) {
                return this.shortCodeBuilder_.getMessageOrBuilder();
            }
            if (this.kindCase_ == 2) {
                return (ShortCode)this.kind_;
            }
            return ShortCode.getDefaultInstance();
        }

        private SingleFieldBuilderV3<ShortCode, ShortCode.Builder, ShortCodeOrBuilder> getShortCodeFieldBuilder() {
            if (this.shortCodeBuilder_ == null) {
                if (this.kindCase_ != 2) {
                    this.kind_ = ShortCode.getDefaultInstance();
                }
                this.shortCodeBuilder_ = new SingleFieldBuilderV3((ShortCode)this.kind_, this.getParentForChildren(), this.isClean());
                this.kind_ = null;
            }
            this.kindCase_ = 2;
            this.onChanged();
            return this.shortCodeBuilder_;
        }

        @Override
        public String getExtension() {
            Object ref = this.extension_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.extension_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getExtensionBytes() {
            Object ref = this.extension_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.extension_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setExtension(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.extension_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearExtension() {
            this.extension_ = PhoneNumber.getDefaultInstance().getExtension();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setExtensionBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            PhoneNumber.checkByteStringIsUtf8(value);
            this.extension_ = value;
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

    public static enum KindCase implements Internal.EnumLite,
    AbstractMessageLite.InternalOneOfEnum
    {
        E164_NUMBER(1),
        SHORT_CODE(2),
        KIND_NOT_SET(0);

        private final int value;

        private KindCase(int value) {
            this.value = value;
        }

        @Deprecated
        public static KindCase valueOf(int value) {
            return KindCase.forNumber(value);
        }

        public static KindCase forNumber(int value) {
            switch (value) {
                case 1: {
                    return E164_NUMBER;
                }
                case 2: {
                    return SHORT_CODE;
                }
                case 0: {
                    return KIND_NOT_SET;
                }
            }
            return null;
        }

        @Override
        public int getNumber() {
            return this.value;
        }
    }

    public static final class ShortCode
    extends GeneratedMessageV3
    implements ShortCodeOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int REGION_CODE_FIELD_NUMBER = 1;
        private volatile Object regionCode_ = "";
        public static final int NUMBER_FIELD_NUMBER = 2;
        private volatile Object number_ = "";
        private byte memoizedIsInitialized = (byte)-1;
        private static final ShortCode DEFAULT_INSTANCE = new ShortCode();
        private static final Parser<ShortCode> PARSER = new AbstractParser<ShortCode>(){

            @Override
            public ShortCode parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = ShortCode.newBuilder();
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

        private ShortCode(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ShortCode() {
            this.regionCode_ = "";
            this.number_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new ShortCode();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return PhoneNumberProto.internal_static_google_type_PhoneNumber_ShortCode_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return PhoneNumberProto.internal_static_google_type_PhoneNumber_ShortCode_fieldAccessorTable.ensureFieldAccessorsInitialized(ShortCode.class, Builder.class);
        }

        @Override
        public String getRegionCode() {
            Object ref = this.regionCode_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.regionCode_ = s2;
            return s2;
        }

        @Override
        public ByteString getRegionCodeBytes() {
            Object ref = this.regionCode_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.regionCode_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public String getNumber() {
            Object ref = this.number_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.number_ = s2;
            return s2;
        }

        @Override
        public ByteString getNumberBytes() {
            Object ref = this.number_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.number_ = b;
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
            if (!GeneratedMessageV3.isStringEmpty(this.regionCode_)) {
                GeneratedMessageV3.writeString(output, 1, this.regionCode_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.number_)) {
                GeneratedMessageV3.writeString(output, 2, this.number_);
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
            if (!GeneratedMessageV3.isStringEmpty(this.regionCode_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.regionCode_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.number_)) {
                size += GeneratedMessageV3.computeStringSize(2, this.number_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ShortCode)) {
                return super.equals(obj);
            }
            ShortCode other = (ShortCode)obj;
            if (!this.getRegionCode().equals(other.getRegionCode())) {
                return false;
            }
            if (!this.getNumber().equals(other.getNumber())) {
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
            hash = 19 * hash + ShortCode.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getRegionCode().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getNumber().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static ShortCode parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ShortCode parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ShortCode parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ShortCode parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ShortCode parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ShortCode parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ShortCode parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ShortCode parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ShortCode parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ShortCode parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ShortCode parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ShortCode parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ShortCode.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ShortCode prototype) {
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

        public static ShortCode getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ShortCode> parser() {
            return PARSER;
        }

        public Parser<ShortCode> getParserForType() {
            return PARSER;
        }

        @Override
        public ShortCode getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ShortCodeOrBuilder {
            private int bitField0_;
            private Object regionCode_ = "";
            private Object number_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return PhoneNumberProto.internal_static_google_type_PhoneNumber_ShortCode_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return PhoneNumberProto.internal_static_google_type_PhoneNumber_ShortCode_fieldAccessorTable.ensureFieldAccessorsInitialized(ShortCode.class, Builder.class);
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
                this.regionCode_ = "";
                this.number_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return PhoneNumberProto.internal_static_google_type_PhoneNumber_ShortCode_descriptor;
            }

            @Override
            public ShortCode getDefaultInstanceForType() {
                return ShortCode.getDefaultInstance();
            }

            @Override
            public ShortCode build() {
                ShortCode result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ShortCode buildPartial() {
                ShortCode result = new ShortCode(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(ShortCode result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.regionCode_ = this.regionCode_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.number_ = this.number_;
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
                if (other instanceof ShortCode) {
                    return this.mergeFrom((ShortCode)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ShortCode other) {
                if (other == ShortCode.getDefaultInstance()) {
                    return this;
                }
                if (!other.getRegionCode().isEmpty()) {
                    this.regionCode_ = other.regionCode_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (!other.getNumber().isEmpty()) {
                    this.number_ = other.number_;
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
                                this.regionCode_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block10;
                            }
                            case 18: {
                                this.number_ = input.readStringRequireUtf8();
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
            public String getRegionCode() {
                Object ref = this.regionCode_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.regionCode_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getRegionCodeBytes() {
                Object ref = this.regionCode_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.regionCode_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setRegionCode(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.regionCode_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearRegionCode() {
                this.regionCode_ = ShortCode.getDefaultInstance().getRegionCode();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setRegionCodeBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ShortCode.checkByteStringIsUtf8(value);
                this.regionCode_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public String getNumber() {
                Object ref = this.number_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.number_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getNumberBytes() {
                Object ref = this.number_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.number_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setNumber(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.number_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearNumber() {
                this.number_ = ShortCode.getDefaultInstance().getNumber();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            public Builder setNumberBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ShortCode.checkByteStringIsUtf8(value);
                this.number_ = value;
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

    public static interface ShortCodeOrBuilder
    extends MessageOrBuilder {
        public String getRegionCode();

        public ByteString getRegionCodeBytes();

        public String getNumber();

        public ByteString getNumberBytes();
    }
}

