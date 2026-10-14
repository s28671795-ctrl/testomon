/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.BackendOrBuilder;
import com.google.api.BackendProto;
import com.google.api.BackendRule;
import com.google.api.BackendRuleOrBuilder;
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

public final class Backend
extends GeneratedMessageV3
implements BackendOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int RULES_FIELD_NUMBER = 1;
    private List<BackendRule> rules_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Backend DEFAULT_INSTANCE = new Backend();
    private static final Parser<Backend> PARSER = new AbstractParser<Backend>(){

        @Override
        public Backend parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Backend.newBuilder();
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

    private Backend(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Backend() {
        this.rules_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Backend();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return BackendProto.internal_static_google_api_Backend_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return BackendProto.internal_static_google_api_Backend_fieldAccessorTable.ensureFieldAccessorsInitialized(Backend.class, Builder.class);
    }

    @Override
    public List<BackendRule> getRulesList() {
        return this.rules_;
    }

    @Override
    public List<? extends BackendRuleOrBuilder> getRulesOrBuilderList() {
        return this.rules_;
    }

    @Override
    public int getRulesCount() {
        return this.rules_.size();
    }

    @Override
    public BackendRule getRules(int index) {
        return this.rules_.get(index);
    }

    @Override
    public BackendRuleOrBuilder getRulesOrBuilder(int index) {
        return this.rules_.get(index);
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
        for (int i = 0; i < this.rules_.size(); ++i) {
            output.writeMessage(1, this.rules_.get(i));
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
        for (int i = 0; i < this.rules_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(1, this.rules_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Backend)) {
            return super.equals(obj);
        }
        Backend other = (Backend)obj;
        if (!this.getRulesList().equals(other.getRulesList())) {
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
        hash = 19 * hash + Backend.getDescriptor().hashCode();
        if (this.getRulesCount() > 0) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getRulesList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Backend parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Backend parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Backend parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Backend parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Backend parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Backend parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Backend parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Backend parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Backend parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Backend parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Backend parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Backend parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Backend.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Backend prototype) {
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

    public static Backend getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Backend> parser() {
        return PARSER;
    }

    public Parser<Backend> getParserForType() {
        return PARSER;
    }

    @Override
    public Backend getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements BackendOrBuilder {
        private int bitField0_;
        private List<BackendRule> rules_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<BackendRule, BackendRule.Builder, BackendRuleOrBuilder> rulesBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return BackendProto.internal_static_google_api_Backend_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return BackendProto.internal_static_google_api_Backend_fieldAccessorTable.ensureFieldAccessorsInitialized(Backend.class, Builder.class);
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
            if (this.rulesBuilder_ == null) {
                this.rules_ = Collections.emptyList();
            } else {
                this.rules_ = null;
                this.rulesBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFE;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return BackendProto.internal_static_google_api_Backend_descriptor;
        }

        @Override
        public Backend getDefaultInstanceForType() {
            return Backend.getDefaultInstance();
        }

        @Override
        public Backend build() {
            Backend result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Backend buildPartial() {
            Backend result = new Backend(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(Backend result) {
            if (this.rulesBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0) {
                    this.rules_ = Collections.unmodifiableList(this.rules_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.rules_ = this.rules_;
            } else {
                result.rules_ = this.rulesBuilder_.build();
            }
        }

        private void buildPartial0(Backend result) {
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
            if (other instanceof Backend) {
                return this.mergeFrom((Backend)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Backend other) {
            if (other == Backend.getDefaultInstance()) {
                return this;
            }
            if (this.rulesBuilder_ == null) {
                if (!other.rules_.isEmpty()) {
                    if (this.rules_.isEmpty()) {
                        this.rules_ = other.rules_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureRulesIsMutable();
                        this.rules_.addAll(other.rules_);
                    }
                    this.onChanged();
                }
            } else if (!other.rules_.isEmpty()) {
                if (this.rulesBuilder_.isEmpty()) {
                    this.rulesBuilder_.dispose();
                    this.rulesBuilder_ = null;
                    this.rules_ = other.rules_;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.rulesBuilder_ = alwaysUseFieldBuilders ? this.getRulesFieldBuilder() : null;
                } else {
                    this.rulesBuilder_.addAllMessages(other.rules_);
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
                        case 10: {
                            BackendRule m4 = input.readMessage(BackendRule.parser(), extensionRegistry);
                            if (this.rulesBuilder_ == null) {
                                this.ensureRulesIsMutable();
                                this.rules_.add(m4);
                                continue block9;
                            }
                            this.rulesBuilder_.addMessage(m4);
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

        private void ensureRulesIsMutable() {
            if ((this.bitField0_ & 1) == 0) {
                this.rules_ = new ArrayList<BackendRule>(this.rules_);
                this.bitField0_ |= 1;
            }
        }

        @Override
        public List<BackendRule> getRulesList() {
            if (this.rulesBuilder_ == null) {
                return Collections.unmodifiableList(this.rules_);
            }
            return this.rulesBuilder_.getMessageList();
        }

        @Override
        public int getRulesCount() {
            if (this.rulesBuilder_ == null) {
                return this.rules_.size();
            }
            return this.rulesBuilder_.getCount();
        }

        @Override
        public BackendRule getRules(int index) {
            if (this.rulesBuilder_ == null) {
                return this.rules_.get(index);
            }
            return this.rulesBuilder_.getMessage(index);
        }

        public Builder setRules(int index, BackendRule value) {
            if (this.rulesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRulesIsMutable();
                this.rules_.set(index, value);
                this.onChanged();
            } else {
                this.rulesBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setRules(int index, BackendRule.Builder builderForValue) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                this.rules_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.rulesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addRules(BackendRule value) {
            if (this.rulesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRulesIsMutable();
                this.rules_.add(value);
                this.onChanged();
            } else {
                this.rulesBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addRules(int index, BackendRule value) {
            if (this.rulesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRulesIsMutable();
                this.rules_.add(index, value);
                this.onChanged();
            } else {
                this.rulesBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addRules(BackendRule.Builder builderForValue) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                this.rules_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.rulesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addRules(int index, BackendRule.Builder builderForValue) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                this.rules_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.rulesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllRules(Iterable<? extends BackendRule> values2) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.rules_);
                this.onChanged();
            } else {
                this.rulesBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearRules() {
            if (this.rulesBuilder_ == null) {
                this.rules_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
            } else {
                this.rulesBuilder_.clear();
            }
            return this;
        }

        public Builder removeRules(int index) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                this.rules_.remove(index);
                this.onChanged();
            } else {
                this.rulesBuilder_.remove(index);
            }
            return this;
        }

        public BackendRule.Builder getRulesBuilder(int index) {
            return this.getRulesFieldBuilder().getBuilder(index);
        }

        @Override
        public BackendRuleOrBuilder getRulesOrBuilder(int index) {
            if (this.rulesBuilder_ == null) {
                return this.rules_.get(index);
            }
            return this.rulesBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends BackendRuleOrBuilder> getRulesOrBuilderList() {
            if (this.rulesBuilder_ != null) {
                return this.rulesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.rules_);
        }

        public BackendRule.Builder addRulesBuilder() {
            return this.getRulesFieldBuilder().addBuilder(BackendRule.getDefaultInstance());
        }

        public BackendRule.Builder addRulesBuilder(int index) {
            return this.getRulesFieldBuilder().addBuilder(index, BackendRule.getDefaultInstance());
        }

        public List<BackendRule.Builder> getRulesBuilderList() {
            return this.getRulesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<BackendRule, BackendRule.Builder, BackendRuleOrBuilder> getRulesFieldBuilder() {
            if (this.rulesBuilder_ == null) {
                this.rulesBuilder_ = new RepeatedFieldBuilderV3(this.rules_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
                this.rules_ = null;
            }
            return this.rulesBuilder_;
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

