/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.UsageProto;
import com.google.api.UsageRuleOrBuilder;
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
import com.google.protobuf.Parser;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class UsageRule
extends GeneratedMessageV3
implements UsageRuleOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int SELECTOR_FIELD_NUMBER = 1;
    private volatile Object selector_ = "";
    public static final int ALLOW_UNREGISTERED_CALLS_FIELD_NUMBER = 2;
    private boolean allowUnregisteredCalls_ = false;
    public static final int SKIP_SERVICE_CONTROL_FIELD_NUMBER = 3;
    private boolean skipServiceControl_ = false;
    private byte memoizedIsInitialized = (byte)-1;
    private static final UsageRule DEFAULT_INSTANCE = new UsageRule();
    private static final Parser<UsageRule> PARSER = new AbstractParser<UsageRule>(){

        @Override
        public UsageRule parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = UsageRule.newBuilder();
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

    private UsageRule(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private UsageRule() {
        this.selector_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new UsageRule();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return UsageProto.internal_static_google_api_UsageRule_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return UsageProto.internal_static_google_api_UsageRule_fieldAccessorTable.ensureFieldAccessorsInitialized(UsageRule.class, Builder.class);
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
    public boolean getAllowUnregisteredCalls() {
        return this.allowUnregisteredCalls_;
    }

    @Override
    public boolean getSkipServiceControl() {
        return this.skipServiceControl_;
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
        if (this.allowUnregisteredCalls_) {
            output.writeBool(2, this.allowUnregisteredCalls_);
        }
        if (this.skipServiceControl_) {
            output.writeBool(3, this.skipServiceControl_);
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
        if (this.allowUnregisteredCalls_) {
            size += CodedOutputStream.computeBoolSize(2, this.allowUnregisteredCalls_);
        }
        if (this.skipServiceControl_) {
            size += CodedOutputStream.computeBoolSize(3, this.skipServiceControl_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UsageRule)) {
            return super.equals(obj);
        }
        UsageRule other = (UsageRule)obj;
        if (!this.getSelector().equals(other.getSelector())) {
            return false;
        }
        if (this.getAllowUnregisteredCalls() != other.getAllowUnregisteredCalls()) {
            return false;
        }
        if (this.getSkipServiceControl() != other.getSkipServiceControl()) {
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
        hash = 19 * hash + UsageRule.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getSelector().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + Internal.hashBoolean(this.getAllowUnregisteredCalls());
        hash = 37 * hash + 3;
        hash = 53 * hash + Internal.hashBoolean(this.getSkipServiceControl());
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static UsageRule parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static UsageRule parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static UsageRule parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static UsageRule parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static UsageRule parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static UsageRule parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static UsageRule parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static UsageRule parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static UsageRule parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static UsageRule parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static UsageRule parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static UsageRule parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return UsageRule.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(UsageRule prototype) {
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

    public static UsageRule getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<UsageRule> parser() {
        return PARSER;
    }

    public Parser<UsageRule> getParserForType() {
        return PARSER;
    }

    @Override
    public UsageRule getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements UsageRuleOrBuilder {
        private int bitField0_;
        private Object selector_ = "";
        private boolean allowUnregisteredCalls_;
        private boolean skipServiceControl_;

        public static final Descriptors.Descriptor getDescriptor() {
            return UsageProto.internal_static_google_api_UsageRule_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return UsageProto.internal_static_google_api_UsageRule_fieldAccessorTable.ensureFieldAccessorsInitialized(UsageRule.class, Builder.class);
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
            this.allowUnregisteredCalls_ = false;
            this.skipServiceControl_ = false;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return UsageProto.internal_static_google_api_UsageRule_descriptor;
        }

        @Override
        public UsageRule getDefaultInstanceForType() {
            return UsageRule.getDefaultInstance();
        }

        @Override
        public UsageRule build() {
            UsageRule result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public UsageRule buildPartial() {
            UsageRule result = new UsageRule(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(UsageRule result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.selector_ = this.selector_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.allowUnregisteredCalls_ = this.allowUnregisteredCalls_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.skipServiceControl_ = this.skipServiceControl_;
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
            if (other instanceof UsageRule) {
                return this.mergeFrom((UsageRule)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(UsageRule other) {
            if (other == UsageRule.getDefaultInstance()) {
                return this;
            }
            if (!other.getSelector().isEmpty()) {
                this.selector_ = other.selector_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (other.getAllowUnregisteredCalls()) {
                this.setAllowUnregisteredCalls(other.getAllowUnregisteredCalls());
            }
            if (other.getSkipServiceControl()) {
                this.setSkipServiceControl(other.getSkipServiceControl());
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
                            this.selector_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block11;
                        }
                        case 16: {
                            this.allowUnregisteredCalls_ = input.readBool();
                            this.bitField0_ |= 2;
                            continue block11;
                        }
                        case 24: {
                            this.skipServiceControl_ = input.readBool();
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
            this.selector_ = UsageRule.getDefaultInstance().getSelector();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setSelectorBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            UsageRule.checkByteStringIsUtf8(value);
            this.selector_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public boolean getAllowUnregisteredCalls() {
            return this.allowUnregisteredCalls_;
        }

        public Builder setAllowUnregisteredCalls(boolean value) {
            this.allowUnregisteredCalls_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearAllowUnregisteredCalls() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.allowUnregisteredCalls_ = false;
            this.onChanged();
            return this;
        }

        @Override
        public boolean getSkipServiceControl() {
            return this.skipServiceControl_;
        }

        public Builder setSkipServiceControl(boolean value) {
            this.skipServiceControl_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearSkipServiceControl() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.skipServiceControl_ = false;
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

