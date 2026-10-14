/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ControlOrBuilder;
import com.google.api.ControlProto;
import com.google.api.MethodPolicy;
import com.google.api.MethodPolicyOrBuilder;
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

public final class Control
extends GeneratedMessageV3
implements ControlOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int ENVIRONMENT_FIELD_NUMBER = 1;
    private volatile Object environment_ = "";
    public static final int METHOD_POLICIES_FIELD_NUMBER = 4;
    private List<MethodPolicy> methodPolicies_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Control DEFAULT_INSTANCE = new Control();
    private static final Parser<Control> PARSER = new AbstractParser<Control>(){

        @Override
        public Control parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Control.newBuilder();
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

    private Control(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Control() {
        this.environment_ = "";
        this.methodPolicies_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Control();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ControlProto.internal_static_google_api_Control_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ControlProto.internal_static_google_api_Control_fieldAccessorTable.ensureFieldAccessorsInitialized(Control.class, Builder.class);
    }

    @Override
    public String getEnvironment() {
        Object ref = this.environment_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.environment_ = s2;
        return s2;
    }

    @Override
    public ByteString getEnvironmentBytes() {
        Object ref = this.environment_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.environment_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public List<MethodPolicy> getMethodPoliciesList() {
        return this.methodPolicies_;
    }

    @Override
    public List<? extends MethodPolicyOrBuilder> getMethodPoliciesOrBuilderList() {
        return this.methodPolicies_;
    }

    @Override
    public int getMethodPoliciesCount() {
        return this.methodPolicies_.size();
    }

    @Override
    public MethodPolicy getMethodPolicies(int index) {
        return this.methodPolicies_.get(index);
    }

    @Override
    public MethodPolicyOrBuilder getMethodPoliciesOrBuilder(int index) {
        return this.methodPolicies_.get(index);
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
        if (!GeneratedMessageV3.isStringEmpty(this.environment_)) {
            GeneratedMessageV3.writeString(output, 1, this.environment_);
        }
        for (int i = 0; i < this.methodPolicies_.size(); ++i) {
            output.writeMessage(4, this.methodPolicies_.get(i));
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
        if (!GeneratedMessageV3.isStringEmpty(this.environment_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.environment_);
        }
        for (int i = 0; i < this.methodPolicies_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(4, this.methodPolicies_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Control)) {
            return super.equals(obj);
        }
        Control other = (Control)obj;
        if (!this.getEnvironment().equals(other.getEnvironment())) {
            return false;
        }
        if (!this.getMethodPoliciesList().equals(other.getMethodPoliciesList())) {
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
        hash = 19 * hash + Control.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getEnvironment().hashCode();
        if (this.getMethodPoliciesCount() > 0) {
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getMethodPoliciesList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Control parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Control parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Control parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Control parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Control parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Control parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Control parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Control parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Control parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Control parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Control parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Control parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Control.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Control prototype) {
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

    public static Control getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Control> parser() {
        return PARSER;
    }

    public Parser<Control> getParserForType() {
        return PARSER;
    }

    @Override
    public Control getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ControlOrBuilder {
        private int bitField0_;
        private Object environment_ = "";
        private List<MethodPolicy> methodPolicies_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<MethodPolicy, MethodPolicy.Builder, MethodPolicyOrBuilder> methodPoliciesBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return ControlProto.internal_static_google_api_Control_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ControlProto.internal_static_google_api_Control_fieldAccessorTable.ensureFieldAccessorsInitialized(Control.class, Builder.class);
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
            this.environment_ = "";
            if (this.methodPoliciesBuilder_ == null) {
                this.methodPolicies_ = Collections.emptyList();
            } else {
                this.methodPolicies_ = null;
                this.methodPoliciesBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFD;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ControlProto.internal_static_google_api_Control_descriptor;
        }

        @Override
        public Control getDefaultInstanceForType() {
            return Control.getDefaultInstance();
        }

        @Override
        public Control build() {
            Control result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Control buildPartial() {
            Control result = new Control(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(Control result) {
            if (this.methodPoliciesBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0) {
                    this.methodPolicies_ = Collections.unmodifiableList(this.methodPolicies_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.methodPolicies_ = this.methodPolicies_;
            } else {
                result.methodPolicies_ = this.methodPoliciesBuilder_.build();
            }
        }

        private void buildPartial0(Control result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.environment_ = this.environment_;
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
            if (other instanceof Control) {
                return this.mergeFrom((Control)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Control other) {
            if (other == Control.getDefaultInstance()) {
                return this;
            }
            if (!other.getEnvironment().isEmpty()) {
                this.environment_ = other.environment_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (this.methodPoliciesBuilder_ == null) {
                if (!other.methodPolicies_.isEmpty()) {
                    if (this.methodPolicies_.isEmpty()) {
                        this.methodPolicies_ = other.methodPolicies_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureMethodPoliciesIsMutable();
                        this.methodPolicies_.addAll(other.methodPolicies_);
                    }
                    this.onChanged();
                }
            } else if (!other.methodPolicies_.isEmpty()) {
                if (this.methodPoliciesBuilder_.isEmpty()) {
                    this.methodPoliciesBuilder_.dispose();
                    this.methodPoliciesBuilder_ = null;
                    this.methodPolicies_ = other.methodPolicies_;
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.methodPoliciesBuilder_ = alwaysUseFieldBuilders ? this.getMethodPoliciesFieldBuilder() : null;
                } else {
                    this.methodPoliciesBuilder_.addAllMessages(other.methodPolicies_);
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
                            this.environment_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 34: {
                            MethodPolicy m4 = input.readMessage(MethodPolicy.parser(), extensionRegistry);
                            if (this.methodPoliciesBuilder_ == null) {
                                this.ensureMethodPoliciesIsMutable();
                                this.methodPolicies_.add(m4);
                                continue block10;
                            }
                            this.methodPoliciesBuilder_.addMessage(m4);
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
        public String getEnvironment() {
            Object ref = this.environment_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.environment_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getEnvironmentBytes() {
            Object ref = this.environment_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.environment_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setEnvironment(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.environment_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearEnvironment() {
            this.environment_ = Control.getDefaultInstance().getEnvironment();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setEnvironmentBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Control.checkByteStringIsUtf8(value);
            this.environment_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        private void ensureMethodPoliciesIsMutable() {
            if ((this.bitField0_ & 2) == 0) {
                this.methodPolicies_ = new ArrayList<MethodPolicy>(this.methodPolicies_);
                this.bitField0_ |= 2;
            }
        }

        @Override
        public List<MethodPolicy> getMethodPoliciesList() {
            if (this.methodPoliciesBuilder_ == null) {
                return Collections.unmodifiableList(this.methodPolicies_);
            }
            return this.methodPoliciesBuilder_.getMessageList();
        }

        @Override
        public int getMethodPoliciesCount() {
            if (this.methodPoliciesBuilder_ == null) {
                return this.methodPolicies_.size();
            }
            return this.methodPoliciesBuilder_.getCount();
        }

        @Override
        public MethodPolicy getMethodPolicies(int index) {
            if (this.methodPoliciesBuilder_ == null) {
                return this.methodPolicies_.get(index);
            }
            return this.methodPoliciesBuilder_.getMessage(index);
        }

        public Builder setMethodPolicies(int index, MethodPolicy value) {
            if (this.methodPoliciesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMethodPoliciesIsMutable();
                this.methodPolicies_.set(index, value);
                this.onChanged();
            } else {
                this.methodPoliciesBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setMethodPolicies(int index, MethodPolicy.Builder builderForValue) {
            if (this.methodPoliciesBuilder_ == null) {
                this.ensureMethodPoliciesIsMutable();
                this.methodPolicies_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.methodPoliciesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addMethodPolicies(MethodPolicy value) {
            if (this.methodPoliciesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMethodPoliciesIsMutable();
                this.methodPolicies_.add(value);
                this.onChanged();
            } else {
                this.methodPoliciesBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addMethodPolicies(int index, MethodPolicy value) {
            if (this.methodPoliciesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMethodPoliciesIsMutable();
                this.methodPolicies_.add(index, value);
                this.onChanged();
            } else {
                this.methodPoliciesBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addMethodPolicies(MethodPolicy.Builder builderForValue) {
            if (this.methodPoliciesBuilder_ == null) {
                this.ensureMethodPoliciesIsMutable();
                this.methodPolicies_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.methodPoliciesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addMethodPolicies(int index, MethodPolicy.Builder builderForValue) {
            if (this.methodPoliciesBuilder_ == null) {
                this.ensureMethodPoliciesIsMutable();
                this.methodPolicies_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.methodPoliciesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllMethodPolicies(Iterable<? extends MethodPolicy> values2) {
            if (this.methodPoliciesBuilder_ == null) {
                this.ensureMethodPoliciesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.methodPolicies_);
                this.onChanged();
            } else {
                this.methodPoliciesBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearMethodPolicies() {
            if (this.methodPoliciesBuilder_ == null) {
                this.methodPolicies_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
            } else {
                this.methodPoliciesBuilder_.clear();
            }
            return this;
        }

        public Builder removeMethodPolicies(int index) {
            if (this.methodPoliciesBuilder_ == null) {
                this.ensureMethodPoliciesIsMutable();
                this.methodPolicies_.remove(index);
                this.onChanged();
            } else {
                this.methodPoliciesBuilder_.remove(index);
            }
            return this;
        }

        public MethodPolicy.Builder getMethodPoliciesBuilder(int index) {
            return this.getMethodPoliciesFieldBuilder().getBuilder(index);
        }

        @Override
        public MethodPolicyOrBuilder getMethodPoliciesOrBuilder(int index) {
            if (this.methodPoliciesBuilder_ == null) {
                return this.methodPolicies_.get(index);
            }
            return this.methodPoliciesBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends MethodPolicyOrBuilder> getMethodPoliciesOrBuilderList() {
            if (this.methodPoliciesBuilder_ != null) {
                return this.methodPoliciesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.methodPolicies_);
        }

        public MethodPolicy.Builder addMethodPoliciesBuilder() {
            return this.getMethodPoliciesFieldBuilder().addBuilder(MethodPolicy.getDefaultInstance());
        }

        public MethodPolicy.Builder addMethodPoliciesBuilder(int index) {
            return this.getMethodPoliciesFieldBuilder().addBuilder(index, MethodPolicy.getDefaultInstance());
        }

        public List<MethodPolicy.Builder> getMethodPoliciesBuilderList() {
            return this.getMethodPoliciesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<MethodPolicy, MethodPolicy.Builder, MethodPolicyOrBuilder> getMethodPoliciesFieldBuilder() {
            if (this.methodPoliciesBuilder_ == null) {
                this.methodPoliciesBuilder_ = new RepeatedFieldBuilderV3(this.methodPolicies_, (this.bitField0_ & 2) != 0, this.getParentForChildren(), this.isClean());
                this.methodPolicies_ = null;
            }
            return this.methodPoliciesBuilder_;
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

