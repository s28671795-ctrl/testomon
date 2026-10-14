/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.Advice;
import com.google.api.AdviceOrBuilder;
import com.google.api.ChangeType;
import com.google.api.ConfigChangeOrBuilder;
import com.google.api.ConfigChangeProto;
import com.google.protobuf.AbstractMessageLite;
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
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ConfigChange
extends GeneratedMessageV3
implements ConfigChangeOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int ELEMENT_FIELD_NUMBER = 1;
    private volatile Object element_ = "";
    public static final int OLD_VALUE_FIELD_NUMBER = 2;
    private volatile Object oldValue_ = "";
    public static final int NEW_VALUE_FIELD_NUMBER = 3;
    private volatile Object newValue_ = "";
    public static final int CHANGE_TYPE_FIELD_NUMBER = 4;
    private int changeType_ = 0;
    public static final int ADVICES_FIELD_NUMBER = 5;
    private List<Advice> advices_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final ConfigChange DEFAULT_INSTANCE = new ConfigChange();
    private static final Parser<ConfigChange> PARSER = new AbstractParser<ConfigChange>(){

        @Override
        public ConfigChange parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = ConfigChange.newBuilder();
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

    private ConfigChange(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private ConfigChange() {
        this.element_ = "";
        this.oldValue_ = "";
        this.newValue_ = "";
        this.changeType_ = 0;
        this.advices_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new ConfigChange();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ConfigChangeProto.internal_static_google_api_ConfigChange_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ConfigChangeProto.internal_static_google_api_ConfigChange_fieldAccessorTable.ensureFieldAccessorsInitialized(ConfigChange.class, Builder.class);
    }

    @Override
    public String getElement() {
        Object ref = this.element_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.element_ = s2;
        return s2;
    }

    @Override
    public ByteString getElementBytes() {
        Object ref = this.element_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.element_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getOldValue() {
        Object ref = this.oldValue_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.oldValue_ = s2;
        return s2;
    }

    @Override
    public ByteString getOldValueBytes() {
        Object ref = this.oldValue_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.oldValue_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getNewValue() {
        Object ref = this.newValue_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.newValue_ = s2;
        return s2;
    }

    @Override
    public ByteString getNewValueBytes() {
        Object ref = this.newValue_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.newValue_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public int getChangeTypeValue() {
        return this.changeType_;
    }

    @Override
    public ChangeType getChangeType() {
        ChangeType result = ChangeType.forNumber(this.changeType_);
        return result == null ? ChangeType.UNRECOGNIZED : result;
    }

    @Override
    public List<Advice> getAdvicesList() {
        return this.advices_;
    }

    @Override
    public List<? extends AdviceOrBuilder> getAdvicesOrBuilderList() {
        return this.advices_;
    }

    @Override
    public int getAdvicesCount() {
        return this.advices_.size();
    }

    @Override
    public Advice getAdvices(int index) {
        return this.advices_.get(index);
    }

    @Override
    public AdviceOrBuilder getAdvicesOrBuilder(int index) {
        return this.advices_.get(index);
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
        if (!GeneratedMessageV3.isStringEmpty(this.element_)) {
            GeneratedMessageV3.writeString(output, 1, this.element_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.oldValue_)) {
            GeneratedMessageV3.writeString(output, 2, this.oldValue_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.newValue_)) {
            GeneratedMessageV3.writeString(output, 3, this.newValue_);
        }
        if (this.changeType_ != ChangeType.CHANGE_TYPE_UNSPECIFIED.getNumber()) {
            output.writeEnum(4, this.changeType_);
        }
        for (int i = 0; i < this.advices_.size(); ++i) {
            output.writeMessage(5, this.advices_.get(i));
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
        if (!GeneratedMessageV3.isStringEmpty(this.element_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.element_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.oldValue_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.oldValue_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.newValue_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.newValue_);
        }
        if (this.changeType_ != ChangeType.CHANGE_TYPE_UNSPECIFIED.getNumber()) {
            size += CodedOutputStream.computeEnumSize(4, this.changeType_);
        }
        for (int i = 0; i < this.advices_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(5, this.advices_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ConfigChange)) {
            return super.equals(obj);
        }
        ConfigChange other = (ConfigChange)obj;
        if (!this.getElement().equals(other.getElement())) {
            return false;
        }
        if (!this.getOldValue().equals(other.getOldValue())) {
            return false;
        }
        if (!this.getNewValue().equals(other.getNewValue())) {
            return false;
        }
        if (this.changeType_ != other.changeType_) {
            return false;
        }
        if (!this.getAdvicesList().equals(other.getAdvicesList())) {
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
        hash = 19 * hash + ConfigChange.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getElement().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getOldValue().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getNewValue().hashCode();
        hash = 37 * hash + 4;
        hash = 53 * hash + this.changeType_;
        if (this.getAdvicesCount() > 0) {
            hash = 37 * hash + 5;
            hash = 53 * hash + this.getAdvicesList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static ConfigChange parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ConfigChange parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ConfigChange parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ConfigChange parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ConfigChange parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ConfigChange parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ConfigChange parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ConfigChange parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ConfigChange parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ConfigChange parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ConfigChange parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ConfigChange parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return ConfigChange.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ConfigChange prototype) {
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

    public static ConfigChange getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ConfigChange> parser() {
        return PARSER;
    }

    public Parser<ConfigChange> getParserForType() {
        return PARSER;
    }

    @Override
    public ConfigChange getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ConfigChangeOrBuilder {
        private int bitField0_;
        private Object element_ = "";
        private Object oldValue_ = "";
        private Object newValue_ = "";
        private int changeType_ = 0;
        private List<Advice> advices_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<Advice, Advice.Builder, AdviceOrBuilder> advicesBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return ConfigChangeProto.internal_static_google_api_ConfigChange_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ConfigChangeProto.internal_static_google_api_ConfigChange_fieldAccessorTable.ensureFieldAccessorsInitialized(ConfigChange.class, Builder.class);
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
            this.element_ = "";
            this.oldValue_ = "";
            this.newValue_ = "";
            this.changeType_ = 0;
            if (this.advicesBuilder_ == null) {
                this.advices_ = Collections.emptyList();
            } else {
                this.advices_ = null;
                this.advicesBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFEF;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ConfigChangeProto.internal_static_google_api_ConfigChange_descriptor;
        }

        @Override
        public ConfigChange getDefaultInstanceForType() {
            return ConfigChange.getDefaultInstance();
        }

        @Override
        public ConfigChange build() {
            ConfigChange result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ConfigChange buildPartial() {
            ConfigChange result = new ConfigChange(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(ConfigChange result) {
            if (this.advicesBuilder_ == null) {
                if ((this.bitField0_ & 0x10) != 0) {
                    this.advices_ = Collections.unmodifiableList(this.advices_);
                    this.bitField0_ &= 0xFFFFFFEF;
                }
                result.advices_ = this.advices_;
            } else {
                result.advices_ = this.advicesBuilder_.build();
            }
        }

        private void buildPartial0(ConfigChange result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.element_ = this.element_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.oldValue_ = this.oldValue_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.newValue_ = this.newValue_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.changeType_ = this.changeType_;
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
            if (other instanceof ConfigChange) {
                return this.mergeFrom((ConfigChange)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ConfigChange other) {
            if (other == ConfigChange.getDefaultInstance()) {
                return this;
            }
            if (!other.getElement().isEmpty()) {
                this.element_ = other.element_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getOldValue().isEmpty()) {
                this.oldValue_ = other.oldValue_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (!other.getNewValue().isEmpty()) {
                this.newValue_ = other.newValue_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            if (other.changeType_ != 0) {
                this.setChangeTypeValue(other.getChangeTypeValue());
            }
            if (this.advicesBuilder_ == null) {
                if (!other.advices_.isEmpty()) {
                    if (this.advices_.isEmpty()) {
                        this.advices_ = other.advices_;
                        this.bitField0_ &= 0xFFFFFFEF;
                    } else {
                        this.ensureAdvicesIsMutable();
                        this.advices_.addAll(other.advices_);
                    }
                    this.onChanged();
                }
            } else if (!other.advices_.isEmpty()) {
                if (this.advicesBuilder_.isEmpty()) {
                    this.advicesBuilder_.dispose();
                    this.advicesBuilder_ = null;
                    this.advices_ = other.advices_;
                    this.bitField0_ &= 0xFFFFFFEF;
                    this.advicesBuilder_ = alwaysUseFieldBuilders ? this.getAdvicesFieldBuilder() : null;
                } else {
                    this.advicesBuilder_.addAllMessages(other.advices_);
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
                block13: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block13;
                        }
                        case 10: {
                            this.element_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block13;
                        }
                        case 18: {
                            this.oldValue_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block13;
                        }
                        case 26: {
                            this.newValue_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block13;
                        }
                        case 32: {
                            this.changeType_ = input.readEnum();
                            this.bitField0_ |= 8;
                            continue block13;
                        }
                        case 42: {
                            Advice m4 = input.readMessage(Advice.parser(), extensionRegistry);
                            if (this.advicesBuilder_ == null) {
                                this.ensureAdvicesIsMutable();
                                this.advices_.add(m4);
                                continue block13;
                            }
                            this.advicesBuilder_.addMessage(m4);
                            continue block13;
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
        public String getElement() {
            Object ref = this.element_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.element_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getElementBytes() {
            Object ref = this.element_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.element_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setElement(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.element_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearElement() {
            this.element_ = ConfigChange.getDefaultInstance().getElement();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setElementBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ConfigChange.checkByteStringIsUtf8(value);
            this.element_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getOldValue() {
            Object ref = this.oldValue_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.oldValue_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getOldValueBytes() {
            Object ref = this.oldValue_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.oldValue_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setOldValue(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.oldValue_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearOldValue() {
            this.oldValue_ = ConfigChange.getDefaultInstance().getOldValue();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setOldValueBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ConfigChange.checkByteStringIsUtf8(value);
            this.oldValue_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public String getNewValue() {
            Object ref = this.newValue_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.newValue_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getNewValueBytes() {
            Object ref = this.newValue_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.newValue_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setNewValue(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.newValue_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearNewValue() {
            this.newValue_ = ConfigChange.getDefaultInstance().getNewValue();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setNewValueBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ConfigChange.checkByteStringIsUtf8(value);
            this.newValue_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        @Override
        public int getChangeTypeValue() {
            return this.changeType_;
        }

        public Builder setChangeTypeValue(int value) {
            this.changeType_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        @Override
        public ChangeType getChangeType() {
            ChangeType result = ChangeType.forNumber(this.changeType_);
            return result == null ? ChangeType.UNRECOGNIZED : result;
        }

        public Builder setChangeType(ChangeType value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.bitField0_ |= 8;
            this.changeType_ = value.getNumber();
            this.onChanged();
            return this;
        }

        public Builder clearChangeType() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.changeType_ = 0;
            this.onChanged();
            return this;
        }

        private void ensureAdvicesIsMutable() {
            if ((this.bitField0_ & 0x10) == 0) {
                this.advices_ = new ArrayList<Advice>(this.advices_);
                this.bitField0_ |= 0x10;
            }
        }

        @Override
        public List<Advice> getAdvicesList() {
            if (this.advicesBuilder_ == null) {
                return Collections.unmodifiableList(this.advices_);
            }
            return this.advicesBuilder_.getMessageList();
        }

        @Override
        public int getAdvicesCount() {
            if (this.advicesBuilder_ == null) {
                return this.advices_.size();
            }
            return this.advicesBuilder_.getCount();
        }

        @Override
        public Advice getAdvices(int index) {
            if (this.advicesBuilder_ == null) {
                return this.advices_.get(index);
            }
            return this.advicesBuilder_.getMessage(index);
        }

        public Builder setAdvices(int index, Advice value) {
            if (this.advicesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAdvicesIsMutable();
                this.advices_.set(index, value);
                this.onChanged();
            } else {
                this.advicesBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setAdvices(int index, Advice.Builder builderForValue) {
            if (this.advicesBuilder_ == null) {
                this.ensureAdvicesIsMutable();
                this.advices_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.advicesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAdvices(Advice value) {
            if (this.advicesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAdvicesIsMutable();
                this.advices_.add(value);
                this.onChanged();
            } else {
                this.advicesBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addAdvices(int index, Advice value) {
            if (this.advicesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAdvicesIsMutable();
                this.advices_.add(index, value);
                this.onChanged();
            } else {
                this.advicesBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addAdvices(Advice.Builder builderForValue) {
            if (this.advicesBuilder_ == null) {
                this.ensureAdvicesIsMutable();
                this.advices_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.advicesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addAdvices(int index, Advice.Builder builderForValue) {
            if (this.advicesBuilder_ == null) {
                this.ensureAdvicesIsMutable();
                this.advices_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.advicesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllAdvices(Iterable<? extends Advice> values2) {
            if (this.advicesBuilder_ == null) {
                this.ensureAdvicesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.advices_);
                this.onChanged();
            } else {
                this.advicesBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearAdvices() {
            if (this.advicesBuilder_ == null) {
                this.advices_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFEF;
                this.onChanged();
            } else {
                this.advicesBuilder_.clear();
            }
            return this;
        }

        public Builder removeAdvices(int index) {
            if (this.advicesBuilder_ == null) {
                this.ensureAdvicesIsMutable();
                this.advices_.remove(index);
                this.onChanged();
            } else {
                this.advicesBuilder_.remove(index);
            }
            return this;
        }

        public Advice.Builder getAdvicesBuilder(int index) {
            return this.getAdvicesFieldBuilder().getBuilder(index);
        }

        @Override
        public AdviceOrBuilder getAdvicesOrBuilder(int index) {
            if (this.advicesBuilder_ == null) {
                return this.advices_.get(index);
            }
            return this.advicesBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends AdviceOrBuilder> getAdvicesOrBuilderList() {
            if (this.advicesBuilder_ != null) {
                return this.advicesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.advices_);
        }

        public Advice.Builder addAdvicesBuilder() {
            return this.getAdvicesFieldBuilder().addBuilder(Advice.getDefaultInstance());
        }

        public Advice.Builder addAdvicesBuilder(int index) {
            return this.getAdvicesFieldBuilder().addBuilder(index, Advice.getDefaultInstance());
        }

        public List<Advice.Builder> getAdvicesBuilderList() {
            return this.getAdvicesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Advice, Advice.Builder, AdviceOrBuilder> getAdvicesFieldBuilder() {
            if (this.advicesBuilder_ == null) {
                this.advicesBuilder_ = new RepeatedFieldBuilderV3(this.advices_, (this.bitField0_ & 0x10) != 0, this.getParentForChildren(), this.isClean());
                this.advices_ = null;
            }
            return this.advicesBuilder_;
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

