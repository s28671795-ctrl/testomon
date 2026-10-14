/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.RoutingParameter;
import com.google.api.RoutingParameterOrBuilder;
import com.google.api.RoutingProto;
import com.google.api.RoutingRuleOrBuilder;
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

public final class RoutingRule
extends GeneratedMessageV3
implements RoutingRuleOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int ROUTING_PARAMETERS_FIELD_NUMBER = 2;
    private List<RoutingParameter> routingParameters_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final RoutingRule DEFAULT_INSTANCE = new RoutingRule();
    private static final Parser<RoutingRule> PARSER = new AbstractParser<RoutingRule>(){

        @Override
        public RoutingRule parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = RoutingRule.newBuilder();
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

    private RoutingRule(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private RoutingRule() {
        this.routingParameters_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new RoutingRule();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return RoutingProto.internal_static_google_api_RoutingRule_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return RoutingProto.internal_static_google_api_RoutingRule_fieldAccessorTable.ensureFieldAccessorsInitialized(RoutingRule.class, Builder.class);
    }

    @Override
    public List<RoutingParameter> getRoutingParametersList() {
        return this.routingParameters_;
    }

    @Override
    public List<? extends RoutingParameterOrBuilder> getRoutingParametersOrBuilderList() {
        return this.routingParameters_;
    }

    @Override
    public int getRoutingParametersCount() {
        return this.routingParameters_.size();
    }

    @Override
    public RoutingParameter getRoutingParameters(int index) {
        return this.routingParameters_.get(index);
    }

    @Override
    public RoutingParameterOrBuilder getRoutingParametersOrBuilder(int index) {
        return this.routingParameters_.get(index);
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
        for (int i = 0; i < this.routingParameters_.size(); ++i) {
            output.writeMessage(2, this.routingParameters_.get(i));
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
        for (int i = 0; i < this.routingParameters_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(2, this.routingParameters_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RoutingRule)) {
            return super.equals(obj);
        }
        RoutingRule other = (RoutingRule)obj;
        if (!this.getRoutingParametersList().equals(other.getRoutingParametersList())) {
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
        hash = 19 * hash + RoutingRule.getDescriptor().hashCode();
        if (this.getRoutingParametersCount() > 0) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getRoutingParametersList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static RoutingRule parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RoutingRule parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RoutingRule parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RoutingRule parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RoutingRule parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RoutingRule parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RoutingRule parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static RoutingRule parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static RoutingRule parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static RoutingRule parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static RoutingRule parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static RoutingRule parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return RoutingRule.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(RoutingRule prototype) {
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

    public static RoutingRule getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<RoutingRule> parser() {
        return PARSER;
    }

    public Parser<RoutingRule> getParserForType() {
        return PARSER;
    }

    @Override
    public RoutingRule getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements RoutingRuleOrBuilder {
        private int bitField0_;
        private List<RoutingParameter> routingParameters_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<RoutingParameter, RoutingParameter.Builder, RoutingParameterOrBuilder> routingParametersBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return RoutingProto.internal_static_google_api_RoutingRule_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return RoutingProto.internal_static_google_api_RoutingRule_fieldAccessorTable.ensureFieldAccessorsInitialized(RoutingRule.class, Builder.class);
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
            if (this.routingParametersBuilder_ == null) {
                this.routingParameters_ = Collections.emptyList();
            } else {
                this.routingParameters_ = null;
                this.routingParametersBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFE;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return RoutingProto.internal_static_google_api_RoutingRule_descriptor;
        }

        @Override
        public RoutingRule getDefaultInstanceForType() {
            return RoutingRule.getDefaultInstance();
        }

        @Override
        public RoutingRule build() {
            RoutingRule result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public RoutingRule buildPartial() {
            RoutingRule result = new RoutingRule(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(RoutingRule result) {
            if (this.routingParametersBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0) {
                    this.routingParameters_ = Collections.unmodifiableList(this.routingParameters_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.routingParameters_ = this.routingParameters_;
            } else {
                result.routingParameters_ = this.routingParametersBuilder_.build();
            }
        }

        private void buildPartial0(RoutingRule result) {
            int from_bitField0_ = this.bitField0_;
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
            if (other instanceof RoutingRule) {
                return this.mergeFrom((RoutingRule)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(RoutingRule other) {
            if (other == RoutingRule.getDefaultInstance()) {
                return this;
            }
            if (this.routingParametersBuilder_ == null) {
                if (!other.routingParameters_.isEmpty()) {
                    if (this.routingParameters_.isEmpty()) {
                        this.routingParameters_ = other.routingParameters_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureRoutingParametersIsMutable();
                        this.routingParameters_.addAll(other.routingParameters_);
                    }
                    this.onChanged();
                }
            } else if (!other.routingParameters_.isEmpty()) {
                if (this.routingParametersBuilder_.isEmpty()) {
                    this.routingParametersBuilder_.dispose();
                    this.routingParametersBuilder_ = null;
                    this.routingParameters_ = other.routingParameters_;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.routingParametersBuilder_ = alwaysUseFieldBuilders ? this.getRoutingParametersFieldBuilder() : null;
                } else {
                    this.routingParametersBuilder_.addAllMessages(other.routingParameters_);
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
                block9: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block9;
                        }
                        case 18: {
                            RoutingParameter m4 = input.readMessage(RoutingParameter.parser(), extensionRegistry);
                            if (this.routingParametersBuilder_ == null) {
                                this.ensureRoutingParametersIsMutable();
                                this.routingParameters_.add(m4);
                                continue block9;
                            }
                            this.routingParametersBuilder_.addMessage(m4);
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

        private void ensureRoutingParametersIsMutable() {
            if ((this.bitField0_ & 1) == 0) {
                this.routingParameters_ = new ArrayList<RoutingParameter>(this.routingParameters_);
                this.bitField0_ |= 1;
            }
        }

        @Override
        public List<RoutingParameter> getRoutingParametersList() {
            if (this.routingParametersBuilder_ == null) {
                return Collections.unmodifiableList(this.routingParameters_);
            }
            return this.routingParametersBuilder_.getMessageList();
        }

        @Override
        public int getRoutingParametersCount() {
            if (this.routingParametersBuilder_ == null) {
                return this.routingParameters_.size();
            }
            return this.routingParametersBuilder_.getCount();
        }

        @Override
        public RoutingParameter getRoutingParameters(int index) {
            if (this.routingParametersBuilder_ == null) {
                return this.routingParameters_.get(index);
            }
            return this.routingParametersBuilder_.getMessage(index);
        }

        public Builder setRoutingParameters(int index, RoutingParameter value) {
            if (this.routingParametersBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRoutingParametersIsMutable();
                this.routingParameters_.set(index, value);
                this.onChanged();
            } else {
                this.routingParametersBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setRoutingParameters(int index, RoutingParameter.Builder builderForValue) {
            if (this.routingParametersBuilder_ == null) {
                this.ensureRoutingParametersIsMutable();
                this.routingParameters_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.routingParametersBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addRoutingParameters(RoutingParameter value) {
            if (this.routingParametersBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRoutingParametersIsMutable();
                this.routingParameters_.add(value);
                this.onChanged();
            } else {
                this.routingParametersBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addRoutingParameters(int index, RoutingParameter value) {
            if (this.routingParametersBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRoutingParametersIsMutable();
                this.routingParameters_.add(index, value);
                this.onChanged();
            } else {
                this.routingParametersBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addRoutingParameters(RoutingParameter.Builder builderForValue) {
            if (this.routingParametersBuilder_ == null) {
                this.ensureRoutingParametersIsMutable();
                this.routingParameters_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.routingParametersBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addRoutingParameters(int index, RoutingParameter.Builder builderForValue) {
            if (this.routingParametersBuilder_ == null) {
                this.ensureRoutingParametersIsMutable();
                this.routingParameters_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.routingParametersBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllRoutingParameters(Iterable<? extends RoutingParameter> values2) {
            if (this.routingParametersBuilder_ == null) {
                this.ensureRoutingParametersIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.routingParameters_);
                this.onChanged();
            } else {
                this.routingParametersBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearRoutingParameters() {
            if (this.routingParametersBuilder_ == null) {
                this.routingParameters_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
            } else {
                this.routingParametersBuilder_.clear();
            }
            return this;
        }

        public Builder removeRoutingParameters(int index) {
            if (this.routingParametersBuilder_ == null) {
                this.ensureRoutingParametersIsMutable();
                this.routingParameters_.remove(index);
                this.onChanged();
            } else {
                this.routingParametersBuilder_.remove(index);
            }
            return this;
        }

        public RoutingParameter.Builder getRoutingParametersBuilder(int index) {
            return this.getRoutingParametersFieldBuilder().getBuilder(index);
        }

        @Override
        public RoutingParameterOrBuilder getRoutingParametersOrBuilder(int index) {
            if (this.routingParametersBuilder_ == null) {
                return this.routingParameters_.get(index);
            }
            return this.routingParametersBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends RoutingParameterOrBuilder> getRoutingParametersOrBuilderList() {
            if (this.routingParametersBuilder_ != null) {
                return this.routingParametersBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.routingParameters_);
        }

        public RoutingParameter.Builder addRoutingParametersBuilder() {
            return this.getRoutingParametersFieldBuilder().addBuilder(RoutingParameter.getDefaultInstance());
        }

        public RoutingParameter.Builder addRoutingParametersBuilder(int index) {
            return this.getRoutingParametersFieldBuilder().addBuilder(index, RoutingParameter.getDefaultInstance());
        }

        public List<RoutingParameter.Builder> getRoutingParametersBuilderList() {
            return this.getRoutingParametersFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<RoutingParameter, RoutingParameter.Builder, RoutingParameterOrBuilder> getRoutingParametersFieldBuilder() {
            if (this.routingParametersBuilder_ == null) {
                this.routingParametersBuilder_ = new RepeatedFieldBuilderV3(this.routingParameters_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
                this.routingParameters_ = null;
            }
            return this.routingParametersBuilder_;
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

