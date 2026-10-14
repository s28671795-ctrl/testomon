/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.LabelDescriptor;
import com.google.api.LabelDescriptorOrBuilder;
import com.google.api.LogDescriptorOrBuilder;
import com.google.api.LogProto;
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

public final class LogDescriptor
extends GeneratedMessageV3
implements LogDescriptorOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 1;
    private volatile Object name_ = "";
    public static final int LABELS_FIELD_NUMBER = 2;
    private List<LabelDescriptor> labels_;
    public static final int DESCRIPTION_FIELD_NUMBER = 3;
    private volatile Object description_ = "";
    public static final int DISPLAY_NAME_FIELD_NUMBER = 4;
    private volatile Object displayName_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final LogDescriptor DEFAULT_INSTANCE = new LogDescriptor();
    private static final Parser<LogDescriptor> PARSER = new AbstractParser<LogDescriptor>(){

        @Override
        public LogDescriptor parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = LogDescriptor.newBuilder();
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

    private LogDescriptor(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private LogDescriptor() {
        this.name_ = "";
        this.labels_ = Collections.emptyList();
        this.description_ = "";
        this.displayName_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new LogDescriptor();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return LogProto.internal_static_google_api_LogDescriptor_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return LogProto.internal_static_google_api_LogDescriptor_fieldAccessorTable.ensureFieldAccessorsInitialized(LogDescriptor.class, Builder.class);
    }

    @Override
    public String getName() {
        Object ref = this.name_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.name_ = s2;
        return s2;
    }

    @Override
    public ByteString getNameBytes() {
        Object ref = this.name_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.name_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public List<LabelDescriptor> getLabelsList() {
        return this.labels_;
    }

    @Override
    public List<? extends LabelDescriptorOrBuilder> getLabelsOrBuilderList() {
        return this.labels_;
    }

    @Override
    public int getLabelsCount() {
        return this.labels_.size();
    }

    @Override
    public LabelDescriptor getLabels(int index) {
        return this.labels_.get(index);
    }

    @Override
    public LabelDescriptorOrBuilder getLabelsOrBuilder(int index) {
        return this.labels_.get(index);
    }

    @Override
    public String getDescription() {
        Object ref = this.description_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.description_ = s2;
        return s2;
    }

    @Override
    public ByteString getDescriptionBytes() {
        Object ref = this.description_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.description_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getDisplayName() {
        Object ref = this.displayName_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.displayName_ = s2;
        return s2;
    }

    @Override
    public ByteString getDisplayNameBytes() {
        Object ref = this.displayName_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.displayName_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            GeneratedMessageV3.writeString(output, 1, this.name_);
        }
        for (int i = 0; i < this.labels_.size(); ++i) {
            output.writeMessage(2, this.labels_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.description_)) {
            GeneratedMessageV3.writeString(output, 3, this.description_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
            GeneratedMessageV3.writeString(output, 4, this.displayName_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.name_);
        }
        for (int i = 0; i < this.labels_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(2, this.labels_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.description_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.description_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
            size += GeneratedMessageV3.computeStringSize(4, this.displayName_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LogDescriptor)) {
            return super.equals(obj);
        }
        LogDescriptor other = (LogDescriptor)obj;
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (!this.getLabelsList().equals(other.getLabelsList())) {
            return false;
        }
        if (!this.getDescription().equals(other.getDescription())) {
            return false;
        }
        if (!this.getDisplayName().equals(other.getDisplayName())) {
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
        hash = 19 * hash + LogDescriptor.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getName().hashCode();
        if (this.getLabelsCount() > 0) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getLabelsList().hashCode();
        }
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getDescription().hashCode();
        hash = 37 * hash + 4;
        hash = 53 * hash + this.getDisplayName().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static LogDescriptor parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LogDescriptor parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LogDescriptor parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LogDescriptor parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LogDescriptor parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LogDescriptor parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LogDescriptor parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static LogDescriptor parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static LogDescriptor parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static LogDescriptor parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static LogDescriptor parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static LogDescriptor parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return LogDescriptor.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(LogDescriptor prototype) {
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

    public static LogDescriptor getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<LogDescriptor> parser() {
        return PARSER;
    }

    public Parser<LogDescriptor> getParserForType() {
        return PARSER;
    }

    @Override
    public LogDescriptor getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements LogDescriptorOrBuilder {
        private int bitField0_;
        private Object name_ = "";
        private List<LabelDescriptor> labels_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<LabelDescriptor, LabelDescriptor.Builder, LabelDescriptorOrBuilder> labelsBuilder_;
        private Object description_ = "";
        private Object displayName_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return LogProto.internal_static_google_api_LogDescriptor_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return LogProto.internal_static_google_api_LogDescriptor_fieldAccessorTable.ensureFieldAccessorsInitialized(LogDescriptor.class, Builder.class);
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
            this.name_ = "";
            if (this.labelsBuilder_ == null) {
                this.labels_ = Collections.emptyList();
            } else {
                this.labels_ = null;
                this.labelsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFD;
            this.description_ = "";
            this.displayName_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return LogProto.internal_static_google_api_LogDescriptor_descriptor;
        }

        @Override
        public LogDescriptor getDefaultInstanceForType() {
            return LogDescriptor.getDefaultInstance();
        }

        @Override
        public LogDescriptor build() {
            LogDescriptor result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public LogDescriptor buildPartial() {
            LogDescriptor result = new LogDescriptor(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(LogDescriptor result) {
            if (this.labelsBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0) {
                    this.labels_ = Collections.unmodifiableList(this.labels_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.labels_ = this.labels_;
            } else {
                result.labels_ = this.labelsBuilder_.build();
            }
        }

        private void buildPartial0(LogDescriptor result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.name_ = this.name_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.description_ = this.description_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.displayName_ = this.displayName_;
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
            if (other instanceof LogDescriptor) {
                return this.mergeFrom((LogDescriptor)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(LogDescriptor other) {
            if (other == LogDescriptor.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (this.labelsBuilder_ == null) {
                if (!other.labels_.isEmpty()) {
                    if (this.labels_.isEmpty()) {
                        this.labels_ = other.labels_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureLabelsIsMutable();
                        this.labels_.addAll(other.labels_);
                    }
                    this.onChanged();
                }
            } else if (!other.labels_.isEmpty()) {
                if (this.labelsBuilder_.isEmpty()) {
                    this.labelsBuilder_.dispose();
                    this.labelsBuilder_ = null;
                    this.labels_ = other.labels_;
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.labelsBuilder_ = alwaysUseFieldBuilders ? this.getLabelsFieldBuilder() : null;
                } else {
                    this.labelsBuilder_.addAllMessages(other.labels_);
                }
            }
            if (!other.getDescription().isEmpty()) {
                this.description_ = other.description_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            if (!other.getDisplayName().isEmpty()) {
                this.displayName_ = other.displayName_;
                this.bitField0_ |= 8;
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
                block12: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block12;
                        }
                        case 10: {
                            this.name_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block12;
                        }
                        case 18: {
                            LabelDescriptor m4 = input.readMessage(LabelDescriptor.parser(), extensionRegistry);
                            if (this.labelsBuilder_ == null) {
                                this.ensureLabelsIsMutable();
                                this.labels_.add(m4);
                                continue block12;
                            }
                            this.labelsBuilder_.addMessage(m4);
                            continue block12;
                        }
                        case 26: {
                            this.description_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block12;
                        }
                        case 34: {
                            this.displayName_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 8;
                            continue block12;
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
        public String getName() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.name_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.name_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.name_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearName() {
            this.name_ = LogDescriptor.getDefaultInstance().getName();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            LogDescriptor.checkByteStringIsUtf8(value);
            this.name_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        private void ensureLabelsIsMutable() {
            if ((this.bitField0_ & 2) == 0) {
                this.labels_ = new ArrayList<LabelDescriptor>(this.labels_);
                this.bitField0_ |= 2;
            }
        }

        @Override
        public List<LabelDescriptor> getLabelsList() {
            if (this.labelsBuilder_ == null) {
                return Collections.unmodifiableList(this.labels_);
            }
            return this.labelsBuilder_.getMessageList();
        }

        @Override
        public int getLabelsCount() {
            if (this.labelsBuilder_ == null) {
                return this.labels_.size();
            }
            return this.labelsBuilder_.getCount();
        }

        @Override
        public LabelDescriptor getLabels(int index) {
            if (this.labelsBuilder_ == null) {
                return this.labels_.get(index);
            }
            return this.labelsBuilder_.getMessage(index);
        }

        public Builder setLabels(int index, LabelDescriptor value) {
            if (this.labelsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLabelsIsMutable();
                this.labels_.set(index, value);
                this.onChanged();
            } else {
                this.labelsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setLabels(int index, LabelDescriptor.Builder builderForValue) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                this.labels_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.labelsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addLabels(LabelDescriptor value) {
            if (this.labelsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLabelsIsMutable();
                this.labels_.add(value);
                this.onChanged();
            } else {
                this.labelsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addLabels(int index, LabelDescriptor value) {
            if (this.labelsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLabelsIsMutable();
                this.labels_.add(index, value);
                this.onChanged();
            } else {
                this.labelsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addLabels(LabelDescriptor.Builder builderForValue) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                this.labels_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.labelsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addLabels(int index, LabelDescriptor.Builder builderForValue) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                this.labels_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.labelsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllLabels(Iterable<? extends LabelDescriptor> values2) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.labels_);
                this.onChanged();
            } else {
                this.labelsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearLabels() {
            if (this.labelsBuilder_ == null) {
                this.labels_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
            } else {
                this.labelsBuilder_.clear();
            }
            return this;
        }

        public Builder removeLabels(int index) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                this.labels_.remove(index);
                this.onChanged();
            } else {
                this.labelsBuilder_.remove(index);
            }
            return this;
        }

        public LabelDescriptor.Builder getLabelsBuilder(int index) {
            return this.getLabelsFieldBuilder().getBuilder(index);
        }

        @Override
        public LabelDescriptorOrBuilder getLabelsOrBuilder(int index) {
            if (this.labelsBuilder_ == null) {
                return this.labels_.get(index);
            }
            return this.labelsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends LabelDescriptorOrBuilder> getLabelsOrBuilderList() {
            if (this.labelsBuilder_ != null) {
                return this.labelsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.labels_);
        }

        public LabelDescriptor.Builder addLabelsBuilder() {
            return this.getLabelsFieldBuilder().addBuilder(LabelDescriptor.getDefaultInstance());
        }

        public LabelDescriptor.Builder addLabelsBuilder(int index) {
            return this.getLabelsFieldBuilder().addBuilder(index, LabelDescriptor.getDefaultInstance());
        }

        public List<LabelDescriptor.Builder> getLabelsBuilderList() {
            return this.getLabelsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<LabelDescriptor, LabelDescriptor.Builder, LabelDescriptorOrBuilder> getLabelsFieldBuilder() {
            if (this.labelsBuilder_ == null) {
                this.labelsBuilder_ = new RepeatedFieldBuilderV3(this.labels_, (this.bitField0_ & 2) != 0, this.getParentForChildren(), this.isClean());
                this.labels_ = null;
            }
            return this.labelsBuilder_;
        }

        @Override
        public String getDescription() {
            Object ref = this.description_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.description_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getDescriptionBytes() {
            Object ref = this.description_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.description_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setDescription(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.description_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearDescription() {
            this.description_ = LogDescriptor.getDefaultInstance().getDescription();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setDescriptionBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            LogDescriptor.checkByteStringIsUtf8(value);
            this.description_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        @Override
        public String getDisplayName() {
            Object ref = this.displayName_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.displayName_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getDisplayNameBytes() {
            Object ref = this.displayName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.displayName_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setDisplayName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.displayName_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearDisplayName() {
            this.displayName_ = LogDescriptor.getDefaultInstance().getDisplayName();
            this.bitField0_ &= 0xFFFFFFF7;
            this.onChanged();
            return this;
        }

        public Builder setDisplayNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            LogDescriptor.checkByteStringIsUtf8(value);
            this.displayName_ = value;
            this.bitField0_ |= 8;
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

