/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.VisibilityOrBuilder;
import com.google.api.VisibilityProto;
import com.google.api.VisibilityRule;
import com.google.api.VisibilityRuleOrBuilder;
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

public final class Visibility
extends GeneratedMessageV3
implements VisibilityOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int RULES_FIELD_NUMBER = 1;
    private List<VisibilityRule> rules_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Visibility DEFAULT_INSTANCE = new Visibility();
    private static final Parser<Visibility> PARSER = new AbstractParser<Visibility>(){

        @Override
        public Visibility parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Visibility.newBuilder();
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

    private Visibility(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Visibility() {
        this.rules_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Visibility();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return VisibilityProto.internal_static_google_api_Visibility_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return VisibilityProto.internal_static_google_api_Visibility_fieldAccessorTable.ensureFieldAccessorsInitialized(Visibility.class, Builder.class);
    }

    @Override
    public List<VisibilityRule> getRulesList() {
        return this.rules_;
    }

    @Override
    public List<? extends VisibilityRuleOrBuilder> getRulesOrBuilderList() {
        return this.rules_;
    }

    @Override
    public int getRulesCount() {
        return this.rules_.size();
    }

    @Override
    public VisibilityRule getRules(int index) {
        return this.rules_.get(index);
    }

    @Override
    public VisibilityRuleOrBuilder getRulesOrBuilder(int index) {
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
        if (!(obj instanceof Visibility)) {
            return super.equals(obj);
        }
        Visibility other = (Visibility)obj;
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
        hash = 19 * hash + Visibility.getDescriptor().hashCode();
        if (this.getRulesCount() > 0) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getRulesList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Visibility parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Visibility parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Visibility parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Visibility parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Visibility parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Visibility parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Visibility parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Visibility parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Visibility parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Visibility parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Visibility parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Visibility parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Visibility.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Visibility prototype) {
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

    public static Visibility getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Visibility> parser() {
        return PARSER;
    }

    public Parser<Visibility> getParserForType() {
        return PARSER;
    }

    @Override
    public Visibility getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements VisibilityOrBuilder {
        private int bitField0_;
        private List<VisibilityRule> rules_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<VisibilityRule, VisibilityRule.Builder, VisibilityRuleOrBuilder> rulesBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return VisibilityProto.internal_static_google_api_Visibility_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return VisibilityProto.internal_static_google_api_Visibility_fieldAccessorTable.ensureFieldAccessorsInitialized(Visibility.class, Builder.class);
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
            return VisibilityProto.internal_static_google_api_Visibility_descriptor;
        }

        @Override
        public Visibility getDefaultInstanceForType() {
            return Visibility.getDefaultInstance();
        }

        @Override
        public Visibility build() {
            Visibility result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Visibility buildPartial() {
            Visibility result = new Visibility(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(Visibility result) {
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

        private void buildPartial0(Visibility result) {
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
            if (other instanceof Visibility) {
                return this.mergeFrom((Visibility)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Visibility other) {
            if (other == Visibility.getDefaultInstance()) {
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
                            VisibilityRule m4 = input.readMessage(VisibilityRule.parser(), extensionRegistry);
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
                this.rules_ = new ArrayList<VisibilityRule>(this.rules_);
                this.bitField0_ |= 1;
            }
        }

        @Override
        public List<VisibilityRule> getRulesList() {
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
        public VisibilityRule getRules(int index) {
            if (this.rulesBuilder_ == null) {
                return this.rules_.get(index);
            }
            return this.rulesBuilder_.getMessage(index);
        }

        public Builder setRules(int index, VisibilityRule value) {
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

        public Builder setRules(int index, VisibilityRule.Builder builderForValue) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                this.rules_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.rulesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addRules(VisibilityRule value) {
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

        public Builder addRules(int index, VisibilityRule value) {
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

        public Builder addRules(VisibilityRule.Builder builderForValue) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                this.rules_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.rulesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addRules(int index, VisibilityRule.Builder builderForValue) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                this.rules_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.rulesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllRules(Iterable<? extends VisibilityRule> values2) {
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

        public VisibilityRule.Builder getRulesBuilder(int index) {
            return this.getRulesFieldBuilder().getBuilder(index);
        }

        @Override
        public VisibilityRuleOrBuilder getRulesOrBuilder(int index) {
            if (this.rulesBuilder_ == null) {
                return this.rules_.get(index);
            }
            return this.rulesBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends VisibilityRuleOrBuilder> getRulesOrBuilderList() {
            if (this.rulesBuilder_ != null) {
                return this.rulesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.rules_);
        }

        public VisibilityRule.Builder addRulesBuilder() {
            return this.getRulesFieldBuilder().addBuilder(VisibilityRule.getDefaultInstance());
        }

        public VisibilityRule.Builder addRulesBuilder(int index) {
            return this.getRulesFieldBuilder().addBuilder(index, VisibilityRule.getDefaultInstance());
        }

        public List<VisibilityRule.Builder> getRulesBuilderList() {
            return this.getRulesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<VisibilityRule, VisibilityRule.Builder, VisibilityRuleOrBuilder> getRulesFieldBuilder() {
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

