/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.FieldPolicy;
import com.google.api.FieldPolicyOrBuilder;
import com.google.api.MethodPolicyOrBuilder;
import com.google.api.PolicyProto;
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

public final class MethodPolicy
extends GeneratedMessageV3
implements MethodPolicyOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int SELECTOR_FIELD_NUMBER = 9;
    private volatile Object selector_ = "";
    public static final int REQUEST_POLICIES_FIELD_NUMBER = 2;
    private List<FieldPolicy> requestPolicies_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final MethodPolicy DEFAULT_INSTANCE = new MethodPolicy();
    private static final Parser<MethodPolicy> PARSER = new AbstractParser<MethodPolicy>(){

        @Override
        public MethodPolicy parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = MethodPolicy.newBuilder();
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

    private MethodPolicy(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private MethodPolicy() {
        this.selector_ = "";
        this.requestPolicies_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new MethodPolicy();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return PolicyProto.internal_static_google_api_MethodPolicy_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return PolicyProto.internal_static_google_api_MethodPolicy_fieldAccessorTable.ensureFieldAccessorsInitialized(MethodPolicy.class, Builder.class);
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
    public List<FieldPolicy> getRequestPoliciesList() {
        return this.requestPolicies_;
    }

    @Override
    public List<? extends FieldPolicyOrBuilder> getRequestPoliciesOrBuilderList() {
        return this.requestPolicies_;
    }

    @Override
    public int getRequestPoliciesCount() {
        return this.requestPolicies_.size();
    }

    @Override
    public FieldPolicy getRequestPolicies(int index) {
        return this.requestPolicies_.get(index);
    }

    @Override
    public FieldPolicyOrBuilder getRequestPoliciesOrBuilder(int index) {
        return this.requestPolicies_.get(index);
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
        for (int i = 0; i < this.requestPolicies_.size(); ++i) {
            output.writeMessage(2, this.requestPolicies_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.selector_)) {
            GeneratedMessageV3.writeString(output, 9, this.selector_);
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
        for (int i = 0; i < this.requestPolicies_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(2, this.requestPolicies_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.selector_)) {
            size += GeneratedMessageV3.computeStringSize(9, this.selector_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MethodPolicy)) {
            return super.equals(obj);
        }
        MethodPolicy other = (MethodPolicy)obj;
        if (!this.getSelector().equals(other.getSelector())) {
            return false;
        }
        if (!this.getRequestPoliciesList().equals(other.getRequestPoliciesList())) {
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
        hash = 19 * hash + MethodPolicy.getDescriptor().hashCode();
        hash = 37 * hash + 9;
        hash = 53 * hash + this.getSelector().hashCode();
        if (this.getRequestPoliciesCount() > 0) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getRequestPoliciesList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static MethodPolicy parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MethodPolicy parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MethodPolicy parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MethodPolicy parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MethodPolicy parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MethodPolicy parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MethodPolicy parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static MethodPolicy parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static MethodPolicy parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static MethodPolicy parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static MethodPolicy parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static MethodPolicy parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return MethodPolicy.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(MethodPolicy prototype) {
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

    public static MethodPolicy getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<MethodPolicy> parser() {
        return PARSER;
    }

    public Parser<MethodPolicy> getParserForType() {
        return PARSER;
    }

    @Override
    public MethodPolicy getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements MethodPolicyOrBuilder {
        private int bitField0_;
        private Object selector_ = "";
        private List<FieldPolicy> requestPolicies_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<FieldPolicy, FieldPolicy.Builder, FieldPolicyOrBuilder> requestPoliciesBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return PolicyProto.internal_static_google_api_MethodPolicy_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return PolicyProto.internal_static_google_api_MethodPolicy_fieldAccessorTable.ensureFieldAccessorsInitialized(MethodPolicy.class, Builder.class);
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
            if (this.requestPoliciesBuilder_ == null) {
                this.requestPolicies_ = Collections.emptyList();
            } else {
                this.requestPolicies_ = null;
                this.requestPoliciesBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFD;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return PolicyProto.internal_static_google_api_MethodPolicy_descriptor;
        }

        @Override
        public MethodPolicy getDefaultInstanceForType() {
            return MethodPolicy.getDefaultInstance();
        }

        @Override
        public MethodPolicy build() {
            MethodPolicy result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public MethodPolicy buildPartial() {
            MethodPolicy result = new MethodPolicy(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(MethodPolicy result) {
            if (this.requestPoliciesBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0) {
                    this.requestPolicies_ = Collections.unmodifiableList(this.requestPolicies_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.requestPolicies_ = this.requestPolicies_;
            } else {
                result.requestPolicies_ = this.requestPoliciesBuilder_.build();
            }
        }

        private void buildPartial0(MethodPolicy result) {
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
            if (other instanceof MethodPolicy) {
                return this.mergeFrom((MethodPolicy)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(MethodPolicy other) {
            if (other == MethodPolicy.getDefaultInstance()) {
                return this;
            }
            if (!other.getSelector().isEmpty()) {
                this.selector_ = other.selector_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (this.requestPoliciesBuilder_ == null) {
                if (!other.requestPolicies_.isEmpty()) {
                    if (this.requestPolicies_.isEmpty()) {
                        this.requestPolicies_ = other.requestPolicies_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureRequestPoliciesIsMutable();
                        this.requestPolicies_.addAll(other.requestPolicies_);
                    }
                    this.onChanged();
                }
            } else if (!other.requestPolicies_.isEmpty()) {
                if (this.requestPoliciesBuilder_.isEmpty()) {
                    this.requestPoliciesBuilder_.dispose();
                    this.requestPoliciesBuilder_ = null;
                    this.requestPolicies_ = other.requestPolicies_;
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.requestPoliciesBuilder_ = alwaysUseFieldBuilders ? this.getRequestPoliciesFieldBuilder() : null;
                } else {
                    this.requestPoliciesBuilder_.addAllMessages(other.requestPolicies_);
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
                        case 18: {
                            FieldPolicy m4 = input.readMessage(FieldPolicy.parser(), extensionRegistry);
                            if (this.requestPoliciesBuilder_ == null) {
                                this.ensureRequestPoliciesIsMutable();
                                this.requestPolicies_.add(m4);
                                continue block10;
                            }
                            this.requestPoliciesBuilder_.addMessage(m4);
                            continue block10;
                        }
                        case 74: {
                            this.selector_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
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
            this.selector_ = MethodPolicy.getDefaultInstance().getSelector();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setSelectorBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            MethodPolicy.checkByteStringIsUtf8(value);
            this.selector_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        private void ensureRequestPoliciesIsMutable() {
            if ((this.bitField0_ & 2) == 0) {
                this.requestPolicies_ = new ArrayList<FieldPolicy>(this.requestPolicies_);
                this.bitField0_ |= 2;
            }
        }

        @Override
        public List<FieldPolicy> getRequestPoliciesList() {
            if (this.requestPoliciesBuilder_ == null) {
                return Collections.unmodifiableList(this.requestPolicies_);
            }
            return this.requestPoliciesBuilder_.getMessageList();
        }

        @Override
        public int getRequestPoliciesCount() {
            if (this.requestPoliciesBuilder_ == null) {
                return this.requestPolicies_.size();
            }
            return this.requestPoliciesBuilder_.getCount();
        }

        @Override
        public FieldPolicy getRequestPolicies(int index) {
            if (this.requestPoliciesBuilder_ == null) {
                return this.requestPolicies_.get(index);
            }
            return this.requestPoliciesBuilder_.getMessage(index);
        }

        public Builder setRequestPolicies(int index, FieldPolicy value) {
            if (this.requestPoliciesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRequestPoliciesIsMutable();
                this.requestPolicies_.set(index, value);
                this.onChanged();
            } else {
                this.requestPoliciesBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setRequestPolicies(int index, FieldPolicy.Builder builderForValue) {
            if (this.requestPoliciesBuilder_ == null) {
                this.ensureRequestPoliciesIsMutable();
                this.requestPolicies_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.requestPoliciesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addRequestPolicies(FieldPolicy value) {
            if (this.requestPoliciesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRequestPoliciesIsMutable();
                this.requestPolicies_.add(value);
                this.onChanged();
            } else {
                this.requestPoliciesBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addRequestPolicies(int index, FieldPolicy value) {
            if (this.requestPoliciesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRequestPoliciesIsMutable();
                this.requestPolicies_.add(index, value);
                this.onChanged();
            } else {
                this.requestPoliciesBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addRequestPolicies(FieldPolicy.Builder builderForValue) {
            if (this.requestPoliciesBuilder_ == null) {
                this.ensureRequestPoliciesIsMutable();
                this.requestPolicies_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.requestPoliciesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addRequestPolicies(int index, FieldPolicy.Builder builderForValue) {
            if (this.requestPoliciesBuilder_ == null) {
                this.ensureRequestPoliciesIsMutable();
                this.requestPolicies_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.requestPoliciesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllRequestPolicies(Iterable<? extends FieldPolicy> values2) {
            if (this.requestPoliciesBuilder_ == null) {
                this.ensureRequestPoliciesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.requestPolicies_);
                this.onChanged();
            } else {
                this.requestPoliciesBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearRequestPolicies() {
            if (this.requestPoliciesBuilder_ == null) {
                this.requestPolicies_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
            } else {
                this.requestPoliciesBuilder_.clear();
            }
            return this;
        }

        public Builder removeRequestPolicies(int index) {
            if (this.requestPoliciesBuilder_ == null) {
                this.ensureRequestPoliciesIsMutable();
                this.requestPolicies_.remove(index);
                this.onChanged();
            } else {
                this.requestPoliciesBuilder_.remove(index);
            }
            return this;
        }

        public FieldPolicy.Builder getRequestPoliciesBuilder(int index) {
            return this.getRequestPoliciesFieldBuilder().getBuilder(index);
        }

        @Override
        public FieldPolicyOrBuilder getRequestPoliciesOrBuilder(int index) {
            if (this.requestPoliciesBuilder_ == null) {
                return this.requestPolicies_.get(index);
            }
            return this.requestPoliciesBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends FieldPolicyOrBuilder> getRequestPoliciesOrBuilderList() {
            if (this.requestPoliciesBuilder_ != null) {
                return this.requestPoliciesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.requestPolicies_);
        }

        public FieldPolicy.Builder addRequestPoliciesBuilder() {
            return this.getRequestPoliciesFieldBuilder().addBuilder(FieldPolicy.getDefaultInstance());
        }

        public FieldPolicy.Builder addRequestPoliciesBuilder(int index) {
            return this.getRequestPoliciesFieldBuilder().addBuilder(index, FieldPolicy.getDefaultInstance());
        }

        public List<FieldPolicy.Builder> getRequestPoliciesBuilderList() {
            return this.getRequestPoliciesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<FieldPolicy, FieldPolicy.Builder, FieldPolicyOrBuilder> getRequestPoliciesFieldBuilder() {
            if (this.requestPoliciesBuilder_ == null) {
                this.requestPoliciesBuilder_ = new RepeatedFieldBuilderV3(this.requestPolicies_, (this.bitField0_ & 2) != 0, this.getParentForChildren(), this.isClean());
                this.requestPolicies_ = null;
            }
            return this.requestPoliciesBuilder_;
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

