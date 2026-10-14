/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.SystemParameter;
import com.google.api.SystemParameterOrBuilder;
import com.google.api.SystemParameterProto;
import com.google.api.SystemParameterRuleOrBuilder;
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

public final class SystemParameterRule
extends GeneratedMessageV3
implements SystemParameterRuleOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int SELECTOR_FIELD_NUMBER = 1;
    private volatile Object selector_ = "";
    public static final int PARAMETERS_FIELD_NUMBER = 2;
    private List<SystemParameter> parameters_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final SystemParameterRule DEFAULT_INSTANCE = new SystemParameterRule();
    private static final Parser<SystemParameterRule> PARSER = new AbstractParser<SystemParameterRule>(){

        @Override
        public SystemParameterRule parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = SystemParameterRule.newBuilder();
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

    private SystemParameterRule(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private SystemParameterRule() {
        this.selector_ = "";
        this.parameters_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new SystemParameterRule();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return SystemParameterProto.internal_static_google_api_SystemParameterRule_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return SystemParameterProto.internal_static_google_api_SystemParameterRule_fieldAccessorTable.ensureFieldAccessorsInitialized(SystemParameterRule.class, Builder.class);
    }

    @Override
    public String getSelector() {
        Object ref = this.selector_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.selector_ = s2;
        return s2;
    }

    @Override
    public ByteString getSelectorBytes() {
        Object ref = this.selector_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.selector_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public List<SystemParameter> getParametersList() {
        return this.parameters_;
    }

    @Override
    public List<? extends SystemParameterOrBuilder> getParametersOrBuilderList() {
        return this.parameters_;
    }

    @Override
    public int getParametersCount() {
        return this.parameters_.size();
    }

    @Override
    public SystemParameter getParameters(int index) {
        return this.parameters_.get(index);
    }

    @Override
    public SystemParameterOrBuilder getParametersOrBuilder(int index) {
        return this.parameters_.get(index);
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
        if (!GeneratedMessageV3.isStringEmpty(this.selector_)) {
            GeneratedMessageV3.writeString(output, 1, this.selector_);
        }
        for (int i = 0; i < this.parameters_.size(); ++i) {
            output.writeMessage(2, this.parameters_.get(i));
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
        if (!GeneratedMessageV3.isStringEmpty(this.selector_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.selector_);
        }
        for (int i = 0; i < this.parameters_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(2, this.parameters_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SystemParameterRule)) {
            return super.equals(obj);
        }
        SystemParameterRule other = (SystemParameterRule)obj;
        if (!this.getSelector().equals(other.getSelector())) {
            return false;
        }
        if (!this.getParametersList().equals(other.getParametersList())) {
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
        hash = 19 * hash + SystemParameterRule.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getSelector().hashCode();
        if (this.getParametersCount() > 0) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getParametersList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static SystemParameterRule parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static SystemParameterRule parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static SystemParameterRule parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static SystemParameterRule parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static SystemParameterRule parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static SystemParameterRule parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static SystemParameterRule parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static SystemParameterRule parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static SystemParameterRule parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static SystemParameterRule parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static SystemParameterRule parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static SystemParameterRule parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return SystemParameterRule.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(SystemParameterRule prototype) {
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

    public static SystemParameterRule getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<SystemParameterRule> parser() {
        return PARSER;
    }

    public Parser<SystemParameterRule> getParserForType() {
        return PARSER;
    }

    @Override
    public SystemParameterRule getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements SystemParameterRuleOrBuilder {
        private int bitField0_;
        private Object selector_ = "";
        private List<SystemParameter> parameters_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<SystemParameter, SystemParameter.Builder, SystemParameterOrBuilder> parametersBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return SystemParameterProto.internal_static_google_api_SystemParameterRule_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return SystemParameterProto.internal_static_google_api_SystemParameterRule_fieldAccessorTable.ensureFieldAccessorsInitialized(SystemParameterRule.class, Builder.class);
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
            this.selector_ = "";
            if (this.parametersBuilder_ == null) {
                this.parameters_ = Collections.emptyList();
            } else {
                this.parameters_ = null;
                this.parametersBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFD;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return SystemParameterProto.internal_static_google_api_SystemParameterRule_descriptor;
        }

        @Override
        public SystemParameterRule getDefaultInstanceForType() {
            return SystemParameterRule.getDefaultInstance();
        }

        @Override
        public SystemParameterRule build() {
            SystemParameterRule result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public SystemParameterRule buildPartial() {
            SystemParameterRule result = new SystemParameterRule(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(SystemParameterRule result) {
            if (this.parametersBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0) {
                    this.parameters_ = Collections.unmodifiableList(this.parameters_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.parameters_ = this.parameters_;
            } else {
                result.parameters_ = this.parametersBuilder_.build();
            }
        }

        private void buildPartial0(SystemParameterRule result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.selector_ = this.selector_;
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
            if (other instanceof SystemParameterRule) {
                return this.mergeFrom((SystemParameterRule)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(SystemParameterRule other) {
            if (other == SystemParameterRule.getDefaultInstance()) {
                return this;
            }
            if (!other.getSelector().isEmpty()) {
                this.selector_ = other.selector_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (this.parametersBuilder_ == null) {
                if (!other.parameters_.isEmpty()) {
                    if (this.parameters_.isEmpty()) {
                        this.parameters_ = other.parameters_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureParametersIsMutable();
                        this.parameters_.addAll(other.parameters_);
                    }
                    this.onChanged();
                }
            } else if (!other.parameters_.isEmpty()) {
                if (this.parametersBuilder_.isEmpty()) {
                    this.parametersBuilder_.dispose();
                    this.parametersBuilder_ = null;
                    this.parameters_ = other.parameters_;
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.parametersBuilder_ = alwaysUseFieldBuilders ? this.getParametersFieldBuilder() : null;
                } else {
                    this.parametersBuilder_.addAllMessages(other.parameters_);
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
                block10: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block10;
                        }
                        case 10: {
                            this.selector_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            SystemParameter m4 = input.readMessage(SystemParameter.parser(), extensionRegistry);
                            if (this.parametersBuilder_ == null) {
                                this.ensureParametersIsMutable();
                                this.parameters_.add(m4);
                                continue block10;
                            }
                            this.parametersBuilder_.addMessage(m4);
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
        public String getSelector() {
            Object ref = this.selector_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.selector_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getSelectorBytes() {
            Object ref = this.selector_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.selector_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setSelector(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.selector_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearSelector() {
            this.selector_ = SystemParameterRule.getDefaultInstance().getSelector();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setSelectorBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            SystemParameterRule.checkByteStringIsUtf8(value);
            this.selector_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        private void ensureParametersIsMutable() {
            if ((this.bitField0_ & 2) == 0) {
                this.parameters_ = new ArrayList<SystemParameter>(this.parameters_);
                this.bitField0_ |= 2;
            }
        }

        @Override
        public List<SystemParameter> getParametersList() {
            if (this.parametersBuilder_ == null) {
                return Collections.unmodifiableList(this.parameters_);
            }
            return this.parametersBuilder_.getMessageList();
        }

        @Override
        public int getParametersCount() {
            if (this.parametersBuilder_ == null) {
                return this.parameters_.size();
            }
            return this.parametersBuilder_.getCount();
        }

        @Override
        public SystemParameter getParameters(int index) {
            if (this.parametersBuilder_ == null) {
                return this.parameters_.get(index);
            }
            return this.parametersBuilder_.getMessage(index);
        }

        public Builder setParameters(int index, SystemParameter value) {
            if (this.parametersBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureParametersIsMutable();
                this.parameters_.set(index, value);
                this.onChanged();
            } else {
                this.parametersBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setParameters(int index, SystemParameter.Builder builderForValue) {
            if (this.parametersBuilder_ == null) {
                this.ensureParametersIsMutable();
                this.parameters_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.parametersBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addParameters(SystemParameter value) {
            if (this.parametersBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureParametersIsMutable();
                this.parameters_.add(value);
                this.onChanged();
            } else {
                this.parametersBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addParameters(int index, SystemParameter value) {
            if (this.parametersBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureParametersIsMutable();
                this.parameters_.add(index, value);
                this.onChanged();
            } else {
                this.parametersBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addParameters(SystemParameter.Builder builderForValue) {
            if (this.parametersBuilder_ == null) {
                this.ensureParametersIsMutable();
                this.parameters_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.parametersBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addParameters(int index, SystemParameter.Builder builderForValue) {
            if (this.parametersBuilder_ == null) {
                this.ensureParametersIsMutable();
                this.parameters_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.parametersBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllParameters(Iterable<? extends SystemParameter> values2) {
            if (this.parametersBuilder_ == null) {
                this.ensureParametersIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.parameters_);
                this.onChanged();
            } else {
                this.parametersBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearParameters() {
            if (this.parametersBuilder_ == null) {
                this.parameters_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
            } else {
                this.parametersBuilder_.clear();
            }
            return this;
        }

        public Builder removeParameters(int index) {
            if (this.parametersBuilder_ == null) {
                this.ensureParametersIsMutable();
                this.parameters_.remove(index);
                this.onChanged();
            } else {
                this.parametersBuilder_.remove(index);
            }
            return this;
        }

        public SystemParameter.Builder getParametersBuilder(int index) {
            return this.getParametersFieldBuilder().getBuilder(index);
        }

        @Override
        public SystemParameterOrBuilder getParametersOrBuilder(int index) {
            if (this.parametersBuilder_ == null) {
                return this.parameters_.get(index);
            }
            return this.parametersBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends SystemParameterOrBuilder> getParametersOrBuilderList() {
            if (this.parametersBuilder_ != null) {
                return this.parametersBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.parameters_);
        }

        public SystemParameter.Builder addParametersBuilder() {
            return this.getParametersFieldBuilder().addBuilder(SystemParameter.getDefaultInstance());
        }

        public SystemParameter.Builder addParametersBuilder(int index) {
            return this.getParametersFieldBuilder().addBuilder(index, SystemParameter.getDefaultInstance());
        }

        public List<SystemParameter.Builder> getParametersBuilderList() {
            return this.getParametersFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<SystemParameter, SystemParameter.Builder, SystemParameterOrBuilder> getParametersFieldBuilder() {
            if (this.parametersBuilder_ == null) {
                this.parametersBuilder_ = new RepeatedFieldBuilderV3(this.parameters_, (this.bitField0_ & 2) != 0, this.getParentForChildren(), this.isClean());
                this.parameters_ = null;
            }
            return this.parametersBuilder_;
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

