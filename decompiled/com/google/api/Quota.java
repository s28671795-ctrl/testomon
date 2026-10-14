/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.MetricRule;
import com.google.api.MetricRuleOrBuilder;
import com.google.api.QuotaLimit;
import com.google.api.QuotaLimitOrBuilder;
import com.google.api.QuotaOrBuilder;
import com.google.api.QuotaProto;
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

public final class Quota
extends GeneratedMessageV3
implements QuotaOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int LIMITS_FIELD_NUMBER = 3;
    private List<QuotaLimit> limits_;
    public static final int METRIC_RULES_FIELD_NUMBER = 4;
    private List<MetricRule> metricRules_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Quota DEFAULT_INSTANCE = new Quota();
    private static final Parser<Quota> PARSER = new AbstractParser<Quota>(){

        @Override
        public Quota parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Quota.newBuilder();
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

    private Quota(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Quota() {
        this.limits_ = Collections.emptyList();
        this.metricRules_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Quota();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return QuotaProto.internal_static_google_api_Quota_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return QuotaProto.internal_static_google_api_Quota_fieldAccessorTable.ensureFieldAccessorsInitialized(Quota.class, Builder.class);
    }

    @Override
    public List<QuotaLimit> getLimitsList() {
        return this.limits_;
    }

    @Override
    public List<? extends QuotaLimitOrBuilder> getLimitsOrBuilderList() {
        return this.limits_;
    }

    @Override
    public int getLimitsCount() {
        return this.limits_.size();
    }

    @Override
    public QuotaLimit getLimits(int index) {
        return this.limits_.get(index);
    }

    @Override
    public QuotaLimitOrBuilder getLimitsOrBuilder(int index) {
        return this.limits_.get(index);
    }

    @Override
    public List<MetricRule> getMetricRulesList() {
        return this.metricRules_;
    }

    @Override
    public List<? extends MetricRuleOrBuilder> getMetricRulesOrBuilderList() {
        return this.metricRules_;
    }

    @Override
    public int getMetricRulesCount() {
        return this.metricRules_.size();
    }

    @Override
    public MetricRule getMetricRules(int index) {
        return this.metricRules_.get(index);
    }

    @Override
    public MetricRuleOrBuilder getMetricRulesOrBuilder(int index) {
        return this.metricRules_.get(index);
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
        for (i = 0; i < this.limits_.size(); ++i) {
            output.writeMessage(3, this.limits_.get(i));
        }
        for (i = 0; i < this.metricRules_.size(); ++i) {
            output.writeMessage(4, this.metricRules_.get(i));
        }
        this.getUnknownFields().writeTo(output);
    }

    @Override
    public int getSerializedSize() {
        int i;
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        for (i = 0; i < this.limits_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(3, this.limits_.get(i));
        }
        for (i = 0; i < this.metricRules_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(4, this.metricRules_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Quota)) {
            return super.equals(obj);
        }
        Quota other = (Quota)obj;
        if (!this.getLimitsList().equals(other.getLimitsList())) {
            return false;
        }
        if (!this.getMetricRulesList().equals(other.getMetricRulesList())) {
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
        hash = 19 * hash + Quota.getDescriptor().hashCode();
        if (this.getLimitsCount() > 0) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getLimitsList().hashCode();
        }
        if (this.getMetricRulesCount() > 0) {
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getMetricRulesList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Quota parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Quota parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Quota parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Quota parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Quota parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Quota parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Quota parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Quota parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Quota parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Quota parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Quota parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Quota parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Quota.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Quota prototype) {
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

    public static Quota getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Quota> parser() {
        return PARSER;
    }

    public Parser<Quota> getParserForType() {
        return PARSER;
    }

    @Override
    public Quota getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements QuotaOrBuilder {
        private int bitField0_;
        private List<QuotaLimit> limits_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<QuotaLimit, QuotaLimit.Builder, QuotaLimitOrBuilder> limitsBuilder_;
        private List<MetricRule> metricRules_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<MetricRule, MetricRule.Builder, MetricRuleOrBuilder> metricRulesBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return QuotaProto.internal_static_google_api_Quota_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return QuotaProto.internal_static_google_api_Quota_fieldAccessorTable.ensureFieldAccessorsInitialized(Quota.class, Builder.class);
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
            if (this.limitsBuilder_ == null) {
                this.limits_ = Collections.emptyList();
            } else {
                this.limits_ = null;
                this.limitsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFE;
            if (this.metricRulesBuilder_ == null) {
                this.metricRules_ = Collections.emptyList();
            } else {
                this.metricRules_ = null;
                this.metricRulesBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFD;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return QuotaProto.internal_static_google_api_Quota_descriptor;
        }

        @Override
        public Quota getDefaultInstanceForType() {
            return Quota.getDefaultInstance();
        }

        @Override
        public Quota build() {
            Quota result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Quota buildPartial() {
            Quota result = new Quota(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(Quota result) {
            if (this.limitsBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0) {
                    this.limits_ = Collections.unmodifiableList(this.limits_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.limits_ = this.limits_;
            } else {
                result.limits_ = this.limitsBuilder_.build();
            }
            if (this.metricRulesBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0) {
                    this.metricRules_ = Collections.unmodifiableList(this.metricRules_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.metricRules_ = this.metricRules_;
            } else {
                result.metricRules_ = this.metricRulesBuilder_.build();
            }
        }

        private void buildPartial0(Quota result) {
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
            if (other instanceof Quota) {
                return this.mergeFrom((Quota)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Quota other) {
            if (other == Quota.getDefaultInstance()) {
                return this;
            }
            if (this.limitsBuilder_ == null) {
                if (!other.limits_.isEmpty()) {
                    if (this.limits_.isEmpty()) {
                        this.limits_ = other.limits_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureLimitsIsMutable();
                        this.limits_.addAll(other.limits_);
                    }
                    this.onChanged();
                }
            } else if (!other.limits_.isEmpty()) {
                if (this.limitsBuilder_.isEmpty()) {
                    this.limitsBuilder_.dispose();
                    this.limitsBuilder_ = null;
                    this.limits_ = other.limits_;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.limitsBuilder_ = alwaysUseFieldBuilders ? this.getLimitsFieldBuilder() : null;
                } else {
                    this.limitsBuilder_.addAllMessages(other.limits_);
                }
            }
            if (this.metricRulesBuilder_ == null) {
                if (!other.metricRules_.isEmpty()) {
                    if (this.metricRules_.isEmpty()) {
                        this.metricRules_ = other.metricRules_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureMetricRulesIsMutable();
                        this.metricRules_.addAll(other.metricRules_);
                    }
                    this.onChanged();
                }
            } else if (!other.metricRules_.isEmpty()) {
                if (this.metricRulesBuilder_.isEmpty()) {
                    this.metricRulesBuilder_.dispose();
                    this.metricRulesBuilder_ = null;
                    this.metricRules_ = other.metricRules_;
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.metricRulesBuilder_ = alwaysUseFieldBuilders ? this.getMetricRulesFieldBuilder() : null;
                } else {
                    this.metricRulesBuilder_.addAllMessages(other.metricRules_);
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
                        case 26: {
                            GeneratedMessageV3 m4 = input.readMessage(QuotaLimit.parser(), extensionRegistry);
                            if (this.limitsBuilder_ == null) {
                                this.ensureLimitsIsMutable();
                                this.limits_.add((QuotaLimit)m4);
                                continue block10;
                            }
                            this.limitsBuilder_.addMessage((QuotaLimit)m4);
                            continue block10;
                        }
                        case 34: {
                            GeneratedMessageV3 m4 = input.readMessage(MetricRule.parser(), extensionRegistry);
                            if (this.metricRulesBuilder_ == null) {
                                this.ensureMetricRulesIsMutable();
                                this.metricRules_.add((MetricRule)m4);
                                continue block10;
                            }
                            this.metricRulesBuilder_.addMessage((MetricRule)m4);
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

        private void ensureLimitsIsMutable() {
            if ((this.bitField0_ & 1) == 0) {
                this.limits_ = new ArrayList<QuotaLimit>(this.limits_);
                this.bitField0_ |= 1;
            }
        }

        @Override
        public List<QuotaLimit> getLimitsList() {
            if (this.limitsBuilder_ == null) {
                return Collections.unmodifiableList(this.limits_);
            }
            return this.limitsBuilder_.getMessageList();
        }

        @Override
        public int getLimitsCount() {
            if (this.limitsBuilder_ == null) {
                return this.limits_.size();
            }
            return this.limitsBuilder_.getCount();
        }

        @Override
        public QuotaLimit getLimits(int index) {
            if (this.limitsBuilder_ == null) {
                return this.limits_.get(index);
            }
            return this.limitsBuilder_.getMessage(index);
        }

        public Builder setLimits(int index, QuotaLimit value) {
            if (this.limitsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLimitsIsMutable();
                this.limits_.set(index, value);
                this.onChanged();
            } else {
                this.limitsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setLimits(int index, QuotaLimit.Builder builderForValue) {
            if (this.limitsBuilder_ == null) {
                this.ensureLimitsIsMutable();
                this.limits_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.limitsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addLimits(QuotaLimit value) {
            if (this.limitsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLimitsIsMutable();
                this.limits_.add(value);
                this.onChanged();
            } else {
                this.limitsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addLimits(int index, QuotaLimit value) {
            if (this.limitsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLimitsIsMutable();
                this.limits_.add(index, value);
                this.onChanged();
            } else {
                this.limitsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addLimits(QuotaLimit.Builder builderForValue) {
            if (this.limitsBuilder_ == null) {
                this.ensureLimitsIsMutable();
                this.limits_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.limitsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addLimits(int index, QuotaLimit.Builder builderForValue) {
            if (this.limitsBuilder_ == null) {
                this.ensureLimitsIsMutable();
                this.limits_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.limitsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllLimits(Iterable<? extends QuotaLimit> values2) {
            if (this.limitsBuilder_ == null) {
                this.ensureLimitsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.limits_);
                this.onChanged();
            } else {
                this.limitsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearLimits() {
            if (this.limitsBuilder_ == null) {
                this.limits_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
            } else {
                this.limitsBuilder_.clear();
            }
            return this;
        }

        public Builder removeLimits(int index) {
            if (this.limitsBuilder_ == null) {
                this.ensureLimitsIsMutable();
                this.limits_.remove(index);
                this.onChanged();
            } else {
                this.limitsBuilder_.remove(index);
            }
            return this;
        }

        public QuotaLimit.Builder getLimitsBuilder(int index) {
            return this.getLimitsFieldBuilder().getBuilder(index);
        }

        @Override
        public QuotaLimitOrBuilder getLimitsOrBuilder(int index) {
            if (this.limitsBuilder_ == null) {
                return this.limits_.get(index);
            }
            return this.limitsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends QuotaLimitOrBuilder> getLimitsOrBuilderList() {
            if (this.limitsBuilder_ != null) {
                return this.limitsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.limits_);
        }

        public QuotaLimit.Builder addLimitsBuilder() {
            return this.getLimitsFieldBuilder().addBuilder(QuotaLimit.getDefaultInstance());
        }

        public QuotaLimit.Builder addLimitsBuilder(int index) {
            return this.getLimitsFieldBuilder().addBuilder(index, QuotaLimit.getDefaultInstance());
        }

        public List<QuotaLimit.Builder> getLimitsBuilderList() {
            return this.getLimitsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<QuotaLimit, QuotaLimit.Builder, QuotaLimitOrBuilder> getLimitsFieldBuilder() {
            if (this.limitsBuilder_ == null) {
                this.limitsBuilder_ = new RepeatedFieldBuilderV3(this.limits_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
                this.limits_ = null;
            }
            return this.limitsBuilder_;
        }

        private void ensureMetricRulesIsMutable() {
            if ((this.bitField0_ & 2) == 0) {
                this.metricRules_ = new ArrayList<MetricRule>(this.metricRules_);
                this.bitField0_ |= 2;
            }
        }

        @Override
        public List<MetricRule> getMetricRulesList() {
            if (this.metricRulesBuilder_ == null) {
                return Collections.unmodifiableList(this.metricRules_);
            }
            return this.metricRulesBuilder_.getMessageList();
        }

        @Override
        public int getMetricRulesCount() {
            if (this.metricRulesBuilder_ == null) {
                return this.metricRules_.size();
            }
            return this.metricRulesBuilder_.getCount();
        }

        @Override
        public MetricRule getMetricRules(int index) {
            if (this.metricRulesBuilder_ == null) {
                return this.metricRules_.get(index);
            }
            return this.metricRulesBuilder_.getMessage(index);
        }

        public Builder setMetricRules(int index, MetricRule value) {
            if (this.metricRulesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMetricRulesIsMutable();
                this.metricRules_.set(index, value);
                this.onChanged();
            } else {
                this.metricRulesBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setMetricRules(int index, MetricRule.Builder builderForValue) {
            if (this.metricRulesBuilder_ == null) {
                this.ensureMetricRulesIsMutable();
                this.metricRules_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.metricRulesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addMetricRules(MetricRule value) {
            if (this.metricRulesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMetricRulesIsMutable();
                this.metricRules_.add(value);
                this.onChanged();
            } else {
                this.metricRulesBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addMetricRules(int index, MetricRule value) {
            if (this.metricRulesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMetricRulesIsMutable();
                this.metricRules_.add(index, value);
                this.onChanged();
            } else {
                this.metricRulesBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addMetricRules(MetricRule.Builder builderForValue) {
            if (this.metricRulesBuilder_ == null) {
                this.ensureMetricRulesIsMutable();
                this.metricRules_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.metricRulesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addMetricRules(int index, MetricRule.Builder builderForValue) {
            if (this.metricRulesBuilder_ == null) {
                this.ensureMetricRulesIsMutable();
                this.metricRules_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.metricRulesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllMetricRules(Iterable<? extends MetricRule> values2) {
            if (this.metricRulesBuilder_ == null) {
                this.ensureMetricRulesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.metricRules_);
                this.onChanged();
            } else {
                this.metricRulesBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearMetricRules() {
            if (this.metricRulesBuilder_ == null) {
                this.metricRules_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
            } else {
                this.metricRulesBuilder_.clear();
            }
            return this;
        }

        public Builder removeMetricRules(int index) {
            if (this.metricRulesBuilder_ == null) {
                this.ensureMetricRulesIsMutable();
                this.metricRules_.remove(index);
                this.onChanged();
            } else {
                this.metricRulesBuilder_.remove(index);
            }
            return this;
        }

        public MetricRule.Builder getMetricRulesBuilder(int index) {
            return this.getMetricRulesFieldBuilder().getBuilder(index);
        }

        @Override
        public MetricRuleOrBuilder getMetricRulesOrBuilder(int index) {
            if (this.metricRulesBuilder_ == null) {
                return this.metricRules_.get(index);
            }
            return this.metricRulesBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends MetricRuleOrBuilder> getMetricRulesOrBuilderList() {
            if (this.metricRulesBuilder_ != null) {
                return this.metricRulesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.metricRules_);
        }

        public MetricRule.Builder addMetricRulesBuilder() {
            return this.getMetricRulesFieldBuilder().addBuilder(MetricRule.getDefaultInstance());
        }

        public MetricRule.Builder addMetricRulesBuilder(int index) {
            return this.getMetricRulesFieldBuilder().addBuilder(index, MetricRule.getDefaultInstance());
        }

        public List<MetricRule.Builder> getMetricRulesBuilderList() {
            return this.getMetricRulesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<MetricRule, MetricRule.Builder, MetricRuleOrBuilder> getMetricRulesFieldBuilder() {
            if (this.metricRulesBuilder_ == null) {
                this.metricRulesBuilder_ = new RepeatedFieldBuilderV3(this.metricRules_, (this.bitField0_ & 2) != 0, this.getParentForChildren(), this.isClean());
                this.metricRules_ = null;
            }
            return this.metricRulesBuilder_;
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

