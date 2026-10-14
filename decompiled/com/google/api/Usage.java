/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.UsageOrBuilder;
import com.google.api.UsageProto;
import com.google.api.UsageRule;
import com.google.api.UsageRuleOrBuilder;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Usage
extends GeneratedMessageV3
implements UsageOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int REQUIREMENTS_FIELD_NUMBER = 1;
    private LazyStringArrayList requirements_ = LazyStringArrayList.emptyList();
    public static final int RULES_FIELD_NUMBER = 6;
    private List<UsageRule> rules_;
    public static final int PRODUCER_NOTIFICATION_CHANNEL_FIELD_NUMBER = 7;
    private volatile Object producerNotificationChannel_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final Usage DEFAULT_INSTANCE = new Usage();
    private static final Parser<Usage> PARSER = new AbstractParser<Usage>(){

        @Override
        public Usage parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Usage.newBuilder();
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

    private Usage(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Usage() {
        this.requirements_ = LazyStringArrayList.emptyList();
        this.rules_ = Collections.emptyList();
        this.producerNotificationChannel_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Usage();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return UsageProto.internal_static_google_api_Usage_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return UsageProto.internal_static_google_api_Usage_fieldAccessorTable.ensureFieldAccessorsInitialized(Usage.class, Builder.class);
    }

    public ProtocolStringList getRequirementsList() {
        return this.requirements_;
    }

    @Override
    public int getRequirementsCount() {
        return this.requirements_.size();
    }

    @Override
    public String getRequirements(int index) {
        return this.requirements_.get(index);
    }

    @Override
    public ByteString getRequirementsBytes(int index) {
        return this.requirements_.getByteString(index);
    }

    @Override
    public List<UsageRule> getRulesList() {
        return this.rules_;
    }

    @Override
    public List<? extends UsageRuleOrBuilder> getRulesOrBuilderList() {
        return this.rules_;
    }

    @Override
    public int getRulesCount() {
        return this.rules_.size();
    }

    @Override
    public UsageRule getRules(int index) {
        return this.rules_.get(index);
    }

    @Override
    public UsageRuleOrBuilder getRulesOrBuilder(int index) {
        return this.rules_.get(index);
    }

    @Override
    public String getProducerNotificationChannel() {
        Object ref = this.producerNotificationChannel_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.producerNotificationChannel_ = s2;
        return s2;
    }

    @Override
    public ByteString getProducerNotificationChannelBytes() {
        Object ref = this.producerNotificationChannel_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.producerNotificationChannel_ = b;
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
        int i;
        for (i = 0; i < this.requirements_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 1, this.requirements_.getRaw(i));
        }
        for (i = 0; i < this.rules_.size(); ++i) {
            output.writeMessage(6, this.rules_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.producerNotificationChannel_)) {
            GeneratedMessageV3.writeString(output, 7, this.producerNotificationChannel_);
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
        int dataSize = 0;
        for (int i = 0; i < this.requirements_.size(); ++i) {
            dataSize += Usage.computeStringSizeNoTag(this.requirements_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getRequirementsList().size();
        for (int i = 0; i < this.rules_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(6, this.rules_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.producerNotificationChannel_)) {
            size += GeneratedMessageV3.computeStringSize(7, this.producerNotificationChannel_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Usage)) {
            return super.equals(obj);
        }
        Usage other = (Usage)obj;
        if (!this.getRequirementsList().equals(other.getRequirementsList())) {
            return false;
        }
        if (!this.getRulesList().equals(other.getRulesList())) {
            return false;
        }
        if (!this.getProducerNotificationChannel().equals(other.getProducerNotificationChannel())) {
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
        hash = 19 * hash + Usage.getDescriptor().hashCode();
        if (this.getRequirementsCount() > 0) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getRequirementsList().hashCode();
        }
        if (this.getRulesCount() > 0) {
            hash = 37 * hash + 6;
            hash = 53 * hash + this.getRulesList().hashCode();
        }
        hash = 37 * hash + 7;
        hash = 53 * hash + this.getProducerNotificationChannel().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Usage parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Usage parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Usage parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Usage parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Usage parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Usage parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Usage parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Usage parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Usage parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Usage parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Usage parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Usage parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Usage.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Usage prototype) {
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

    public static Usage getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Usage> parser() {
        return PARSER;
    }

    public Parser<Usage> getParserForType() {
        return PARSER;
    }

    @Override
    public Usage getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements UsageOrBuilder {
        private int bitField0_;
        private LazyStringArrayList requirements_ = LazyStringArrayList.emptyList();
        private List<UsageRule> rules_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<UsageRule, UsageRule.Builder, UsageRuleOrBuilder> rulesBuilder_;
        private Object producerNotificationChannel_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return UsageProto.internal_static_google_api_Usage_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return UsageProto.internal_static_google_api_Usage_fieldAccessorTable.ensureFieldAccessorsInitialized(Usage.class, Builder.class);
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
            this.requirements_ = LazyStringArrayList.emptyList();
            if (this.rulesBuilder_ == null) {
                this.rules_ = Collections.emptyList();
            } else {
                this.rules_ = null;
                this.rulesBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFD;
            this.producerNotificationChannel_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return UsageProto.internal_static_google_api_Usage_descriptor;
        }

        @Override
        public Usage getDefaultInstanceForType() {
            return Usage.getDefaultInstance();
        }

        @Override
        public Usage build() {
            Usage result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Usage buildPartial() {
            Usage result = new Usage(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(Usage result) {
            if (this.rulesBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0) {
                    this.rules_ = Collections.unmodifiableList(this.rules_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.rules_ = this.rules_;
            } else {
                result.rules_ = this.rulesBuilder_.build();
            }
        }

        private void buildPartial0(Usage result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                this.requirements_.makeImmutable();
                result.requirements_ = this.requirements_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.producerNotificationChannel_ = this.producerNotificationChannel_;
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
            if (other instanceof Usage) {
                return this.mergeFrom((Usage)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Usage other) {
            if (other == Usage.getDefaultInstance()) {
                return this;
            }
            if (!other.requirements_.isEmpty()) {
                if (this.requirements_.isEmpty()) {
                    this.requirements_ = other.requirements_;
                    this.bitField0_ |= 1;
                } else {
                    this.ensureRequirementsIsMutable();
                    this.requirements_.addAll(other.requirements_);
                }
                this.onChanged();
            }
            if (this.rulesBuilder_ == null) {
                if (!other.rules_.isEmpty()) {
                    if (this.rules_.isEmpty()) {
                        this.rules_ = other.rules_;
                        this.bitField0_ &= 0xFFFFFFFD;
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
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.rulesBuilder_ = alwaysUseFieldBuilders ? this.getRulesFieldBuilder() : null;
                } else {
                    this.rulesBuilder_.addAllMessages(other.rules_);
                }
            }
            if (!other.getProducerNotificationChannel().isEmpty()) {
                this.producerNotificationChannel_ = other.producerNotificationChannel_;
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
                            String s2 = input.readStringRequireUtf8();
                            this.ensureRequirementsIsMutable();
                            this.requirements_.add(s2);
                            continue block11;
                        }
                        case 50: {
                            UsageRule m4 = input.readMessage(UsageRule.parser(), extensionRegistry);
                            if (this.rulesBuilder_ == null) {
                                this.ensureRulesIsMutable();
                                this.rules_.add(m4);
                                continue block11;
                            }
                            this.rulesBuilder_.addMessage(m4);
                            continue block11;
                        }
                        case 58: {
                            this.producerNotificationChannel_ = input.readStringRequireUtf8();
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

        private void ensureRequirementsIsMutable() {
            if (!this.requirements_.isModifiable()) {
                this.requirements_ = new LazyStringArrayList(this.requirements_);
            }
            this.bitField0_ |= 1;
        }

        public ProtocolStringList getRequirementsList() {
            this.requirements_.makeImmutable();
            return this.requirements_;
        }

        @Override
        public int getRequirementsCount() {
            return this.requirements_.size();
        }

        @Override
        public String getRequirements(int index) {
            return this.requirements_.get(index);
        }

        @Override
        public ByteString getRequirementsBytes(int index) {
            return this.requirements_.getByteString(index);
        }

        public Builder setRequirements(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureRequirementsIsMutable();
            this.requirements_.set(index, value);
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder addRequirements(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureRequirementsIsMutable();
            this.requirements_.add(value);
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder addAllRequirements(Iterable<String> values2) {
            this.ensureRequirementsIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.requirements_);
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearRequirements() {
            this.requirements_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder addRequirementsBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Usage.checkByteStringIsUtf8(value);
            this.ensureRequirementsIsMutable();
            this.requirements_.add(value);
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        private void ensureRulesIsMutable() {
            if ((this.bitField0_ & 2) == 0) {
                this.rules_ = new ArrayList<UsageRule>(this.rules_);
                this.bitField0_ |= 2;
            }
        }

        @Override
        public List<UsageRule> getRulesList() {
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
        public UsageRule getRules(int index) {
            if (this.rulesBuilder_ == null) {
                return this.rules_.get(index);
            }
            return this.rulesBuilder_.getMessage(index);
        }

        public Builder setRules(int index, UsageRule value) {
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

        public Builder setRules(int index, UsageRule.Builder builderForValue) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                this.rules_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.rulesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addRules(UsageRule value) {
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

        public Builder addRules(int index, UsageRule value) {
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

        public Builder addRules(UsageRule.Builder builderForValue) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                this.rules_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.rulesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addRules(int index, UsageRule.Builder builderForValue) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                this.rules_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.rulesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllRules(Iterable<? extends UsageRule> values2) {
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
                this.bitField0_ &= 0xFFFFFFFD;
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

        public UsageRule.Builder getRulesBuilder(int index) {
            return this.getRulesFieldBuilder().getBuilder(index);
        }

        @Override
        public UsageRuleOrBuilder getRulesOrBuilder(int index) {
            if (this.rulesBuilder_ == null) {
                return this.rules_.get(index);
            }
            return this.rulesBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends UsageRuleOrBuilder> getRulesOrBuilderList() {
            if (this.rulesBuilder_ != null) {
                return this.rulesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.rules_);
        }

        public UsageRule.Builder addRulesBuilder() {
            return this.getRulesFieldBuilder().addBuilder(UsageRule.getDefaultInstance());
        }

        public UsageRule.Builder addRulesBuilder(int index) {
            return this.getRulesFieldBuilder().addBuilder(index, UsageRule.getDefaultInstance());
        }

        public List<UsageRule.Builder> getRulesBuilderList() {
            return this.getRulesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<UsageRule, UsageRule.Builder, UsageRuleOrBuilder> getRulesFieldBuilder() {
            if (this.rulesBuilder_ == null) {
                this.rulesBuilder_ = new RepeatedFieldBuilderV3(this.rules_, (this.bitField0_ & 2) != 0, this.getParentForChildren(), this.isClean());
                this.rules_ = null;
            }
            return this.rulesBuilder_;
        }

        @Override
        public String getProducerNotificationChannel() {
            Object ref = this.producerNotificationChannel_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.producerNotificationChannel_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getProducerNotificationChannelBytes() {
            Object ref = this.producerNotificationChannel_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.producerNotificationChannel_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setProducerNotificationChannel(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.producerNotificationChannel_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearProducerNotificationChannel() {
            this.producerNotificationChannel_ = Usage.getDefaultInstance().getProducerNotificationChannel();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setProducerNotificationChannelBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Usage.checkByteStringIsUtf8(value);
            this.producerNotificationChannel_ = value;
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
}

